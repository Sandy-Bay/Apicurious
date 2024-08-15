package sandybay.apicurious.api.util;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.common.block.blockentity.SimpleBlockHousingBE;
import sandybay.apicurious.api.register.DataComponentRegistrar;
import sandybay.apicurious.common.bee.genetic.Genome;

import java.util.List;

public class SimpleBlockHousingHelper
{
  public static Holder<IAllele<?>> getSpeciesInSlot(SimpleBlockHousingBE housing, int slot, boolean active)
  {
    ItemStack stack = housing.getInventory().getStackInSlot(slot);
    Genome genome = stack.get(DataComponentRegistrar.GENOME);
    if (genome == null) return null;
    return genome.getSpecies(active);
  }

  public static List<ItemStack> getFrames(SimpleBlockHousingBE housing)
  {
    ItemStackHandler handler = housing.getInventory();
    return List.of(handler.getStackInSlot(2), handler.getStackInSlot(3), handler.getStackInSlot(4));
  }
}
