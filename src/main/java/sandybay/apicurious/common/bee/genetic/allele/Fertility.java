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

public class Fertility implements IAllele<Fertility>
{

  public static final ResourceKey<IAllele<?>> LOW_FERTILITY = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createResourceLocation("fertility/low"));
  public static final ResourceKey<IAllele<?>> AVERAGE_FERTILITY = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createResourceLocation("fertility/average"));
  public static final ResourceKey<IAllele<?>> HIGH_FERTILITY = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createResourceLocation("fertility/high"));
  public static final ResourceKey<IAllele<?>> MAXIMUM_FERTILITY = ResourceKey.create(ApicuriousRegistries.ALLELES, Apicurious.createResourceLocation("fertility/maximum"));

  public static final MapCodec<Fertility> CODEC = RecordCodecBuilder.mapCodec(
          instance -> instance.group(
                  Codec.INT.fieldOf("offspring").forGetter(Fertility::getOffspring),
                  Codec.BOOL.fieldOf("isDominantTrait").forGetter(Fertility::isDominantTrait),
                  Codec.STRING.fieldOf("name").forGetter(Fertility::getName)
          ).apply(instance, Fertility::new)
  );
  public static final StreamCodec<RegistryFriendlyByteBuf, Fertility> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.INT, Fertility::getOffspring,
          ByteBufCodecs.BOOL, Fertility::isDominantTrait,
          ByteBufCodecs.STRING_UTF8, Fertility::getName,
          Fertility::new
  );

  private final int offspring;
  private final boolean isDominantTrait;
  private final String name;
  public Component readableName;

  public Fertility(int offspring, boolean isDominantTrait, String name)
  {
    this.offspring = offspring;
    this.isDominantTrait = isDominantTrait;
    this.name = name;
  }

  public int getOffspring()
  {
    return offspring;
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
    Fertility fertility = (Fertility) o;
    return offspring == fertility.offspring && isDominantTrait == fertility.isDominantTrait && Objects.equals(name, fertility.name);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(offspring, isDominantTrait, name);
  }

  @Override
  public MapCodec<Fertility> getCodec()
  {
    return CODEC;
  }

  @Override
  public StreamCodec<RegistryFriendlyByteBuf, Fertility> getStreamCodec()
  {
    return NETWORK_CODEC;
  }

  @Override
  public AlleleType<Fertility> getTraitKey()
  {
    return AlleleTypeRegistration.FERTILITY_TYPE.get();
  }
}