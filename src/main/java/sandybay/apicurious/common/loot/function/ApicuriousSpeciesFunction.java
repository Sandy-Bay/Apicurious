package sandybay.apicurious.common.loot.function;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.register.DataComponentRegistrar;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.item.BeeItem;

public record ApicuriousSpeciesFunction(ResourceKey<IAllele<?>> speciesKey) implements LootItemFunction
{
  public static final MapCodec<ApicuriousSpeciesFunction> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(ResourceKey.codec(ApicuriousRegistries.ALLELES).fieldOf("speciesKey").forGetter(func -> func.speciesKey)).apply(instance, ApicuriousSpeciesFunction::new));
  private static final Logger LOGGER = LogManager.getLogger(ApicuriousSpeciesFunction.class);

  public static Builder getBuilder(ResourceKey<IAllele<?>> speciesKey)
  {
    return new Builder(speciesKey);
  }

  @Override
  public MapCodec<? extends LootItemFunction> codec()
  {
    return CODEC;
  }

  @Override
  public ItemStack apply(ItemStack stack, LootContext context)
  {
    if (stack.getItem() instanceof BeeItem)
    {
      Level level = context.getLevel();
      level.registryAccess().lookup(ApicuriousRegistries.ALLELES).ifPresentOrElse(alleles ->
      {
        IAllele<?> allele = alleles.getValue(speciesKey);
        if (allele == null)
        {
          LOGGER.warn("ApicuriousSpeciesFunction: no allele registered for key '{}'; leaving item stack unmodified.", speciesKey.identifier());
          return;
        }
        if (!(allele instanceof BeeSpecies species))
        {
          LOGGER.warn("ApicuriousSpeciesFunction: allele for key '{}' is not a BeeSpecies (was {}); leaving item stack unmodified.", speciesKey.identifier(), allele.getClass().getName());
          return;
        }
        stack.set(DataComponentRegistrar.GENOME, species.getSpeciesDefaultGenome(level));
      }, () -> LOGGER.warn("ApicuriousSpeciesFunction: allele registry '{}' is not available in this context; leaving item stack unmodified.", ApicuriousRegistries.ALLELES.identifier()));
    }
    return stack;
  }

  public record Builder(ResourceKey<IAllele<?>> speciesKey) implements LootItemFunction.Builder
  {
    @Override
    public LootItemFunction build()
    {
      return new ApicuriousSpeciesFunction(speciesKey);
    }
  }
}