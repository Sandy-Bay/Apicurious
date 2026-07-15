package sandybay.apicurious.common.compat.jei.handler;

import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import sandybay.apicurious.client.gui.CentrifugeScreen;
import sandybay.apicurious.common.compat.jei.ApicuriousRecipeTypes;

import java.util.ArrayList;
import java.util.Collection;

public class JEICentrifugeContainerHandler implements IGuiContainerHandler<CentrifugeScreen>
{
  @Override
  public Collection<IGuiClickableArea> getGuiClickableAreas(CentrifugeScreen containerScreen, double guiMouseX,
                                                            double guiMouseY)
  {
    Collection<IGuiClickableArea> areas = new ArrayList<>();
    areas.add(IGuiClickableArea.createBasic(69, 35, 22, 15, ApicuriousRecipeTypes.CENTRIFUGE));
    return areas;
  }
}
