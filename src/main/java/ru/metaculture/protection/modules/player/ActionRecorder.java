package ru.metaculture.protection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.MathHelper;
import org.wild.mixin.acceser.ClientPlayerInteractionManagerAccessor;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleAccess(
   usernames = {"lichoday", "bitrixtime", "oblamovvv"}
)
@ModuleRegister(
   name = "ActionRecorder",
   description = "Записывает и воспроизводит действия игрока",
   category = Category.Player,
   riskLevels = {ModuleRiskLevel.NEW}
)
public class ActionRecorder extends Module {
   private static final String KEY = "KEY";
   private static final String MOUSE = "MOUSE";
   private static final String SCROLL = "SCROLL";
   private final TextSetting file = new TextSetting("File", "default").resolve(48);
   private final KeybindSetting recordKey = new KeybindSetting("Record Key", -1);
   private final KeybindSetting playKey = new KeybindSetting("Play Key", -1);
   private final KeybindSetting stopKey = new KeybindSetting("Stop Key", -1);
   private final BooleanSetting infiniteLoop = new BooleanSetting("Infinite Loop", false);
   private final NumberSetting loops = new NumberSetting("Loops", 1.0F, 1.0F, 20.0F, 1.0F, false).setVisibilityCondition(this.infiniteLoop::isEnabled);
   private final NumberSetting playDurationSec = new NumberSetting("Play Duration Sec", 0.0F, 0.0F, 600.0F, 1.0F, false);
   private final NumberSetting recordLimitSec = new NumberSetting("Record Limit Sec", 0.0F, 0.0F, 600.0F, 1.0F, false);
   private final BooleanSetting autoSave = new BooleanSetting("Auto Save", true);
   private final BooleanSetting rotationController = new BooleanSetting("Rotation Controller", true);
   private final NumberSetting minRotationSpeed = new NumberSetting("Min Rotation Speed", 1.0F, 0.2F, 180.0F, 0.1F, false)
      .setVisibilityCondition(() -> !this.rotationController.isEnabled());
   private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
   private final RotationResetController rotationResetController = new RotationResetController();
   private ActionRecorder.ActionRecorderState3 actionRecorderState3;
   private ActionRecorder.ActionRecorderState3 actionRecorderState32;
   private boolean flag;
   private boolean flag2;
   private int intValue;
   private int intValue2;
   private int intValue3;
   private int intValue4 = -1;
   private float floatValue;
   private float floatValue2;
   private float floatValue3;
   private float floatValue4;
   private boolean flag3;
   private boolean flag4;
   private boolean flag5;
   private double doubleValue;
   private double doubleValue2;
   private boolean flag6;
   private boolean flag7;
   private boolean flag8;

   public ActionRecorder() {
      this.addSettings(
         new Setting[]{
            this.file,
            this.recordKey,
            this.playKey,
            this.stopKey,
            this.infiniteLoop,
            this.loops,
            this.playDurationSec,
            this.recordLimitSec,
            this.autoSave,
            this.rotationController,
            this.minRotationSpeed
         }
      );
   }

   @Override
   public void onDisable() {
      if (this.flag) {
         this.invoke4(this.autoSave.isEnabled());
      }

      if (this.flag2) {
         this.invoke6(false);
      }

      super.onDisable();
   }

