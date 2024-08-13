package sandybay.apicurious.common.bee.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import sandybay.apicurious.api.bee.condition.ICondition;
import sandybay.apicurious.api.bee.condition.ConditionType;
import sandybay.apicurious.api.register.ConditionTypeRegistrar;
import sandybay.apicurious.common.block.blockentity.SimpleBlockHousingBE;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalField;
import java.util.Date;

// TODO: Figure out how to make this work with just days and months, since years would fuck-up things and require rewriting.
public record DateCondition(LocalDate from, LocalDate to) implements ICondition
{
  public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM:dd");

  public static MapCodec<DateCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
          instance.group(
            Codec.STRING.fieldOf("from").forGetter(c -> c.from().format(FORMATTER)),
            Codec.STRING.fieldOf("to").forGetter(c -> c.to().format(FORMATTER))
          ).apply(instance, (from, to) -> new DateCondition(LocalDate.parse(from, FORMATTER), LocalDate.parse(to, FORMATTER)))
  );

  public static final StreamCodec<RegistryFriendlyByteBuf, DateCondition> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.STRING_UTF8, condition -> condition.from.toString(),
          ByteBufCodecs.STRING_UTF8, condition -> condition.to.toString(),
          (from, to) -> new DateCondition(LocalDate.parse(from), LocalDate.parse(to))
  );


  @Override
  public ConditionType getConditionType()
  {
    return ConditionTypeRegistrar.IS_CORRECT_DATE.get();
  }

  @Override
  public boolean test(SimpleBlockHousingBE housing)
  {
    LocalDate currentDate = LocalDate.parse(LocalDate.now().format(FORMATTER), FORMATTER);
    return (from.isEqual(currentDate) || from.isBefore(currentDate)) && (to.isEqual(currentDate) || currentDate.isBefore(to));
  }
}
