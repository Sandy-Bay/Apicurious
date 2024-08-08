package sandybay.apicurious.common.bee.genetic.allele;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.AlleleType;
import sandybay.apicurious.api.bee.genetic.IAllele;
import sandybay.apicurious.api.register.AlleleTypeRegistration;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

import java.util.Objects;

public class Flowers implements IAllele<Flowers>
{

  public static final ResourceKey<IAllele<?>> NORMAL_FLOWERS = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createResourceLocation("flowers/normal_flowers"));
  public static final ResourceKey<IAllele<?>> ROCK = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createResourceLocation("flowers/overworld_rocks"));
  public static final ResourceKey<IAllele<?>> NETHER_ROCK = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createResourceLocation("flowers/nether_rocks"));

  public static final MapCodec<Flowers> CODEC = RecordCodecBuilder.mapCodec(
          instance -> instance.group(
                  TagKey.codec(Registries.BLOCK).fieldOf("flowers").forGetter(Flowers::getFlowers),
                  Codec.BOOL.fieldOf("isDominantTrait").forGetter(Flowers::isDominantTrait),
                  Codec.STRING.fieldOf("name").forGetter(Flowers::getName)
          ).apply(instance, Flowers::new)
  );
  public static final StreamCodec<RegistryFriendlyByteBuf, Flowers> NETWORK_CODEC = StreamCodec.composite(
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
  public MapCodec<Flowers> getCodec()
  {
    return CODEC;
  }

  @Override
  public StreamCodec<RegistryFriendlyByteBuf, Flowers> getStreamCodec()
  {
    return NETWORK_CODEC;
  }

  @Override
  public AlleleType<Flowers> getTraitKey()
  {
    return AlleleTypeRegistration.FLOWERS_TYPE.get();
  }
}
