package sandybay.apicurious.common.bee.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import sandybay.apicurious.api.condition.ConditionType;
import sandybay.apicurious.api.condition.ICondition;
import sandybay.apicurious.api.register.ConditionTypeRegistrar;
import sandybay.apicurious.common.block.housing.blockentity.SimpleBlockHousingBE;

public record TemperatureCondition(HolderSet<Biome> temperature) implements ICondition
{
  public static final MapCodec<TemperatureCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("temperature").forGetter(TemperatureCondition::temperature)).apply(instance, TemperatureCondition::new));

  public static final StreamCodec<RegistryFriendlyByteBuf, TemperatureCondition> NETWORK_CODEC = StreamCodec.composite(ByteBufCodecs.fromCodecWithRegistries(RegistryCodecs.homogeneousList(Registries.BIOME)), TemperatureCondition::temperature, TemperatureCondition::new);

  @Override
  public ConditionType getConditionType()
  {
    return ConditionTypeRegistrar.IS_CORRECT_TEMPERATURE.get();
  }

  @Override
  public boolean test(SimpleBlockHousingBE housing)
  {
    TagKey<Biome> temperatureAtPosition = housing.validation.helper.getTemperatureAtPosition(housing.getBlockPos());
    return temperature.stream().anyMatch(h -> h.is(temperatureAtPosition));
  }

  @Override
  public Component getDisplayText()
  {
    Component base = Component.translatable("apicurious.condition.temperature");
    // Todo: Implement display text
    return base;
  }
}
