package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "GeyserHelper",
   category = Category.Player,
   description = "Подсвечивает лут который можно слутать с ивента 'Гейзер' на FunTime"
)
public class GeyserHelper extends Module {
   private final Map<ItemEntity, Long> valuesByKey = new ConcurrentHashMap<>();
   private final List<GeyserHelper.GeyserHelperItemData> items = new ArrayList<>();
   private final Map<Integer, Long> valuesByKey2 = new ConcurrentHashMap<>();
   public final BooleanSetting sinhronizatsiyaSNametags = new BooleanSetting("Синхронизация с NameTags", true);
   public final ModeSetting stilistika = new ModeSetting("Стилистика", "Тёмный", "Тёмный", "Светлый", "Блюр").setVisibilityCondition(this.sinhronizatsiyaSNametags::isEnabled);
   public final NumberSetting prozrachnost = new NumberSetting("Прозрачность", 1.0F, 0.1F, 1.0F, 0.05F, true).setVisibilityCondition(this.sinhronizatsiyaSNametags::isEnabled);
   private final ConfigurableHudElement configurableHudElement = new ConfigurableHudElement() {};
   private boolean flag = false;
   private float floatValue = 0.0F;
   private float floatValue2 = 0.0F;
   private float floatValue3 = 0.0F;
   private float floatValue4 = 0.0F;
   private float floatValue5 = 0.0F;
   private boolean flag2 = false;
   private long timestamp = 0L;
   private final List<GeyserHelper.GeyserHelperData> items2 = new ArrayList<>();
   private int intValue;
   private int intValue2;
   private float floatValue6 = 1.0F;
   private String tyomnyy = "Тёмный";

