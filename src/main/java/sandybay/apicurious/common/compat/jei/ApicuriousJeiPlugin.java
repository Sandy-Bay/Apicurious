package sandybay.apicurious.common.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.api.recipe.CentrifugeRecipe;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.compat.jei.category.BeeOutputCategory;
import sandybay.apicurious.common.compat.jei.category.CentrifugeCategory;
import sandybay.apicurious.common.item.BeeItem;
import sandybay.apicurious.common.registrar.ItemRegistrar;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class ApicuriousJeiPlugin implements IModPlugin
{

  @Override
  public @NotNull ResourceLocation getPluginUid()
  {
    return Apicurious.createResourceLocation("jei_plugin");
  }

  @Override
  public void registerItemSubtypes(ISubtypeRegistration registration)
  {
    IModPlugin.super.registerItemSubtypes(registration);
  }

  @Override
  public void registerCategories(@NotNull IRecipeCategoryRegistration registry)
  {
    IJeiHelpers jeiHelpers = registry.getJeiHelpers();
    //registry.addRecipeCategories(new BeeOutputCategory(jeiHelpers.getGuiHelper()));
    registry.addRecipeCategories(new CentrifugeCategory(jeiHelpers.getGuiHelper()));
  }

  @Override
  public void registerRecipes(@NotNull IRecipeRegistration registration)
  {
    //registration.addRecipes(ApicuriousRecipeTypes.OUTPUTS, getBeeOutputRecipes());
    registration.addRecipes(ApicuriousRecipeTypes.CENTRIFUGE, getCentrifugeRecipes());
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
    alleles.holders().filter(allele -> allele.getKey().location().getPath().contains("species/")).forEach(allele ->
    {
      BeeSpecies species = (BeeSpecies) allele.value();
      ItemStack input = BeeItem.getBeeWithSpecies(Minecraft.getInstance().level, allele.getKey(), ItemRegistrar.QUEEN);
      OutputTable output = species.getOutputData().outputTable().value();
      recipes.add(new BeeOutputCategory.Recipe(input, output));
    });
    return recipes;
  }


}
