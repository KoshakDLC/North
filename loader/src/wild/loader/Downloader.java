package wild.loader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Downloads the client build from GitHub releases.
 *
 * <p>The loader ships without JSON or HTTP libraries, so the release payload is scanned with a
 * regex instead of being parsed. GitHub always emits an asset as name → size → download url,
 * which is enough to pick the jar and know how big it is.
 */
final class Downloader {
   private static final Pattern ASSET = Pattern.compile(
      "\"name\"\\s*:\\s*\"([^\"]+\\.jar)\".*?\"size\"\\s*:\\s*(\\d+).*?\"browser_download_url\"\\s*:\\s*\"([^\"]+)\"", Pattern.DOTALL
   );
   private static final Pattern TAG = Pattern.compile("\"tag_name\"\\s*:\\s*\"([^\"]+)\"");
   private static final int BUFFER = 1 << 16;

   private static final HttpClient CLIENT = HttpClient.newBuilder()
      .connectTimeout(Duration.ofSeconds(10L))
      .followRedirects(Redirect.NORMAL)
      .build();

   private Downloader() {
   }

   record Asset(String tag, String name, String url, long size) {
      /** Cache subfolder, so a new release downloads again while the jar keeps its real name. */
      String safeTag() {
         String safe = this.tag.replaceAll("[^A-Za-z0-9._-]", "_");
         return safe.isBlank() ? "latest" : safe;
      }
   }

   interface Progress {
      void update(long done, long total);
   }

   /** Latest release of {@code owner/name}, or null when the repository has no published jar. */
   static Downloader.Asset latestRelease(String repository) throws IOException, InterruptedException {
      URI uri = URI.create("https://api.github.com/repos/" + repository.trim() + "/releases/latest");
      HttpRequest request = HttpRequest.newBuilder(uri)
         .timeout(Duration.ofSeconds(20L))
         .header("Accept", "application/vnd.github+json")
         .header("User-Agent", "NorthLoader")
         .GET()
         .build();
      HttpResponse<String> response = CLIENT.send(request, BodyHandlers.ofString(StandardCharsets.UTF_8));
      if (response.statusCode() == 404) {
         return null;
      } else if (response.statusCode() < 200 || response.statusCode() >= 300) {
         throw new IOException("GitHub ответил HTTP " + response.statusCode());
      } else {
         String body = response.body();
         Matcher tagMatcher = TAG.matcher(body);
         String tag = tagMatcher.find() ? tagMatcher.group(1) : "latest";
         Matcher assets = ASSET.matcher(body);

         while (assets.find()) {
            String name = assets.group(1);
            if (!name.contains("-sources") && !name.contains("-dev")) {
               return new Downloader.Asset(tag, name, assets.group(3), Long.parseLong(assets.group(2)));
            }
         }

         return null;
      }
   }

   /** Plain GET of a metadata document. */
   static String text(String url) throws IOException, InterruptedException {
      HttpRequest request = HttpRequest.newBuilder(URI.create(url))
         .timeout(Duration.ofSeconds(30L))
         .header("Accept", "application/json")
         .header("User-Agent", "NorthLoader")
         .GET()
         .build();
      HttpResponse<String> response = CLIENT.send(request, BodyHandlers.ofString(StandardCharsets.UTF_8));
      if (response.statusCode() < 200 || response.statusCode() >= 300) {
         throw new IOException("HTTP " + response.statusCode() + " от " + url);
      }

      return response.body();
   }

   /**
    * Downloads a file unless the copy on disk already matches. The hash is only checked when the
    * metadata provides one; a matching size is enough for the rest, which keeps repeat launches fast.
    */
   static boolean fileIfMissing(String url, Path target, String sha1, long size) throws IOException, InterruptedException {
      if (intact(target, sha1, size)) {
         return false;
      }

      download(url, target, null);
      return true;
   }

   static boolean intact(Path target, String sha1, long size) {
      if (!Files.isRegularFile(target)) {
         return false;
      }

      try {
         if (size > 0L && Files.size(target) != size) {
            return false;
         }
      } catch (IOException exception) {
         return false;
      }

      return sha1 == null || sha1.isBlank() || sha1.equalsIgnoreCase(sha1(target));
   }

   static String sha1(Path file) {
      try (InputStream stream = Files.newInputStream(file)) {
         MessageDigest digest = MessageDigest.getInstance("SHA-1");
         byte[] buffer = new byte[BUFFER];
         int read;

         while ((read = stream.read(buffer)) > 0) {
            digest.update(buffer, 0, read);
         }

         StringBuilder builder = new StringBuilder();

         for (byte value : digest.digest()) {
            builder.append(String.format("%02x", value));
         }

         return builder.toString();
      } catch (Exception exception) {
         return "";
      }
   }

   /** Streams a file to disk, reporting progress. Writes to a temporary file first. */
   static void download(String url, Path target, Downloader.Progress progress) throws IOException, InterruptedException {
      Path parent = target.getParent();
      if (parent != null) {
         Files.createDirectories(parent);
      }

      HttpRequest request = HttpRequest.newBuilder(URI.create(url))
         .timeout(Duration.ofMinutes(5L))
         .header("Accept", "application/octet-stream")
         .header("User-Agent", "NorthLoader")
         .GET()
         .build();
      HttpResponse<InputStream> response = CLIENT.send(request, BodyHandlers.ofInputStream());
      if (response.statusCode() < 200 || response.statusCode() >= 300) {
         response.body().close();
         throw new IOException("HTTP " + response.statusCode());
      }

      long total = response.headers().firstValueAsLong("content-length").orElse(-1L);
      Path temporary = target.resolveSibling(target.getFileName() + ".part");

      try (InputStream input = response.body(); OutputStream output = Files.newOutputStream(temporary)) {
         byte[] buffer = new byte[BUFFER];
         long done = 0L;
         int read;

         while ((read = input.read(buffer)) > 0) {
            output.write(buffer, 0, read);
            done += read;
            if (progress != null) {
               progress.update(done, total);
            }
         }
      }

      Files.move(temporary, target, StandardCopyOption.REPLACE_EXISTING);
   }

   /**
    * Runs downloads on a small pool. An asset index is thousands of tiny files, and fetching them
    * one at a time takes minutes of pure latency.
    */
   static void parallel(List<Callable<Void>> tasks) throws IOException, InterruptedException {
      if (tasks.isEmpty()) {
         return;
      }

      ExecutorService pool = Executors.newFixedThreadPool(Math.min(16, Math.max(4, tasks.size())));

      try {
         List<Future<Void>> futures = pool.invokeAll(tasks);

         for (Future<Void> future : futures) {
            try {
               future.get();
            } catch (ExecutionException exception) {
               Throwable cause = exception.getCause();
               if (cause instanceof IOException failure) {
                  throw failure;
               }

               throw new IOException(cause == null ? exception.toString() : cause.toString(), cause);
            }
         }
      } finally {
         pool.shutdownNow();
      }
   }

   static String humanSize(long bytes) {
      if (bytes < 0L) {
         return "?";
      } else {
         return bytes < 1024L * 1024L ? Math.max(1L, bytes / 1024L) + " КБ" : String.format("%.1f МБ", bytes / 1048576.0);
      }
   }
}
