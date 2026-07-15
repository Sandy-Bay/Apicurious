package sandybay.apicurious.common.bee.genetic.allele;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.allele.AbstractAllele;
import sandybay.apicurious.api.bee.genetic.allele.AlleleType;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.register.AlleleTypeRegistrar;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

import java.util.Objects;

public class Flowers extends AbstractAllele<Flowers>
{

  public static final ResourceKey<IAllele<?>> FLOWERS = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("flowers/normal_flowers"));
  public static final ResourceKey<IAllele<?>> CACTI = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("flowers/cacti"));
  public static final ResourceKey<IAllele<?>> JUNGLE = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("flowers/jungle"));
  public static final ResourceKey<IAllele<?>> SNOW = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("flowers/snow"));
  public static final ResourceKey<IAllele<?>> MUSHROOM = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("flowers/mushroom"));
  public static final ResourceKey<IAllele<?>> WHEAT = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("flowers/wheat"));
  public static final ResourceKey<IAllele<?>> LILY_PAD = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("flowers/lily_pad"));
  public static final ResourceKey<IAllele<?>> STONE = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("flowers/overworld_stone"));
  public static final ResourceKey<IAllele<?>> NETHER_STONE = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("flowers/nether_stone"));
  public static final ResourceKey<IAllele<?>> END_STONE = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("flowers/end_stone"));
  public static final ResourceKey<IAllele<?>> REDSTONE = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("flowers/redstone"));
  public static final ResourceKey<IAllele<?>> DEAD_BUSH = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("flowers/dead_bush"));
  public static final ResourceKey<IAllele<?>> WOOD = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("flowers/wood"));
  public static final ResourceKey<IAllele<?>> SUGAR_CANE = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createIdentifier("flowers/sugar_cane"));

  public static final MapCodec<Flowers> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(TagKey.codec(Registries.BLOCK).fieldOf("flowers").forGetter(Flowers::getFlowers), Codec.BOOL.fieldOf("isDominantTrait").forGetter(Flowers::isDominantTrait), Codec.STRING.fieldOf("name").forGetter(Flowers::getName)).apply(instance, Flowers::new));

  public static final StreamCodec<RegistryFriendlyByteBuf, Flowers> NETWORK_CODEC = StreamCodec.composite(ByteBufCodecs.fromCodec(TagKey.codec(Registries.BLOCK)), Flowers::getFlowers, ByteBufCodecs.BOOL, Flowers::isDominantTrait, ByteBufCodecs.STRING_UTF8, Flowers::getName, Flowers::new);

  private final TagKey<Block> flowers;

  public Flowers(TagKey<Block> flowers, boolean isDominantTrait, String name)
  {
    super(isDominantTrait, name);
    this.flowers = flowers;
  }

  public TagKey<Block> getFlowers()
  {
    return flowers;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (!super.equals(o))
    {
      return false;
    }
    Flowers otherFlowers = (Flowers) o;
    return flowers.equals(otherFlowers.flowers);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(super.hashCode(), flowers);
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
    return AlleleTypeRegistrar.FLOWERS_TYPE.get();
  }
}
