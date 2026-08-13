package ru.metaculture.protection;

import baritone.api.BaritoneAPI;
import baritone.api.pathing.goals.GoalBlock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import net.minecraft.world.chunk.WorldChunk;
import org.wild.mixin.acceser.BossBarHudAccessor;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "AutoWarden",
   description = "Автоматизирует фарм варденов на анархии",
   category = Category.Misc
)
public class AutoWarden extends Module {
   private static final Pattern TIMER = Pattern.compile("(\\d{2}):(\\d{2})");
   private int i;
   private boolean j;
   private Box k;
   private BlockPos l;
   private String n;
   private final List<Integer> b = new ArrayList<>();
   private final Map<BlockPos, Integer> c = new HashMap<>();
   private final Map<BlockPos, Integer> d = new HashMap<>();
   private final Map<BlockPos, ChestTimer> chestTimers = new HashMap<>();
   private final BooleanSetting e = new BooleanSetting("Использовать скорость", false);
   private final BooleanSetting f = new BooleanSetting("Репортить обидчиков", false);
   private final ModeSetting g = new ModeSetting("Приоритеты лута", "Средний", "Низкий", "Средний", "Высокий");
   private int h = 1;
   private final DualTimer m = new DualTimer();
   private AutoWarden.State o = AutoWarden.State.SAVE;
   private AutoWarden.UseTask useTask;

   public enum State {
      SAVE,
      TAKE,
      COLLECTING,
      ESCAPE
   }

   public List<Integer> q() {
      return this.b;
   }

   public AutoWarden() {
      this.addSettings(new Setting[]{this.e, this.f, this.g});
   }

   @Override
   public void onEnable() {
      super.onEnable();
      int current = currentAnarchy();
      if (current >= 0) {
         this.b.remove(Integer.valueOf(current));
         this.b.add(0, current);
      }
      this.h = 1;
      this.o = AutoWarden.State.COLLECTING;
      this.d.clear();
      ChatUtil.sendClientMessage("Shift + Пробел — быстрое выключение функции");
      BaritoneAPI.getSettings().avoidance.value = true;
      BaritoneAPI.getSettings().maxFallHeightNoWater.value = 256;
      BaritoneAPI.getSettings().blockFreeLook.value = true;
      BaritoneAPI.getSettings().randomLooking.value = 1.0;
      BaritoneAPI.getSettings().randomLooking113.value = 1.0;
      this.d(true);
   }

   @Override
   public void onDisable() {
      super.onDisable();
      BaritoneAPI.getSettings().allowBreak.value = false;
      BaritoneAPI.getSettings().allowPlace.value = false;
      BaritoneAPI.getSettings().avoidance.value = false;
      BaritoneAPI.getSettings().maxFallHeightNoWater.value = 3;
      this.d(false);
      this.C();
      this.useTask = null;
   }

   private void d(boolean add) {
      List<Block> list = (List<Block>)BaritoneAPI.getSettings().blocksToAvoid.value;
      for (Block block : Registries.BLOCK) {
         if (isSculk(block.getDefaultState())) {
            if (!add) {
               list.remove(block);
            } else if (!list.contains(block)) {
               list.add(block);
            }
         }
      }
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      this.tickUse();
      if (CLIENT.currentScreen instanceof DeathScreen && CLIENT.player != null && CLIENT.player.deathTime >= 5) {
         CLIENT.player.requestRespawn();
      }
      if (CLIENT.player == null || CLIENT.world == null) {
         return;
      }
      if (CLIENT.options.sneakKey.isPressed() && CLIENT.options.jumpKey.isPressed()) {
         this.toggle();
         return;
      }
      this.refreshChestTimers();
      for (Entity entity : CLIENT.world.getEntities()) {
         if (entity instanceof WardenEntity warden) {
            this.d.put(warden.getBlockPos(), CLIENT.player.age + 100);
         }
      }
      this.d.values().removeIf(expire -> CLIENT.player.age > expire);
      if (this.n != null) {
         if (CLIENT.player.age >= 20 && CLIENT.player.age < 30) {
            CLIENT.player.networkHandler.sendChatCommand("report " + this.n + " чит");
            this.n = null;
            return;
         }
         return;
      }
      if (CLIENT.player.age < 5) {
         this.i = 0;
         this.c.clear();
         return;
      }
      if (currentAnarchy() < 0) {
         if (CLIENT.player.age % 100 == 0 && this.t() >= 0 && CLIENT.player.age > 300) {
            CLIENT.player.networkHandler.sendChatCommand("an" + this.t());
         }
         this.o = AutoWarden.State.SAVE;
         return;
      }
      if (CLIENT.player.age % 100 == 0 && this.E() && (this.k == null || !this.G())) {
         this.F();
      }
      if (CLIENT.player.hasStatusEffect(StatusEffects.DARKNESS) && this.b(32.0)) {
         this.e(false);
         return;
      }
      switch (this.o) {
         case SAVE -> this.v();
         case TAKE -> this.w();
         case COLLECTING -> this.x();
         case ESCAPE -> this.B();
      }
      if (!this.r() || CLIENT.player.age % 15 != 0) {
         return;
      }
      this.C();
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (packetEvent.getPacketEventState() != PacketEvent.PacketEventState.RECEIVE) {
         return;
      }
      if (packetEvent.getPacket() instanceof GameMessageS2CPacket message) {
         String text = message.content().getString();
         if (!text.contains("Помянем. Вы погибли")) {
            return;
         }
         this.j = true;
         if (this.f.isEnabled() && CLIENT.player != null && text.contains("Вас убил")) {
            StringBuilder effects = new StringBuilder();
            for (StatusEffectInstance effect : CLIENT.player.getStatusEffects()) {
               effects.append(effect.getEffectType().getIdAsString()).append(' ');
            }
            ChatUtil.sendClientMessage("Эффекты при смерти: " + (effects.isEmpty() ? "нет" : effects.toString().trim()));
            if (!CLIENT.player.hasStatusEffect(StatusEffects.DARKNESS) && !this.a(2.0)) {
               this.n = text.split("Вас убил ")[1].split(",")[0].trim();
            }
         }
      } else if (packetEvent.getPacket() instanceof PlaySoundS2CPacket sound && CLIENT.player != null) {
         String path = ((SoundEvent)sound.getSound().value()).id().getPath();
         if (path.contains("warden.roar") || path.contains("warden.angry") || path.contains("warden.sonic")) {
            this.i = CLIENT.player.age + 100;
         }
      }
   }

