package sandybay.apicurious.common.loot.function;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.register.DataComponentRegistrar;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.species.BeeSpecies;

import java.util.Objects;

public record ApicuriousSpeciesFunction(ResourceKey<IAllele<?>> speciesKey) implements LootItemFunction
{
  public static final MapCodec<ApicuriousSpeciesFunction> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
          .group(ResourceKey.codec(ApicuriousRegistries.ALLELES).fieldOf("speciesKey").forGetter(func -> func.speciesKey))
          .apply(instance, ApicuriousSpeciesFunction::new));

  @Override
  public MapCodec<? extends LootItemFunction> codec()
  {
    return CODEC;
  }

  @Override
  public ItemStack apply(ItemStack stack, LootContext context)
  {
    context.getLevel()
            .registryAccess()
            .get(ApicuriousRegistries.ALLELES)
            .flatMap(registryReference -> Objects.requireNonNull(registryReference.value()).getOptional(speciesKey))
            .ifPresent(allele -> stack.set(DataComponentRegistrar.GENOME, ((BeeSpecies) allele).getSpeciesDefaultGenome(context.getLevel())));
    return stack;
  }

  public static Builder getBuilder(ResourceKey<IAllele<?>> speciesKey)
  {
    return new Builder(speciesKey);
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
