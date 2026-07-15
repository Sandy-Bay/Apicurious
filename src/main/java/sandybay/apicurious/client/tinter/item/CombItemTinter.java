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
import sandybay.apicurious.common.item.CombItem;

public record CombItemTinter(boolean isOutline) implements ItemTintSource
{
  public static final MapCodec<CombItemTinter> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.BOOL.optionalFieldOf("isOutline", false).forGetter(CombItemTinter::isOutline)).apply(instance, CombItemTinter::new));

  @Override
  public int calculate(ItemStack stack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity)
  {
    return stack.getItem() instanceof CombItem comb ? isOutline() ? ARGB.opaque(comb.getOutline().getIntColor()) : ARGB.opaque(comb.getCells().getIntColor()) : ARGB.opaque(0xFFFFFFFF);
  }

  @Override
  public MapCodec<? extends ItemTintSource> type()
  {
    return MAP_CODEC;
  }
}
