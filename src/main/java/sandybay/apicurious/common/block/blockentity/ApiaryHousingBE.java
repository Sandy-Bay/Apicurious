package sandybay.apicurious.common.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.EnumBeeType;
import sandybay.apicurious.api.bee.IBeeItem;
import sandybay.apicurious.api.housing.blockentity.SimpleBlockHousingBE;
import sandybay.apicurious.api.register.ApicuriousDataComponentRegistration;
import sandybay.apicurious.api.util.ApicuriousConstants;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.bee.species.trait.Fertility;
import sandybay.apicurious.common.bee.species.trait.Lifespan;
import sandybay.apicurious.common.block.housing.ApiaryBlock;
import sandybay.apicurious.common.register.ApicuriousBlockRegistration;
import sandybay.apicurious.common.register.ApicuriousItemRegistration;

import java.util.List;
import java.util.function.Predicate;

public class ApiaryHousingBE extends SimpleBlockHousingBE
{

  public ContainerData containerData = new ContainerData()
  {
    @Override
    public int get(int pIndex)
    {
      return switch (pIndex)
      {
        case 0 -> isActive ? 1 : 0;
        case 1 -> currentWork;
        case 2 -> maxWork;
        default -> throw new IllegalArgumentException("Invalid index: " + pIndex);
      };
    }

    @Override
    public void set(int pIndex, int pValue)
    {
      throw new IllegalStateException("Cannot set values through IIntArray");
    }

    @Override
    public int getCount()
    {
      return 3;
    }
  };

  public ApiaryHousingBE(BlockPos pos, BlockState blockState)
  {
    super(ApicuriousBlockRegistration.APIARY.getType(), pos, blockState);
  }

