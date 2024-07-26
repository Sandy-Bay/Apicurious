package sandybay.apicurious.common.bee.species.trait;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.ITrait;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

public class Flowers implements ITrait<Flowers> {

    public static final ResourceKey<Flowers> NORMAL_FLOWERS = ResourceKey.create(ApicuriousRegistries.FLOWERS, Apicurious.createResourceLocation("normal_flowers"));
    public static final ResourceKey<Flowers> ROCK = ResourceKey.create(ApicuriousRegistries.FLOWERS, Apicurious.createResourceLocation("overworld_rocks"));
    public static final ResourceKey<Flowers> NETHER_ROCK = ResourceKey.create(ApicuriousRegistries.FLOWERS, Apicurious.createResourceLocation("nether_rocks"));

  public static final Codec<Flowers> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  TagKey.codec(Registries.BLOCK).fieldOf("flowers").forGetter(Flowers::getFlowers),
                  Codec.STRING.fieldOf("name").forGetter(Flowers::getName)
          ).apply(instance, Flowers::new)
  );
  public static final StreamCodec<ByteBuf, Flowers> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.fromCodec(TagKey.codec(Registries.BLOCK)), Flowers::getFlowers,
          ByteBufCodecs.STRING_UTF8, Flowers::getName,
          Flowers::new
  );

  private final TagKey<Block> flowers;
  private final String name;
  private Component readableName;

  public Flowers(TagKey<Block> flowers, String name) {
    this.flowers = flowers;
    this.name = name;
  }

  public TagKey<Block> getFlowers() {
    return flowers;
  }

  private String getName() {
    return name;
  }

  @Override
  public Component getReadableName() {
    if (readableName == null) readableName = Component.translatable(this.name);
    return readableName;
  }

  @Override
  public Codec<Flowers> getCodec() {
    return CODEC;
  }

  @Override
  public StreamCodec<ByteBuf, Flowers> getStreamCodec() {
    return NETWORK_CODEC;
  }
}