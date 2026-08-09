package ru.metaculture.protection;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.Settings;
import baritone.api.pathing.goals.GoalNear;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.entity.ItemEntity;
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
import net.minecraft.world.chunk.WorldChunk;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "AutoCopperDange",
   category = Category.Misc,
   description = "Фарм кувшинов в медном данже Funtime через Baritone"
)
public class AutoCopperDange extends Module {
   public final ModeSetting rezhim = new ModeSetting("Режим", "Baritone", "Baritone", "Скан");
   public final NumberSetting radiusPoiska = new NumberSetting("Радиус поиска", 48.0F, 16.0F, 96.0F, 1.0F, false);
   public final NumberSetting distantsiyaLoma = new NumberSetting("Дистанция лома", 4.2F, 2.5F, 5.0F, 0.1F, true)
      .setVisibilityCondition(() -> !this.rezhim.is("Скан"));
   public final BooleanSetting podbiratLoot = new BooleanSetting("Подбирать лут", true);
   public final BooleanSetting tolkoMednyyDanzh = new BooleanSetting("Только медный данж", true)
      .visibleWhen(() -> !this.rezhim.is("Скан"));
   public final TextSetting anarhiyaFarma = new TextSetting("Анархия фарма", "");
   public final TextSetting anarhiyaSklada = new TextSetting("Анархия склада", "312");
   public final TextSetting tablivkaSunduka = new TextSetting("Табличка сундука", "Ресурсы");
   public final BooleanSetting vozvrashatsyaNaFarm = new BooleanSetting("Возвращаться на фарм", true);
   public final BooleanSetting logi = new BooleanSetting("Логи", true);
   public final TextSetting dopolnitelnyeBloki = new TextSetting("Доп. блоки", "decorated_pot");

   private static final long RESCAN_MS = 1500L;
   private static final long MINE_RESTART_MS = 2500L;
   private static final long BREAK_TIMEOUT_MS = 5000L;
   private static final long WARP_RETRY_MS = 8000L;
   private static final long PVP_MIN_MS = 30000L;
   private static final long PVP_CLEAR_MS = 1500L;
   private static final long HOME_RETRY_MS = 3500L;
   private static final long DEPOSIT_CLICK_MS = 60L;
   private static final double LOOT_RANGE = 24.0;
   private static final double CHEST_REACH = 4.0;
   private static final int MAX_HOME_ATTEMPTS = 2;

   private IBaritone baritone;
   private AutoCopperDange.State state = AutoCopperDange.State.SEARCH;
   private BlockPos target;
   private BlockPos breaking;
   private BlockPos stashChest;
   private boolean wasInPvp;
   private boolean stashRequested;
   private long pvpClearSince;
   private long pvpStartedAt;
   private int homeAttempts;
   private final Set<BlockPos> blacklist = new HashSet<>();
   private final DualTimer rescanTimer = new DualTimer();
   private final DualTimer mineTimer = new DualTimer();
   private final DualTimer breakTimer = new DualTimer();
   private final DualTimer pathTimer = new DualTimer();
   private final DualTimer warpTimer = new DualTimer();
   private final DualTimer depositTimer = new DualTimer();
   private final DualTimer actionTimer = new DualTimer();
   private final DualTimer homeTimer = new DualTimer();

   private boolean prevAllowBreak;
   private boolean prevAllowPlace;
   private boolean prevAllowSprint;
   private boolean prevLegitMine;
   private boolean prevMineScanDroppedItems;
   private float prevBlockReach;
   private int prevMineMaxOreLocationsCount;

