package sandybay.apicurious.common.bee.species.trait;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.ITrait;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.api.util.ApicuriousConstants;

import java.util.Objects;

public class Fertility implements ITrait<Fertility>
{

  public static final ResourceKey<Fertility> LOW_FERTILITY = ResourceKey.create(ApicuriousRegistries.FERTILITIES, Apicurious.createResourceLocation("low"));
  public static final ResourceKey<Fertility> AVERAGE_FERTILITY = ResourceKey.create(ApicuriousRegistries.FERTILITIES, Apicurious.createResourceLocation("average"));
  public static final ResourceKey<Fertility> HIGH_FERTILITY = ResourceKey.create(ApicuriousRegistries.FERTILITIES, Apicurious.createResourceLocation("high"));
  public static final ResourceKey<Fertility> MAXIMUM_FERTILITY = ResourceKey.create(ApicuriousRegistries.FERTILITIES, Apicurious.createResourceLocation("maximum"));

  public static final Codec<Fertility> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  Codec.INT.fieldOf("offspring").forGetter(Fertility::getOffspring),
                  Codec.BOOL.fieldOf("isDominantTrait").forGetter(Fertility::isDominantTrait),
                  Codec.STRING.fieldOf("name").forGetter(Fertility::getName)
          ).apply(instance, Fertility::new)
  );
  public static final StreamCodec<ByteBuf, Fertility> NETWORK_CODEC = StreamCodec.composite(
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
  public Codec<Fertility> getCodec()
  {
    return CODEC;
  }

  @Override
  public StreamCodec<ByteBuf, Fertility> getStreamCodec()
  {
    return NETWORK_CODEC;
  }

  @Override
  public ResourceLocation getTraitKey()
  {
    return ApicuriousConstants.FERTILITY;
  }
}
