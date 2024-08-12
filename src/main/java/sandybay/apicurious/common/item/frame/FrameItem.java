package sandybay.apicurious.common.item.frame;

import net.minecraft.world.item.Item;
import sandybay.apicurious.api.item.IFrameItem;
import sandybay.apicurious.api.item.TerritoryModifier;

public class FrameItem extends Item implements IFrameItem
{

  private final float lifespanModifier;
  private final float productionModifier;
  private final float mutationModifier;
  private final TerritoryModifier territoryModifier;

  public FrameItem(Properties pProperties, float lifespanModifier, float productionModifier, float mutationModifier, TerritoryModifier territoryModifier)
  {
    super(pProperties);
    this.lifespanModifier = lifespanModifier;
    this.productionModifier = productionModifier;
    this.mutationModifier = mutationModifier;
    this.territoryModifier = territoryModifier;
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

  @Override
  public TerritoryModifier getTerritoryModifier()
  {
    return this.territoryModifier;
  }
}
