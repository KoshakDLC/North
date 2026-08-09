package ru.metaculture.protection;

import com.mojang.authlib.yggdrasil.ProfileResult;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.session.ProfileKeys;
import net.minecraft.client.session.Session;
import net.minecraft.client.session.Session.AccountType;
import org.wild.mixin.acceser.MinecraftClientSessionAccessor;

public final class SessionManager {
   private static SessionManager.SessionManagerData current;

   private SessionManager() {
   }

   public static void invoke(MinecraftClient minecraftClient) {
      if (minecraftClient != null && current == null) {
         MinecraftClientSessionAccessor minecraftClientSessionAccessor = (MinecraftClientSessionAccessor)minecraftClient;
         Session session2 = minecraftClientSessionAccessor.litka$getSession();
         if (session2 != null) {
            ProfileKeys profileKeys2 = minecraftClientSessionAccessor.litka$getProfileKeys();
            CompletableFuture completableFuture = minecraftClientSessionAccessor.litka$getGameProfileFuture();
            current = new SessionManager.SessionManagerData(session2, profileKeys2 == null ? ProfileKeys.MISSING : profileKeys2, completableFuture == null ? CompletableFuture.completedFuture(null) : completableFuture);
         }
      }
   }

   public static Optional<Session> resolve(MinecraftClient minecraftClient) {
      invoke(minecraftClient);
      return Optional.ofNullable(current).map(SessionManager.SessionManagerData::session);
   }

   public static boolean check(MinecraftClient minecraftClient, String string) {
      invoke(minecraftClient);
      if (minecraftClient != null && current != null) {
         if (string != null && !current.session().getUsername().equalsIgnoreCase(string)) {
            return false;
         } else {
            invoke3(minecraftClient, current);
            return true;
         }
      } else {
         return false;
      }
   }

   public static Session resolve2(MinecraftClient minecraftClient, String string) {
      if (minecraftClient == null) {
         return null;
      } else {
         invoke(minecraftClient);
         String text = string == null ? "" : string;
         Session session3 = new Session(
            text,
            UUID.nameUUIDFromBytes(("OfflinePlayer:" + text).getBytes(StandardCharsets.UTF_8)),
            "",
            Optional.empty(),
            Optional.empty(),
            AccountType.LEGACY
         );
         invoke3(minecraftClient, new SessionManager.SessionManagerData(session3, ProfileKeys.MISSING, CompletableFuture.completedFuture(null)));
         return session3;
      }
   }

   public static void invoke2() {
      SessionManager.SessionManagerData sessionManagerData = current;
      current = null;
      if (sessionManagerData != null && sessionManagerData.gameProfileFuture() != null && !sessionManagerData.gameProfileFuture().isDone()) {
         sessionManagerData.gameProfileFuture().cancel(true);
      }
   }

   private static void invoke3(MinecraftClient minecraftClient, SessionManager.SessionManagerData sessionManagerData2) {
      MinecraftClientSessionAccessor minecraftClientSessionAccessor2 = (MinecraftClientSessionAccessor)minecraftClient;
      minecraftClientSessionAccessor2.litka$setSession(sessionManagerData2.session());
      minecraftClientSessionAccessor2.litka$setProfileKeys(sessionManagerData2.profileKeys());
      minecraftClientSessionAccessor2.litka$setGameProfileFuture(sessionManagerData2.gameProfileFuture());
   }

   record SessionManagerData(Session session, ProfileKeys profileKeys, CompletableFuture<ProfileResult> gameProfileFuture) {
   }
}
