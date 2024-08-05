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

/**
 * Speed is a trait inherited by Bees which alters the chance of a bee to produce output.
 * The faster the speed, the higher the chance of a bee creating a product per bee cycle update.
 */
public class Speed implements ITrait<Speed>
{

  public static final ResourceKey<Speed> SLOWEST = ResourceKey.create(ApicuriousRegistries.SPEEDS, Apicurious.createResourceLocation("slowest"));
  public static final ResourceKey<Speed> SLOWER = ResourceKey.create(ApicuriousRegistries.SPEEDS, Apicurious.createResourceLocation("slower"));
  public static final ResourceKey<Speed> SLOW = ResourceKey.create(ApicuriousRegistries.SPEEDS, Apicurious.createResourceLocation("slow"));
  public static final ResourceKey<Speed> AVERAGE = ResourceKey.create(ApicuriousRegistries.SPEEDS, Apicurious.createResourceLocation("average"));
  public static final ResourceKey<Speed> FAST = ResourceKey.create(ApicuriousRegistries.SPEEDS, Apicurious.createResourceLocation("fast"));
  public static final ResourceKey<Speed> FASTER = ResourceKey.create(ApicuriousRegistries.SPEEDS, Apicurious.createResourceLocation("faster"));
  public static final ResourceKey<Speed> FASTEST = ResourceKey.create(ApicuriousRegistries.SPEEDS, Apicurious.createResourceLocation("fastest"));


  public static final Codec<Speed> CODEC = RecordCodecBuilder.create(
          instance -> instance.group(
                  Codec.FLOAT.fieldOf("productionModifier").forGetter(Speed::getProductionModifier),
                  Codec.STRING.fieldOf("name").forGetter(Speed::getName)
          ).apply(instance, Speed::new)
  );
  public static final StreamCodec<ByteBuf, Speed> NETWORK_CODEC = StreamCodec.composite(
          ByteBufCodecs.FLOAT, Speed::getProductionModifier,
          ByteBufCodecs.STRING_UTF8, Speed::getName,
          Speed::new
  );

  private final float productionModifier;
  private final String name;
  private Component readableName;

  public Speed(float productionModifier, String name)
  {
    this.productionModifier = productionModifier;
    this.name = name;
  }

  public float getProductionModifier()
  {
    return productionModifier;
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
    return Float.compare(productionModifier, speed.productionModifier) == 0 && Objects.equals(name, speed.name) && Objects.equals(readableName, speed.readableName);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(productionModifier, name, readableName);
  }

  @Override
  public Codec<Speed> getCodec()
  {
    return CODEC;
  }

  @Override
  public StreamCodec<ByteBuf, Speed> getStreamCodec()
  {
    return NETWORK_CODEC;
  }

  @Override
  public ResourceLocation getTraitKey()
  {
    return ApicuriousConstants.SPEED;
  }
}
