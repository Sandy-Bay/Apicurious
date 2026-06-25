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
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.genetic.allele.IAllele;
import sandybay.apicurious.api.bee.genetic.mutation.IMutation;
import sandybay.apicurious.api.bee.output.OutputTable;
import sandybay.apicurious.api.condition.ICondition;
import sandybay.apicurious.api.recipe.CentrifugeRecipe;
import sandybay.apicurious.api.register.DataComponentRegistrar;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.client.gui.ApiaryScreen;
import sandybay.apicurious.client.gui.BeeHousingScreen;
import sandybay.apicurious.client.gui.CentrifugeScreen;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.genetic.Genome;
import sandybay.apicurious.common.bee.genetic.mutation.ConditionalMutation;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.compat.jei.category.BeeMutationCategory;
import sandybay.apicurious.common.compat.jei.category.BeeOutputCategory;
import sandybay.apicurious.common.compat.jei.category.CentrifugeCategory;
import sandybay.apicurious.common.compat.jei.handler.JEICentrifugeContainerHandler;
import sandybay.apicurious.common.compat.jei.handler.JEIHousingContainerHandler;
import sandybay.apicurious.common.config.ApicuriousMainConfig;
import sandybay.apicurious.common.item.BeeItem;
import sandybay.apicurious.common.registrar.ItemRegistrar;

import java.util.*;

@JeiPlugin
public class ApicuriousJeiPlugin implements IModPlugin
{

  @Override
  public @NotNull Identifier getPluginUid()
  {
    return Apicurious.createIdentifier("jei_plugin");
  }

  @Override
  public void registerItemSubtypes(ISubtypeRegistration registry)
  {
//    registry.registerSubtypeInterpreter(ItemRegistrar.QUEEN.item().get(), new ISubtypeInterpreter<>()
//    {
//      @Override
//      public @Nullable Object getSubtypeData(ItemStack stack, UidContext context)
//      {
//        stack.set(DataComponentRegistrar.IDENTIFIED, false);
//        if (stack.isEmpty() || !stack.has(DataComponentRegistrar.GENOME)) return null;
//        return stack.get(DataComponentRegistrar.GENOME);
//      }
//
//      public String getLegacyStringSubtypeInfo(ItemStack stack, UidContext context)
//      {
//        stack.set(DataComponentRegistrar.IDENTIFIED, false);
//        if (stack.isEmpty() || !stack.has(DataComponentRegistrar.GENOME)) {return "";}
//        Genome genome = stack.get(DataComponentRegistrar.GENOME);
//        if (genome == null) {return "";}
//        BeeSpecies species = (BeeSpecies) genome.getSpecies(true).value();
//        String path = species.getSpeciesKey().identifier().getPath();
//        return path.replaceAll("species/", "");
//      }
//    });
  }

  @Override
  public void registerGuiHandlers(IGuiHandlerRegistration registry)
  {
//    registry.addGuiContainerHandler(CentrifugeScreen.class, new JEICentrifugeContainerHandler());
//    if (ApicuriousMainConfig.main_config.shouldJEIMutations.get())
//    {
//      registry.addGuiContainerHandler(ApiaryScreen.class, new JEIHousingContainerHandler.JEIApiaryContainerHandler());
//      registry.addGuiContainerHandler(BeeHousingScreen.class, new JEIHousingContainerHandler.JEIBeeHousingContainerHandler());
//    }
  }

  @Override
  public void registerCategories(@NotNull IRecipeCategoryRegistration registry)
  {
//    registry.getJeiHelpers().getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, List.of(
//            BeeItem.getBeeWithSpecies(Minecraft.getInstance().level, ApicuriousSpecies.DEBUG.species(), ItemRegistrar.QUEEN.item()),
//            BeeItem.getBeeWithSpecies(Minecraft.getInstance().level, ApicuriousSpecies.UNDEFINED.species(), ItemRegistrar.QUEEN.item())
//    ));
//    IJeiHelpers jeiHelpers = registry.getJeiHelpers();
//    registry.addRecipeCategories(new CentrifugeCategory(jeiHelpers.getGuiHelper()));
//    registry.addRecipeCategories(new BeeOutputCategory(jeiHelpers.getGuiHelper()));
//    if (ApicuriousMainConfig.main_config.shouldJEIMutations.get())
//    {
//      registry.addRecipeCategories(new BeeMutationCategory(jeiHelpers.getGuiHelper()));
//    }
  }

