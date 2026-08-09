package wild.loader;

/** Development helper: checks that GitHub release parsing works against real repositories. */
public final class DownloadCheck {
   public static void main(String[] args) throws Exception {
      if (args.length > 1 && "download".equals(args[0])) {
         Downloader.Asset asset = Downloader.latestRelease(args[1]);
         java.nio.file.Path target = java.nio.file.Paths.get(System.getProperty("java.io.tmpdir"), "wild-check", asset.safeTag(), asset.name());
         long[] last = {-1L};
         Downloader.download(asset.url(), target, (done, total) -> {
            long percent = total > 0L ? done * 100L / total : -1L;
            if (percent != last[0] && percent % 25L == 0L) {
               last[0] = percent;
               System.out.println("  " + percent + "% (" + Downloader.humanSize(done) + ")");
            }
         });
         System.out.println("скачано в " + target + " размер=" + java.nio.file.Files.size(target) + " ожидалось=" + asset.size());
         return;
      }

      for (String repository : args) {
         try {
            Downloader.Asset asset = Downloader.latestRelease(repository);
            if (asset == null) {
               System.out.println(repository + " -> нет джарника в релизах");
            } else {
               System.out.println(
                  repository + " -> тег=" + asset.tag() + " файл=" + asset.name() + " размер=" + Downloader.humanSize(asset.size()) + " папка=" + asset.safeTag()
               );
            }
         } catch (Exception exception) {
            System.out.println(repository + " -> ошибка: " + exception);
         }
      }
   }
}
