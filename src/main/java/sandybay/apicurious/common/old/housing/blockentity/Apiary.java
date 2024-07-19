package sandybay.apicurious.common.old.housing.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import sandybay.apicurious.api.housing.old.HousingValidation;
import sandybay.apicurious.api.util.ApicuriousConstants;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.datacomponent.ApicuriousDataComponents;

public class Apiary extends BlockEntity {

  private final ItemStackHandler input;
  private final ItemStackHandler frames;
  private final ItemStackHandler output;

  private int workDuration;
  private boolean finishedWork = false;
  private HousingValidation validator;

  private int lifeSpan;

  public Apiary(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
    super(type, pos, blockState);
    this.input = new ItemStackHandler(2);
    this.frames = new ItemStackHandler(3);
    this.output = new ItemStackHandler(7);
    this.validator = new HousingValidation();
  }

  /**
   * Apiary Workflow:
   * - Princess and Drone in Slots 0, 1
   *   - Then perform work for 3.75 sec
   *     - (75 ticks)
   *   - Create Queen for Princess/Drone combo and insert into Slot 0
   * - Queen in Slot 0
   * 	- Validate Run
   * 	- If valid for run
   * 		- Cache lifespan of Queen to a field
   * 		- Set tick duration value to 550
   * 		- Decrement tick duration each tick
   * 		- Every 200 ticks attempt to produce a flower
   * 		- Once tick duration hits 0
   * 			- Attempt to generate output
   * 			- Set tick duration to 550 again
   * 			- Decrement local lifespan field by 1
   * 			- If Lifespan field reaches 0
   * 			- Create a new princess and 1-X drones (Fertility-dependant) with genetics of the queen.
   */
//  public void tick() {
//    // TODO: Implement PrincessBeeItem
//    if (false /*input.getStackInSlot(0).getItem() instanceof PrincessBeeItem && !input.getStackInSlot(1).isEmpty()*/) {
//      if (workDuration == 0) {
//        if (finishedWork) {
//          this.workDuration = 75;
//          finishedWork = false;
//        }
//      } else {
//        workDuration--;
//        if (workDuration == 0) {
//          this.finishedWork = true;
//        }
//      }
//      // TODO: Implement QueenBeeItem
//    } else if (false /*input.getStackInSlot(0).getItem() instanceof QueenBeeItem*/) {
//      // TODO: Change this to genome data not bee species
//      BeeSpecies genome = getGenomeFromInput();
//      Area area = genome.getProductionData().getArea().value();
//      if (isValidEnvironment(genome, area)) {
//        if (lifeSpan == 0) lifeSpan = genome.getProductionData().getLifespan().value().getCycles();
//        if (workDuration == 0) workDuration = ApicuriousConstants.WORKCYCLE;
//        workDuration--;
//        if (shouldPollinate()) {
//          Pollination pollination = genome.getProductionData().getPollination().value();
//
//        }
//        if (workDuration == 0) {
//          handleWorkCompletion();
//        }
//      }
//    }
//  }

  private BeeSpecies getGenomeFromInput() {
    // TODO: Change this to genome data not bee species
    return input.getStackInSlot(0).get(ApicuriousDataComponents.BEE_SPECIES);
  }

//  private boolean isValidEnvironment(BeeSpecies genome, Area area) {
//    return validator.validate(
//            input.getStackInSlot(0),
//            level,
//            worldPosition,
//            BaseHousing.getTerritory(worldPosition, area.getXZOffset(), area.getYOffset())
//    );
//  }

  private boolean shouldPollinate() {
    return (ApicuriousConstants.WORKCYCLE - workDuration) % 200 == 0;
  }

  private void handleWorkCompletion() {
    // TODO: Generate output
    lifeSpan--;
    if (lifeSpan == 0) {
      generateNewPrincessAndDrones();
    }
  }

  private void generateNewPrincessAndDrones() {
    // TODO: Creat new princess + 1-X drones (Fertility-dependant) with genetics of the queen.
  }
}