  /**
   * Apiary Workflow: <br>
   * <ul>
   *   <li>Princess and Drone in Slots 0, 1</li>
   *   <ul>
   *     <li>Then perform work for 3.75 sec</li>
   *     <ul>
   *       <li>(75 ticks)</li>
   *     </ul>
   *     <li>Create Queen for Princess/Drone combo and insert into Slot 0</li>
   *   </ul>
   *   <li>Queen in Slot 0</li>
   *   <ul>
   *     <li>Validate Run</li>
   *     <li>If valid for run</li>
   *     <ul>
   *       <li>Cache the lifespan of Queen to a field</li>
   *       <li>Set tick duration value to 550</li>
   *       <li>Decrement tick duration each tick</li>
   *       <li>Every 200 ticks attempt to produce a flower</li>
   *       <li>Once tick duration hits 0</li>
   *       <ul>
   *         <li>Attempt to generate output</li>
   *         <li>Set tick duration to 550 again</li>
   *         <li>Decrement local lifespan field by 1</li>
   *         <li>If Lifespan field reaches 0</li>
   *         <li>Create a new princess adn 1-X drones (Fertility-dependant) with genetics of the queen.</li>
   *       </ul>
   *     </ul>
   *   </ul>
   * </ul>
   */
  @Override
  public void serverTick(Level level, BlockPos pos, BlockState state)
  {
    if (!this.isActive)
    {
      if (isValidForStartup())
      {
        this.currentWork = 75;
        this.maxWork = currentWork;
        return;
      }
      if (this.currentWork != 0)
      {
        this.currentWork--;
        if (this.currentWork == 0)
        {
          ItemStack princess = getInventory().getStackInSlot(0);
          BeeSpecies species = princess.get(ApicuriousDataComponentRegistration.BEE_SPECIES);
          ItemStack queen = new ItemStack(ApicuriousItemRegistration.QUEEN);
          queen.set(ApicuriousDataComponentRegistration.BEE_SPECIES, species);
          getInventory().extractItem(0, 1, false);
          getInventory().extractItem(1, 1, false);
          getInventory().setStackInSlot(0, queen);
          changeActiveState(state, true);
          this.maxWork = 0;
          Apicurious.LOGGER.info("Successfully turned Princess of type %s, into Queen of type %s".formatted(species.getReadableName(), species.getReadableName()));
        }
      }
    } else
    {
      ItemStack stack = getInventory().getStackInSlot(0);
      if (stack.getItem() instanceof IBeeItem bee && bee.getBeeType() == EnumBeeType.QUEEN)
      {
        if (state.getBlock() instanceof ApiaryBlock apiary)
        {
          if (this.territory == null) this.territory = apiary.getTerritory(stack, pos);
          if (stack.has(ApicuriousDataComponentRegistration.BEE_SPECIES) && validation.validate(stack, level, pos, this.territory))
          {
            BeeSpecies species = stack.get(ApicuriousDataComponentRegistration.BEE_SPECIES);
            if (species == null) return;
            if (this.currentWork == 0 && this.maxWork == 0)
            {
              Holder<Lifespan> lifespanHolder = species.getProductionData().getLifespan();
              if (!lifespanHolder.isBound())
                throw new IllegalArgumentException("Lifespan was unbound for species: %s, REPORT THIS!".formatted(species.getReadableName()));
              this.currentWork = 75; //ApicuriousConstants.WORKCYCLE * lifespanHolder.value().getCycles();
              this.maxWork = 75; //this.currentWork;
            }
            this.currentWork--;
            if (Math.abs(this.currentWork - this.maxWork) % 200 == 0 && apiary.shouldPollinate(level.getRandom(), stack))
            {
              Predicate<BlockPos> filter = new LimitedFilter<>(filteredPos -> level.getBlockState(filteredPos).is(BlockTags.DIRT), 7);
              List<BlockPos> found = this.territory.stream().filter(filter).toList();
              for (BlockPos f : found)
              {
                // TODO: Figure out a better way to both find valid blocks and generate random flowers.
                level.setBlock(f.above(), Blocks.POPPY.defaultBlockState(), Block.UPDATE_ALL);
              }
            }
            // TODO: Implement effect occurrences here.
            if (Math.abs(this.currentWork - this.maxWork) % 5 == 0) // Change this back to WorkCycle later
            {
              List<ItemStack> outputs = species.getOutputData().getOutputs();
              for (ItemStack output : outputs) {
                ItemStack out = output;
                for (int i = 5; i < 12; i++)
                {
                  if (getInventory().insertItem(i, out, true) != out) {
                      out = getInventory().insertItem(i, out, false);
                      if (out.isEmpty()) {
                        break;
                      }
                  }
                }
              }
            }
            if (this.currentWork == 0)
            {
              // TODO: Improve the below code
              changeActiveState(state, false);
              this.currentWork = 0;
              this.maxWork = 0;
              this.territory = null;
              getInventory().extractItem(0, 1, false);
              ItemStack princess = new ItemStack(ApicuriousItemRegistration.PRINCESS.get(), 1);
              Fertility fertility = species.getProductionData().getFertility().value();
              ItemStack drones = new ItemStack(ApicuriousItemRegistration.DRONE.get(), fertility.getOffspring());
              princess.set(ApicuriousDataComponentRegistration.BEE_SPECIES, species);
              drones.set(ApicuriousDataComponentRegistration.BEE_SPECIES, species);
              // TODO: Change this so it inserts into any available free slot in the
              for (int i = 5; i < 12; i++)
              {
                if (getInventory().insertItem(i, princess, true) != princess)
                {
                  getInventory().insertItem(i, princess, false);
                  break;
                }
              }
              for (int i = 5; i < 12; i++)
              {
                if (getInventory().insertItem(i, drones, true) != drones)
                {
                  getInventory().insertItem(i, drones, false);
                  break;
                }
              }
              Apicurious.LOGGER.info("Successfully completed full Queen cycle!");
            }
          }
        }
      }
    }
  }

  @Override
  public void clientTick(Level level, BlockPos pos, BlockState state)
  {

  }

  private static class LimitedFilter<T> implements Predicate<T>
  {
    final int limit;
    private final Predicate<T> delegate;
    int matches = 0;

    public LimitedFilter(Predicate<T> delegate, int limit)
    {
      this.delegate = delegate;
      this.limit = limit;
    }

    public boolean test(T toTest)
    {
      if (this.matches > this.limit) return true;
      boolean result = delegate.test(toTest);
      if (result) matches++;
      return result;
    }
  }
}
