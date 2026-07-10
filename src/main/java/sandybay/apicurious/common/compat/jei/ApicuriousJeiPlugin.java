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
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

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
        registry.registerSubtypeInterpreter(ItemRegistrar.QUEEN.item().get(), new ISubtypeInterpreter<>()
        {
          @Override
          public @Nullable Object getSubtypeData(ItemStack stack, UidContext context)
          {
            stack.set(DataComponentRegistrar.IDENTIFIED, false);
            if (stack.isEmpty() || !stack.has(DataComponentRegistrar.GENOME)) return null;
            return stack.get(DataComponentRegistrar.GENOME);
          }
        });
  }

  @Override
  public void registerGuiHandlers(IGuiHandlerRegistration registry)
  {
        registry.addGuiContainerHandler(CentrifugeScreen.class, new JEICentrifugeContainerHandler());
        registry.addGuiContainerHandler(ApiaryScreen.class, new JEIHousingContainerHandler.JEIApiaryContainerHandler());
        registry.addGuiContainerHandler(BeeHousingScreen.class, new JEIHousingContainerHandler.JEIBeeHousingContainerHandler());
  }

  @Override
  public void registerCategories(@NotNull IRecipeCategoryRegistration registry)
  {
        registry.getJeiHelpers().getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, List.of(
                BeeItem.getBeeWithSpecies(Minecraft.getInstance().level, ApicuriousSpecies.DEBUG.species(), ItemRegistrar.QUEEN.item()),
                BeeItem.getBeeWithSpecies(Minecraft.getInstance().level, ApicuriousSpecies.UNDEFINED.species(), ItemRegistrar.QUEEN.item())
        ));
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        registry.addRecipeCategories(new CentrifugeCategory(jeiHelpers.getGuiHelper()));
        registry.addRecipeCategories(new BeeOutputCategory(jeiHelpers.getGuiHelper()));
        if (ApicuriousMainConfig.main_config.shouldJEIMutations.get())
        {
          registry.addRecipeCategories(new BeeMutationCategory(jeiHelpers.getGuiHelper()));
        }
  }

  @Override
  public void registerRecipes(@NotNull IRecipeRegistration registration)
  {
        registration.addRecipes(ApicuriousRecipeTypes.CENTRIFUGE, getCentrifugeRecipes());
        registration.addRecipes(ApicuriousRecipeTypes.BEE_OUTPUTS, getBeeOutputRecipes());
        if (ApicuriousMainConfig.main_config.shouldJEIMutations.get())
        {
          registration.addRecipes(ApicuriousRecipeTypes.BEE_MUTATIONS, getBeeMutationRecipes());
        }
  }

  private List<BeeMutationCategory.Recipe> getBeeMutationRecipes()
  {
    List<BeeMutationCategory.Recipe> recipes = new ArrayList<>();
    ClientLevel level = Minecraft.getInstance().level;
    Registry<IMutation> mutations = level.registryAccess().lookupOrThrow(ApicuriousRegistries.MUTATIONS);
    for (Map.Entry<ResourceKey<IMutation>, IMutation> mutation : mutations.entrySet().stream().toList())
    {
      IMutation imutation = mutation.getValue();
      List<ItemStack> firstOptions = getBeeOptions(imutation.first());
      List<ItemStack> secondOptions = getBeeOptions(imutation.second());
      BeeSpecies outputSpecies = (BeeSpecies) imutation.output().value();
      ItemStack outputBee = BeeItem.getBeeWithSpecies(level, outputSpecies.getSpeciesKey(), ItemRegistrar.DRONE.item());
      if (imutation instanceof ConditionalMutation conditionalMutation)
      {
        List<ICondition> conditions = conditionalMutation.conditions().stream().map(Holder::value).toList();
        recipes.add(new BeeMutationCategory.Recipe(mutation.getKey(), firstOptions, secondOptions, conditionalMutation.chance(), conditions, outputBee));
      }
      else
      {
        recipes.add(new BeeMutationCategory.Recipe(mutation.getKey(), firstOptions, secondOptions, imutation.chance(), List.of(), outputBee));
      }
    }
    return recipes;
  }

  private List<ItemStack> getBeeOptions(HolderSet<? extends IAllele<?>> alleles)
  {
    List<ItemStack> options = new ArrayList<>(alleles.stream().map(Holder::value).map(allele -> (BeeSpecies) allele).map(species -> BeeItem.getBeeWithSpecies(Minecraft.getInstance().level, species.getSpeciesKey(), ItemRegistrar.DRONE.item())).toList());
    Collections.shuffle(options);
    return options;
  }

  private List<CentrifugeCategory.Recipe> getCentrifugeRecipes()
  {
    List<CentrifugeCategory.Recipe> recipes = new ArrayList<>();
    ClientLevel level = Minecraft.getInstance().level;
    if (level == null) return recipes;
    Registry<CentrifugeRecipe> recipeRegistry = level.registryAccess().lookupOrThrow(ApicuriousRegistries.CENTRIFUGE_RECIPES);
    recipeRegistry.entrySet().forEach(recipe ->
    {
      CentrifugeRecipe centrifuge = recipe.getValue();
      recipes.add(new CentrifugeCategory.Recipe(recipe.getKey(), centrifuge.input().create(), centrifuge.duration(), centrifuge.outputs()));
    });
    return recipes;
  }

  private List<BeeOutputCategory.Recipe> getBeeOutputRecipes()
  {
    List<BeeOutputCategory.Recipe> recipes = new ArrayList<>();
    ClientLevel level = Minecraft.getInstance().level;
    if (level == null) return recipes;
    Registry<IAllele<?>> alleles = level.registryAccess().lookupOrThrow(ApicuriousRegistries.ALLELES);
    alleles.entrySet().stream().filter(allele ->
    {
      String path = allele.getKey().identifier().getPath();
      return path.contains("species/") && (!path.contains("undefined") || !path.contains("debug"));
    }).forEach(allele ->
    {
      if (allele.getValue() instanceof BeeSpecies species)
      {
        ItemStack input = BeeItem.getBeeWithSpecies(level, species.getSpeciesKey(), ItemRegistrar.DRONE.item());
        OutputTable output = species.getOutputData().outputTable().value();
        recipes.add(new BeeOutputCategory.Recipe(allele.getKey(), input, output));
      }
    });
    return recipes;
  }


}
