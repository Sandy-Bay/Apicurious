//package sandybay.apicurious.common.old.traits;
//
//import com.mojang.serialization.Codec;
//import com.mojang.serialization.codecs.RecordCodecBuilder;
//import io.netty.buffer.ByteBuf;
//import net.minecraft.network.chat.Component;
//import net.minecraft.network.codec.ByteBufCodecs;
//import net.minecraft.network.codec.StreamCodec;
//import net.minecraft.resources.ResourceKey;
//import sandybay.apicurious.Apicurious;
//import sandybay.apicurious.old.traits.ITrait;
//import sandybay.apicurious.api.registry.ApicuriousRegistries;
//
///**
// * Speed is a trait inherited by Bees which alters the chance of a bee to produce output.
// * The faster the speed, the higher the chance of a bee creating a product per bee cycle update.
// */
//public class Speed implements ITrait<Speed> {
//
//  public static void load() {}
//
//  public static final ApicuriousHolder<Speed> SLOWEST = create(-0.7f, "slowest");
//  public static final ApicuriousHolder<Speed> SLOWER = create(-0.4f, "slower");
//  public static final ApicuriousHolder<Speed> SLOW = create(-0.2f, "slow");
//  public static final ApicuriousHolder<Speed> NORMAL = create(0.0f, "normal");
//  public static final ApicuriousHolder<Speed> FAST = create(0.2f, "fast");
//  public static final ApicuriousHolder<Speed> FASTER = create(0.4f, "faster");
//  public static final ApicuriousHolder<Speed> FASTEST = create(0.7f, "fastest");
//
//  private static ApicuriousHolder<Speed> create(float productionModifier, String name) {
//    ResourceKey<Speed> key = ResourceKey.create(ApicuriousRegistries.SPEEDS, Apicurious.createResourceLocation(name));
//    Speed area = new Speed(productionModifier, "apicurious.speed." + name);
//    return new ApicuriousHolder<>(key, area);
//  }
//
//  public static final Codec<Speed> CODEC = RecordCodecBuilder.create(
//          instance -> instance.group(
//                  Codec.FLOAT.fieldOf("productionModifier").forGetter(Speed::getProductionModifier),
//                  Codec.STRING.fieldOf("name").forGetter(Speed::getName)
//          ).apply(instance, Speed::new)
//  );
//  public static final StreamCodec<ByteBuf, Speed> NETWORK_CODEC = StreamCodec.composite(
//          ByteBufCodecs.FLOAT, Speed::getProductionModifier,
//          ByteBufCodecs.STRING_UTF8, Speed::getName,
//          Speed::new
//  );
//
//  private final float productionModifier;
//  private final String name;
//  private Component readableName;
//
//  Speed(float productionModifier, String name) {
//    this.productionModifier = productionModifier;
//    this.name = name;
//  }
//
//  public float getProductionModifier() {
//    return productionModifier;
//  }
//
//  private String getName() {
//    return name;
//  }
//
//  public Component getReadableName() {
//    if (readableName == null) readableName = Component.translatable(this.name);
//    return readableName;
//  }
//
//  @Override
//  public Codec<Speed> getCodec() {
//    return CODEC;
//  }
//
//  @Override
//  public StreamCodec<ByteBuf, Speed> getStreamCodec() {
//    return NETWORK_CODEC;
//  }
//}
