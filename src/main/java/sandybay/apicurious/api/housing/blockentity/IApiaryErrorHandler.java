package sandybay.apicurious.api.housing.blockentity;

import sandybay.apicurious.api.housing.HousingError;

public interface IApiaryErrorHandler
{
  void addError(HousingError error);

  void removeError(HousingError error);

  void clearErrors();
}
