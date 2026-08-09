package ru.metaculture.protection;

import com.mojang.blaze3d.systems.RenderPass;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.client.render.RenderLayer.MultiPhase;

public interface MultiPhaseRenderPass {
   MultiPhase withRenderPassSetup(Consumer<RenderPass> consumer);

   static MultiPhaseRenderPass resolve(MultiPhase multiPhase) {
      Objects.requireNonNull(multiPhase, "multiPhase");
      return (MultiPhaseRenderPass)(Object)multiPhase;
   }
}
