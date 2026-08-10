package wild.loader;

import java.util.List;
import java.util.Map;

/**
 * Development helper: resolves every metadata document the install walks through and prints what
 * it found. Nothing large is downloaded, so this is the quick way to see whether Mojang or Fabric
 * changed a payload the loader depends on.
 */
public final class MetaCheck {
   public static void main(String[] args) throws Exception {
      String version = args.length > 0 ? args[0] : Pipeline.MINECRAFT_VERSION;
      String versionUrl = null;

      for (Object entry : Json.array(Json.get(Json.parse(Downloader.text("https://piston-meta.mojang.com/mc/game/version_manifest_v2.json")), "versions"))) {
         if (version.equals(Json.text(Json.get(entry, "id"), ""))) {
            versionUrl = Json.text(Json.get(entry, "url"), null);
            break;
         }
      }

      if (versionUrl == null) {
         System.out.println("Mojang не знает версию " + version);
         return;
      }

      Map<String, Object> vanilla = Json.object(Json.parse(Downloader.text(versionUrl)));
      System.out.println("версия  " + version + " тип=" + Json.text(vanilla.get("type"), "?"));
      System.out.println("клиент  " + Downloader.humanSize(Json.number(Json.at(vanilla, "downloads", "client", "size"), -1L)));
      System.out.println("библиотек " + Json.array(vanilla.get("libraries")).size());

      String index = Json.text(Json.at(vanilla, "assetIndex", "id"), "?");
      Map<String, Object> objects = Json.object(Json.get(Json.parse(Downloader.text(Json.text(Json.at(vanilla, "assetIndex", "url"), ""))), "objects"));
      System.out.println("ресурсов " + objects.size() + " (индекс " + index + ")");

      String component = Json.text(Json.at(vanilla, "javaVersion", "component"), "?");
      List<Object> builds = Json.array(Json.at(Json.parse(Downloader.text(
         "https://launchermeta.mojang.com/v1/products/java-runtime/2ec0cc96c44e5a76b9c8b7c39df7210883d12871/all.json"
      )), Runtimes.platform(), component));
      if (builds.isEmpty()) {
         System.out.println("java    " + component + " для " + Runtimes.platform() + " не собирается");
      } else {
         Map<String, Object> files = Json.object(Json.get(Json.parse(Downloader.text(Json.text(Json.at(builds.get(0), "manifest", "url"), ""))), "files"));
         System.out.println("java    " + component + " " + Json.text(Json.at(builds.get(0), "version", "name"), "?") + ", файлов " + files.size());
      }

      String loader = null;

      for (Object entry : Json.array(Json.parse(Downloader.text("https://meta.fabricmc.net/v2/versions/loader/" + version)))) {
         String candidate = Json.text(Json.at(entry, "loader", "version"), null);
         if (candidate != null && (loader == null || Json.flag(Json.at(entry, "loader", "stable"), false))) {
            loader = candidate;
            if (Json.flag(Json.at(entry, "loader", "stable"), false)) {
               break;
            }
         }
      }

      if (loader == null) {
         System.out.println("fabric  не поддерживает " + version);
         return;
      }

      Map<String, Object> profile = Json.object(
         Json.parse(Downloader.text("https://meta.fabricmc.net/v2/versions/loader/" + version + "/" + loader + "/profile/json"))
      );
      System.out.println("fabric  " + loader + " main=" + Json.text(profile.get("mainClass"), "?") + " библиотек " + Json.array(profile.get("libraries")).size());
   }
}
