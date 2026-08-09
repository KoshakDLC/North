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
import java.time.Duration;
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
         .header("User-Agent", "WildLoader")
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

   /** Streams a file to disk, reporting progress. Writes to a temporary file first. */
   static void download(String url, Path target, Downloader.Progress progress) throws IOException, InterruptedException {
      Path parent = target.getParent();
      if (parent != null) {
         Files.createDirectories(parent);
      }

      HttpRequest request = HttpRequest.newBuilder(URI.create(url))
         .timeout(Duration.ofMinutes(5L))
         .header("Accept", "application/octet-stream")
         .header("User-Agent", "WildLoader")
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

   static String humanSize(long bytes) {
      if (bytes < 0L) {
         return "?";
      } else {
         return bytes < 1024L * 1024L ? Math.max(1L, bytes / 1024L) + " КБ" : String.format("%.1f МБ", bytes / 1048576.0);
      }
   }
}
