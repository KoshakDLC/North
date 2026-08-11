package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;
import ru.metaculture.protection.cosmetics.CosmeticPack;
import ru.metaculture.protection.cosmetics.model.CosmeticModel;
import ru.metaculture.protection.cosmetics.render.CosmeticRenderer;
import ru.metaculture.protection.cosmetics.render.MaceKosaRenderer;

@ModuleRegister(
   name = "Cosmetics",
   description = "3D-косметика PulseVisuals: шляпы, крылья, плащи, броня и питомцы",
   category = Category.Visuals
)
public final class Cosmetics extends Module {
   public final ModeSetting hat;
   public final ModeSetting wings;
   public final ModeSetting cape;
   public final ModeSetting bodywear;
   public final ModeSetting pet;
   public final BooleanSetting naDrugihIgrokah;

   public Cosmetics() {
      this.hat = new ModeSetting("Шляпа", "Нет", CosmeticPack.options("hat"));
      this.wings = new ModeSetting("Крылья", "Нет", CosmeticPack.options("wings"));
      this.cape = new ModeSetting("Плащ", "Нет", CosmeticPack.options("cape"));
      this.bodywear = new ModeSetting("Одежда", "Нет", CosmeticPack.options("bodywear"));
      this.pet = new ModeSetting("Питомец", "Нет", CosmeticPack.options("pet"));
      this.naDrugihIgrokah = new BooleanSetting("На других игроках", false);
      this.addSettings(this.hat, this.wings, this.cape, this.bodywear, this.pet, this.naDrugihIgrokah);
   }

   public static void renderForPlayer(
      PlayerEntityRenderState state,
      PlayerEntityModel playerModel,
      MatrixStack matrices,
      VertexConsumerProvider buffers,
      int light
   ) {
      if (WildClient.INSTANCE == null || WildClient.INSTANCE.moduleManager == null) {
         return;
      }

      Cosmetics cosmetics = WildClient.INSTANCE.moduleManager.getModule(Cosmetics.class);
      if (cosmetics != null) {
         cosmetics.render(state, playerModel, matrices, buffers, light);
      }
   }

   private void render(
      PlayerEntityRenderState state,
      PlayerEntityModel playerModel,
      MatrixStack matrices,
      VertexConsumerProvider buffers,
      int light
   ) {
      if (!this.enabled || CLIENT == null || CLIENT.player == null || state == null || playerModel == null || matrices == null || buffers == null) {
         return;
      }

      boolean self = state.id == CLIENT.player.getId();
      if (!self && !this.naDrugihIgrokah.isEnabled()) {
         return;
      }

      if (self && CLIENT.options.getPerspective() == Perspective.FIRST_PERSON) {
         return;
      }

      if (state.spectator || state.invisible || state.invisibleToPlayer) {
         return;
      }

      for (CosmeticModel model : this.selectedModels()) {
         CosmeticRenderer.getInstance().renderCosmetic(model, playerModel, matrices, buffers, light);
      }
   }

   private List<CosmeticModel> selectedModels() {
      List<CosmeticModel> models = new ArrayList<>(5);
      this.addIfPresent(models, "hat", this.hat.getValue());
      this.addIfPresent(models, "wings", this.wings.getValue());
      this.addIfPresent(models, "cape", this.cape.getValue());
      this.addIfPresent(models, "bodywear", this.bodywear.getValue());
      this.addIfPresent(models, "pet", this.pet.getValue());
      return models;
   }

   private void addIfPresent(List<CosmeticModel> models, String type, String name) {
      if (MaceKosaRenderer.isSelectedCosmetic(type, name)) {
         return;
      }

      CosmeticModel model = CosmeticPack.resolve(type, name);
      if (model != null) {
         models.add(model);
      }
   }
}
