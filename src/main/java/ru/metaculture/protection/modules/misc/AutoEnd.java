package ru.metaculture.protection;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.Settings;
import baritone.api.pathing.goals.GoalNear;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import org.wild.mixin.acceser.BossBarHudAccessor;
import org.wild.mixin.acceser.ClientPlayerInteractionManagerAccessor;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "AutoEnd",
   category = Category.Misc,
   description = "Фарм энда FunTime: стеш → инвиз → /an фарма → /warp энда"
)
public class AutoEnd extends Module {
   public final ModeSetting rezhim = new ModeSetting("Режим", "Baritone", "Baritone", "Скан");
   public final NumberSetting radiusPoiska = new NumberSetting("Радиус поиска", 48.0F, 16.0F, 96.0F, 1.0F, false);
   public final NumberSetting distantsiyaLoma = new NumberSetting("Дистанция лома", 4.2F, 2.5F, 5.0F, 0.1F, true)
      .setVisibilityCondition(() -> !this.rezhim.is("Скан"));
   public final BooleanSetting podbiratLoot = new BooleanSetting("Подбирать лут", true);
   public final BooleanSetting chistitArheologiyu = new BooleanSetting("Чистить археологию", true);
   public final TextSetting anarhiyaSklada = new TextSetting("Анархия склада", "312");
   public final TextSetting anarhiyaFarma = new TextSetting("Анархия фарма", "103");
   public final TextSetting warpEnda = new TextSetting("Варп энда", "endeajavar");
   public final TextSetting tablivkaResursov = new TextSetting("Табличка ресурсов", "Ресурсы");
   public final TextSetting tablivkaInviza = new TextSetting("Табличка инвиза", "Инвиз");
   public final NumberSetting pauzaPosleBoya = new NumberSetting("Пауза после боя (сек)", 2.0F, 0.5F, 10.0F, 0.5F, true);
   public final BooleanSetting logi = new BooleanSetting("Логи", true);
   public final TextSetting dopolnitelnyeBloki = new TextSetting("Доп. блоки", "decorated_pot,suspicious_sand,suspicious_gravel");

   private static final Pattern PVP_SECONDS_PATTERN = Pattern.compile("(?iu)(\\d+)\\s*(?:сек(?:унд(?:ы|у)?)?|sec|s)\\b");
   private static final long RESCAN_MS = 1500L;
   private static final long MINE_RESTART_MS = 2500L;
   private static final long BREAK_TIMEOUT_MS = 5000L;
   private static final long BRUSH_TIMEOUT_MS = 12000L;
   private static final long WARP_RETRY_MS = 4000L;
   private static final long WARP_END_WAIT_MS = 2500L;
   private static final long DEPOSIT_CLICK_MS = 60L;
   private static final long DRINK_MS = 1850L;
   private static final double LOOT_RANGE = 24.0;
   private static final double CHEST_REACH = 4.0;

   private IBaritone baritone;
   private AutoEnd.State state = AutoEnd.State.SEARCH;
   private BlockPos target;
   private BlockPos breaking;
   private BlockPos stashChest;
   private BlockPos invizChest;
   private boolean targetBrushable;
   private boolean wasInPvp;
   private boolean farmedThisTrip;
   private boolean farmZone;
   private boolean anarchyFullFlag;
   private long pvpClearSince;
   private int pvpRemainingSeconds = -1;
   private int previousSelectedSlot = -1;
   private boolean loggedNoBrush;
   private boolean drinkingInviz;
   private final Set<BlockPos> blacklist = new HashSet<>();
   private final DualTimer rescanTimer = new DualTimer();
   private final DualTimer mineTimer = new DualTimer();
   private final DualTimer breakTimer = new DualTimer();
   private final DualTimer pathTimer = new DualTimer();
   private final DualTimer depositTimer = new DualTimer();
   private final DualTimer actionTimer = new DualTimer();
   private final DualTimer warpTimer = new DualTimer();
   private final DualTimer drinkTimer = new DualTimer();

   private boolean prevAllowBreak;
   private boolean prevAllowPlace;
   private boolean prevAllowSprint;
   private boolean prevLegitMine;
   private boolean prevMineScanDroppedItems;
   private float prevBlockReach;
   private int prevMineMaxOreLocationsCount;

   public AutoEnd() {
      this.addSettings(
         new Setting[]{
            this.rezhim,
            this.radiusPoiska,
            this.distantsiyaLoma,
            this.podbiratLoot,
            this.chistitArheologiyu,
            this.anarhiyaSklada,
            this.anarhiyaFarma,
            this.warpEnda,
            this.tablivkaResursov,
            this.tablivkaInviza,
            this.pauzaPosleBoya,
            this.logi,
            this.dopolnitelnyeBloki
         }
      );
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.baritone = BaritoneAPI.getProvider().getPrimaryBaritone();
      this.target = null;
      this.breaking = null;
      this.stashChest = null;
      this.invizChest = null;
      this.targetBrushable = false;
      this.wasInPvp = false;
      this.farmedThisTrip = false;
      this.farmZone = false;
      this.anarchyFullFlag = false;
      this.pvpClearSince = 0L;
      this.pvpRemainingSeconds = -1;
      this.previousSelectedSlot = -1;
      this.loggedNoBrush = false;
      this.drinkingInviz = false;
      this.blacklist.clear();
      this.rescanTimer.invoke();
      this.mineTimer.invoke();
      this.breakTimer.invoke();
      this.pathTimer.invoke();
      this.depositTimer.invoke();
      this.actionTimer.invoke();
      this.warpTimer.invoke();
      this.drinkTimer.invoke();
      this.captureBaritoneSettings();
      this.applyBaritoneSettings();
      this.syncAnarchyFromCommand();
      this.log("Запуск цикла: стеш → инвиз → /an" + this.resolveFarmAnarchy() + " → /warp " + this.resolveEndWarp());

      if (this.farmZone || this.isInEndDimension()) {
         this.farmZone = true;
         this.state = AutoEnd.State.SEARCH;
         this.log("Уже на фарме — продолжаю");
         if (this.rezhim.is("Baritone")) {
            this.startMineProcess();
         }
      } else {
         this.beginCycleFromStash();
      }
   }

   @Override
   public void onDisable() {
      this.releaseUseKey();
      this.restoreSelectedSlot();
      this.cancelBaritone();
      this.restoreBaritoneSettings();
      this.target = null;
      this.breaking = null;
      this.stashChest = null;
      this.invizChest = null;
      this.drinkingInviz = false;
      this.farmZone = false;
      this.state = AutoEnd.State.SEARCH;
      RotationController.rotationControllerState2 = RotationController.RotationControllerState2.IDLE;
      RotationController.intValue = 0;
      RotationController.rotation = null;
      FreeLookController.active = false;
      if (CLIENT.player != null) {
         CLIENT.player.closeHandledScreen();
      }

      super.onDisable();
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (!packetEvent.getPacketEventState().equals(PacketEvent.PacketEventState.RECEIVE)) {
         return;
      }

      if (!(packetEvent.getPacket() instanceof GameMessageS2CPacket packet)) {
         return;
      }

      String text = packet.content().getString().replaceAll("(?i)§[0-9a-fk-or]", "").toLowerCase(Locale.ROOT);
      if (text.isEmpty()) {
         return;
      }

      if (this.state == AutoEnd.State.WARP_FARM
         && (text.contains("заполнен") || text.contains("переполнен") || text.contains("полный") || text.contains("full"))) {
         this.anarchyFullFlag = true;
         this.log("Анархия /an" + this.resolveFarmAnarchy() + " заполнена — пробую ещё");
      }
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player == null || CLIENT.world == null || CLIENT.interactionManager == null || this.baritone == null) {
         return;
      }

      if (PlayerHelper.check()) {
         this.cancelBaritone();
         return;
      }

      this.pruneBlacklist();
      this.updatePvpAndHomeRequest();

      if (this.isStashFlow()) {
         this.tickStashFlow();
         return;
      }

      if (this.isInvizFlow()) {
         this.tickInvizFlow();
         return;
      }

      if (this.isTravelFlow()) {
         this.tickTravelFlow();
         return;
      }

