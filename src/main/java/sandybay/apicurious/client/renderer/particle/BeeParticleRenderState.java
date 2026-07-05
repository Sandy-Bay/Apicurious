package sandybay.apicurious.client.renderer.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.state.level.ParticleGroupRenderState;

import java.util.ArrayList;
import java.util.List;

/**
 * Collects one PoseStack + baked ItemStackRenderState per live bee particle each
 * frame in extractRenderState(), then hands them to the SubmitNodeCollector when
 * the deferred render pass actually calls submit().
 */
public class BeeParticleRenderState implements ParticleGroupRenderState
{
  private record Entry(double x, double y, double z, ItemStackRenderState itemRenderState, int light) {}

  private final List<Entry> entries = new ArrayList<>();

  public void add(BeeParticle particle, float partialTick)
  {
    entries.add(new Entry(
            particle.interpX(partialTick),
            particle.interpY(partialTick),
            particle.interpZ(partialTick),
            particle.getItemRenderState(),
            0 // TODO: Fix Light-level
    ));
  }

  @Override
  public void clear()
  {
    entries.clear();
  }

  @Override
  public void submit(SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState)
  {
    // ADAPT: confirm CameraRenderState's actual position accessor in 26.2.
    // Guessed as a Vec3-like field/getter named `pos`. If it's e.g. getPosition()
    // or a Vector3f, adjust the three lines below accordingly.
    double camX = cameraRenderState.pos.x();
    double camY = cameraRenderState.pos.y();
    double camZ = cameraRenderState.pos.z();

    for (Entry entry : entries)
    {
      PoseStack poseStack = new PoseStack();
      poseStack.pushPose();
      poseStack.translate(entry.x() - camX, entry.y() - camY, entry.z() - camZ);
      poseStack.scale(0.5f, 0.5f, 0.5f);

      entry.itemRenderState().submit(
              poseStack,
              submitNodeCollector,
              entry.light(),
              net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY,
              0 // outline color — 0/none unless you want a glow-outline effect
      );

      poseStack.popPose();
    }
  }
}