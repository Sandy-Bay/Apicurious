package sandybay.apicurious.api.register;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.common.bee.genetic.Genome;

public class DataComponentRegistrar
{
  public static final DeferredRegister.DataComponents REGISTRAR = DeferredRegister.createDataComponents(Apicurious.MODID);

  public static final DeferredHolder<DataComponentType<?>, DataComponentType<Genome>> GENOME = REGISTRAR.registerComponentType("genome", builder -> builder.persistent(Genome.CODEC).networkSynchronized(Genome.NETWORK_CODEC));

  public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> IDENTIFIED = REGISTRAR.registerComponentType("identified", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

  public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemContainerContents>> ANALYZER_CONTENTS = REGISTRAR.registerComponentType("analyzer_contents", builder -> builder.persistent(ItemContainerContents.CODEC).networkSynchronized(ItemContainerContents.STREAM_CODEC));

  public static void register(IEventBus bus)
  {
    REGISTRAR.register(bus);
  }

}
