package sandybay.apicurious.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.MatrixUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.item.BaseBeeItem;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.item.bee.DroneBeeItem;
import sandybay.apicurious.common.item.bee.PrincessBeeItem;
import sandybay.apicurious.common.item.bee.QueenBeeItem;

import java.util.Locale;

public class BeeItemRenderer extends BlockEntityWithoutLevelRenderer {

  public BeeItemRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher, EntityModelSet entityModelSet) {
    super(blockEntityRenderDispatcher, entityModelSet);
  }

  @Override
  public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
    if (stack.getItem() instanceof BaseBeeItem) {
      ModelResourceLocation fallback = null;
      ModelResourceLocation mrl = null;
      BakedModel model;
      BeeSpecies species = BaseBeeItem.getSpecies(stack);
      ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
      ModelManager manager = renderer.getItemModelShaper().getModelManager();
      switch (stack.getItem()) {
        case DroneBeeItem drone -> {
          fallback = ModelResourceLocation.standalone(Apicurious.createResourceLocation("item/species/default_drone"));
          mrl = ModelResourceLocation.standalone(Apicurious.createResourceLocation("item/species/" + species.getReadableName().getString().toLowerCase(Locale.ROOT) + "_drone"));
        }
        case PrincessBeeItem princess -> {
          fallback = ModelResourceLocation.standalone(Apicurious.createResourceLocation("item/species/default_princess"));
          mrl = ModelResourceLocation.standalone(Apicurious.createResourceLocation("item/species/" + species.getReadableName().getString().toLowerCase(Locale.ROOT) + "_princess"));
        }
        case QueenBeeItem queen -> {
          fallback = ModelResourceLocation.standalone(Apicurious.createResourceLocation("item/species/default_queen"));
          mrl = ModelResourceLocation.standalone(Apicurious.createResourceLocation("item/species/" + species.getReadableName().getString().toLowerCase(Locale.ROOT) + "_queen"));
        }
        default -> {}
      }
      if (fallback == null) return;
      model = manager.getModel(mrl);
      if (model == manager.getMissingModel()) {
        model = manager.getModel(fallback);
      }
      if (!stack.isEmpty()) {
        poseStack.popPose();
        poseStack.pushPose();
        //This was not needed in the loop below, and if you scale do it before the translate
        if (displayContext != ItemDisplayContext.GUI) poseStack.scale(0.5F, 0.5F, 0.5F);
        poseStack.translate(-0.5F, -0.5F, -0.5F);

        for (var bakedModel : model.getRenderPasses(stack, false)) {
          for (var rendertype : bakedModel.getRenderTypes(stack, false)) {
            renderer.renderModelLists(bakedModel, stack, packedLight, packedOverlay, poseStack, ItemRenderer.getFoilBuffer(buffer, rendertype, true, stack.hasFoil()));
          }
        }
      }
    }
  }

  private static boolean isLeftHand(ItemDisplayContext type)
  {
    return type == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || type == ItemDisplayContext.THIRD_PERSON_LEFT_HAND;
  }
}
