package north.license;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/** JSON file backed license key registry. */
final class Store {
   private final Path file;
   private final Object lock = new Object();
   private final Map<String, KeyRecord> byHash = new LinkedHashMap<>();

   Store(Path file) throws Exception {
      this.file = file;
      this.load();
   }

   List<String> createKeys(int count, long validUntilMs, String role, String usernamePrefix, int maxDevices) throws Exception {
      List<String> created = new ArrayList<>();
      synchronized (this.lock) {
         for (int i = 0; i < count; i++) {
            String key = generateKey();
            String hash = Crypto.sha256Hex(normalize(key));
            int uid = 1000 + this.byHash.size() + 1;
            String username = usernamePrefix == null || usernamePrefix.isBlank() ? "User" + uid : usernamePrefix + uid;
            this.byHash.put(hash, new KeyRecord(hash, validUntilMs, role.toUpperCase(Locale.ROOT), username, uid, maxDevices, new ArrayList<>(), false));
            created.add(key);
         }

         this.save();
      }

      return created;
   }

   ActivationResult activate(String rawKey, String hwid) throws Exception {
      if (rawKey == null || rawKey.isBlank() || hwid == null || hwid.isBlank()) {
         return ActivationResult.error("Введите ключ и HWID");
      }

      String hash = Crypto.sha256Hex(normalize(rawKey));
      synchronized (this.lock) {
         KeyRecord record = this.byHash.get(hash);
         if (record == null) {
            return ActivationResult.error("Ключ не найден");
         }

         if (record.revoked) {
            return ActivationResult.error("Ключ отозван");
         }

         long now = Instant.now().toEpochMilli();
         if (record.validUntilMs > 0L && record.validUntilMs <= now) {
            return ActivationResult.error("Срок ключа истёк");
         }

         String device = hwid.trim().toLowerCase(Locale.ROOT);
         if (!record.devices.contains(device)) {
            if (record.devices.size() >= Math.max(1, record.maxDevices)) {
               return ActivationResult.error("Лимит устройств исчерпан (" + record.maxDevices + ")");
            }

            record.devices.add(device);
            this.save();
         }

         return ActivationResult.ok(record, device);
      }
   }

   boolean revoke(String rawKey) throws Exception {
      String hash = Crypto.sha256Hex(normalize(rawKey));
      synchronized (this.lock) {
         KeyRecord record = this.byHash.get(hash);
         if (record == null) {
            return false;
         }

         record.revoked = true;
         this.save();
         return true;
      }
   }

   /** Online check used by loader/client so a revoked key dies even with a local license file. */
   ActivationResult validate(String keyHash, String hwid) {
      if (keyHash == null || keyHash.isBlank() || hwid == null || hwid.isBlank()) {
         return ActivationResult.error("Нет данных лицензии");
      }

      synchronized (this.lock) {
         KeyRecord record = this.byHash.get(keyHash.trim().toLowerCase(Locale.ROOT));
         if (record == null) {
            // accept raw hex as stored
            record = this.byHash.get(keyHash.trim());
         }

         if (record == null) {
            return ActivationResult.error("Ключ не найден");
         }

         if (record.revoked) {
            return ActivationResult.error("Ключ отозван");
         }

         long now = Instant.now().toEpochMilli();
         if (record.validUntilMs > 0L && record.validUntilMs <= now) {
            return ActivationResult.error("Срок ключа истёк");
         }

         String device = hwid.trim().toLowerCase(Locale.ROOT);
         if (!record.devices.contains(device)) {
            return ActivationResult.error("HWID не привязан к ключу");
         }

         return ActivationResult.ok(record, device);
      }
   }

   int size() {
      synchronized (this.lock) {
         return this.byHash.size();
      }
   }