   @EventHandler
   public void onMovementInput(MovementInputEvent event) {
      if (CLIENT.player == null) {
         return;
      }
      if (!CLIENT.player.isOnGround() && !CLIENT.player.isClimbing()) {
         event.setFlag(false);
      }
      if (this.r() && CLIENT.player.getMainHandStack().isEmpty() && !this.a(3.0)) {
         int dir = CLIENT.player.age % 10 <= (int)random(3.0F, 8.0F) ? -1 : 1;
         event.setFloatValue(dir);
         event.setFloatValue2(dir);
      }
   }

   private boolean r() {
      if (isSculk(CLIENT.world.getBlockState(CLIENT.player.getBlockPos()))
         || isSculk(CLIENT.world.getBlockState(CLIENT.player.getBlockPos().down()))) {
         return true;
      }
      return !this.A()
         && this.o == AutoWarden.State.COLLECTING
         && this.E()
         && CLIENT.currentScreen == null
         && !this.L()
         && !this.isUsing()
         && this.s();
   }

   private boolean s() {
      Box box = CLIENT.player.getBoundingBox().expand(0.05000000009506496, 0.0, 0.05000000009506496);
      for (BlockPos pos : BlockPos.iterate(BlockPos.ofFloored(box.minX, box.minY, box.minZ), BlockPos.ofFloored(box.maxX, box.maxY, box.maxZ))) {
         if (!CLIENT.world.getBlockState(pos).isAir()) {
            return true;
         }
      }
      return false;
   }

   private boolean a(double range) {
      for (BlockPos chest : this.chests()) {
         if (CLIENT.player.squaredDistanceTo(Vec3d.ofCenter(chest)) <= range * range) {
            return true;
         }
      }
      return false;
   }

   private int t() {
      return this.b.isEmpty() ? -1 : this.b.getFirst();
   }

   private boolean u() {
      return this.t() >= 0 && this.t() == currentAnarchy();
   }

   private void v() {
      if (this.t() >= 0 && currentAnarchy() != this.t() && !inPvp() && CLIENT.player.age % 5 == 0 && CLIENT.player.age > 5) {
         CLIENT.player.networkHandler.sendChatCommand("an" + this.t());
      }
      if (this.u()) {
         this.M();
         this.a(this.R(), true, AutoWarden.State.TAKE);
      }
   }

   private void w() {
      if (this.j && this.b.size() > 1) {
         int next = this.h + 1;
         this.h = next;
         if (next >= this.b.size()) {
            this.h = 1;
         }
         this.j = false;
      }
      this.i = 0;
      this.c.clear();
      if (CLIENT.player.age % 20 == 0) {
         StringBuilder missing = new StringBuilder("Собираем (возможно не хватает) -> ");
         if (this.Q() < 1) {
            missing.append("зелье невидимости, ");
         }
         if (countItem(Items.GOLDEN_CARROT) < 3) {
            missing.append("золотая морковь, ");
         }
         if (this.e.isEnabled() && this.a(this::f) < 0) {
            missing.append("зелье скорости ");
         }
         if (CLIENT.player.age % 200 == 0 && !missing.isEmpty() && !this.O()) {
            CLIENT.player.dropSelectedItem(false);
         }
      }
      if (this.u()) {
         this.a(this.P() || this.O(), false, AutoWarden.State.COLLECTING);
      }
   }

   private void x() {
      if (this.y()) {
         return;
      }
      this.eatIfHungry(18);
      if (this.isUsing()) {
         if (this.L()) {
            this.C();
            return;
         }
         return;
      }
      if (this.b.size() <= 1) {
         if (CLIENT.player.age % 20 == 0) {
            ChatUtil.sendClientMessage("ОШИБКА -> .warden list пустой");
         }
         return;
      }
      if (this.h >= this.b.size()) {
         this.h = 1;
      }
      int target = this.b.get(this.h);
      if (currentAnarchy() != target) {
         if (CLIENT.player.age % 10 != 0 || CLIENT.player.age <= 10) {
            return;
         }
         CLIENT.player.networkHandler.sendChatCommand("an" + target);
         return;
      }
      if (CLIENT.player.age > 5) {
         this.z();
      }
   }

   private int b(int base) {
      double mul = this.g.is("Низкий") ? 1.5 : (this.g.is("Высокий") ? 0.80000014538821 : 1.0);
      return (int)(base * mul);
   }

   private boolean y() {
      boolean aggro = this.A();
      if ((aggro
            || this.D() > this.b(20)
            || CLIENT.player.getHungerManager().getFoodLevel() < 8
            || this.c.values().stream().filter(count -> count >= 2).count() >= 3 && CLIENT.player.age % 30 == 0)
         && CLIENT.player.age > 100) {
         if (aggro) {
            this.j = true;
         }
         this.o = AutoWarden.State.ESCAPE;
         return true;
      }
      if (!inPvp() && this.D() > this.b(8)) {
         this.o = AutoWarden.State.ESCAPE;
         return true;
      }
      int pvpTime = pvpSeconds();
      if (pvpTime >= 0 && pvpTime < 7 && !this.b(14.0) && this.D() > this.b(7)) {
         this.o = AutoWarden.State.ESCAPE;
         return true;
      }
      return false;
   }

