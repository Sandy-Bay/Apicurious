package sandybay.apicurious.api.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.world.level.block.Block;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.block.HiveBlock;

import java.util.Objects;

public class ClientHelper
{
  public static int getHiveTint(Block block)
  {
    ClientPacketListener connection = Minecraft.getInstance().getConnection();
    if (connection != null && block instanceof HiveBlock hiveBlock)
    {
      return connection.registryAccess().lookup(ApicuriousRegistries.ALLELES).map(registryReference -> Objects.requireNonNull(registryReference.getValue((hiveBlock.getSpecies())))).map(allele -> ((BeeSpecies) allele).getVisualData().getBeeColor().getOutlineTint().getIntColor()).orElse(0xFFFFFFFF);
    }
    return 0xFFFFFFFF;
  }
}
