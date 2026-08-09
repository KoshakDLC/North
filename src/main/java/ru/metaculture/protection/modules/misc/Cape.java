package ru.metaculture.protection;

import com.mojang.authlib.GameProfile;
import java.util.function.Consumer;
import net.minecraft.util.Identifier;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleAccess(
   usernames = {"lichoday"}
)
@ModuleRegister(
   name = "Cape",
   category = Category.Misc,
   description = "Добавляет вам плащик"
)
public class Cape extends Module {
   public static void invoke(GameProfile gameProfile, Consumer<Identifier> consumer) {
   }
}
