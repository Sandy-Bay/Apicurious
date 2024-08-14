package sandybay.apicurious.common.bee.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import sandybay.apicurious.api.condition.ConditionType;
import sandybay.apicurious.api.condition.ICondition;
import sandybay.apicurious.api.housing.blockentity.SimpleBlockHousingBE;
import sandybay.apicurious.api.register.ConditionTypeRegistrar;

public record HumidityCondition(HolderSet<Biome> humidity) implements ICondition
{
  public static final MapCodec<HumidityCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
          instance.group(
                  RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("humidity").forGetter(HumidityCondition::humidity)
          ).apply(instance, HumidityCondition::new)
  );

  public static final StreamCodec<RegistryFriendlyByteBuf, HumidityCondition> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.fromCodecWithRegistries(RegistryCodecs.homogeneousList(Registries.BIOME)), HumidityCondition::humidity,
          HumidityCondition::new
  );

  @Override
  public ConditionType getConditionType()
  {
    return ConditionTypeRegistrar.IS_CORRECT_HUMIDITY.get();
  }

  @Override
  public boolean test(SimpleBlockHousingBE housing)
  {
    TagKey<Biome> humidityAtPosition = housing.validation.helper.getHumidityAtPosition(housing.getBlockPos());
    return humidity.stream().anyMatch(h -> h.is(humidityAtPosition));
  }
}
