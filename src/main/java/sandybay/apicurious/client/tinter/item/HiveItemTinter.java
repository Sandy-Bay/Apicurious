package sandybay.apicurious.client.tinter.item;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;
import sandybay.apicurious.api.util.ClientHelper;

public record HiveItemTinter() implements ItemTintSource
{
  public static final MapCodec<HiveItemTinter> MAP_CODEC = MapCodec.unit(new HiveItemTinter());

  @Override
  public int calculate(ItemStack stack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity)
  {
    if (stack.getItem() instanceof BlockItem blockItem)
    {
      return ARGB.opaque(ClientHelper.getHiveTint(blockItem.getBlock()));
    }
    return ARGB.opaque(0xFFFFFFFF);
  }

  @Override
  public MapCodec<? extends ItemTintSource> type()
  {
    return MAP_CODEC;
  }
}
