package sandybay.apicurious.api.item;

import net.minecraft.world.item.Item;
import sandybay.apicurious.api.bee.species.BeeSpecies;

/**
 * Implement the following:
 * Species, which is the default Info
 * Mutation, which is the deviation
 * Mate, which is after a bee has "mated" and contains both their specific and mutation info
 */
public class ApicuriousBeeItem extends Item {

  private final BeeSpecies species;

  public ApicuriousBeeItem(Properties properties, BeeSpecies species) {
    super(properties);
    this.species = species;
    //TODO: Implement DataComponents for BeeSpecies, Mutations, Mate
  }

  public BeeSpecies getSpecies() {
    return species;
  }

}
