package sandybay.apicurious.client.tinter.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import sandybay.apicurious.api.register.DataComponentRegistrar;
import sandybay.apicurious.common.bee.genetic.Genome;
import sandybay.apicurious.common.bee.genetic.allele.groups.VisualData;
import sandybay.apicurious.common.bee.species.BeeColor;
import sandybay.apicurious.common.bee.species.BeeSpecies;

public record BeeItemTinter(boolean isOutline, boolean isBody) implements ItemTintSource
{
  public static final MapCodec<BeeItemTinter> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.BOOL.optionalFieldOf("isOutline", false).forGetter(BeeItemTinter::isOutline), Codec.BOOL.optionalFieldOf("isBody", false).forGetter(BeeItemTinter::isBody)).apply(instance, BeeItemTinter::new));

  @Override
  public int calculate(ItemStack stack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity)
  {
    Genome genome = stack.get(DataComponentRegistrar.GENOME);
    if (genome == null)
    {
      return 0xFFFFFFFF;
    }
    BeeSpecies species = (BeeSpecies) genome.getSpecies(true).value();
    VisualData visualData = species.getVisualData();
    if (visualData == null || visualData.hasCustomRender())
    {
      return 0xFFFFFFFF;
    }
    BeeColor color = visualData.getBeeColor();
    return isOutline() ? color.getOutlineTint().getIntColor() : isBody() ? color.getBodyTint().getIntColor() : color.getWingTint().getIntColor();
  }

  @Override
  public @NonNull MapCodec<? extends ItemTintSource> type()
  {
    return MAP_CODEC;
  }
}
