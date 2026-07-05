package sandybay.apicurious.client.renderer.particle;

import net.minecraft.client.Camera;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleGroup;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.state.level.ParticleGroupRenderState;

/**
 * Custom particle group for bee-item-model particles. Mirrors QuadParticleGroup's
 * shape: walk live particles each frame, feed a shared render state, return it.
 */
public class BeeParticleGroup extends ParticleGroup<BeeParticle>
{
  public static final ParticleRenderType BEE_ITEM_MODEL =
          new ParticleRenderType("BEE_ITEM_MODEL", "BIM");

  private final BeeParticleRenderState renderState = new BeeParticleRenderState();

  public BeeParticleGroup(ParticleEngine engine)
  {
    super(engine);
  }

  @Override
  public ParticleGroupRenderState extractRenderState(Frustum frustum, Camera camera, float partialTickTime)
  {
    renderState.clear();
    for (BeeParticle particle : this.particles)
    {
      if (frustum.pointInFrustum(particle.interpX(partialTickTime), particle.interpY(partialTickTime), particle.interpZ(partialTickTime)))
      {
        renderState.add(particle, partialTickTime);
      }
    }
    return renderState;
  }
}