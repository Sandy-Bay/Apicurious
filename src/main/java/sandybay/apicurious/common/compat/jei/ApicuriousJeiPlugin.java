package sandybay.apicurious.common.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.api.recipe.CentrifugeRecipe;
import sandybay.apicurious.api.register.DataComponentRegistrar;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.client.gui.CentrifugeScreen;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.genetic.Genome;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.compat.jei.category.BeeOutputCategory;
import sandybay.apicurious.common.compat.jei.category.CentrifugeCategory;
import sandybay.apicurious.common.compat.jei.handler.JEICentrifugeContainerHandler;
import sandybay.apicurious.common.item.BeeItem;
import sandybay.apicurious.common.registrar.ItemRegistrar;

import java.util.ArrayList;
import java.util.List;

// Todo: Figure out how to make it so you can press "U" on queens in the jei field to see their outputs.
@JeiPlugin
public class ApicuriousJeiPlugin implements IModPlugin
{

  @Override
  public @NotNull ResourceLocation getPluginUid()
  {
    return Apicurious.createResourceLocation("jei_plugin");
  }

  @Override
  public void registerItemSubtypes(ISubtypeRegistration registry)
  {
    registry.registerSubtypeInterpreter(ItemRegistrar.QUEEN.get(), new ISubtypeInterpreter<>()
    {
      @Override
      public @Nullable Object getSubtypeData(ItemStack stack, UidContext context)
      {
        stack.set(DataComponentRegistrar.IDENTIFIED, false);
        if (stack.isEmpty() || !stack.has(DataComponentRegistrar.GENOME)) return null;
        return stack.get(DataComponentRegistrar.GENOME);
      }

      @Override
      public String getLegacyStringSubtypeInfo(ItemStack stack, UidContext context)
      {
        stack.set(DataComponentRegistrar.IDENTIFIED, false);
        if (stack.isEmpty() || !stack.has(DataComponentRegistrar.GENOME)) {return "";}
        Genome genome = stack.get(DataComponentRegistrar.GENOME);
        if (genome == null) {return "";}
        BeeSpecies species = (BeeSpecies) genome.getSpecies(true).value();
        String path = species.getSpeciesKey().location().getPath();
        return path.replaceAll("species/", "");
      }
    });
  }

  @Override
  public void registerGuiHandlers(IGuiHandlerRegistration registry)
  {
    registry.addGuiContainerHandler(CentrifugeScreen.class, new JEICentrifugeContainerHandler());
  }

  @Override
  public void registerCategories(@NotNull IRecipeCategoryRegistration registry)
  {
    registry.getJeiHelpers().getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, List.of(
            BeeItem.getBeeWithSpecies(Minecraft.getInstance().level, ApicuriousSpecies.DEBUG.species(), ItemRegistrar.QUEEN),
            BeeItem.getBeeWithSpecies(Minecraft.getInstance().level, ApicuriousSpecies.UNDEFINED.species(), ItemRegistrar.QUEEN)
    ));
    IJeiHelpers jeiHelpers = registry.getJeiHelpers();
    registry.addRecipeCategories(new CentrifugeCategory(jeiHelpers.getGuiHelper()));
    registry.addRecipeCategories(new BeeOutputCategory(jeiHelpers.getGuiHelper()));
  }

  @Override
  public void registerRecipes(@NotNull IRecipeRegistration registration)
  {
    registration.addRecipes(ApicuriousRecipeTypes.CENTRIFUGE, getCentrifugeRecipes());
    registration.addRecipes(ApicuriousRecipeTypes.BEE_OUTPUTS, getBeeOutputRecipes());
  }

  private List<CentrifugeCategory.Recipe> getCentrifugeRecipes()
  {
    List<CentrifugeCategory.Recipe> recipes = new ArrayList<>();
    Registry<CentrifugeRecipe> alleles = Minecraft.getInstance().level.registryAccess().registryOrThrow(ApicuriousRegistries.CENTRIFUGE_RECIPES);
    alleles.holders().forEach(recipe ->
    {
      CentrifugeRecipe centrifuge = recipe.value();
      recipes.add(new CentrifugeCategory.Recipe(centrifuge.input(), centrifuge.duration(), centrifuge.outputs()));
    });
    return recipes;
  }

  private List<BeeOutputCategory.Recipe> getBeeOutputRecipes()
  {
    List<BeeOutputCategory.Recipe> recipes = new ArrayList<>();
    Registry<IAllele<?>> alleles = Minecraft.getInstance().level.registryAccess().registryOrThrow(ApicuriousRegistries.ALLELES);
    alleles.holders().filter(allele -> {
      String path = allele.key().location().getPath();
      return path.contains("species/") && (!path.contains("undefined") || !path.contains("debug"));
    }).forEach(allele ->
    {
      BeeSpecies species = (BeeSpecies) allele.value();
      ItemStack input = BeeItem.getBeeWithSpecies(Minecraft.getInstance().level, allele.getKey(), ItemRegistrar.QUEEN);
      OutputTable output = species.getOutputData().outputTable().value();
      recipes.add(new BeeOutputCategory.Recipe(input, output));
    });
    return recipes;
  }


}
