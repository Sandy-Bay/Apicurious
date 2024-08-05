package sandybay.apicurious.common.bee.species.trait;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.ITrait;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.api.util.ApicuriousConstants;

import java.util.Objects;

public class Flowers implements ITrait<Flowers>
{

  public static final ResourceKey<Flowers> NORMAL_FLOWERS = ResourceKey.create(ApicuriousRegistries.FLOWERS, Apicurious.createResourceLocation("normal_flowers"));
  public static final ResourceKey<Flowers> ROCK = ResourceKey.create(ApicuriousRegistries.FLOWERS, Apicurious.createResourceLocation("overworld_rocks"));
  public static final ResourceKey<Flowers> NETHER_ROCK = ResourceKey.create(ApicuriousRegistries.FLOWERS, Apicurious.createResourceLocation("nether_rocks"));

  public static final Codec<Flowers> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  TagKey.codec(Registries.BLOCK).fieldOf("flowers").forGetter(Flowers::getFlowers),
                  Codec.BOOL.fieldOf("isDominantTrait").forGetter(Flowers::isDominantTrait),
                  Codec.STRING.fieldOf("name").forGetter(Flowers::getName)
          ).apply(instance, Flowers::new)
  );
  public static final StreamCodec<ByteBuf, Flowers> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.fromCodec(TagKey.codec(Registries.BLOCK)), Flowers::getFlowers,
          ByteBufCodecs.BOOL, Flowers::isDominantTrait,
          ByteBufCodecs.STRING_UTF8, Flowers::getName,
          Flowers::new
  );

  private final TagKey<Block> flowers;
  private final boolean isDominantTrait;
  private final String name;
  private Component readableName;

  public Flowers(TagKey<Block> flowers, boolean isDominantTrait, String name)
  {
    this.flowers = flowers;
    this.isDominantTrait = isDominantTrait;
    this.name = name;
  }

  public TagKey<Block> getFlowers()
  {
    return flowers;
  }

  @Override
  public boolean isDominantTrait()
  {
    return isDominantTrait;
  }

  private String getName()
  {
    return name;
  }

  @Override
  public Component getReadableName()
  {
    if (readableName == null) readableName = Component.translatable(this.name);
    return readableName;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Flowers flowers = (Flowers) o;
    return Objects.equals(this.flowers, flowers.flowers) && isDominantTrait == flowers.isDominantTrait && Objects.equals(name, flowers.name);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(flowers, isDominantTrait, name);
  }

  @Override
  public Codec<Flowers> getCodec()
  {
    return CODEC;
  }

  @Override
  public StreamCodec<ByteBuf, Flowers> getStreamCodec()
  {
    return NETWORK_CODEC;
  }

  @Override
  public ResourceLocation getTraitKey()
  {
    return ApicuriousConstants.FLOWERS;
  }
}
