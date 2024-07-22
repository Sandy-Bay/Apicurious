package sandybay.apicurious.api.housing;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface ITickingApiary {

  /**
   * Server-side ticking method.
   * This method is used to implement any business-side logic for the ticking BlockEntity.
   *
   * @param level The level of the BlockEntity, this can be null.
   * @param pos The position of the BlockEntity in the world.
   * @param state The state of the BlockState of the Block holding the BlockEntity.
   * @param housingBE The BlockEntity itself.
   */
  void serverTick(Level level, BlockPos pos, BlockState state);

  /**
   * Client-side ticking method.
   * This method is used to implement any presentation-side logic for the ticking BlockEntity.
   *
   * @param level The level of the BlockEntity.
   * @param pos The position of the BlockEntity in the world.
   * @param state The state of the BlockState of the Block holding the BlockEntity.
   * @param housingBE The BlockEntity itself.
   */
  void clientTick(Level level, BlockPos pos, BlockState state);
}