   public AutoCopperDange() {
      this.addSettings(
         new Setting[]{
            this.rezhim,
            this.radiusPoiska,
            this.distantsiyaLoma,
            this.podbiratLoot,
            this.tolkoMednyyDanzh,
            this.anarhiyaFarma,
            this.anarhiyaSklada,
            this.tablivkaSunduka,
            this.vozvrashatsyaNaFarm,
            this.logi,
            this.dopolnitelnyeBloki
         }
      );
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.baritone = BaritoneAPI.getProvider().getPrimaryBaritone();
      this.state = AutoCopperDange.State.SEARCH;
      this.target = null;
      this.breaking = null;
      this.stashChest = null;
      this.wasInPvp = false;
      this.stashRequested = false;
      this.pvpClearSince = 0L;
      this.pvpStartedAt = 0L;
      this.homeAttempts = 0;
      this.blacklist.clear();
      this.rescanTimer.invoke();
      this.mineTimer.invoke();
      this.breakTimer.invoke();
      this.pathTimer.invoke();
      this.warpTimer.invoke();
      this.depositTimer.invoke();
      this.actionTimer.invoke();
      this.homeTimer.invoke();
      this.captureBaritoneSettings();
      this.applyBaritoneSettings();
      this.syncAnarchyFromCommand();
      this.log("Запуск фарма кувшинов (" + this.rezhim.getValue() + ")");
      if (this.resolveFarmAnarchy().isEmpty()) {
         this.log("§eЗадай анархию фарма: §f.copper anarchy <номер>");
      }
      if (this.rezhim.is("Baritone")) {
         this.startMineProcess();
      }
   }

   @Override
   public void onDisable() {
      this.cancelBaritone();
      this.restoreBaritoneSettings();
      this.target = null;
      this.breaking = null;
      this.stashChest = null;
      this.state = AutoCopperDange.State.SEARCH;
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
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player == null || CLIENT.world == null || CLIENT.interactionManager == null || this.baritone == null) {
         return;
      }

      if (PlayerHelper.check()) {
         this.cancelBaritone();
         return;
      }

      this.pruneBlacklist();
      this.updatePvpAndStashRequest();

      if (this.isStashFlow()) {
         this.tickStashFlow();
         return;
      }

      if (this.isInCombat()) {
         if (this.state != AutoCopperDange.State.WAIT_PVP) {
            this.cancelBaritone();
            this.state = AutoCopperDange.State.WAIT_PVP;
            this.log("Режим боя — жду окончания");
         }
         return;
      }

      if (this.tickLootPickup()) {
         return;
      }

      // Restore: if (this.tryStashLootWhenSafe()) return;

