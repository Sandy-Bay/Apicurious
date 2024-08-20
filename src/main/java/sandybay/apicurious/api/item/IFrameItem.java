package sandybay.apicurious.api.item;

public interface IFrameItem
{
  float getLifespanModifier();

  float getProductionModifier();

  float getMutationChanceModifier();

  float getAdditionalPrincessModifier();

  TerritoryModifier getTerritoryModifier();

  // TODO: Implement effects that can check for this.
  default boolean pacifies()
  {
    return false;
  }

}
