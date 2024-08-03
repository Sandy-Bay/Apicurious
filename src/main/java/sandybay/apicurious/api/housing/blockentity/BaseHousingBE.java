package sandybay.apicurious.api.housing.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.tags.TagKey;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.housing.ITickingApiary;
import sandybay.apicurious.api.util.ApicuriousTags;

import java.util.List;

//This does not really need to be in the API, we can just fire events for the users
public abstract class BaseHousingBE extends BlockEntity implements ITickingApiary, IApiaryErrorHandler, MenuProvider
{

  public BaseHousingBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState)
  {
    super(type, pos, blockState);
  }

  //Not sure where to put this I would say the API but it goes here for now
  public static TagKey<Biome> getCurrentHumidity(Level level, BlockPos pos)
  {
    var keys = getOurTags(level, pos);
    //This is lazy and can be optimized later
    for (TagKey<Biome> key : keys)
    {
      if (key.location().toString().contains("humidity"))
        return key;
    }
    return ApicuriousTags.BiomeTags.AVERAGE_HUMIDITY;
  }

  public static TagKey<Biome> getCurrentTemperature(Level level, BlockPos pos)
  {
    var keys = getOurTags(level, pos);
    //This is lazy and can be optimized later
    for (TagKey<Biome> key : keys)
    {
      if (key.location().toString().contains("temperature"))
        return key;
    }
    return ApicuriousTags.BiomeTags.AVERAGE_TEMPERATURE;
  }

  public static List<TagKey<Biome>> getOurTags(Level level, BlockPos pos)
  {
    return level.getBiome(pos).tags().filter(biomeTagKey -> biomeTagKey.location().getNamespace().startsWith(Apicurious.MODID)).toList();
  }

  // World Save / Read Methods
  @Override
  protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
  {
    super.saveAdditional(tag, registries);
    saveData(tag, registries, false, true);
  }

  @Override
  protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
  {
    super.loadAdditional(tag, registries);
    readData(tag, registries, false, true);
  }

  // Data Syncing Methods
  @Override
  public CompoundTag getUpdateTag(HolderLookup.Provider registries)
  {
    CompoundTag tag = super.getUpdateTag(registries);
    saveData(tag, registries, true, true);
    return tag;
  }

  @Override
  public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider registries)
  {
    super.handleUpdateTag(tag, registries);
    readData(tag, registries, true, true);
  }

  // Data Update Methods
  @Nullable
  @Override
  public Packet<ClientGamePacketListener> getUpdatePacket()
  {
    return ClientboundBlockEntityDataPacket.create(this, (be, reg) ->
    {
      CompoundTag tag = new CompoundTag();
      saveData(tag, reg, false, false);
      return tag;
    });
  }

  @Override
  public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider registries)
  {
    readData(pkt.getTag(), registries, false, false);
  }

  // Utility Methods
  public abstract void saveData(CompoundTag tag, HolderLookup.Provider registries, boolean clientOnly, boolean alwaysSave);

  public abstract void readData(CompoundTag tag, HolderLookup.Provider registries, boolean clientOnly, boolean alwaysSave);
}
