package sandybay.apicurious.common.item;

import net.minecraft.world.item.Item;
import sandybay.apicurious.api.item.IFrameItem;

public class FrameItem extends Item implements IFrameItem
{

  private final float lifespanModifier;
  private final float productionModifier;
  private final float mutationModifier;

  public FrameItem(Properties pProperties, float lifespanModifier, float productionModifier, float mutationModifier)
  {
    super(pProperties);
    this.lifespanModifier = lifespanModifier;
    this.productionModifier = productionModifier;
    this.mutationModifier = mutationModifier;
  }

  @Override
  public float getLifespanModifier()
  {
    return this.lifespanModifier;
  }

  @Override
  public float getProductionModifier()
  {
    return this.productionModifier;
  }

  @Override
  public float getMutationChanceModifier()
  {
    return this.mutationModifier;
  }
}
