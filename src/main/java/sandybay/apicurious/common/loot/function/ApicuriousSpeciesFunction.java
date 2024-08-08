package sandybay.apicurious.common.loot.function;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import sandybay.apicurious.api.bee.genetic.IAllele;
import sandybay.apicurious.api.register.DataComponentRegistration;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.data.LootItemFunctionRegistration;

import java.util.List;

public class ApicuriousSpeciesFunction extends LootItemConditionalFunction
{

  public static final MapCodec<ApicuriousSpeciesFunction> CODEC = RecordCodecBuilder.mapCodec(
          instance -> commonFields(instance)
                  .and(ResourceKey.codec(ApicuriousRegistries.ALLELES).fieldOf("speciesKey").forGetter(func -> func.speciesKey))
                  .apply(instance, ApicuriousSpeciesFunction::new)
  );

  private final ResourceKey<IAllele<?>> speciesKey;

  public ApicuriousSpeciesFunction(List<LootItemCondition> conditions, ResourceKey<IAllele<?>> speciesKey)
  {
    super(conditions);
    this.speciesKey = speciesKey;
  }

  public static ApicuriousSpeciesFunction.Builder getBuilder(ResourceKey<IAllele<?>> speciesKey)
  {
    return new Builder(speciesKey);
  }

  @Override
  public LootItemFunctionType<? extends LootItemConditionalFunction> getType()
  {
    return LootItemFunctionRegistration.SPECIES_FUNCTION.get();
  }

  @Override
  protected ItemStack run(ItemStack stack, LootContext context)
  {
    context.getLevel().registryAccess().registry(ApicuriousRegistries.ALLELES).ifPresent(registry ->
    {
      BeeSpecies species = (BeeSpecies) registry.get(speciesKey);
      stack.set(DataComponentRegistration.BEE_SPECIES, species);
    });
    return stack;
  }

  public static class Builder extends LootItemConditionalFunction.Builder<ApicuriousSpeciesFunction.Builder>
  {

    private final ResourceKey<IAllele<?>> speciesKey;

    Builder(ResourceKey<IAllele<?>> speciesKey)
    {
      this.speciesKey = speciesKey;
    }

    @Override
    public LootItemFunction build()
    {
      return new ApicuriousSpeciesFunction(this.getConditions(), this.speciesKey);
    }

    @Override
    protected Builder getThis()
    {
      return this;
    }
  }
}
