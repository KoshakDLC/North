package ru.metaculture.protection;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.Settings;
import baritone.api.pathing.goals.GoalNear;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.option.Perspective;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkDeltaUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleAccess(
   usernames = {"lichoday"}
)
@ModuleRegister(
   name = "AutoAncientBot",
   description = "Автоматический фарм древних обломков через ТНТ",
   category = Category.Misc
)
public class AutoAncientBot extends Module {
   private final BooleanSetting logiVChat = new BooleanSetting("Логи в чат", true);
   private final BooleanSetting pyorki = new BooleanSetting("Пёрки", true);
   private final NumberSetting minDistantsiyaPyorki = new NumberSetting("Мин. дистанция пёрки", 16.0F, 8.0F, 48.0F, 1.0F, false)
      .setVisibilityCondition(() -> !this.pyorki.isEnabled());
   private final BooleanSetting debug = new BooleanSetting("Debug", false);
   private static final int INT_VALUE = 26;
   private static final int INT_VALUE_2 = 6;
   private static final int INT_VALUE_3 = 2;
   private static final int INT_VALUE_4 = 36;
   private static final int INT_VALUE_5 = 36;
   private static final int INT_VALUE_6 = 18000;
   private static final int INT_VALUE_7 = 2500;
   private static final int INT_VALUE_8 = 2500;
   private static final int INT_VALUE_9 = 16;
   private static final int INT_VALUE_10 = 19;
   private static final int INT_VALUE_11 = 7000;
   private static final int INT_VALUE_12 = 300;
   private static final int INT_VALUE_13 = 3500;
   private static final int INT_VALUE_14 = 2;
   private static final int INT_VALUE_15 = 22000;
   private static final int INT_VALUE_16 = 2;
   private static final double DOUBLE_VALUE = 4.2;
   private static final float FLOAT_VALUE = 4.0F;
   private static final float FLOAT_VALUE_2 = 140.0F;
   private static final float FLOAT_VALUE_3 = 34.0F;
   private static final float FLOAT_VALUE_4 = 1.35F;
   private static final long TIMESTAMP = 90L;
   private static final long TIMESTAMP_2 = 3000L;
   private static final long TIMESTAMP_3 = 9000L;
   private static final int INT_VALUE_17 = 2;
   private static final int INT_VALUE_18 = 4;
   private static final int INT_VALUE_19 = 900;
   private static final int INT_VALUE_20 = 900;
   private static final int INT_VALUE_21 = 1400;
   private static final int INT_VALUE_22 = 8000;
   private static final int INT_VALUE_23 = 1400;
   private static final double DOUBLE_VALUE_2 = 9.0;
   private static final int INT_VALUE_24 = 2500;
   private static final int INT_VALUE_25 = 3;
   private static final double DOUBLE_VALUE_3 = 0.03;
   private static final double DOUBLE_VALUE_4 = 0.99;
   private static final double DOUBLE_VALUE_5 = 1.5;
   private static final int INT_VALUE_26 = 160;
   private static final double DOUBLE_VALUE_6 = 1.8;
   private static final double DOUBLE_VALUE_7 = 27.0;
   private static final int INT_VALUE_27 = 2500;
   private static final int INT_VALUE_28 = 1200;
   private static final int INT_VALUE_29 = 2000;
   private static final int INT_VALUE_30 = 5000;
   private static final double DOUBLE_VALUE_8 = 25.0;
   private static final int INT_VALUE_31 = 3500;
   private static final float FLOAT_VALUE_5 = 8.0F;
   private AutoAncientBot.AutoAncientBotState4 autoAncientBotState4 = AutoAncientBot.AutoAncientBotState4.SEARCHING;
   private AutoAncientBot.AutoAncientBotState2 autoAncientBotState2 = AutoAncientBot.AutoAncientBotState2.APPROACHING;
   private final DualTimer dualTimer = new DualTimer();
   private final DualTimer dualTimer2 = new DualTimer();
   private final DualTimer dualTimer3 = new DualTimer();
   private final DualTimer dualTimer4 = new DualTimer();
   private final DualTimer dualTimer5 = new DualTimer();
   private final DualTimer dualTimer6 = new DualTimer();
   private final DualTimer dualTimer7 = new DualTimer();
   private final DualTimer dualTimer8 = new DualTimer();
   private final DualTimer dualTimer9 = new DualTimer();
   private final DualTimer dualTimer10 = new DualTimer();
   private final DualTimer dualTimer11 = new DualTimer();
   private final DualTimer dualTimer12 = new DualTimer();
   private final DualTimer dualTimer13 = new DualTimer();
   private final DualTimer dualTimer14 = new DualTimer();
   private final DualTimer dualTimer15 = new DualTimer();
   private final Set<BlockPos> values = new HashSet<>();
   private final Set<BlockPos> values2 = new HashSet<>();
   private final Map<BlockPos, Boolean> valuesByKey = new HashMap<>();
   private BlockPos blockPos;
   private BlockPos blockPos2;
   private BlockPos blockPos3;
   private BlockPos blockPos4;
   private BlockPos blockPos5;
   private BlockPos blockPos6;
   private BlockPos blockPos7;
   private BlockPos blockPos8;
   private boolean flag;
   private boolean flag2;
   private boolean flag3;
   private boolean flag4;
   private boolean flag5;
   private boolean flag6;
   private boolean flag7;
   private List<Block> items = List.of();
   private List<Item> items2 = List.of();
   private double doubleValue;
   private int intValue;
   private int intValue2;
   private int intValue3;
   private int intValue4;
   private BlockPos blockPos9;
   private int intValue5 = -1;
   private int intValue6 = -1;
   private int intValue7 = -1;
   private int intValue8 = -1;
   private boolean flag8;
   private boolean flag9;
   private boolean flag10;
   private boolean flag11;
   private boolean flag12;
   private boolean flag13;
   private boolean DynamicButtonSetting;
   private Vec3d vec3d;
   private AutoAncientBot.AutoAncientBotState3 autoAncientBotState3 = AutoAncientBot.AutoAncientBotState3.IDLE;
   private Vec3d vec3d2;
   private Vec3d vec3d3;
   private String SpacerSetting;
   private float FoundryShaderSetting;
   private float floatValue;
   private int intValue9 = -1;
   private int intValue10;
   private Vec3d vec3d4;
   private boolean flag14;
   private int intValue11;
   private int intValue12;
   private int intValue13;
   private int intValue14;
   private int intValue15;
   private long timestamp;

   public AutoAncientBot() {
      this.addSettings(new Setting[]{this.logiVChat, this.pyorki, this.minDistantsiyaPyorki, this.debug});
   }

   @Override
   public void onEnable() {
      if (CLIENT.player != null && CLIENT.world != null) {
         AttackAura attackAura = WildClient.INSTANCE.moduleManager.getModule(AttackAura.class);
         if (attackAura != null && attackAura.enabled) {
            ChatUtil.sendClientMessage("[AutoAncient] Disable HitAura first.");
            this.toggle();
         } else if (this.compute6(Blocks.TNT.asItem()) != -1 && this.compute6(Items.FLINT_AND_STEEL) != -1) {
            super.onEnable();
            if (CLIENT.options != null) {
               CLIENT.options.setPerspective(Perspective.FIRST_PERSON);
            }

            AncientXray ancientXray = this.resolve14();
            if (ancientXray != null) {
               ancientXray.invoke2();
            }

            this.invoke39();
            this.values.clear();
            this.values2.clear();
            this.valuesByKey.clear();
            this.blockPos6 = null;
            this.blockPos7 = null;
            this.blockPos8 = null;
            this.blockPos = null;
            this.blockPos2 = null;
            this.blockPos3 = null;
            this.blockPos4 = null;
            this.blockPos5 = null;
            this.flag = false;
            this.intValue = 0;
            this.intValue5 = -1;
            this.intValue6 = -1;
            this.intValue7 = -1;
            this.intValue8 = -1;
            this.autoAncientBotState2 = AutoAncientBot.AutoAncientBotState2.APPROACHING;
            this.flag11 = false;
            this.vec3d = null;
            this.dualTimer7.invoke();
            this.dualTimer8.invoke();
            this.dualTimer9.invoke();
            this.dualTimer10.invoke();
            this.dualTimer11.invoke();
            this.flag8 = false;
            this.flag9 = false;
            this.flag10 = false;
            this.flag12 = false;
            this.flag13 = false;
            this.DynamicButtonSetting = false;
            this.flag2 = false;
            this.autoAncientBotState3 = AutoAncientBot.AutoAncientBotState3.IDLE;
            this.vec3d2 = null;
            this.vec3d3 = null;
            this.SpacerSetting = null;
            this.intValue9 = -1;
            this.intValue10 = 0;
            this.vec3d4 = null;
            this.dualTimer13.invoke();
            this.dualTimer14.invoke();
            this.dualTimer15.invoke();
            this.dualTimer2.invoke();
            this.dualTimer3.invoke();
            this.intValue3 = 0;
            this.flag14 = false;
            this.intValue11 = 0;
            this.intValue12 = 0;
            this.intValue13 = 0;
            this.intValue14 = 0;
            this.intValue15 = 0;
            this.timestamp = System.currentTimeMillis();
            this.doubleValue = Math.toRadians(CLIENT.player.getYaw()) + (Math.PI / 2);
            this.invoke33(AutoAncientBot.AutoAncientBotState4.SEARCHING);
            this.invoke35("Запущен. ТНТ: " + this.compute7(Blocks.TNT.asItem()) + ", пёрок: " + this.compute7(Items.ENDER_PEARL));
            if (this.pyorki.isEnabled() && this.compute6(Items.ENDER_PEARL) == -1) {
               this.invoke35("Пёрок в хотбаре нет — броски работать не будут.");
            }

            FreeLookController.flag = true;
            FreeLookController.active = true;
            FreeLookController.floatValue = CLIENT.player.getYaw();
            FreeLookController.floatValue2 = CLIENT.player.getPitch();
            this.invoke34("enabled");
         } else {
            ChatUtil.sendClientMessage("[AutoAncient] TNT and flint must be in hotbar.");
            this.toggle();
         }
      } else {
         this.toggle();
      }
   }

