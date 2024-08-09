package sandybay.apicurious.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;
import sandybay.apicurious.api.bee.EnumBeeType;
import sandybay.apicurious.api.bee.IBeeItem;
import sandybay.apicurious.api.bee.genetic.IAllele;
import sandybay.apicurious.api.register.DataComponentRegistration;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.genetic.Genome;
import sandybay.apicurious.common.bee.species.BeeSpecies;

import java.util.List;

/**
 * Implement the following:
 * Species, which is the default Info
 * Mutation, which is the deviation
 * Mate, which is after a bee has "mated" and contains both their specific and mutation info
 */
public class BaseBeeItem extends Item implements IBeeItem
{

  public static BeeSpecies EMPTY_SPECIES = null;

  public final EnumBeeType beeType;

  public BaseBeeItem(Properties properties, EnumBeeType beeType)
  {
    super(properties);
    this.beeType = beeType;
  }

  //This should be in the API so other mods can access it if needed
  public static <T extends BaseBeeItem> ItemStack getBeeWithSpecies(Level level, ResourceKey<IAllele<?>> speciesKey, DeferredHolder<Item, T> item)
  {
    ItemStack bee = new ItemStack(item.get());
    if (level instanceof ServerLevel serverLevel)
    {
      serverLevel.registryAccess().registry(ApicuriousRegistries.ALLELES).ifPresent(registry ->
      {
        BeeSpecies species = (BeeSpecies) registry.get(speciesKey);
        if (species == null) return;
        bee.set(DataComponentRegistration.GENOME, species.getSpeciesDefaultGenome());
      });
    } else if (level instanceof ClientLevel || level == null)
    {
      ClientPacketListener connection = Minecraft.getInstance().getConnection();
      if (connection != null)
      {
        connection.registryAccess().registry(ApicuriousRegistries.ALLELES).ifPresent(registry ->
        {
          BeeSpecies species = (BeeSpecies) registry.get(speciesKey);
          if (species == null) return;
          bee.set(DataComponentRegistration.GENOME, species.getSpeciesDefaultGenome());
        });
      }
    }
    return bee;
  }


  public EnumBeeType getBeeType()
  {
    return beeType;
  }

  @Override
  public @NotNull Component getName(ItemStack stack)
  {
    Genome genome = stack.get(DataComponentRegistration.GENOME);
    if (genome == null) return Component.literal("ERROR");
    return genome.getSpecies(true).getReadableName().copy().append(" ").append(Component.translatable("item.apicurious." + getBeeType().toString().toLowerCase()));
  }

  @Override
  public boolean isFoil(ItemStack stack)
  {
    Genome genome = stack.get(DataComponentRegistration.GENOME);
    if (genome == null) return false;
    return genome.getSpecies(true).getVisualData().hasEffect();
  }

  @Override
  public void appendHoverText(@NotNull ItemStack pStack, @NotNull TooltipContext pContext, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pTooltipFlag)
  {
    super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    if (Screen.hasShiftDown())
    {
      Genome genome = pStack.get(DataComponentRegistration.GENOME);
      if (genome == null) return;
      pTooltipComponents.add(Component.translatable("apicurious.tooltip.area").append(genome.getArea(true).getReadableName()));
      pTooltipComponents.add(Component.translatable("apicurious.tooltip.lifespan").append(genome.getLifespan(true).getReadableName()));
      pTooltipComponents.add(Component.translatable("apicurious.tooltip.speed").append(genome.getSpeed(true).getReadableName()));
      pTooltipComponents.add(Component.translatable("apicurious.tooltip.fertility").append(genome.getFertility(true).getReadableName()));

      pTooltipComponents.add(Component.literal("T: ").append(genome.getTemperaturePreference(true).getReadableName()).append(" / ")
              .append(genome.getTemperatureTolerance(true).getReadableName()).withColor(ChatFormatting.GREEN.getColor()));

      pTooltipComponents.add(Component.literal("H: ").append(genome.getHumidityPreference(true).getReadableName()).append(" / ")
              .append(genome.getHumidityTolerance(true).getReadableName()).withColor(ChatFormatting.GREEN.getColor()));

      pTooltipComponents.add(Component.translatable("apicurious.tooltip.flowers").append(genome.getFlowers(true).getReadableName()));

    } else
    {
      pTooltipComponents.add(Component.translatable("apicurious.bee.shiftdown"));
    }

    if (pTooltipFlag.isAdvanced())
    {
      Genome genome = pStack.get(DataComponentRegistration.GENOME);
      if (genome == null) return;
      pTooltipComponents.add(Component.literal(genome.toString()));
    }
  }
}
