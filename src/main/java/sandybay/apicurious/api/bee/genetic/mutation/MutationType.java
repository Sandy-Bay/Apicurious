package sandybay.apicurious.api.bee.genetic.mutation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

public record MutationType(MapCodec<? extends IMutation> codec)
{
  public static Codec<MutationType> CODEC = ApicuriousRegistries.MUTATION_TYPE_REGISTRY.byNameCodec();
}
