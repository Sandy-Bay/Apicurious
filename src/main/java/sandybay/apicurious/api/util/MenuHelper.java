package sandybay.apicurious.api.util;

import net.minecraft.world.item.ItemStack;

public class MenuHelper
{
  /**
   * Determines if two @link {@link ItemStack} match and can be merged into a single slot
   */
  public static boolean canStacksMerge(ItemStack stack1, ItemStack stack2)
  {
    if (stack1.isEmpty() || stack2.isEmpty()) return false;
    return ItemStack.isSameItemSameComponents(stack1, stack2);
  }
}
