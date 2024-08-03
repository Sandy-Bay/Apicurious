package sandybay.apicurious.api.housing.blockentity;

import sandybay.apicurious.api.ApiaryError;

public interface IApiaryErrorHandler
{
  void addError(ApiaryError error);

  void removeError(ApiaryError error);

  void clearErrors();
}
