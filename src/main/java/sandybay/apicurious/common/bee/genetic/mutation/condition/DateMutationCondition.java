package sandybay.apicurious.common.bee.genetic.mutation.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import sandybay.apicurious.api.bee.genetic.mutation.condition.IMutationCondition;
import sandybay.apicurious.api.bee.genetic.mutation.condition.MutationConditionType;
import sandybay.apicurious.api.register.MutationConditionTypeRegistrar;
import sandybay.apicurious.common.block.blockentity.SimpleBlockHousingBE;

import java.time.LocalDate;

public record DateMutationCondition(LocalDate from, LocalDate to) implements IMutationCondition
{

  public static MapCodec<DateMutationCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
          instance.group(
            Codec.STRING.fieldOf("from").forGetter(c -> c.from().toString()),
            Codec.STRING.fieldOf("to").forGetter(c -> c.to().toString())
          ).apply(instance, (from, to) -> new DateMutationCondition(LocalDate.parse(from), LocalDate.parse(to)))
  );


  @Override
  public MutationConditionType getConditionType()
  {
    return MutationConditionTypeRegistrar.IS_CORRECT_DATE.get();
  }

  @Override
  public boolean test(SimpleBlockHousingBE housing)
  {
    LocalDate currentDate = LocalDate.now();
    return (from.isEqual(currentDate) || from.isBefore(currentDate)) && (to.isEqual(currentDate) || currentDate.isBefore(to));
  }
}
