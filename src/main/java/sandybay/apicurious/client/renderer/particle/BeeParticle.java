package sandybay.apicurious.client.renderer.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

/**
 * Purely a state/movement holder. Does not render itself — the owning
 * {@link BeeParticleGroup} reads {@link #getItemRenderState()} and this
 * particle's position each frame during extractRenderState().
 */
public class BeeParticle extends Particle
{
  private enum State { TO_FLOWER, CIRCLING, RETURNING }

  private final BlockPos homePos;
  private final BlockPos flowerPos;
  private State state;

  private int circleTicks;
  private final int maxCircleTicks;
  private final double circleRadius;
  private double circleAngle;

  /** Baked once at construction — the bee item's appearance doesn't change mid-flight. */
  private final ItemStackRenderState itemRenderState = new ItemStackRenderState();

  public BeeParticle(ClientLevel level, double x, double y, double z,
                     BlockPos homePos, BlockPos flowerPos,
                     ItemStack stackToRender, ItemDisplayContext displayContext)
  {
    super(level, x, y, z);
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

    bakeItemRenderState(level, stackToRender, displayContext);
  }

  /**
   * Resolves the layered bee item model into {@link #itemRenderState} once.
   * The tint is expected to come from whatever data component / ItemColor your
   * layered model's tinted layer already reads off {@code stackToRender} — set
   * that component on the stack BEFORE calling this (see the Apiary spawn code),
   * since there is no supported way to override a tint after ItemModel#update runs.
   */
  private void bakeItemRenderState(ClientLevel level, ItemStack stack, ItemDisplayContext displayContext)
  {
    ItemModelResolver resolver = Minecraft.getInstance().getItemModelResolver();
    resolver.updateForTopItem(
            itemRenderState,
            stack,
            displayContext,
            level,
            null,   // ItemOwner — no holding entity for a free-floating particle
            0             // seed
    );
  }

  public ItemStackRenderState getItemRenderState()
  {
    return itemRenderState;
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
        if (++circleTicks >= maxCircleTicks) {state = State.RETURNING;}
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

  public double interpX(float partialTick) {return Mth.lerp(partialTick, xo, x);}
  public double interpY(float partialTick) {return Mth.lerp(partialTick, yo, y);}
  public double interpZ(float partialTick) {return Mth.lerp(partialTick, zo, z);}

  @Override
  public ParticleRenderType getGroup()
  {
    return BeeParticleGroup.BEE_ITEM_MODEL;
  }
}