      if (this.isInCombat()) {
         if (this.state != AutoEnd.State.WAIT_PVP) {
            this.cancelBaritone();
            this.releaseUseKey();
            this.restoreSelectedSlot();
            this.state = AutoEnd.State.WAIT_PVP;
            this.log("Режим боя — жду окончания (парсю боссбар)");
         } else {
            int sec = this.parsePvpSeconds();
            if (sec >= 0 && sec != this.pvpRemainingSeconds) {
               this.pvpRemainingSeconds = sec;
               this.pvpClearSince = 0L;
               this.log("Бой продлён/обновлён: ~" + sec + "с");
            }
         }

         return;
      }

      if (this.state == AutoEnd.State.WAIT_PVP) {
         return;
      }

      if (!this.farmZone) {
         this.beginCycleFromStash();
         return;
      }

      if (this.tickLootPickup()) {
         return;
      }

      if (this.rezhim.is("Baritone")) {
         this.tickBaritoneMine();
      } else {
         this.tickScanMode();
      }
   }

   private boolean isInCombat() {
      return this.parsePvpSeconds() >= 0;
   }

   private int parsePvpSeconds() {
      if (CLIENT.inGameHud == null || CLIENT.inGameHud.getBossBarHud() == null) {
         return -1;
      }

      Map<?, ?> bars = ((BossBarHudAccessor)CLIENT.inGameHud.getBossBarHud()).getBossBars();
      int best = -1;

      for (Object value : bars.values()) {
         if (!(value instanceof ClientBossBar bossBar)) {
            continue;
         }

         String text = bossBar.getName().getString().replaceAll("(?i)§[0-9a-fk-or]", "").toLowerCase(Locale.ROOT);
         boolean combat = text.contains("pvp")
            || text.contains("пвп")
            || text.contains("combat")
            || text.contains("режим боя")
            || text.contains("в бою")
            || text.contains("до выхода")
            || text.contains("нельзя выйти")
            || text.contains("не выходите")
            || text.contains("выход через");
         if (!combat) {
            continue;
         }

         Matcher matcher = PVP_SECONDS_PATTERN.matcher(text);
         if (matcher.find()) {
            try {
               best = Math.max(best, Integer.parseInt(matcher.group(1)));
            } catch (NumberFormatException ignored) {
               best = Math.max(best, 999);
            }
         } else {
            best = Math.max(best, 999);
         }
      }

      return best;
   }

   private boolean isInEndDimension() {
      return CLIENT.world != null && CLIENT.world.getRegistryKey().equals(World.END);
   }

   private void updatePvpAndHomeRequest() {
      boolean inPvp = this.isInCombat();
      long now = System.currentTimeMillis();
      if (inPvp) {
         this.wasInPvp = true;
         this.pvpClearSince = 0L;
         int sec = this.parsePvpSeconds();
         if (sec != this.pvpRemainingSeconds) {
            this.pvpRemainingSeconds = sec;
         }

         if (this.isStashFlow() || this.isInvizFlow() || this.isTravelFlow()) {
            this.cancelBaritone();
            this.releaseUseKey();
            if (CLIENT.player != null) {
               CLIENT.player.closeHandledScreen();
            }

            this.state = AutoEnd.State.WAIT_PVP;
            this.log("Снова бой — жду, пока спадёт (может продлиться)");
         }

         return;
      }

      if (!this.wasInPvp) {
         this.pvpRemainingSeconds = -1;
         return;
      }

      long clearMs = Math.round(this.pauzaPosleBoya.getValue() * 1000.0F);
      if (this.pvpClearSince <= 0L) {
         this.pvpClearSince = now;
         this.log("Бой спал — пауза " + this.pauzaPosleBoya.getValue() + "с (если продлится, жду снова)");
         return;
      }

      if (now - this.pvpClearSince < clearMs) {
         return;
      }

      this.wasInPvp = false;
      this.pvpClearSince = 0L;
      this.pvpRemainingSeconds = -1;

      if (this.farmZone && (this.hasLootToDeposit() || this.farmedThisTrip)) {
         this.log("Бой закончился — еду на стеш /an" + this.resolveStashAnarchy());
         this.beginStashDepositFlow();
      } else if (this.farmZone) {
         this.log("Бой закончился, лута нет — продолжаю фарм");
         this.state = AutoEnd.State.SEARCH;
         if (this.rezhim.is("Baritone")) {
            this.startMineProcess();
         }
      }
   }

   private boolean tickLootPickup() {
      if (!this.podbiratLoot.isEnabled()) {
         return false;
      }

      ItemEntity loot = this.findNearbyLoot();
      if (loot == null) {
         if (this.state == AutoEnd.State.LOOT) {
            this.state = AutoEnd.State.SEARCH;
         }

         return false;
      }

      if (this.state != AutoEnd.State.LOOT) {
         this.cancelBaritone();
         this.restoreSelectedSlot();
         this.state = AutoEnd.State.LOOT;
         this.farmedThisTrip = true;
         this.log("Подбираю: " + loot.getStack().getName().getString());
         this.pathTimer.setTimestamp22(0L);
      }

      if (!this.baritone.getCustomGoalProcess().isActive() || this.pathTimer.check5(1500L)) {
         this.goTo(loot.getBlockPos(), 1);
         this.pathTimer.invoke();
      }

      return true;
   }

   private boolean isStashFlow() {
      return this.state == AutoEnd.State.WARP_STASH
         || this.state == AutoEnd.State.FIND_CHEST
         || this.state == AutoEnd.State.PATH_CHEST
         || this.state == AutoEnd.State.OPEN_CHEST
         || this.state == AutoEnd.State.DEPOSIT;
   }

   private boolean isInvizFlow() {
      return this.state == AutoEnd.State.FIND_INVIZ
         || this.state == AutoEnd.State.PATH_INVIZ
         || this.state == AutoEnd.State.OPEN_INVIZ
         || this.state == AutoEnd.State.TAKE_INVIZ;
   }

   private boolean isTravelFlow() {
      return this.state == AutoEnd.State.WARP_FARM
         || this.state == AutoEnd.State.DRINK_INVIZ
         || this.state == AutoEnd.State.WARP_END;
   }

   private void beginCycleFromStash() {
      this.cancelBaritone();
      this.releaseUseKey();
      this.restoreSelectedSlot();
      this.farmZone = false;
      this.target = null;
      this.breaking = null;
      this.stashChest = null;
      this.invizChest = null;
      this.drinkingInviz = false;
      this.anarchyFullFlag = false;
      this.state = AutoEnd.State.WARP_STASH;
      this.warpTimer.setTimestamp22(0L);
      this.actionTimer.invoke();
      this.log("Еду на стеш /an" + this.resolveStashAnarchy());
   }

   private void beginStashDepositFlow() {
      this.cancelBaritone();
      this.releaseUseKey();
      this.restoreSelectedSlot();
      this.farmZone = false;
      this.target = null;
      this.breaking = null;
      this.stashChest = null;
      this.state = AutoEnd.State.WARP_STASH;
      this.warpTimer.setTimestamp22(0L);
      this.actionTimer.invoke();
   }

   private void beginTakeInvizThenFarm() {
      this.stashChest = null;
      this.invizChest = null;
      this.drinkingInviz = false;
      if (this.findInvizPotionSlot() >= 0) {
         this.log("Инвиз уже в инвентаре — на /an" + this.resolveFarmAnarchy());
         this.state = AutoEnd.State.WARP_FARM;
         this.anarchyFullFlag = false;
         this.warpTimer.setTimestamp22(0L);
         this.actionTimer.invoke();
         return;
      }

      this.state = AutoEnd.State.FIND_INVIZ;
      this.actionTimer.invoke();
      this.log("Беру инвиз (пока не пью) из «" + this.tablivkaInviza.getValue() + "»");
   }

   private void tickStashFlow() {
      switch (this.state) {
         case WARP_STASH -> this.tickWarpStash();
         case FIND_CHEST, PATH_CHEST -> this.tickFindAndPathChest(this.tablivkaResursov.getValue(), true);
         case OPEN_CHEST -> this.tickOpenChest(true);
         case DEPOSIT -> this.tickDeposit();
         default -> {
         }
      }
   }

   private void tickInvizFlow() {
      switch (this.state) {
         case FIND_INVIZ, PATH_INVIZ -> this.tickFindAndPathChest(this.tablivkaInviza.getValue(), false);
         case OPEN_INVIZ -> this.tickOpenChest(false);
         case TAKE_INVIZ -> this.tickTakeInviz();
         default -> {
         }
      }
   }

   private void tickTravelFlow() {
      switch (this.state) {
         case WARP_FARM -> this.tickWarpFarm();
         case DRINK_INVIZ -> this.tickDrinkInviz();
         case WARP_END -> this.tickWarpEnd();
         default -> {
         }
      }
   }

   private void tickWarpStash() {
      if (this.isInCombat()) {
         this.state = AutoEnd.State.WAIT_PVP;
         return;
      }

      String stash = this.resolveStashAnarchy();
      if (stash.isEmpty()) {
         this.log("§cУкажи анархию склада в настройках или §f.end stash <номер>");
         return;
      }

      ServerStatsParser.INSTANCE.invoke2();
      if (stash.equals(ServerStatsParser.INSTANCE.getNA2())) {
         if (this.hasLootToDeposit() || this.farmedThisTrip) {
            this.log("На стеше /an" + stash + " — складываю в «" + this.tablivkaResursov.getValue() + "»");
            this.stashChest = null;
            this.state = AutoEnd.State.FIND_CHEST;
            this.actionTimer.invoke();
         } else {
            this.beginTakeInvizThenFarm();
         }

         return;
      }

      if (this.warpTimer.check5(WARP_RETRY_MS)) {
         CLIENT.player.networkHandler.sendChatCommand("an" + stash);
         this.log("Варп на стеш: /an" + stash);
         this.warpTimer.invoke();
      }
   }

   private void tickWarpFarm() {
      if (this.isInCombat()) {
         this.state = AutoEnd.State.WAIT_PVP;
         return;
      }

      String farm = this.resolveFarmAnarchy();
      if (farm.isEmpty()) {
         this.log("§cУкажи анархию фарма в настройках или §f.end farm <номер>");
         return;
      }

      ServerStatsParser.INSTANCE.invoke2();
      if (farm.equals(ServerStatsParser.INSTANCE.getNA2()) && !this.anarchyFullFlag) {
         this.log("На /an" + farm + " — пью инвиз");
         this.state = AutoEnd.State.DRINK_INVIZ;
         this.actionTimer.invoke();
         return;
      }

      if (this.anarchyFullFlag || this.warpTimer.check5(WARP_RETRY_MS)) {
         boolean retryFull = this.anarchyFullFlag;
         this.anarchyFullFlag = false;
         CLIENT.player.networkHandler.sendChatCommand("an" + farm);
         this.log("Варп на фарм: /an" + farm + (retryFull ? " (повтор, была заполнена)" : ""));
         this.warpTimer.invoke();
      }
   }

   private void tickWarpEnd() {
      if (this.isInCombat()) {
         this.state = AutoEnd.State.WAIT_PVP;
         return;
      }

      if (this.farmZone || this.isInEndDimension()) {
         this.farmZone = true;
         this.farmedThisTrip = false;
         this.wasInPvp = false;
         this.pvpClearSince = 0L;
         this.resumeFarming();
         this.log("На варпе энда — фармлю");
         return;
      }

      if (this.warpTimer.check5(WARP_RETRY_MS)) {
         String warp = this.resolveEndWarp();
         CLIENT.player.networkHandler.sendChatCommand("warp " + warp);
         this.log("Варп: /warp " + warp);
         this.warpTimer.invoke();
         this.actionTimer.invoke();
      }

      // After sending warp, wait a bit then treat as arrived.
      if (this.actionTimer.check5(WARP_END_WAIT_MS) && this.warpTimer.check5(500L)) {
         this.farmZone = true;
         this.farmedThisTrip = false;
         this.resumeFarming();
         this.log("Считал вход на /warp " + this.resolveEndWarp() + " успешным — фармлю");
      }
   }

   private void tickFindAndPathChest(String signName, boolean depositMode) {
      if (CLIENT.currentScreen instanceof GenericContainerScreen) {
         this.state = depositMode ? AutoEnd.State.DEPOSIT : AutoEnd.State.TAKE_INVIZ;
         this.depositTimer.invoke();
         return;
      }

      BlockPos chest = depositMode ? this.stashChest : this.invizChest;
      if (chest == null || !this.isStorage(chest) || !this.chestMatchesSign(chest, signName)) {
         chest = this.findChestBySign(signName);
         if (chest == null) {
            if (this.actionTimer.check5(10000L)) {
               this.log("§cСундук «" + signName + "» не найден");
               this.actionTimer.invoke();
               if (!depositMode) {
                  if (this.findInvizPotionSlot() >= 0) {
                     this.state = AutoEnd.State.WARP_FARM;
                     this.anarchyFullFlag = false;
                     this.warpTimer.setTimestamp22(0L);
                  }
               } else {
                  this.finishDepositAndRestart();
               }
            }

            return;
         }

         if (depositMode) {
            this.stashChest = chest;
         } else {
            this.invizChest = chest;
         }

         this.log("Сундук «" + signName + "»: " + this.formatPos(chest));
         this.state = depositMode ? AutoEnd.State.PATH_CHEST : AutoEnd.State.PATH_INVIZ;
         this.pathTimer.invoke();
      }

      chest = depositMode ? this.stashChest : this.invizChest;
      double dist = CLIENT.player.getPos().distanceTo(Vec3d.ofCenter(chest));
      if (dist <= CHEST_REACH && this.canSeeBlock(chest)) {
         this.cancelPath();
         this.state = depositMode ? AutoEnd.State.OPEN_CHEST : AutoEnd.State.OPEN_INVIZ;
         this.actionTimer.invoke();
         return;
      }

      if (!this.baritone.getCustomGoalProcess().isActive() || this.pathTimer.check5(2500L)) {
         this.goTo(chest, 1);
         this.pathTimer.invoke();
      }

      if (this.actionTimer.check5(20000L)) {
         this.log("§cНе смог подойти к сундуку «" + signName + "»");
         if (depositMode) {
            this.finishDepositAndRestart();
         } else {
            this.actionTimer.invoke();
         }
      }
   }

   private void tickOpenChest(boolean depositMode) {
      if (CLIENT.currentScreen instanceof GenericContainerScreen) {
         this.state = depositMode ? AutoEnd.State.DEPOSIT : AutoEnd.State.TAKE_INVIZ;
         this.depositTimer.invoke();
         return;
      }

      BlockPos chest = depositMode ? this.stashChest : this.invizChest;
      if (chest == null) {
         this.state = depositMode ? AutoEnd.State.FIND_CHEST : AutoEnd.State.FIND_INVIZ;
         return;
      }

      Rotation rotation = this.rotationTo(Vec3d.ofCenter(chest));
      RotationController.invoke3(rotation, 35.0F, 35.0F, 35.0F, 35.0F, 2, 20, false);
      if (new Rotation(CLIENT.player).measure(rotation) > 8.0F) {
         return;
      }

      if (this.actionTimer.check5(200L)) {
         BlockHitResult hit = this.raycastTo(chest);
         CLIENT.interactionManager.interactBlock(CLIENT.player, Hand.MAIN_HAND, hit);
         CLIENT.player.swingHand(Hand.MAIN_HAND);
         this.state = depositMode ? AutoEnd.State.DEPOSIT : AutoEnd.State.TAKE_INVIZ;
         this.depositTimer.invoke();
         this.actionTimer.invoke();
      }

      if (this.pathTimer.check5(8000L) && !(CLIENT.currentScreen instanceof GenericContainerScreen)) {
         this.log("§cСундук не открылся, ищу снова");
         if (depositMode) {
            this.stashChest = null;
            this.state = AutoEnd.State.FIND_CHEST;
         } else {
            this.invizChest = null;
            this.state = AutoEnd.State.FIND_INVIZ;
         }

         this.actionTimer.invoke();
      }
   }

   private void tickDeposit() {
      if (!(CLIENT.currentScreen instanceof GenericContainerScreen screen)) {
         if (!this.hasLootToDeposit()) {
            this.finishDepositAndRestart();
         } else if (this.actionTimer.check5(1500L)) {
            this.state = AutoEnd.State.OPEN_CHEST;
            this.actionTimer.invoke();
         }

         return;
      }

      if (!this.depositTimer.check5(DEPOSIT_CLICK_MS)) {
         return;
      }

      GenericContainerScreenHandler handler = (GenericContainerScreenHandler)screen.getScreenHandler();
      int playerStart = handler.slots.size() - 36;
      boolean moved = false;

      for (int i = playerStart; i < handler.slots.size(); i++) {
         Slot slot = handler.slots.get(i);
         if (!slot.hasStack()) {
            continue;
         }

         ItemStack stack = slot.getStack();
         if (!this.shouldDeposit(stack)) {
            continue;
         }

         CLIENT.interactionManager.clickSlot(handler.syncId, i, 0, SlotActionType.QUICK_MOVE, CLIENT.player);
         this.depositTimer.invoke();
         moved = true;
         break;
      }

      if (!moved) {
         CLIENT.player.closeHandledScreen();
         this.log("Склад «" + this.tablivkaResursov.getValue() + "» завершён");
         this.finishDepositAndRestart();
      }
   }

   private void tickTakeInviz() {
      if (!(CLIENT.currentScreen instanceof GenericContainerScreen screen)) {
         if (this.findInvizPotionSlot() >= 0) {
            this.log("Инвиз взят — еду на /an" + this.resolveFarmAnarchy() + " (пока не пью)");
            this.state = AutoEnd.State.WARP_FARM;
            this.anarchyFullFlag = false;
            this.warpTimer.setTimestamp22(0L);
            this.actionTimer.invoke();
         } else if (this.actionTimer.check5(1500L)) {
            this.state = AutoEnd.State.OPEN_INVIZ;
            this.actionTimer.invoke();
         }

         return;
      }

      if (!this.depositTimer.check5(DEPOSIT_CLICK_MS)) {
         return;
      }

      if (this.findInvizPotionSlot() >= 0) {
         CLIENT.player.closeHandledScreen();
         this.log("Инвиз взят — еду на /an" + this.resolveFarmAnarchy() + " (пока не пью)");
         this.state = AutoEnd.State.WARP_FARM;
         this.anarchyFullFlag = false;
         this.warpTimer.setTimestamp22(0L);
         this.actionTimer.invoke();
         return;
      }

      GenericContainerScreenHandler handler = (GenericContainerScreenHandler)screen.getScreenHandler();
      int chestSlots = handler.slots.size() - 36;
      boolean took = false;

      for (int i = 0; i < chestSlots; i++) {
         Slot slot = handler.slots.get(i);
         if (!slot.hasStack() || !this.isInvisibilityPotion(slot.getStack())) {
            continue;
         }

         CLIENT.interactionManager.clickSlot(handler.syncId, i, 0, SlotActionType.QUICK_MOVE, CLIENT.player);
         this.depositTimer.invoke();
         took = true;
         break;
      }

      if (!took) {
         CLIENT.player.closeHandledScreen();
         if (this.findInvizPotionSlot() >= 0) {
            this.state = AutoEnd.State.WARP_FARM;
            this.anarchyFullFlag = false;
            this.warpTimer.setTimestamp22(0L);
         } else {
            this.log("§cВ сундуке «" + this.tablivkaInviza.getValue() + "» нет зелий — всё равно на /an" + this.resolveFarmAnarchy());
            this.state = AutoEnd.State.WARP_FARM;
            this.anarchyFullFlag = false;
            this.warpTimer.setTimestamp22(0L);
         }

         this.actionTimer.invoke();
      }
   }

   private void tickDrinkInviz() {
      if (this.hasInvisibility()) {
         this.releaseUseKey();
         this.restoreSelectedSlot();
         this.drinkingInviz = false;
         this.log("Инвиз активен — /warp " + this.resolveEndWarp());
         this.state = AutoEnd.State.WARP_END;
         this.warpTimer.setTimestamp22(0L);
         this.actionTimer.invoke();
         return;
      }

      if (this.drinkingInviz) {
         if (CLIENT.player.isUsingItem() && !this.drinkTimer.check5(DRINK_MS)) {
            CLIENT.options.useKey.setPressed(true);
            return;
         }

         this.releaseUseKey();
         this.restoreSelectedSlot();
         this.drinkingInviz = false;
         if (this.hasInvisibility()) {
            this.state = AutoEnd.State.WARP_END;
            this.warpTimer.setTimestamp22(0L);
            this.log("Выпил инвиз — /warp " + this.resolveEndWarp());
         } else if (this.findInvizPotionSlot() >= 0) {
            this.log("Инвиз не наложился — пробую ещё");
         } else {
            this.log("§cЗелья нет — иду на варп без инвиза");
            this.state = AutoEnd.State.WARP_END;
            this.warpTimer.setTimestamp22(0L);
         }

         this.actionTimer.invoke();
         return;
      }

      int slot = this.findInvizPotionSlot();
      if (slot < 0) {
         this.log("§cНечего пить — /warp " + this.resolveEndWarp());
         this.state = AutoEnd.State.WARP_END;
         this.warpTimer.setTimestamp22(0L);
         this.actionTimer.invoke();
         return;
      }

      int hotbar = this.equipInventorySlot(slot);
      if (hotbar < 0) {
         return;
      }

      CLIENT.options.useKey.setPressed(true);
      this.drinkingInviz = true;
      this.drinkTimer.invoke();
      this.log("Пью зелье невидимости");
   }

   private void finishDepositAndRestart() {
      this.stashChest = null;
      this.farmedThisTrip = false;
      this.farmZone = false;
      if (CLIENT.player != null) {
         CLIENT.player.closeHandledScreen();
      }

      this.beginTakeInvizThenFarm();
   }

   private void resumeFarming() {
      this.state = AutoEnd.State.SEARCH;
      this.target = null;
      this.breaking = null;
      this.stashChest = null;
      this.invizChest = null;
      this.targetBrushable = false;
      this.farmZone = true;
      this.applyBaritoneSettings();
      this.log("Продолжаю фарм");
      if (this.rezhim.is("Baritone")) {
         this.startMineProcess();
      }
   }

   private void tickBaritoneMine() {
      if (this.chistitArheologiyu.isEnabled() && this.hasBrush()) {
         if (this.loggedNoBrush) {
            this.loggedNoBrush = false;
            this.unblacklistBrushables();
            this.log("Кисть найдена — возвращаюсь к археологии");
         }

         BlockPos brushable = this.target != null && this.targetBrushable && this.isBrushable(CLIENT.world.getBlockState(this.target))
            ? this.target
            : this.findNearestBrushable(Math.round(this.radiusPoiska.getValue()));
         if (brushable != null) {
            this.cancelBaritoneMineOnly();
            this.target = brushable;
            this.targetBrushable = true;
            this.state = AutoEnd.State.BREAK;
            this.breakBrushable(brushable);
            return;
         }
      } else if (this.chistitArheologiyu.isEnabled() && !this.hasBrush() && !this.loggedNoBrush) {
         this.loggedNoBrush = true;
         this.log("§eНет кисти — археологию пропущу, пока не появится");
      }

      this.state = AutoEnd.State.MINE;
      if (!this.baritone.getMineProcess().isActive() && this.mineTimer.check5(MINE_RESTART_MS)) {
         this.startMineProcess();
         this.mineTimer.invoke();
      }
   }

   private void tickScanMode() {
      if (this.chistitArheologiyu.isEnabled() && this.hasBrush() && this.loggedNoBrush) {
         this.loggedNoBrush = false;
         this.unblacklistBrushables();
         this.log("Кисть найдена — возвращаюсь к археологии");
      }

      if (this.target != null) {
         BlockState state = CLIENT.world.getBlockState(this.target);
         if (!this.isFarmTarget(state)) {
            this.releaseUseKey();
            this.blacklistTemporary(this.target);
            this.restoreSelectedSlot();
            this.target = null;
            this.breaking = null;
            this.targetBrushable = false;
            this.state = AutoEnd.State.SEARCH;
            this.cancelPath();
         } else if (this.targetBrushable && !this.hasBrush()) {
            this.releaseUseKey();
            this.restoreSelectedSlot();
            this.target = null;
            this.breaking = null;
            this.targetBrushable = false;
            this.state = AutoEnd.State.SEARCH;
            this.cancelPath();
         }
      }

      if (this.target == null || this.rescanTimer.check5(RESCAN_MS)) {
         TargetSelection next = this.findNearestTarget();
         this.rescanTimer.invoke();
         if (next == null) {
            this.state = AutoEnd.State.SEARCH;
            this.cancelPath();
            this.restoreSelectedSlot();
            return;
         }

         if (this.target == null || !this.target.equals(next.pos) || this.targetBrushable != next.brushable) {
            this.target = next.pos;
            this.targetBrushable = next.brushable;
            this.breaking = null;
            this.log((next.brushable ? "Археология: " : "Кувшин: ") + this.formatPos(this.target));
            if (next.brushable) {
               this.goToStandOn(this.target);
            } else {
               this.goTo(this.target, 2);
            }

            this.pathTimer.invoke();
         }
      }

      if (this.target == null) {
         return;
      }

      if (this.targetBrushable) {
         this.state = AutoEnd.State.BREAK;
         this.breakBrushable(this.target);
         return;
      }

      double reach = this.distantsiyaLoma.getValue();
      double dist = CLIENT.player.getEyePos().distanceTo(Vec3d.ofCenter(this.target));
      if (dist > reach) {
         this.state = AutoEnd.State.PATH;
         if (!this.baritone.getCustomGoalProcess().isActive() || this.pathTimer.check5(3000L)) {
            this.goTo(this.target, 2);
            this.pathTimer.invoke();
         }

         return;
      }

      this.state = AutoEnd.State.BREAK;
      this.cancelPath();
      this.breakTarget(this.target);
   }

   private void startMineProcess() {
      List<Block> jars = this.resolveMineBlocks();
      if (jars.isEmpty()) {
         this.log("§cНе найдены блоки для фарма");
         return;
      }

      this.cancelPath();
      try {
         this.baritone.getMineProcess().mine(0, jars.toArray(Block[]::new));
         this.log("Baritone mine: " + jars.size() + " тип(ов)");
      } catch (Throwable exception) {
         String[] names = jars.stream().map(block -> Registries.BLOCK.getId(block).toString()).toArray(String[]::new);
         this.baritone.getMineProcess().mineByName(0, names);
         this.log("Baritone mineByName: " + String.join(", ", names));
      }
   }

   private void breakTarget(BlockPos pos) {
      this.restoreSelectedSlot();
      BlockHitResult hit = this.raycastTo(pos);
      if (hit == null) {
         this.goTo(pos, 1);
         return;
      }

      Vec3d look = hit.getPos();
      Rotation rotation = this.rotationTo(look);
      float delta = new Rotation(CLIENT.player).measure(rotation);
      RotationController.invoke3(rotation, 40.0F, 40.0F, 40.0F, 40.0F, 2, 20, false);
      if (delta > 8.0F) {
         return;
      }

      Direction side = hit.getSide();
      if (this.breaking == null || !this.breaking.equals(pos)) {
         CLIENT.interactionManager.attackBlock(pos, side);
         CLIENT.player.swingHand(Hand.MAIN_HAND);
         this.breaking = pos.toImmutable();
         this.breakTimer.invoke();
         this.farmedThisTrip = true;
         return;
      }

      if (this.breakTimer.check5(BREAK_TIMEOUT_MS)) {
         this.blacklistTemporary(pos);
         this.target = null;
         this.breaking = null;
         this.log("Пропуск " + this.formatPos(pos) + " (таймаут)");
         return;
      }

      CLIENT.interactionManager.updateBlockBreakingProgress(pos, side);
      CLIENT.player.swingHand(Hand.MAIN_HAND);
   }

   private void breakBrushable(BlockPos pos) {
      if (!this.equipBrush()) {
         if (!this.loggedNoBrush) {
            this.loggedNoBrush = true;
            this.log("§eНет кисти — жду, песок в блеклист не кидаю");
         }

         this.releaseUseKey();
         this.target = null;
         this.breaking = null;
         this.targetBrushable = false;
         return;
      }

      this.loggedNoBrush = false;

      if (!this.isStandingOn(pos)) {
         this.releaseUseKey();
         this.state = AutoEnd.State.PATH;
         if (!this.baritone.getCustomGoalProcess().isActive() || this.pathTimer.check5(1500L)) {
            this.goToStandOn(pos);
            this.pathTimer.invoke();
         }

         if (this.breaking == null || !this.breaking.equals(pos)) {
            this.breaking = pos.toImmutable();
            this.breakTimer.invoke();
            this.log("Иду на песок " + this.formatPos(pos));
         } else if (this.breakTimer.check5(BRUSH_TIMEOUT_MS)) {
            this.blacklistTemporary(pos);
            this.restoreSelectedSlot();
            this.target = null;
            this.breaking = null;
            this.targetBrushable = false;
            this.log("Пропуск археологии " + this.formatPos(pos) + " (не смог встать)");
         }

         return;
      }

      this.cancelPath();
      this.state = AutoEnd.State.BREAK;

      // Stand on sand and look straight down at it.
      Rotation lookDown = new Rotation(CLIENT.player.getYaw(), 90.0F);
      RotationController.invoke3(lookDown, 80.0F, 80.0F, 80.0F, 80.0F, 1, 8, false);
      CLIENT.player.setPitch(90.0F);
      if (new Rotation(CLIENT.player).measure(lookDown) > 18.0F && Math.abs(CLIENT.player.getPitch() - 90.0F) > 18.0F) {
         this.releaseUseKey();
         return;
      }

      Hand hand = CLIENT.player.getMainHandStack().isOf(Items.BRUSH) ? Hand.MAIN_HAND : Hand.OFF_HAND;
      BlockHitResult hit = this.raycastDownTo(pos);
      if (hit == null || !hit.getBlockPos().equals(pos)) {
         // Force a downward hit on the sand under our feet.
         hit = new BlockHitResult(
            new Vec3d(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5),
            Direction.UP,
            pos,
            false
         );
      }

      if (this.breaking == null || !this.breaking.equals(pos)) {
         this.breaking = pos.toImmutable();
         this.breakTimer.invoke();
      }

      if (this.breakTimer.check5(BRUSH_TIMEOUT_MS)) {
         this.releaseUseKey();
         this.blacklistTemporary(pos);
         this.restoreSelectedSlot();
         this.target = null;
         this.breaking = null;
         this.targetBrushable = false;
         this.log("Пропуск археологии " + this.formatPos(pos) + " (таймаут)");
         return;
      }

      if (CLIENT.options != null) {
         CLIENT.options.useKey.setPressed(true);
      }

      boolean alreadyBrushing = CLIENT.player.isUsingItem() && CLIENT.player.getActiveItem().isOf(Items.BRUSH);
      if (!alreadyBrushing) {
         CLIENT.interactionManager.interactBlock(CLIENT.player, hand, hit);
         CLIENT.player.swingHand(hand);
         this.farmedThisTrip = true;
      }
   }

   private boolean isStandingOn(BlockPos pos) {
      if (CLIENT.player == null || pos == null) {
         return false;
      }

      double dx = CLIENT.player.getX() - (pos.getX() + 0.5);
      double dz = CLIENT.player.getZ() - (pos.getZ() + 0.5);
      double horizontal = dx * dx + dz * dz;
      double y = CLIENT.player.getY();
      boolean above = y >= pos.getY() - 0.05 && y <= pos.getY() + 1.35;
      BlockPos feet = CLIENT.player.getBlockPos();
      return horizontal <= 0.55 && above && (feet.equals(pos) || feet.down().equals(pos) || feet.equals(pos.up()));
   }

   private BlockHitResult raycastDownTo(BlockPos pos) {
      Vec3d eye = CLIENT.player.getEyePos();
      Vec3d look = eye.add(0.0, -4.0, 0.0);
      BlockHitResult hit = CLIENT.world.raycast(new RaycastContext(eye, look, ShapeType.OUTLINE, FluidHandling.NONE, CLIENT.player));
      if (hit != null && hit.getBlockPos().equals(pos)) {
         return hit;
      }

      Vec3d top = new Vec3d(pos.getX() + 0.5, pos.getY() + 0.98, pos.getZ() + 0.5);
      return new BlockHitResult(top, Direction.UP, pos, false);
   }

   private void releaseUseKey() {
      if (CLIENT.options != null) {
         CLIENT.options.useKey.setPressed(false);
      }
   }

   private boolean hasBrush() {
      if (CLIENT.player == null) {
         return false;
      }

      if (CLIENT.player.getOffHandStack().isOf(Items.BRUSH)) {
         return true;
      }

      for (int i = 0; i < 36; i++) {
         if (CLIENT.player.getInventory().getStack(i).isOf(Items.BRUSH)) {
            return true;
         }
      }

      return false;
   }

   private boolean equipBrush() {
      if (CLIENT.player.getMainHandStack().isOf(Items.BRUSH) || CLIENT.player.getOffHandStack().isOf(Items.BRUSH)) {
         return true;
      }

      for (int i = 0; i < 9; i++) {
         if (CLIENT.player.getInventory().getStack(i).isOf(Items.BRUSH)) {
            if (this.previousSelectedSlot < 0) {
               this.previousSelectedSlot = CLIENT.player.getInventory().getSelectedSlot();
            }

            CLIENT.player.getInventory().setSelectedSlot(i);
            return true;
         }
      }

      for (int i = 9; i < 36; i++) {
         if (!CLIENT.player.getInventory().getStack(i).isOf(Items.BRUSH)) {
            continue;
         }

         if (this.previousSelectedSlot < 0) {
            this.previousSelectedSlot = CLIENT.player.getInventory().getSelectedSlot();
         }

         int hotbar = CLIENT.player.getInventory().getSelectedSlot();
         CLIENT.interactionManager.clickSlot(
            CLIENT.player.playerScreenHandler.syncId, i, hotbar, SlotActionType.SWAP, CLIENT.player
         );
         CLIENT.player.getInventory().setSelectedSlot(hotbar);
         return CLIENT.player.getMainHandStack().isOf(Items.BRUSH)
            || CLIENT.player.getInventory().getStack(hotbar).isOf(Items.BRUSH);
      }

      return false;
   }

   private void restoreSelectedSlot() {
      if (this.previousSelectedSlot >= 0 && CLIENT.player != null) {
         CLIENT.player.getInventory().setSelectedSlot(this.previousSelectedSlot);
         this.previousSelectedSlot = -1;
      }
   }

   private TargetSelection findNearestTarget() {
      int radius = Math.round(this.radiusPoiska.getValue());
      BlockPos origin = CLIENT.player.getBlockPos();
      BlockPos best = null;
      boolean bestBrushable = false;
      double bestDist = Double.MAX_VALUE;

      for (int x = -radius; x <= radius; x++) {
         for (int y = -radius; y <= radius; y++) {
            for (int z = -radius; z <= radius; z++) {
               BlockPos pos = origin.add(x, y, z);
               if (this.blacklist.contains(pos.toImmutable())) {
                  continue;
               }

               BlockState state = CLIENT.world.getBlockState(pos);
               boolean brushable = this.isBrushable(state);
               boolean jar = this.isJar(state);
               if (brushable && (!this.chistitArheologiyu.isEnabled() || !this.hasBrush())) {
                  continue;
               }

               if (!jar && !brushable) {
                  continue;
               }

               double dist = CLIENT.player.squaredDistanceTo(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
               if (brushable) {
                  dist -= 4.0;
               }

               if (dist < bestDist) {
                  bestDist = dist;
                  best = pos.toImmutable();
                  bestBrushable = brushable;
               }
            }
         }
      }

      return best == null ? null : new TargetSelection(best, bestBrushable);
   }

   private BlockPos findNearestBrushable(int radius) {
      BlockPos origin = CLIENT.player.getBlockPos();
      BlockPos best = null;
      double bestDist = Double.MAX_VALUE;

      for (int x = -radius; x <= radius; x++) {
         for (int y = -radius; y <= radius; y++) {
            for (int z = -radius; z <= radius; z++) {
               BlockPos pos = origin.add(x, y, z);
               if (this.blacklist.contains(pos.toImmutable())) {
                  continue;
               }

               if (!this.isBrushable(CLIENT.world.getBlockState(pos))) {
                  continue;
               }

               double dist = CLIENT.player.squaredDistanceTo(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
               if (dist < bestDist) {
                  bestDist = dist;
                  best = pos.toImmutable();
               }
            }
         }
      }

      return best;
   }

   private BlockPos findChestBySign(String signName) {
      if (CLIENT.world == null || CLIENT.player == null) {
         return null;
      }

      ChunkPos chunkPos = new ChunkPos(CLIENT.player.getBlockPos());
      BlockPos best = null;
      double bestDist = Double.MAX_VALUE;
      int chunkRadius = 8;

      for (int cx = -chunkRadius; cx <= chunkRadius; cx++) {
         for (int cz = -chunkRadius; cz <= chunkRadius; cz++) {
            WorldChunk chunk = CLIENT.world.getChunk(chunkPos.x + cx, chunkPos.z + cz);
            if (chunk == null) {
               continue;
            }

            for (BlockPos pos : chunk.getBlockEntities().keySet()) {
               if (!this.isStorage(pos) || !this.chestMatchesSign(pos, signName)) {
                  continue;
               }

               double dist = CLIENT.player.getPos().distanceTo(Vec3d.ofCenter(pos));
               if (dist < bestDist) {
                  bestDist = dist;
                  best = pos.toImmutable();
               }
            }
         }
      }

      return best;
   }

   private boolean chestMatchesSign(BlockPos chestPos, String signName) {
      String sign = this.readNearbySign(chestPos).toLowerCase(Locale.ROOT);
      if (sign.isEmpty()) {
         return false;
      }

      String wanted = signName == null ? "" : signName.trim().toLowerCase(Locale.ROOT);
      if (wanted.isEmpty()) {
         return false;
      }

      if (sign.contains(wanted)) {
         return true;
      }

      if (wanted.contains("ресурс")) {
         return sign.contains("ресурс") || sign.contains("ресы");
      }

      if (wanted.contains("инвиз")) {
         return sign.contains("инвиз") || sign.contains("невид") || sign.contains("invis");
      }

      return false;
   }

   private String readNearbySign(BlockPos blockPos) {
      SignBlockEntity nearest = null;
      double best = Double.MAX_VALUE;

      for (BlockPos pos : BlockPos.iterate(blockPos.add(-1, -1, -1), blockPos.add(1, 1, 1))) {
         if (CLIENT.world.getBlockEntity(pos) instanceof SignBlockEntity sign) {
            double dist = pos.getSquaredDistance(blockPos);
            if (dist < best) {
               best = dist;
               nearest = sign;
            }
         }
      }

      if (nearest == null) {
         return "";
      }

      StringBuilder builder = new StringBuilder();
      for (Text text : nearest.getFrontText().getMessages(false)) {
         builder.append(text.getString()).append(' ');
      }

      for (Text text : nearest.getBackText().getMessages(false)) {
         builder.append(text.getString()).append(' ');
      }

      return builder.toString().replaceAll("§.", "").trim();
   }

   private boolean isStorage(BlockPos pos) {
      BlockEntity entity = CLIENT.world.getBlockEntity(pos);
      return entity instanceof ChestBlockEntity || entity instanceof BarrelBlockEntity || entity instanceof ShulkerBoxBlockEntity;
   }

   private boolean canSeeBlock(BlockPos pos) {
      Vec3d eye = CLIENT.player.getEyePos();
      Vec3d center = Vec3d.ofCenter(pos);
      BlockHitResult hit = CLIENT.world.raycast(new RaycastContext(eye, center, ShapeType.OUTLINE, FluidHandling.NONE, CLIENT.player));
      return hit != null && hit.getBlockPos().equals(pos) || eye.distanceTo(center) <= CHEST_REACH;
   }

   private boolean isFarmTarget(BlockState state) {
      return this.isJar(state) || this.isBrushable(state) && this.chistitArheologiyu.isEnabled();
   }

   private boolean isJar(BlockState state) {
      if (state == null || state.isAir()) {
         return false;
      }

      Block block = state.getBlock();
      if (block == Blocks.DECORATED_POT) {
         return true;
      }

      for (Block configured : this.resolveMineBlocks()) {
         if (configured == block && !this.isBrushableBlock(configured)) {
            return true;
         }
      }

      return false;
   }

   private boolean isBrushable(BlockState state) {
      return state != null && this.isBrushableBlock(state.getBlock());
   }

   private boolean isBrushableBlock(Block block) {
      return block == Blocks.SUSPICIOUS_SAND || block == Blocks.SUSPICIOUS_GRAVEL;
   }

   private List<Block> resolveMineBlocks() {
      ArrayList<Block> blocks = new ArrayList<>();
      blocks.add(Blocks.DECORATED_POT);
      String raw = this.dopolnitelnyeBloki.getValue();
      if (raw != null && !raw.isBlank()) {
         for (String part : raw.split("[,;\\s]+")) {
            if (part.isBlank()) {
               continue;
            }

            String token = part.trim().toLowerCase(Locale.ROOT);
            Identifier id = Identifier.tryParse(token.contains(":") ? token : "minecraft:" + token);
            if (id == null) {
               continue;
            }

            Block block = Registries.BLOCK.get(id);
            if (block != null && block != Blocks.AIR && !blocks.contains(block) && !this.isBrushableBlock(block)) {
               blocks.add(block);
            }
         }
      }

      return blocks;
   }

   private ItemEntity findNearbyLoot() {
      ItemEntity best = null;
      double bestDist = LOOT_RANGE * LOOT_RANGE;

      for (ItemEntity item : CLIENT.world.getEntitiesByClass(ItemEntity.class, CLIENT.player.getBoundingBox().expand(LOOT_RANGE), ItemEntity::isAlive)) {
         ItemStack stack = item.getStack();
         if (stack.isEmpty() || !this.shouldPickup(stack)) {
            continue;
         }

         double dist = CLIENT.player.squaredDistanceTo(item);
         if (dist < bestDist) {
            bestDist = dist;
            best = item;
         }
      }

      return best;
   }

   private boolean hasLootToDeposit() {
      for (int i = 0; i < 36; i++) {
         if (this.shouldDeposit(CLIENT.player.getInventory().getStack(i))) {
            return true;
         }
      }

      return false;
   }

   private boolean shouldPickup(ItemStack stack) {
      return stack != null && !stack.isEmpty() && !this.isJunkLoot(stack);
   }

   private boolean shouldDeposit(ItemStack stack) {
      return stack != null
         && !stack.isEmpty()
         && !this.isProtectedGear(stack)
         && !this.isJunkLoot(stack)
         && !this.isInvisibilityPotion(stack);
   }

   private boolean isProtectedGear(ItemStack stack) {
      return stack.isOf(Items.TOTEM_OF_UNDYING)
         || stack.isOf(Items.BRUSH)
         || stack.isOf(Items.NETHERITE_PICKAXE)
         || stack.isOf(Items.DIAMOND_PICKAXE)
         || stack.isOf(Items.IRON_PICKAXE)
         || stack.isOf(Items.GOLDEN_PICKAXE)
         || stack.isOf(Items.STONE_PICKAXE)
         || stack.isOf(Items.WOODEN_PICKAXE)
         || stack.isOf(Items.NETHERITE_SWORD)
         || stack.isOf(Items.DIAMOND_SWORD)
         || stack.isOf(Items.NETHERITE_AXE)
         || stack.isOf(Items.DIAMOND_AXE)
         || stack.isOf(Items.NETHERITE_HELMET)
         || stack.isOf(Items.NETHERITE_CHESTPLATE)
         || stack.isOf(Items.NETHERITE_LEGGINGS)
         || stack.isOf(Items.NETHERITE_BOOTS)
         || stack.isOf(Items.DIAMOND_HELMET)
         || stack.isOf(Items.DIAMOND_CHESTPLATE)
         || stack.isOf(Items.DIAMOND_LEGGINGS)
         || stack.isOf(Items.DIAMOND_BOOTS)
         || stack.isOf(Items.ELYTRA)
         || stack.isOf(Items.SHIELD)
         || stack.isOf(Items.ENDER_PEARL)
         || stack.isOf(Items.CHORUS_FRUIT)
         || stack.isOf(Items.GOLDEN_APPLE)
         || stack.isOf(Items.ENCHANTED_GOLDEN_APPLE)
         || stack.isOf(Items.COOKED_BEEF)
         || stack.isOf(Items.COOKED_PORKCHOP)
         || stack.isOf(Items.GOLDEN_CARROT)
         || stack.isOf(Items.POTION)
         || stack.isOf(Items.SPLASH_POTION)
         || stack.isOf(Items.LINGERING_POTION);
   }

   private boolean isJunkLoot(ItemStack stack) {
      return stack.isOf(Items.COBBLESTONE)
         || stack.isOf(Items.COBBLED_DEEPSLATE)
         || stack.isOf(Items.STONE)
         || stack.isOf(Items.DEEPSLATE)
         || stack.isOf(Items.DIRT)
         || stack.isOf(Items.COARSE_DIRT)
         || stack.isOf(Items.GRAVEL)
         || stack.isOf(Items.SAND)
         || stack.isOf(Items.NETHERRACK)
         || stack.isOf(Items.BASALT)
         || stack.isOf(Items.SMOOTH_BASALT)
         || stack.isOf(Items.END_STONE)
         || stack.isOf(Items.TUFF)
         || stack.isOf(Items.ANDESITE)
         || stack.isOf(Items.DIORITE)
         || stack.isOf(Items.GRANITE)
         || stack.isOf(Items.ROTTEN_FLESH)
         || stack.isOf(Items.BONE)
         || stack.isOf(Items.STRING)
         || stack.isOf(Items.STICK)
         || stack.isOf(Items.WHEAT_SEEDS)
         || stack.isOf(Items.KELP)
         || stack.isOf(Items.FEATHER)
         || stack.isOf(Items.LEATHER);
   }

   private boolean hasInvisibility() {
      StatusEffectInstance effect = CLIENT.player.getStatusEffect(StatusEffects.INVISIBILITY);
      return effect != null && effect.getDuration() > 40;
   }

   private boolean isInvisibilityPotion(ItemStack stack) {
      if (stack == null || stack.isEmpty() || !stack.isOf(Items.POTION)) {
         return false;
      }

      PotionContentsComponent contents = stack.get(DataComponentTypes.POTION_CONTENTS);
      if (contents == null) {
         return false;
      }

      for (StatusEffectInstance effect : contents.getEffects()) {
         if (effect.getEffectType().equals(StatusEffects.INVISIBILITY)) {
            return true;
         }
      }

      return false;
   }

   private int findInvizPotionSlot() {
      for (int i = 0; i < 36; i++) {
         if (this.isInvisibilityPotion(CLIENT.player.getInventory().getStack(i))) {
            return i;
         }
      }

      return -1;
   }

   private int equipInventorySlot(int slot) {
      if (slot >= 0 && slot < 9) {
         if (this.previousSelectedSlot < 0) {
            this.previousSelectedSlot = CLIENT.player.getInventory().getSelectedSlot();
         }

         CLIENT.player.getInventory().setSelectedSlot(slot);
         if (CLIENT.interactionManager instanceof ClientPlayerInteractionManagerAccessor accessor) {
            accessor.invokeSyncSelectedSlot();
         }

         return slot;
      }

      if (slot < 9 || slot >= 36) {
         return -1;
      }

      if (this.previousSelectedSlot < 0) {
         this.previousSelectedSlot = CLIENT.player.getInventory().getSelectedSlot();
      }

      int hotbar = CLIENT.player.getInventory().getSelectedSlot();
      for (int i = 0; i < 9; i++) {
         if (CLIENT.player.getInventory().getStack(i).isEmpty()) {
            hotbar = i;
            break;
         }
      }

      CLIENT.interactionManager.clickSlot(CLIENT.player.playerScreenHandler.syncId, slot, hotbar, SlotActionType.SWAP, CLIENT.player);
      CLIENT.player.getInventory().setSelectedSlot(hotbar);
      if (CLIENT.interactionManager instanceof ClientPlayerInteractionManagerAccessor accessor) {
         accessor.invokeSyncSelectedSlot();
      }

      return hotbar;
   }

   private BlockHitResult raycastTo(BlockPos pos) {
      Vec3d eye = CLIENT.player.getEyePos();
      Vec3d center = Vec3d.ofCenter(pos);
      BlockHitResult hit = CLIENT.world.raycast(new RaycastContext(eye, center, ShapeType.OUTLINE, FluidHandling.NONE, CLIENT.player));
      if (hit != null && hit.getBlockPos().equals(pos)) {
         return hit;
      }

      for (Direction direction : Direction.values()) {
         Vec3d point = center.add(direction.getOffsetX() * 0.51, direction.getOffsetY() * 0.51, direction.getOffsetZ() * 0.51);
         BlockHitResult sideHit = CLIENT.world.raycast(new RaycastContext(eye, point, ShapeType.OUTLINE, FluidHandling.NONE, CLIENT.player));
         if (sideHit != null && sideHit.getBlockPos().equals(pos)) {
            return sideHit;
         }
      }

      return new BlockHitResult(center, Direction.UP, pos, false);
   }

   private Rotation rotationTo(Vec3d target) {
      Vec3d eye = CLIENT.player.getEyePos();
      double dx = target.x - eye.x;
      double dy = target.y - eye.y;
      double dz = target.z - eye.z;
      double horiz = Math.sqrt(dx * dx + dz * dz);
      float yaw = (float)(MathHelper.atan2(dz, dx) * (180.0 / Math.PI)) - 90.0F;
      float pitch = (float)(-(MathHelper.atan2(dy, horiz) * (180.0 / Math.PI)));
      return new Rotation(yaw, pitch);
   }

   private void goTo(BlockPos pos, int range) {
      if (this.baritone != null && pos != null) {
         this.baritone.getMineProcess().cancel();
         this.baritone.getCustomGoalProcess().setGoalAndPath(new GoalNear(pos, range));
      }
   }

   private void goToStandOn(BlockPos sand) {
      if (this.baritone != null && sand != null) {
         this.baritone.getMineProcess().cancel();
         // Stand on top of the suspicious block.
         this.baritone.getCustomGoalProcess().setGoalAndPath(new GoalNear(sand.up(), 0));
      }
   }

   private void cancelPath() {
      if (this.baritone != null) {
         this.baritone.getPathingBehavior().cancelEverything();
      }
   }

   private void cancelBaritoneMineOnly() {
      if (this.baritone != null) {
         try {
            this.baritone.getMineProcess().cancel();
         } catch (Throwable ignored) {
         }
      }
   }

   private void cancelBaritone() {
      this.releaseUseKey();
      if (this.baritone != null) {
         try {
            this.baritone.getMineProcess().cancel();
         } catch (Throwable ignored) {
         }

         this.baritone.getPathingBehavior().cancelEverything();
      }
   }

   private void captureBaritoneSettings() {
      Settings settings = BaritoneAPI.getSettings();
      this.prevAllowBreak = Boolean.TRUE.equals(settings.allowBreak.value);
      this.prevAllowPlace = Boolean.TRUE.equals(settings.allowPlace.value);
      this.prevAllowSprint = Boolean.TRUE.equals(settings.allowSprint.value);
      this.prevLegitMine = Boolean.TRUE.equals(settings.legitMine.value);
      this.prevMineScanDroppedItems = Boolean.TRUE.equals(settings.mineScanDroppedItems.value);
      this.prevBlockReach = settings.blockReachDistance.value;
      this.prevMineMaxOreLocationsCount = settings.mineMaxOreLocationsCount.value;
   }

   private void applyBaritoneSettings() {
      Settings settings = BaritoneAPI.getSettings();
      settings.allowBreak.value = true;
      settings.allowPlace.value = false;
      settings.allowSprint.value = true;
      settings.legitMine.value = false;
      settings.mineScanDroppedItems.value = this.podbiratLoot.isEnabled();
      settings.blockReachDistance.value = Math.max(4.5F, this.distantsiyaLoma.getValue());
      settings.mineMaxOreLocationsCount.value = 64;
      settings.mineGoalUpdateInterval.value = 5;
   }

   private void restoreBaritoneSettings() {
      try {
         Settings settings = BaritoneAPI.getSettings();
         settings.allowBreak.value = this.prevAllowBreak;
         settings.allowPlace.value = this.prevAllowPlace;
         settings.allowSprint.value = this.prevAllowSprint;
         settings.legitMine.value = this.prevLegitMine;
         settings.mineScanDroppedItems.value = this.prevMineScanDroppedItems;
         settings.blockReachDistance.value = this.prevBlockReach;
         settings.mineMaxOreLocationsCount.value = this.prevMineMaxOreLocationsCount;
      } catch (Throwable ignored) {
      }
   }

   private void blacklistTemporary(BlockPos pos) {
      this.blacklist.add(pos.toImmutable());
   }

   private void unblacklistBrushables() {
      if (CLIENT.world == null || this.blacklist.isEmpty()) {
         return;
      }

      this.blacklist.removeIf(pos -> this.isBrushable(CLIENT.world.getBlockState(pos)));
   }

   private void pruneBlacklist() {
      if (this.blacklist.size() > 256) {
         this.blacklist.clear();
      }
   }

   private String formatPos(BlockPos pos) {
      return pos.getX() + " " + pos.getY() + " " + pos.getZ();
   }

   private void log(String message) {
      if (this.logi.isEnabled()) {
         ChatUtil.sendClientMessage("§d[AutoEnd] §f" + message);
      }
   }

   private String resolveFarmAnarchy() {
      String fromCommand = EndFarmCommand.getFarmAnarchy();
      if (!fromCommand.isEmpty()) {
         return fromCommand;
      }

      return normalizeAnarchy(this.anarhiyaFarma.getValue());
   }

   private String resolveStashAnarchy() {
      String fromCommand = EndFarmCommand.getStashAnarchy();
      if (!fromCommand.isEmpty()) {
         return fromCommand;
      }

      return normalizeAnarchy(this.anarhiyaSklada.getValue());
   }

   private String resolveEndWarp() {
      String fromCommand = EndFarmCommand.getEndWarp();
      if (!fromCommand.isEmpty()) {
         return fromCommand;
      }

      String value = this.warpEnda.getValue();
      return value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
   }

   private void syncAnarchyFromCommand() {
      String farm = EndFarmCommand.getFarmAnarchy();
      if (!farm.isEmpty()) {
         this.anarhiyaFarma.value = farm;
      }

      String stash = EndFarmCommand.getStashAnarchy();
      if (!stash.isEmpty()) {
         this.anarhiyaSklada.value = stash;
      }

      String warp = EndFarmCommand.getEndWarp();
      if (!warp.isEmpty()) {
         this.warpEnda.value = warp;
      }
   }

   private static String normalizeAnarchy(String value) {
      return value == null ? "" : value.replaceAll("[^0-9]", "").trim();
   }

   private enum State {
      SEARCH,
      MINE,
      PATH,
      BREAK,
      LOOT,
      WAIT_PVP,
      WARP_STASH,
      FIND_CHEST,
      PATH_CHEST,
      OPEN_CHEST,
      DEPOSIT,
      FIND_INVIZ,
      PATH_INVIZ,
      OPEN_INVIZ,
      TAKE_INVIZ,
      WARP_FARM,
      DRINK_INVIZ,
      WARP_END
   }

   private record TargetSelection(BlockPos pos, boolean brushable) {
   }
}
