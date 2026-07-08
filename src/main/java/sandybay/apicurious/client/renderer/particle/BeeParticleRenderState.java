package sandybay.apicurious.client.renderer.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.state.level.ParticleGroupRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Brightness;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public record BeeParticleRenderState(List<Entry> entries) implements ParticleGroupRenderState
{
  public record Entry(ItemModel model, ItemStack stack, PoseStack pose) {}

  @Override
  public void submit(SubmitNodeCollector collector, CameraRenderState cameraRenderState)
  {
    for (Entry entry : entries)
    {
      ItemStackRenderState renderState = new ItemStackRenderState();
      entry.model().update(renderState, entry.stack(), Minecraft.getInstance().getItemModelResolver(), ItemDisplayContext.GROUND, Minecraft.getInstance().level, null, 0);
      renderState.submit(entry.pose(), collector, Brightness.FULL_BRIGHT.pack(), OverlayTexture.NO_OVERLAY, 0);
    }
  }
}
