package sandybay.apicurious.client.renderer.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;
import sandybay.apicurious.Apicurious;

public class BeeParticle extends Particle
{
  public static final ParticleRenderType BEE = new ParticleRenderType("BEE_PARTICLE", "BP");
  private static final int MIN_DIP_INTERVAL = 30;
  private static final int DIP_INTERVAL_RANGE = 50;
  private static final int MIN_DIP_DURATION = 10;
  private static final int DIP_DURATION_RANGE = 10;
  private final ItemModel model;
  private final ItemStack stack;
  private final BlockPos homePos;
  private final BlockPos flowerPos;
  private final AABB flowerAabb;
  private final double radiusMargin;
  private final int maxCircleTicks;
  private BeeParticle.State state;
  private int circleTicks;
  private double circleAngle;
  // Handles the periodic "dip in and touch the flower" motion while circling.
  private int ticksUntilNextDip;
  private int dipTicks;
  private int dipDuration;

  public BeeParticle(ClientLevel level, double x, double y, double z, ItemStack stack, BlockPos homePos,
                     BlockPos flowerPos)
  {
    super(level, x, y, z);
    this.stack = stack;
    this.homePos = homePos;
    this.flowerPos = flowerPos;
    this.model = Minecraft.getInstance().getModelManager().getItemModel(Apicurious.createIdentifier("species/default_drone"));
    this.state = flowerPos != null ? State.TO_FLOWER : State.RETURNING;
    this.circleTicks = 0;
    this.maxCircleTicks = 40 + this.random.nextInt(40);
    this.radiusMargin = 0.1 + this.random.nextDouble() * 0.15;
    this.circleAngle = this.random.nextDouble() * Math.PI * 2;
    this.flowerAabb = computeFlowerAabb(level, flowerPos);
    this.ticksUntilNextDip = MIN_DIP_INTERVAL + this.random.nextInt(DIP_INTERVAL_RANGE);
    this.dipTicks = 0;
    this.dipDuration = 0;
    this.lifetime = 20 * 20;
    this.hasPhysics = false;
    this.gravity = 0f;
  }

  /**
   * Grabs the collision/outline shape of the block at the flower position and
   * returns its world-space bounding box, so the bee's circling can hug the
   * actual shape of the flower instead of an arbitrary fixed-size circle.
   */
  private static AABB computeFlowerAabb(ClientLevel level, @Nullable BlockPos flowerPos)
  {
    if (flowerPos == null)
    {
      return null;
    }
    BlockState state = level.getBlockState(flowerPos);
    VoxelShape shape = state.getShape(level, flowerPos);
    AABB local = shape.isEmpty() ? new AABB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0) : shape.bounds();
    return local.move(flowerPos.getX(), flowerPos.getY(), flowerPos.getZ());
  }

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
    switch (this.state)
    {
      case TO_FLOWER -> tickFlyTo(flowerCircle(), () ->
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

  private Vec3 flowerCircle()
  {
    return new Vec3(this.circleX(), this.circleY(), this.circleZ());
  }

  private AABB flowerBounds()
  {
    if (flowerAabb != null)
    {
      return flowerAabb;
    }
    // Fall back to a small AABB around home so the math below still works
    // if there is no flower to circle.
    Vec3 center = homeCenter();
    return new AABB(center.x - 0.5, center.y, center.z - 0.5, center.x + 0.5, center.y + 1.0, center.z + 0.5);
  }

  private Vec3 homeCenter()
  {
    return Vec3.atCenterOf(homePos);
  }

  private void tickFlyTo(Vec3 target, Runnable onArrive)
  {
    Vec3 current = new Vec3(this.x, this.y, this.z);
    Vec3 delta = target.subtract(current);
    double dist = delta.length();
    if (dist < 0.15)
    {
      onArrive.run();
      return;
    }
    double speed = Mth.clamp(dist * 0.1, 0.02, 0.18);
    Vec3 step = delta.normalize().scale(speed);
    this.setPos(x + step.x, y + step.y + getWobble(), z + step.z);
    this.xd = step.x;
    this.yd = step.y;
    this.zd = step.z;
  }

  private double circleX()
  {
    AABB bounds = flowerBounds();
    double centerX = (bounds.minX + bounds.maxX) * 0.5;
    double radiusX = (bounds.getXsize() * 0.5 + radiusMargin) * dipFactor();
    return centerX + Math.cos(circleAngle) * radiusX;
  }

  private double circleY()
  {
    AABB bounds = flowerBounds();
    double hoverY = bounds.maxY + radiusMargin * 0.5 + (Math.sqrt(circleAngle * 2.0) * 0.08);
    double touchY = Mth.lerp(0.25, bounds.minY, bounds.maxY);
    return Mth.lerp(dipFactor(), touchY, hoverY);
  }

  private double circleZ()
  {
    AABB bounds = flowerBounds();
    double centerZ = (bounds.minZ + bounds.maxZ) * 0.5;
    double radiusZ = (bounds.getZsize() * 0.5 + radiusMargin) * dipFactor();
    return centerZ + Math.sin(circleAngle) * radiusZ;
  }

  /**
   * 1.0 = normal circling orbit around the flower.
   * 0.0 = fully dipped in, touching the flower's surface to grab pollen/nectar.
   * Eases smoothly in and out of the dip using a sine curve.
   */
  private double dipFactor()
  {
    if (dipDuration <= 0)
    {
      return 1.0;
    }
    double t = dipTicks / (double) dipDuration;
    return Math.max(1.0 - Math.sin(t * Math.PI), 0.2);
  }

  private void updateDipState()
  {
    if (dipDuration > 0)
    {
      if (++dipTicks >= dipDuration)
      {
        dipDuration = 0;
        dipTicks = 0;
        ticksUntilNextDip = MIN_DIP_INTERVAL + this.random.nextInt(DIP_INTERVAL_RANGE);
      }
    }
    else if (--ticksUntilNextDip <= 0)
    {
      dipDuration = MIN_DIP_DURATION + this.random.nextInt(DIP_DURATION_RANGE);
      dipTicks = 0;
    }
  }

  private void tickCircle()
  {
    circleAngle += 0.12;
    updateDipState();
    this.setPos(circleX(), circleY() + getWobble(), circleZ());
  }

  private double getWobble()
  {
    return Math.sin((age + circleAngle) * 0.5) * 0.01;
  }

  public ItemStack getStack()
  {
    return stack;
  }

  public ItemModel getModel()
  {
    return model;
  }

  @Override
  public ParticleRenderType getGroup()
  {
    return BEE;
  }

  protected Vec3 getRenderPos(float a)
  {
    return new Vec3(this.xo, this.yo, this.zo).lerp(this.getPos(), a);
  }

  private enum State
  {TO_FLOWER, CIRCLING, RETURNING}

  public static class Provider implements ParticleProvider<BeeParticleOption>
  {

    @Override
    public @Nullable Particle createParticle(BeeParticleOption beeParticleOption, ClientLevel clientLevel, double x,
                                             double y, double z, double xd, double yd, double zd, RandomSource random)
    {
      return new BeeParticle(clientLevel, x, y, z, beeParticleOption.stack(), beeParticleOption.homePos(), beeParticleOption.flowerPos());
    }
  }
}