   private void z() {
      StatusEffectInstance invis = CLIENT.player.getStatusEffect(StatusEffects.INVISIBILITY);
      boolean ready = CLIENT.player.hasStatusEffect(StatusEffects.DARKNESS) || invis != null && invis.getDuration() >= 400;
      if (!ready && invis == null && this.Q() < 1 && CLIENT.player.age % 5 == 0 && !inPvp()) {
         this.o = AutoWarden.State.ESCAPE;
         return;
      }
      if (!ready) {
         this.K();
      }
      if (!this.E()) {
         if (CLIENT.player.age % 50 == 0) {
            CLIENT.player.networkHandler.sendChatCommand("home");
         }
      } else if (ready) {
         int speedSlot = this.e.isEnabled() && CLIENT.player.getStatusEffect(StatusEffects.SPEED) == null ? this.a(this::f) : -1;
         if (speedSlot < 0) {
            this.H();
         } else {
            this.useSlot(speedSlot);
         }
      }
   }

   private boolean A() {
      if (CLIENT.player.age < this.i) {
         for (Entity entity : CLIENT.world.getEntities()) {
            if (entity instanceof WardenEntity warden) {
               double distSq = CLIENT.player.squaredDistanceTo(warden);
               if (distSq < 900.0 && this.b(warden) && (distSq < 16.0 || this.a(warden))) {
                  return true;
               }
            }
         }
      }
      return false;
   }

   private boolean a(WardenEntity warden) {
      return (CLIENT.player.getX() - warden.getX()) * (warden.getX() - warden.lastX)
            + (CLIENT.player.getZ() - warden.getZ()) * (warden.getZ() - warden.lastZ)
         > 0.010000003841705648;
   }

   private boolean b(WardenEntity warden) {
      double yawToMe = Math.toDegrees(Math.atan2(-(CLIENT.player.getX() - warden.getX()), CLIENT.player.getZ() - warden.getZ()));
      return Math.abs((((((warden.getBodyYaw() - yawToMe) % 360.0) + 540.0) % 360.0) - 180.0)) < 10.0;
   }

   private void B() {
      if (this.u()) {
         this.o = AutoWarden.State.SAVE;
         return;
      }
      if (this.A() && inPvp()) {
         this.e(true);
         return;
      }
      BlockPos near = this.J();
      if (CLIENT.currentScreen instanceof GenericContainerScreen
         || near != null && this.remaining(near) < 0 && CLIENT.player.getEyePos().squaredDistanceTo(Vec3d.ofCenter(near)) <= 16.0) {
         this.H();
         return;
      }
      if (inPvp()) {
         if (this.D() >= 23 || this.b(2.0) || pvpSeconds() <= 16 || near == null) {
            this.e(true);
         } else {
            this.H();
         }
         return;
      }
      this.o = AutoWarden.State.SAVE;
   }

   private void e(boolean warden) {
      this.N();
      this.M();
      BlockPos best = null;
      double bestScore = -1.0;
      int y = CLIENT.player.getBlockPos().getY();
      for (int angle = 0; angle < 360; angle += 30) {
         int x = this.c((int)(CLIENT.player.getX() + Math.cos(Math.toRadians(angle)) * 25.0));
         int z = this.d((int)(CLIENT.player.getZ() + Math.sin(Math.toRadians(angle)) * 25.0));
         double score = this.a(x, z, warden);
         if (score > bestScore) {
            bestScore = score;
            best = new BlockPos(x, y, z);
         }
      }
      this.a(best);
   }

   private void a(BlockPos spot) {
      if (spot != null) {
         if (CLIENT.player.age % 10 == 0 || !this.L() && CLIENT.player.age % 5 == 0) {
            BaritoneAPI.getProvider()
               .getPrimaryBaritone()
               .getCustomGoalProcess()
               .setGoalAndPath(new GoalBlock(this.c(spot.getX()), spot.getY(), this.d(spot.getZ())));
         }
      }
   }

   private void C() {
      BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().cancelEverything();
   }

   private double a(int x, int z, boolean warden) {
      double min = Double.MAX_VALUE;
      for (Entity entity : CLIENT.world.getEntities()) {
         if (entity != CLIENT.player && (entity instanceof PlayerEntity || warden && entity instanceof WardenEntity)) {
            min = Math.min(min, Math.hypot(entity.getX() - x, entity.getZ() - z));
         }
      }
      return min;
   }

   private int D() {
      int n = 0;
      for (int slot = 0; slot < 36; slot++) {
         if (!CLIENT.player.getInventory().getStack(slot).isEmpty()) {
            n++;
         }
      }
      return n;
   }

   private boolean E() {
      return CLIENT.world.getRegistryKey().getValue().toString().equals("minecraft:overworld")
         && CLIENT.player.getX() <= -1921.0
         && CLIENT.player.getX() >= -2070.0
         && CLIENT.player.getZ() <= -1929.0
         && CLIENT.player.getZ() >= -2076.0;
   }

   private void F() {
      this.k = new Box(-2070.0, CLIENT.player.getBlockPos().getY(), -2076.0, -1921.0, CLIENT.player.getBlockPos().getY(), -1929.0);
   }

   private boolean G() {
      return this.k != null
         && CLIENT.player.getX() >= this.k.minX
         && CLIENT.player.getX() <= this.k.maxX
         && CLIENT.player.getZ() >= this.k.minZ
         && CLIENT.player.getZ() <= this.k.maxZ;
   }

   private int c(int x) {
      return this.k == null ? x : (int)Math.max(this.k.minX + 10.0, Math.min(this.k.maxX - 10.0, x));
   }

   private int d(int z) {
      return this.k == null ? z : (int)Math.max(this.k.minZ + 10.0, Math.min(this.k.maxZ - 10.0, z));
   }

