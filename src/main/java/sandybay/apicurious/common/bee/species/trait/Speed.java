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

/**
 * Speed is a trait inherited by Bees which alters the chance of a bee to produce output.
 * The faster the speed, the higher the chance of a bee creating a product per bee cycle update.
 */
public class Speed implements IAllele<Speed>
{

  public static final ResourceKey<Speed> SLOWEST = ResourceKey.create(ApicuriousRegistries.SPEEDS, Apicurious.createResourceLocation("slowest"));
  public static final ResourceKey<Speed> SLOWER = ResourceKey.create(ApicuriousRegistries.SPEEDS, Apicurious.createResourceLocation("slower"));
  public static final ResourceKey<Speed> SLOW = ResourceKey.create(ApicuriousRegistries.SPEEDS, Apicurious.createResourceLocation("slow"));
  public static final ResourceKey<Speed> AVERAGE = ResourceKey.create(ApicuriousRegistries.SPEEDS, Apicurious.createResourceLocation("average"));
  public static final ResourceKey<Speed> FAST = ResourceKey.create(ApicuriousRegistries.SPEEDS, Apicurious.createResourceLocation("fast"));
  public static final ResourceKey<Speed> FASTER = ResourceKey.create(ApicuriousRegistries.SPEEDS, Apicurious.createResourceLocation("faster"));
  public static final ResourceKey<Speed> FASTEST = ResourceKey.create(ApicuriousRegistries.SPEEDS, Apicurious.createResourceLocation("fastest"));


  public static final MapCodec<Speed> CODEC = RecordCodecBuilder.mapCodec(
          instance -> instance.group(
                  Codec.FLOAT.fieldOf("productionModifier").forGetter(Speed::getProductionModifier),
                  Codec.BOOL.fieldOf("isDominantTrait").forGetter(Speed::isDominantTrait),
                  Codec.STRING.fieldOf("name").forGetter(Speed::getName)
          ).apply(instance, Speed::new)
  );
  public static final StreamCodec<RegistryFriendlyByteBuf, Speed> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.FLOAT, Speed::getProductionModifier,
          ByteBufCodecs.BOOL, Speed::isDominantTrait,
          ByteBufCodecs.STRING_UTF8, Speed::getName,
          Speed::new
  );

  private final float productionModifier;
  private final boolean isDominantTrait;
  private final String name;
  private Component readableName;

  public Speed(float productionModifier, boolean isDominantTrait, String name)
  {
    this.productionModifier = productionModifier;
    this.isDominantTrait = isDominantTrait;
    this.name = name;
  }

  public float getProductionModifier()
  {
    return productionModifier;
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
    Speed speed = (Speed) o;
    return Float.compare(productionModifier, speed.productionModifier) == 0 && isDominantTrait == speed.isDominantTrait && Objects.equals(name, speed.name);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(productionModifier, isDominantTrait, name);
  }

  @Override
  public MapCodec<Speed> getCodec()
  {
    return CODEC;
  }

  @Override
  public StreamCodec<RegistryFriendlyByteBuf, Speed> getStreamCodec()
  {
    return NETWORK_CODEC;
  }

  @Override
  public AlleleType<Speed> getTraitKey()
  {
    return AlleleTypeRegistration.SPEED_TYPE.get();
  }
}
