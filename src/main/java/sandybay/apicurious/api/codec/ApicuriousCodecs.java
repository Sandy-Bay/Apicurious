package sandybay.apicurious.api.codec;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.time.MonthDay;

public class ApicuriousCodecs
{
  public static final Codec<MonthDay> MONTH_DAY_CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.INT.fieldOf("month").forGetter(MonthDay::getMonthValue), Codec.INT.fieldOf("day").forGetter(MonthDay::getDayOfMonth)).apply(instance, MonthDay::of));

  public static final StreamCodec<RegistryFriendlyByteBuf, MonthDay> MONTH_DAY_STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.INT, MonthDay::getMonthValue, ByteBufCodecs.INT, MonthDay::getDayOfMonth, MonthDay::of);
}
