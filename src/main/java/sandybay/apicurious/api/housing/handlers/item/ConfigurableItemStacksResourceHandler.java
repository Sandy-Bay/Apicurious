package sandybay.apicurious.api.housing.handlers.item;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

public class ConfigurableItemStacksResourceHandler extends ItemStacksResourceHandler
{
  private final Map<Integer, Integer> slotAmountFilter;
  private BiPredicate<ItemResource, Integer> insertPredicate;
  private BiPredicate<ItemResource, Integer> extractPredicate;
  private BiConsumer<ItemResource, Integer> onSlotChanged;
  private int slotLimit;

  private boolean hasChanged;

  public ConfigurableItemStacksResourceHandler(int size)
  {
    super(size);
    this.insertPredicate = (stack, integer) -> true;
    this.extractPredicate = (stack, integer) -> true;
    this.onSlotChanged = (stack, integer) -> {};
    this.slotAmountFilter = new HashMap<>();
    this.slotLimit = 64;
  }

  @Override
  public boolean isValid(int index, ItemResource resource)
  {
    return insertPredicate.test(resource, index);
  }

  @Override
  public int extract(int index, ItemResource resource, int amount, TransactionContext transaction)
  {
    return extractPredicate.test(resource, index) ? super.extract(index, resource, amount, transaction) : 0;
  }

  @Override
  protected void onContentsChanged(int index, ItemStack previousContents)
  {
    hasChanged = true;
    onSlotChanged.accept(getResource(index), index);
  }

  @Override
  protected int getCapacity(int index, ItemResource resource)
  {
    return slotAmountFilter.getOrDefault(index, slotLimit);
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
  public ConfigurableItemStacksResourceHandler setInputFilter(BiPredicate<ItemResource, Integer> predicate)
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
  public ConfigurableItemStacksResourceHandler setOutputFilter(BiPredicate<ItemResource, Integer> predicate)
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
  public ConfigurableItemStacksResourceHandler setOnSlotChanged(BiConsumer<ItemResource, Integer> onSlotChanged)
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
  public ConfigurableItemStacksResourceHandler setSlotLimit(int slot, int limit)
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
  public ConfigurableItemStacksResourceHandler setSlotLimit(int limit)
  {
    this.slotLimit = limit;
    return this;
  }
}
