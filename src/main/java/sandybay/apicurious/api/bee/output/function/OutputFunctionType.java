package sandybay.apicurious.api.bee.output.function;

import com.mojang.serialization.MapCodec;

public record OutputFunctionType(MapCodec<? extends OutputFunction> codec) {}
