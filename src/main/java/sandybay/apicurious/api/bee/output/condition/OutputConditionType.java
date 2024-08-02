package sandybay.apicurious.api.bee.output.condition;

import com.mojang.serialization.MapCodec;

public record OutputConditionType(MapCodec<? extends OutputCondition> codec) {}
