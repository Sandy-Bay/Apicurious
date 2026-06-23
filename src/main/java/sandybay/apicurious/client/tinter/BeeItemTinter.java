package sandybay.apicurious.client.tinter;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;
import sandybay.apicurious.api.register.DataComponentRegistrar;
import sandybay.apicurious.common.bee.genetic.Genome;
import sandybay.apicurious.common.bee.species.BeeSpecies;

public record BeeItemTinter(boolean isOutline, boolean isBody) implements ItemTintSource
{
  public static final MapCodec<BeeItemTinter> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance
          .group(Codec.BOOL.optionalFieldOf("isOutline", false).forGetter(BeeItemTinter::isOutline), Codec.BOOL.optionalFieldOf("isBody", false).forGetter(BeeItemTinter::isBody))
          .apply(instance, BeeItemTinter::new)
  );

  @Override
  public int calculate(ItemStack stack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity)
  {
    return ARGB.opaque(getColor(stack, this.isOutline(), this.isBody()));
  }

  private static int getColor(ItemStack stack, boolean isOutline, boolean isBody)
  {
    Genome genome = stack.get(DataComponentRegistrar.GENOME);
    if (genome == null) {return 0xFFFFFFFF;}
    BeeSpecies species = (BeeSpecies) genome.getSpecies(true).value();
    if (species.getVisualData() == null || species.getVisualData().hasCustomRender()) {return 0xFFFFFFFF;}
    return isOutline ? species.getVisualData().getBeeColor().getOutlineTint().getIntColor() : isBody ? species.getVisualData().getBeeColor().getBodyTint().getIntColor() : species.getVisualData().getBeeColor().getWingTint().getIntColor();
  }

  @Override
  public MapCodec<? extends ItemTintSource> type()
  {
    return MAP_CODEC;
  }
}
