package sandybay.apicurious.common.registrar;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.client.renderer.particle.BeeParticleOption;

import java.util.function.Function;

public class ParticleTypeRegistrar
{
  private static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, Apicurious.MODID);
  public static final DeferredHolder<ParticleType<?>, ParticleType<BeeParticleOption>> BEE = register("bee", false, BeeParticleOption::codec, BeeParticleOption::streamCodec);

  public static void register(IEventBus bus)
  {
    PARTICLE_TYPES.register(bus);
  }

  private static <T extends ParticleOptions> DeferredHolder<ParticleType<?>, ParticleType<T>> register(String name,
                                                                                                       boolean overrideLimiter,
                                                                                                       Function<ParticleType<T>, MapCodec<T>> codec,
                                                                                                       Function<ParticleType<T>, StreamCodec<? super RegistryFriendlyByteBuf, T>> streamCodec)
  {
    return PARTICLE_TYPES.register(name, () -> new ParticleType<T>(overrideLimiter)
    {
      @Override
      public MapCodec<T> codec()
      {
        return codec.apply(this);
      }

      @Override
      public StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec()
      {
        return streamCodec.apply(this);
      }
    });
  }

}
