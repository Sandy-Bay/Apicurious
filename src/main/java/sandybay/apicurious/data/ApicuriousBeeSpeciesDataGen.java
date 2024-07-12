package sandybay.apicurious.data;

import com.mojang.serialization.Codec;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.server.packs.PackType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.JsonCodecProvider;
import sandybay.apicurious.api.bee.species.BeeSpecies;

import java.util.concurrent.CompletableFuture;

public class ApicuriousBeeSpeciesDataGen extends JsonCodecProvider<BeeSpecies> {


  /**
   * @param output             {@linkplain PackOutput} provided by the {@link DataGenerator}.
   * @param target
   * @param directory          String representing the directory to generate jsons in, e.g. "dimension" or "cheesemod/cheese".
   * @param packType           PackType specifying whether to generate entries in assets or data.
   * @param codec              Codec to encode values to jsons with using the provided DynamicOps.
   * @param lookupProvider
   * @param modId
   * @param existingFileHelper
   */
  public ApicuriousBeeSpeciesDataGen(PackOutput output, PackOutput.Target target, String directory, PackType packType, Codec<BeeSpecies> codec, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, ExistingFileHelper existingFileHelper) {
    super(output, target, directory, packType, codec, lookupProvider, modId, existingFileHelper);
  }

  @Override
  protected void gather() {

  }
}
