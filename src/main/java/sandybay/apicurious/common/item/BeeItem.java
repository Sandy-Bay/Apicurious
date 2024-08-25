package sandybay.apicurious.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import sandybay.apicurious.api.bee.EnumBeeType;
import sandybay.apicurious.api.bee.IBeeItem;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.register.DataComponentRegistrar;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.genetic.Genome;
import sandybay.apicurious.common.bee.species.BeeSpecies;

import java.util.List;

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
    if (level instanceof ServerLevel serverLevel)
    {
      serverLevel.registryAccess().registry(ApicuriousRegistries.ALLELES).ifPresent(registry ->
      {
        BeeSpecies species = (BeeSpecies) registry.get(speciesKey);
        if (species == null) {return;}
        bee.set(DataComponentRegistrar.GENOME, species.getSpeciesDefaultGenome(level));
      });
    }
    else if (level instanceof ClientLevel || level == null)
    {
      ClientPacketListener connection = Minecraft.getInstance().getConnection();
      if (connection != null)
      {
        connection.registryAccess().registry(ApicuriousRegistries.ALLELES).ifPresent(registry ->
        {
          BeeSpecies species = (BeeSpecies) registry.get(speciesKey);
          if (species == null) {return;}
          bee.set(DataComponentRegistrar.GENOME, species.getSpeciesDefaultGenome(level));
        });
      }
    }
    return bee;
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand)
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
    Genome genome = stack.get(DataComponentRegistrar.GENOME);
    if (genome == null) {return Component.literal("ERROR");}
    return genome.getSpecies(true).value().getReadableName().copy().append(" ").append(Component.translatable("item.apicurious." + getBeeType().toString().toLowerCase()));
  }

  @Override
  public boolean isFoil(ItemStack stack)
  {
    Genome genome = stack.get(DataComponentRegistrar.GENOME);
    if (genome == null) {return false;}
    return ((BeeSpecies) genome.getSpecies(true).value()).getVisualData().hasEffect();
  }

  @Override
  public void appendHoverText(@NotNull ItemStack pStack, @NotNull TooltipContext pContext, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pTooltipFlag)
  {
    super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    if (pStack.has(DataComponentRegistrar.IDENTIFIED) && Boolean.TRUE.equals(pStack.get(DataComponentRegistrar.IDENTIFIED)))
    {
      if (Screen.hasShiftDown())
      {
        Genome genome = pStack.get(DataComponentRegistrar.GENOME);
        if (genome == null) {return;}
        pTooltipComponents.add(Component.translatable("apicurious.tooltip.area").append(genome.getArea(true).value().getReadableName()));
        pTooltipComponents.add(Component.translatable("apicurious.tooltip.lifespan").append(genome.getLifespan(true).value().getReadableName()));
        pTooltipComponents.add(Component.translatable("apicurious.tooltip.speed").append(genome.getSpeed(true).value().getReadableName()));
        pTooltipComponents.add(Component.translatable("apicurious.tooltip.fertility").append(genome.getFertility(true).value().getReadableName()));

        pTooltipComponents.add(Component.literal("T: ").append(genome.getTemperaturePreference(true).value().getReadableName()).append(" / ").append(genome.getTemperatureTolerance(true).value().getReadableName()).withStyle(ChatFormatting.GREEN));

        pTooltipComponents.add(Component.literal("H: ").append(genome.getHumidityPreference(true).value().getReadableName()).append(" / ").append(genome.getHumidityTolerance(true).value().getReadableName()).withStyle(ChatFormatting.GREEN));

        pTooltipComponents.add(Component.translatable("apicurious.tooltip.flowers").append(genome.getFlowers(true).value().getReadableName()));

      }
      else
      {
        pTooltipComponents.add(Component.translatable("apicurious.bee.shiftdown"));
      }
    }
    else
    {
      pTooltipComponents.add(Component.translatable("apicurious.tooltip.unidentified"));
    }
  }
}
