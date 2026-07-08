package sandybay.apicurious.client.renderer.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleGroup;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.state.level.ParticleGroupRenderState;
import org.joml.Quaternionf;

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
      // normalizes the location of the particle
      pose.translate(beeParticle.getRenderPos(v).subtract(camera.position()));
      // Ensures the particle is always facing the camera
      Quaternionf rotation = new Quaternionf();
      SingleQuadParticle.FacingCameraMode.LOOKAT_XYZ.setRotation(rotation, camera, v);
      pose.mulPose(rotation);
      entries.add(new BeeParticleRenderState.Entry(beeParticle.getModel(), beeParticle.getStack(), pose));
    }
    return new BeeParticleRenderState(entries);
  }
}
