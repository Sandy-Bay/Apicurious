package sandybay.apicurious.api.util;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public record ApicuriousJsonCodecProvider<T>(PackOutput packOutput, DataGenerator generator, PackOutput.Target target, String folder, Codec<T> codec, Map<ResourceLocation,T> objects) implements DataProvider
{
  private static final Logger LOGGER = LogUtils.getLogger();

  @Override
  public CompletableFuture<?> run(CachedOutput cache)
  {
    PackOutput.PathProvider pathProvider = packOutput.createPathProvider(target, folder);
    List<CompletableFuture<?>> results = new ArrayList<>();
    for (var entry : this.objects.entrySet())
    {
      var id = entry.getKey();
      this.codec.encodeStart(JsonOps.INSTANCE, entry.getValue())
              .resultOrPartial(s -> LOGGER.error("{} failed to encode {}: {}", this.getName(), id, s))
              .ifPresent(json -> {
                results.add(DataProvider.saveStable(cache, json, pathProvider.json(id)));
              });
    }
    return CompletableFuture.allOf(results.toArray(CompletableFuture[]::new));
  }

  /**
   * Gets the name of this data provider. Used by the data generator to log its root data providers.
   */
  @Override
  public String getName()
  {
    return String.format("%s", this.folder);
  }

}
