package sandybay.apicurious.common.compat.jei.handler;

import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import sandybay.apicurious.client.gui.ApiaryScreen;
import sandybay.apicurious.client.gui.BeeHousingScreen;
import sandybay.apicurious.common.compat.jei.ApicuriousRecipeTypes;
import sandybay.apicurious.common.menu.ApiaryMenu;
import sandybay.apicurious.common.menu.BeeHousingMenu;

import java.util.ArrayList;
import java.util.Collection;

public class JEIHousingContainerHandler<M extends AbstractContainerMenu, T extends AbstractContainerScreen<M>> implements IGuiContainerHandler<T>
{
  @Override
  public Collection<IGuiClickableArea> getGuiClickableAreas(T containerScreen, double guiMouseX, double guiMouseY)
  {
    Collection<IGuiClickableArea> areas = new ArrayList<>();
    areas.add(IGuiClickableArea.createBasic(20, 36, 4, 48, ApicuriousRecipeTypes.BEE_MUTATIONS));
    return areas;
  }

  public static class JEIApiaryContainerHandler extends JEIHousingContainerHandler<ApiaryMenu, ApiaryScreen> {}
  public static class JEIBeeHousingContainerHandler extends JEIHousingContainerHandler<BeeHousingMenu, BeeHousingScreen> {}
}
