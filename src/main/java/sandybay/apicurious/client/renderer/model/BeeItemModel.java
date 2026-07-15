package sandybay.apicurious.client.renderer.model;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.item.MissingItemModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4fc;
import org.jspecify.annotations.Nullable;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.bee.IBeeItem;
import sandybay.apicurious.api.register.DataComponentRegistrar;
import sandybay.apicurious.common.bee.genetic.Genome;
import sandybay.apicurious.common.bee.species.BeeSpecies;

import java.util.Locale;

/**
 * Replaces the old BeeItemRenderer (BlockEntityWithoutLevelRenderer).
 * Picks a per-species "item/species/<name>_<type>" client item if one
 * exists, otherwise falls back to "item/species/default_<type>".
 * <p>
 * Registered via RegisterItemModelsEvent and referenced from each bee
 * item's own client item JSON (assets/apicurious/items/drone.json, etc.)
 */
public record BeeItemModel(Identifier fallback) implements ItemModel
{

  @Override
  public void update(ItemStackRenderState state, ItemStack stack, ItemModelResolver resolver,
                     ItemDisplayContext displayContext, @Nullable ClientLevel level, @Nullable ItemOwner owner,
                     int seed)
  {
    if (!(stack.getItem() instanceof IBeeItem beeItem))
    {
      return;
    }

    ModelManager manager = Minecraft.getInstance().getModelManager();
    Identifier modelId = this.fallback;
    Genome genome = stack.get(DataComponentRegistrar.GENOME);
    BeeSpecies species = genome != null ? (BeeSpecies) genome.getSpecies(true).value() : null;

    if (species != null)
    {
      String suffix = switch (beeItem.getBeeType())
      {
        case DRONE -> "drone";
        case PRINCESS -> "princess";
        case QUEEN -> "queen";
        default -> null;
      };

      if (suffix != null)
      {
        Identifier speciesModelId = Apicurious.createIdentifier("species/" + species.getReadableName().getString().toLowerCase(Locale.ROOT) + "_" + suffix);
        if (manager.bakedItemStackModels.containsKey(speciesModelId))
        {
          modelId = speciesModelId;
        }
      }

      ItemModel resolved = manager.getItemModel(modelId);
      if (!(resolved instanceof MissingItemModel))
      {
        resolved.update(state, stack, resolver, displayContext, level, owner, seed);
      }
    }
  }

  public record Unbaked(Identifier fallback) implements ItemModel.Unbaked
  {
    public static final MapCodec<Unbaked> MAP_CODEC = Identifier.CODEC.fieldOf("fallback").xmap(Unbaked::new, Unbaked::fallback);

    @Override
    public MapCodec<? extends ItemModel.Unbaked> type()
    {
      return MAP_CODEC;
    }

    @Override
    public ItemModel bake(BakingContext bakingContext, Matrix4fc matrix4fc)
    {
      return new BeeItemModel(fallback);
    }

    @Override
    public void resolveDependencies(Resolver resolver)
    {
    }
  }
}