   @EventHandler
   public void onRawInput(RawInputEvent rawInputEvent) {
      if (rawInputEvent.getAction() == 1 && this.recordKey.getKeyCode() != -1 && rawInputEvent.getKeyCode() == this.recordKey.getKeyCode()) {
         this.invoke();
         rawInputEvent.invalidate();
      } else if (rawInputEvent.getAction() == 1
         && this.playKey.getKeyCode() != -1
         && rawInputEvent.getKeyCode() == this.playKey.getKeyCode()) {
         this.invoke2();
         rawInputEvent.invalidate();
      } else if (rawInputEvent.getAction() == 1
         && this.stopKey.getKeyCode() != -1
         && rawInputEvent.getKeyCode() == this.stopKey.getKeyCode()) {
         this.invoke7();
         rawInputEvent.invalidate();
      } else {
         if (this.flag && rawInputEvent.getKeyCode() >= 0 && !this.check2(rawInputEvent.getKeyCode())) {
            this.invoke9(
               ActionRecorder.ActionRecorderState.resolve(
                  this.intValue,
                  this.actionRecorderState3.items2.size(),
                  rawInputEvent.getKeyCode(),
                  rawInputEvent.getScanCode(),
                  rawInputEvent.getAction(),
                  rawInputEvent.getModifiers()
               )
            );
         }
      }
   }

   @EventHandler
   public void onMouseButtonPosition(MouseButtonPositionEvent mouseButtonPositionEvent) {
      if (this.flag) {
         if (!this.check2(-100 - mouseButtonPositionEvent.getButton())) {
            this.invoke9(
               ActionRecorder.ActionRecorderState.resolve2(
                  this.intValue,
                  this.actionRecorderState3.items2.size(),
                  mouseButtonPositionEvent.getButton(),
                  mouseButtonPositionEvent.getAction(),
                  mouseButtonPositionEvent.getModifiers()
               )
            );
         }
      }
   }

   @EventHandler
   public void onMouseScroll(MouseScrollEvent mouseScrollEvent) {
      if (this.flag) {
         if ((!(mouseScrollEvent.getVerticalOffset() > 0.0) || !this.check2(-200)) && (!(mouseScrollEvent.getVerticalOffset() < 0.0) || !this.check2(-201))) {
            this.invoke9(
               ActionRecorder.ActionRecorderState.resolve3(
                  this.intValue, this.actionRecorderState3.items2.size(), mouseScrollEvent.getHorizontalOffset(), mouseScrollEvent.getVerticalOffset()
               )
            );
         }
      }
   }

   @EventHandler(
      priority = 4
   )
   public void onMovementInput(MovementInputEvent movementInputEvent) {
      if (this.flag2) {
         ActionRecorder.ActionRecorderState2 actionRecorderState2 = this.resolve(this.intValue2);
         if (actionRecorderState2 != null) {
            movementInputEvent.setFloatValue(actionRecorderState2.floatValue5);
            movementInputEvent.setFloatValue2(actionRecorderState2.floatValue6);
            movementInputEvent.setFlag(actionRecorderState2.flag);
            movementInputEvent.setFlag2(actionRecorderState2.flag2);
            movementInputEvent.setFlag3(actionRecorderState2.flag3);
         }
      } else {
         if (this.flag) {
            this.floatValue3 = movementInputEvent.getFloatValue();
            this.floatValue4 = movementInputEvent.getFloatValue2();
            this.flag3 = movementInputEvent.isFlag();
            this.flag4 = movementInputEvent.isFlag2();
            this.flag5 = movementInputEvent.isFlag3();
         }
      }
   }

   @EventHandler(
      priority = 4
   )
   public void onMouseDelta(MouseDeltaEvent mouseDeltaEvent) {
      if (this.flag2) {
         mouseDeltaEvent.invalidate();
      } else {
         if (this.flag) {
            this.doubleValue = this.doubleValue + mouseDeltaEvent.getDoubleValue();
            this.doubleValue2 = this.doubleValue2 + mouseDeltaEvent.getDoubleValue2();
         }
      }
   }

   @EventHandler(
      priority = 4
   )
   public void onPlayerMotion(PlayerMotionEvent playerMotionEvent) {
      if (this.flag2) {
         ActionRecorder.ActionRecorderState2 actionRecorderState22 = this.resolve(this.intValue2);
         if (actionRecorderState22 != null) {
            this.invoke10(actionRecorderState22);
            this.invoke11(actionRecorderState22, playerMotionEvent);
            this.invoke12(this.intValue2);
         }
      }
   }

