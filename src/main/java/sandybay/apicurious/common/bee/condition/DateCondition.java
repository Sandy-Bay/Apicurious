package sandybay.apicurious.common.bee.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import sandybay.apicurious.api.condition.ConditionType;
import sandybay.apicurious.api.condition.ICondition;
import sandybay.apicurious.common.block.blockentity.SimpleBlockHousingBE;
import sandybay.apicurious.api.register.ConditionTypeRegistrar;

import java.time.LocalDate;

// TODO: Figure out how to make this work with just days and months, since years would fuck-up things and require rewriting.
public record DateCondition(LocalDate from, LocalDate to) implements ICondition
{
  public static final StreamCodec<RegistryFriendlyByteBuf, DateCondition> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.STRING_UTF8, condition -> condition.from.toString(),
          ByteBufCodecs.STRING_UTF8, condition -> condition.to.toString(),
          (from, to) ->
          {
            if (LocalDate.parse(to).isBefore(LocalDate.parse(from)))
              throw new IllegalArgumentException("To date can't be before From date!");
            return new DateCondition(LocalDate.parse(from), LocalDate.parse(to));
          }
  );
  public static MapCodec<DateCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
          instance.group(
                  Codec.STRING.fieldOf("from").forGetter(c -> c.from().toString()),
                  Codec.STRING.fieldOf("to").forGetter(c -> c.to().toString())
          ).apply(instance, (from, to) ->
          {
            if (LocalDate.parse(to).isBefore(LocalDate.parse(from)))
              throw new IllegalArgumentException("To date can't be before From date!");
            return new DateCondition(LocalDate.parse(from), LocalDate.parse(to));
          })
  );

  @Override
  public ConditionType getConditionType()
  {
    return ConditionTypeRegistrar.IS_CORRECT_DATE.get();
  }

  @Override
  public boolean test(SimpleBlockHousingBE housing)
  {
    if (to().isBefore(from())) return false;
    LocalDate currentDate = LocalDate.now();
    int currentDay = currentDate.getDayOfMonth();
    int currentMonth = currentDate.getMonthValue();
    return (from.getMonthValue() >= currentMonth && currentMonth <= to.getMonthValue()) &&
            (from.getDayOfMonth() >= currentDay && currentDay <= to.getDayOfMonth());
  }
}
