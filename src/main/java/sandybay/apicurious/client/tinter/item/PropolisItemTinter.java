package sandybay.apicurious.client.tinter.item;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;
import sandybay.apicurious.common.item.PropolisItem;

public record PropolisItemTinter() implements ItemTintSource
{
  public static final MapCodec<PropolisItemTinter> MAP_CODEC = MapCodec.unit(new PropolisItemTinter());

  @Override
  public int calculate(ItemStack stack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity)
  {
    if (stack.getItem() instanceof PropolisItem propolisItem)
    {
      return ARGB.opaque(propolisItem.getTint().getIntColor());
    }
    return ARGB.opaque(0xFFFFFFFF);
  }

  @Override
  public MapCodec<? extends ItemTintSource> type()
  {
    return MAP_CODEC;
  }
}
