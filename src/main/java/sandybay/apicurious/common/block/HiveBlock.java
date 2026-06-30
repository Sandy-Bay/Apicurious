package sandybay.apicurious.common.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.NotNull;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

public class HiveBlock extends Block
{
  public static final MapCodec<HiveBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(ResourceKey.codec(ApicuriousRegistries.ALLELES).fieldOf("speciesKey").forGetter(HiveBlock::getSpecies), BlockBehaviour.propertiesCodec()).apply(instance, HiveBlock::new));
  private final ResourceKey<IAllele<?>> species;

  public HiveBlock(ResourceKey<IAllele<?>> species, Properties properties)
  {
    super(properties);
    this.registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.FACING, Direction.NORTH));
    this.species = species;
  }

  @Override
  protected @NotNull MapCodec<? extends Block> codec()
  {
    return CODEC;
  }

  @Override
  public BlockState getStateForPlacement(BlockPlaceContext pContext)
  {
    return this.defaultBlockState().setValue(BlockStateProperties.FACING, pContext.getHorizontalDirection().getOpposite());
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
  {
    pBuilder.add(BlockStateProperties.FACING);
  }

  public ResourceKey<IAllele<?>> getSpecies()
  {
    return species;
  }
}
