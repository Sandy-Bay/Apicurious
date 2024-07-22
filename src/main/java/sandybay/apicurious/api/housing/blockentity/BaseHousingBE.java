package sandybay.apicurious.api.housing.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import sandybay.apicurious.api.housing.ITickingApiary;

public abstract class BaseHousingBE extends BlockEntity implements ITickingApiary {

  public BaseHousingBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
    super(type, pos, blockState);
  }

  // Logic Methods
  @Override
  public void serverTick(Level level, BlockPos pos, BlockState state) {

  }

  @Override
  public void clientTick(Level level, BlockPos pos, BlockState state) {

  }

  // World Save / Read Methods
  @Override
  protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
    super.saveAdditional(tag, registries);
    saveData(tag, registries, false, true);
  }

  @Override
  protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
    super.loadAdditional(tag, registries);
    readData(tag, registries, false, true);
  }

  // Data Syncing Methods
  @Override
  public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
    CompoundTag tag = super.getUpdateTag(registries);
    saveData(tag, registries, true, true);
    return tag;
  }

  @Override
  public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider registries) {
    super.handleUpdateTag(tag, registries);
    readData(tag, registries, true, true);
  }

  // Data Update Methods
  @Nullable
  @Override
  public Packet<ClientGamePacketListener> getUpdatePacket() {
    return ClientboundBlockEntityDataPacket.create(this, (be, reg) -> {
      CompoundTag tag = new CompoundTag();
      saveData(tag, reg, false, false);
      return tag;
    });
  }

  @Override
  public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider registries) {
    readData(pkt.getTag(), registries, false, false);
  }

  // Utility Methods
  public abstract void saveData(CompoundTag tag, HolderLookup.Provider registries, boolean clientOnly, boolean alwaysSave);
  public abstract void readData(CompoundTag tag, HolderLookup.Provider registries, boolean clientOnly, boolean alwaysSave);
}
