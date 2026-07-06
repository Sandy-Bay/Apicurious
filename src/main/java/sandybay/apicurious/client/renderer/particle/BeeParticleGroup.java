package sandybay.apicurious.client.renderer.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleGroup;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.state.level.ParticleGroupRenderState;

public class BeeParticleGroup extends ParticleGroup<BeeParticle>
{
  private final ParticleRenderType particleType;
  private final BeeParticleRenderState particleRenderState = new BeeParticleRenderState();

  public BeeParticleGroup(ParticleEngine engine, ParticleRenderType particleType)
  {
    super(engine);
    this.particleType = particleType;
  }

  @Override
  public ParticleGroupRenderState extractRenderState(Frustum frustum, Camera camera, float partialTickTime)
  {
    for (BeeParticle particle : this.particles)
    {
      if (frustum.pointInFrustum(particle.x, particle.y, particle.z))
      {
        try
        {
          particle.extract(this.particleRenderState, camera, partialTickTime);
        }
        catch (Throwable e)
        {
          CrashReport report = CrashReport.forThrowable(e, "Rendering bee particle");
          CrashReportCategory category = report.addCategory("Bee particle being rendered");
          category.setDetail("Particle", particle::toString);
          category.setDetail("Particle Type", this.particleRenderType::toString);
          throw new ReportedException(report);
        }
      }
    }
    return this.particleRenderState;
  }

  public static class BeeParticleRenderState implements ParticleGroupRenderState
  {

    @Override
    public void submit(SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState)
    {
      submitNodeCollector.submitItem();
    }
  }
}
