package sandybay.apicurious.common.item.frame;

import sandybay.apicurious.api.item.TerritoryModifier;

public class RestraintFrame extends FrameItem
{

  public RestraintFrame(Properties pProperties)
  {
    super(pProperties, 0.75f, 0.75f, 1.0f, new TerritoryModifier(xz -> Math.round((float)xz/2), y -> Math.round((float)y/2)));
  }

  @Override
  public boolean pacifies()
  {
    return true;
  }
}
