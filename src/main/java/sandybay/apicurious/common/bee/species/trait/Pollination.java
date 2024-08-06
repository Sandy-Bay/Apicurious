package sandybay.apicurious.common.bee.species.trait;

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

public class Pollination implements IAllele<Pollination>
{

  public static final ResourceKey<Pollination> SLOWEST = ResourceKey.create(ApicuriousRegistries.POLLINATIONS, Apicurious.createResourceLocation("slowest"));
  public static final ResourceKey<Pollination> SLOWER = ResourceKey.create(ApicuriousRegistries.POLLINATIONS, Apicurious.createResourceLocation("slower"));
  public static final ResourceKey<Pollination> SLOW = ResourceKey.create(ApicuriousRegistries.POLLINATIONS, Apicurious.createResourceLocation("slow"));
  public static final ResourceKey<Pollination> AVERAGE = ResourceKey.create(ApicuriousRegistries.POLLINATIONS, Apicurious.createResourceLocation("average"));
  public static final ResourceKey<Pollination> FAST = ResourceKey.create(ApicuriousRegistries.POLLINATIONS, Apicurious.createResourceLocation("fast"));
  public static final ResourceKey<Pollination> FASTER = ResourceKey.create(ApicuriousRegistries.POLLINATIONS, Apicurious.createResourceLocation("faster"));
  public static final ResourceKey<Pollination> FASTEST = ResourceKey.create(ApicuriousRegistries.POLLINATIONS, Apicurious.createResourceLocation("fastest"));

  public static final MapCodec<Pollination> CODEC = RecordCodecBuilder.mapCodec(
          instance -> instance.group(
                  Codec.FLOAT.fieldOf("pollinationChance").forGetter(Pollination::getPollinationChance),
                  Codec.BOOL.fieldOf("isDominantTrait").forGetter(Pollination::isDominantTrait),
                  Codec.STRING.fieldOf("name").forGetter(Pollination::getName)
          ).apply(instance, Pollination::new)
  );
  public static final StreamCodec<RegistryFriendlyByteBuf, Pollination> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.FLOAT, Pollination::getPollinationChance,
          ByteBufCodecs.BOOL, Pollination::isDominantTrait,
          ByteBufCodecs.STRING_UTF8, Pollination::getName,
          Pollination::new
  );

  private final float pollinationChance;
  private final boolean isDominantTrait;
  private final String name;
  private Component readableName;

  public Pollination(float pollinationChance, boolean isDominantTrait, String name)
  {
    this.pollinationChance = pollinationChance;
    this.isDominantTrait = isDominantTrait;
    this.name = name;
  }

  public float getPollinationChance()
  {
    return pollinationChance;
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
    Pollination that = (Pollination) o;
    return Float.compare(pollinationChance, that.pollinationChance) == 0 && isDominantTrait == that.isDominantTrait && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(pollinationChance, isDominantTrait, name);
  }

  @Override
  public MapCodec<Pollination> getCodec()
  {
    return CODEC;
  }

  @Override
  public StreamCodec<RegistryFriendlyByteBuf, Pollination> getStreamCodec()
  {
    return NETWORK_CODEC;
  }

  @Override
  public AlleleType<Pollination> getTraitKey()
  {
    return AlleleTypeRegistration.POLLINATION_TYPE.get();
  }
}