   @EventHandler
   public void onClientTick(ClientTickEvent clientTickEvent) {
      if (this.flag) {
         this.invoke8();
         this.intValue++;
         if (this.recordLimitSec.getValue() > 0.0F && this.intValue >= Math.round(this.recordLimitSec.getValue() * 20.0F)) {
            this.invoke4(this.autoSave.isEnabled());
         }
      }

      if (this.flag2) {
         if (this.intValue4 != this.intValue2) {
            ActionRecorder.ActionRecorderState2 actionRecorderState23 = this.resolve(this.intValue2);
            if (actionRecorderState23 != null) {
               this.invoke10(actionRecorderState23);
            }

            this.invoke12(this.intValue2);
         }

         this.invoke18();
      }
   }

   @EventHandler
   public void onWorldReady(WorldReadyEvent worldReadyEvent) {
      this.invoke7();
   }

   @EventHandler
   public void onWorldJoin(WorldJoinEvent worldJoinEvent) {
      this.invoke7();
   }

   private void invoke() {
      if (this.flag) {
         this.invoke4(this.autoSave.isEnabled());
      } else {
         this.invoke3();
      }
   }

   private void invoke2() {
      if (this.flag2) {
         this.invoke6(true);
      } else {
         if (this.flag) {
            this.invoke4(this.autoSave.isEnabled());
         }

         this.invoke5();
      }
   }

   private void invoke3() {
      if (!this.check3()) {
         ChatUtil.sendClientMessage("[ActionRecorder] Player is not ready.");
      } else {
         if (this.flag2) {
            this.invoke6(false);
         }

         this.actionRecorderState3 = new ActionRecorder.ActionRecorderState3();
         this.actionRecorderState3.timestamp = System.currentTimeMillis();
         this.actionRecorderState3.defaultValue = this.resolve6();
         this.flag = true;
         this.intValue = 0;
         this.floatValue = CLIENT.player.getYaw();
         this.floatValue2 = CLIENT.player.getPitch();
         this.floatValue3 = 0.0F;
         this.floatValue4 = 0.0F;
         this.flag3 = false;
         this.flag4 = false;
         this.flag5 = false;
         this.doubleValue = 0.0;
         this.doubleValue2 = 0.0;
         ChatUtil.sendClientMessage("[ActionRecorder] Recording: " + this.actionRecorderState3.defaultValue);
      }
   }

   private void invoke4(boolean bl) {
      if (this.flag) {
         this.flag = false;
         if (bl && this.actionRecorderState3 != null) {
            this.invoke19(this.actionRecorderState3);
         }

         int intValue = this.actionRecorderState3 != null && this.actionRecorderState3.items != null ? this.actionRecorderState3.items.size() : 0;
         ChatUtil.sendClientMessage("[ActionRecorder] Recording stopped. Ticks: " + intValue);
      }
   }

   private void invoke5() {
      if (!this.check3()) {
         ChatUtil.sendClientMessage("[ActionRecorder] Player is not ready.");
      } else {
         ActionRecorder.ActionRecorderState3 actionRecorderState3 = this.resolve2();
         if (actionRecorderState3 != null && actionRecorderState3.items != null && !actionRecorderState3.items.isEmpty()) {
            this.invoke20(actionRecorderState3);
            this.actionRecorderState32 = actionRecorderState3;
            this.flag2 = true;
            this.intValue2 = 0;
            this.intValue3 = 0;
            this.intValue4 = -1;
            this.flag6 = false;
            this.flag7 = false;
            this.flag8 = false;
            ChatUtil.sendClientMessage("[ActionRecorder] Playback: " + this.actionRecorderState32.defaultValue);
         } else {
            ChatUtil.sendClientMessage("[ActionRecorder] Recording is empty or missing.");
         }
      }
   }

