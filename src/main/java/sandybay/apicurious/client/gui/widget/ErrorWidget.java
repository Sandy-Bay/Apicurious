package sandybay.apicurious.client.gui.widget;

import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import sandybay.apicurious.api.housing.HousingError;

import java.util.List;

public class ErrorWidget extends InfoWidget
{
  public HousingError housingError;

  public ErrorWidget(int pX, int pY, int pWidth, int pHeight, int openSizeWidth, int openSizeHeight, boolean openLeft, float red, float green, float blue, HousingError housingError)
  {
    super(pX, pY, pWidth, pHeight, openSizeWidth, openSizeHeight, openLeft, red, green, blue, housingError.getIcon(), List.of(Component.translatable(housingError.getTooltip())));
    this.housingError = housingError;
    this.setTooltip(Tooltip.create(Component.translatable(housingError.getMessage())));
  }

  public HousingError getApiaryError()
  {
    return housingError;
  }

  @Override
  public void setX(int x)
  {
    super.setX(x);
  }

  @Override
  public void setY(int y)
  {
    super.setY(y);
  }
}
