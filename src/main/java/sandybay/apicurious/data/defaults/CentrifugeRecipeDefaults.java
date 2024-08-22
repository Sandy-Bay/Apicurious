package sandybay.apicurious.data.defaults;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.recipe.CentrifugeRecipe;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.registrar.ItemRegistrar;

public class CentrifugeRecipeDefaults {
  public static void defaults(BootstrapContext<CentrifugeRecipe> bootstrap)
  {
    bootstrap.register(
            ResourceKey.create(ApicuriousRegistries.CENTRIFUGE_RECIPES, Apicurious.createResourceLocation("test")),
            CentrifugeRecipe.recipe(Items.HONEYCOMB)
                    .withDuration(20)
                    .withOutput(ItemRegistrar.HONEY_DROP, 0.9f)
                    .withOutput(ItemRegistrar.BEESWAX, 1f)
                    .build()
    );
  }
}