   private void invoke6(boolean bl) {
      if (this.flag2) {
         this.flag2 = false;
         this.actionRecorderState32 = null;
         this.intValue2 = 0;
         this.intValue3 = 0;
         this.intValue4 = -1;
         this.flag6 = false;
         this.flag7 = false;
         this.flag8 = false;
         this.rotationResetController.invoke4();
         this.invoke22();
         if (bl) {
            ChatUtil.sendClientMessage("[ActionRecorder] Playback stopped.");
         }
      }
   }

   private void invoke7() {
      if (this.flag) {
         this.invoke4(this.autoSave.isEnabled());
      }

      if (this.flag2) {
         this.invoke6(false);
      }
   }

   private void invoke8() {
      if (this.check3() && this.actionRecorderState3 != null) {
         ActionRecorder.ActionRecorderState2 actionRecorderState24 = new ActionRecorder.ActionRecorderState2();
         actionRecorderState24.intValue = this.intValue;
         actionRecorderState24.floatValue = CLIENT.player.getYaw();
         actionRecorderState24.floatValue2 = CLIENT.player.getPitch();
         actionRecorderState24.floatValue3 = Math.abs(MathHelper.wrapDegrees(actionRecorderState24.floatValue - this.floatValue));
         actionRecorderState24.floatValue4 = Math.abs(actionRecorderState24.floatValue2 - this.floatValue2);
         actionRecorderState24.doubleValue = this.doubleValue;
         actionRecorderState24.doubleValue2 = this.doubleValue2;
         actionRecorderState24.floatValue5 = this.floatValue3;
         actionRecorderState24.floatValue6 = this.floatValue4;
         actionRecorderState24.flag = this.flag3;
         actionRecorderState24.flag2 = this.flag4;
         actionRecorderState24.flag3 = this.flag5;
         actionRecorderState24.intValue2 = CLIENT.player.getInventory().getSelectedSlot();
         actionRecorderState24.doubleValue3 = CLIENT.player.getVelocity().x;
         actionRecorderState24.doubleValue4 = CLIENT.player.getVelocity().y;
         actionRecorderState24.doubleValue5 = CLIENT.player.getVelocity().z;
         actionRecorderState24.doubleValue6 = Math.hypot(actionRecorderState24.doubleValue3, actionRecorderState24.doubleValue5);
         this.actionRecorderState3.items.add(actionRecorderState24);
         this.floatValue = actionRecorderState24.floatValue;
         this.floatValue2 = actionRecorderState24.floatValue2;
         this.doubleValue = 0.0;
         this.doubleValue2 = 0.0;
      }
   }

   private void invoke9(ActionRecorder.ActionRecorderState actionRecorderState) {
      if (this.actionRecorderState3 != null && this.actionRecorderState3.items2 != null) {
         this.actionRecorderState3.items2.add(actionRecorderState);
      }
   }

   private void invoke10(ActionRecorder.ActionRecorderState2 actionRecorderState25) {
      if (!this.check3()) {
         this.invoke6(false);
      } else {
         this.invoke21(actionRecorderState25.intValue2);
         CLIENT.options.forwardKey.setPressed(actionRecorderState25.floatValue5 > 0.0F);
         CLIENT.options.backKey.setPressed(actionRecorderState25.floatValue5 < 0.0F);
         CLIENT.options.leftKey.setPressed(actionRecorderState25.floatValue6 > 0.0F);
         CLIENT.options.rightKey.setPressed(actionRecorderState25.floatValue6 < 0.0F);
         CLIENT.options.jumpKey.setPressed(actionRecorderState25.flag);
         CLIENT.options.sneakKey.setPressed(actionRecorderState25.flag2);
         CLIENT.options.sprintKey.setPressed(actionRecorderState25.flag3);
         CLIENT.options.attackKey.setPressed(this.flag6);
         CLIENT.options.useKey.setPressed(this.flag7);
         CLIENT.options.pickItemKey.setPressed(this.flag8);
         CLIENT.player.setSprinting(actionRecorderState25.flag3);
      }
   }

