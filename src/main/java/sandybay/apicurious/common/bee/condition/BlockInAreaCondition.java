package sandybay.apicurious.common.bee.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import sandybay.apicurious.api.condition.ConditionType;
import sandybay.apicurious.api.condition.ICondition;
import sandybay.apicurious.api.housing.BaseHousingBlock;
import sandybay.apicurious.api.register.ConditionTypeRegistrar;
import sandybay.apicurious.api.util.SimpleBlockHousingHelper;
import sandybay.apicurious.common.block.housing.blockentity.SimpleBlockHousingBE;

import java.util.stream.Collectors;

public record BlockInAreaCondition(HolderSet<Block> blocks) implements ICondition
{
  public static final MapCodec<BlockInAreaCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("blocks").forGetter(BlockInAreaCondition::blocks)).apply(instance, BlockInAreaCondition::new));
  public static final StreamCodec<RegistryFriendlyByteBuf, BlockInAreaCondition> NETWORK_CODEC = StreamCodec.composite(ByteBufCodecs.fromCodecWithRegistries(RegistryCodecs.homogeneousList(Registries.BLOCK)), BlockInAreaCondition::blocks, BlockInAreaCondition::new);

  @Override
  public ConditionType getConditionType()
  {
    return ConditionTypeRegistrar.HAS_BLOCK_IN_VICINITY.get();
  }

  @Override
  public boolean test(SimpleBlockHousingBE housing)
  {
    Level level = housing.getLevel();
    if (level == null || !(housing.getBlockState().getBlock() instanceof BaseHousingBlock block)) {return false;}
    if (housing.territory == null)
    {housing.territory = block.getTerritory(housing.getInventory().getResource(0), housing.getBlockPos(), SimpleBlockHousingHelper.getFrames(housing));}
    return housing.territory.stream().anyMatch(pos -> blocks.contains(level.getBlockState(pos).typeHolder()));
  }

  @Override
  public Component getDisplayText()
  {
    // Implemented: list the resource locations of the required blocks.
    String blockList = blocks().stream()
            .map(holder -> holder.unwrapKey()
                    .map(key -> key.identifier().toString())
                    .orElse("unknown"))
            .collect(Collectors.joining(", "));
    return Component.translatable("apicurious.condition.block_in_area", blockList);
  }
}