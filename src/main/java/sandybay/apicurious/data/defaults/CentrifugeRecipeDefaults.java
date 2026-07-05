package sandybay.apicurious.data.defaults;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.recipe.CentrifugeRecipe;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.registrar.ItemRegistrar;

import java.util.function.Consumer;

public class CentrifugeRecipeDefaults
{
  public static void defaults(BootstrapContext<CentrifugeRecipe> bootstrap)
  {
    bootstrap.register(ResourceKey.create(ApicuriousRegistries.CENTRIFUGE_RECIPES, Apicurious.createIdentifier("honey")), CentrifugeRecipe.recipe(Items.HONEYCOMB).withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.9f).withOutput(ItemRegistrar.BEESWAX.item(), 1f).build());
    recipe(bootstrap, ItemRegistrar.COCOA_COMB, builder -> builder.withOutput(ItemRegistrar.BEESWAX.item(), 1f).withOutput(Items.COCOA_BEANS, 0.5f));
    recipe(bootstrap, ItemRegistrar.DRIPPING_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.4f).withOutput(ItemRegistrar.HONEY_DEW.item(), 1f));
    recipe(bootstrap, ItemRegistrar.FROZEN_COMB, builder -> builder.withOutput(ItemRegistrar.BEESWAX.item(), 0.8f).withOutput(Items.SNOWBALL, 0.4f).withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.7f));
    recipe(bootstrap, ItemRegistrar.MELLOW_COMB, builder -> builder.withOutput(ItemRegistrar.BEESWAX.item(), 0.2f).withOutput(Items.QUARTZ, 0.3f).withOutput(ItemRegistrar.HONEY_DEW.item(), 0.6f));
    recipe(bootstrap, ItemRegistrar.MOSSY_COMB, builder -> builder.withOutput(ItemRegistrar.BEESWAX.item(), 1f).withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.9f));
    recipe(bootstrap, ItemRegistrar.MYSTERIOUS_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.4f).withOutput(Items.ENDER_PEARL, 1f));
    recipe(bootstrap, ItemRegistrar.PARCHED_COMB, builder -> builder.withOutput(ItemRegistrar.BEESWAX.item(), 1f).withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.9f));
    recipe(bootstrap, ItemRegistrar.POWDERY_COMB, builder -> builder.withOutput(ItemRegistrar.BEESWAX.item(), 0.2f).withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.2f).withOutput(Items.GUNPOWDER, 0.9f));
    recipe(bootstrap, ItemRegistrar.SILKY_COMB, builder -> builder.withOutput(ItemRegistrar.SILKEN_PROPOLIS.propolis(), 0.8f).withOutput(ItemRegistrar.HONEY_DROP.drop(), 1f));
    recipe(bootstrap, ItemRegistrar.SIMMERING_COMB, builder -> builder.withOutput(ItemRegistrar.REFRACTORY_WAX.item(), 1f).withOutput(ItemRegistrar.PHOSPHOR.item(), 0.7f));
    recipe(bootstrap, ItemRegistrar.STRINGY_COMB, builder -> builder.withOutput(ItemRegistrar.PROPOLIS.propolis(), 1f).withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.4f));
    recipe(bootstrap, ItemRegistrar.WHEATEN_COMB, builder -> builder.withOutput(Items.WHEAT, 0.8f).withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.2f).withOutput(ItemRegistrar.BEESWAX.item(), 0.2f));
    recipe(bootstrap, ItemRegistrar.ROCKY_COMB, builder -> builder.withOutput(ItemRegistrar.BEESWAX.item(), 0.5f).withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.25f));
    recipe(bootstrap, ItemRegistrar.SEEDY_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.9f).withOutput(ItemRegistrar.BEESWAX.item(), 1f));
    recipe(bootstrap, ItemRegistrar.DUSTY_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.25f));
    recipe(bootstrap, ItemRegistrar.DIAMOND_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.25f).withOutput(ItemRegistrar.BEESWAX.item(), 0.5f).withOutput(ItemRegistrar.DIAMOND_NUGGET.item(), 1f));
    recipe(bootstrap, ItemRegistrar.EMERALD_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.25f).withOutput(ItemRegistrar.BEESWAX.item(), 0.5f).withOutput(ItemRegistrar.EMERALD_NUGGET.item(), 1f));
    recipe(bootstrap, ItemRegistrar.COPPER_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.25f).withOutput(ItemRegistrar.BEESWAX.item(), 0.5f).withOutput(ItemRegistrar.COPPER_NUGGET.item(), 1f));
    recipe(bootstrap, ItemRegistrar.IRON_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.25f).withOutput(ItemRegistrar.BEESWAX.item(), 0.5f).withOutput(Items.IRON_NUGGET, 1f));
    recipe(bootstrap, ItemRegistrar.GOLD_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.25f).withOutput(ItemRegistrar.BEESWAX.item(), 0.5f).withOutput(Items.GOLD_NUGGET, 1f));
    recipe(bootstrap, ItemRegistrar.LAPIS_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.25f).withOutput(ItemRegistrar.BEESWAX.item(), 0.5f).withOutput(new ItemStackTemplate(Items.LAPIS_LAZULI, 6), 1f));
    recipe(bootstrap, ItemRegistrar.DAMP_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.9f).withOutput(ItemRegistrar.WATERY_PROPOLIS.propolis(), 1f));
    recipe(bootstrap, ItemRegistrar.ENERGETIC_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.5f).withOutput(ItemRegistrar.BEESWAX.item(), 0.8f).withOutput(new ItemStackTemplate(Items.REDSTONE, 3), 1f));
    recipe(bootstrap, ItemRegistrar.STATIC_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.5f).withOutput(ItemRegistrar.BEESWAX.item(), 0.8f).withOutput(new ItemStackTemplate(Items.REDSTONE, 6), 1f));
    recipe(bootstrap, ItemRegistrar.RED_TINTED_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.8f).withOutput(ItemRegistrar.BEESWAX.item(), 0.6f).withOutput(ItemRegistrar.RED_TINTED_DROP.drop(), 1f));
    recipe(bootstrap, ItemRegistrar.YELLOW_TINTED_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.8f).withOutput(ItemRegistrar.BEESWAX.item(), 0.6f).withOutput(ItemRegistrar.YELLOW_TINTED_DROP.drop(), 1f));
    recipe(bootstrap, ItemRegistrar.BLUE_TINTED_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.8f).withOutput(ItemRegistrar.BEESWAX.item(), 0.6f).withOutput(ItemRegistrar.BLUE_TINTED_DROP.drop(), 1f));
    recipe(bootstrap, ItemRegistrar.GREEN_TINTED_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.8f).withOutput(ItemRegistrar.BEESWAX.item(), 0.6f).withOutput(ItemRegistrar.GREEN_TINTED_DROP.drop(), 1f));
    recipe(bootstrap, ItemRegistrar.BROWN_TINTED_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.8f).withOutput(ItemRegistrar.BEESWAX.item(), 0.6f).withOutput(ItemRegistrar.BROWN_TINTED_DROP.drop(), 1f));
    recipe(bootstrap, ItemRegistrar.WHITE_TINTED_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.8f).withOutput(ItemRegistrar.BEESWAX.item(), 0.6f).withOutput(ItemRegistrar.WHITE_TINTED_DROP.drop(), 1f));
    recipe(bootstrap, ItemRegistrar.BLACK_TINTED_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.8f).withOutput(ItemRegistrar.BEESWAX.item(), 0.6f).withOutput(ItemRegistrar.BLACK_TINTED_DROP.drop(), 1f));
    recipe(bootstrap, ItemRegistrar.ORANGE_TINTED_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.8f).withOutput(ItemRegistrar.BEESWAX.item(), 0.6f).withOutput(ItemRegistrar.ORANGE_TINTED_DROP.drop(), 1f));
    recipe(bootstrap, ItemRegistrar.CYAN_TINTED_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.8f).withOutput(ItemRegistrar.BEESWAX.item(), 0.6f).withOutput(ItemRegistrar.CYAN_TINTED_DROP.drop(), 1f));
    recipe(bootstrap, ItemRegistrar.PURPLE_TINTED_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.8f).withOutput(ItemRegistrar.BEESWAX.item(), 0.6f).withOutput(ItemRegistrar.PURPLE_TINTED_DROP.drop(), 1f));
    recipe(bootstrap, ItemRegistrar.GRAY_TINTED_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.8f).withOutput(ItemRegistrar.BEESWAX.item(), 0.6f).withOutput(ItemRegistrar.GRAY_TINTED_DROP.drop(), 1f));
    recipe(bootstrap, ItemRegistrar.LIGHT_BLUE_TINTED_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.8f).withOutput(ItemRegistrar.BEESWAX.item(), 0.6f).withOutput(ItemRegistrar.LIGHT_BLUE_TINTED_DROP.drop(), 1f));
    recipe(bootstrap, ItemRegistrar.PINK_TINTED_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.8f).withOutput(ItemRegistrar.BEESWAX.item(), 0.6f).withOutput(ItemRegistrar.PINK_TINTED_DROP.drop(), 1f));
    recipe(bootstrap, ItemRegistrar.LIME_TINTED_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.8f).withOutput(ItemRegistrar.BEESWAX.item(), 0.6f).withOutput(ItemRegistrar.LIME_TINTED_DROP.drop(), 1f));
    recipe(bootstrap, ItemRegistrar.LIGHT_GRAY_TINTED_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.8f).withOutput(ItemRegistrar.BEESWAX.item(), 0.6f).withOutput(ItemRegistrar.LIGHT_GRAY_TINTED_DROP.drop(), 1f));
    recipe(bootstrap, ItemRegistrar.MAGENTA_TINTED_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.8f).withOutput(ItemRegistrar.BEESWAX.item(), 0.6f).withOutput(ItemRegistrar.MAGENTA_TINTED_DROP.drop(), 1f));
    recipe(bootstrap, ItemRegistrar.UNSTABLE_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.25f).withOutput(ItemRegistrar.SALTPETER.item(), 1f));
    recipe(bootstrap, ItemRegistrar.CLAY_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.8f).withOutput(Items.CLAY_BALL, 0.8f).withOutput(ItemRegistrar.BEESWAX.item(), 0.25f));
    recipe(bootstrap, ItemRegistrar.BARREN_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.5f).withOutput(ItemRegistrar.BEESWAX.item(), 1f));
    recipe(bootstrap, ItemRegistrar.DECOMPOSED_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.25f).withOutput(new ItemStackTemplate(Items.BONE_MEAL, 3), 1f));
    recipe(bootstrap, ItemRegistrar.ANCIENT_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.9f).withOutput(ItemRegistrar.BEESWAX.item(), 1f));
    recipe(bootstrap, ItemRegistrar.FOSSILISED_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.75f).withOutput(ItemRegistrar.BEESWAX.item(), 0.8f).withOutput(new ItemStackTemplate(Items.COAL, 2), 0.5f));
    recipe(bootstrap, ItemRegistrar.GLACIAL_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.75f).withOutput(ItemRegistrar.BEESWAX.item(), 0.8f));
    recipe(bootstrap, ItemRegistrar.FUNGAL_COMB, builder -> builder.withOutput(ItemRegistrar.BEESWAX.item(), 0.9f).withOutput(Items.RED_MUSHROOM, 0.75f).withOutput(Items.BROWN_MUSHROOM, 1f));
    recipe(bootstrap, ItemRegistrar.BLAZING_COMB, builder -> builder.withOutput(ItemRegistrar.BEESWAX.item(), 0.75f).withOutput(new ItemStackTemplate(Items.BLAZE_POWDER, 3), 1f));
    recipe(bootstrap, ItemRegistrar.GLOWING_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.25f).withOutput(new ItemStackTemplate(Items.GLOWSTONE_DUST, 3), 1f));
    recipe(bootstrap, ItemRegistrar.VENOMOUS_COMB, builder -> builder.withOutput(ItemRegistrar.BEESWAX.item(), 0.2f).withOutput(ItemRegistrar.HONEY_DROP.drop(), 1f));
    recipe(bootstrap, ItemRegistrar.BRIMSTONE_COMB, builder -> builder.withOutput(ItemRegistrar.BEESWAX.item(), 0.8f).withOutput(ItemRegistrar.ACIDIC_DROP.drop(), 0.5f).withOutput(ItemRegistrar.SULFUR.item(), 0.75f));
    recipe(bootstrap, ItemRegistrar.MUCOUS_COMB, builder -> builder.withOutput(ItemRegistrar.HONEY_DROP.drop(), 0.75f).withOutput(Items.SLIME_BALL, 0.75f).withOutput(ItemRegistrar.BEESWAX.item(), 1f));
  }

  public static void recipe(BootstrapContext<CentrifugeRecipe> bootstrap, ItemRegistrar.CombHolder holder, Consumer<CentrifugeRecipe.Builder> consumer)
  {
    CentrifugeRecipe.Builder builder = CentrifugeRecipe.recipe(holder.comb());
    consumer.accept(builder);
    bootstrap.register(holder.recipe(), builder.build());
  }
}
