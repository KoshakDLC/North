package ru.metaculture.protection;

import net.minecraft.client.render.entity.state.ItemEntityRenderState;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "ItemPhysic",
   category = Category.Visuals,
   description = "Рендерит предметы лежащими на поверхности"
)
public class ItemPhysic extends Module {
   private static final float FLOAT_VALUE = 90.0F;
   private static final float FLOAT_VALUE_2 = 0.0F;
   private static final float FLOAT_VALUE_3 = 22.0F;
   private static boolean flag;

   @Override
   public void onEnable() {
      super.onEnable();
      flag = true;
   }

   @Override
   public void onDisable() {
      super.onDisable();
      flag = false;
   }

   public static boolean check(ItemEntityRenderState itemEntityRenderState) {
      return flag && itemEntityRenderState instanceof ItemPhysicsStateAccessor itemPhysicsStateAccessor && itemPhysicsStateAccessor.wild$isItemPhysicOnGround();
   }

   public static boolean check2(ItemEntityRenderState itemEntityRenderState) {
      return flag && itemEntityRenderState instanceof ItemPhysicsStateAccessor itemPhysicsStateAccessor2 && !itemPhysicsStateAccessor2.wild$isItemPhysicOnGround();
   }

   public static float measure() {
      return 0.0F;
   }

   public static float measure2() {
      return 90.0F;
   }

   public static float measure3(float f) {
      return f * 22.0F;
   }
}
