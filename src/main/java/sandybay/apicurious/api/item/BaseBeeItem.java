package sandybay.apicurious.api.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.bee.species.VisualData;
import sandybay.apicurious.common.datacomponent.ApicuriousDataComponents;

/**
 * Implement the following:
 * Species, which is the default Info
 * Mutation, which is the deviation
 * Mate, which is after a bee has "mated" and contains both their specific and mutation info
 */
public abstract class BaseBeeItem extends Item {

  private static final BeeSpecies EMPTY_SPECIES = new BeeSpecies("apicurious.species.undefined", VisualData.Builder.create().build());

  public BaseBeeItem(Properties properties) {
    super(properties.component(ApicuriousDataComponents.BEE_SPECIES, EMPTY_SPECIES));
  }

  public static BeeSpecies getSpecies(ItemStack stack) {
    return stack.get(ApicuriousDataComponents.BEE_SPECIES);
  }

  public static <T extends BaseBeeItem> ItemStack getBeeWithSpecies(Level level, ResourceKey<BeeSpecies> speciesKey, DeferredHolder<Item, T> item) {
    ItemStack bee = new ItemStack(item.get());
    if (level instanceof ServerLevel serverLevel) {
      serverLevel.registryAccess().registry(ApicuriousRegistries.BEE_SPECIES).ifPresent(registry -> {
        BeeSpecies species = registry.get(speciesKey);
        bee.set(ApicuriousDataComponents.BEE_SPECIES, species);
      });
    } else if (level instanceof ClientLevel || level == null) {
      ClientPacketListener connection = Minecraft.getInstance().getConnection();
      if (connection != null) {
        connection.registryAccess().registry(ApicuriousRegistries.BEE_SPECIES).ifPresent(registry -> {
          BeeSpecies species = registry.get(speciesKey);
          bee.set(ApicuriousDataComponents.BEE_SPECIES, species);
        });
      }
    }
    return bee;
  }

  public abstract String getType();

  @Override
  public Component getName(ItemStack stack) {
    BeeSpecies species = stack.get(ApicuriousDataComponents.BEE_SPECIES);
    if (species == null) return Component.literal("ERROR");
    return species.getReadableName().copy().append(" ").append(Component.translatable("apicurious.item.bee." + getType()));
  }

  @Override
  public boolean isFoil(ItemStack stack) {
    BeeSpecies species = stack.get(ApicuriousDataComponents.BEE_SPECIES);
    if (species == null) return false;
    return species.getVisualData().hasEffect();
  }
}
