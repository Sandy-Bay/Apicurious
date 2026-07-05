package sandybay.apicurious.client.renderer.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.data.AtlasIds;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class BeeParticle extends SingleQuadParticle
{

  public BeeParticle(ClientLevel level, double x, double y, double z, BlockPos homePos, BlockPos flowerPos, TextureAtlasSprite sprite)
  {
    super(level, x, y, z, sprite);
    this.homePos = homePos;
    this.flowerPos = flowerPos;
    this.state = flowerPos != null ? State.TO_FLOWER : State.RETURNING;
    this.circleTicks = 0;
    this.maxCircleTicks = 40 + this.random.nextInt(40);
    this.circleRadius = 0.6 + this.random.nextDouble() * 0.4;
    this.circleAngle = this.random.nextDouble() * Math.PI * 2;
    this.lifetime = 20 * 20; // hard safety cap
    this.hasPhysics = false;
    this.gravity = 0f;
  }

  private enum State { TO_FLOWER, CIRCLING, RETURNING }

  private final BlockPos homePos;
  private final BlockPos flowerPos;
  private BeeParticle.State state;

  private int circleTicks;
  private final int maxCircleTicks;
  private final double circleRadius;
  private double circleAngle;

  @Override
  public void tick()
  {
    this.xo = this.x;
    this.yo = this.y;
    this.zo = this.z;

    if (this.age++ >= this.lifetime)
    {
      this.remove();
      return;
    }

    switch (state)
    {
      case TO_FLOWER -> tickFlyTo(flowerCenter(), () ->
      {
        state = State.CIRCLING;
        circleTicks = 0;
      });
      case CIRCLING ->
      {
        tickCircle();
        if (++circleTicks >= maxCircleTicks)
        {
          state = State.RETURNING;
        }
      }
      case RETURNING -> tickFlyTo(homeCenter(), this::remove);
    }
  }

  private Vec3 flowerCenter()
  {
    return flowerPos == null ? homeCenter() : Vec3.atCenterOf(flowerPos).add(0, 0.3, 0);
  }

  private Vec3 homeCenter()
  {
    return Vec3.atCenterOf(homePos).add(0, 0.6, 0);
  }

  private void tickFlyTo(Vec3 target, Runnable onArrive)
  {
    Vec3 current = new Vec3(x, y, z);
    Vec3 delta = target.subtract(current);
    double dist = delta.length();

    if (dist < 0.15)
    {
      onArrive.run();
      return;
    }

    double speed = Mth.clamp(dist * 0.1, 0.02, 0.18);
    Vec3 step = delta.normalize().scale(speed);
    double wobble = Math.sin((age + circleAngle) * 0.5) * 0.01;

    this.setPos(x + step.x, y + step.y + wobble, z + step.z);
    this.xd = step.x;
    this.yd = step.y;
    this.zd = step.z;
  }

  private void tickCircle()
  {
    circleAngle += 0.12;
    Vec3 center = flowerCenter();
    double bob = Math.sin(circleAngle * 2.0) * 0.08;
    double px = center.x + Math.cos(circleAngle) * circleRadius;
    double pz = center.z + Math.sin(circleAngle) * circleRadius;
    double py = center.y + bob;
    this.setPos(px, py, pz);
  }

  @Override
  protected @NonNull Layer getLayer()
  {
    return Layer.OPAQUE_ITEMS;
  }

  public static class Provider implements ParticleProvider<BeeParticleOption>
  {
    private final ItemStackRenderState scratchRenderState = new ItemStackRenderState();

    protected TextureAtlasSprite getSprite(ItemStackTemplate item, ClientLevel level, RandomSource random) {
      Minecraft.getInstance().getItemModelResolver().updateForTopItem(this.scratchRenderState, item.create(), ItemDisplayContext.GROUND, level, null, 0);
      Material.Baked material = this.scratchRenderState.pickParticleMaterial(random);
      return material != null ? material.sprite() : Minecraft.getInstance().getAtlasManager().getAtlasOrThrow(AtlasIds.ITEMS).missingSprite();
    }

    @Override
    public @Nullable Particle createParticle(BeeParticleOption beeParticleOption, ClientLevel clientLevel, double x, double y, double z, double xd, double yd, double zd, RandomSource random)
    {
      return new BeeParticle(clientLevel, x, y, z, beeParticleOption.homePos(), beeParticleOption.flowerPos(), this.getSprite(beeParticleOption.stack(), clientLevel, random));
    }
  }
}
