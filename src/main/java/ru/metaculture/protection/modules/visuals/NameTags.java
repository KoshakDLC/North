package ru.metaculture.protection;

import com.mojang.blaze3d.opengl.GlStateManager;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "NameTags",
   description = "Теги сущностей",
   category = Category.Visuals
)
public class NameTags extends Module {
   private static final float FLOAT_VALUE = 0.0625F;
   private static final long TIMESTAMP = 250L;
   public final ModeSetting rezhimOtobrazheniya = new ModeSetting("Режим отображения", "Legacy", "Legacy", "New");
   private static final int INT_VALUE = RenderManager.RenderManagerState.compute32(88, 220, 116, 255);
   private static final int INT_VALUE_CLIENT_MARK = RenderManager.RenderManagerState.compute32(162, 134, 255, 255);
   private static final String IGROKI = "Игроки";
   private static final String GOLYE = "Голые";
   private static final String MOBY = "Мобы";
   private static final String ZHIVOTNYE = "Животные";
   private static final String PREDMETY = "Предметы";
   public final GroupSetting tseli = new GroupSetting(
         "Цели",
         new BooleanSetting("Игроки", true),
         new BooleanSetting("Голые", true),
         new BooleanSetting("Мобы", false),
         new BooleanSetting("Животные", false),
         new BooleanSetting("Предметы", false)
      )
      .visibleWhen(() -> this.rezhimOtobrazheniya.is("New"));
   public final GroupSetting tip = new GroupSetting("Тип", new BooleanSetting("Player", true), new BooleanSetting("Hologram", true))
      .visibleWhen(() -> this.rezhimOtobrazheniya.is("New"));
   public final BooleanSetting bronya = new BooleanSetting("Броня", true)
      .visibleWhen(() -> this.rezhimOtobrazheniya.is("Legacy") && !this.tseli.isEnabled("Игроки"));
   public final BooleanSetting pravayaRuka = new BooleanSetting("Правая рука", true)
      .visibleWhen(() -> this.rezhimOtobrazheniya.is("Legacy") && !this.tseli.isEnabled("Игроки"));
   public final BooleanSetting levayaRuka = new BooleanSetting("Левая рука", true)
      .visibleWhen(() -> this.rezhimOtobrazheniya.is("Legacy") && !this.tseli.isEnabled("Игроки"));
   public final BooleanSetting effekty = new BooleanSetting("Эффекты", true).visibleWhen(() -> !this.rezhimOtobrazheniya.is("Legacy"));
   public final BooleanSetting polosaHp = new BooleanSetting("Полоса HP", true).visibleWhen(() -> !this.rezhimOtobrazheniya.is("Legacy"));
   public final BooleanSetting nevidimki = new BooleanSetting("Невидимки", true).visibleWhen(() -> this.rezhimOtobrazheniya.is("Legacy"));
   public final BooleanSetting infoPriNavodke = new BooleanSetting("Инфо при наводке", true).visibleWhen(() -> this.rezhimOtobrazheniya.is("Legacy"));
   public final NumberSetting razmer = new NumberSetting("Размер", 1.2F, 0.75F, 1.9F, 0.05F, true)
      .setVisibilityCondition(() -> this.rezhimOtobrazheniya.is("Legacy"));
   public final NumberSetting radiusDetaley = new NumberSetting("Радиус деталей", 11.0F, 2.0F, 32.0F, 0.5F, false)
      .setVisibilityCondition(() -> this.rezhimOtobrazheniya.is("Legacy"));
   public final ModeSetting rezhimObvodki = new ModeSetting("Режим обводки", "Боксы", "Боксы", "Скелет", "Не рендерить")
      .setVisibilityCondition(() -> !this.rezhimOtobrazheniya.is("Legacy"));
   public final ModeSetting stilistika = new ModeSetting("Стилистика", "Тёмный", "Тёмный", "Светлый", "Блюр", "Неоморфизм", "Феррофлюид")
      .setVisibilityCondition(() -> this.rezhimOtobrazheniya.is("New"));
   public final BooleanSetting pokazyvatGolovu = new BooleanSetting("Показывать голову", true).visibleWhen(() -> this.rezhimOtobrazheniya.is("New"));
   public final BooleanSetting otobrazhatPolnyeImena = new BooleanSetting("Отображать полные имена", false).visibleWhen(() -> this.rezhimOtobrazheniya.is("New"));
   public final BooleanSetting podsvetkaPredmetov = new BooleanSetting("Подсветка предметов", true).visibleWhen(() -> this.rezhimOtobrazheniya.is("New"));
   public final BooleanSetting tenPlashek = new BooleanSetting("Тень плашек", true).visibleWhen(() -> this.rezhimOtobrazheniya.is("New"));
   public final BooleanSetting gradientTeksta = new BooleanSetting("Градиент текста", false).visibleWhen(() -> this.rezhimOtobrazheniya.is("New"));
   public final BooleanSetting tsvetPredmetaVGradiente = new BooleanSetting("Цвет предмета в градиенте", true)
      .visibleWhen(() -> this.rezhimOtobrazheniya.is("New") || !this.gradientTeksta.isEnabled());
   public final ColorSetting vtoroyTsvetTeksta = new ColorSetting("Второй цвет текста", 47.0F, 0.45F, 1.0F)
      .setVisibilityCondition(() -> this.rezhimOtobrazheniya.is("New") || !this.gradientTeksta.isEnabled());
   public final NumberSetting skorostGradienta = new NumberSetting("Скорость градиента", 1.0F, 0.2F, 3.0F, 0.1F, false)
      .setVisibilityCondition(() -> this.rezhimOtobrazheniya.is("New") || !this.gradientTeksta.isEnabled());
   public final BooleanSetting znachokKlienta = new BooleanSetting("Значок клиента", true);
   public final NumberSetting prozrachnost = new NumberSetting("Прозрачность", 1.0F, 0.1F, 1.0F, 0.05F, true);
   private final NameTagRenderer nameTagRenderer = new NameTagRenderer();
   private final Map<PlayerEntity, NameTags.NameTagsData4> valuesByKey = new HashMap<>();
   private final Map<LivingEntity, NameTags.NameTagsData4> valuesByKey2 = new HashMap<>();
   private final Map<ItemEntity, NameTags.NameTagsData4> valuesByKey3 = new HashMap<>();
   private final Map<Integer, NameTags.NameTagsData2> valuesByKey4 = new HashMap<>();
   private final List<NameTags.NameTagsItemData> items = new ArrayList<>();
   private final Set<Integer> values = new HashSet<>();
   private final List<ItemStack> items2 = new ArrayList<>();
   private final List<ItemStack> items3 = new ArrayList<>();
   private final MatrixStack matrixStack = new MatrixStack();
   private final Vector3f vector3f = new Vector3f();
   private final List<NameTags.NameTagsData3> items4 = new ArrayList<>();
   private final Map<String, Float> valuesByKey5 = new HashMap<>();
   private final Map<Integer, Long> valuesByKey6 = new HashMap<>();
   private final ConfigurableHudElement configurableHudElement = new ConfigurableHudElement() {};
   private boolean flag = false;
   private float floatValue = 0.0F;
   private float floatValue2 = 0.0F;
   private float floatValue3 = 0.0F;
   private float floatValue4 = 0.0F;
   private float floatValue5 = 0.0F;
   private boolean flag2 = false;
   private boolean flag3 = false;
   private long timestamp = 0L;
   private int intValue;
   private int intValue2;
   private int intValue3;
   private int intValue4;
   private int intValue5;
   private int intValue6;
   private static final ThemePalette THEME_PALETTE = ThemePalette.resolve2();

   public NameTags() {
      this.addSettings(
         new Setting[]{
            this.rezhimOtobrazheniya,
            this.tseli,
            this.tip,
            this.bronya,
            this.pravayaRuka,
            this.levayaRuka,
            this.effekty,
            this.polosaHp,
            this.nevidimki,
            this.infoPriNavodke,
            this.znachokKlienta,
            this.razmer,
            this.radiusDetaley,
            this.rezhimObvodki,
            this.prozrachnost,
            this.stilistika,
            this.pokazyvatGolovu,
            this.otobrazhatPolnyeImena,
            this.podsvetkaPredmetov,
            this.tenPlashek,
            this.gradientTeksta,
            this.tsvetPredmetaVGradiente,
            this.vtoroyTsvetTeksta,
            this.skorostGradienta
         }
      );
      this.configurableHudElement.invoke(this.tseli);
      this.configurableHudElement.invoke(this.tip);
      this.configurableHudElement.invoke(this.bronya);
      this.configurableHudElement.invoke(this.pravayaRuka);
      this.configurableHudElement.invoke(this.levayaRuka);
      this.configurableHudElement.invoke(this.effekty);
      this.configurableHudElement.invoke(this.polosaHp);
      this.configurableHudElement.invoke(this.rezhimObvodki);
      this.configurableHudElement.invoke(this.znachokKlienta);
      this.configurableHudElement.invoke(this.prozrachnost);
      this.configurableHudElement.invoke(this.stilistika);
      this.configurableHudElement.invoke(this.pokazyvatGolovu);
      this.configurableHudElement.invoke(this.otobrazhatPolnyeImena);
      this.configurableHudElement.invoke(this.podsvetkaPredmetov);
      this.configurableHudElement.invoke(this.tenPlashek);
      this.configurableHudElement.invoke(this.gradientTeksta);
      this.configurableHudElement.invoke(this.tsvetPredmetaVGradiente);
      this.configurableHudElement.invoke(this.vtoroyTsvetTeksta);
      this.configurableHudElement.invoke(this.skorostGradienta);
   }

   @Override
   public void onEnable() {
      this.nameTagRenderer.invoke();
      this.valuesByKey6.clear();
      this.valuesByKey5.clear();
      this.valuesByKey4.clear();
      super.onEnable();
   }

   @Override
   public void onDisable() {
      this.nameTagRenderer.invoke();
      this.valuesByKey6.clear();
      this.valuesByKey5.clear();
      this.valuesByKey4.clear();
      super.onDisable();
   }

