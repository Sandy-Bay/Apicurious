package sandybay.apicurious.client.tinter;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;
import sandybay.apicurious.client.ApicuriousClientEvents;

public record BeeHiveItemTinter() implements ItemTintSource
{
  public static final MapCodec<BeeHiveItemTinter> MAP_CODEC = MapCodec.unit(new BeeHiveItemTinter());

  @Override
  public int calculate(ItemStack stack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity)
  {
    if (stack.getItem() instanceof BlockItem blockItem)
    {
      return ARGB.opaque(ApicuriousClientEvents.getHiveTint(blockItem.getBlock()));
    }
    return ARGB.opaque(0xFFFFFFFF);
  }

  @Override
  public MapCodec<? extends ItemTintSource> type()
  {
    return MAP_CODEC;
  }
}