   public GeyserHelper() {
      this.addSettings(new Setting[]{this.sinhronizatsiyaSNametags, this.stilistika, this.prozrachnost});
      this.configurableHudElement.invoke(this.sinhronizatsiyaSNametags);
      this.configurableHudElement.invoke(this.stilistika);
      this.configurableHudElement.invoke(this.prozrachnost);
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.valuesByKey2.clear();
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.valuesByKey.clear();
      this.items.clear();
      this.valuesByKey2.clear();
      this.flag = false;
      this.floatValue5 = 0.0F;
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (CLIENT.world != null) {
         if (packetEvent.getPacket() instanceof PlaySoundS2CPacket playSoundS2CPacket) {
            String text = ((SoundEvent)playSoundS2CPacket.getSound().value()).toString();
            if (this.check(text)) {
               this.invoke4(playSoundS2CPacket.getX(), playSoundS2CPacket.getY(), playSoundS2CPacket.getZ());
            }
         }
      }
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.world != null) {
         this.valuesByKey.keySet().removeIf(itemEntity -> !itemEntity.isAlive() || itemEntity.getStack().isEmpty());
      }
   }

   @EventHandler
   public void onHudRender(HudRenderEvent hudRenderEvent) {
      if (CLIENT.world != null && CLIENT.player != null) {
         this.items2.clear();
         this.invoke2();
         float floatValue = CLIENT.getRenderTickCounter().getTickProgress(true);
         RenderManager renderManager = hudRenderEvent.getRenderManager();
         Camera camera = CLIENT.gameRenderer.getCamera();
         Vec3d vec3d = camera.getPos();
         float floatValue2 = (float)CLIENT.mouse.getX();
         float floatValue3 = (float)CLIENT.mouse.getY();
         boolean flag = CLIENT.currentScreen instanceof ChatScreen;
         this.items.clear();
         NameTags nameTags = this.resolve();
         boolean flag2 = nameTags != null && nameTags.enabled && nameTags.tseli.isEnabled("Предметы");
         HashSet hashSet = new HashSet();
         if (!this.valuesByKey.isEmpty()) {
            for (Entry entry : this.valuesByKey.entrySet()) {
               ItemEntity itemEntity2 = (ItemEntity)entry.getKey();
               Vec3d vec3d2 = itemEntity2.getLerpedPos(floatValue);
               double doubleValue = flag2 ? 0.52 : 0.7;
               Vec3d vec3d3 = new Vec3d(vec3d2.x, vec3d2.y + doubleValue, vec3d2.z);
               if (!(vec3d3.squaredDistanceTo(vec3d) < 1.0E-6)) {
                  Vec3d vec3d4 = MathUtils.resolve(vec3d3);
                  if (!(vec3d4.z <= 0.001F) && !(vec3d4.z > 1.0)) {
                     double doubleValue2 = vec3d.distanceTo(vec3d3);
                     hashSet.add(itemEntity2.getId());
                     this.valuesByKey2.putIfAbsent(itemEntity2.getId(), System.currentTimeMillis());
                     float floatValue4 = MathHelper.clamp((float)(System.currentTimeMillis() - this.valuesByKey2.get(itemEntity2.getId())) / 300.0F, 0.0F, 1.0F);
                     float floatValue5 = 1.0F - (float)Math.pow(1.0F - floatValue4, 4.0);
                     this.invoke(renderManager, itemEntity2, (Long)entry.getValue(), (float)vec3d4.x, (float)vec3d4.y, (float)doubleValue2, flag2, floatValue5);
                  }
               }
            }
         }

         this.valuesByKey2.keySet().retainAll(hashSet);
         this.invoke5(renderManager);
         if (flag) {
            boolean flag3 = GLFW.glfwGetMouseButton(CLIENT.getWindow().getHandle(), 0) == 1;
            boolean flag4 = flag3 && !this.flag2;
            this.flag2 = flag3;
            if (flag4 && System.currentTimeMillis() - this.timestamp > 150L) {
               this.timestamp = System.currentTimeMillis();
               boolean flag5 = false;

               for (GeyserHelper.GeyserHelperData geyserHelperData : this.items2) {
                  if (this.check2(floatValue2, floatValue3, geyserHelperData.x, geyserHelperData.y, geyserHelperData.w, geyserHelperData.h)) {
                     flag5 = true;
                     this.flag = !this.flag;
                     if (this.flag) {
                        this.floatValue = geyserHelperData.x;
                        this.floatValue2 = geyserHelperData.y;
                        this.floatValue3 = geyserHelperData.w;
                        this.floatValue4 = geyserHelperData.h;
                     }
                     break;
                  }
               }

               if (!flag5 && this.flag) {
                  boolean flag6 = this.check2(floatValue2, floatValue3, this.floatValue - 250.0F, this.floatValue2 - 150.0F, 600.0F, 500.0F);
                  if (!flag6) {
                     this.flag = false;
                  }
               }
            }

            float floatValue6 = this.flag ? 1.0F : 0.0F;
            this.floatValue5 = this.floatValue5 + (floatValue6 - this.floatValue5) * 0.15F;
            if (this.floatValue5 > 0.01F) {
               HudSettingsRenderer.invoke(
                  renderManager,
                  this.configurableHudElement,
                  this.floatValue,
                  this.floatValue2,
                  this.floatValue3,
                  this.floatValue4,
                  CLIENT.getWindow().getScaledWidth(),
                  CLIENT.getWindow().getScaledHeight(),
                  this.floatValue5,
                  floatValue2,
                  floatValue3,
                  flag4,
                  flag3
               );
            }
         } else {
            this.flag = false;
            this.floatValue5 = 0.0F;
            this.flag2 = false;
         }
      }
   }

   private void invoke(RenderManager renderManager2, ItemEntity itemEntity, long l, float f, float g, float h, boolean bl, float i) {
      float floatValue7 = (float)MathHelper.clamp(16.0 / Math.max((double)h, 12.0), 0.75, 1.15);
      float floatValue8 = 6.0F * floatValue7;
      ItemStack itemStack = itemEntity.getStack();
      float floatValue9 = 24.0F * floatValue7;
      long longValue = System.currentTimeMillis() - l;
      String text2 = String.format("%.0f сек", (float)longValue / 1000.0F);
      float floatValue10 = 6.0F * floatValue7;
      float floatValue11 = 20.0F * floatValue7;
      float floatValue12 = RenderManager.resolve7(FontRegistry.fontObject, text2, floatValue9).floatValue + floatValue10 * 2.0F;
      if (bl) {
         float floatValue13 = 12.0F * floatValue7;
         float floatValue14 = floatValue13 * i;
         float floatValue15 = g + floatValue14;
         float floatValue16 = f - floatValue12 / 2.0F;
         this.invoke3(renderManager2, floatValue16, floatValue15, floatValue12, floatValue11, floatValue8, i);
         renderManager2.invoke69(FontRegistry.fontObject, floatValue16 + floatValue10, floatValue15 + 14.0F * floatValue7, floatValue9, text2, this.compute(this.intValue2, i));
         this.items2.add(new GeyserHelper.GeyserHelperData(floatValue16, floatValue15, floatValue12, floatValue11));
      } else {
         float floatValue17 = 8.0F * floatValue7 * (1.0F - i);
         float floatValue18 = g + floatValue17;
         float floatValue19 = 22.0F * floatValue7;
         float floatValue20 = 4.0F * floatValue7;
         float floatValue21 = floatValue19 + floatValue20 + floatValue12;
         float floatValue22 = f - floatValue21 / 2.0F;
         this.invoke3(renderManager2, floatValue22, floatValue18, floatValue19, floatValue19, floatValue8, i);
         float floatValue23 = floatValue18 + (floatValue19 - floatValue11) / 2.0F;
         this.invoke3(renderManager2, floatValue22 + floatValue19 + floatValue20, floatValue23, floatValue12, floatValue11, floatValue8, i);
         renderManager2.invoke69(
            FontRegistry.fontObject, floatValue22 + floatValue19 + floatValue20 + floatValue10, floatValue23 + 14.0F * floatValue7, floatValue9, text2, this.compute(this.intValue2, i)
         );
         float floatValue24 = (floatValue19 - 16.0F * floatValue7) / 2.0F;
         this.items.add(new GeyserHelper.GeyserHelperItemData(itemStack, floatValue22 + floatValue24, floatValue18 + floatValue24, floatValue22, floatValue18, floatValue19, itemEntity.getId(), floatValue7));
         this.items2.add(new GeyserHelper.GeyserHelperData(floatValue22, floatValue18, floatValue21, floatValue19));
      }
   }

   private void invoke2() {
      NameTags nameTags2 = this.resolve();
      boolean flag7 = this.sinhronizatsiyaSNametags.isEnabled() && nameTags2 != null;
      String text3 = flag7 ? nameTags2.stilistika.getValue() : this.stilistika.getValue();
      float floatValue25 = flag7 ? nameTags2.prozrachnost.getValue() : this.prozrachnost.getValue();
      if (text3.equals("Светлый")) {
         this.intValue = RenderManager.RenderManagerState.compute32(240, 240, 245, (int)(255.0F * floatValue25));
         this.intValue2 = RenderManager.RenderManagerState.compute32(30, 30, 30, 255);
      } else if (text3.equals("Блюр")) {
         this.intValue = RenderManager.RenderManagerState.compute32(10, 10, 10, (int)(120.0F * floatValue25));
         this.intValue2 = RenderManager.RenderManagerState.compute32(250, 250, 250, 255);
      } else {
         this.intValue = RenderManager.RenderManagerState.compute32(25, 25, 26, (int)(255.0F * floatValue25));
         this.intValue2 = RenderManager.RenderManagerState.compute32(240, 240, 240, 255);
      }

      this.floatValue6 = floatValue25;
      this.tyomnyy = text3;
   }

   private NameTags resolve() {
      try {
         return (NameTags)WildClient.INSTANCE.moduleManager.findModule(NameTags.class);
      } catch (Exception exception) {
         return null;
      }
   }

   private void invoke3(RenderManager renderManager3, float f, float g, float h, float i, float j, float k) {
      float floatValue26 = this.floatValue6 * k;
      if (!(floatValue26 <= 0.05F)) {
         if (this.tyomnyy.equals("Блюр")) {
            renderManager3.invoke48(23.0F);
            renderManager3.invoke44(f, g, h, i, j, floatValue26);
         }

         int intValue = this.compute(this.intValue, k);
         renderManager3.invoke5(f, g, h, i, j, intValue);
      }
   }

   private int compute(int i, float f) {
      int intValue2 = i >> 24 & 0xFF;
      int intValue3 = i >> 16 & 0xFF;
      int intValue4 = i >> 8 & 0xFF;
      int intValue5 = i & 0xFF;
      return RenderManager.RenderManagerState.compute32(intValue3, intValue4, intValue5, (int)(intValue2 * f));
   }

   private boolean check(String string) {
      String text4 = string.toLowerCase();
      return text4.contains("extinguish") || text4.contains("fizz") || text4.contains("burn") || text4.contains("lava");
   }

   private void invoke4(double d, double e, double f) {
      CLIENT.execute(() -> {
         if (CLIENT.world != null) {
            for (Entity entity : CLIENT.world.getEntities()) {
               if (entity instanceof ItemEntity itemEntity3 && itemEntity3.squaredDistanceTo(d, e, f) <= 9.0) {
                  this.valuesByKey.putIfAbsent(itemEntity3, System.currentTimeMillis());
               }
            }
         }
      });
   }

   private boolean check2(float f, float g, float h, float i, float j, float k) {
      return f >= h && f <= h + j && g >= i && g <= i + k;
   }

   private void invoke5(RenderManager renderManager4) {
      if (renderManager4 != null && !this.items.isEmpty()) {
         for (GeyserHelper.GeyserHelperItemData geyserHelperItemData : this.items) {
            float floatValue27 = ItemRenderUtil.measure(geyserHelperItemData.clipX());
            float floatValue28 = ItemRenderUtil.measure(geyserHelperItemData.clipY());
            float floatValue29 = Math.max(1.0F, ItemRenderUtil.measure(geyserHelperItemData.clipSize()));
            renderManager4.invoke20();
            renderManager4.invoke24(floatValue27, floatValue28, floatValue29, floatValue29, floatValue29 * 0.27F, floatValue29 * 0.27F, floatValue29 * 0.27F, floatValue29 * 0.27F);

            try {
               ItemRenderUtil.invoke3(
                  renderManager4,
                  geyserHelperItemData.stack(),
                  ItemRenderUtil.measure(geyserHelperItemData.x()),
                  ItemRenderUtil.measure(geyserHelperItemData.y()),
                  ItemRenderUtil.measure3(geyserHelperItemData.scale()),
                  geyserHelperItemData.seed(),
                  true,
                  geyserHelperItemData.seed()
               );
            } finally {
               renderManager4.invoke20();
               renderManager4.invoke25();
            }
         }

         this.items.clear();
      }
   }

   record GeyserHelperData(float x, float y, float w, float h) {
   }

   record GeyserHelperItemData(ItemStack stack, float x, float y, float clipX, float clipY, float clipSize, int seed, float scale) {
   }
}
