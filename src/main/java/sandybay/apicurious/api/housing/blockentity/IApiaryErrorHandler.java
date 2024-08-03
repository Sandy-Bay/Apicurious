package sandybay.apicurious.api.housing.blockentity;

import sandybay.apicurious.api.EnumApiaryError;

public interface IApiaryErrorHandler
{
  void addError(EnumApiaryError error);
  void removeError(EnumApiaryError error);
  void clearErrors();
}
