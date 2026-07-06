package sandybay.apicurious.client.renderer.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleGroup;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.state.level.ParticleGroupRenderState;

import java.util.ArrayList;
import java.util.List;

public class BeeParticleGroup extends ParticleGroup<BeeParticle>
{

  public BeeParticleGroup(ParticleEngine engine)
  {
    super(engine);
  }

  @Override
  public ParticleGroupRenderState extractRenderState(Frustum frustum, Camera camera, float v)
  {
    List<BeeParticleRenderState.Entry> entries = new ArrayList<>();
    for (BeeParticle beeParticle : this.particles)
    {
      PoseStack pose =  new PoseStack();
      pose.pushPose();
      pose.mulPose(camera.rotation());
      entries.add(new BeeParticleRenderState.Entry(beeParticle.getModel(), beeParticle.getStack(), pose));
    }
    return new BeeParticleRenderState(entries);
  }
}
