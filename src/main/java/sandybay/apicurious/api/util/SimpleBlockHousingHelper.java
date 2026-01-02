package sandybay.apicurious.api.util;

import net.minecraft.core.Holder;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.register.DataComponentRegistrar;
import sandybay.apicurious.common.bee.genetic.Genome;
import sandybay.apicurious.common.block.housing.blockentity.SimpleBlockHousingBE;

import java.util.List;

public class SimpleBlockHousingHelper
{
  public static Holder<IAllele<?>> getSpeciesInSlot(SimpleBlockHousingBE housing, int slot, boolean active)
  {
    ItemResource stack = housing.getInventory().getResource(slot);
    Genome genome = stack.get(DataComponentRegistrar.GENOME);
    if (genome == null) {return null;}
    return genome.getSpecies(active);
  }

  public static List<ItemResource> getFrames(SimpleBlockHousingBE housing)
  {
    ItemStacksResourceHandler handler = housing.getInventory();
    return List.of(handler.getResource(2), handler.getResource(3), handler.getResource(4));
  }
}
