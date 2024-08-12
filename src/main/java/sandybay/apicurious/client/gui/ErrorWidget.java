package sandybay.apicurious.client.gui;

import net.minecraft.network.chat.Component;
import sandybay.apicurious.api.housing.HousingError;

import java.util.List;

public class ErrorWidget extends InfoWidget
{
  HousingError housingError;

  public ErrorWidget(int pX, int pY, int pWidth, int pHeight, int openSizeWidth, int openSizeHeight, boolean openLeft, float red, float green, float blue, HousingError housingError)
  {
    super(pX, pY, pWidth, pHeight, openSizeWidth, openSizeHeight, openLeft, red, green, blue, housingError.getIcon(), List.of(Component.translatable(housingError.getMessage())));
    this.housingError = housingError;
  }

  public HousingError getApiaryError()
  {
    return housingError;
  }
}