   private void invoke11(ActionRecorder.ActionRecorderState2 actionRecorderState26, PlayerMotionEvent playerMotionEvent2) {
      if (CLIENT.player != null) {
         if (this.rotationController.isEnabled()) {
            float floatValue = Math.abs(MathHelper.wrapDegrees(actionRecorderState26.floatValue - CLIENT.player.getYaw()));
            float floatValue2 = Math.abs(actionRecorderState26.floatValue2 - CLIENT.player.getPitch());
            float floatValue3 = Math.max(this.minRotationSpeed.getValue(), Math.max(actionRecorderState26.floatValue3, floatValue));
            float floatValue4 = Math.max(this.minRotationSpeed.getValue(), Math.max(actionRecorderState26.floatValue4, floatValue2));
            this.rotationResetController.invoke2(new Rotation(actionRecorderState26.floatValue, actionRecorderState26.floatValue2), floatValue3, floatValue4, 1, 30);
            playerMotionEvent2.setFloatValue(CLIENT.player.getYaw());
            playerMotionEvent2.setFloatValue2(CLIENT.player.getPitch());
         } else {
            CLIENT.player.setYaw(actionRecorderState26.floatValue);
            CLIENT.player.setPitch(actionRecorderState26.floatValue2);
            playerMotionEvent2.setFloatValue(actionRecorderState26.floatValue);
            playerMotionEvent2.setFloatValue2(actionRecorderState26.floatValue2);
         }
      }
   }

   private void invoke12(int i) {
      if (this.actionRecorderState32 != null && this.actionRecorderState32.items2 != null && this.intValue4 != i) {
         this.intValue4 = i;

         for (ActionRecorder.ActionRecorderState actionRecorderState4 : this.actionRecorderState32.items2) {
            if (actionRecorderState4.intValue == i) {
               this.invoke13(actionRecorderState4);
            }
         }
      }
   }

   private void invoke13(ActionRecorder.ActionRecorderState actionRecorderState5) {
      if ("KEY".equals(actionRecorderState5.text)) {
         this.invoke14(actionRecorderState5);
      } else if ("MOUSE".equals(actionRecorderState5.text)) {
         this.invoke15(actionRecorderState5);
      }
   }

   private void invoke14(ActionRecorder.ActionRecorderState actionRecorderState6) {
      boolean flag = actionRecorderState6.intValue5 != 0;
      if (this.check(CLIENT.options.attackKey, actionRecorderState6.intValue3, actionRecorderState6.intValue4)) {
         this.flag6 = flag;
         CLIENT.options.attackKey.setPressed(flag);
         if (flag) {
            this.invoke16();
         }
      } else if (this.check(CLIENT.options.useKey, actionRecorderState6.intValue3, actionRecorderState6.intValue4)) {
         this.flag7 = flag;
         CLIENT.options.useKey.setPressed(flag);
         if (flag) {
            this.invoke17();
         }
      } else if (this.check(CLIENT.options.pickItemKey, actionRecorderState6.intValue3, actionRecorderState6.intValue4)) {
         this.flag8 = flag;
         CLIENT.options.pickItemKey.setPressed(flag);
      } else {
         if (flag && CLIENT.options.hotbarKeys != null) {
            for (int intValue2 = 0; intValue2 < CLIENT.options.hotbarKeys.length; intValue2++) {
               if (this.check(CLIENT.options.hotbarKeys[intValue2], actionRecorderState6.intValue3, actionRecorderState6.intValue4)) {
                  this.invoke21(intValue2);
                  return;
               }
            }
         }
      }
   }

   private void invoke15(ActionRecorder.ActionRecorderState actionRecorderState7) {
      boolean flag2 = actionRecorderState7.intValue5 != 0;
      if (actionRecorderState7.intValue3 == 0) {
         this.flag6 = flag2;
         CLIENT.options.attackKey.setPressed(flag2);
         if (flag2) {
            this.invoke16();
         }
      } else if (actionRecorderState7.intValue3 == 1) {
         this.flag7 = flag2;
         CLIENT.options.useKey.setPressed(flag2);
         if (flag2) {
            this.invoke17();
         }
      } else {
         if (actionRecorderState7.intValue3 == 2) {
            this.flag8 = flag2;
            CLIENT.options.pickItemKey.setPressed(flag2);
         }
      }
   }

