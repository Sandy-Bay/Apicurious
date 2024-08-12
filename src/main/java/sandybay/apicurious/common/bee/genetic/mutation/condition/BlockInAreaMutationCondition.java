package sandybay.apicurious.common.bee.genetic.mutation.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import sandybay.apicurious.api.bee.genetic.mutation.condition.IMutationCondition;
import sandybay.apicurious.api.bee.genetic.mutation.condition.MutationConditionType;
import sandybay.apicurious.api.housing.BaseHousingBlock;
import sandybay.apicurious.api.register.MutationConditionTypeRegistrar;
import sandybay.apicurious.api.util.SimpleBlockHousingHelper;
import sandybay.apicurious.common.block.blockentity.SimpleBlockHousingBE;

public record BlockInAreaMutationCondition(HolderSet<Block> blocks) implements IMutationCondition
{
  public static final MapCodec<BlockInAreaMutationCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
          instance.group(
                  RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("biomes").forGetter(BlockInAreaMutationCondition::blocks)
          ).apply(instance, BlockInAreaMutationCondition::new)
  );

  @Override
  public MutationConditionType getConditionType()
  {
    return MutationConditionTypeRegistrar.HAS_BLOCK_IN_VICINITY.get();
  }

  @Override
  public boolean test(SimpleBlockHousingBE housing)
  {
    Level level = housing.getLevel();
    if (level == null || !(housing.getBlockState().getBlock() instanceof BaseHousingBlock block)) return false;
    if (housing.territory == null) housing.territory = block.getTerritory(housing.getInventory().getStackInSlot(0), housing.getBlockPos(), SimpleBlockHousingHelper.getFrames(housing));
    return housing.territory.stream().anyMatch(pos -> blocks.contains(level.getBlockState(pos).getBlockHolder()));
  }
}
