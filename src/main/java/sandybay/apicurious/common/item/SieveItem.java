package sandybay.apicurious.common.item;

import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.enchantment.Enchantment;
import sandybay.apicurious.api.util.ApicuriousTags;

public class SieveItem extends Item
{
  private static final ToolMaterial SIEVE_MATERIAL = new ToolMaterial(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 59, 15.0F, 0.0F, 1, ItemTags.WOODEN_TOOL_MATERIALS);

  public SieveItem(Properties props)
  {
    super(SIEVE_MATERIAL.applyToolProperties(props, ApicuriousTags.BlockTags.HIVE, 1.0f, 1.0f, 0.0f).durability(32));
  }

  @Override
  public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment)
  {
    return false;
  }
}