   private void load() throws Exception {
      if (!Files.isRegularFile(this.file)) {
         return;
      }

      String text = Files.readString(this.file, StandardCharsets.UTF_8).trim();
      if (text.isEmpty()) {
         return;
      }

      Object parsed = MiniJson.parse(text);
      List<Object> keys = MiniJson.array(MiniJson.get(parsed, "keys"));
      for (Object entry : keys) {
         KeyRecord record = KeyRecord.fromJson(MiniJson.object(entry));
         this.byHash.put(record.keyHash, record);
      }
   }

   private void save() throws Exception {
      Path parent = this.file.getParent();
      if (parent != null) {
         Files.createDirectories(parent);
      }

      StringBuilder builder = new StringBuilder();
      builder.append("{\n  \"keys\": [\n");
      int index = 0;
      for (KeyRecord record : this.byHash.values()) {
         if (index++ > 0) {
            builder.append(",\n");
         }

         builder.append("    ").append(record.toJson());
      }

      builder.append("\n  ]\n}\n");
      Files.writeString(this.file, builder.toString(), StandardCharsets.UTF_8);
   }

   private static String normalize(String key) {
      return key.trim().toUpperCase(Locale.ROOT).replace(" ", "");
   }

   private static String generateKey() {
      String alphabet = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
      StringBuilder builder = new StringBuilder();
      ThreadLocalRandom random = ThreadLocalRandom.current();
      for (int group = 0; group < 4; group++) {
         if (group > 0) {
            builder.append('-');
         }

         for (int i = 0; i < 4; i++) {
            builder.append(alphabet.charAt(random.nextInt(alphabet.length())));
         }
      }

      return builder.toString();
   }

   static final class KeyRecord {
      final String keyHash;
      final long validUntilMs;
      final String role;
      final String username;
      final int uid;
      final int maxDevices;
      final List<String> devices;
      boolean revoked;

      KeyRecord(String keyHash, long validUntilMs, String role, String username, int uid, int maxDevices, List<String> devices, boolean revoked) {
         this.keyHash = keyHash;
         this.validUntilMs = validUntilMs;
         this.role = role;
         this.username = username;
         this.uid = uid;
         this.maxDevices = maxDevices;
         this.devices = devices;
         this.revoked = revoked;
      }

      static KeyRecord fromJson(Map<String, Object> json) {
         List<String> devices = new ArrayList<>();
         for (Object value : MiniJson.array(json.get("devices"))) {
            if (value instanceof String text) {
               devices.add(text);
            }
         }

         return new KeyRecord(
            MiniJson.text(json.get("keyHash"), ""),
            MiniJson.number(json.get("validUntil"), 0L),
            MiniJson.text(json.get("role"), "USER"),
            MiniJson.text(json.get("username"), "User"),
            (int)MiniJson.number(json.get("uid"), 1000L),
            (int)MiniJson.number(json.get("maxDevices"), 1L),
            devices,
            MiniJson.flag(json.get("revoked"), false)
         );
      }

      String toJson() {
         StringBuilder devicesJson = new StringBuilder("[");
         for (int i = 0; i < this.devices.size(); i++) {
            if (i > 0) {
               devicesJson.append(',');
            }

            devicesJson.append(MiniJson.quote(this.devices.get(i)));
         }

         devicesJson.append(']');
         return "{"
            + "\"keyHash\":" + MiniJson.quote(this.keyHash) + ","
            + "\"validUntil\":" + this.validUntilMs + ","
            + "\"role\":" + MiniJson.quote(this.role) + ","
            + "\"username\":" + MiniJson.quote(this.username) + ","
            + "\"uid\":" + this.uid + ","
            + "\"maxDevices\":" + this.maxDevices + ","
            + "\"devices\":" + devicesJson + ","
            + "\"revoked\":" + this.revoked
            + "}";
      }
   }

   record ActivationResult(boolean ok, String error, KeyRecord record, String hwid) {
      static ActivationResult ok(KeyRecord record, String hwid) {
         return new ActivationResult(true, null, record, hwid);
      }

      static ActivationResult error(String message) {
         return new ActivationResult(false, message, null, null);
      }
   }
}
