package sandybay.apicurious.api.bee;

import sandybay.apicurious.api.util.Coloring;

public interface IBeeColor {
  Coloring getOutlineTint();

  Coloring getWingTint();

  Coloring getBodyTint();
}
