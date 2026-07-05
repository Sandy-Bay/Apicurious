package sandybay.apicurious.client.renderer.particle;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStackTemplate;

public record BeeParticleOption(ParticleType<BeeParticleOption> type, ItemStackTemplate stack, BlockPos homePos, BlockPos flowerPos) implements ParticleOptions
{

  public static MapCodec<BeeParticleOption> codec(ParticleType<BeeParticleOption> type)
  {
    return RecordCodecBuilder.mapCodec(instance -> instance.group(
            ItemStackTemplate.CODEC.fieldOf("stack").forGetter(BeeParticleOption::stack),
            BlockPos.CODEC.fieldOf("homePos").forGetter(BeeParticleOption::homePos),
            BlockPos.CODEC.fieldOf("flowerPos").forGetter(BeeParticleOption::flowerPos)
    ).apply(instance, (stack, homepos, flowerpos) -> new BeeParticleOption(type, stack, homepos, flowerpos)));
  }

  public static StreamCodec<? super RegistryFriendlyByteBuf, BeeParticleOption> streamCodec(ParticleType<BeeParticleOption> type) {
    return StreamCodec.composite(
            ItemStackTemplate.STREAM_CODEC, BeeParticleOption::stack,
            BlockPos.STREAM_CODEC, BeeParticleOption::homePos,
            BlockPos.STREAM_CODEC, BeeParticleOption::flowerPos,
            (stack, homepos, flowerpos) -> new BeeParticleOption(type, stack, homepos, flowerpos)
    );
  }

  @Override
  public ParticleType<?> getType()
  {
    return this.type;
  }
}
