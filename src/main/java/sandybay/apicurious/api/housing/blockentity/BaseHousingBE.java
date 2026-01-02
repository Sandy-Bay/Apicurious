package sandybay.apicurious.api.housing.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;
import sandybay.apicurious.api.housing.ITicker;

//This does not really need to be in the API, we can just fire events for the users
public abstract class BaseHousingBE extends BlockEntity implements ITicker, IApiaryErrorHandler, MenuProvider
{

  public BaseHousingBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState)
  {
    super(type, pos, blockState);
  }

  // World Save / Read Methods

  @Override
  protected void saveAdditional(ValueOutput output)
  {
    super.saveAdditional(output);
    saveWorldData(output); //false, true
  }

  @Override
  protected void loadAdditional(ValueInput input)
  {
    super.loadAdditional(input);
    readWorldData(input);
  }

  // Data Syncing Methods
  @Override
  public CompoundTag getUpdateTag(HolderLookup.Provider registries)
  {
    CompoundTag tag = super.getUpdateTag(registries);
    saveSyncData(tag, registries); // false, false
    return tag;
  }

  @Override
  public void handleUpdateTag(ValueInput input)
  {
    super.handleUpdateTag(input);
    readSyncData(input); // true, true
  }

  // Data Update Methods
  @Nullable
  @Override
  public Packet<ClientGamePacketListener> getUpdatePacket()
  {
    return ClientboundBlockEntityDataPacket.create(this, (be, reg) ->
    {
      CompoundTag tag = new CompoundTag();
      saveUpdateData(tag, reg);
      return tag;
    });
  }

  @Override
  public void onDataPacket(Connection net, ValueInput valueInput)
  {
    super.onDataPacket(net, valueInput);
    readUpdateData(net, valueInput); // false, false
  }

  // Utility Methods
  public abstract void saveWorldData(ValueOutput output);
  public abstract void readWorldData(ValueInput input);
  public abstract void saveSyncData(CompoundTag tag, HolderLookup.Provider registries);
  public abstract void readSyncData(ValueInput input);
  public abstract void saveUpdateData(CompoundTag tag, HolderLookup.Provider registries);
  public abstract void readUpdateData(Connection net, ValueInput output);

}