   private void H() {
      if (CLIENT.currentScreen instanceof GenericContainerScreen screen) {
         this.a(screen);
         return;
      }
      BlockPos pick = this.J();
      if (pick == null) {
         pick = this.I();
      }
      boolean stay = pick != null && this.l != null && !pick.equals(this.l) && this.remaining(this.l) > 25000;
      if (!stay) {
         this.m.invoke();
      }
      if (!stay || this.m.check9(1000L)) {
         this.l = pick;
      }
      BlockPos target = this.l;
      if (target == null && CLIENT.player.age % 40 == 0) {
         this.o = AutoWarden.State.ESCAPE;
         this.j = true;
         return;
      }
      if (target == null) {
         return;
      }
      long remaining = this.remaining(target);
      if (remaining > 1000 && this.a(target, 7.0)) {
         BlockPos spot = this.c(target);
         if (spot != null) {
            if (CLIENT.player.squaredDistanceTo(Vec3d.ofCenter(spot)) > 2.0) {
               this.a(spot);
            } else {
               this.C();
            }
         }
         return;
      }
      if (remaining > 6000) {
         this.a(this.b(target));
         return;
      }
      double distSq = CLIENT.player.getEyePos().squaredDistanceTo(Vec3d.ofCenter(target));
      if (distSq <= 20.0) {
         if (this.c.getOrDefault(target, 0) < (remaining >= 0 ? 1 : 3)) {
            this.a(target, remaining >= 0 ? 6 : 1);
         }
         return;
      }
      if (distSq > 10.0) {
         this.M();
      }
      this.a(this.c(target));
   }

   private BlockPos b(BlockPos chest) {
      double angle = CLIENT.player.age / 40.0 * 2.4000011930854526;
      return new BlockPos(this.c(chest.getX() + (int)(Math.cos(angle) * 10.0)), chest.getY(), this.d(chest.getZ() + (int)(Math.sin(angle) * 10.0)));
   }

   private BlockPos I() {
      BlockPos best = null;
      long bestMs = 45000;
      for (BlockPos chest : this.chests()) {
         long remaining = this.remaining(chest);
         if (remaining >= 0 && remaining < bestMs && this.f(chest) && !this.d(chest) && !this.e(chest)) {
            bestMs = remaining;
            best = chest;
         }
      }
      return best;
   }

   private BlockPos J() {
      BlockPos best = null;
      int bestTier = 99;
      double bestSq = Double.MAX_VALUE;
      for (BlockPos chest : this.chests()) {
         if (!this.f(chest) || this.e(chest)) {
            continue;
         }
         double distSq = CLIENT.player.getEyePos().squaredDistanceTo(Vec3d.ofCenter(chest));
         long remaining = this.remaining(chest);
         if (this.d(chest) && (remaining >= 0 || distSq > 16.0)) {
            continue;
         }
         if (remaining < 0 && this.c.getOrDefault(chest, 0) >= 3) {
            continue;
         }
         int tier;
         if (remaining < 0 && distSq <= 25.0) {
            tier = 0;
         } else if (remaining >= 0 && remaining <= 5000 && distSq <= 144.0) {
            tier = 1;
         } else if (remaining < 0 && distSq <= 144.0) {
            tier = 2;
         } else if (remaining >= 0 && remaining <= 15000 && distSq <= 625.0) {
            tier = 3;
         } else if (remaining < 0) {
            tier = 4;
         } else {
            continue;
         }
         double up = Vec3d.ofCenter(chest).y - CLIENT.player.getY();
         double dx = chest.getX() + 0.5 - CLIENT.player.getX();
         double dz = chest.getZ() + 0.5 - CLIENT.player.getZ();
         double weightedSq = dx * dx + dz * dz + (up > 0.0 ? 2 : 1) * up * up;
         if (tier < bestTier || tier == bestTier && weightedSq < bestSq) {
            bestTier = tier;
            bestSq = weightedSq;
            best = chest;
         }
      }
      return best;
   }

   private boolean b(double range) {
      for (Entity entity : CLIENT.world.getEntities()) {
         if (entity instanceof PlayerEntity player && player != CLIENT.player && CLIENT.player.squaredDistanceTo(player) < range * range) {
            return true;
         }
      }
      return false;
   }

   private void a(GenericContainerScreen screen) {
      if (this.L()) {
         this.C();
         return;
      }
      if (CLIENT.player.age % 2 != 0) {
         return;
      }
      Slot slot = this.a(screen, false, stack -> !stack.isEmpty() && !this.b(stack));
      if (slot == null) {
         this.N();
      } else {
         this.a(screen, slot, 0, SlotActionType.QUICK_MOVE);
      }
   }

   private BlockPos c(BlockPos chest) {
      for (int dx = -1; dx <= 1; dx++) {
         for (int dz = -1; dz <= 1; dz++) {
            if (dx != 0 || dz != 0) {
               BlockPos side = chest.add(dx, 0, dz);
               if (CLIENT.world.getBlockState(side).isAir()
                  && CLIENT.world.getBlockState(side.up()).isAir()
                  && !CLIENT.world.getBlockState(side.down()).isAir()
                  && this.a(side, chest)) {
                  return side;
               }
            }
         }
      }
      if (CLIENT.world.getBlockState(chest.up()).isAir() && CLIENT.world.getBlockState(chest.up().up()).isAir() && this.a(chest.up(), chest)) {
         return chest.up();
      }
      return null;
   }

   private boolean a(BlockPos from, BlockPos chest) {
      return this.a(Vec3d.ofCenter(from).add(0.0, CLIENT.player.getStandingEyeHeight() - 0.5, 0.0), chest) != null;
   }

