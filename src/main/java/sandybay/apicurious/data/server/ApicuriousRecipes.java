package sandybay.apicurious.data.server;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import sandybay.apicurious.common.registrar.BlockRegistrar;
import sandybay.apicurious.common.registrar.ItemRegistrar;

public class ApicuriousRecipes extends RecipeProvider
{

  protected ApicuriousRecipes(HolderLookup.Provider registries, RecipeOutput output)
  {
    super(registries, output);
  }

  @Override
  protected void buildRecipes()
  {
        this.output.includeRootAdvancement();

        // Housings
        this.shaped(RecipeCategory.MISC, BlockRegistrar.APIARY.asItem(), 1)
                .define('S', ItemTags.WOODEN_SLABS)
                .define('P', ItemTags.PLANKS)
                .define('C', Tags.Items.CHESTS_WOODEN)
                .pattern("SSS").pattern("PCP").pattern("PPP")
                .unlockedBy("has_chest", this.has(Tags.Items.CHESTS_WOODEN))
                .save(output);
        this.shaped(RecipeCategory.MISC, BlockRegistrar.BEE_HOUSING.asItem(), 1)
                .define('S', ItemTags.WOODEN_SLABS)
                .define('P', ItemTags.PLANKS)
                .define('F', ItemTags.WOODEN_FENCES)
                .define('C', Tags.Items.CHESTS_WOODEN)
                .pattern("SSS").pattern("FCF").pattern("PPP")
                .unlockedBy("has_chest", this.has(Tags.Items.CHESTS_WOODEN))
                .save(output);

        // Frames
        this.shaped(RecipeCategory.MISC, ItemRegistrar.UNTREATED_FRAME.asItem(), 1)
                .define('S', Items.STICK)
                .define('T', Tags.Items.STRINGS)
                .pattern("SSS").pattern("STS").pattern("SSS")
                .unlockedBy("has_stick", this.has(Items.STICK))
                .save(output);
        this.shapeless(RecipeCategory.MISC, ItemRegistrar.IMPREGNATED_STICK.asItem(), 1)
                .requires(Items.STICK)
                .requires(ItemRegistrar.BEESWAX.item().get())
                .unlockedBy("has_stick", this.has(Items.STICK))
                .save(output);
        this.shaped(RecipeCategory.MISC, ItemRegistrar.IMPREGNATED_FRAME.asItem(), 1)
                .define('S', ItemRegistrar.IMPREGNATED_STICK.asItem())
                .define('T', Tags.Items.STRINGS)
                .pattern("SSS").pattern("STS").pattern("SSS")
                .unlockedBy("has_impregnated_stick", this.has(ItemRegistrar.IMPREGNATED_STICK.asItem()))
                .save(output);
        this.shaped(RecipeCategory.MISC, ItemRegistrar.HEALING_FRAME.asItem(), 1)
                .define('F', ItemRegistrar.IMPREGNATED_FRAME.asItem())
                .define('C', Items.CLAY_BALL)
                .pattern(" C ").pattern("CFC").pattern(" C ")
                .unlockedBy("has_impregnated_frame", this.has(ItemRegistrar.IMPREGNATED_FRAME.asItem()))
                .save(output);
        this.shapeless(RecipeCategory.MISC, ItemRegistrar.SOUL_FRAME.asItem(), 1)
                .requires(ItemRegistrar.IMPREGNATED_FRAME.asItem())
                .requires(Items.SOUL_SAND)
                .unlockedBy("has_impregnated_frame", this.has(ItemRegistrar.IMPREGNATED_FRAME.asItem()))
                .save(output);
        this.shapeless(RecipeCategory.MISC, ItemRegistrar.RESTRAINT_FRAME.asItem(), 1)
                .requires(ItemRegistrar.IMPREGNATED_FRAME.asItem())
                .requires(Tags.Items.BARS_IRON)
                .unlockedBy("has_impregnated_frame", this.has(ItemRegistrar.IMPREGNATED_FRAME.asItem()))
                .save(output);
        this.shapeless(RecipeCategory.MISC, ItemRegistrar.ROYAL_FRAME.asItem(), 1)
                .requires(ItemRegistrar.PROVEN_FRAME.asItem())
                .requires(Tags.Items.NETHER_STARS)
                .unlockedBy("has_proven_frame", this.has(ItemRegistrar.PROVEN_FRAME.asItem()))
                .save(output);

        // Nuggets
        this.shaped(RecipeCategory.MISC, Items.COPPER_INGOT, 1)
                .define('N', ItemRegistrar.COPPER_NUGGET.asItem())
                .pattern("NNN").pattern("NNN").pattern("NNN")
                .unlockedBy("has_copper_ingot", this.has(Tags.Items.INGOTS_COPPER))
                .save(output);
        this.shapeless(RecipeCategory.MISC, ItemRegistrar.COPPER_NUGGET.asItem(), 9)
                .requires(Tags.Items.INGOTS_COPPER)
                .unlockedBy("has_copper_ingot", this.has(Tags.Items.INGOTS_COPPER))
                .save(output);
        this.shaped(RecipeCategory.MISC, Items.DIAMOND, 1)
                .define('N', ItemRegistrar.DIAMOND_NUGGET.asItem())
                .pattern("NNN").pattern("NNN").pattern("NNN")
                .unlockedBy("has_diamond", this.has(Tags.Items.GEMS_DIAMOND))
                .save(output);
        this.shapeless(RecipeCategory.MISC, ItemRegistrar.DIAMOND_NUGGET.asItem(), 9)
                .requires(Tags.Items.GEMS_DIAMOND)
                .unlockedBy("has_diamond", this.has(Tags.Items.GEMS_DIAMOND))
                .save(output);
        this.shaped(RecipeCategory.MISC, Items.EMERALD, 1)
                .define('N', ItemRegistrar.EMERALD_NUGGET.asItem())
                .pattern("NNN").pattern("NNN").pattern("NNN")
                .unlockedBy("has_emerald", this.has(Tags.Items.GEMS_EMERALD))
                .save(output);
        this.shapeless(RecipeCategory.MISC, ItemRegistrar.EMERALD_NUGGET.asItem(), 9)
                .requires(Tags.Items.GEMS_EMERALD)
                .unlockedBy("has_emerald", this.has(Tags.Items.GEMS_EMERALD))
                .save(output);

        // Tools
        this.shaped(RecipeCategory.TOOLS, ItemRegistrar.SIEVE.asItem(), 1)
                .define('S', Items.STICK)
                .define('W', ItemTags.WOOL)
                .pattern("SWS").pattern("SSS").pattern(" S ")
                .unlockedBy("has_wool", this.has(ItemTags.WOOL))
                .save(output);
        this.shaped(RecipeCategory.TOOLS, ItemRegistrar.ANALYZER.asItem(), 1)
                .define('G', Tags.Items.GLASS_PANES)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .pattern("IGI").pattern("IGI").pattern("RDR")
                .unlockedBy("has_diamond", this.has(Tags.Items.GEMS_DIAMOND))
                .save(output);


  }
}
