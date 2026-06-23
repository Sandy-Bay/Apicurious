package sandybay.apicurious.client.tinter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.api.util.ClientHelper;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.block.HiveBlock;

import java.util.Objects;

public record BeeHiveBlockTinter() implements BlockTintSource
{
  @Override
  public int color(BlockState blockState)
  {
    return ClientHelper.getHiveTint(blockState.getBlock());
  }

}
