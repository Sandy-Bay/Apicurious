package sandybay.apicurious.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import sandybay.apicurious.api.bee.EnumBeeType;
import sandybay.apicurious.api.bee.IBeeItem;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.register.DataComponentRegistrar;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.genetic.Genome;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.config.ApicuriousMainConfig;

import java.util.function.Consumer;

public class BeeItem extends Item implements IBeeItem
{

  public static BeeSpecies EMPTY_SPECIES = null;

  public final EnumBeeType beeType;

  public BeeItem(Properties properties, EnumBeeType beeType)
  {
    super(properties.component(DataComponentRegistrar.IDENTIFIED, false));
    this.beeType = beeType;
  }

  //This should be in the API so other mods can access it if needed
  public static ItemStack getBeeWithSpecies(Level level, ResourceKey<IAllele<?>> speciesKey, Holder<Item> item)
  {
    ItemStack bee = new ItemStack(item);
    level.registryAccess().lookup(ApicuriousRegistries.ALLELES).ifPresent(alleles ->
    {
      BeeSpecies species = (BeeSpecies) alleles.getOrThrow(speciesKey).value();
      bee.set(DataComponentRegistrar.GENOME, species.getSpeciesDefaultGenome(level));
    });
    return bee;
  }

  public static ItemStack getBeeWithSpecies(HolderLookup.Provider provider, ResourceKey<IAllele<?>> speciesKey,
                                            Holder<Item> item)
  {
    ItemStack bee = new ItemStack(item);
    provider.lookup(ApicuriousRegistries.ALLELES).ifPresent(registry ->
    {
      BeeSpecies species = (BeeSpecies) registry.getOrThrow(speciesKey).value();
      bee.set(DataComponentRegistrar.GENOME, species.getSpeciesDefaultGenome(provider));
    });
    return bee;
  }

  @Override
  public InteractionResult use(Level pLevel, Player pPlayer, InteractionHand pUsedHand)
  {
    pPlayer.getItemInHand(pUsedHand).set(DataComponentRegistrar.IDENTIFIED, true);
    return super.use(pLevel, pPlayer, pUsedHand);
  }

  public EnumBeeType getBeeType()
  {
    return beeType;
  }

  @Override
  public @NotNull Component getName(ItemStack stack)
  {
    if (ApicuriousMainConfig.main_config.weDontTalkAboutThat.get() && getBeeType() == EnumBeeType.DRONE)
    {
      return Component.translatable("item.apicurious.drone.secret");
    }
    Genome genome = stack.get(DataComponentRegistrar.GENOME);
    if (genome == null)
    {
      return Component.literal("ERROR");
    }
    return genome.getSpecies(true).value().getReadableName().copy().append(" ").append(Component.translatable("item.apicurious." + getBeeType().toString().toLowerCase()));
  }

  @Override
  public boolean isFoil(ItemStack stack)
  {
    Genome genome = stack.get(DataComponentRegistrar.GENOME);
    if (genome == null)
    {
      return false;
    }
    return ((BeeSpecies) genome.getSpecies(true).value()).getVisualData().hasEffect();
  }

  @Override
  public void appendHoverText(@NotNull ItemStack pStack, @NotNull TooltipContext pContext,
                              TooltipDisplay tooltipDisplay, @NotNull Consumer<Component> pTooltipComponents,
                              @NotNull TooltipFlag pTooltipFlag)
  {
    super.appendHoverText(pStack, pContext, tooltipDisplay, pTooltipComponents, pTooltipFlag);
    if (pStack.has(DataComponentRegistrar.IDENTIFIED) && Boolean.TRUE.equals(pStack.get(DataComponentRegistrar.IDENTIFIED)))
    {
      if (Minecraft.getInstance().hasShiftDown())
      {
        Genome genome = pStack.get(DataComponentRegistrar.GENOME);
        if (genome == null)
        {
          return;
        }
        pTooltipComponents.accept(Component.translatable("apicurious.tooltip.area").append(genome.getArea(true).value().getReadableName()));
        pTooltipComponents.accept(Component.translatable("apicurious.tooltip.lifespan").append(genome.getLifespan(true).value().getReadableName()));
        pTooltipComponents.accept(Component.translatable("apicurious.tooltip.speed").append(genome.getSpeed(true).value().getReadableName()));
        pTooltipComponents.accept(Component.translatable("apicurious.tooltip.fertility").append(genome.getFertility(true).value().getReadableName()));
        pTooltipComponents.accept(Component.literal("T: ").append(genome.getTemperaturePreference(true).value().getReadableName()).append(" / ").append(genome.getTemperatureTolerance(true).value().getReadableName()).withStyle(ChatFormatting.GREEN));
        pTooltipComponents.accept(Component.literal("H: ").append(genome.getHumidityPreference(true).value().getReadableName()).append(" / ").append(genome.getHumidityTolerance(true).value().getReadableName()).withStyle(ChatFormatting.GREEN));
        pTooltipComponents.accept(Component.translatable("apicurious.tooltip.flowers").append(genome.getFlowers(true).value().getReadableName()));
      }
      else
      {
        pTooltipComponents.accept(Component.translatable("apicurious.bee.shiftdown"));
      }
    }
    else
    {
      pTooltipComponents.accept(Component.translatable("apicurious.tooltip.unidentified"));
    }
  }
}
