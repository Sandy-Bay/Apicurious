package sandybay.apicurious.common.bee.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.biome.Biome;
import sandybay.apicurious.api.condition.ConditionType;
import sandybay.apicurious.api.condition.ICondition;
import sandybay.apicurious.api.register.ConditionTypeRegistrar;
import sandybay.apicurious.common.block.blockentity.SimpleBlockHousingBE;

public record BiomeCondition(HolderSet<Biome> biomes) implements ICondition
{

  public static final MapCodec<BiomeCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
          instance.group(
                  RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(BiomeCondition::biomes)
          ).apply(instance, BiomeCondition::new)
  );

  public static final StreamCodec<RegistryFriendlyByteBuf, BiomeCondition> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.fromCodecWithRegistries(RegistryCodecs.homogeneousList(Registries.BIOME)), BiomeCondition::biomes,
          BiomeCondition::new
  );

  @Override
  public ConditionType getConditionType()
  {
    return ConditionTypeRegistrar.IS_CORRECT_BIOME.get();
  }

  @Override
  public boolean test(SimpleBlockHousingBE housing)
  {
    if (housing.getLevel() == null) return false;
    Holder<Biome> biome = housing.getLevel().getBiome(housing.getBlockPos());
    return biomes().contains(biome);
  }
}