   private Vec3d a(Vec3d eye, BlockPos chest) {
      Vec3d center = Vec3d.ofCenter(chest);
      Vec3d best = null;
      double bestSq = Double.MAX_VALUE;
      for (double dx = -0.3999999563044224; dx <= 0.41000000193542635; dx += 0.4000000009895358) {
         for (double dy = -0.3999999563044224; dy <= 0.41000000193542635; dy += 0.4000000009895358) {
            for (double dz = -0.3999999563044224; dz <= 0.41000000193542635; dz += 0.4000000009895358) {
               Vec3d point = center.add(dx, dy, dz);
               double sq = point.squaredDistanceTo(center);
               BlockHitResult hit = CLIENT.world.raycast(new RaycastContext(eye, point, ShapeType.COLLIDER, FluidHandling.NONE, CLIENT.player));
               if (sq < bestSq && hit.getBlockPos().equals(chest)) {
                  bestSq = sq;
                  best = point;
               }
            }
         }
      }
      return best;
   }

   private boolean d(BlockPos pos) {
      for (BlockPos warden : this.d.keySet()) {
         if (warden.getSquaredDistance(pos) < 25.0) {
            return true;
         }
      }
      return false;
   }

   private boolean e(BlockPos pos) {
      for (Entity entity : CLIENT.world.getEntities()) {
         if (entity instanceof PlayerEntity player
            && player != CLIENT.player
            && player.getPos().squaredDistanceTo(Vec3d.ofCenter(pos)) < 20.0
            && Stream.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET)
               .anyMatch(slot -> !player.getEquippedStack(slot).isEmpty())) {
            return true;
         }
      }
      return false;
   }

   private boolean a(BlockPos pos, double range) {
      for (Entity entity : CLIENT.world.getEntities()) {
         if (entity instanceof PlayerEntity player && player != CLIENT.player && player.getPos().squaredDistanceTo(Vec3d.ofCenter(pos)) < range * range) {
            return true;
         }
      }
      return false;
   }

   private boolean f(BlockPos chest) {
      return this.c(chest) != null;
   }

   private void K() {
      int slot = this.a(this::e);
      if (slot >= 0 && CLIENT.player.age > 20) {
         this.useSlot(slot);
      }
   }

   private int a(Predicate<ItemStack> match) {
      for (int slot = 0; slot < 36; slot++) {
         if (match.test(CLIENT.player.getInventory().getStack(slot))) {
            return slot;
         }
      }
      return -1;
   }

   private void a(boolean active, boolean hopper, AutoWarden.State next) {
      if (!active) {
         if (this.N()) {
            this.o = next;
         }
         return;
      }
      if (CLIENT.currentScreen instanceof GenericContainerScreen screen) {
         if (!hopper) {
            this.c(screen);
         } else {
            this.b(screen);
         }
         return;
      }
      this.a(this.f(hopper), 2);
   }

   private boolean a(BlockPos chest, int rate) {
      if (chest == null || CLIENT.currentScreen instanceof GenericContainerScreen) {
         return false;
      }
      Vec3d eye = CLIENT.player.getEyePos();
      Vec3d aim = this.a(eye, chest);
      if (aim == null) {
         return false;
      }
      Rotation target = rotationTo(eye, aim);
      float t = CLIENT.player.age + CLIENT.getRenderTickCounter().getTickProgress(false);
      float sw = (float)((Math.sin(t * 0.31F) * 0.5 + Math.sin(t * 0.73F + 1.1F) * 0.3000000317022817 + Math.sin(t * 1.7F + 2.6F) * 0.1999999860971588) * 8.0);
      RotationController.invoke7(new Rotation(target.floatValue + sw, MathHelper.clamp(target.floatValue2 + sw / 4.0F, -90.0F, 90.0F)), 120.0F, 120.0F, 1, 1);
      if (CLIENT.player.age % rate != 0 || new Rotation(CLIENT.player).measure(target) > 5.0F) {
         return false;
      }
      BlockHitResult hit = CLIENT.world.raycast(new RaycastContext(eye, aim, ShapeType.COLLIDER, FluidHandling.NONE, CLIENT.player));
      if (!hit.getBlockPos().equals(chest)) {
         return false;
      }
      CLIENT.interactionManager.interactBlock(CLIENT.player, Hand.MAIN_HAND, hit);
      CLIENT.player.swingHand(Hand.MAIN_HAND);
      this.c.merge(chest, 1, Integer::sum);
      return true;
   }

   private boolean L() {
      return CLIENT.player.getVelocity().horizontalLengthSquared() > 0.002500001077917312;
   }

   private void M() {
      if (CLIENT.player.getMainHandStack().isEmpty()) {
         return;
      }
      for (int slot = 0; slot < 9; slot++) {
         if (CLIENT.player.getInventory().getStack(slot).isEmpty()) {
            CLIENT.player.getInventory().setSelectedSlot(slot);
            return;
         }
      }
      for (int slot = 9; slot < 36; slot++) {
         if (CLIENT.player.getInventory().getStack(slot).isEmpty()) {
            if (CLIENT.player.age % 10 >= 2 && this.L()) {
               this.C();
            }
            if (CLIENT.player.age % 10 == 4) {
               this.swapSlots(CLIENT.player.getInventory().getSelectedSlot(), slot);
            }
            return;
         }
      }
   }

   private boolean N() {
      if (CLIENT.currentScreen instanceof GenericContainerScreen && CLIENT.player.age % 2 == 0) {
         CLIENT.player.closeHandledScreen();
      }
      return !(CLIENT.currentScreen instanceof GenericContainerScreen);
   }

   private void b(GenericContainerScreen screen) {
      if (CLIENT.player.age % 2 != 0) {
         return;
      }
      boolean keepPotion = false;
      boolean keepCarrot = false;
      int moved = 0;
      for (Slot slot : screen.getScreenHandler().slots) {
         if (moved >= 4) {
            return;
         }
         ItemStack stack = slot.getStack();
         if (slot.inventory == CLIENT.player.getInventory() && !stack.isEmpty() && (!this.e.isEnabled() || !this.f(stack))) {
            if (!keepPotion && this.e(stack)) {
               keepPotion = true;
            } else if (keepCarrot || !stack.isOf(Items.GOLDEN_CARROT)) {
               this.a(screen, slot, 0, SlotActionType.QUICK_MOVE);
               moved++;
            } else {
               keepCarrot = true;
            }
         }
      }
   }

   private void c(GenericContainerScreen screen) {
      if (CLIENT.player.age % 2 != 0) {
         return;
      }
      ItemStack cursor = screen.getScreenHandler().getCursorStack();
      Predicate<ItemStack> same = stack -> stack.isEmpty() || ItemStack.areItemsAndComponentsEqual(stack, cursor);
      if (!cursor.isEmpty()) {
         if (!this.a(cursor)) {
            this.a(screen, this.a(screen, false, same), 0, SlotActionType.PICKUP);
         } else {
            this.a(screen, this.a(screen, true, same), 1, SlotActionType.PICKUP);
         }
         return;
      }
      this.a(screen, this.a(screen, false, this::a), 0, SlotActionType.PICKUP);
   }

   private Slot a(GenericContainerScreen screen, boolean player, Predicate<ItemStack> match) {
      for (Slot slot : screen.getScreenHandler().slots) {
         if ((slot.inventory == CLIENT.player.getInventory()) == player && match.test(slot.getStack())) {
            return slot;
         }
      }
      return null;
   }

   private void a(GenericContainerScreen screen, Slot slot, int button, SlotActionType type) {
      if (slot != null) {
         CLIENT.interactionManager.clickSlot(screen.getScreenHandler().syncId, slot.id, button, type, CLIENT.player);
      }
   }

   private boolean O() {
      return CLIENT.currentScreen instanceof GenericContainerScreen screen && !screen.getScreenHandler().getCursorStack().isEmpty();
   }

   private boolean a(ItemStack stack) {
      return !stack.isEmpty()
         && (
            this.e(stack) && this.Q() < 1
               || stack.isOf(Items.GOLDEN_CARROT) && countItem(Items.GOLDEN_CARROT) < 3
               || this.e.isEnabled() && this.f(stack) && this.a(this::f) < 0
         );
   }

   private boolean P() {
      return this.Q() < 1 || countItem(Items.GOLDEN_CARROT) < 3 || this.e.isEnabled() && this.a(this::f) < 0;
   }

   private int Q() {
      int total = 0;
      for (int slot = 0; slot < 36; slot++) {
         if (this.e(CLIENT.player.getInventory().getStack(slot))) {
            total++;
         }
      }
      return total;
   }

   private boolean b(ItemStack stack) {
      if (this.g.is("Низкий")) {
         return false;
      }
      return this.d(stack) || this.g.is("Высокий") && this.c(stack);
   }

   private boolean c(ItemStack stack) {
      Item item = stack.getItem();
      return isSword(item)
         || item instanceof ShovelItem
         || item instanceof AxeItem
         || stack.isOf(Items.CHORUS_FRUIT)
         || stack.isOf(Items.DISC_FRAGMENT_5)
         || stack.isOf(Items.NAUTILUS_SHELL)
         || stack.isOf(Items.BOOKSHELF)
         || stack.isOf(Items.COOKED_MUTTON)
         || stack.isOf(Items.SKELETON_SPAWN_EGG)
         || stack.isOf(Items.CREEPER_SPAWN_EGG)
         || stack.isOf(Items.ZOMBIE_SPAWN_EGG)
         || stack.isOf(Items.VINDICATOR_SPAWN_EGG)
         || stack.isOf(Items.PIGLIN_SPAWN_EGG)
         || stack.isOf(Items.FIRE_CHARGE)
         || stack.isOf(Items.LEATHER)
         || stack.isOf(Items.SHULKER_SHELL)
         || stack.isOf(Items.EXPERIENCE_BOTTLE)
         || stack.isOf(Items.WITHER_ROSE)
         || stack.isOf(Items.EMERALD)
         || stack.isOf(Items.SUGAR)
         || this.hasFtid(stack, "potion-popper")
         || stack.contains(DataComponentTypes.JUKEBOX_PLAYABLE)
         || stack.isOf(Items.GHAST_TEAR)
         || stack.isOf(Items.DRAGON_BREATH)
         || stack.isOf(Items.VEX_SPAWN_EGG)
         || stack.isOf(Items.ENDERMITE_SPAWN_EGG)
         || stack.isOf(Items.CAT_SPAWN_EGG)
         || stack.isOf(Items.ENCHANTING_TABLE)
         || stack.isOf(Items.DIAMOND_HELMET)
         || stack.isOf(Items.DIAMOND_CHESTPLATE)
         || stack.isOf(Items.DIAMOND_LEGGINGS)
         || stack.isOf(Items.DIAMOND_BOOTS);
   }

   private boolean d(ItemStack stack) {
      Item item = stack.getItem();
      return item instanceof ShearsItem
         || item instanceof AxeItem
         || item instanceof HoeItem
         || item instanceof SmithingTemplateItem && !stack.isOf(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE)
         || stack.isOf(Items.BLAZE_ROD)
         || stack.isOf(Items.ENCHANTED_BOOK)
         || stack.isOf(Items.TRIDENT)
         || stack.isOf(Items.NAME_TAG)
         || stack.isOf(Items.SCULK)
         || stack.isOf(Items.SCULK_SENSOR)
         || stack.isOf(Items.ENDER_CHEST)
         || stack.isOf(Items.REINFORCED_DEEPSLATE)
         || stack.isOf(Items.PUFFERFISH)
         || stack.isOf(Items.HONEY_BOTTLE)
         || stack.isOf(Items.FERMENTED_SPIDER_EYE)
         || stack.isOf(Items.ANVIL)
         || stack.isOf(Items.COOKED_PORKCHOP);
   }

   private boolean hasFtid(ItemStack stack, String id) {
      NbtComponent data = stack.get(DataComponentTypes.CUSTOM_DATA);
      if (data == null) {
         return false;
      }
      return data.copyNbt()
         .getCompound("PublicBukkitValues")
         .flatMap(compound -> compound.getString("minecraft:ftid"))
         .filter(id::equals)
         .isPresent();
   }

   private boolean e(ItemStack stack) {
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

   private boolean f(ItemStack stack) {
      if (!stack.isOf(Items.POTION)) {
         return false;
      }
      PotionContentsComponent contents = stack.get(DataComponentTypes.POTION_CONTENTS);
      if (contents == null) {
         return false;
      }
      for (StatusEffectInstance effect : contents.getEffects()) {
         if (effect.getEffectType().equals(StatusEffects.SPEED)) {
            return true;
         }
      }
      return false;
   }

   private BlockPos f(boolean hopper) {
      BlockPos origin = CLIENT.player.getBlockPos();
      BlockPos.Mutable pos = new BlockPos.Mutable();
      for (int dx = -4; dx <= 4; dx++) {
         for (int dy = -4; dy <= 4; dy++) {
            for (int dz = -4; dz <= 4; dz++) {
               pos.set(origin.getX() + dx, origin.getY() + dy, origin.getZ() + dz);
               if (CLIENT.world.getBlockState(pos).isOf(Blocks.CHEST)
                  && this.g(pos) == hopper
                  && CLIENT.world.getBlockState(pos.up()).isAir()
                  && CLIENT.world.raycast(new RaycastContext(CLIENT.player.getEyePos(), Vec3d.ofCenter(pos), ShapeType.COLLIDER, FluidHandling.NONE, CLIENT.player))
                     .getBlockPos()
                     .equals(pos)) {
                  return pos.toImmutable();
               }
            }
         }
      }
      return null;
   }

   private boolean g(BlockPos pos) {
      if (CLIENT.world.getBlockState(pos.down()).isOf(Blocks.HOPPER)) {
         return true;
      }
      BlockState state = CLIENT.world.getBlockState(pos);
      if (state.contains(ChestBlock.CHEST_TYPE) && state.get(ChestBlock.CHEST_TYPE) != ChestType.SINGLE) {
         for (Direction dir : Direction.Type.HORIZONTAL) {
            BlockPos partner = pos.offset(dir);
            BlockState ps = CLIENT.world.getBlockState(partner);
            if (ps.isOf(Blocks.CHEST)
               && ps.contains(ChestBlock.CHEST_TYPE)
               && ps.get(ChestBlock.CHEST_TYPE) != ChestType.SINGLE
               && ps.get(ChestBlock.CHEST_TYPE) != state.get(ChestBlock.CHEST_TYPE)
               && ps.get(ChestBlock.FACING) == state.get(ChestBlock.FACING)
               && CLIENT.world.getBlockState(partner.down()).isOf(Blocks.HOPPER)) {
               return true;
            }
         }
      }
      return false;
   }

   private boolean R() {
      boolean keepPotion = false;
      boolean keepCarrot = false;
      for (int slot = 0; slot < 36; slot++) {
         ItemStack stack = CLIENT.player.getInventory().getStack(slot);
         if (!stack.isEmpty() && (!this.e.isEnabled() || !this.f(stack))) {
            if (!keepPotion && this.e(stack)) {
               keepPotion = true;
            } else if (keepCarrot || !stack.isOf(Items.GOLDEN_CARROT)) {
               return true;
            } else {
               keepCarrot = true;
            }
         }
      }
      return false;
   }

   private List<BlockPos> chests() {
      List<BlockPos> result = new ArrayList<>();
      if (CLIENT.world == null || CLIENT.player == null) {
         return result;
      }
      ChunkPos chunk = new ChunkPos(CLIENT.player.getBlockPos());
      for (int dx = -6; dx <= 6; dx++) {
         for (int dz = -6; dz <= 6; dz++) {
            WorldChunk worldChunk = CLIENT.world.getChunk(chunk.x + dx, chunk.z + dz);
            if (worldChunk == null) {
               continue;
            }
            for (BlockPos pos : worldChunk.getBlockEntities().keySet()) {
               if (pos.getY() >= -60
                  && pos.getY() <= -35
                  && pos.getX() >= -2070
                  && pos.getX() <= -1921
                  && pos.getZ() >= -2076
                  && pos.getZ() <= -1929) {
                  BlockEntity blockEntity = CLIENT.world.getBlockEntity(pos);
                  if (blockEntity instanceof ChestBlockEntity || blockEntity instanceof BarrelBlockEntity) {
                     result.add(pos.toImmutable());
                  }
               }
            }
         }
      }
      return result;
   }

   private long remaining(BlockPos pos) {
      if (pos == null || CLIENT.world == null) {
         return -1L;
      }
      for (Entity entity : CLIENT.world.getEntities()) {
         if (entity instanceof ArmorStandEntity stand
            && stand.getBlockPos().getX() == pos.getX()
            && stand.getBlockPos().getZ() == pos.getZ()) {
            Matcher matcher = TIMER.matcher(stand.getName().getString());
            if (matcher.find()) {
               long ms = (Long.parseLong(matcher.group(1)) * 60L + Long.parseLong(matcher.group(2))) * 1000L;
               this.chestTimers.computeIfAbsent(pos.toImmutable(), key -> new ChestTimer()).update(ms, currentAnarchy());
               return ms;
            }
         }
      }
      ChestTimer timer = this.chestTimers.get(pos);
      return timer != null && timer.anarchy == currentAnarchy() ? timer.remaining() : -1L;
   }

   private void refreshChestTimers() {
      int anarchy = currentAnarchy();
      this.chestTimers.values().removeIf(timer -> timer.remaining() <= 0 || timer.anarchy != anarchy);
   }

   private void eatIfHungry(int level) {
      if (CLIENT.player.getHungerManager().getFoodLevel() >= level || this.isUsing()) {
         return;
      }
      int slot = this.a(stack -> stack.contains(DataComponentTypes.FOOD) && !stack.isOf(Items.PUFFERFISH) && !stack.isOf(Items.CHORUS_FRUIT) && !stack.isOf(Items.ROTTEN_FLESH));
      if (slot >= 0) {
         this.useSlot(slot);
      }
   }

   private void useSlot(int slot) {
      if (this.useTask == null && slot >= 0) {
         this.useTask = new AutoWarden.UseTask(CLIENT.player.getInventory().getSelectedSlot(), slot);
      }
   }

   private boolean isUsing() {
      return this.useTask != null;
   }

   private void tickUse() {
      if (this.useTask == null || CLIENT.player == null || CLIENT.interactionManager == null || CLIENT.player.age <= 40) {
         return;
      }
      AutoWarden.UseTask task = this.useTask;
      boolean inventory = task.itemSlot > 8;
      task.ticks++;
      if (task.ticks == 1) {
         if (inventory) {
            this.swapSlots(task.itemSlot, task.hotbar);
         } else {
            CLIENT.player.getInventory().setSelectedSlot(task.itemSlot);
         }
         return;
      }
      if (!task.started && task.ticks > 0) {
         if (CLIENT.player.isUsingItem()) {
            task.started = true;
         } else {
            CLIENT.interactionManager.interactItem(CLIENT.player, Hand.MAIN_HAND);
         }
         return;
      }
      if (task.started && !CLIENT.player.isUsingItem()) {
         if (inventory) {
            this.swapSlots(task.hotbar, task.itemSlot);
         } else {
            CLIENT.player.getInventory().setSelectedSlot(task.hotbar);
         }
         this.useTask = null;
         return;
      }
      if (task.ticks >= 60) {
         ChatUtil.sendClientMessage("Использование предмета не удалось по неизвестной причине");
         this.useTask = null;
      }
   }

   private void swapSlots(int from, int to) {
      int fromId = from < 9 ? from + 36 : from;
      int button = to < 9 ? to : from < 9 ? from : 0;
      CLIENT.interactionManager.clickSlot(CLIENT.player.playerScreenHandler.syncId, fromId, button, SlotActionType.SWAP, CLIENT.player);
   }

   private static int countItem(Item item) {
      int count = 0;
      for (int slot = 0; slot < 36; slot++) {
         ItemStack stack = CLIENT.player.getInventory().getStack(slot);
         if (stack.isOf(item)) {
            count += stack.getCount();
         }
      }
      return count;
   }

   private static int currentAnarchy() {
      ServerStatsParser.INSTANCE.invoke2();
      String value = ServerStatsParser.INSTANCE.getNA2();
      if (value == null || value.isBlank() || "N/A".equals(value)) {
         return -1;
      }
      try {
         return Integer.parseInt(value.replaceAll("[^0-9]", ""));
      } catch (NumberFormatException exception) {
         return -1;
      }
   }

   private static boolean inPvp() {
      return ServerStatsParser.check();
   }

   private static int pvpSeconds() {
      if (CLIENT.inGameHud == null || CLIENT.inGameHud.getBossBarHud() == null) {
         return -1;
      }
      Map<?, ClientBossBar> bars = ((BossBarHudAccessor)CLIENT.inGameHud.getBossBarHud()).getBossBars();
      for (ClientBossBar bar : bars.values()) {
         String name = bar.getName().getString().toLowerCase(Locale.ROOT);
         if (name.contains("pvp") || name.contains("пвп")) {
            Matcher matcher = Pattern.compile("(\\d+):(\\d+)").matcher(name);
            if (matcher.find()) {
               return Integer.parseInt(matcher.group(1)) * 60 + Integer.parseInt(matcher.group(2));
            }
            Matcher matcher2 = Pattern.compile("(\\d+)").matcher(name);
            if (matcher2.find()) {
               return Integer.parseInt(matcher2.group(1));
            }
         }
      }
      return -1;
   }

   private static Rotation rotationTo(Vec3d eye, Vec3d point) {
      Vec3d diff = point.subtract(eye);
      double dist = Math.sqrt(diff.x * diff.x + diff.z * diff.z);
      float yaw = (float)Math.toDegrees(Math.atan2(diff.z, diff.x)) - 90.0F;
      float pitch = (float)(-Math.toDegrees(Math.atan2(diff.y, dist)));
      return new Rotation(MathHelper.wrapDegrees(yaw), MathHelper.clamp(pitch, -90.0F, 90.0F));
   }

   private static boolean isSword(Item item) {
      return item == Items.WOODEN_SWORD
         || item == Items.STONE_SWORD
         || item == Items.IRON_SWORD
         || item == Items.GOLDEN_SWORD
         || item == Items.DIAMOND_SWORD
         || item == Items.NETHERITE_SWORD;
   }

   private static boolean isSculk(BlockState state) {
      Block block = state.getBlock();
      return block == Blocks.SCULK
         || block == Blocks.SCULK_VEIN
         || block == Blocks.SCULK_SENSOR
         || block == Blocks.SCULK_SHRIEKER
         || block == Blocks.SCULK_CATALYST
         || block == Blocks.CALIBRATED_SCULK_SENSOR;
   }

   private static float random(float min, float max) {
      return (float)(Math.random() * (max - min) + min);
   }

   private static final class UseTask {
      private final int hotbar;
      private final int itemSlot;
      private boolean started;
      private int ticks;

      private UseTask(int hotbar, int itemSlot) {
         this.hotbar = hotbar;
         this.itemSlot = itemSlot;
      }
   }

   private static final class ChestTimer {
      private long ms;
      private long started = System.currentTimeMillis();
      private int anarchy = currentAnarchy();

      private void update(long current, int currentAnarchy) {
         if (Math.abs(current / 1000L - this.remaining() / 1000L) > 5L) {
            this.ms = current;
            this.started = System.currentTimeMillis();
            this.anarchy = currentAnarchy;
         }
      }

      private long remaining() {
         return Math.max(0L, this.ms - (System.currentTimeMillis() - this.started));
      }
   }
}