   private void invoke16() {
      if (this.check3() && CLIENT.currentScreen == null) {
         if (CLIENT.crosshairTarget instanceof EntityHitResult entityHitResult) {
            Entity entity = entityHitResult.getEntity();
            if (entity != null) {
               CLIENT.interactionManager.attackEntity(CLIENT.player, entity);
               CLIENT.player.swingHand(Hand.MAIN_HAND);
               return;
            }
         }

         if (CLIENT.crosshairTarget instanceof BlockHitResult blockHitResult && CLIENT.interactionManager.attackBlock(blockHitResult.getBlockPos(), blockHitResult.getSide())) {
            CLIENT.player.swingHand(Hand.MAIN_HAND);
         }
      }
   }

   private void invoke17() {
      if (this.check3() && CLIENT.currentScreen == null) {
         if (CLIENT.crosshairTarget instanceof BlockHitResult blockHitResult2) {
            ActionResult actionResult = CLIENT.interactionManager.interactBlock(CLIENT.player, Hand.MAIN_HAND, blockHitResult2);
            if (actionResult != ActionResult.PASS && actionResult != ActionResult.FAIL) {
               CLIENT.player.swingHand(Hand.MAIN_HAND);
               return;
            }
         }

         ActionResult actionResult2 = CLIENT.interactionManager.interactItem(CLIENT.player, Hand.MAIN_HAND);
         if (actionResult2 != ActionResult.PASS && actionResult2 != ActionResult.FAIL) {
            CLIENT.player.swingHand(Hand.MAIN_HAND);
         }
      }
   }

   private void invoke18() {
      int intValue3 = this.compute();
      if (intValue3 <= 0) {
         this.invoke6(false);
      } else if (this.intValue2 + 1 < intValue3) {
         this.intValue2++;
      } else {
         this.intValue3++;
         if (!this.infiniteLoop.isEnabled() && this.intValue3 >= Math.max(1, Math.round(this.loops.getValue()))) {
            this.invoke6(true);
         } else {
            this.intValue2 = 0;
            this.intValue4 = -1;
            this.flag6 = false;
            this.flag7 = false;
            this.flag8 = false;
         }
      }
   }

   private int compute() {
      if (this.actionRecorderState32 != null && this.actionRecorderState32.items != null) {
         int intValue4 = this.actionRecorderState32.items.size();
         if (this.playDurationSec.getValue() > 0.0F) {
            intValue4 = Math.min(intValue4, Math.max(1, Math.round(this.playDurationSec.getValue() * 20.0F)));
         }

         return intValue4;
      } else {
         return 0;
      }
   }

   private ActionRecorder.ActionRecorderState2 resolve(int i) {
      if (this.actionRecorderState32 != null && this.actionRecorderState32.items != null && !this.actionRecorderState32.items.isEmpty()) {
         int intValue5 = Math.max(0, Math.min(i, this.actionRecorderState32.items.size() - 1));
         ActionRecorder.ActionRecorderState2 actionRecorderState27 = this.actionRecorderState32.items.get(intValue5);
         if (actionRecorderState27.intValue == i) {
            return actionRecorderState27;
         } else {
            for (ActionRecorder.ActionRecorderState2 actionRecorderState28 : this.actionRecorderState32.items) {
               if (actionRecorderState28.intValue == i) {
                  return actionRecorderState28;
               }
            }

            return actionRecorderState27;
         }
      } else {
         return null;
      }
   }