   @EventHandler
   public void onWorldJoin(WorldJoinEvent worldJoinEvent) {
      this.nameTagRenderer.invoke();
      this.valuesByKey.clear();
      this.valuesByKey2.clear();
      this.valuesByKey3.clear();
      this.items.clear();
      this.valuesByKey4.clear();
      this.valuesByKey5.clear();
      this.valuesByKey6.clear();
   }

   @EventHandler(
      priority = 0
   )
   public void onHudRender(HudRenderEvent hudRenderEvent) {
      if (this.enabled && !(CLIENT.currentScreen instanceof InventoryScreen)) {
         if (GraphicsQuality.isUltraLow() || this.rezhimOtobrazheniya.is("Legacy")) {
            this.invoke(hudRenderEvent);
         } else {
            this.nameTagRenderer.invoke2(hudRenderEvent, this);
         }
      }
   }

   public boolean check(int i) {
      return this.enabled && i == 60;
   }

   private void invoke(HudRenderEvent hudRenderEvent2) {
      if (CLIENT.world != null && CLIENT.player != null) {
         this.invoke2();
         this.items4.clear();
         float floatValue = CLIENT.getRenderTickCounter().getTickProgress(true);
         this.invoke11(floatValue);
         this.invoke9();
         RenderManager renderManager = hudRenderEvent2.getRenderManager();
         DrawContext context = hudRenderEvent2.getDrawContext();
         this.items.clear();
         float floatValue2 = (float)CLIENT.mouse.getX();
         float floatValue3 = (float)CLIENT.mouse.getY();
         boolean flag = CLIENT.currentScreen instanceof ChatScreen;
         Set values = this.values;
         values.clear();
         if (!this.valuesByKey.isEmpty() || !this.valuesByKey2.isEmpty() || !this.valuesByKey3.isEmpty()) {
            boolean ultraLow = GraphicsQuality.isUltraLow();
            for (Entry entry2 : this.valuesByKey.entrySet()) {
               PlayerEntity playerEntity2 = (PlayerEntity)entry2.getKey();
               NameTags.NameTagsData4 nameTagsData4 = (NameTags.NameTagsData4)entry2.getValue();
               if (!this.check2(nameTagsData4)) {
                  values.add(playerEntity2.getId());
                  this.valuesByKey6.putIfAbsent(playerEntity2.getId(), System.currentTimeMillis());
                  float floatValue4 = MathHelper.clamp((float)(System.currentTimeMillis() - this.valuesByKey6.get(playerEntity2.getId())) / 300.0F, 0.0F, 1.0F);
                  this.invoke4(renderManager, context, playerEntity2, nameTagsData4, floatValue2, floatValue3, flag, floatValue, floatValue4);
               }
            }

            if (!ultraLow) {
               for (Entry entry3 : this.valuesByKey2.entrySet()) {
                  LivingEntity livingEntity2 = (LivingEntity)entry3.getKey();
                  NameTags.NameTagsData4 nameTagsData42 = (NameTags.NameTagsData4)entry3.getValue();
                  if (!this.check2(nameTagsData42)) {
                     values.add(livingEntity2.getId());
                     this.valuesByKey6.putIfAbsent(livingEntity2.getId(), System.currentTimeMillis());
                     float floatValue5 = MathHelper.clamp((float)(System.currentTimeMillis() - this.valuesByKey6.get(livingEntity2.getId())) / 300.0F, 0.0F, 1.0F);
                     if (!(livingEntity2 instanceof VillagerEntity villagerEntity2 && this.check3(renderManager, villagerEntity2, nameTagsData42, floatValue5))) {
                        this.invoke15(renderManager, livingEntity2, nameTagsData42, floatValue2, floatValue3, flag, floatValue5);
                     }
                  }
               }

               for (Entry entry4 : this.valuesByKey3.entrySet()) {
                  ItemEntity itemEntity2 = (ItemEntity)entry4.getKey();
                  NameTags.NameTagsData4 nameTagsData43 = (NameTags.NameTagsData4)entry4.getValue();
                  if (!this.check2(nameTagsData43)) {
                     values.add(itemEntity2.getId());
                     this.valuesByKey6.putIfAbsent(itemEntity2.getId(), System.currentTimeMillis());
                     float floatValue6 = MathHelper.clamp((float)(System.currentTimeMillis() - this.valuesByKey6.get(itemEntity2.getId())) / 300.0F, 0.0F, 1.0F);
                     ItemStack itemStack2 = itemEntity2.getStack();
                     this.invoke18(renderManager, itemEntity2, nameTagsData43, itemStack2, floatValue6);
                  }
               }
            }
         }

         this.valuesByKey6.keySet().retainAll(values);
         this.invoke23(renderManager, context);
         if (flag) {
            boolean flag2 = GLFW.glfwGetMouseButton(CLIENT.getWindow().getHandle(), 0) == 1;
            boolean flag3 = GLFW.glfwGetMouseButton(CLIENT.getWindow().getHandle(), 1) == 1;
            boolean flag4 = flag2 && !this.flag2;
            boolean flag5 = flag3 && !this.flag3;
            this.flag2 = flag2;
            this.flag3 = flag3;
            if ((flag4 || flag5) && System.currentTimeMillis() - this.timestamp > 150L) {
               this.timestamp = System.currentTimeMillis();
               boolean flag6 = false;

               for (NameTags.NameTagsData3 nameTagsData3 : this.items4) {
                  if (this.check8(floatValue2, floatValue3, nameTagsData3.x, nameTagsData3.y, nameTagsData3.w, nameTagsData3.h)) {
                     flag6 = true;
                     if (flag4) {
                        this.flag = !this.flag;
                        if (this.flag) {
                           this.floatValue = nameTagsData3.x;
                           this.floatValue2 = nameTagsData3.y;
                           this.floatValue3 = nameTagsData3.w;
                           this.floatValue4 = nameTagsData3.h;
                        }
                     } else if (nameTagsData3.playerName != null) {
                        FriendCommand.invoke5(nameTagsData3.playerName);
                     }
                     break;
                  }
               }

               if (flag4 && !flag6 && this.flag) {
                  boolean flag7 = this.check8(floatValue2, floatValue3, this.floatValue - 250.0F, this.floatValue2 - 150.0F, 600.0F, 500.0F);
                  if (!flag7) {
                     this.flag = false;
                  }
               }
            }

            float floatValue7 = this.flag ? 1.0F : 0.0F;
            this.floatValue5 = this.floatValue5 + (floatValue7 - this.floatValue5) * 0.15F;
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
                  flag2
               );
            }
         } else {
            this.flag = false;
            this.floatValue5 = 0.0F;
            this.flag2 = false;
            this.flag3 = false;
         }
      }
   }

   private void invoke2() {
      float floatValue8 = this.prozrachnost.getValue();
      String text = this.stilistika.getValue();
      if (text.equals("Светлый")) {
         this.intValue = RenderManager.RenderManagerState.compute32(240, 240, 245, (int)(255.0F * floatValue8));
         this.intValue2 = RenderManager.RenderManagerState.compute32(220, 220, 225, (int)(200.0F * floatValue8));
         this.intValue3 = RenderManager.RenderManagerState.compute32(200, 200, 200, (int)(180.0F * floatValue8));
         this.intValue4 = RenderManager.RenderManagerState.compute32(170, 170, 170, (int)(255.0F * floatValue8));
         this.intValue5 = RenderManager.RenderManagerState.compute32(30, 30, 30, 255);
         this.intValue6 = RenderManager.RenderManagerState.compute32(100, 100, 100, 255);
      } else if (text.equals("Блюр")) {
         this.intValue = RenderManager.RenderManagerState.compute32(10, 10, 10, (int)(120.0F * floatValue8));
         this.intValue2 = RenderManager.RenderManagerState.compute32(30, 30, 30, (int)(90.0F * floatValue8));
         this.intValue3 = RenderManager.RenderManagerState.compute32(255, 255, 255, (int)(40.0F * floatValue8));
         this.intValue4 = RenderManager.RenderManagerState.compute32(255, 255, 255, (int)(90.0F * floatValue8));
         this.intValue5 = RenderManager.RenderManagerState.compute32(250, 250, 250, 255);
         this.intValue6 = RenderManager.RenderManagerState.compute32(200, 200, 200, 255);
      } else if (text.equals("Неоморфизм")) {
         this.intValue = NeumorphismRenderer.compute(floatValue8);
         this.intValue2 = NeumorphismRenderer.compute(floatValue8);
         this.intValue3 = RenderManager.RenderManagerState.compute32(0, 0, 0, 0);
         this.intValue4 = RenderManager.RenderManagerState.compute32(0, 0, 0, 0);
         this.intValue5 = NeumorphismRenderer.compute2(1.0F);
         this.intValue6 = NeumorphismRenderer.compute3(1.0F);
      } else {
         this.intValue = RenderManager.RenderManagerState.compute32(25, 25, 26, (int)(255.0F * floatValue8));
         this.intValue2 = RenderManager.RenderManagerState.compute32(35, 35, 35, (int)(170.0F * floatValue8));
         this.intValue3 = RenderManager.RenderManagerState.compute32(78, 78, 78, (int)(176.0F * floatValue8));
         this.intValue4 = RenderManager.RenderManagerState.compute32(120, 120, 120, (int)(255.0F * floatValue8));
         this.intValue5 = RenderManager.RenderManagerState.compute32(240, 240, 240, 255);
         this.intValue6 = RenderManager.RenderManagerState.compute32(200, 200, 200, 255);
      }

      this.invoke3(floatValue8, text);
   }

   private void invoke3(float f, String string) {
      Theme theme = WildClient.INSTANCE != null && WildClient.INSTANCE.themeManager != null ? WildClient.INSTANCE.themeManager.getTheme() : Theme.WILD;
      boolean flag8 = "Светлый".equals(string) || THEME_PALETTE.check(theme) || NeumorphismRenderer.check13();
      ColorScheme colorScheme = ColorScheme.resolve2(theme, flag8);
      int intValue = ColorUtils.compute14(colorScheme.getIntValue14(), colorScheme.getIntValue15(), 0.42F);
      if (flag8 && !"Неоморфизм".equals(string)) {
         this.intValue = ColorUtils.compute2(ColorUtils.compute14(-196865, colorScheme.getIntValue14(), 0.026F), (int)(184.0F * f));
         this.intValue2 = ColorUtils.compute2(ColorUtils.compute14(-1, colorScheme.getIntValue15(), 0.04F), (int)(210.0F * f));
         this.intValue3 = ColorUtils.compute2(ColorUtils.compute14(-15261133, intValue, 0.34F), (int)(48.0F * f));
         this.intValue4 = ColorUtils.compute2(ColorUtils.compute14(-15261133, intValue, 0.56F), (int)(92.0F * f));
         this.intValue5 = ColorUtils.compute2(ColorUtils.compute14(-15722718, colorScheme.getIntValue14(), 0.035F), 255);
         this.intValue6 = ColorUtils.compute2(ColorUtils.compute14(-12168086, colorScheme.getIntValue15(), 0.055F), 255);
      } else if ("Феррофлюид".equals(string)) {
         this.intValue = ColorUtils.compute2(ColorUtils.compute14(-15657182, colorScheme.getIntValue14(), 0.1F), (int)(230.0F * f));
         this.intValue2 = ColorUtils.compute2(ColorUtils.compute14(-15393492, colorScheme.getIntValue15(), 0.14F), (int)(235.0F * f));
         this.intValue3 = ColorUtils.compute2(intValue, (int)(72.0F * f));
         this.intValue4 = ColorUtils.compute2(intValue, (int)(122.0F * f));
         this.intValue5 = RenderManager.RenderManagerState.compute32(246, 248, 255, 255);
         this.intValue6 = RenderManager.RenderManagerState.compute32(188, 197, 214, 255);
      }
   }

   private void invoke4(
      RenderManager renderManager2,
      DrawContext drawContext,
      PlayerEntity playerEntity,
      NameTags.NameTagsData4 nameTagsData44,
      float f,
      float g,
      boolean bl,
      float h,
      float i
   ) {
      float floatValue9 = (float)MathHelper.clamp(16.0 / Math.max(nameTagsData44.distance(), 12.0), 0.75, 1.15);
      float floatValue10 = Math.abs(nameTagsData44.feetY() - nameTagsData44.headY());
      float floatValue11 = Math.max(4.0F * floatValue9, nameTagsData44.boxRight() - nameTagsData44.boxLeft());
      float floatValue12 = nameTagsData44.boxLeft();
      float floatValue13 = Math.min(nameTagsData44.headY(), nameTagsData44.feetY());
      float floatValue14 = 6.0F * floatValue9;
      String text2 = playerEntity.getGameProfile() != null ? playerEntity.getGameProfile().getName() : playerEntity.getName().getString();
      String text3 = ProtectInfo.resolve7(text2);
      String text4 = TargetHud.resolve10(playerEntity);
      int intValue2 = TargetHud.compute3(playerEntity, RenderManager.RenderManagerState.compute31(255, 70, 70), 255);
      boolean flag9 = FriendCommand.check(text2);
      if (this.rezhimObvodki.is("Боксы")) {
         float floatValue15 = floatValue11 * 0.25F;
         float floatValue16 = Math.max(1.0F, 1.5F * floatValue9);
         long longValue = System.currentTimeMillis();
         float floatValue17 = (float)(Math.sin(longValue / 200.0) + 1.0) / 2.0F;
         int intValue3 = this.compute3(RenderManager.RenderManagerState.compute32(150, 150, 150, 150), RenderManager.RenderManagerState.compute32(255, 255, 255, 220), floatValue17 * 0.4F);
         intValue3 = this.compute6(intValue3, i);
         renderManager2.invoke5(floatValue12, floatValue13, floatValue15, floatValue16, 0.0F, intValue3);
         renderManager2.invoke5(floatValue12, floatValue13, floatValue16, floatValue15, 0.0F, intValue3);
         renderManager2.invoke5(floatValue12 + floatValue11 - floatValue15, floatValue13, floatValue15, floatValue16, 0.0F, intValue3);
         renderManager2.invoke5(floatValue12 + floatValue11 - floatValue16, floatValue13, floatValue16, floatValue15, 0.0F, intValue3);
         renderManager2.invoke5(floatValue12, floatValue13 + floatValue10 - floatValue16, floatValue15, floatValue16, 0.0F, intValue3);
         renderManager2.invoke5(floatValue12, floatValue13 + floatValue10 - floatValue15, floatValue16, floatValue15, 0.0F, intValue3);
         renderManager2.invoke5(floatValue12 + floatValue11 - floatValue15, floatValue13 + floatValue10 - floatValue16, floatValue15, floatValue16, 0.0F, intValue3);
         renderManager2.invoke5(floatValue12 + floatValue11 - floatValue16, floatValue13 + floatValue10 - floatValue15, floatValue16, floatValue15, 0.0F, intValue3);
      } else if (this.rezhimObvodki.is("Скелет")) {
         this.invoke8(renderManager2, playerEntity, i);
      } else {
         this.rezhimObvodki.is("Не рендерить");
      }

      float floatValue18 = TargetHud.measure((LivingEntity)playerEntity);
      String text5 = Integer.toString(Math.round(floatValue18));
      String text6 = " HP";
      String text7 = flag9 ? "[FRIEND] " : "";
      float floatValue19 = 22.0F * floatValue9;
      float floatValue20 = 22.0F * floatValue9;
      float floatValue21 = 16.0F * floatValue9;
      float floatValue22 = 6.0F * floatValue9;
      float floatValue23 = 4.0F * floatValue9;
      float floatValue24 = 22.0F * floatValue9;
      float floatValue25 = text7.isEmpty() ? 0.0F : RenderManager.resolve7(FontRegistry.fontObject, text7, floatValue19).floatValue;
      float floatValue26 = RenderManager.resolve7(FontRegistry.fontObject, text3, floatValue19).floatValue;
      float floatValue27 = text4.isEmpty() ? 0.0F : RenderManager.resolve7(FontRegistry.fontObject, text4, floatValue20).floatValue;
      float floatValue28 = RenderManager.resolve7(FontRegistry.fontObject, text5, floatValue19).floatValue;
      float floatValue29 = RenderManager.resolve7(FontRegistry.fontObject, text6, floatValue19).floatValue;
      float floatValue30 = floatValue25 + (text4.isEmpty() ? 0.0F : floatValue27 + floatValue23) + floatValue26 + floatValue23 + floatValue28 + floatValue29;
      float floatValue31 = floatValue30 + floatValue22 * 2.0F;
      float floatValue32 = floatValue31 + (this.pokazyvatGolovu.isEnabled() ? floatValue24 + floatValue23 : 0.0F);
      float floatValue33 = nameTagsData44.screenX() - floatValue32 / 2.0F;
      float floatValue34 = floatValue13 - floatValue24 - 8.0F * floatValue9;
      List items = this.items2;
      items.clear();
      if (this.bronya.isEnabled() && !GraphicsQuality.isUltraLow()) {
         ItemStack itemStack3 = playerEntity.getEquippedStack(EquipmentSlot.HEAD);
         if (!itemStack3.isEmpty()) {
            items.add(itemStack3);
         }

         ItemStack itemStack4 = playerEntity.getEquippedStack(EquipmentSlot.CHEST);
         if (!itemStack4.isEmpty()) {
            items.add(itemStack4);
         }

         ItemStack itemStack5 = playerEntity.getEquippedStack(EquipmentSlot.LEGS);
         if (!itemStack5.isEmpty()) {
            items.add(itemStack5);
         }

         ItemStack itemStack6 = playerEntity.getEquippedStack(EquipmentSlot.FEET);
         if (!itemStack6.isEmpty()) {
            items.add(itemStack6);
         }
      }

      float floatValueMarkTop = floatValue34;
      if (!items.isEmpty()) {
         float floatValue35 = 18.0F * floatValue9;
         float floatValue36 = 4.0F * floatValue9;
         float floatValue37 = items.size() * floatValue35 + (items.size() - 1) * floatValue36;
         float floatValue38 = nameTagsData44.screenX() - floatValue37 / 2.0F;
         float floatValue39 = floatValue34 - floatValue35 - 6.0F * floatValue9;
         floatValueMarkTop = floatValue39;
         int intValue4 = 0;

         for (ItemStack itemStack7 : (Iterable<ItemStack>)items) {
            this.invoke5(renderManager2, floatValue38, floatValue39, floatValue35, floatValue35, floatValue14, i, itemStack7);
            this.invoke22(playerEntity, itemStack7, floatValue38 + floatValue9, floatValue39 + floatValue9, intValue4, floatValue9, 0);
            floatValue38 += floatValue35 + floatValue36;
            intValue4++;
         }
      }

      float floatValue40 = floatValue33;
      if (this.pokazyvatGolovu.isEnabled()) {
         this.invoke21(renderManager2, floatValue33, floatValue34, floatValue24, floatValue24, floatValue14, i, 0.0F);
         float floatValue41 = floatValue24 - 4.0F * floatValue9;
         this.invoke17(renderManager2, playerEntity, floatValue33 + 2.0F * floatValue9, floatValue34 + 2.0F * floatValue9, floatValue41, i);
         floatValue40 = floatValue33 + (floatValue24 + floatValue23);
      }

      if (flag9) {
         int intValue5 = this.compute6(RenderManager.RenderManagerState.compute32(25, 80, 25, 255), i);
         renderManager2.invoke5(floatValue40, floatValue34, floatValue31, floatValue24, floatValue14, intValue5);
      } else {
         this.invoke21(renderManager2, floatValue40, floatValue34, floatValue31, floatValue24, floatValue14, i, 0.0F);
      }

      this.items4.add(new NameTags.NameTagsData3(floatValue40, floatValue34, floatValue31, floatValue24, text2));
      if (this.znachokKlienta.isEnabled() && ClientUserRegistry.isClientUser(text2)) {
         float floatValueMarkSize = 19.0F * floatValue9;
         float floatValueMarkWidth = RenderManager.resolve7(BrandMark.font(), BrandMark.GLYPH, floatValueMarkSize).floatValue;
         renderManager2.invoke69(
            BrandMark.font(),
            nameTagsData44.screenX() - floatValueMarkWidth / 2.0F,
            floatValueMarkTop - 5.0F * floatValue9,
            floatValueMarkSize,
            BrandMark.GLYPH,
            this.compute6(INT_VALUE_CLIENT_MARK, i)
         );
      }

      floatValue40 += floatValue22;
      float floatValue42 = floatValue34 + 15.0F * floatValue9;
      if (!text7.isEmpty()) {
         renderManager2.invoke69(FontRegistry.fontObject, floatValue40, floatValue42, floatValue19, text7, this.compute6(RenderManager.RenderManagerState.compute32(60, 150, 255, 255), i));
         floatValue40 += floatValue25;
      }

      if (!text4.isEmpty()) {
         renderManager2.invoke69(FontRegistry.fontObject, floatValue40, floatValue42, floatValue20, text4, this.compute6(intValue2, i));
         floatValue40 += floatValue27 + floatValue23;
      }

      this.invoke19(renderManager2, FontRegistry.fontObject, floatValue40, floatValue42, floatValue19, text3, this.compute6(flag9 ? INT_VALUE : this.intValue5, i));
      floatValue40 += floatValue26 + floatValue23;
      renderManager2.invoke69(FontRegistry.fontObject, floatValue40, floatValue42, floatValue19, text5, this.compute6(this.compute(floatValue18, playerEntity.getMaxHealth()), i));
      floatValue40 += floatValue28;
      renderManager2.invoke69(FontRegistry.fontObject, floatValue40, floatValue42, floatValue19, text6, this.compute6(RenderManager.RenderManagerState.compute32(150, 150, 150, 255), i));
      float floatValue43 = MathHelper.clamp(floatValue18 / playerEntity.getMaxHealth(), 0.0F, 1.0F);
      int intValue6 = this.compute6(RenderManager.RenderManagerState.compute32(60, 150, 255, 255), i);
      float floatValue44 = 2.0F * floatValue9;
      float floatValue45 = floatValue12 - floatValue44 - 4.0F * floatValue9;
      if (this.polosaHp.isEnabled()) {
         renderManager2.invoke5(floatValue45, floatValue13, floatValue44, floatValue10, 1.0F, this.compute6(RenderManager.RenderManagerState.compute32(0, 0, 0, 100), i));
         float floatValue46 = floatValue10 * floatValue43;
         renderManager2.invoke5(floatValue45, floatValue13 + (floatValue10 - floatValue46), floatValue44, floatValue46, 1.0F, intValue6);
      }

      float floatValue47 = floatValue12 + floatValue11 + 8.0F * floatValue9;
      float floatValue48 = floatValue13;
      float floatValue49 = 20.0F * floatValue9;
      if (this.effekty.isEnabled()) {
         for (StatusEffectInstance statusEffectInstance : playerEntity.getStatusEffects()) {
            String text8 = Text.translatable(statusEffectInstance.getTranslationKey()).getString();
            int intValue7 = statusEffectInstance.getAmplifier() + 1;
            String text9 = text8 + (intValue7 > 1 ? " " + intValue7 : "");
            int intValue8 = ((StatusEffect)statusEffectInstance.getEffectType().value()).isBeneficial() ? this.intValue5 : RenderManager.RenderManagerState.compute32(255, 60, 60, 255);
            renderManager2.invoke69(FontRegistry.fontObject, floatValue47, floatValue48 + 10.0F * floatValue9, floatValue49, text9, this.compute6(intValue8, i));
            floatValue48 += 12.0F * floatValue9;
         }
      }

      List items2 = this.items3;
      items2.clear();
      if (this.levayaRuka.isEnabled() && !playerEntity.getOffHandStack().isEmpty()) {
         items2.add(playerEntity.getOffHandStack());
      }

      if (this.pravayaRuka.isEnabled() && !playerEntity.getMainHandStack().isEmpty()) {
         items2.add(playerEntity.getMainHandStack());
      }

      float floatValue50 = floatValue13 + floatValue10 + 6.0F * floatValue9;
      float floatValue51 = 22.0F * floatValue9;
      float floatValue52 = 4.0F * floatValue9;
      if (this.otobrazhatPolnyeImena.isEnabled()) {
         float floatValue53 = floatValue50;

         for (int intValue9 = 0; intValue9 < items2.size(); intValue9++) {
            floatValue53 += this.measure(renderManager2, playerEntity, (ItemStack)items2.get(intValue9), nameTagsData44.screenX(), floatValue53, floatValue9, i, 99 + intValue9, 1)
               + 3.0F * floatValue9;
         }
      } else {
         float floatValue54 = items2.size() * floatValue51 + Math.max(0, items2.size() - 1) * floatValue52;
         float floatValue55 = nameTagsData44.screenX() - floatValue54 / 2.0F;

         for (int intValue10 = 0; intValue10 < items2.size(); intValue10++) {
            ItemStack itemStack8 = (ItemStack)items2.get(intValue10);
            this.invoke5(renderManager2, floatValue55, floatValue50, floatValue51, floatValue51, floatValue14, i, itemStack8);
            float floatValue56 = 3.0F * floatValue9;
            this.invoke22(playerEntity, itemStack8, floatValue55 + floatValue56, floatValue50 + floatValue56, 99 + intValue10, floatValue9, 1);
            floatValue55 += floatValue51 + floatValue52;
         }
      }
   }

   private float measure(RenderManager renderManager3, PlayerEntity playerEntity, ItemStack itemStack, float f, float g, float h, float i, int j, int k) {
      float floatValue57 = 22.0F * h;
      float floatValue58 = 4.0F * h;
      float floatValue59 = 7.0F * h;
      float floatValue60 = 18.0F * h;
      float floatValue61 = Math.max(70.0F * h, Math.min(190.0F * h, CLIENT.getWindow().getFramebufferWidth() * 0.28F));
      String text10 = this.resolve7(this.resolve6(itemStack, true), floatValue60, floatValue61);
      float floatValue62 = RenderManager.resolve7(FontRegistry.fontObject2, text10, floatValue60).floatValue;
      float floatValue63 = floatValue62 + floatValue59 * 2.0F;
      float floatValue64 = floatValue57 + floatValue58 + floatValue63;
      float floatValue65 = f - floatValue64 / 2.0F;
      float floatValue66 = 6.0F * h;
      this.invoke5(renderManager3, floatValue65, g, floatValue57, floatValue57, floatValue66, i, itemStack);
      this.invoke22(playerEntity, itemStack, floatValue65 + 3.0F * h, g + 3.0F * h, j, h, k);
      float floatValue67 = floatValue65 + floatValue57 + floatValue58;
      this.invoke5(renderManager3, floatValue67, g, floatValue63, floatValue57, floatValue66, i, itemStack);
      this.invoke20(renderManager3, FontRegistry.fontObject2, floatValue67 + floatValue59, g + 15.0F * h, floatValue60, text10, itemStack, j, i);
      return floatValue57;
   }

   private void invoke5(RenderManager renderManager4, float f, float g, float h, float i, float j, float k, ItemStack itemStack) {
      if (this.podsvetkaPredmetov.isEnabled()) {
         int intValue11 = this.compute4(itemStack, k);
         renderManager4.invoke41(f, g, h, i, j, 5.0F, 1.0F, this.compute6(intValue11, 0.55F));
      }

      this.invoke21(renderManager4, f, g, h, i, j, k, 0.0F);
   }

   public static void capturePlayerSkeleton(PlayerEntityRenderState playerEntityRenderState, PlayerEntityModel playerEntityModel, MatrixStack matrixStack) {
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
         NameTags nameTags = WildClient.INSTANCE.moduleManager.getModule(NameTags.class);
         if (nameTags != null) {
            nameTags.invoke6(playerEntityRenderState, playerEntityModel, matrixStack);
         }
      }
   }

   private void invoke6(PlayerEntityRenderState playerEntityRenderState, PlayerEntityModel playerEntityModel, MatrixStack matrixStack) {
      if (this.enabled && CLIENT != null && CLIENT.player != null && CLIENT.world != null) {
         if (!(CLIENT.currentScreen instanceof InventoryScreen)) {
            if (this.rezhimOtobrazheniya.is("Legacy") && this.rezhimObvodki.is("Скелет")) {
               if (playerEntityRenderState != null && playerEntityModel != null && matrixStack != null) {
                  if (!playerEntityRenderState.spectator && !playerEntityRenderState.invisible && !playerEntityRenderState.invisibleToPlayer) {
                     if (playerEntityRenderState.id != CLIENT.player.getId() || CLIENT.options.getPerspective() != Perspective.FIRST_PERSON) {
                        Vec3d vec3d3 = CLIENT.gameRenderer.getCamera().getPos();
                        ArrayList arrayList = new ArrayList(14);
                        Vec3d vec3d4 = this.resolve(playerEntityModel.body, matrixStack, vec3d3, 0.0F, 0.0F, 0.0F);
                        Vec3d vec3d5 = this.resolve(playerEntityModel.body, matrixStack, vec3d3, 0.0F, 6.0F, 0.0F);
                        Vec3d vec3d6 = this.resolve(playerEntityModel.body, matrixStack, vec3d3, 0.0F, 12.0F, 0.0F);
                        Vec3d vec3d7 = this.resolve(playerEntityModel.head, matrixStack, vec3d3, 0.0F, -8.0F, 0.0F);
                        Vec3d vec3d8 = this.resolve(playerEntityModel.head, matrixStack, vec3d3, 0.0F, 0.0F, 0.0F);
                        Vec3d vec3d9 = this.resolve(playerEntityModel.rightArm, matrixStack, vec3d3, 0.0F, 0.0F, 0.0F);
                        Vec3d vec3d10 = this.resolve(playerEntityModel.rightArm, matrixStack, vec3d3, 0.0F, 4.5F, 0.0F);
                        Vec3d vec3d11 = this.resolve(playerEntityModel.rightArm, matrixStack, vec3d3, 0.0F, 10.0F, 0.0F);
                        Vec3d vec3d12 = this.resolve(playerEntityModel.leftArm, matrixStack, vec3d3, 0.0F, 0.0F, 0.0F);
                        Vec3d vec3d13 = this.resolve(playerEntityModel.leftArm, matrixStack, vec3d3, 0.0F, 4.5F, 0.0F);
                        Vec3d vec3d14 = this.resolve(playerEntityModel.leftArm, matrixStack, vec3d3, 0.0F, 10.0F, 0.0F);
                        Vec3d vec3d15 = this.resolve(playerEntityModel.rightLeg, matrixStack, vec3d3, 0.0F, 0.0F, 0.0F);
                        Vec3d vec3d16 = this.resolve(playerEntityModel.rightLeg, matrixStack, vec3d3, 0.0F, 6.0F, 0.0F);
                        Vec3d vec3d17 = this.resolve(playerEntityModel.rightLeg, matrixStack, vec3d3, 0.0F, 12.0F, 0.0F);
                        Vec3d vec3d18 = this.resolve(playerEntityModel.leftLeg, matrixStack, vec3d3, 0.0F, 0.0F, 0.0F);
                        Vec3d vec3d19 = this.resolve(playerEntityModel.leftLeg, matrixStack, vec3d3, 0.0F, 6.0F, 0.0F);
                        Vec3d vec3d20 = this.resolve(playerEntityModel.leftLeg, matrixStack, vec3d3, 0.0F, 12.0F, 0.0F);
                        this.invoke7(arrayList, vec3d4, vec3d5);
                        this.invoke7(arrayList, vec3d5, vec3d6);
                        this.invoke7(arrayList, vec3d7, vec3d8);
                        this.invoke7(arrayList, vec3d12, vec3d9);
                        this.invoke7(arrayList, vec3d18, vec3d15);
                        this.invoke7(arrayList, vec3d12, vec3d13);
                        this.invoke7(arrayList, vec3d13, vec3d14);
                        this.invoke7(arrayList, vec3d9, vec3d10);
                        this.invoke7(arrayList, vec3d10, vec3d11);
                        this.invoke7(arrayList, vec3d18, vec3d19);
                        this.invoke7(arrayList, vec3d19, vec3d20);
                        this.invoke7(arrayList, vec3d15, vec3d16);
                        this.invoke7(arrayList, vec3d16, vec3d17);
                        if (!arrayList.isEmpty()) {
                           this.valuesByKey4.put(playerEntityRenderState.id, new NameTags.NameTagsData2(arrayList, System.currentTimeMillis()));
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private Vec3d resolve(ModelPart modelPart, MatrixStack matrixStack, Vec3d vec3d, float f, float g, float h) {
      this.matrixStack.loadIdentity();
      this.matrixStack.peek().getPositionMatrix().set(matrixStack.peek().getPositionMatrix());
      modelPart.applyTransform(this.matrixStack);
      Matrix4f matrix4f = this.matrixStack.peek().getPositionMatrix();
      Vector3f vector3f = this.vector3f.set(f * 0.0625F, g * 0.0625F, h * 0.0625F);
      matrix4f.transformPosition(vector3f);
      return vec3d.add(vector3f.x, vector3f.y, vector3f.z);
   }

   private void invoke7(List<NameTags.NameTagsData> list, Vec3d vec3d, Vec3d vec3d2) {
      if (vec3d != null && vec3d2 != null) {
         list.add(new NameTags.NameTagsData(vec3d, vec3d2));
      }
   }

   private void invoke8(RenderManager renderManager5, PlayerEntity playerEntity, float f) {
      NameTags.NameTagsData2 nameTagsData2 = this.valuesByKey4.get(playerEntity.getId());
      if (nameTagsData2 != null && System.currentTimeMillis() - nameTagsData2.capturedAt() <= 250L) {
         for (NameTags.NameTagsData nameTagsData : nameTagsData2.bones()) {
            this.invoke10(renderManager5, nameTagsData.start(), nameTagsData.end(), f);
         }
      }
   }

   private void invoke9() {
      long longValue2 = System.currentTimeMillis();
      this.valuesByKey4.entrySet().removeIf(entry -> longValue2 - entry.getValue().capturedAt() > 250L);
   }

   private void invoke10(RenderManager renderManager6, Vec3d vec3d, Vec3d vec3d2, float f) {
      Vec3d vec3d21 = this.resolve2(vec3d.x, vec3d.y, vec3d.z);
      Vec3d vec3d22 = this.resolve2(vec3d2.x, vec3d2.y, vec3d2.z);
      if (vec3d21 != null && vec3d22 != null) {
         double doubleValue = CLIENT.gameRenderer.getCamera().getPos().distanceTo(vec3d);
         float floatValue68 = MathHelper.clamp((float)(12.0 / Math.max(doubleValue, 1.0)), 1.0F, 10.0F);
         float floatValue69 = f * this.prozrachnost.getValue();
         int intValue12 = this.compute6(RenderManager.RenderManagerState.compute32(200, 200, 210, 255), floatValue69);
         float floatValue70 = (float)Math.hypot(vec3d22.x - vec3d21.x, vec3d22.y - vec3d21.y);
         float floatValue71 = (float)Math.toDegrees(Math.atan2(vec3d22.y - vec3d21.y, vec3d22.x - vec3d21.x));
         renderManager6.invoke56((float)vec3d21.x, (float)vec3d21.y);
         renderManager6.invoke54(floatValue71);
         if (!(doubleValue > 12.0) && !(floatValue68 < 2.0F)) {
            int intValue13 = this.compute6(RenderManager.RenderManagerState.compute32(20, 20, 20, 180), floatValue69);
            int intValue14 = this.compute6(RenderManager.RenderManagerState.compute32(255, 255, 255, 255), floatValue69);
            renderManager6.invoke5(0.0F, -floatValue68 * 0.3F, floatValue70, floatValue68 * 0.6F, 0.0F, intValue12);
            renderManager6.invoke5(0.0F, -floatValue68 * 0.1F, floatValue70, floatValue68 * 0.2F, 0.0F, intValue14);
         } else {
            renderManager6.invoke5(0.0F, -floatValue68 / 2.0F, floatValue70, floatValue68, 0.0F, intValue12);
         }

         renderManager6.invoke55();
         renderManager6.invoke57();
      }
   }

   private Vec3d resolve2(double d, double e, double f) {
      Camera camera = CLIENT.gameRenderer.getCamera();
      Vec3d vec3d23 = new Vec3d(d, e, f);
      if (vec3d23.squaredDistanceTo(camera.getPos()) < 1.0E-6) {
         return null;
      } else {
         Vec3d vec3d24 = MathUtils.resolve(vec3d23);
         return !(vec3d24.z <= 0.001F) && !(vec3d24.z > 1.0) ? vec3d24 : null;
      }
   }

   private void invoke11(float f) {
      this.valuesByKey.clear();
      this.valuesByKey2.clear();
      this.valuesByKey3.clear();
      if (CLIENT.world != null && CLIENT.player != null) {
         if (this.tseli.isEnabled("Игроки")) {
            this.invoke12(f);
         }

         if (this.tseli.isEnabled("Мобы") || this.tseli.isEnabled("Животные") || this.check4()) {
            this.invoke13(f);
         }

         if (this.tseli.isEnabled("Предметы")) {
            this.invoke14(f);
         }
      }
   }

   private void invoke12(float f) {
      for (PlayerEntity playerEntity3 : CLIENT.world.getPlayers()) {
         if (playerEntity3 != null
            && playerEntity3.isAlive()
            && (playerEntity3 != CLIENT.player || !CLIENT.options.getPerspective().isFirstPerson())
            && (this.tseli.isEnabled("Голые") || playerEntity3.getArmor() != 0 || playerEntity3 == CLIENT.player)) {
            NameTags.NameTagsData4 nameTagsData45 = this.resolve3(f, playerEntity3, playerEntity3.getHeight() + 0.2, 0.02);
            if (nameTagsData45 != null) {
               this.valuesByKey.put(playerEntity3, nameTagsData45);
            }
         }
      }
   }

   private void invoke13(float f) {
      AutoVillageTrade autoVillageTrade = this.resolve5();

      for (Entity entity2 : CLIENT.world.getEntities()) {
         if (entity2 instanceof LivingEntity livingEntity3
            && livingEntity3.isAlive()
            && livingEntity3 != CLIENT.player
            && !(livingEntity3 instanceof PlayerEntity)
            && !(livingEntity3 instanceof ArmorStandEntity)) {
            boolean flag10 = livingEntity3 instanceof VillagerEntity villagerEntity3 && autoVillageTrade != null && autoVillageTrade.resolve(villagerEntity3) != null;
            if ((!this.check6(livingEntity3) || this.tseli.isEnabled("Мобы") || flag10)
               && (!this.check7(livingEntity3) || this.tseli.isEnabled("Животные"))
               && (this.check6(livingEntity3) || this.check7(livingEntity3) || flag10)) {
               NameTags.NameTagsData4 nameTagsData46 = this.resolve3(f, livingEntity3, livingEntity3.getHeight() + 0.18, 0.02);
               if (nameTagsData46 != null) {
                  this.valuesByKey2.put(livingEntity3, nameTagsData46);
               }
            }
         }
      }
   }

   private void invoke14(float f) {
      for (Entity entity3 : CLIENT.world.getEntities()) {
         if (entity3 instanceof ItemEntity itemEntity3 && itemEntity3.isAlive() && !itemEntity3.getStack().isEmpty()) {
            NameTags.NameTagsData4 nameTagsData47 = this.resolve3(f, itemEntity3, 0.52, 0.0);
            if (nameTagsData47 != null) {
               this.valuesByKey3.put(itemEntity3, nameTagsData47);
            }
         }
      }
   }

   private NameTags.NameTagsData4 resolve3(float f, Entity entity, double d, double e) {
      Camera camera2 = CLIENT.gameRenderer.getCamera();
      Vec3d vec3d25 = camera2.getPos();
      Vec3d vec3d26 = entity.getLerpedPos(f);
      Box box2 = entity.getBoundingBox();
      Vec3d vec3d27 = entity.getPos();
      Box box3 = box2.offset(vec3d26.x - vec3d27.x, vec3d26.y - vec3d27.y, vec3d26.z - vec3d27.z);
      Box box4 = new Box(box3.minX - 0.02, vec3d26.y + e, box3.minZ - 0.02, box3.maxX + 0.02, vec3d26.y + d, box3.maxZ + 0.02);
      NameTags.NameTagsData4 nameTagsData48 = this.resolve4(box4, vec3d25);
      if (nameTagsData48 != null) {
         return nameTagsData48;
      } else {
         Vec3d vec3d28 = new Vec3d(vec3d26.x, vec3d26.y + d, vec3d26.z);
         Vec3d vec3d29 = new Vec3d(vec3d26.x, vec3d26.y + e, vec3d26.z);
         if (vec3d28.squaredDistanceTo(vec3d25) < 1.0E-6) {
            return null;
         } else {
            Vec3d vec3d30 = MathUtils.resolve(vec3d28);
            Vec3d vec3d31 = MathUtils.resolve(vec3d29);
            if (vec3d30.z <= 0.001F || vec3d30.z > 1.0) {
               return null;
            } else if (!(vec3d31.z <= 0.001F) && !(vec3d31.z > 1.0)) {
               double doubleValue2 = vec3d25.distanceTo(vec3d28);
               float floatValue72 = Math.abs((float)vec3d31.y - (float)vec3d30.y);
               float floatValue73 = floatValue72 * 0.45F;
               float floatValue74 = (float)vec3d30.x;
               return new NameTags.NameTagsData4(floatValue74, (float)vec3d30.y, (float)vec3d31.y, (float)vec3d30.z, doubleValue2, floatValue74 - floatValue73 / 2.0F, floatValue74 + floatValue73 / 2.0F);
            } else {
               return null;
            }
         }
      }
   }

   private NameTags.NameTagsData4 resolve4(Box box, Vec3d vec3d) {
      float floatValue75 = Float.POSITIVE_INFINITY;
      float floatValue76 = Float.POSITIVE_INFINITY;
      float floatValue77 = Float.NEGATIVE_INFINITY;
      float floatValue78 = Float.NEGATIVE_INFINITY;
      float floatValue79 = 0.0F;
      double doubleValue3 = (box.minX + box.maxX) * 0.5;
      double doubleValue4 = (box.minY + box.maxY) * 0.5;
      double doubleValue5 = (box.minZ + box.maxZ) * 0.5;

      for (int intValue15 = 0; intValue15 < 2; intValue15++) {
         double doubleValue6 = intValue15 == 0 ? box.minX : box.maxX;

         for (int intValue16 = 0; intValue16 < 2; intValue16++) {
            double doubleValue7 = intValue16 == 0 ? box.minY : box.maxY;

            for (int intValue17 = 0; intValue17 < 2; intValue17++) {
               double doubleValue8 = intValue17 == 0 ? box.minZ : box.maxZ;
               Vec3d vec3d32 = MathUtils.resolve(new Vec3d(doubleValue6, doubleValue7, doubleValue8));
               if (vec3d32 == null || vec3d32.z <= 0.001F || vec3d32.z > 1.0) {
                  return null;
               }

               floatValue75 = Math.min(floatValue75, (float)vec3d32.x);
               floatValue76 = Math.min(floatValue76, (float)vec3d32.y);
               floatValue77 = Math.max(floatValue77, (float)vec3d32.x);
               floatValue78 = Math.max(floatValue78, (float)vec3d32.y);
               floatValue79 += (float)vec3d32.z;
            }
         }
      }

      if (Float.isFinite(floatValue75) && Float.isFinite(floatValue76) && Float.isFinite(floatValue77) && Float.isFinite(floatValue78)) {
         double doubleValue9 = vec3d.distanceTo(new Vec3d(doubleValue3, doubleValue4, doubleValue5));
         return new NameTags.NameTagsData4((floatValue75 + floatValue77) * 0.5F, floatValue76, floatValue78, floatValue79 / 8.0F, doubleValue9, floatValue75, floatValue77);
      } else {
         return null;
      }
   }

   private boolean check2(NameTags.NameTagsData4 nameTagsData49) {
      return nameTagsData49 == null || nameTagsData49.depth() <= 0.001F || nameTagsData49.depth() > 1.0F;
   }

   private int compute(float f, float g) {
      float floatValue80 = MathHelper.clamp(f / Math.max(1.0F, g), 0.0F, 1.0F);
      int intValue18 = floatValue80 >= 0.5F ? (int)(255.0F * (1.0F - floatValue80) * 2.0F) : 255;
      int intValue19 = floatValue80 >= 0.5F ? 255 : (int)(255.0F * floatValue80 * 2.0F);
      return RenderManager.RenderManagerState.compute32(intValue18, intValue19, 50, 255);
   }

   private boolean check3(RenderManager renderManager7, VillagerEntity villagerEntity, NameTags.NameTagsData4 nameTagsData410, float f) {
      AutoVillageTrade autoVillageTrade2 = this.resolve5();
      if (autoVillageTrade2 == null) {
         return false;
      } else {
         AutoVillageTrade.AutoVillageTradePriceData autoVillageTradePriceData = autoVillageTrade2.resolve(villagerEntity);
         if (autoVillageTradePriceData != null && autoVillageTradePriceData.itemStack() != null && !autoVillageTradePriceData.itemStack().isEmpty()) {
            ItemStack itemStack9 = autoVillageTradePriceData.itemStack();
            float floatValue81 = (float)MathHelper.clamp(16.0 / Math.max(nameTagsData410.distance(), 12.0), 0.75, 1.15);
            float floatValue82 = 6.0F * floatValue81;
            float floatValue83 = 18.0F * floatValue81;
            float floatValue84 = 22.0F * floatValue81;
            float floatValue85 = 4.0F * floatValue81;
            float floatValue86 = 6.0F * floatValue81;
            String text11 = autoVillageTradePriceData.price() + " изумр. · x" + autoVillageTradePriceData.availableAmount();
            float floatValue87 = RenderManager.resolve7(FontRegistry.fontObject2, text11, floatValue83).floatValue;
            float floatValue88 = floatValue87 + floatValue86 * 2.0F;
            float floatValue89 = floatValue84 + floatValue85 + floatValue88;
            float floatValue90 = nameTagsData410.screenX() - floatValue89 / 2.0F;
            float floatValue91 = nameTagsData410.headY() - 18.0F * floatValue81;
            this.invoke5(renderManager7, floatValue90, floatValue91, floatValue84, floatValue84, floatValue82, f, itemStack9);
            float floatValue92 = (floatValue84 - 16.0F * floatValue81) / 2.0F;
            this.invoke22(CLIENT.player, itemStack9, floatValue90 + floatValue92, floatValue91 + floatValue92, villagerEntity.getId(), floatValue81, 0);
            float floatValue93 = floatValue90 + floatValue84 + floatValue85;
            this.invoke5(renderManager7, floatValue93, floatValue91, floatValue88, floatValue84, floatValue82, f, itemStack9);
            this.invoke20(renderManager7, FontRegistry.fontObject2, floatValue93 + floatValue86, floatValue91 + 15.0F * floatValue81, floatValue83, text11, itemStack9, villagerEntity.getId(), f);
            return true;
         } else {
            return false;
         }
      }
   }

   private AutoVillageTrade resolve5() {
      return WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null ? WildClient.INSTANCE.moduleManager.getModule(AutoVillageTrade.class) : null;
   }

   private boolean check4() {
      AutoVillageTrade autoVillageTrade3 = this.resolve5();
      return autoVillageTrade3 != null && autoVillageTrade3.enabled;
   }

   private void invoke15(RenderManager renderManager8, LivingEntity livingEntity, NameTags.NameTagsData4 nameTagsData411, float f, float g, boolean bl, float h) {
      float floatValue94 = (float)MathHelper.clamp(6.0 / Math.max(nameTagsData411.distance(), 1.0), 0.45, 1.0);
      float floatValue95 = 6.0F * floatValue94;
      String text12 = ProtectInfo.resolve7(livingEntity.getName().getString());
      float floatValue96 = livingEntity.getHealth() + livingEntity.getAbsorptionAmount();
      float floatValue97 = livingEntity.getMaxHealth();
      String text13 = " " + String.format("%.1f", floatValue96).replace(',', '.');
      if (text13.endsWith(".0")) {
         text13 = text13.substring(0, text13.length() - 2);
      }

      float floatValue98 = 22.0F * floatValue94;
      float floatValue99 = RenderManager.resolve7(FontRegistry.fontObject, text12, floatValue98).floatValue;
      float floatValue100 = RenderManager.resolve7(FontRegistry.fontObject, text13, floatValue98).floatValue;
      float floatValue101 = this.pokazyvatGolovu.isEnabled() ? 14.0F * floatValue94 : 0.0F;
      float floatValue102 = this.pokazyvatGolovu.isEnabled() ? 4.0F * floatValue94 : 0.0F;
      float floatValue103 = floatValue101 + floatValue102 + floatValue99 + floatValue100;
      float floatValue104 = floatValue103 + 16.0F * floatValue94;
      float floatValue105 = 17.0F * floatValue94;
      float floatValue106 = nameTagsData411.screenX() - floatValue104 / 2.0F;
      float floatValue107 = nameTagsData411.headY() - 18.0F * floatValue94;
      boolean flag11 = bl && this.check8(f, g, floatValue106, floatValue107, floatValue104, floatValue105);
      String text14 = livingEntity.getUuidAsString();
      float floatValue108 = this.valuesByKey5.getOrDefault(text14, 0.0F);
      float floatValue109 = flag11 && !this.flag ? 1.0F : 0.0F;
      floatValue108 += (floatValue109 - floatValue108) * 0.15F;
      this.valuesByKey5.put(text14, floatValue108);
      this.invoke21(renderManager8, floatValue106, floatValue107, floatValue104, floatValue105, floatValue95, h, floatValue108);
      float floatValue110 = nameTagsData411.screenX() - floatValue103 / 2.0F;
      if (this.pokazyvatGolovu.isEnabled()) {
         this.invoke17(renderManager8, livingEntity, floatValue110, floatValue107 + 1.5F * floatValue94, floatValue101, h);
         floatValue110 += floatValue101 + floatValue102;
      }

      this.invoke19(renderManager8, FontRegistry.fontObject, floatValue110, floatValue107 + 12.2F * floatValue94, floatValue98, text12, this.compute6(this.intValue6, h));
      floatValue110 += floatValue99;
      renderManager8.invoke69(FontRegistry.fontObject, floatValue110, floatValue107 + 12.2F * floatValue94, floatValue98, text13, this.compute6(this.compute(floatValue96, floatValue97), h));
   }

   private void invoke16(RenderManager renderManager9, String string, float f, float g, float h, float i) {
      if (CLIENT.getNetworkHandler() != null) {
         PlayerListEntry playerListEntry = null;

         for (PlayerListEntry playerListEntry2 : CLIENT.getNetworkHandler().getPlayerList()) {
            if (playerListEntry2.getProfile().getName().equalsIgnoreCase(string)) {
               playerListEntry = playerListEntry2;
               break;
            }
         }

         if (playerListEntry != null) {
            try {
               Identifier identifier = playerListEntry.getSkinTextures().texture();
               AbstractTexture abstractTexture = CLIENT.getTextureManager().getTexture(identifier);
               if (abstractTexture != null && abstractTexture.getGlTexture() instanceof GlTexture glTexture && glTexture.getGlId() > 0) {
                  int intValue20 = glTexture.getGlId();
                  GlStateManager._bindTexture(intValue20);
                  renderManager9.invoke65(i);
                  renderManager9.invoke12(intValue20, f, g, h, h, 0.125F, 0.125F, 0.25F, 0.25F, 3.0F);
                  renderManager9.invoke12(intValue20, f, g, h, h, 0.625F, 0.125F, 0.75F, 0.25F, 3.0F);
                  renderManager9.invoke66();
               }
            } catch (Throwable exception) {
            }
         }
      }
   }

   private void invoke17(RenderManager renderManager10, LivingEntity livingEntity, float f, float g, float h, float i) {
      boolean flag12 = false;
      if (livingEntity instanceof PlayerEntity playerEntity4) {
         if (playerEntity4 instanceof AbstractClientPlayerEntity abstractClientPlayerEntity) {
            try {
               Identifier identifier2 = abstractClientPlayerEntity.getSkinTextures().texture();
               AbstractTexture abstractTexture2 = CLIENT.getTextureManager().getTexture(identifier2);
               if (abstractTexture2 != null && abstractTexture2.getGlTexture() instanceof GlTexture glTexture2 && glTexture2.getGlId() > 0) {
                  int intValue21 = glTexture2.getGlId();
                  GlStateManager._bindTexture(intValue21);
                  renderManager10.invoke65(i);
                  renderManager10.invoke12(intValue21, f, g, h, h, 0.125F, 0.125F, 0.25F, 0.25F, 3.0F);
                  renderManager10.invoke12(intValue21, f, g, h, h, 0.625F, 0.125F, 0.75F, 0.25F, 3.0F);
                  renderManager10.invoke66();
                  flag12 = true;
               }
            } catch (Throwable exception2) {
            }
         }

         if (!flag12 && CLIENT.getNetworkHandler() != null) {
            PlayerListEntry playerListEntry3 = null;

            for (PlayerListEntry playerListEntry4 : CLIENT.getNetworkHandler().getPlayerList()) {
               if (playerListEntry4.getProfile().getId().equals(playerEntity4.getUuid()) || playerListEntry4.getProfile().getName().equalsIgnoreCase(playerEntity4.getName().getString())) {
                  playerListEntry3 = playerListEntry4;
                  break;
               }
            }

            if (playerListEntry3 != null) {
               try {
                  Identifier identifier3 = playerListEntry3.getSkinTextures().texture();
                  AbstractTexture abstractTexture3 = CLIENT.getTextureManager().getTexture(identifier3);
                  if (abstractTexture3 != null && abstractTexture3.getGlTexture() instanceof GlTexture glTexture3 && glTexture3.getGlId() > 0) {
                     int intValue22 = glTexture3.getGlId();
                     GlStateManager._bindTexture(intValue22);
                     renderManager10.invoke65(i);
                     renderManager10.invoke12(intValue22, f, g, h, h, 0.125F, 0.125F, 0.25F, 0.25F, 3.0F);
                     renderManager10.invoke12(intValue22, f, g, h, h, 0.625F, 0.125F, 0.75F, 0.25F, 3.0F);
                     renderManager10.invoke66();
                     flag12 = true;
                  }
               } catch (Throwable exception3) {
               }
            }
         }
      } else {
         try {
            EntityRenderer entityRenderer = CLIENT.getEntityRenderDispatcher().getRenderer(livingEntity);
            Identifier identifier4 = null;

            for (Method method : entityRenderer.getClass().getMethods()) {
               if (method.getReturnType() == Identifier.class
                  && method.getParameterCount() == 1
                  && method.getParameterTypes()[0].isAssignableFrom(livingEntity.getClass())) {
                  method.setAccessible(true);
                  identifier4 = (Identifier)method.invoke(entityRenderer, livingEntity);
                  break;
               }
            }

            if (identifier4 != null) {
               AbstractTexture abstractTexture4 = CLIENT.getTextureManager().getTexture(identifier4);
               if (abstractTexture4 != null && abstractTexture4.getGlTexture() instanceof GlTexture glTexture4 && glTexture4.getGlId() > 0) {
                  int intValue23 = glTexture4.getGlId();
                  GlStateManager._bindTexture(intValue23);
                  renderManager10.invoke65(i);
                  float floatValue111 = 0.125F;
                  float floatValue112 = 0.125F;
                  float floatValue113 = 0.25F;
                  float floatValue114 = 0.25F;
                  if (livingEntity instanceof AnimalEntity) {
                     floatValue111 = 0.0F;
                     floatValue112 = 0.125F;
                     floatValue113 = 0.125F;
                     floatValue114 = 0.25F;
                  }

                  renderManager10.invoke12(intValue23, f, g, h, h, floatValue111, floatValue112, floatValue113, floatValue114, 3.0F);
                  renderManager10.invoke66();
                  flag12 = true;
               }
            }
         } catch (Throwable exception4) {
         }
      }

      if (!flag12) {
         int intValue24 = this.compute6(ColorUtils.compute43(30, 30, 30, 120), i);
         renderManager10.invoke5(f, g, h, h, 4.0F, intValue24);
         String text15 = ProtectInfo.resolve7(livingEntity.getName().getString());
         String text16 = text15.isEmpty() ? "?" : text15.substring(0, 1).toUpperCase();
         int intValue25 = this.compute6(ColorUtils.compute43(200, 200, 200, 200), i);
         float floatValue115 = h * 0.65F;
         float floatValue116 = RenderManager.resolve7(FontRegistry.fontObject4, text16, floatValue115).floatValue;
         renderManager10.invoke69(FontRegistry.fontObject4, f + (h - floatValue116) / 2.0F, g + h / 2.0F + floatValue115 * 0.35F, floatValue115, text16, intValue25);
      }
   }

   private void invoke18(RenderManager renderManager11, ItemEntity itemEntity, NameTags.NameTagsData4 nameTagsData412, ItemStack itemStack, float f) {
      float floatValue117 = (float)MathHelper.clamp(16.0 / Math.max(nameTagsData412.distance(), 12.0), 0.75, 1.15);
      float floatValue118 = 6.0F * floatValue117;
      float floatValue119 = 20.0F * floatValue117;
      String text17 = this.resolve7(
         this.resolve6(itemStack, this.otobrazhatPolnyeImena.isEnabled()),
         floatValue119,
         Math.max(86.0F * floatValue117, Math.min(190.0F * floatValue117, CLIENT.getWindow().getFramebufferWidth() * 0.3F))
      );
      float floatValue120 = 22.0F * floatValue117;
      float floatValue121 = 4.0F * floatValue117;
      float floatValue122 = RenderManager.resolve7(FontRegistry.fontObject2, text17, floatValue119).floatValue;
      float floatValue123 = 6.0F * floatValue117;
      float floatValue124 = floatValue122 + floatValue123 * 2.0F;
      float floatValue125 = floatValue120 + floatValue121 + floatValue124;
      float floatValue126 = nameTagsData412.screenX() - floatValue125 / 2.0F;
      float floatValue127 = nameTagsData412.headY() - 12.0F * floatValue117;
      this.invoke5(renderManager11, floatValue126, floatValue127, floatValue120, floatValue120, floatValue118, f, itemStack);
      float floatValue128 = (floatValue120 - 16.0F * floatValue117) / 2.0F;
      this.invoke22(CLIENT.player, itemStack, floatValue126 + floatValue128, floatValue127 + floatValue128, itemEntity.getId(), floatValue117, 0);
      float floatValue129 = floatValue126 + floatValue120 + floatValue121;
      this.invoke5(renderManager11, floatValue129, floatValue127, floatValue124, floatValue120, floatValue118, f, itemStack);
      this.invoke20(renderManager11, FontRegistry.fontObject2, floatValue129 + floatValue123, floatValue127 + 15.0F * floatValue117, floatValue119, text17, itemStack, itemEntity.getId(), f);
   }

   private void invoke19(RenderManager renderManager12, FontObject fontObject, float f, float g, float h, String string, int i) {
      if (!this.gradientTeksta.isEnabled()) {
         renderManager12.invoke69(fontObject, f, g, h, string, i);
      } else {
         int intValue26 = RenderManager.RenderManagerState.compute24(this.vtoroyTsvetTeksta.compute(), i >>> 24 & 0xFF);
         renderManager12.invoke71(fontObject, f, g, h, string, i, intValue26, this.measure2());
      }
   }

   private void invoke20(RenderManager renderManager13, FontObject fontObject2, float f, float g, float h, String string, ItemStack itemStack, int i, float j) {
      if (!this.gradientTeksta.isEnabled()) {
         renderManager13.invoke69(fontObject2, f, g, h, string, this.compute5(itemStack, j));
      } else if (!this.tsvetPredmetaVGradiente.isEnabled()) {
         this.invoke19(renderManager13, fontObject2, f, g, h, string, this.compute5(itemStack, j));
      } else {
         int[] intValues = ItemRenderUtil.resolve(itemStack, i);
         if (intValues == null) {
            intValues = ItemRenderUtil.resolve2();
         }

         int intValue27 = Math.round(255.0F * MathHelper.clamp(j, 0.0F, 1.0F));
         int intValue28 = RenderManager.RenderManagerState.compute32(0, 0, 0, Math.round(185.0F * MathHelper.clamp(j, 0.0F, 1.0F)));
         renderManager13.invoke69(fontObject2, f + Math.max(0.45F, h * 0.035F), g + Math.max(0.45F, h * 0.035F), h, string, intValue28);
         renderManager13.invoke71(
            fontObject2,
            f,
            g,
            h,
            string,
            RenderManager.RenderManagerState.compute24(intValues[0], intValue27),
            RenderManager.RenderManagerState.compute24(intValues[1], intValue27),
            this.measure2()
         );
      }
   }

   private float measure2() {
      float floatValue130 = Math.max(600.0F, 2600.0F / Math.max(0.1F, this.skorostGradienta.getValue()));
      return (float)(System.currentTimeMillis() % (long)floatValue130) / floatValue130;
   }

   private void invoke21(RenderManager renderManager14, float f, float g, float h, float i, float j, float k, float l) {
      if (!(k <= 0.05F)) {
         if (this.stilistika.getValue().equals("Неоморфизм")) {
            float floatValue131 = k * this.prozrachnost.getValue();
            float floatValue132 = 4.8F + l * 1.8F;
            float floatValue133 = 16.0F + l * 4.0F;
            float floatValue134 = 0.72F + l * 0.12F;
            if (NeumorphismRenderer.check11(null, f, g, h, i, j, floatValue132, floatValue133, floatValue134, 1, false, floatValue131)) {
               return;
            }
         }

         if (this.stilistika.getValue().equals("Блюр")) {
            renderManager14.invoke48(23.0F);
            renderManager14.invoke44(f, g, h, i, j, k * this.prozrachnost.getValue());
         }

         int intValue29 = this.compute3(this.intValue3, this.intValue4, l);
         int intValue30 = this.compute3(this.intValue, this.intValue2, l);
         intValue30 = this.compute6(intValue30, k);
         intValue29 = this.compute6(intValue29, k);
         if (this.tenPlashek.isEnabled()) {
            renderManager14.invoke41(f, g, h, i, j, this.check5() ? 7.0F : 5.0F, 1.0F, this.compute2(k));
         }

         renderManager14.invoke5(f, g, h, i, j, intValue30);
         renderManager14.invoke28(f, g, h, i, j, intValue29, 1.0F);
      }
   }

   private boolean check5() {
      Theme theme2 = WildClient.INSTANCE != null && WildClient.INSTANCE.themeManager != null ? WildClient.INSTANCE.themeManager.getTheme() : Theme.WILD;
      return "Светлый".equals(this.stilistika.getValue()) || THEME_PALETTE.check(theme2) || NeumorphismRenderer.check13();
   }

   private int compute2(float f) {
      if (!this.check5()) {
         return this.compute6(RenderManager.RenderManagerState.compute32(0, 0, 0, 120), f);
      } else {
         Theme theme3 = WildClient.INSTANCE != null && WildClient.INSTANCE.themeManager != null ? WildClient.INSTANCE.themeManager.getTheme() : Theme.WILD;
         ColorScheme colorScheme2 = ColorScheme.resolve2(theme3, true);
         int intValue31 = ColorUtils.compute14(-10787208, colorScheme2.getIntValue15(), 0.1F);
         return ColorUtils.compute2(intValue31, (int)(48.0F * Math.max(0.0F, Math.min(1.0F, f * this.prozrachnost.getValue()))));
      }
   }

   private int compute3(int i, int j, float f) {
      int intValue32 = i >> 24 & 0xFF;
      int intValue33 = i >> 16 & 0xFF;
      int intValue34 = i >> 8 & 0xFF;
      int intValue35 = i & 0xFF;
      int intValue36 = j >> 24 & 0xFF;
      int intValue37 = j >> 16 & 0xFF;
      int intValue38 = j >> 8 & 0xFF;
      int intValue39 = j & 0xFF;
      int intValue40 = (int)(intValue32 + (intValue36 - intValue32) * f);
      int intValue41 = (int)(intValue33 + (intValue37 - intValue33) * f);
      int intValue42 = (int)(intValue34 + (intValue38 - intValue34) * f);
      int intValue43 = (int)(intValue35 + (intValue39 - intValue35) * f);
      return RenderManager.RenderManagerState.compute32(intValue41, intValue42, intValue43, intValue40);
   }

   private void invoke22(PlayerEntity playerEntity, ItemStack itemStack, float f, float g, int i, float h, int j) {
      if (itemStack != null && !itemStack.isEmpty()) {
         this.items.add(new NameTags.NameTagsItemData(playerEntity, itemStack.copy(), f, g, i, h, j));
      }
   }

   private void invoke23(RenderManager renderManager15, DrawContext drawContext) {
      if (!this.items.isEmpty()) {
         this.items.sort(Comparator.comparingInt(NameTags.NameTagsItemData::priority));

         for (NameTags.NameTagsItemData nameTagsItemData : this.items) {
            ItemRenderUtil.invoke3(renderManager15, nameTagsItemData.stack(), nameTagsItemData.x(), nameTagsItemData.y(), nameTagsItemData.scale(), nameTagsItemData.seed(), false, nameTagsItemData.priority());
         }

         this.items.clear();
      }
   }

   private boolean check6(LivingEntity livingEntity) {
      return livingEntity instanceof Monster || livingEntity instanceof SlimeEntity || livingEntity instanceof VillagerEntity;
   }

   private boolean check7(LivingEntity livingEntity) {
      return livingEntity instanceof AnimalEntity;
   }

   private int compute4(ItemStack itemStack, float f) {
      boolean flag13 = itemStack != null && !itemStack.isEmpty() && itemStack.contains(DataComponentTypes.CUSTOM_NAME);
      int intValue44 = this.compute5(itemStack, f);
      int intValue45 = flag13
         ? RenderManager.RenderManagerState.compute32(
            RenderManager.RenderManagerState.compute26(intValue44), RenderManager.RenderManagerState.compute27(intValue44), RenderManager.RenderManagerState.compute28(intValue44), 210
         )
         : RenderManager.RenderManagerState.compute32(142, 148, 158, 135);
      return this.compute6(intValue45, f);
   }

   private int compute5(ItemStack itemStack, float f) {
      int intValue46 = RenderManager.RenderManagerState.compute32(220, 255, 245, 255);
      if (itemStack != null && !itemStack.isEmpty()) {
         int[] intValues2 = new int[]{intValue46};

         try {
            Text text18 = itemStack.getName();
            text18.visit((style, string) -> {
               TextColor var3x = style.getColor();
               if (var3x != null && string != null && !string.isBlank()) {
                  intValues2[0] = RenderManager.RenderManagerState.compute32(var3x.getRgb() >> 16 & 0xFF, var3x.getRgb() >> 8 & 0xFF, var3x.getRgb() & 0xFF, 255);
                  return Optional.of(Boolean.TRUE);
               } else {
                  return Optional.empty();
               }
            }, Style.EMPTY);
         } catch (Throwable exception5) {
         }

         return this.compute6(intValues2[0], f);
      } else {
         return this.compute6(intValue46, f);
      }
   }

   private String resolve6(ItemStack itemStack, boolean bl) {
      if (itemStack != null && !itemStack.isEmpty()) {
         int intValue47 = Math.max(1, itemStack.getCount());
         String text19 = itemStack.getName().getString().replaceAll("§.", "").replaceAll("\\p{Cntrl}", "").replaceAll("\\s+", " ").trim();
         if (text19.isEmpty()) {
            text19 = "Предмет";
         }

         if (!bl && text19.length() > 22) {
            text19 = text19.substring(0, 19).trim() + "...";
         }

         return text19 + (intValue47 > 1 ? " x" + intValue47 : "");
      } else {
         return "Пусто";
      }
   }

   private String resolve7(String string, float f, float g) {
      if (string == null || string.isEmpty()) {
         return "";
      } else if (RenderManager.resolve7(FontRegistry.fontObject2, string, f).floatValue <= g) {
         return string;
      } else {
         String text20 = "...";
         float floatValue135 = RenderManager.resolve7(FontRegistry.fontObject2, text20, f).floatValue;
         int intValue48 = string.length();

         while (intValue48 > 0 && RenderManager.resolve7(FontRegistry.fontObject2, string.substring(0, intValue48), f).floatValue + floatValue135 > g) {
            intValue48--;
         }

         return intValue48 <= 0 ? text20 : string.substring(0, intValue48).trim() + text20;
      }
   }

   private int compute6(int i, float f) {
      int intValue49 = i >> 24 & 0xFF;
      int intValue50 = i >> 16 & 0xFF;
      int intValue51 = i >> 8 & 0xFF;
      int intValue52 = i & 0xFF;
      return RenderManager.RenderManagerState.compute32(intValue50, intValue51, intValue52, (int)(intValue49 * f));
   }

   private boolean check8(float f, float g, float h, float i, float j, float k) {
      return f >= h && f <= h + j && g >= i && g <= i + k;
   }

   record NameTagsData(Vec3d start, Vec3d end) {
   }

   record NameTagsData2(List<NameTags.NameTagsData> bones, long capturedAt) {
   }

   record NameTagsData3(float x, float y, float w, float h, String playerName) {
   }

   record NameTagsItemData(PlayerEntity player, ItemStack stack, float x, float y, int seed, float scale, int priority) {
   }

   record NameTagsData4(float screenX, float headY, float feetY, float depth, double distance, float boxLeft, float boxRight) {
   }
}
