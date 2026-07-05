package sandybay.apicurious.api.util;

import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.client.model.generators.template.ExtendedModelTemplate;
import sandybay.apicurious.Apicurious;

import java.util.Optional;

public class ApicuriousModelTemplates
{
  public static final TextureSlot LAYER3 = TextureSlot.create("layer3");
  public static final TextureSlot LAYER4 = TextureSlot.create("layer4");

  public static final ModelTemplate BEE_TEMPLATE = new ModelTemplate(Optional.of(ModelLocationUtils.decorateItemModelLocation("generated")), Optional.empty(), TextureSlot.LAYER0, TextureSlot.LAYER1, TextureSlot.LAYER2, LAYER3, LAYER4);
  public static final ExtendedModelTemplate HIVE_TEMPLATE = new ModelTemplate(Optional.of(ModelLocationUtils.decorateBlockModelLocation("orientable_with_bottom")), Optional.empty(), TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE, TextureSlot.FRONT, TextureSlot.PARTICLE).extend().element(builder -> builder.from(0f, 0f, 0f).to(16f, 16f, 16f).face(Direction.DOWN, faceBuilder -> faceBuilder.texture(TextureSlot.BOTTOM).tintindex(0).cullface(Direction.DOWN)).face(Direction.UP, faceBuilder -> faceBuilder.texture(TextureSlot.TOP).tintindex(0).cullface(Direction.UP)).face(Direction.NORTH, faceBuilder -> faceBuilder.texture(TextureSlot.FRONT).tintindex(0).cullface(Direction.NORTH)).face(Direction.SOUTH, faceBuilder -> faceBuilder.texture(TextureSlot.SIDE).tintindex(0).cullface(Direction.SOUTH)).face(Direction.EAST, faceBuilder -> faceBuilder.texture(TextureSlot.SIDE).tintindex(0).cullface(Direction.EAST)).face(Direction.WEST, faceBuilder -> faceBuilder.texture(TextureSlot.SIDE).tintindex(0).cullface(Direction.WEST))).build();

  public static final ExtendedModelTemplate HOUSING_TEMPLATE = new ModelTemplate(Optional.of(ModelLocationUtils.decorateBlockModelLocation("cube")), Optional.empty(), TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE, TextureSlot.PARTICLE).extend().element(builder -> builder.face(Direction.DOWN, faceBuilder -> faceBuilder.texture(TextureSlot.BOTTOM)).face(Direction.UP, faceBuilder -> faceBuilder.texture(TextureSlot.TOP)).face(Direction.NORTH, faceBuilder -> faceBuilder.texture(TextureSlot.SIDE)).face(Direction.SOUTH, faceBuilder -> faceBuilder.texture(TextureSlot.SIDE)).face(Direction.EAST, faceBuilder -> faceBuilder.texture(TextureSlot.SIDE)).face(Direction.WEST, faceBuilder -> faceBuilder.texture(TextureSlot.SIDE))).build();

  public static final ModelTemplate APIARY_ITEM_TEMPLATE = new ModelTemplate(Optional.of(Apicurious.createIdentifier("block/apiary")), Optional.empty());

  public static final ModelTemplate BEE_HOUSING_ITEM_TEMPLATE = new ModelTemplate(Optional.of(Apicurious.createIdentifier("block/bee_housing")), Optional.empty());

  public static final ModelTemplate CENTRIFUGE_ITEM_TEMPLATE = new ModelTemplate(Optional.of(Apicurious.createIdentifier("block/centrifuge")), Optional.empty());

  public static TextureMapping createBeeTextures()
  {
    return new TextureMapping().put(TextureSlot.LAYER0, new Material(Apicurious.createIdentifier("item/bee/bee_outline"))).put(TextureSlot.LAYER1, new Material(Apicurious.createIdentifier("item/bee/bee_body"))).put(TextureSlot.LAYER2, new Material(Apicurious.createIdentifier("item/bee/bee_body_stripes"))).put(LAYER3, new Material(Apicurious.createIdentifier("item/bee/bee_wing_fill"))).put(LAYER4, new Material(Apicurious.createIdentifier("item/bee/bee_wing_tint")));
  }

  public static TextureMapping createHiveTextures()
  {
    return new TextureMapping().put(TextureSlot.TOP, new Material(Apicurious.createIdentifier("block/hive/hive_top"))).put(TextureSlot.BOTTOM, new Material(Apicurious.createIdentifier("block/hive/hive_bottom"))).put(TextureSlot.SIDE, new Material(Apicurious.createIdentifier("block/hive/hive_side"))).put(TextureSlot.FRONT, new Material(Apicurious.createIdentifier("block/hive/hive_front"))).put(TextureSlot.PARTICLE, new Material(Apicurious.createIdentifier("block/hive/hive_side")));
  }

  public static TextureMapping createApiaryTextures()
  {
    return new TextureMapping().put(TextureSlot.TOP, new Material(Apicurious.createIdentifier("block/housing/apiary_top"))).put(TextureSlot.BOTTOM, new Material(Apicurious.createIdentifier("block/housing/apiary_bottom"))).put(TextureSlot.SIDE, new Material(Apicurious.createIdentifier("block/housing/apiary_side"))).put(TextureSlot.PARTICLE, new Material(Apicurious.createIdentifier("block/housing/apiary_side")));
  }

  public static TextureMapping createActiveApiaryTextures()
  {
    return new TextureMapping().put(TextureSlot.TOP, new Material(Apicurious.createIdentifier("block/housing/apiary_top"))).put(TextureSlot.BOTTOM, new Material(Apicurious.createIdentifier("block/housing/apiary_bottom"))).put(TextureSlot.SIDE, new Material(Apicurious.createIdentifier("block/housing/apiary_side_active"))).put(TextureSlot.PARTICLE, new Material(Apicurious.createIdentifier("block/housing/apiary_side_active")));
  }

  public static TextureMapping createBeeHousingTextures()
  {
    return new TextureMapping().put(TextureSlot.TOP, new Material(Apicurious.createIdentifier("block/housing/beehouse_top"))).put(TextureSlot.BOTTOM, new Material(Apicurious.createIdentifier("block/housing/beehouse_bottom"))).put(TextureSlot.SIDE, new Material(Apicurious.createIdentifier("block/housing/beehouse_side"))).put(TextureSlot.PARTICLE, new Material(Apicurious.createIdentifier("block/housing/beehouse_side")));
  }

  public static TextureMapping createActiveBeeHousingTextures()
  {
    return new TextureMapping().put(TextureSlot.TOP, new Material(Apicurious.createIdentifier("block/housing/beehouse_top"))).put(TextureSlot.BOTTOM, new Material(Apicurious.createIdentifier("block/housing/beehouse_bottom"))).put(TextureSlot.SIDE, new Material(Apicurious.createIdentifier("block/housing/beehouse_side_active"))).put(TextureSlot.PARTICLE, new Material(Apicurious.createIdentifier("block/housing/beehouse_side_active")));
  }

}
