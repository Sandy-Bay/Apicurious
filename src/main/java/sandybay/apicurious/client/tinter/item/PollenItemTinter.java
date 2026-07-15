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
import sandybay.apicurious.common.item.PollenItem;

public record PollenItemTinter(boolean isHighLight) implements ItemTintSource
{
  public static final MapCodec<PollenItemTinter> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.BOOL.optionalFieldOf("isHighlight", false).forGetter(PollenItemTinter::isHighLight)).apply(instance, PollenItemTinter::new));

  @Override
  public int calculate(ItemStack stack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity)
  {
    return stack.getItem() instanceof PollenItem pollenItem ? isHighLight() ? ARGB.opaque(pollenItem.getPollenTint().getIntColor()) : ARGB.opaque(pollenItem.getPollenHighlight().getIntColor()) : ARGB.opaque(0xFFFFFFFF);
  }

  @Override
  public MapCodec<? extends ItemTintSource> type()
  {
    return MAP_CODEC;
  }
}