      if (this.rezhim.is("Baritone")) {
         this.tickBaritoneMine();
      } else {
         this.tickScanMode();
      }
   }

   private boolean isInCombat() {
      return PvPSafe.check2() || ServerStatsParser.check();
   }

   private void updatePvpAndStashRequest() {
      boolean inPvp = this.isInCombat();
      long now = System.currentTimeMillis();
      if (inPvp) {
         this.wasInPvp = true;
         this.pvpClearSince = 0L;
         if (this.pvpStartedAt <= 0L) {
            this.pvpStartedAt = now;
            this.log("Режим боя Funtime — жду минимум " + PVP_MIN_MS / 1000L + "с");
         }

         if (this.state == AutoCopperDange.State.WARP_ANARCHY || this.state == AutoCopperDange.State.RETURN_FARM) {
            this.cancelBaritone();
            this.state = AutoCopperDange.State.WAIT_PVP;
            this.log("Снова бой — отменяю варп");
         }

         return;
      }

      if (!this.wasInPvp) {
         return;
      }

      if (this.pvpStartedAt > 0L && now - this.pvpStartedAt < PVP_MIN_MS) {
         return;
      }

      if (this.pvpClearSince <= 0L) {
         this.pvpClearSince = now;
         this.log("Бой спал — пауза " + PVP_CLEAR_MS / 1000L + "с перед складом");
         return;
      }

      if (now - this.pvpClearSince < PVP_CLEAR_MS) {
         return;
      }

      this.wasInPvp = false;
      this.pvpClearSince = 0L;
      this.pvpStartedAt = 0L;
      if (this.hasLootToDeposit()) {
         this.stashRequested = true;
         this.log("Бой закончился — еду на склад /an" + this.resolveStashAnarchy());
         this.beginStashFlow();
      } else {
         this.log("Бой закончился, лута нет — продолжаю фарм");
      }
   }

   private boolean tickLootPickup() {
      if (!this.podbiratLoot.isEnabled()) {
         return false;
      }

      ItemEntity loot = this.findNearbyLoot();
      if (loot == null) {
         if (this.state == AutoCopperDange.State.LOOT) {
            this.state = AutoCopperDange.State.SEARCH;
         }

         return false;
      }

      if (this.state != AutoCopperDange.State.LOOT) {
         this.cancelBaritone();
         this.state = AutoCopperDange.State.LOOT;
         this.log("Подбираю: " + loot.getStack().getName().getString());
         this.pathTimer.setTimestamp22(0L);
      }

      if (!this.baritone.getCustomGoalProcess().isActive() || this.pathTimer.check5(1500L)) {
         this.goTo(loot.getBlockPos(), 1);
         this.pathTimer.invoke();
      }

      return true;
   }

   /*
    * Disabled: stash when loot present and no PvP.
    * To restore: uncomment method, STASH_LOOT_CHECK_MS + stashLootTimer,
    * and calls in onPlayerTick / tickLootPickup.
    *
   private boolean tryStashLootWhenSafe() {
      if (this.isStashFlow() || this.isInCombat() || this.wasInPvp) {
         return false;
      }
      if (!this.hasLootToDeposit()) {
         return false;
      }
      if (!this.stashLootTimer.check5(STASH_LOOT_CHECK_MS)) {
         return false;
      }
      this.stashLootTimer.invoke();
      this.stashRequested = true;
      this.log("Лут есть, PvP нет — склад /an" + this.resolveStashAnarchy());
      this.beginStashFlow();
      return true;
   }
   */

   private boolean isStashFlow() {
      return this.state == AutoCopperDange.State.WARP_ANARCHY
         || this.state == AutoCopperDange.State.FIND_CHEST
         || this.state == AutoCopperDange.State.PATH_CHEST
         || this.state == AutoCopperDange.State.OPEN_CHEST
         || this.state == AutoCopperDange.State.DEPOSIT
         || this.state == AutoCopperDange.State.RETURN_FARM;
   }

   private void beginStashFlow() {
      this.cancelBaritone();
      this.target = null;
      this.breaking = null;
      this.stashChest = null;
      this.state = AutoCopperDange.State.WARP_ANARCHY;
      this.warpTimer.invoke();
      this.actionTimer.invoke();
   }

   private void tickStashFlow() {
      switch (this.state) {
         case WARP_ANARCHY -> this.tickWarpAnarchy();
         case FIND_CHEST, PATH_CHEST -> this.tickFindAndPathChest();
         case OPEN_CHEST -> this.tickOpenChest();
         case DEPOSIT -> this.tickDeposit();
         case RETURN_FARM -> this.tickReturnFarm();
         default -> {
         }
      }
   }

   private void tickWarpAnarchy() {
      if (this.isInCombat()) {
         this.wasInPvp = true;
         this.pvpClearSince = 0L;
         if (this.pvpStartedAt <= 0L) {
            this.pvpStartedAt = System.currentTimeMillis();
         }

         this.cancelBaritone();
         this.state = AutoCopperDange.State.WAIT_PVP;
         this.log("Бой во время варпа — жду");
         return;
      }

      String targetAnarchy = this.resolveStashAnarchy();
      if (targetAnarchy.isEmpty()) {
         this.log("§cУкажи анархию склада (.copper stash <номер> или в настройках)");
         this.finishStashAndResume(false);
         return;
      }

      ServerStatsParser.INSTANCE.invoke2();
      String current = ServerStatsParser.INSTANCE.getNA2();
      if (targetAnarchy.equals(current)) {
         this.log("На анархии " + targetAnarchy + " — ищу сундук «" + this.tablivkaSunduka.getValue() + "»");
         this.state = AutoCopperDange.State.FIND_CHEST;
         this.actionTimer.invoke();
         return;
      }

      if (this.warpTimer.check5(WARP_RETRY_MS)) {
         CLIENT.player.networkHandler.sendChatCommand("an" + targetAnarchy);
         this.log("Варп: /an" + targetAnarchy);
         this.warpTimer.invoke();
      }
   }

   private void tickFindAndPathChest() {
      if (CLIENT.currentScreen instanceof GenericContainerScreen) {
         this.state = AutoCopperDange.State.DEPOSIT;
         this.depositTimer.invoke();
         return;
      }

      if (this.stashChest == null || !this.isStorage(this.stashChest) || !this.chestMatchesSign(this.stashChest)) {
         this.stashChest = this.findStashChest();
         if (this.stashChest == null) {
            if (this.actionTimer.check5(10000L)) {
               this.log("§cСундук с табличкой не найден");
               this.finishStashAndResume(true);
            }
            return;
         }

         this.log("Сундук: " + this.formatPos(this.stashChest));
         this.state = AutoCopperDange.State.PATH_CHEST;
         this.pathTimer.invoke();
      }

      double dist = CLIENT.player.getPos().distanceTo(Vec3d.ofCenter(this.stashChest));
      if (dist <= CHEST_REACH && this.canSeeBlock(this.stashChest)) {
         this.cancelPath();
         this.state = AutoCopperDange.State.OPEN_CHEST;
         this.actionTimer.invoke();
         return;
      }

      if (!this.baritone.getCustomGoalProcess().isActive() || this.pathTimer.check5(2500L)) {
         this.goTo(this.stashChest, 1);
         this.pathTimer.invoke();
      }

      if (this.actionTimer.check5(20000L)) {
         this.log("§cНе смог подойти к сундуку");
         this.finishStashAndResume(true);
      }
   }

   private void tickOpenChest() {
      if (CLIENT.currentScreen instanceof GenericContainerScreen) {
         this.state = AutoCopperDange.State.DEPOSIT;
         this.depositTimer.invoke();
         return;
      }

      if (this.stashChest == null) {
         this.state = AutoCopperDange.State.FIND_CHEST;
         return;
      }

      Rotation rotation = this.rotationTo(Vec3d.ofCenter(this.stashChest));
      RotationController.invoke3(rotation, 35.0F, 35.0F, 35.0F, 35.0F, 2, 20, false);
      if (new Rotation(CLIENT.player).measure(rotation) > 8.0F) {
         return;
      }

      if (this.actionTimer.check5(200L)) {
         BlockHitResult hit = this.raycastTo(this.stashChest);
         CLIENT.interactionManager.interactBlock(CLIENT.player, Hand.MAIN_HAND, hit);
         CLIENT.player.swingHand(Hand.MAIN_HAND);
         this.state = AutoCopperDange.State.DEPOSIT;
         this.depositTimer.invoke();
         this.actionTimer.invoke();
      }

      if (this.pathTimer.check5(8000L) && !(CLIENT.currentScreen instanceof GenericContainerScreen)) {
         this.log("§cНе открылся сундук, повтор поиска");
         this.stashChest = null;
         this.state = AutoCopperDange.State.FIND_CHEST;
         this.actionTimer.invoke();
      }
   }

   private void tickDeposit() {
      if (!(CLIENT.currentScreen instanceof GenericContainerScreen screen)) {
         if (!this.hasLootToDeposit()) {
            this.finishStashAndResume(true);
         } else if (this.actionTimer.check5(1500L)) {
            this.state = AutoCopperDange.State.OPEN_CHEST;
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
         this.log("Склад завершён");
         this.finishStashAndResume(true);
      }
   }

   private void tickReturnFarm() {
      if (this.isInCombat()) {
         this.wasInPvp = true;
         this.pvpClearSince = 0L;
         if (this.pvpStartedAt <= 0L) {
            this.pvpStartedAt = System.currentTimeMillis();
         }

         this.cancelBaritone();
         this.state = AutoCopperDange.State.WAIT_PVP;
         this.log("Бой во время возврата — жду");
         return;
      }

      String farmAnarchy = this.resolveFarmAnarchy();
      if (!this.vozvrashatsyaNaFarm.isEnabled() || farmAnarchy.isEmpty()) {
         this.resumeFarming();
         return;
      }

      ServerStatsParser.INSTANCE.invoke2();
      String current = ServerStatsParser.INSTANCE.getNA2();
      if (!farmAnarchy.equals(current)) {
         if (this.warpTimer.check5(WARP_RETRY_MS)) {
            CLIENT.player.networkHandler.sendChatCommand("an" + farmAnarchy);
            this.log("Возврат на фарм /an" + farmAnarchy);
            this.warpTimer.invoke();
            this.homeAttempts = 0;
            this.homeTimer.invoke();
         }

         return;
      }

      if (!this.isPlayerInCopperDungeon() && this.homeAttempts < MAX_HOME_ATTEMPTS) {
         if (this.homeTimer.check5(HOME_RETRY_MS)) {
            CLIENT.player.networkHandler.sendChatCommand("home home");
            this.homeAttempts++;
            this.homeTimer.invoke();
            this.log("Не в медном данже — /home home (" + this.homeAttempts + "/" + MAX_HOME_ATTEMPTS + ")");
         }

         return;
      }

      if (this.isPlayerInCopperDungeon()) {
         this.log("Вернулся в медный данж /an" + farmAnarchy);
      } else {
         this.log("Вернулся на /an" + farmAnarchy + " (данж не найден после /home)");
      }

      this.resumeFarming();
   }

   private void finishStashAndResume(boolean returnToFarm) {
      this.stashRequested = false;
      this.stashChest = null;
      if (CLIENT.player != null) {
         CLIENT.player.closeHandledScreen();
      }

      this.cancelBaritone();
      String farmAnarchy = this.resolveFarmAnarchy();
      if (returnToFarm && this.vozvrashatsyaNaFarm.isEnabled() && !farmAnarchy.isEmpty()) {
         this.state = AutoCopperDange.State.RETURN_FARM;
         this.homeAttempts = 0;
         this.warpTimer.setTimestamp22(0L);
         this.homeTimer.invoke();
         this.log("После склада возвращаюсь на /an" + farmAnarchy);
      } else {
         if (farmAnarchy.isEmpty()) {
            this.log("§eАнархия фарма не задана — остаюсь здесь. Укажи: §f.copper anarchy <номер>");
         }

         this.resumeFarming();
      }
   }

   private void resumeFarming() {
      this.state = AutoCopperDange.State.SEARCH;
      this.target = null;
      this.breaking = null;
      this.stashChest = null;
      this.stashRequested = false;
      this.homeAttempts = 0;
      this.applyBaritoneSettings();
      this.log("Продолжаю фарм");
      if (this.rezhim.is("Baritone")) {
         this.startMineProcess();
      }
   }

   private void tickBaritoneMine() {
      this.state = AutoCopperDange.State.MINE;
      if (!this.baritone.getMineProcess().isActive() && this.mineTimer.check5(MINE_RESTART_MS)) {
         this.startMineProcess();
         this.mineTimer.invoke();
      }
   }

   private void tickScanMode() {
      if (this.target != null && !this.isJar(CLIENT.world.getBlockState(this.target))) {
         this.blacklistTemporary(this.target);
         this.target = null;
         this.breaking = null;
         this.state = AutoCopperDange.State.SEARCH;
         this.cancelPath();
      }

      if (this.target == null || this.rescanTimer.check5(RESCAN_MS)) {
         BlockPos next = this.findNearestJar();
         this.rescanTimer.invoke();
         if (next == null) {
            this.state = AutoCopperDange.State.SEARCH;
            this.cancelPath();
            return;
         }

         if (this.target == null || !this.target.equals(next)) {
            this.target = next.toImmutable();
            this.breaking = null;
            this.log("Цель: " + this.formatPos(this.target));
            this.goTo(this.target, 2);
            this.pathTimer.invoke();
         }
      }

      if (this.target == null) {
         return;
      }

      double reach = this.distantsiyaLoma.getValue();
      double dist = CLIENT.player.getEyePos().distanceTo(Vec3d.ofCenter(this.target));
      if (dist > reach) {
         this.state = AutoCopperDange.State.PATH;
         if (!this.baritone.getCustomGoalProcess().isActive() || this.pathTimer.check5(3000L)) {
            this.goTo(this.target, 2);
            this.pathTimer.invoke();
         }
         return;
      }

      this.state = AutoCopperDange.State.BREAK;
      this.cancelPath();
      this.breakTarget(this.target);
   }

   private void startMineProcess() {
      List<Block> jars = this.resolveJarBlocks();
      if (jars.isEmpty()) {
         this.log("§cНе найдены блоки кувшинов");
         return;
      }

      this.cancelPath();
      try {
         this.baritone.getMineProcess().mine(0, jars.toArray(Block[]::new));
         this.log("Baritone mine: " + jars.size() + " тип(ов) кувшинов");
      } catch (Throwable exception) {
         String[] names = jars.stream().map(block -> Registries.BLOCK.getId(block).toString()).toArray(String[]::new);
         this.baritone.getMineProcess().mineByName(0, names);
         this.log("Baritone mineByName: " + String.join(", ", names));
      }
   }

   private void breakTarget(BlockPos pos) {
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

   private BlockPos findNearestJar() {
      int radius = Math.round(this.radiusPoiska.getValue());
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

               BlockState state = CLIENT.world.getBlockState(pos);
               if (!this.isJar(state)) {
                  continue;
               }

               if (this.tolkoMednyyDanzh.isEnabled() && !this.isCopperDungeonContext(pos)) {
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

   private BlockPos findStashChest() {
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
               if (!this.isStorage(pos) || !this.chestMatchesSign(pos)) {
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

   private boolean chestMatchesSign(BlockPos chestPos) {
      String sign = this.readNearbySign(chestPos).toLowerCase(Locale.ROOT);
      if (sign.isEmpty()) {
         return false;
      }

      String wanted = this.tablivkaSunduka.getValue() == null ? "" : this.tablivkaSunduka.getValue().trim().toLowerCase(Locale.ROOT);
      if (wanted.isEmpty()) {
         wanted = "ресурсы";
      }

      if (sign.contains(wanted)) {
         return true;
      }

      return wanted.contains("ресурс") && (sign.contains("ресурс") || sign.contains("ресы"));
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

   private boolean isPlayerInCopperDungeon() {
      return CLIENT.player != null && CLIENT.world != null && this.isCopperDungeonContext(CLIENT.player.getBlockPos(), 16);
   }

   private boolean isCopperDungeonContext(BlockPos pot) {
      return this.isCopperDungeonContext(pot, 6);
   }

   private boolean isCopperDungeonContext(BlockPos pot, int r) {
      for (int x = -r; x <= r; x++) {
         for (int y = -r; y <= r; y++) {
            for (int z = -r; z <= r; z++) {
               if (this.isCopperDungeonBlock(CLIENT.world.getBlockState(pot.add(x, y, z)).getBlock())) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   private boolean isCopperDungeonBlock(Block block) {
      return block == Blocks.COPPER_BLOCK
         || block == Blocks.CUT_COPPER
         || block == Blocks.CHISELED_COPPER
         || block == Blocks.COPPER_GRATE
         || block == Blocks.COPPER_BULB
         || block == Blocks.WAXED_COPPER_BLOCK
         || block == Blocks.WAXED_CUT_COPPER
         || block == Blocks.WAXED_CHISELED_COPPER
         || block == Blocks.WAXED_COPPER_GRATE
         || block == Blocks.WAXED_COPPER_BULB
         || block == Blocks.EXPOSED_COPPER
         || block == Blocks.WEATHERED_COPPER
         || block == Blocks.OXIDIZED_COPPER
         || block == Blocks.TUFF
         || block == Blocks.TUFF_BRICKS
         || block == Blocks.CHISELED_TUFF
         || block == Blocks.CHISELED_TUFF_BRICKS
         || block == Blocks.POLISHED_TUFF
         || block == Blocks.TRIAL_SPAWNER
         || block == Blocks.VAULT;
   }

   private boolean isJar(BlockState state) {
      if (state == null || state.isAir()) {
         return false;
      }

      Block block = state.getBlock();
      if (block == Blocks.DECORATED_POT) {
         return true;
      }

      for (Block configured : this.resolveJarBlocks()) {
         if (configured == block) {
            return true;
         }
      }

      return false;
   }

   private List<Block> resolveJarBlocks() {
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
            if (block != null && block != Blocks.AIR && !blocks.contains(block)) {
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
      return stack != null && !stack.isEmpty() && !this.isProtectedGear(stack) && !this.isJunkLoot(stack);
   }

   private boolean isProtectedGear(ItemStack stack) {
      return stack.isOf(Items.TOTEM_OF_UNDYING)
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
         || stack.isOf(Items.NETHERRACK)
         || stack.isOf(Items.BASALT)
         || stack.isOf(Items.SMOOTH_BASALT)
         || stack.isOf(Items.TUFF)
         || stack.isOf(Items.ANDESITE)
         || stack.isOf(Items.DIORITE)
         || stack.isOf(Items.GRANITE)
         || stack.isOf(Items.ROTTEN_FLESH)
         || stack.isOf(Items.BONE)
         || stack.isOf(Items.STRING)
         || stack.isOf(Items.STICK)
         || stack.isOf(Items.WHEAT_SEEDS)
         || stack.isOf(Items.KELP);
   }

   private String resolveFarmAnarchy() {
      String fromCommand = CopperFarmCommand.getFarmAnarchy();
      if (!fromCommand.isEmpty()) {
         return fromCommand;
      }

      return normalizeAnarchy(this.anarhiyaFarma.getValue());
   }

   private String resolveStashAnarchy() {
      String fromCommand = CopperFarmCommand.getStashAnarchy();
      if (!fromCommand.isEmpty()) {
         return fromCommand;
      }

      return normalizeAnarchy(this.anarhiyaSklada.getValue());
   }

   private void syncAnarchyFromCommand() {
      String farm = CopperFarmCommand.getFarmAnarchy();
      if (!farm.isEmpty()) {
         this.anarhiyaFarma.value = farm;
      }

      String stash = CopperFarmCommand.getStashAnarchy();
      if (!stash.isEmpty()) {
         this.anarhiyaSklada.value = stash;
      }
   }

   private static String normalizeAnarchy(String value) {
      return value == null ? "" : value.replaceAll("[^0-9]", "").trim();
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

   private void cancelPath() {
      if (this.baritone != null) {
         this.baritone.getPathingBehavior().cancelEverything();
      }
   }

   private void cancelBaritone() {
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
         ChatUtil.sendClientMessage("§6[AutoCopperDange] §f" + message);
      }
   }

   private enum State {
      SEARCH,
      MINE,
      PATH,
      BREAK,
      LOOT,
      WAIT_PVP,
      WARP_ANARCHY,
      FIND_CHEST,
      PATH_CHEST,
      OPEN_CHEST,
      DEPOSIT,
      RETURN_FARM
   }
}
