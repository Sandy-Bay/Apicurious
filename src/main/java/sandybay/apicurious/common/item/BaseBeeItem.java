package sandybay.apicurious.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;
import sandybay.apicurious.api.bee.EnumBeeType;
import sandybay.apicurious.api.bee.IBeeItem;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.client.BeeItemRenderer;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.register.ApicuriousDataComponentRegistration;

import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

/**
 * Implement the following:
 * Species, which is the default Info
 * Mutation, which is the deviation
 * Mate, which is after a bee has "mated" and contains both their specific and mutation info
 */
public class BaseBeeItem extends Item implements IBeeItem {

  public static BeeSpecies EMPTY_SPECIES = null;

  public final EnumBeeType beeType;

  public BaseBeeItem(Properties properties, EnumBeeType beeType) {
    super(properties.component(ApicuriousDataComponentRegistration.BEE_SPECIES, EMPTY_SPECIES));
    this.beeType = beeType;
  }

  public static BeeSpecies getSpecies(ItemStack stack) {
    return stack.get(ApicuriousDataComponentRegistration.BEE_SPECIES);
  }

  //This should be in the API so other mods can access it if needed
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


  public EnumBeeType getBeeType() {
    return beeType;
  }

  @Override
  public @NotNull Component getName(ItemStack stack) {
    BeeSpecies species = stack.get(ApicuriousDataComponentRegistration.BEE_SPECIES);
    if (species == null) return Component.literal("ERROR");
    return species.getReadableName().copy().append(" ").append(Component.translatable("item.apicurious." + getBeeType().toString().toLowerCase()));
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
      public @NotNull BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return new BeeItemRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
      }
    });
  }

  @Override
  public void appendHoverText(@NotNull ItemStack pStack, @NotNull TooltipContext pContext, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pTooltipFlag)
  {
    super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    if(Screen.hasShiftDown())
    {
      BeeSpecies species = getSpecies(pStack);
      pTooltipComponents.add(species.getProductionData().getLifespan().value().getReadableName());
      pTooltipComponents.add(species.getProductionData().getSpeed().value().getReadableName());

      var tempData = species.getEnvironmentalData().getTemperatureData();
      pTooltipComponents.add(Component.literal("T: ").append(tempData.preference().value().getReadableName()).append(" / ")
              .append(tempData.tolerance().value().getReadableName()).withColor(ChatFormatting.GREEN.getColor()));

      var humidData = species.getEnvironmentalData().getHumidityData();
      pTooltipComponents.add(Component.literal("H: ").append(humidData.preference().value().getReadableName()).append(" / ")
              .append(humidData.tolerance().value().getReadableName()).withColor(ChatFormatting.GREEN.getColor()));

      pTooltipComponents.add(species.getEnvironmentalData().getFlowers().value().getReadableName());

    }
    else
    {
      pTooltipComponents.add(Component.translatable("apicurious.bee.shiftdown"));
    }
  }
}
