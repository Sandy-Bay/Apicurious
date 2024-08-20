package sandybay.apicurious.api.function;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.block.housing.blockentity.SimpleBlockHousingBE;

import java.util.List;
import java.util.function.BiFunction;

public interface IFunction extends BiFunction<SimpleBlockHousingBE, List<ItemStack>, List<ItemStack>>
{
  Codec<IFunction> TYPED_CODEC = ApicuriousRegistries.FUNCTION_TYPE_REGISTRY
          .byNameCodec()
          .dispatch("function", IFunction::getFunctionType, FunctionType::codec);

  StreamCodec<RegistryFriendlyByteBuf, IFunction> NETWORK_TYPED_CODEC = ByteBufCodecs
          .registry(ApicuriousRegistries.FUNCTION_TYPES)
          .dispatch(IFunction::getFunctionType, FunctionType::streamCodec);

  default List<ItemStack> resolve(SimpleBlockHousingBE housing, List<ItemStack> stacks)
  {
    return this.apply(housing, stacks);
  }

  FunctionType getFunctionType();
}
