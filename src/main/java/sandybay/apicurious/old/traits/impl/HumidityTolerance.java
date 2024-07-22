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
//public class HumidityTolerance implements ITrait<HumidityTolerance> {
//
//    public static void load() {}
//
//    // TODO: Add proper group tags.
//    public static final ApicuriousHolder<HumidityTolerance> NO_TOLERANCE = create(0, "none");
//    public static final ApicuriousHolder<HumidityTolerance> LOWEST_TOLERANCE = create(1, "lowest");
//    public static final ApicuriousHolder<HumidityTolerance> LOW_TOLERANCE = create(2, "low");
//    public static final ApicuriousHolder<HumidityTolerance> AVERAGE_TOLERANCE = create(3, "average");
//    public static final ApicuriousHolder<HumidityTolerance> HIGH_TOLERANCE = create(4, "high");
//    public static final ApicuriousHolder<HumidityTolerance> MAX_TOLERANCE = create(5, "max");
//
//    private static ApicuriousHolder<HumidityTolerance> create(int toleranceModifier, String name) {
//        ResourceKey<HumidityTolerance> key = ResourceKey.create(ApicuriousRegistries.HUMIDITY_TOLERANCES, Apicurious.createResourceLocation(name));
//        HumidityTolerance area = new HumidityTolerance(toleranceModifier, "apicurious.tolerance.humidity." + name);
//        return new ApicuriousHolder<>(key, area);
//    }
//
//    public static final Codec<HumidityTolerance> CODEC = RecordCodecBuilder.create(
//            instance -> instance.group(
//                    Codec.INT.fieldOf("toleranceModifier").forGetter(HumidityTolerance::getToleranceModifier),
//                    Codec.STRING.fieldOf("humidityTolerance").forGetter(HumidityTolerance::getName)
//            ).apply(instance, HumidityTolerance::new)
//    );
//
//    public static final StreamCodec<ByteBuf, HumidityTolerance> NETWORK_CODEC = StreamCodec.composite(
//            ByteBufCodecs.INT, HumidityTolerance::getToleranceModifier,
//            ByteBufCodecs.STRING_UTF8, HumidityTolerance::getName,
//            HumidityTolerance::new
//    );
//
//    private final int toleranceModifier;
//    private final String name;
//    private Component readableName;
//
//    public HumidityTolerance(int toleranceModifier, String name) {
//        this.toleranceModifier = toleranceModifier;
//        this.name = name;
//    }
//
//    public int getToleranceModifier() {
//        return toleranceModifier;
//    }
//
//    private String getName() {
//        return name;
//    }
//
//    public Component getReadableName() {
//        if (readableName == null) readableName = Component.translatable(this.name);
//        return readableName;
//    }
//
//    @Override
//    public Codec<HumidityTolerance> getCodec() {
//        return CODEC;
//    }
//
//    @Override
//    public StreamCodec<ByteBuf, HumidityTolerance> getStreamCodec() {
//        return NETWORK_CODEC;
//    }
//}