  @Override
  public void registerRecipes(@NotNull IRecipeRegistration registration)
  {
//    registration.addRecipes(ApicuriousRecipeTypes.CENTRIFUGE, getCentrifugeRecipes());
//    registration.addRecipes(ApicuriousRecipeTypes.BEE_OUTPUTS, getBeeOutputRecipes());
//    if (ApicuriousMainConfig.main_config.shouldJEIMutations.get())
//    {
//      registration.addRecipes(ApicuriousRecipeTypes.BEE_MUTATIONS, getBeeMutationRecipes());
//    }
  }

  private List<BeeMutationCategory.Recipe> getBeeMutationRecipes() {
    List<BeeMutationCategory.Recipe> recipes = new ArrayList<>();
    Registry<IMutation> mutations = Minecraft.getInstance().level.registryAccess().lookupOrThrow(ApicuriousRegistries.MUTATIONS);
    for (IMutation mutation : mutations.entrySet().stream().map(Map.Entry::getValue).toList()) {
      List<ItemStack> firstOptions = getBeeOptions(mutation.getFirst());
      List<ItemStack> secondOptions = getBeeOptions(mutation.getSecond());
      BeeSpecies outputSpecies = (BeeSpecies) mutation.getOutput().value();
      ItemStack outputBee = BeeItem.getBeeWithSpecies(Minecraft.getInstance().level, outputSpecies.getSpeciesKey(), ItemRegistrar.DRONE.item());
      if (mutation instanceof ConditionalMutation conditionalMutation) {
        List<ICondition> conditions = conditionalMutation.conditions().stream().map(Holder::value).toList();
        recipes.add(new BeeMutationCategory.Recipe(firstOptions, secondOptions, conditionalMutation.chance(), conditions, outputBee));
      } else {
        recipes.add(new BeeMutationCategory.Recipe(firstOptions, secondOptions, mutation.getChance(), List.of(), outputBee));
      }
    }
    return recipes;
  }

  private List<ItemStack> getBeeOptions(HolderSet<? extends IAllele<?>> alleles) {
    List<ItemStack> options = new ArrayList<>(alleles.stream().map(Holder::value).map(allele -> (BeeSpecies) allele).map(species -> BeeItem.getBeeWithSpecies(Minecraft.getInstance().level, species.getSpeciesKey(), ItemRegistrar.DRONE.item())).toList());
    Collections.shuffle(options);
    return options;
  }

  private List<CentrifugeCategory.Recipe> getCentrifugeRecipes()
  {
    List<CentrifugeCategory.Recipe> recipes = new ArrayList<>();
    Registry<CentrifugeRecipe> recipeRegistry = Minecraft.getInstance().level.registryAccess().lookupOrThrow(ApicuriousRegistries.CENTRIFUGE_RECIPES);
    recipeRegistry.entrySet().forEach(recipe ->
    {
      CentrifugeRecipe centrifuge = recipe.getValue();
      recipes.add(new CentrifugeCategory.Recipe(centrifuge.input().create(), centrifuge.duration(), centrifuge.outputs()));
    });
    return recipes;
  }

  private List<BeeOutputCategory.Recipe> getBeeOutputRecipes()
  {
    List<BeeOutputCategory.Recipe> recipes = new ArrayList<>();
    Registry<IAllele<?>> alleles = Minecraft.getInstance().level.registryAccess().lookupOrThrow(ApicuriousRegistries.ALLELES);
    alleles.entrySet().stream().filter(allele -> {
      String path = allele.getKey().identifier().getPath();
      return path.contains("species/") && (!path.contains("undefined") || !path.contains("debug"));
    }).forEach(allele ->
    {
      BeeSpecies species = (BeeSpecies) allele.getValue();
      ItemStack input = BeeItem.getBeeWithSpecies(Minecraft.getInstance().level, allele.getKey(), ItemRegistrar.QUEEN.item());
      OutputTable output = species.getOutputData().outputTable().value();
      recipes.add(new BeeOutputCategory.Recipe(input, output));
    });
    return recipes;
  }


}
