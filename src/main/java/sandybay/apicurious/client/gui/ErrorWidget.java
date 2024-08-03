package sandybay.apicurious.client.gui;

import net.minecraft.network.chat.Component;
import sandybay.apicurious.api.ApiaryError;

import java.util.List;

public class ErrorWidget extends InfoWidget
{
  ApiaryError apiaryError;

  public ErrorWidget(int pX, int pY, int pWidth, int pHeight, int openSizeWidth, int openSizeHeight, boolean openLeft, float red, float green, float blue, ApiaryError apiaryError)
  {
    super(pX, pY, pWidth, pHeight, openSizeWidth, openSizeHeight, openLeft, red, green, blue, apiaryError.getIcon(), List.of(Component.translatable(apiaryError.getMessage())));
    this.apiaryError = apiaryError;
  }

  public ApiaryError getApiaryError()
  {
    return apiaryError;
  }
}
