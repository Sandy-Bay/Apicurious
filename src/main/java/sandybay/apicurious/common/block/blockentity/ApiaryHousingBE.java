package sandybay.apicurious.common.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import sandybay.apicurious.api.housing.blockentity.SimpleBlockHousingBE;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.register.ApicuriousBlockRegistration;
import sandybay.apicurious.common.register.ApicuriousDataComponentRegistration;
import sandybay.apicurious.common.register.ApicuriousItemRegistration;

public class ApiaryHousingBE extends SimpleBlockHousingBE {

  public ApiaryHousingBE(BlockPos pos, BlockState blockState) {
    super(ApicuriousBlockRegistration.APIARY.getType(), pos, blockState);
  }

  /**
   * Apiary Workflow:
   * - Princess and Drone in Slots 0, 1
   *   - Then perform work for 3.75 sec
   *     - (75 ticks)
   *   - Create Queen for Princess/Drone combo and insert into Slot 0
   * - Queen in Slot 0
   *     - Validate Run
   *     - If valid for run
   *         - Cache lifespan of Queen to a field
   *         - Set tick duration value to 550
   *         - Decrement tick duration each tick
   *         - Every 200 ticks attempt to produce a flower
   *         - Once tick duration hits 0
   *             - Attempt to generate output
   *             - Set tick duration to 550 again
   *             - Decrement local lifespan field by 1
   *             - If Lifespan field reaches 0
   *             - Create a new princess and 1-X drones (Fertility-dependant) with genetics of the queen.
   */
  @Override
  public void serverTick(Level level, BlockPos pos, BlockState state) {
    if (!this.isActive) {
      if (isValidForStartup()) {
        this.work = 75;
        return;
      }
      this.work--;
      if (this.work == 0) {
        ItemStack princess = getInventory().getStackInSlot(0);
        BeeSpecies species = princess.get(ApicuriousDataComponentRegistration.BEE_SPECIES);
        ItemStack queen = new ItemStack(ApicuriousItemRegistration.QUEEN);
        queen.set(ApicuriousDataComponentRegistration.BEE_SPECIES, species);
        getInventory().extractItem(0, 1, false);
        getInventory().extractItem(1, 1, false);
        getInventory().setStackInSlot(0, queen);
        changeActiveState(state, true);
      }
    } else {

    }
  }

  @Override
  public void clientTick(Level level, BlockPos pos, BlockState state) {

  }

}
