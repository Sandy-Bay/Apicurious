package sandybay.apicurious.api.housing.handlers.item;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

public class ConfigurableItemStackHandler extends ItemStackHandler
{

  private final Map<Integer, Integer> slotAmountFilter;
  private BiPredicate<ItemStack, Integer> insertPredicate;
  private BiPredicate<ItemStack, Integer> extractPredicate;
  private BiConsumer<ItemStack, Integer> onSlotChanged;
  private int slotLimit;

  private boolean hasChanged;

  public ConfigurableItemStackHandler(int size)
  {
    super(size);
    this.insertPredicate = (stack, integer) -> true;
    this.extractPredicate = (stack, integer) -> true;
    this.onSlotChanged = (stack, integer) ->
    {
    };
    this.slotAmountFilter = new HashMap<>();
    this.slotLimit = 64;
  }

  // Logic Methods
  @Nonnull
  @Override
  public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
  {
    if (stack.isEmpty())
    {
      return ItemStack.EMPTY;
    }
    validateSlotIndex(slot);
    ItemStack existingStack = this.stacks.get(slot);
    int limit = slotAmountFilter.getOrDefault(slot, slotLimit);
    if (!existingStack.isEmpty())
    {
      if (!ItemStack.isSameItemSameComponents(stack, existingStack))
      {
        System.out.println("new " + stack.getDisplayName().getString() + " " + stack.getComponents());
        System.out.println("existing: " + stack.getDisplayName().getString() + " " + existingStack.getComponents());
        return stack;
      }
      limit -= existingStack.getCount();
    }
    if (limit <= 0)
    {
      return stack;
    }
    boolean reachedLimit = stack.getCount() > limit;
    if (!simulate)
    {
      if (existingStack.isEmpty())
      {
        this.stacks.set(slot, reachedLimit ? stack.copyWithCount(limit) : stack);
      } else
      {
        existingStack.grow(reachedLimit ? limit : stack.getCount());
      }
      onContentsChanged(slot);
    }
    return reachedLimit ? stack.copyWithCount(stack.getCount() - limit) : ItemStack.EMPTY;
  }

  @Nonnull
  @Override
  public ItemStack extractItem(int slot, int amount, boolean simulate)
  {
    if (!extractPredicate.test(getStackInSlot(slot), slot)) return ItemStack.EMPTY;
    return super.extractItem(slot, amount, simulate);
  }

  @Override
  protected void onContentsChanged(int slot)
  {
    hasChanged = true;
    onSlotChanged.accept(getStackInSlot(slot), slot);
  }

  @Override
  public boolean isItemValid(int slot, @Nonnull ItemStack stack)
  {
    return insertPredicate.test(stack, slot);
  }

  // Getters / Setters
  public boolean hasChanged()
  {
    return this.hasChanged;
  }

  /**
   * Sets the predicate input filter to filter what items go into which slot.
   *
   * @param predicate A bi predicate where the itemstack is the item trying to be inserted and the slot where is trying to be inserted to
   * @return itself
   */
  public ConfigurableItemStackHandler setInputFilter(BiPredicate<ItemStack, Integer> predicate)
  {
    this.insertPredicate = predicate;
    return this;
  }

  /**
   * Sets the predicate output filter to filter what can be extracted from which slot.
   *
   * @param predicate A bi predicate where the itemstack is the item trying to be extracted and the slot where is trying to be extracted
   * @return itself
   */
  public ConfigurableItemStackHandler setOutputFilter(BiPredicate<ItemStack, Integer> predicate)
  {
    this.extractPredicate = predicate;
    return this;
  }

  /**
   * Sets the predicate slot changed that gets triggered when a slot is changed.
   *
   * @param onSlotChanged A bi predicate where the itemstack and slot changed
   * @return itself
   */
  public ConfigurableItemStackHandler setOnSlotChanged(BiConsumer<ItemStack, Integer> onSlotChanged)
  {
    this.onSlotChanged = onSlotChanged;
    return this;
  }

  /**
   * Sets the limit amount for a specific slot, this limit has priority instead of the slot limit for all the slots
   *
   * @param slot  The slot to set the limit to
   * @param limit The limit for the slot
   * @return itself
   */
  public ConfigurableItemStackHandler setSlotLimit(int slot, int limit)
  {
    this.slotAmountFilter.put(slot, limit);
    return this;
  }

  /**
   * Sets the default limit for all the slots
   *
   * @param limit The default limit for all the slot that don't have specific limit
   * @return itself
   */
  public ConfigurableItemStackHandler setSlotLimit(int limit)
  {
    this.slotLimit = limit;
    return this;
  }


}
