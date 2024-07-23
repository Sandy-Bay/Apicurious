package sandybay.apicurious.api.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.registries.DeferredHolder;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.client.BeeItemRenderer;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.bee.species.VisualData;
import sandybay.apicurious.common.register.ApicuriousDataComponentRegistration;

import java.util.function.Consumer;

/**
 * Implement the following:
 * Species, which is the default Info
 * Mutation, which is the deviation
 * Mate, which is after a bee has "mated" and contains both their specific and mutation info
 */
public abstract class BaseBeeItem extends Item {

  private static final BeeSpecies EMPTY_SPECIES = new BeeSpecies("apicurious.species.undefined", VisualData.Builder.create().build());

  public BaseBeeItem(Properties properties) {
    super(properties.component(ApicuriousDataComponentRegistration.BEE_SPECIES, EMPTY_SPECIES));
  }

  public static BeeSpecies getSpecies(ItemStack stack) {
    return stack.get(ApicuriousDataComponentRegistration.BEE_SPECIES);
  }

  public static <T extends BaseBeeItem> ItemStack getBeeWithSpecies(Level level, ResourceKey<BeeSpecies> speciesKey, DeferredHolder<Item, T> item) {
    ItemStack bee = new ItemStack(item.get());
    if (level instanceof ServerLevel serverLevel) {
      serverLevel.registryAccess().registry(ApicuriousRegistries.BEE_SPECIES).ifPresent(registry -> {
        BeeSpecies species = registry.get(speciesKey);
        bee.set(ApicuriousDataComponentRegistration.BEE_SPECIES, species);
      });
    } else if (level instanceof ClientLevel || level == null) {
      ClientPacketListener connection = Minecraft.getInstance().getConnection();
      if (connection != null) {
        connection.registryAccess().registry(ApicuriousRegistries.BEE_SPECIES).ifPresent(registry -> {
          BeeSpecies species = registry.get(speciesKey);
          bee.set(ApicuriousDataComponentRegistration.BEE_SPECIES, species);
        });
      }
    }
    return bee;
  }

  public abstract String getType();

  @Override
  public Component getName(ItemStack stack) {
    BeeSpecies species = stack.get(ApicuriousDataComponentRegistration.BEE_SPECIES);
    if (species == null) return Component.literal("ERROR");
    return species.getReadableName().copy().append(" ").append(Component.translatable("apicurious.item.bee." + getType()));
  }

  @Override
  public boolean isFoil(ItemStack stack) {
    BeeSpecies species = stack.get(ApicuriousDataComponentRegistration.BEE_SPECIES);
    if (species == null) return false;
    return species.getVisualData().hasEffect();
  }

  @Override
  public void initializeClient(Consumer<IClientItemExtensions> consumer) {
    consumer.accept(new IClientItemExtensions() {
      @Override
      public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return new BeeItemRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
      }
    });
  }
}
