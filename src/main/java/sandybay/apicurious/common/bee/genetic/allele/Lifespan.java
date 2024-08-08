package sandybay.apicurious.common.bee.genetic.allele;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.AlleleType;
import sandybay.apicurious.api.bee.genetic.IAllele;
import sandybay.apicurious.api.register.AlleleTypeRegistration;
import sandybay.apicurious.api.registry.ApicuriousRegistries;

import java.util.Objects;

public class Lifespan implements IAllele<Lifespan>
{

  public static final ResourceKey<IAllele<?>> SHOREST = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createResourceLocation("lifespan/shorest"));
  public static final ResourceKey<IAllele<?>> SHORTER = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createResourceLocation("lifespan/shorter"));
  public static final ResourceKey<IAllele<?>> SHORT = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createResourceLocation("lifespan/short"));
  public static final ResourceKey<IAllele<?>> SHORTENED = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createResourceLocation("lifespan/shortened"));
  public static final ResourceKey<IAllele<?>> AVERAGE = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createResourceLocation("lifespan/average"));
  public static final ResourceKey<IAllele<?>> ELONGATED = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createResourceLocation("lifespan/elongated"));
  public static final ResourceKey<IAllele<?>> LONG = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createResourceLocation("lifespan/long"));
  public static final ResourceKey<IAllele<?>> LONGER = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createResourceLocation("lifespan/longer"));
  public static final ResourceKey<IAllele<?>> LONGEST = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createResourceLocation("lifespan/longest"));

  public static final MapCodec<Lifespan> CODEC = RecordCodecBuilder.mapCodec(
          instance -> instance.group(
                  Codec.INT.fieldOf("cycles").forGetter(Lifespan::getCycles),
                  Codec.BOOL.fieldOf("isDominantTrait").forGetter(Lifespan::isDominantTrait),
                  Codec.STRING.fieldOf("name").forGetter(Lifespan::getName)
          ).apply(instance, Lifespan::new)
  );
  public static final StreamCodec<RegistryFriendlyByteBuf, Lifespan> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.INT, Lifespan::getCycles,
          ByteBufCodecs.BOOL, Lifespan::isDominantTrait,
          ByteBufCodecs.STRING_UTF8, Lifespan::getName,
          Lifespan::new
  );

  private final int cycles;
  private final boolean isDominantTrait;
  private final String name;
  private Component readableName;

  public Lifespan(int cycles, boolean isDominantTrait, String name)
  {
    this.cycles = cycles;
    this.isDominantTrait = isDominantTrait;
    this.name = name;
  }

  public int getCycles()
  {
    return cycles;
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
    Lifespan lifespan = (Lifespan) o;
    return cycles == lifespan.cycles && isDominantTrait == lifespan.isDominantTrait && Objects.equals(name, lifespan.name);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(cycles, isDominantTrait, name);
  }

  @Override
  public MapCodec<Lifespan> getCodec()
  {
    return CODEC;
  }

  @Override
  public StreamCodec<RegistryFriendlyByteBuf, Lifespan> getStreamCodec()
  {
    return NETWORK_CODEC;
  }

  @Override
  public AlleleType<Lifespan> getTraitKey()
  {
    return AlleleTypeRegistration.LIFESPAN_TYPE.get();
  }
}
