package sandybay.apicurious.common.bee.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import sandybay.apicurious.api.condition.ConditionType;
import sandybay.apicurious.api.condition.ICondition;
import sandybay.apicurious.api.register.ConditionTypeRegistrar;
import sandybay.apicurious.common.block.housing.blockentity.SimpleBlockHousingBE;

import java.time.LocalDate;
import java.time.MonthDay;

import static sandybay.apicurious.api.codec.ApicuriousCodecs.*;
import static sandybay.apicurious.api.codec.ApicuriousCodecs.MONTH_DAY_CODEC;

public record DateCondition(MonthDay from, MonthDay to) implements ICondition
{

  public static final StreamCodec<RegistryFriendlyByteBuf, DateCondition> NETWORK_CODEC =
          StreamCodec.composite(
                  MONTH_DAY_STREAM_CODEC,
                  DateCondition::from,
                  MONTH_DAY_STREAM_CODEC,
                  DateCondition::to,
                  DateCondition::new
          );

  public static final MapCodec<DateCondition> CODEC =
          RecordCodecBuilder.mapCodec(instance -> instance.group(
                  MONTH_DAY_CODEC.fieldOf("from").forGetter(DateCondition::from),
                  MONTH_DAY_CODEC.fieldOf("to").forGetter(DateCondition::to)
          ).apply(instance, DateCondition::new));

  @Override
  public ConditionType getConditionType()
  {
    return ConditionTypeRegistrar.IS_CORRECT_DATE.get();
  }

  @Override
  public boolean test(SimpleBlockHousingBE housing)
  {
    MonthDay today = MonthDay.from(LocalDate.now());

    // Normal range (Apr 1 -> Jun 30)
    if (!to.isBefore(from))
    {
      return !today.isBefore(from) && !today.isAfter(to);
    }

    // Wrapped range (Dec 20 -> Jan 10)
    return !today.isBefore(from) || !today.isAfter(to);
  }

  @Override
  public Component getDisplayText()
  {
    return Component.literal(
            "- Date: %02d-%02d -> %02d-%02d".formatted(
                    from.getMonthValue(),
                    from.getDayOfMonth(),
                    to.getMonthValue(),
                    to.getDayOfMonth()
            )
    );
  }
}