   private void invoke19(ActionRecorder.ActionRecorderState3 actionRecorderState32) {
      try {
         this.invoke20(actionRecorderState32);
         Path path = this.resolve3();
         Files.createDirectories(path.getParent());

         try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            this.gson.toJson(actionRecorderState32, bufferedWriter);
         }

         ChatUtil.sendClientMessage("[ActionRecorder] Saved: " + path.getFileName());
      } catch (Throwable exception) {
         ChatUtil.sendClientMessage("[ActionRecorder] Save failed: " + exception.getMessage());
      }
   }

   private ActionRecorder.ActionRecorderState3 resolve2() {
      try {
         Path path2 = this.resolve3();
         if (!Files.isRegularFile(path2)) {
            return null;
         } else {
            ActionRecorder.ActionRecorderState3 actionRecorderState33;
            try (BufferedReader bufferedReader = Files.newBufferedReader(path2, StandardCharsets.UTF_8)) {
               actionRecorderState33 = (ActionRecorder.ActionRecorderState3)this.gson.fromJson(bufferedReader, ActionRecorder.ActionRecorderState3.class);
            }

            return actionRecorderState33;
         }
      } catch (Throwable exception2) {
         ChatUtil.sendClientMessage("[ActionRecorder] Load failed: " + exception2.getMessage());
         return null;
      }
   }

   private void invoke20(ActionRecorder.ActionRecorderState3 actionRecorderState34) {
      if (actionRecorderState34.items == null) {
         actionRecorderState34.items = new ArrayList<>();
      }

      if (actionRecorderState34.items2 == null) {
         actionRecorderState34.items2 = new ArrayList<>();
      }

      actionRecorderState34.intValue = 1;
      actionRecorderState34.defaultValue = this.resolve6();
      actionRecorderState34.intValue2 = actionRecorderState34.items.size();
      actionRecorderState34.items.sort(Comparator.comparingInt(actionRecorderState29 -> actionRecorderState29.intValue));
      actionRecorderState34.items2
         .sort(Comparator.<ActionRecorder.ActionRecorderState>comparingInt(actionRecorderState8 -> actionRecorderState8.intValue).thenComparingInt(actionRecorderState9 -> actionRecorderState9.intValue2));
   }

   private Path resolve3() {
      return this.resolve4().resolve(this.resolve5());
   }

   private Path resolve4() {
      return WildClient.INSTANCE != null && WildClient.INSTANCE.file != null
         ? WildClient.INSTANCE.file.toPath().resolve("action_records")
         : CLIENT.runDirectory.toPath().resolve("Wild").resolve("action_records");
   }

   private String resolve5() {
      String text = this.resolve6();
      return text.endsWith(".json") ? text : text + ".json";
   }

   private String resolve6() {
      String text2 = this.file.getValue();
      if (text2 == null || text2.isBlank()) {
         text2 = "default";
      }

      text2 = text2.trim().replace('\\', '/');
      int intValue6 = text2.lastIndexOf(47);
      if (intValue6 >= 0) {
         text2 = text2.substring(intValue6 + 1);
      }

      String text3 = text2.replaceAll("[^a-zA-Z0-9._-]", "_");
      if (text3.isBlank() || text3.equals(".") || text3.equals("..")) {
         text3 = "default";
      }

      if (text3.endsWith(".json")) {
         text3 = text3.substring(0, text3.length() - 5);
      }

      return text3;
   }

   private void invoke21(int i) {
      if (CLIENT.player != null && i >= 0 && i <= 8) {
         if (CLIENT.player.getInventory().getSelectedSlot() != i) {
            CLIENT.player.getInventory().setSelectedSlot(i);
            if (CLIENT.interactionManager instanceof ClientPlayerInteractionManagerAccessor clientPlayerInteractionManagerAccessor) {
               clientPlayerInteractionManagerAccessor.invokeSyncSelectedSlot();
            }
         }
      }
   }

   private boolean check(KeyBinding keyBinding, int i, int j) {
      return keyBinding != null && keyBinding.matchesKey(i, j);
   }

   private boolean check2(int i) {
      return i != -1 && (i == this.recordKey.getKeyCode() || i == this.playKey.getKeyCode() || i == this.stopKey.getKeyCode());
   }

   private void invoke22() {
      if (CLIENT.options != null) {
         this.invoke23(CLIENT.options.forwardKey);
         this.invoke23(CLIENT.options.backKey);
         this.invoke23(CLIENT.options.leftKey);
         this.invoke23(CLIENT.options.rightKey);
         this.invoke23(CLIENT.options.jumpKey);
         this.invoke23(CLIENT.options.sneakKey);
         this.invoke23(CLIENT.options.sprintKey);
         CLIENT.options.attackKey.setPressed(false);
         CLIENT.options.useKey.setPressed(false);
         CLIENT.options.pickItemKey.setPressed(false);
         if (CLIENT.player != null) {
            CLIENT.player.setSprinting(false);
         }
      }
   }

   private void invoke23(KeyBinding keyBinding) {
      if (keyBinding != null && CLIENT.getWindow() != null) {
         boolean flag3 = InputUtil.isKeyPressed(CLIENT.getWindow().getHandle(), keyBinding.getDefaultKey().getCode());
         keyBinding.setPressed(flag3);
      }
   }

   private boolean check3() {
      return CLIENT.player != null && CLIENT.world != null && CLIENT.interactionManager != null && CLIENT.options != null;
   }

   static final class ActionRecorderState {
      int intValue;
      int intValue2;
      String text;
      int intValue3;
      int intValue4;
      int intValue5;
      int intValue6;
      double doubleValue;
      double doubleValue2;

      private ActionRecorderState() {
      }

      static ActionRecorder.ActionRecorderState resolve(int i, int j, int k, int l, int m, int n) {
         ActionRecorder.ActionRecorderState actionRecorderState10 = new ActionRecorder.ActionRecorderState();
         actionRecorderState10.intValue = i;
         actionRecorderState10.intValue2 = j;
         actionRecorderState10.text = "KEY";
         actionRecorderState10.intValue3 = k;
         actionRecorderState10.intValue4 = l;
         actionRecorderState10.intValue5 = m;
         actionRecorderState10.intValue6 = n;
         return actionRecorderState10;
      }

      static ActionRecorder.ActionRecorderState resolve2(int i, int j, int k, int l, int m) {
         ActionRecorder.ActionRecorderState actionRecorderState11 = new ActionRecorder.ActionRecorderState();
         actionRecorderState11.intValue = i;
         actionRecorderState11.intValue2 = j;
         actionRecorderState11.text = "MOUSE";
         actionRecorderState11.intValue3 = k;
         actionRecorderState11.intValue5 = l;
         actionRecorderState11.intValue6 = m;
         return actionRecorderState11;
      }

      static ActionRecorder.ActionRecorderState resolve3(int i, int j, double d, double e) {
         ActionRecorder.ActionRecorderState actionRecorderState12 = new ActionRecorder.ActionRecorderState();
         actionRecorderState12.intValue = i;
         actionRecorderState12.intValue2 = j;
         actionRecorderState12.text = "SCROLL";
         actionRecorderState12.doubleValue = d;
         actionRecorderState12.doubleValue2 = e;
         return actionRecorderState12;
      }
   }

   static final class ActionRecorderState2 {
      int intValue;
      float floatValue;
      float floatValue2;
      float floatValue3;
      float floatValue4;
      double doubleValue;
      double doubleValue2;
      float floatValue5;
      float floatValue6;
      boolean flag;
      boolean flag2;
      boolean flag3;
      int intValue2;
      double doubleValue3;
      double doubleValue4;
      double doubleValue5;
      double doubleValue6;
   }

   static final class ActionRecorderState3 {
      int intValue = 1;
      long timestamp;
      String defaultValue = "default";
      int intValue2;
      List<ActionRecorder.ActionRecorderState2> items = new ArrayList<>();
      List<ActionRecorder.ActionRecorderState> items2 = new ArrayList<>();
   }
}
