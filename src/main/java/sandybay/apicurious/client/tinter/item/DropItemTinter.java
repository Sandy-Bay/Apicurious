package sandybay.apicurious.client.tinter.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;
import sandybay.apicurious.common.item.DropItem;

public record DropItemTinter(boolean isHighlight) implements ItemTintSource
{
  public static final MapCodec<DropItemTinter> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.BOOL.optionalFieldOf("isHighlight", false).forGetter(DropItemTinter::isHighlight)).apply(instance, DropItemTinter::new));


  @Override
  public int calculate(ItemStack stack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity)
  {
    if (stack.getItem() instanceof DropItem drop)
    {
      return isHighlight() ? ARGB.opaque(drop.getDropHighlight().getIntColor()) : ARGB.opaque(drop.getDropTint().getIntColor());
    }
    return ARGB.opaque(0xFFFFFFFF);
  }

  @Override
  public MapCodec<? extends ItemTintSource> type()
  {
    return MAP_CODEC;
  }
}
