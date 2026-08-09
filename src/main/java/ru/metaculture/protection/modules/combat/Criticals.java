package ru.metaculture.protection;

import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.Full;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos.Mutable;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "Criticals",
   description = "Критический удар под плавным падением или в паутине",
   category = Category.Combat,
   riskLevels = {ModuleRiskLevel.RISKY, ModuleRiskLevel.GRIM}
)
public class Criticals extends Module {
   public static boolean flag;
   public final GroupSetting usloviya = new GroupSetting("Условия", new BooleanSetting("Паутина", true), new BooleanSetting("Плавное падение", true));

   public Criticals() {
      this.addSettings(new Setting[]{this.usloviya});
   }

   @EventHandler
   public void onAttackEntity(AttackEntityEvent attackEntityEvent) {
      if (!flag) {
         if (!ServerBlockUtils.check() && CLIENT.player != null && CLIENT.world != null) {
            if (CLIENT.player.networkHandler != null) {
               if (!CLIENT.player.isGliding()) {
                  Entity entity = attackEntityEvent.getEntity();
                  if (entity != null && entity != CLIENT.player && !(entity instanceof EndCrystalEntity)) {
                     boolean flag = this.usloviya.isEnabled("Паутина") && this.check();
                     boolean flag2 = this.usloviya.isEnabled("Плавное падение") && CLIENT.player.hasStatusEffect(StatusEffects.SLOW_FALLING);
                     if (flag || flag2) {
                        float floatValue = MathHelper.lerp(ThreadLocalRandom.current().nextFloat(), 1.0E-7F, 1.0E-6F);
                        CLIENT.player.fallDistance = floatValue;
                        CLIENT.player
                           .networkHandler
                           .sendPacket(
                              new Full(
                                 CLIENT.player.getX(),
                                 CLIENT.player.getY() - floatValue,
                                 CLIENT.player.getZ(),
                                 CLIENT.player.getYaw(),
                                 CLIENT.player.getPitch(),
                                 false,
                                 CLIENT.player.horizontalCollision
                              )
                           );
                     }
                  }
               }
            }
         }
      }
   }

   private boolean check() {
      Box box = CLIENT.player.getBoundingBox().contract(1.0E-7);
      int intValue = MathHelper.floor(box.minX);
      int intValue2 = MathHelper.floor(box.maxX);
      int intValue3 = MathHelper.floor(box.minY);
      int intValue4 = MathHelper.floor(box.maxY);
      int intValue5 = MathHelper.floor(box.minZ);
      int intValue6 = MathHelper.floor(box.maxZ);
      Mutable mutable = new Mutable();

      for (int intValue7 = intValue; intValue7 <= intValue2; intValue7++) {
         for (int intValue8 = intValue3; intValue8 <= intValue4; intValue8++) {
            for (int intValue9 = intValue5; intValue9 <= intValue6; intValue9++) {
               mutable.set(intValue7, intValue8, intValue9);
               if (CLIENT.world.getBlockState(mutable).isOf(Blocks.COBWEB)) {
                  return true;
               }
            }
         }
      }

      return false;
   }
}