   @Override
   public void onDisable() {
      RotationController.rotationControllerState2 = RotationController.RotationControllerState2.IDLE;
      RotationController.intValue = 0;
      RotationController.rotation = null;
      FreeLookController.flag = false;
      FreeLookController.active = false;
      super.onDisable();
      IBaritone iBaritone2 = BaritoneAPI.getProvider().getPrimaryBaritone();
      this.invoke26(iBaritone2);
      this.invoke27(iBaritone2);
      this.invoke28();
      this.invoke38();
      this.invoke25();
      if (CLIENT.options != null) {
         CLIENT.options.useKey.setPressed(false);
         CLIENT.options.attackKey.setPressed(false);
         CLIENT.options.jumpKey.setPressed(false);
      }

      iBaritone2.getMineProcess().cancel();
      iBaritone2.getBuilderProcess().onLostControl();
      this.invoke22();
      iBaritone2.getSelectionManager().removeAllSelections();
      this.blockPos = null;
      if (this.flag2) {
         iBaritone2.getCommandManager().execute("resume");
         this.flag2 = false;
      }

      this.invoke40();
      if (this.timestamp > 0L) {
         long longValue = Math.max(1L, (System.currentTimeMillis() - this.timestamp) / 1000L);
         this.invoke35(
            "Итог: обломков "
               + this.intValue13
               + ", ТНТ "
               + this.intValue14
               + " (съедено "
               + this.intValue12
               + "), пёрок "
               + this.intValue15
               + ", время "
               + longValue / 60L
               + " мин "
               + longValue % 60L
               + " с"
         );
         this.timestamp = 0L;
      }

      this.invoke34("disabled");
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null && CLIENT.interactionManager != null) {
         AncientXray ancientXray2 = this.resolve14();
         if (ancientXray2 == null) {
            ChatUtil.sendClientMessage("[AutoAncient] AncientXray module is not registered.");
            this.toggle();
         } else {
            if (!ancientXray2.enabled) {
               ancientXray2.invoke();
            }

            IBaritone iBaritone3 = BaritoneAPI.getProvider().getPrimaryBaritone();
            if (!this.check11(iBaritone3)) {
               if (!this.check31(iBaritone3)) {
                  if (!this.check12(iBaritone3)) {
                     if (!this.check17(iBaritone3)) {
                        if (!this.check15(iBaritone3)) {
                           if (this.autoAncientBotState4 != AutoAncientBot.AutoAncientBotState4.MINING
                              && this.autoAncientBotState4 != AutoAncientBot.AutoAncientBotState4.PLACING_TNT
                              && this.autoAncientBotState4 != AutoAncientBot.AutoAncientBotState4.IGNITING_TNT
                              && this.autoAncientBotState4 != AutoAncientBot.AutoAncientBotState4.WAITING_EXPLOSION) {
                              BlockPos blockPos3 = this.resolve4(ancientXray2);
                              if (blockPos3 != null) {
                                 this.invoke22();
                                 this.invoke16(blockPos3);
                                 return;
                              }
                           }

                           switch (this.autoAncientBotState4) {
                              case SEARCHING:
                                 this.invoke(iBaritone3);
                                 break;
                              case MOVING_SEARCH:
                                 this.invoke2(iBaritone3);
                                 break;
                              case MOVING_SITE:
                                 this.invoke3(iBaritone3);
                                 break;
                              case CLEARING_SITE:
                                 this.invoke4(iBaritone3);
                                 break;
                              case PLACING_TNT:
                                 this.invoke5(iBaritone3);
                                 break;
                              case IGNITING_TNT:
                                 this.invoke6(iBaritone3);
                                 break;
                              case WAITING_EXPLOSION:
                                 this.invoke7();
                                 break;
                              case WAITING_SCAN:
                                 this.invoke8(ancientXray2);
                                 break;
                              case MINING:
                                 this.invoke9(iBaritone3, ancientXray2);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (CLIENT.world != null) {
         AncientXray ancientXray3 = this.resolve14();
         if (ancientXray3 != null) {
            if (packetEvent.getPacket() instanceof ExplosionS2CPacket explosionS2CPacket) {
               BlockPos blockPos4 = BlockPos.ofFloored(explosionS2CPacket.center());
               if (this.check25(blockPos4)) {
                  this.flag = true;
                  this.blockPos5 = blockPos4;
                  this.values.clear();
                  this.valuesByKey.clear();
                  this.values2.add(blockPos4.toImmutable());
                  this.flag11 = false;
                  if (!ancientXray3.enabled) {
                     ancientXray3.invoke3(blockPos4, 10);
                  }

                  if (this.autoAncientBotState4 == AutoAncientBot.AutoAncientBotState4.WAITING_EXPLOSION) {
                     this.invoke35("Взрыв! Сканирую обломки...");
                  }

                  if (this.autoAncientBotState4 == AutoAncientBot.AutoAncientBotState4.WAITING_EXPLOSION || this.autoAncientBotState4 == AutoAncientBot.AutoAncientBotState4.WAITING_SCAN) {
                     this.invoke33(AutoAncientBot.AutoAncientBotState4.WAITING_SCAN);
                  }
               }
            } else if (packetEvent.getPacket() instanceof BlockUpdateS2CPacket blockUpdateS2CPacket) {
               if (this.blockPos4 != null && blockUpdateS2CPacket.getPos().equals(this.blockPos4) && blockUpdateS2CPacket.getState().isOf(Blocks.TNT)) {
                  this.flag14 = true;
               }

               if (!ancientXray3.enabled) {
                  ancientXray3.invoke4(blockUpdateS2CPacket.getPos(), blockUpdateS2CPacket.getState().getBlock());
               }
            } else if (packetEvent.getPacket() instanceof ChunkDeltaUpdateS2CPacket chunkDeltaUpdateS2CPacket) {
               chunkDeltaUpdateS2CPacket.visitUpdates((blockPos, blockState) -> {
                  if (this.blockPos4 != null && blockPos.equals(this.blockPos4) && blockState.isOf(Blocks.TNT)) {
                     this.flag14 = true;
                  }

                  if (!ancientXray3.enabled) {
                     ancientXray3.invoke4(blockPos, blockState.getBlock());
                  }
               });
            }
         }
      }
   }

   private void invoke(IBaritone iBaritone) {
      this.invoke25();
      BlockPos blockPos5 = this.resolve5();
      if (blockPos5 != null) {
         this.blockPos3 = blockPos5;
         this.invoke31(iBaritone, blockPos5);
         this.invoke33(AutoAncientBot.AutoAncientBotState4.MOVING_SITE);
         this.invoke35("Место для взрыва: " + blockPos5.toShortString());
      } else {
         this.invoke20(iBaritone);
      }
   }

   private void invoke2(IBaritone iBaritone) {
      if (!this.check32(this.blockPos2, "лечу к зоне поиска")) {
         if (this.check23(iBaritone)) {
            this.invoke30(iBaritone);
         } else {
            if (this.blockPos2 == null
               || this.check26(this.blockPos2, 9.0)
               || this.dualTimer.check5(18000L)
               || !this.check9(iBaritone) && this.dualTimer.check5(2500L)) {
               this.invoke22();
               this.invoke33(AutoAncientBot.AutoAncientBotState4.SEARCHING);
            }
         }
      }
   }

   private void invoke3(IBaritone iBaritone) {
      if (this.blockPos3 == null) {
         this.invoke33(AutoAncientBot.AutoAncientBotState4.SEARCHING);
      } else if (!this.check32(this.blockPos3, "лечу к месту взрыва")) {
         if (this.check23(iBaritone)) {
            this.invoke30(iBaritone);
         } else if (this.dualTimer.check5(22000L)) {
            this.intValue++;
            this.invoke33(AutoAncientBot.AutoAncientBotState4.SEARCHING);
         } else {
            if (!this.check26(this.blockPos3, 10.0)) {
               if (this.check9(iBaritone)) {
                  return;
               }

               if (!this.dualTimer.check5(2500L)) {
                  return;
               }
            }

            this.invoke22();
            if (!this.check2(this.blockPos3)) {
               this.intValue++;
               this.invoke33(AutoAncientBot.AutoAncientBotState4.SEARCHING);
            } else {
               this.blockPos4 = this.resolve6(this.blockPos3);
               if (this.blockPos4 == null) {
                  this.intValue++;
                  this.invoke33(AutoAncientBot.AutoAncientBotState4.SEARCHING);
               } else {
                  this.intValue11 = 0;
                  this.flag14 = false;
                  this.blockPos7 = null;
                  this.invoke33(AutoAncientBot.AutoAncientBotState4.CLEARING_SITE);
                  this.invoke35("Расчищаю площадку: " + this.blockPos4.toShortString());
               }
            }
         }
      }
   }

   private void invoke4(IBaritone iBaritone) {
      if (this.blockPos4 == null) {
         this.invoke33(AutoAncientBot.AutoAncientBotState4.SEARCHING);
      } else if (this.check5(this.blockPos4)) {
         this.invoke22();
         this.blockPos7 = null;
         this.invoke33(AutoAncientBot.AutoAncientBotState4.PLACING_TNT);
      } else if (this.dualTimer.check5(14000L)) {
         this.intValue++;
         this.invoke22();
         this.invoke33(AutoAncientBot.AutoAncientBotState4.SEARCHING);
      } else {
         BlockPos blockPos6 = this.resolve(this.blockPos4);
         if (blockPos6 == null) {
            this.intValue++;
            this.invoke22();
            this.invoke33(AutoAncientBot.AutoAncientBotState4.SEARCHING);
         } else {
            BlockHitResult blockHitResult = this.resolve10(blockPos6);
            if (blockHitResult == null) {
               if (this.check23(iBaritone)) {
                  this.invoke30(iBaritone);
               } else {
                  this.invoke32(iBaritone, this.blockPos4);
               }
            } else {
               AutoAncientBot.AutoAncientBotState autoAncientBotState = this.resolve7(blockHitResult.getBlockPos(), 3000L);
               if (autoAncientBotState == AutoAncientBot.AutoAncientBotState.STUCK) {
                  this.intValue++;
                  this.invoke33(AutoAncientBot.AutoAncientBotState4.SEARCHING);
               } else {
                  if (autoAncientBotState == AutoAncientBot.AutoAncientBotState.NO_REACH) {
                     this.invoke32(iBaritone, this.blockPos4);
                  }
               }
            }
         }
      }
   }

   private BlockPos resolve(BlockPos blockPos) {
      if (!CLIENT.world.getBlockState(blockPos).isReplaceable()) {
         return blockPos;
      } else {
         return !CLIENT.world.getBlockState(blockPos.up()).isReplaceable() ? blockPos.up() : null;
      }
   }

   private void invoke5(IBaritone iBaritone) {
      if (this.blockPos4 == null) {
         this.invoke33(AutoAncientBot.AutoAncientBotState4.SEARCHING);
      } else if (CLIENT.world.getBlockState(this.blockPos4).isOf(Blocks.TNT)) {
         if (this.flag14) {
            if (this.dualTimer5.check5(1400L)) {
               this.invoke33(AutoAncientBot.AutoAncientBotState4.IGNITING_TNT);
            }
         } else {
            if (this.dualTimer5.check5(2500L)) {
               this.intValue12++;
               this.intValue11++;
               this.invoke35("Сервер съел ТНТ — переставляю (#" + this.intValue12 + ")");
               this.invoke18(this.blockPos4, 0);
               if (this.intValue11 >= 3) {
                  this.invoke35("ТНТ пропадает на этом месте — ищу другое");
                  this.intValue++;
                  this.invoke33(AutoAncientBot.AutoAncientBotState4.SEARCHING);
                  return;
               }

               this.dualTimer.invoke();
               this.dualTimer5.invoke();
            }
         }
      } else if (!this.check24(this.blockPos4)) {
         if (this.check23(iBaritone)) {
            this.invoke30(iBaritone);
         } else {
            this.invoke32(iBaritone, this.blockPos4);
         }
      } else {
         this.invoke22();
         if (!this.check5(this.blockPos4)) {
            this.intValue++;
            this.invoke33(AutoAncientBot.AutoAncientBotState4.SEARCHING);
         } else {
            Vec3d vec3d3 = new Vec3d(this.blockPos4.getX() + 0.5, this.blockPos4.getY(), this.blockPos4.getZ() + 0.5);
            Rotation rotation = this.resolve8(vec3d3);
            this.invoke24(rotation);
            if (!(new Rotation(CLIENT.player).measure(rotation) > 4.0F)) {
               if (this.dualTimer5.check5(900L)) {
                  if (!this.check7(this.blockPos4)) {
                     if (this.check6()) {
                        return;
                     }

                     this.toggle();
                     return;
                  }

                  this.dualTimer5.invoke();
               }

               if (this.dualTimer.check5(8000L)) {
                  this.intValue++;
                  this.invoke33(AutoAncientBot.AutoAncientBotState4.SEARCHING);
               }
            }
         }
      }
   }

   private void invoke6(IBaritone iBaritone) {
      if (this.blockPos4 == null) {
         this.invoke33(AutoAncientBot.AutoAncientBotState4.SEARCHING);
      } else if (!CLIENT.world.getBlockState(this.blockPos4).isOf(Blocks.TNT)) {
         if (this.dualTimer.check5(600L)) {
            this.invoke22();
            this.flag = false;
            this.invoke33(AutoAncientBot.AutoAncientBotState4.WAITING_EXPLOSION);
         }
      } else if (!this.check24(this.blockPos4)) {
         if (this.check23(iBaritone)) {
            this.invoke30(iBaritone);
         } else {
            this.invoke32(iBaritone, this.blockPos4);
         }
      } else {
         this.invoke22();
         BlockHitResult blockHitResult2 = this.resolve12(this.blockPos4);
         if (blockHitResult2 != null) {
            Rotation rotation2 = this.resolve8(blockHitResult2.getPos());
            this.invoke24(rotation2);
            if (!(new Rotation(CLIENT.player).measure(rotation2) > 4.0F)) {
               if (this.dualTimer.check5(1400L)) {
                  if (this.dualTimer5.check5(900L)) {
                     if (!this.check8(this.blockPos4)) {
                        this.intValue++;
                        this.invoke33(AutoAncientBot.AutoAncientBotState4.SEARCHING);
                        return;
                     }

                     this.flag = false;
                     this.intValue14++;
                     this.invoke22();
                     this.invoke33(AutoAncientBot.AutoAncientBotState4.WAITING_EXPLOSION);
                     this.invoke35("Поджёг ТНТ #" + this.intValue14 + " (" + this.blockPos4.toShortString() + ")");
                  }
               }
            }
         }
      }
   }

   private void invoke7() {
      if (!this.flag
         && this.blockPos4 != null
         && CLIENT.world.getBlockState(this.blockPos4).isOf(Blocks.TNT)
         && this.dualTimer.check5(1800L)) {
         this.invoke33(AutoAncientBot.AutoAncientBotState4.IGNITING_TNT);
      } else {
         if (this.flag || this.dualTimer.check5(6500L)) {
            this.invoke33(AutoAncientBot.AutoAncientBotState4.WAITING_SCAN);
         }
      }
   }

   private void invoke8(AncientXray ancientXray4) {
      if (this.dualTimer.check5(1200L)) {
         BlockPos blockPos7 = this.resolve4(ancientXray4);
         if (blockPos7 != null) {
            this.invoke16(blockPos7);
         } else if (this.compute6(Blocks.TNT.asItem()) == -1 && this.blockPos5 != null && !this.flag11) {
            ancientXray4.invoke3(this.blockPos5, 2);
            this.flag11 = true;
            this.invoke35("ТНТ закончилась — финальный скан вокруг взрыва");
            this.dualTimer.invoke();
         } else {
            if (this.dualTimer.check5(4500L)) {
               this.invoke35("Обломков рядом нет — ищу новое место");
               this.blockPos6 = null;
               this.blockPos7 = null;
               this.autoAncientBotState2 = AutoAncientBot.AutoAncientBotState2.APPROACHING;
               this.invoke33(AutoAncientBot.AutoAncientBotState4.SEARCHING);
               this.invoke20(BaritoneAPI.getProvider().getPrimaryBaritone());
            }
         }
      }
   }

   private void invoke9(IBaritone iBaritone, AncientXray ancientXray5) {
      if (this.autoAncientBotState2 != AutoAncientBot.AutoAncientBotState2.BREAKING) {
         this.invoke25();
      }

      List items = this.resolve2(ancientXray5);
      if (items.isEmpty()) {
         this.invoke11(iBaritone);
         this.invoke33(AutoAncientBot.AutoAncientBotState4.SEARCHING);
         this.invoke20(iBaritone);
      } else if (this.blockPos6 != null && !items.contains(this.blockPos6)) {
         this.invoke36();
         this.invoke17(ancientXray5);
      } else if (this.blockPos6 != null && !CLIENT.world.getBlockState(this.blockPos6).isOf(Blocks.ANCIENT_DEBRIS)) {
         this.invoke36();
         this.invoke17(ancientXray5);
      } else {
         BlockPos blockPos8 = this.blockPos6 == null ? this.resolve3((List<BlockPos>)items) : this.blockPos6;
         if (this.blockPos6 != null && this.blockPos6.equals(blockPos8)) {
            if (this.autoAncientBotState2 == AutoAncientBot.AutoAncientBotState2.APPROACHING) {
               this.invoke12(iBaritone, ancientXray5);
            } else {
               if (this.autoAncientBotState2 == AutoAncientBot.AutoAncientBotState2.BREAKING) {
                  this.invoke13(iBaritone, ancientXray5);
               }
            }
         } else {
            if (this.blockPos6 == null) {
               this.intValue2 = 0;
            }

            this.invoke10(iBaritone, blockPos8, true);
         }
      }
   }

   private void invoke10(IBaritone iBaritone, BlockPos blockPos, boolean bl) {
      this.blockPos6 = blockPos.toImmutable();
      this.invoke25();
      this.invoke22();
      this.autoAncientBotState2 = AutoAncientBot.AutoAncientBotState2.APPROACHING;
      this.intValue3 = 0;
      this.intValue4 = 0;
      this.blockPos9 = null;
      this.blockPos7 = null;
      this.dualTimer4.invoke();
      this.dualTimer6.invoke();
      if (bl) {
         this.dualTimer.invoke();
      }

      this.invoke34("target ore " + this.blockPos6.toShortString());
   }

   private void invoke11(IBaritone iBaritone) {
      iBaritone.getMineProcess().cancel();
      iBaritone.getBuilderProcess().onLostControl();
      this.invoke22();
      iBaritone.getSelectionManager().removeAllSelections();
      this.blockPos6 = null;
      this.blockPos7 = null;
      this.autoAncientBotState2 = AutoAncientBot.AutoAncientBotState2.APPROACHING;
      this.invoke25();
   }

   private List<BlockPos> resolve2(AncientXray ancientXray6) {
      ArrayList arrayList = new ArrayList<>(ancientXray6.resolve());
      arrayList.removeIf(blockPos -> {
         boolean flag = !CLIENT.world.getBlockState((BlockPos)blockPos).isOf(Blocks.ANCIENT_DEBRIS);
         if (flag) {
            ancientXray6.invoke5((BlockPos)blockPos);
         }

         if (!flag && !this.values.contains(blockPos) && this.check21((BlockPos)blockPos)) {
            this.values.add(((BlockPos)blockPos).toImmutable());
            ancientXray6.invoke5((BlockPos)blockPos);
            this.invoke35("Пропускаю обломок " + ((BlockPos)blockPos).toShortString() + " — замурован в лаве, не подойти");
            return true;
         } else {
            return flag || this.values.contains(blockPos);
         }
      });
      return arrayList;
   }

   private BlockPos resolve3(List<BlockPos> list) {
      return list.stream()
         .min(Comparator.comparingDouble(blockPos -> CLIENT.player.squaredDistanceTo(Vec3d.ofCenter(blockPos))))
         .orElse((BlockPos)list.get(0));
   }

   private void invoke12(IBaritone iBaritone, AncientXray ancientXray7) {
      this.invoke25();
      if (this.check10(this.blockPos6)) {
         this.invoke15(iBaritone);
      } else if (this.dualTimer6.check5(22000L)) {
         this.invoke19(ancientXray7, "не смог дойти");
      } else if (!this.check32(this.blockPos6, "лечу к обломку")) {
         this.invoke23(iBaritone, this.blockPos6, 2);
      }
   }

   private void invoke13(IBaritone iBaritone, AncientXray ancientXray8) {
      BlockHitResult blockHitResult3 = this.resolve10(this.blockPos6);
      if (blockHitResult3 == null) {
         this.blockPos7 = null;
         if (this.dualTimer7.check5(900L)) {
            this.intValue4++;
            if (this.intValue4 > 4) {
               this.invoke19(ancientXray8, "не удержаться рядом (лава/обрыв)");
               return;
            }

            this.invoke14(iBaritone);
            this.invoke34("lost reach " + this.blockPos6.toShortString());
         }
      } else {
         this.dualTimer7.invoke();
         BlockPos blockPos9 = blockHitResult3.getBlockPos();
         if (!blockPos9.equals(this.blockPos9)) {
            this.blockPos9 = blockPos9.toImmutable();
            this.intValue4 = 0;
         }

         boolean flag2 = blockPos9.equals(this.blockPos6);
         AutoAncientBot.AutoAncientBotState autoAncientBotState2 = this.resolve7(blockPos9, flag2 ? 9000L : 3000L);
         if (autoAncientBotState2 != AutoAncientBot.AutoAncientBotState.STUCK) {
            if (this.dualTimer.check5(18000L)) {
               if (this.check(iBaritone)) {
                  this.intValue2++;
                  this.invoke34("retry ore " + this.blockPos6.toShortString() + " #" + this.intValue2);
                  this.invoke14(iBaritone);
                  this.dualTimer.invoke();
                  return;
               }

               this.invoke19(ancientXray8, "не выкопался за таймаут");
            }
         } else {
            this.intValue3++;
            if (flag2 || this.intValue3 > 2) {
               this.invoke19(ancientXray8, "фантомные блоки, ресинк");
            }
         }
      }
   }

   private void invoke14(IBaritone iBaritone) {
      this.invoke25();
      this.dualTimer7.invoke();
      this.autoAncientBotState2 = AutoAncientBot.AutoAncientBotState2.APPROACHING;
      this.dualTimer6.invoke();
      this.invoke23(iBaritone, this.blockPos6, 2);
   }

   private void invoke15(IBaritone iBaritone) {
      this.invoke22();
      this.autoAncientBotState2 = AutoAncientBot.AutoAncientBotState2.BREAKING;
      this.intValue3 = 0;
      this.blockPos7 = null;
      this.dualTimer.invoke();
      this.dualTimer7.invoke();
      this.invoke34("break ore " + this.blockPos6.toShortString());
   }

   private void invoke16(BlockPos blockPos) {
      this.blockPos6 = blockPos.toImmutable();
      this.intValue2 = 0;
      this.autoAncientBotState2 = AutoAncientBot.AutoAncientBotState2.APPROACHING;
      this.invoke33(AutoAncientBot.AutoAncientBotState4.MINING);
      this.invoke10(BaritoneAPI.getProvider().getPrimaryBaritone(), this.blockPos6, true);
      AncientXray ancientXray9 = this.resolve14();
      int intValue = ancientXray9 == null ? 0 : this.resolve2(ancientXray9).size();
      this.invoke35("Иду к обломку " + this.blockPos6.toShortString() + (intValue > 1 ? " (в очереди: " + intValue + ")" : ""));
   }

   private boolean check(IBaritone iBaritone) {
      if (this.blockPos6 != null && this.intValue2 < 2) {
         if (!CLIENT.world.getBlockState(this.blockPos6).isOf(Blocks.ANCIENT_DEBRIS)) {
            return false;
         } else {
            double doubleValue = CLIENT.player.squaredDistanceTo(Vec3d.ofCenter(this.blockPos6));
            return iBaritone.getPathingBehavior().isPathing() || doubleValue <= 144.0 || this.check28(this.blockPos6);
         }
      } else {
         return false;
      }
   }

   private void invoke17(AncientXray ancientXray10) {
      IBaritone iBaritone4 = BaritoneAPI.getProvider().getPrimaryBaritone();
      iBaritone4.getBuilderProcess().onLostControl();
      this.invoke22();
      iBaritone4.getSelectionManager().removeAllSelections();
      ancientXray10.invoke5(this.blockPos6);
      this.blockPos6 = null;
      this.blockPos7 = null;
      this.autoAncientBotState2 = AutoAncientBot.AutoAncientBotState2.APPROACHING;
      this.invoke25();
   }

   private void invoke18(BlockPos blockPos, int i) {
      if (CLIENT.getNetworkHandler() != null) {
         for (int intValue2 = -i; intValue2 <= i; intValue2++) {
            for (int intValue3 = -i; intValue3 <= i; intValue3++) {
               for (int intValue4 = -i; intValue4 <= i; intValue4++) {
                  BlockPos blockPos10 = blockPos.add(intValue2, intValue3, intValue4);
                  CLIENT.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(Action.START_DESTROY_BLOCK, blockPos10, Direction.UP));
                  CLIENT.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(Action.ABORT_DESTROY_BLOCK, blockPos10, Direction.UP));
               }
            }
         }
      }
   }

   private void invoke19(AncientXray ancientXray11, String string) {
      if (this.blockPos6 != null) {
         this.values.add(this.blockPos6.toImmutable());
         ancientXray11.invoke5(this.blockPos6);
         this.invoke35("Пропускаю обломок " + this.blockPos6.toShortString() + " — " + string);
      }

      IBaritone iBaritone5 = BaritoneAPI.getProvider().getPrimaryBaritone();
      iBaritone5.getMineProcess().cancel();
      iBaritone5.getBuilderProcess().onLostControl();
      this.invoke22();
      iBaritone5.getSelectionManager().removeAllSelections();
      this.blockPos6 = null;
      this.blockPos7 = null;
      this.autoAncientBotState2 = AutoAncientBot.AutoAncientBotState2.APPROACHING;
      this.invoke25();
   }

   private BlockPos resolve4(AncientXray ancientXray12) {
      return this.resolve2(ancientXray12)
         .stream()
         .min(Comparator.comparingDouble(blockPos -> CLIENT.player.squaredDistanceTo(Vec3d.ofCenter(blockPos))))
         .orElse(null);
   }

   private void invoke20(IBaritone iBaritone) {
      BlockPos blockPos11 = CLIENT.player.getBlockPos();
      if (this.intValue > 0 && this.intValue % 4 == 0) {
         this.doubleValue++;
      }

      byte byteValue = 36;
      int intValue5 = blockPos11.getX() + (int)Math.round(Math.cos(this.doubleValue) * byteValue);
      int intValue6 = blockPos11.getZ() + (int)Math.round(Math.sin(this.doubleValue) * byteValue);
      this.blockPos2 = new BlockPos(intValue5, this.compute4(blockPos11.getY()), intValue6);
      this.invoke31(iBaritone, this.blockPos2);
      this.intValue++;
      this.invoke33(AutoAncientBot.AutoAncientBotState4.MOVING_SEARCH);
      this.invoke34("search " + this.blockPos2.toShortString());
   }

   private BlockPos resolve5() {
      BlockPos blockPos12 = CLIENT.player.getBlockPos();
      byte byteValue2 = 26;
      BlockPos blockPos13 = null;
      int intValue7 = Integer.MIN_VALUE;

      for (int intValue8 = -byteValue2; intValue8 <= byteValue2; intValue8 += 4) {
         for (int intValue9 = -byteValue2; intValue9 <= byteValue2; intValue9 += 4) {
            for (byte byteValue3 = -6; byteValue3 <= 6; byteValue3 += 2) {
               BlockPos blockPos14 = new BlockPos(blockPos12.getX() + intValue8, blockPos12.getY() + byteValue3, blockPos12.getZ() + intValue9);
               int intValue10 = this.compute(blockPos14);
               if (intValue10 != Integer.MIN_VALUE && (blockPos13 == null || intValue10 > intValue7)) {
                  blockPos13 = blockPos14;
                  intValue7 = intValue10;
               }
            }
         }
      }

      return blockPos13;
   }

   private int compute(BlockPos blockPos) {
      if (this.check3(blockPos)) {
         return Integer.MIN_VALUE;
      } else if (!this.check27(blockPos.down())) {
         return Integer.MIN_VALUE;
      } else {
         int intValue11 = 0;
         int intValue12 = 0;
         int intValue13 = 0;
         int intValue14 = 0;

         for (int intValue15 = -5; intValue15 <= 5; intValue15++) {
            for (int intValue16 = -3; intValue16 <= 3; intValue16++) {
               for (int intValue17 = -5; intValue17 <= 5; intValue17++) {
                  BlockPos blockPos15 = blockPos.add(intValue15, intValue16, intValue17);
                  BlockState blockState2 = CLIENT.world.getBlockState(blockPos15);
                  Block block2 = blockState2.getBlock();
                  intValue11++;
                  if (this.check29(block2)) {
                     intValue13++;
                  } else if (block2 == Blocks.LAVA) {
                     intValue14++;
                  } else if (this.check30(block2)) {
                     intValue12++;
                  }
               }
            }
         }

         if (!(intValue12 < intValue11 * 0.48) && !(intValue13 > intValue11 * 0.34) && !(intValue14 > intValue11 * 0.2)) {
            double doubleValue2 = CLIENT.player.squaredDistanceTo(Vec3d.ofCenter(blockPos));
            return intValue12 * 3 - intValue13 * 4 - intValue14 * 5 - (int)(doubleValue2 * 0.02) + this.compute5(blockPos.getY());
         } else {
            return Integer.MIN_VALUE;
         }
      }
   }

   private boolean check2(BlockPos blockPos) {
      return this.compute(blockPos) != Integer.MIN_VALUE;
   }

   private boolean check3(BlockPos blockPos) {
      double doubleValue3 = 842.4;

      for (BlockPos blockPos16 : this.values2) {
         if (this.measure2(blockPos, blockPos16) <= doubleValue3) {
            return true;
         }
      }

      return false;
   }

   private BlockPos resolve6(BlockPos blockPos) {
      BlockPos blockPos17 = null;

      for (int intValue18 = 0; intValue18 <= 2; intValue18++) {
         for (int intValue19 = -1; intValue19 <= 1; intValue19++) {
            for (int intValue20 = -1; intValue20 <= 1; intValue20++) {
               BlockPos blockPos18 = blockPos.add(intValue19, intValue18, intValue20);
               if (this.check4(blockPos18)) {
                  if (this.check5(blockPos18)) {
                     return blockPos18.toImmutable();
                  }

                  if (blockPos17 == null) {
                     blockPos17 = blockPos18.toImmutable();
                  }
               }
            }
         }
      }

      if (blockPos17 != null) {
         return blockPos17;
      } else {
         return this.check4(blockPos) ? blockPos.toImmutable() : null;
      }
   }

   private boolean check4(BlockPos blockPos) {
      BlockState blockState3 = CLIENT.world.getBlockState(blockPos);
      BlockState blockState4 = CLIENT.world.getBlockState(blockPos.up());
      return this.check27(blockPos.down()) && blockState3.getFluidState().isEmpty() && blockState4.getFluidState().isEmpty();
   }

   private boolean check5(BlockPos blockPos) {
      return this.check27(blockPos.down())
         && CLIENT.world.getBlockState(blockPos).isReplaceable()
         && CLIENT.world.getBlockState(blockPos.up()).isReplaceable();
   }

   private boolean check6() {
      AncientXray ancientXray13 = this.resolve14();
      if (ancientXray13 == null) {
         return false;
      } else {
         BlockPos blockPos19 = this.resolve4(ancientXray13);
         if (blockPos19 == null) {
            return false;
         } else {
            this.invoke16(blockPos19);
            return true;
         }
      }
   }

   private boolean check7(BlockPos blockPos) {
      int intValue21 = this.compute6(Blocks.TNT.asItem());
      if (intValue21 == -1) {
         ChatUtil.sendClientMessage("[AutoAncient] TNT is missing from hotbar.");
         return false;
      } else {
         ItemStackUtils.invoke(intValue21);
         this.flag14 = false;
         BlockPos blockPos20 = blockPos.down();
         BlockHitResult blockHitResult4 = new BlockHitResult(new Vec3d(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5), Direction.UP, blockPos20, false);
         CLIENT.interactionManager.interactBlock(CLIENT.player, Hand.MAIN_HAND, blockHitResult4);
         CLIENT.player.swingHand(Hand.MAIN_HAND);
         return true;
      }
   }

   private boolean check8(BlockPos blockPos) {
      int intValue22 = this.compute6(Items.FLINT_AND_STEEL);
      if (intValue22 == -1) {
         ChatUtil.sendClientMessage("[AutoAncient] Flint and steel is missing from hotbar.");
         this.toggle();
         return false;
      } else {
         BlockHitResult blockHitResult5 = this.resolve12(blockPos);
         if (blockHitResult5 == null) {
            return false;
         } else {
            ItemStackUtils.invoke(intValue22);
            BlockHitResult blockHitResult6 = this.resolve9();
            BlockHitResult blockHitResult7 = blockHitResult6 != null && blockHitResult6.getBlockPos().equals(blockPos) ? blockHitResult6 : blockHitResult5;
            CLIENT.interactionManager.interactBlock(CLIENT.player, Hand.MAIN_HAND, blockHitResult7);
            CLIENT.player.swingHand(Hand.MAIN_HAND);
            return true;
         }
      }
   }

   private AutoAncientBot.AutoAncientBotState resolve7(BlockPos blockPos, long l) {
      if (CLIENT.player != null && CLIENT.world != null && CLIENT.interactionManager != null) {
         BlockHitResult blockHitResult8 = this.resolve12(blockPos);
         if (blockHitResult8 != null && !(CLIENT.player.getEyePos().distanceTo(blockHitResult8.getPos()) > 4.2)) {
            this.invoke22();
            Rotation rotation3 = this.resolve8(blockHitResult8.getPos());
            this.invoke24(rotation3);
            if (new Rotation(CLIENT.player).measure(rotation3) > 4.0F) {
               return AutoAncientBot.AutoAncientBotState.AIMING;
            } else {
               BlockHitResult blockHitResult9 = this.resolve9();
               BlockHitResult blockHitResult10 = blockHitResult9 != null && blockHitResult9.getBlockPos().equals(blockPos) ? blockHitResult9 : blockHitResult8;
               if (!blockPos.equals(this.blockPos7)) {
                  if (!this.dualTimer3.check5(90L)) {
                     return AutoAncientBot.AutoAncientBotState.AIMING;
                  }

                  CLIENT.interactionManager.attackBlock(blockPos, blockHitResult10.getSide());
                  this.blockPos7 = blockPos.toImmutable();
                  this.dualTimer2.invoke();
                  this.dualTimer3.invoke();
               } else {
                  if (this.dualTimer2.check5(l)) {
                     this.invoke21(blockPos);
                     this.blockPos7 = null;
                     return AutoAncientBot.AutoAncientBotState.STUCK;
                  }

                  CLIENT.interactionManager.updateBlockBreakingProgress(blockPos, blockHitResult10.getSide());
               }

               CLIENT.player.swingHand(Hand.MAIN_HAND);
               return AutoAncientBot.AutoAncientBotState.BREAKING;
            }
         } else {
            return AutoAncientBot.AutoAncientBotState.NO_REACH;
         }
      } else {
         return AutoAncientBot.AutoAncientBotState.NO_REACH;
      }
   }

   private void invoke21(BlockPos blockPos) {
      if (CLIENT.getNetworkHandler() != null) {
         CLIENT.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(Action.ABORT_DESTROY_BLOCK, blockPos, Direction.DOWN));
      }
   }

   private void invoke22() {
      IBaritone iBaritone6 = BaritoneAPI.getProvider().getPrimaryBaritone();
      if (iBaritone6.getPathingBehavior().isPathing() || iBaritone6.getCustomGoalProcess().isActive()) {
         iBaritone6.getPathingBehavior().cancelEverything();
      }

      this.blockPos = null;
   }

   private boolean check9(IBaritone iBaritone) {
      return iBaritone.getPathingBehavior().isPathing() || iBaritone.getCustomGoalProcess().isActive();
   }

   private void invoke23(IBaritone iBaritone, BlockPos blockPos, int i) {
      BlockPos blockPos21 = blockPos.toImmutable();
      boolean flag3 = !blockPos21.equals(this.blockPos);
      if (flag3 || !iBaritone.getCustomGoalProcess().isActive() && this.dualTimer4.check5(600L)) {
         iBaritone.getCustomGoalProcess().setGoalAndPath(new GoalNear(blockPos21, i));
         this.blockPos = blockPos21;
         this.dualTimer4.invoke();
         if (flag3) {
            this.invoke29();
            this.invoke34("walk " + blockPos21.toShortString());
         }
      }
   }

   private void invoke24(Rotation rotation4) {
      float floatValue = new Rotation(CLIENT.player).measure(rotation4);
      float floatValue2 = Math.max(34.0F, Math.min(140.0F, floatValue * 1.35F));
      RotationController.invoke3(rotation4, floatValue2, floatValue2, floatValue2, floatValue2, 2, 20, false);
   }

   private Rotation resolve8(Vec3d vec3d) {
      Vec3d vec3d4 = CLIENT.player.getEyePos();
      double doubleValue4 = vec3d.x - vec3d4.x;
      double doubleValue5 = vec3d.y - vec3d4.y;
      double doubleValue6 = vec3d.z - vec3d4.z;
      double doubleValue7 = Math.sqrt(doubleValue4 * doubleValue4 + doubleValue6 * doubleValue6);
      float floatValue3 = (float)Math.toDegrees(Math.atan2(-doubleValue4, doubleValue6));
      float floatValue4 = (float)(-Math.toDegrees(Math.atan2(doubleValue5, doubleValue7)));
      return new Rotation(floatValue3, MathHelper.clamp(floatValue4, -90.0F, 90.0F));
   }

   private BlockHitResult resolve9() {
      double doubleValue8 = Math.toRadians(CLIENT.player.getYaw());
      double doubleValue9 = Math.toRadians(CLIENT.player.getPitch());
      double doubleValue10 = Math.cos(doubleValue9);
      Vec3d vec3d5 = new Vec3d(-Math.sin(doubleValue8) * doubleValue10, -Math.sin(doubleValue9), Math.cos(doubleValue8) * doubleValue10);
      Vec3d vec3d6 = CLIENT.player.getEyePos();
      Vec3d vec3d7 = vec3d6.add(vec3d5.multiply(4.6000000000000005));
      BlockHitResult blockHitResult11 = CLIENT.world.raycast(new RaycastContext(vec3d6, vec3d7, ShapeType.OUTLINE, FluidHandling.NONE, CLIENT.player));
      return blockHitResult11.getType() == Type.BLOCK ? blockHitResult11 : null;
   }

   private BlockHitResult resolve10(BlockPos blockPos) {
      return this.resolve11(blockPos, 4.2);
   }

   private BlockHitResult resolve11(BlockPos blockPos, double d) {
      BlockHitResult blockHitResult12 = this.resolve12(blockPos);
      if (blockHitResult12 == null) {
         Vec3d vec3d8 = CLIENT.player.getEyePos();
         BlockHitResult blockHitResult13 = CLIENT.world
            .raycast(new RaycastContext(vec3d8, Vec3d.ofCenter(blockPos), ShapeType.OUTLINE, FluidHandling.NONE, CLIENT.player));
         if (blockHitResult13.getType() != Type.BLOCK) {
            return null;
         }

         BlockPos blockPos22 = blockHitResult13.getBlockPos();
         if (!blockPos22.equals(blockPos) && CLIENT.world.getBlockState(blockPos22).getHardness(CLIENT.world, blockPos22) < 0.0F) {
            return null;
         }

         blockHitResult12 = blockHitResult13;
      }

      return CLIENT.player.getEyePos().distanceTo(blockHitResult12.getPos()) > d ? null : blockHitResult12;
   }

   private boolean check10(BlockPos blockPos) {
      return blockPos != null && this.resolve11(blockPos, 3.7) != null;
   }

   private BlockHitResult resolve12(BlockPos blockPos) {
      Vec3d vec3d9 = CLIENT.player.getEyePos();
      double[] doubleValues = new double[]{0.5, 0.2, 0.8};

      for (double doubleValue11 : doubleValues) {
         for (double doubleValue12 : doubleValues) {
            for (double doubleValue13 : doubleValues) {
               Vec3d vec3d10 = new Vec3d(blockPos.getX() + doubleValue11, blockPos.getY() + doubleValue12, blockPos.getZ() + doubleValue13);
               BlockHitResult blockHitResult14 = CLIENT.world.raycast(new RaycastContext(vec3d9, vec3d10, ShapeType.OUTLINE, FluidHandling.NONE, CLIENT.player));
               if (blockHitResult14.getType() == Type.BLOCK && blockHitResult14.getBlockPos().equals(blockPos)) {
                  return blockHitResult14;
               }
            }
         }
      }

      return null;
   }

   private void invoke25() {
      if (CLIENT.options != null) {
         CLIENT.options.attackKey.setPressed(false);
      }

      this.blockPos7 = null;
   }

   private boolean check11(IBaritone iBaritone) {
      boolean flag4 = PlayerHelper.check();
      if (flag4) {
         if (!this.flag2) {
            iBaritone.getCommandManager().execute("pause");
            this.flag2 = true;
            this.invoke25();
         }

         return true;
      } else {
         if (this.flag2) {
            iBaritone.getCommandManager().execute("resume");
            this.flag2 = false;
         }

         return false;
      }
   }

   private boolean check12(IBaritone iBaritone) {
      if (CLIENT.player == null || CLIENT.interactionManager == null || CLIENT.options == null) {
         return false;
      } else if (this.flag13) {
         if (this.check13() && this.intValue7 >= 0 && this.check14(this.intValue7) && !this.dualTimer11.check5(3500L)) {
            ItemStackUtils.invoke(this.intValue7);
            CLIENT.options.useKey.setPressed(true);
            if (!CLIENT.player.isUsingItem()) {
               CLIENT.interactionManager.interactItem(CLIENT.player, Hand.MAIN_HAND);
            }

            return true;
         } else {
            this.invoke26(iBaritone);
            return false;
         }
      } else if (!this.check13()) {
         this.DynamicButtonSetting = false;
         return false;
      } else {
         int intValue23 = this.compute2();
         if (intValue23 == -1) {
            if (!this.DynamicButtonSetting) {
               ChatUtil.sendClientMessage("[AutoAncient] Fire resistance potion is missing from hotbar.");
               this.DynamicButtonSetting = true;
            }

            return false;
         } else {
            this.DynamicButtonSetting = false;
            this.invoke27(iBaritone);
            this.flag13 = true;
            this.intValue7 = intValue23;
            this.intValue8 = CLIENT.player.getInventory().getSelectedSlot();
            this.dualTimer11.invoke();
            if (!this.flag10) {
               iBaritone.getCommandManager().execute("pause");
               this.flag10 = true;
            }

            this.invoke25();
            ItemStackUtils.invoke(this.intValue7);
            CLIENT.options.useKey.setPressed(true);
            CLIENT.interactionManager.interactItem(CLIENT.player, Hand.MAIN_HAND);
            this.invoke34("drink fire res " + this.intValue7);
            return true;
         }
      }
   }

   private void invoke26(IBaritone iBaritone) {
      if (CLIENT.options != null) {
         CLIENT.options.useKey.setPressed(false);
      }

      if (CLIENT.player != null && this.intValue8 >= 0 && this.intValue8 < 9) {
         ItemStackUtils.invoke(this.intValue8);
      }

      if (iBaritone != null && this.flag10) {
         iBaritone.getCommandManager().execute("resume");
      }

      this.flag10 = false;
      this.flag13 = false;
      this.intValue7 = -1;
      this.intValue8 = -1;
   }

   private boolean check13() {
      if (CLIENT.player == null) {
         return false;
      } else {
         StatusEffectInstance statusEffectInstance = CLIENT.player.getStatusEffect(StatusEffects.FIRE_RESISTANCE);
         return statusEffectInstance == null || statusEffectInstance.getDuration() <= 300;
      }
   }

   private int compute2() {
      if (CLIENT.player == null) {
         return -1;
      } else {
         for (int intValue24 = 0; intValue24 < 9; intValue24++) {
            if (this.check14(intValue24)) {
               return intValue24;
            }
         }

         return -1;
      }
   }

   private boolean check14(int i) {
      if (CLIENT.player != null && i >= 0 && i <= 8) {
         ItemStack itemStack = CLIENT.player.getInventory().getStack(i);
         if (!itemStack.isEmpty() && itemStack.isOf(Items.POTION)) {
            PotionContentsComponent potionContentsComponent = (PotionContentsComponent)itemStack.get(DataComponentTypes.POTION_CONTENTS);
            if (potionContentsComponent == null) {
               return false;
            } else {
               for (StatusEffectInstance statusEffectInstance2 : potionContentsComponent.getEffects()) {
                  RegistryEntry registryEntry = statusEffectInstance2.getEffectType();
                  if (registryEntry.equals(StatusEffects.FIRE_RESISTANCE)) {
                     return true;
                  }
               }

               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean check15(IBaritone iBaritone) {
      if (CLIENT.player == null || CLIENT.interactionManager == null || CLIENT.options == null) {
         return false;
      } else if (this.flag12) {
         if (CLIENT.player.getHungerManager().getFoodLevel() < 19
            && this.intValue5 >= 0
            && this.check16(this.intValue5)
            && !this.dualTimer10.check5(7000L)) {
            ItemStackUtils.invoke(this.intValue5);
            CLIENT.options.useKey.setPressed(true);
            if (!CLIENT.player.isUsingItem()) {
               CLIENT.interactionManager.interactItem(CLIENT.player, Hand.MAIN_HAND);
            }

            return true;
         } else {
            this.invoke27(iBaritone);
            return false;
         }
      } else if (this.autoAncientBotState4 != AutoAncientBot.AutoAncientBotState4.PLACING_TNT
         && this.autoAncientBotState4 != AutoAncientBot.AutoAncientBotState4.IGNITING_TNT
         && this.autoAncientBotState4 != AutoAncientBot.AutoAncientBotState4.WAITING_EXPLOSION) {
         if (CLIENT.player.getHungerManager().getFoodLevel() <= 16 && CLIENT.player.canConsume(false)) {
            int intValue25 = this.compute3();
            if (intValue25 == -1) {
               return false;
            } else {
               this.flag12 = true;
               this.intValue5 = intValue25;
               this.intValue6 = CLIENT.player.getInventory().getSelectedSlot();
               this.dualTimer10.invoke();
               if (!this.flag9) {
                  iBaritone.getCommandManager().execute("pause");
                  this.flag9 = true;
               }

               this.invoke25();
               ItemStackUtils.invoke(this.intValue5);
               CLIENT.options.useKey.setPressed(true);
               CLIENT.interactionManager.interactItem(CLIENT.player, Hand.MAIN_HAND);
               this.invoke34("eat " + this.intValue5);
               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private void invoke27(IBaritone iBaritone) {
      if (CLIENT.options != null) {
         CLIENT.options.useKey.setPressed(false);
      }

      if (CLIENT.player != null && this.intValue6 >= 0 && this.intValue6 < 9) {
         ItemStackUtils.invoke(this.intValue6);
      }

      if (iBaritone != null && this.flag9) {
         iBaritone.getCommandManager().execute("resume");
      }

      this.flag9 = false;
      this.flag12 = false;
      this.intValue5 = -1;
      this.intValue6 = -1;
   }

   private int compute3() {
      if (CLIENT.player == null) {
         return -1;
      } else {
         for (int intValue26 = 0; intValue26 < 9; intValue26++) {
            if (this.check16(intValue26)) {
               return intValue26;
            }
         }

         return -1;
      }
   }

   private boolean check16(int i) {
      if (CLIENT.player != null && i >= 0 && i <= 8) {
         ItemStack itemStack2 = CLIENT.player.getInventory().getStack(i);
         return !itemStack2.isEmpty() && itemStack2.contains(DataComponentTypes.FOOD);
      } else {
         return false;
      }
   }

   private boolean check17(IBaritone iBaritone) {
      if (CLIENT.player == null || CLIENT.world == null || CLIENT.options == null) {
         return false;
      } else if (!CLIENT.player.isInLava()) {
         if (this.flag8) {
            this.invoke28();
         }

         return false;
      } else {
         if (!this.flag8) {
            this.dualTimer15.invoke();
            this.invoke35("Упал в лаву — выбираюсь");
         }

         this.flag8 = true;
         this.invoke27(iBaritone);
         this.invoke25();
         this.blockPos7 = null;
         BaritoneAPI.getSettings().assumeWalkOnLava.value = true;
         if (this.blockPos8 == null || this.dualTimer9.check5(2500L)) {
            BlockPos blockPos23 = this.resolve13(10);
            if (blockPos23 != null) {
               this.blockPos8 = blockPos23.toImmutable();
               this.dualTimer9.invoke();
               this.invoke34("lava escape " + this.blockPos8.toShortString());
            }
         }

         if (this.blockPos8 != null) {
            this.invoke23(iBaritone, this.blockPos8, 1);
         }

         if (this.autoAncientBotState3 == AutoAncientBot.AutoAncientBotState3.IDLE && this.pyorki.isEnabled() && this.dualTimer15.check5(3500L)) {
            BlockPos blockPos24 = this.resolve13(24);
            if (blockPos24 != null && this.check34(Vec3d.ofBottomCenter(blockPos24), "выбираюсь из лавы")) {
               this.dualTimer15.invoke();
               CLIENT.options.jumpKey.setPressed(true);
               return true;
            }

            this.dualTimer15.invoke();
         }

         CLIENT.options.jumpKey.setPressed(true);
         return true;
      }
   }

   private void invoke28() {
      if (CLIENT.options != null) {
         CLIENT.options.jumpKey.setPressed(false);
      }

      if (this.flag3) {
         BaritoneAPI.getSettings().assumeWalkOnLava.value = false;
      }

      this.flag8 = false;
      this.blockPos8 = null;
   }

   private BlockPos resolve13(int i) {
      BlockPos blockPos25 = CLIENT.player.getBlockPos();
      BlockPos blockPos26 = null;
      double doubleValue14 = Double.MAX_VALUE;

      for (int intValue27 = -i; intValue27 <= i; intValue27++) {
         for (int intValue28 = -2; intValue28 <= 7; intValue28++) {
            for (int intValue29 = -i; intValue29 <= i; intValue29++) {
               BlockPos blockPos27 = blockPos25.add(intValue27, intValue28, intValue29);
               if (this.check18(blockPos27)) {
                  double doubleValue15 = CLIENT.player.squaredDistanceTo(Vec3d.ofCenter(blockPos27)) + Math.max(0, intValue28) * 0.6;
                  if (doubleValue15 < doubleValue14) {
                     doubleValue14 = doubleValue15;
                     blockPos26 = blockPos27.toImmutable();
                  }
               }
            }
         }
      }

      return blockPos26;
   }

   private boolean check18(BlockPos blockPos) {
      return this.check27(blockPos.down())
         && this.check19(blockPos)
         && this.check19(blockPos.up())
         && !this.check20(blockPos.down())
         && !this.check20(blockPos)
         && !this.check20(blockPos.up());
   }

   private boolean check19(BlockPos blockPos) {
      BlockState blockState5 = CLIENT.world.getBlockState(blockPos);
      return blockState5.getFluidState().isEmpty() && blockState5.getCollisionShape(CLIENT.world, blockPos).isEmpty();
   }

   private boolean check20(BlockPos blockPos) {
      return CLIENT.world.getBlockState(blockPos).isOf(Blocks.LAVA);
   }

   private boolean check21(BlockPos blockPos) {
      int intValue30 = 0;

      for (Direction direction : Direction.values()) {
         BlockPos blockPos28 = blockPos.offset(direction);
         if (this.check20(blockPos28)) {
            intValue30++;
         } else if (this.check22(blockPos28)) {
            return false;
         }
      }

      return intValue30 == 0 ? false : this.valuesByKey.computeIfAbsent(blockPos.toImmutable(), blockPosx -> this.resolve15(blockPosx, 4) == null);
   }

   private boolean check22(BlockPos blockPos) {
      BlockState blockState6 = CLIENT.world.getBlockState(blockPos);
      return blockState6.getFluidState().isEmpty() && blockState6.getCollisionShape(CLIENT.world, blockPos).isEmpty();
   }

   private boolean check23(IBaritone iBaritone) {
      if (CLIENT.player != null && iBaritone.getPathingBehavior().isPathing()) {
         Vec3d vec3d11 = CLIENT.player.getPos();
         if (this.vec3d != null && !(vec3d11.squaredDistanceTo(this.vec3d) > 0.04)) {
            return this.dualTimer8.check5(2500L);
         } else {
            this.vec3d = vec3d11;
            this.dualTimer8.invoke();
            return false;
         }
      } else {
         this.invoke29();
         return false;
      }
   }

   private void invoke29() {
      this.vec3d = CLIENT.player == null ? null : CLIENT.player.getPos();
      this.dualTimer8.invoke();
   }

   private void invoke30(IBaritone iBaritone) {
      this.invoke25();
      this.invoke18(CLIENT.player.getBlockPos(), 1);
      this.invoke22();
      switch (this.autoAncientBotState4) {
         case MOVING_SEARCH:
            if (this.blockPos2 != null) {
               this.invoke31(iBaritone, this.blockPos2);
            }
            break;
         case MOVING_SITE:
            if (this.blockPos3 != null) {
               this.invoke31(iBaritone, this.blockPos3);
            }
            break;
         case CLEARING_SITE:
            if (this.blockPos4 != null) {
               this.invoke32(iBaritone, this.blockPos4);
            }
            break;
         case PLACING_TNT:
         case IGNITING_TNT:
            if (this.blockPos4 != null) {
               this.invoke32(iBaritone, this.blockPos4);
            }
      }

      this.invoke29();
      this.invoke34("path rebuild");
   }

   private void invoke31(IBaritone iBaritone, BlockPos blockPos) {
      this.invoke23(iBaritone, blockPos, 1);
   }

   private void invoke32(IBaritone iBaritone, BlockPos blockPos) {
      this.invoke23(iBaritone, blockPos, 2);
   }

   private boolean check24(BlockPos blockPos) {
      return CLIENT.player.squaredDistanceTo(Vec3d.ofCenter(blockPos)) <= 9.0;
   }

   private int compute4(int i) {
      return i + MathHelper.clamp(36 - i, -4, 4);
   }

   private int compute5(int i) {
      int intValue31 = Math.abs(i - 36);
      return Math.max(-120, 90 - intValue31 * 6);
   }

   private boolean check25(BlockPos blockPos) {
      return this.blockPos4 != null
         ? this.measure(blockPos, this.blockPos4) <= 2304.0
         : this.autoAncientBotState4 == AutoAncientBot.AutoAncientBotState4.WAITING_EXPLOSION || this.autoAncientBotState4 == AutoAncientBot.AutoAncientBotState4.WAITING_SCAN;
   }

   private boolean check26(BlockPos blockPos, double d) {
      return CLIENT.player.squaredDistanceTo(Vec3d.ofCenter(blockPos)) <= d;
   }

   private boolean check27(BlockPos blockPos) {
      BlockState blockState7 = CLIENT.world.getBlockState(blockPos);
      return !blockState7.isReplaceable() && blockState7.getFluidState().isEmpty();
   }

   private boolean check28(BlockPos blockPos) {
      for (Direction direction2 : Direction.values()) {
         Block block3 = CLIENT.world.getBlockState(blockPos.offset(direction2)).getBlock();
         if (this.check29(block3) || block3 == Blocks.LAVA) {
            return true;
         }
      }

      return false;
   }

   private boolean check29(Block block) {
      return block == Blocks.AIR || block == Blocks.CAVE_AIR || block == Blocks.VOID_AIR;
   }

   private boolean check30(Block block) {
      return block == Blocks.NETHERRACK
         || block == Blocks.BASALT
         || block == Blocks.SMOOTH_BASALT
         || block == Blocks.BLACKSTONE
         || block == Blocks.SOUL_SAND
         || block == Blocks.SOUL_SOIL
         || block == Blocks.GRAVEL
         || block == Blocks.NETHER_GOLD_ORE
         || block == Blocks.NETHER_QUARTZ_ORE;
   }

   private int compute6(Item item) {
      if (CLIENT.player == null) {
         return -1;
      } else {
         for (int intValue32 = 0; intValue32 < 9; intValue32++) {
            if (CLIENT.player.getInventory().getStack(intValue32).isOf(item)) {
               return intValue32;
            }
         }

         return -1;
      }
   }

   private double measure(BlockPos blockPos, BlockPos blockPos2) {
      double doubleValue16 = blockPos.getX() - blockPos2.getX();
      double doubleValue17 = blockPos.getY() - blockPos2.getY();
      double doubleValue18 = blockPos.getZ() - blockPos2.getZ();
      return doubleValue16 * doubleValue16 + doubleValue17 * doubleValue17 + doubleValue18 * doubleValue18;
   }

   private double measure2(BlockPos blockPos, BlockPos blockPos2) {
      double doubleValue19 = blockPos.getX() - blockPos2.getX();
      double doubleValue20 = blockPos.getZ() - blockPos2.getZ();
      return doubleValue19 * doubleValue19 + doubleValue20 * doubleValue20;
   }

   private void invoke33(AutoAncientBot.AutoAncientBotState4 autoAncientBotState4) {
      if (this.autoAncientBotState4 != autoAncientBotState4) {
         this.invoke34(this.autoAncientBotState4 + " -> " + autoAncientBotState4);
      }

      this.autoAncientBotState4 = autoAncientBotState4;
      this.dualTimer.invoke();
      this.dualTimer5.invoke();
   }

   private AncientXray resolve14() {
      return WildClient.INSTANCE.moduleManager.getModule(AncientXray.class);
   }

   private void invoke34(String string) {
      if (this.debug.isEnabled()) {
         ChatUtil.sendClientMessage("[AutoAncient] " + string);
      }
   }

   private void invoke35(String string) {
      if (this.logiVChat.isEnabled()) {
         ChatUtil.sendClientMessage("[AutoAncient] " + string);
      }
   }

   private void invoke36() {
      if (this.blockPos6 != null && this.autoAncientBotState2 == AutoAncientBot.AutoAncientBotState2.BREAKING) {
         if (!CLIENT.world.getBlockState(this.blockPos6).isOf(Blocks.ANCIENT_DEBRIS)) {
            this.intValue13++;
            this.invoke35("Обломок добыт (всего: " + this.intValue13 + ")");
         }
      }
   }

   private int compute7(Item item) {
      if (CLIENT.player == null) {
         return 0;
      } else {
         int intValue33 = 0;

         for (int intValue34 = 0; intValue34 < 36; intValue34++) {
            ItemStack itemStack3 = CLIENT.player.getInventory().getStack(intValue34);
            if (itemStack3.isOf(item)) {
               intValue33 += itemStack3.getCount();
            }
         }

         return intValue33;
      }
   }

   private boolean check31(IBaritone iBaritone) {
      if (this.autoAncientBotState3 == AutoAncientBot.AutoAncientBotState3.IDLE) {
         return false;
      } else if (CLIENT.player != null && CLIENT.world != null && CLIENT.interactionManager != null && CLIENT.options != null) {
         if (CLIENT.player.isInLava()) {
            CLIENT.options.jumpKey.setPressed(true);
         }

         if (this.intValue9 >= 0 && this.autoAncientBotState3 == AutoAncientBot.AutoAncientBotState3.AWAITING) {
            ItemStackUtils.invoke(this.intValue9);
            this.intValue9 = -1;
         }

         if (this.autoAncientBotState3 == AutoAncientBot.AutoAncientBotState3.AIMING) {
            if (this.dualTimer12.check5(2000L)) {
               this.invoke34("pearl aim timeout");
               this.invoke38();
               return false;
            } else {
               int intValue35 = this.compute6(Items.ENDER_PEARL);
               if (intValue35 == -1) {
                  this.invoke38();
                  return false;
               } else {
                  Rotation rotation5 = new Rotation(this.FoundryShaderSetting, this.floatValue);
                  this.invoke24(rotation5);
                  if (new Rotation(CLIENT.player).measure(rotation5) > 2.5F) {
                     return true;
                  } else {
                     Vec3d vec3d12 = CLIENT.player.getEyePos().subtract(0.0, 0.1, 0.0);
                     AutoAncientBot.AutoAncientBotState5 autoAncientBotState5 = this.resolve16(vec3d12, this.vec3d2);
                     if (autoAncientBotState5 != null && !(autoAncientBotState5.doubleValue > 1.8) && this.check35(autoAncientBotState5.vec3d)) {
                        this.FoundryShaderSetting = autoAncientBotState5.floatValue;
                        this.floatValue = autoAncientBotState5.floatValue2;
                        if (new Rotation(CLIENT.player).measure(new Rotation(this.FoundryShaderSetting, this.floatValue)) > 2.5F) {
                           return true;
                        } else {
                           this.intValue9 = CLIENT.player.getInventory().getSelectedSlot();
                           ItemStackUtils.invoke(intValue35);
                           CLIENT.interactionManager.interactItem(CLIENT.player, Hand.MAIN_HAND);
                           CLIENT.player.swingHand(Hand.MAIN_HAND);
                           this.intValue15++;
                           this.dualTimer13.invoke();
                           this.vec3d3 = CLIENT.player.getPos();
                           this.autoAncientBotState3 = AutoAncientBot.AutoAncientBotState3.AWAITING;
                           this.dualTimer12.invoke();
                           return true;
                        }
                     } else {
                        this.invoke34("pearl solution lost");
                        this.invoke38();
                        return false;
                     }
                  }
               }
            }
         } else {
            boolean flag5 = this.vec3d2 != null && CLIENT.player.squaredDistanceTo(this.vec3d2) <= 25.0;
            boolean flag6 = this.vec3d3 != null && CLIENT.player.getPos().squaredDistanceTo(this.vec3d3) > 64.0;
            if (flag5 || flag6) {
               this.invoke35("Телепорт: " + this.SpacerSetting);
               this.intValue10 = 0;
               this.vec3d4 = null;
               this.invoke37(iBaritone);
               this.invoke38();
               return false;
            } else if (this.dualTimer12.check5(5000L)) {
               this.intValue10++;
               this.vec3d4 = this.vec3d2;
               this.invoke35("Пёрка не долетела — эта цель в бане, иду пешком");
               this.invoke38();
               return false;
            } else {
               return true;
            }
         }
      } else {
         this.invoke38();
         return false;
      }
   }

   private void invoke37(IBaritone iBaritone) {
      this.invoke18(CLIENT.player.getBlockPos(), 1);
      this.blockPos = null;
      if (this.autoAncientBotState4 == AutoAncientBot.AutoAncientBotState4.MINING) {
         this.autoAncientBotState2 = AutoAncientBot.AutoAncientBotState2.APPROACHING;
         this.blockPos7 = null;
         this.dualTimer6.invoke();
      } else {
         this.invoke30(iBaritone);
      }

      this.invoke29();
   }

   private void invoke38() {
      if (this.intValue9 >= 0) {
         ItemStackUtils.invoke(this.intValue9);
         this.intValue9 = -1;
      }

      this.autoAncientBotState3 = AutoAncientBot.AutoAncientBotState3.IDLE;
      this.vec3d2 = null;
      this.SpacerSetting = null;
      this.vec3d3 = null;
   }

   private boolean check32(BlockPos blockPos, String string) {
      if (blockPos == null || !this.pyorki.isEnabled() || this.autoAncientBotState3 != AutoAncientBot.AutoAncientBotState3.IDLE) {
         return false;
      } else if (!this.check33()) {
         return false;
      } else {
         Vec3d vec3d13 = Vec3d.ofCenter(blockPos);
         if (vec3d13.y - CLIENT.player.getY() > 2.5) {
            return false;
         } else {
            double doubleValue21 = vec3d13.x - CLIENT.player.getX();
            double doubleValue22 = vec3d13.z - CLIENT.player.getZ();
            double doubleValue23 = doubleValue21 * doubleValue21 + doubleValue22 * doubleValue22;
            double doubleValue24 = this.minDistantsiyaPyorki.getValue();
            if (doubleValue23 < doubleValue24 * doubleValue24) {
               return false;
            } else if (!this.dualTimer14.check5(1200L)) {
               return false;
            } else {
               this.dualTimer14.invoke();
               BlockPos blockPos29 = blockPos;
               if (doubleValue23 > 729.0) {
                  Vec3d vec3d14 = vec3d13.subtract(CLIENT.player.getPos()).normalize();
                  blockPos29 = BlockPos.ofFloored(CLIENT.player.getPos().add(vec3d14.multiply(27.0)));
               }

               BlockPos blockPos30 = this.resolve15(blockPos29, 5);
               return blockPos30 != null && this.check34(Vec3d.ofBottomCenter(blockPos30), string);
            }
         }
      }
   }

   private boolean check33() {
      long longValue2 = 2500L * (1L + Math.min(this.intValue10, 3));
      return this.dualTimer13.check5(longValue2);
   }

   private boolean check34(Vec3d vec3d, String string) {
      if (!this.pyorki.isEnabled() || this.autoAncientBotState3 != AutoAncientBot.AutoAncientBotState3.IDLE || vec3d == null) {
         return false;
      } else if (CLIENT.player == null || CLIENT.interactionManager == null) {
         return false;
      } else if (this.flag12 || this.flag13) {
         return false;
      } else if (!this.check33()) {
         return false;
      } else if (this.compute6(Items.ENDER_PEARL) == -1) {
         return false;
      } else {
         boolean flag7 = CLIENT.player.isInLava();
         if (!flag7 && CLIENT.player.getHealth() < 8.0F) {
            return false;
         } else if (!flag7 && vec3d.y - CLIENT.player.getY() > 2.5) {
            return false;
         } else if (this.vec3d4 != null && vec3d.squaredDistanceTo(this.vec3d4) < 16.0) {
            return false;
         } else {
            Vec3d vec3d15 = CLIENT.player.getEyePos().subtract(0.0, 0.1, 0.0);
            AutoAncientBot.AutoAncientBotState5 autoAncientBotState52 = this.resolve16(vec3d15, vec3d);
            if (autoAncientBotState52 != null && !(autoAncientBotState52.doubleValue > 1.8) && this.check35(autoAncientBotState52.vec3d)) {
               this.vec3d2 = vec3d;
               this.SpacerSetting = string;
               this.FoundryShaderSetting = autoAncientBotState52.floatValue;
               this.floatValue = autoAncientBotState52.floatValue2;
               this.autoAncientBotState3 = AutoAncientBot.AutoAncientBotState3.AIMING;
               this.dualTimer12.invoke();
               this.invoke25();
               this.invoke22();
               this.invoke35("Кидаю пёрку: " + string + " → " + (int)Math.floor(vec3d.x) + " " + (int)Math.floor(vec3d.y) + " " + (int)Math.floor(vec3d.z));
               return true;
            } else {
               this.invoke34("pearl no solution: " + string);
               return false;
            }
         }
      }
   }

   private BlockPos resolve15(BlockPos blockPos, int i) {
      BlockPos blockPos31 = null;
      double doubleValue25 = Double.MAX_VALUE;

      for (int intValue36 = -i; intValue36 <= i; intValue36++) {
         for (int intValue37 = -i; intValue37 <= i; intValue37++) {
            for (int intValue38 = -i; intValue38 <= i; intValue38++) {
               BlockPos blockPos32 = blockPos.add(intValue36, intValue37, intValue38);
               if (this.check18(blockPos32)) {
                  double doubleValue26 = this.measure(blockPos32, blockPos);
                  if (doubleValue26 < doubleValue25) {
                     doubleValue25 = doubleValue26;
                     blockPos31 = blockPos32.toImmutable();
                  }
               }
            }
         }
      }

      return blockPos31;
   }

   private boolean check35(Vec3d vec3d) {
      BlockPos blockPos33 = BlockPos.ofFloored(vec3d);
      if (!this.check20(blockPos33) && !this.check20(blockPos33.up())) {
         for (int intValue39 = 1; intValue39 <= 4; intValue39++) {
            BlockPos blockPos34 = blockPos33.down(intValue39);
            if (this.check20(blockPos34)) {
               return false;
            }

            if (this.check27(blockPos34)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private AutoAncientBot.AutoAncientBotState5 resolve16(Vec3d vec3d, Vec3d vec3d2) {
      double doubleValue27 = vec3d2.x - vec3d.x;
      double doubleValue28 = vec3d2.z - vec3d.z;
      float floatValue5 = (float)Math.toDegrees(Math.atan2(-doubleValue27, doubleValue28));
      AutoAncientBot.AutoAncientBotState5 autoAncientBotState53 = null;

      for (float floatValue6 = -6.0F; floatValue6 <= 6.0F; floatValue6 += 2.0F) {
         float floatValue7 = floatValue5 + floatValue6;

         for (float floatValue8 = -40.0F; floatValue8 <= 80.0F; floatValue8 += 2.0F) {
            AutoAncientBot.AutoAncientBotState5 autoAncientBotState54 = this.resolve17(vec3d, vec3d2, floatValue7, floatValue8);
            if (autoAncientBotState54 != null && (autoAncientBotState53 == null || autoAncientBotState54.doubleValue < autoAncientBotState53.doubleValue)) {
               autoAncientBotState53 = autoAncientBotState54;
            }
         }
      }

      if (autoAncientBotState53 == null) {
         return null;
      } else {
         AutoAncientBot.AutoAncientBotState5 autoAncientBotState55 = autoAncientBotState53;

         for (float floatValue9 = autoAncientBotState53.floatValue - 2.0F; floatValue9 <= autoAncientBotState53.floatValue + 2.0F; floatValue9 += 0.5F) {
            for (float floatValue10 = autoAncientBotState53.floatValue2 - 2.0F; floatValue10 <= autoAncientBotState53.floatValue2 + 2.0F; floatValue10 += 0.3F) {
               AutoAncientBot.AutoAncientBotState5 autoAncientBotState56 = this.resolve17(vec3d, vec3d2, floatValue9, floatValue10);
               if (autoAncientBotState56 != null && autoAncientBotState56.doubleValue < autoAncientBotState55.doubleValue) {
                  autoAncientBotState55 = autoAncientBotState56;
               }
            }
         }

         return autoAncientBotState55;
      }
   }

   private AutoAncientBot.AutoAncientBotState5 resolve17(Vec3d vec3d, Vec3d vec3d2, float f, float g) {
      Vec3d vec3d16 = this.resolve18(f, g);
      Vec3d vec3d17 = this.resolve19(vec3d, vec3d16);
      if (vec3d17 == null) {
         return null;
      } else {
         double doubleValue29 = Math.sqrt(vec3d17.squaredDistanceTo(vec3d2));
         return new AutoAncientBot.AutoAncientBotState5(MathHelper.wrapDegrees(f), MathHelper.clamp(g, -90.0F, 90.0F), doubleValue29, vec3d17);
      }
   }

   private Vec3d resolve18(float f, float g) {
      float floatValue11 = f * (float) (Math.PI / 180.0);
      float floatValue12 = g * (float) (Math.PI / 180.0);
      double doubleValue30 = -MathHelper.sin(floatValue11) * MathHelper.cos(floatValue12);
      double doubleValue31 = -MathHelper.sin(floatValue12);
      double doubleValue32 = MathHelper.cos(floatValue11) * MathHelper.cos(floatValue12);
      Vec3d vec3d18 = new Vec3d(doubleValue30, doubleValue31, doubleValue32).normalize().multiply(1.5);
      Vec3d vec3d19 = CLIENT.player.getMovement();
      return vec3d18.add(vec3d19.x, CLIENT.player.isOnGround() ? 0.0 : vec3d19.y, vec3d19.z);
   }

   private Vec3d resolve19(Vec3d vec3d, Vec3d vec3d2) {
      if (CLIENT.world == null) {
         return null;
      } else {
         Vec3d vec3d20 = vec3d;
         Vec3d vec3d21 = vec3d2;

         for (int intValue40 = 0; intValue40 < 160; intValue40++) {
            vec3d21 = vec3d21.subtract(0.0, 0.03, 0.0).multiply(0.99);
            Vec3d vec3d22 = vec3d20.add(vec3d21);
            BlockHitResult blockHitResult15 = CLIENT.world.raycast(new RaycastContext(vec3d20, vec3d22, ShapeType.COLLIDER, FluidHandling.NONE, CLIENT.player));
            if (blockHitResult15.getType() != Type.MISS) {
               return blockHitResult15.getPos();
            }

            vec3d20 = vec3d22;
         }

         return vec3d20;
      }
   }

   private void invoke39() {
      Settings settings = BaritoneAPI.getSettings();
      this.flag4 = (Boolean)settings.allowPlace.value;
      this.flag5 = (Boolean)settings.allowBreak.value;
      this.flag6 = (Boolean)settings.assumeWalkOnLava.value;
      this.flag7 = (Boolean)settings.walkWhileBreaking.value;
      List items2 = (List)settings.blocksToAvoid.value;
      this.items = (List<Block>)(Object)(items2 == null ? List.of() : new ArrayList<>(items2));
      List items3 = (List)settings.acceptableThrowawayItems.value;
      this.items2 = (List<Item>)(Object)(items3 == null ? List.of() : new ArrayList<>(items3));
      settings.allowPlace.value = true;
      settings.allowBreak.value = true;
      settings.assumeWalkOnLava.value = false;
      settings.walkWhileBreaking.value = false;
      if (items2 != null) {
         items2.remove(Blocks.LAVA);
      }

      if (items3 != null) {
         items3.remove(Blocks.TNT.asItem());
         this.invoke41(items3, Blocks.NETHERRACK.asItem());
         this.invoke41(items3, Blocks.BLACKSTONE.asItem());
         this.invoke41(items3, Blocks.BASALT.asItem());
         this.invoke41(items3, Blocks.COBBLESTONE.asItem());
      }

      this.flag3 = true;
   }

   private void invoke40() {
      if (this.flag3) {
         Settings settings2 = BaritoneAPI.getSettings();
         settings2.allowPlace.value = this.flag4;
         settings2.allowBreak.value = this.flag5;
         settings2.assumeWalkOnLava.value = this.flag6;
         settings2.walkWhileBreaking.value = this.flag7;
         List items4 = (List)settings2.blocksToAvoid.value;
         if (items4 != null) {
            items4.clear();
            items4.addAll(this.items);
         }

         List items5 = (List)settings2.acceptableThrowawayItems.value;
         if (items5 != null) {
            items5.clear();
            items5.addAll(this.items2);
         }

         this.flag3 = false;
      }
   }

   private void invoke41(List<Item> list, Item item) {
      if (!list.contains(item)) {
         list.add(item);
      }
   }

   static enum AutoAncientBotState {
      AIMING,
      BREAKING,
      STUCK,
      NO_REACH;
   }

   static enum AutoAncientBotState2 {
      APPROACHING,
      BREAKING;
   }

   static enum AutoAncientBotState3 {
      IDLE,
      AIMING,
      AWAITING;
   }

   static enum AutoAncientBotState4 {
      SEARCHING,
      MOVING_SEARCH,
      MOVING_SITE,
      CLEARING_SITE,
      PLACING_TNT,
      IGNITING_TNT,
      WAITING_EXPLOSION,
      WAITING_SCAN,
      MINING;
   }

   static final class AutoAncientBotState5 {
      final float floatValue;
      final float floatValue2;
      final double doubleValue;
      final Vec3d vec3d;

      AutoAncientBotState5(float f, float g, double d, Vec3d vec3d) {
         this.floatValue = f;
         this.floatValue2 = g;
         this.doubleValue = d;
         this.vec3d = vec3d;
      }
   }
}
