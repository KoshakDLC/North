package ru.metaculture.protection;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;

public interface MinecraftAccessor {
   MinecraftClient a_ = MinecraftClient.getInstance();
   Window b_ = a_.getWindow();
}
