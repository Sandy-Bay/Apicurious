package sandybay.apicurious.api.item;

import java.util.function.Function;

public record TerritoryModifier(Function<Integer, Integer> xzMod, Function<Integer, Integer> yMod)
{
}
