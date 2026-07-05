package sandybay.apicurious.data.client;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.renderer.item.CompositeModel;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.data.PackOutput;
import net.minecraft.util.random.WeightedList;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.api.housing.BaseHousingBlock;
import sandybay.apicurious.api.util.ApicuriousModelTemplates;
import sandybay.apicurious.client.renderer.model.BeeItemModel;
import sandybay.apicurious.client.tinter.item.*;
import sandybay.apicurious.common.registrar.BlockRegistrar;
import sandybay.apicurious.common.registrar.ItemRegistrar;

import java.util.List;
import java.util.Optional;

public class ApicuriousModelProvider extends ModelProvider
{
  public ApicuriousModelProvider(PackOutput output)
  {
    super(output, "apicurious");
  }

  private static void generateMisc(ItemModelGenerators itemModels)
  {
    itemModels.itemModelOutput.accept(ItemRegistrar.SIEVE.item().get(), ItemModelUtils.plainModel(Apicurious.createIdentifier("item/sieve")));
    ModelTemplates.FLAT_ITEM.create(ItemRegistrar.SIEVE.item().get(), TextureMapping.layer0(ItemRegistrar.SIEVE.item().get()), itemModels.modelOutput);
    itemModels.itemModelOutput.accept(ItemRegistrar.ANALYZER.item().get(), ItemModelUtils.plainModel(Apicurious.createIdentifier("item/analyzer")));
    ModelTemplates.FLAT_ITEM.create(ItemRegistrar.ANALYZER.item().get(), TextureMapping.layer0(ItemRegistrar.ANALYZER.item().get()), itemModels.modelOutput);
  }

  private static void generateBees(ItemModelGenerators itemModels)
  {
    // TODO: Implement Models for Drone, Princess and Queen

    // Item Models
    itemModels.itemModelOutput.accept(ItemRegistrar.DRONE.item().get(), new BeeItemModel.Unbaked(Apicurious.createIdentifier("species/default_drone")));
    itemModels.itemModelOutput.accept(ItemRegistrar.PRINCESS.item().get(), new BeeItemModel.Unbaked(Apicurious.createIdentifier("species/default_princess")));
    itemModels.itemModelOutput.accept(ItemRegistrar.QUEEN.item().get(), new BeeItemModel.Unbaked(Apicurious.createIdentifier("species/default_queen")));
    itemModels.itemModelOutput.register(Apicurious.createIdentifier("species/default_drone"), new ClientItem(new CuboidItemModelWrapper.Unbaked(Apicurious.createIdentifier("item/species/default_bee"), Optional.empty(), List.of(new BeeItemTinter(true, false), new BeeItemTinter(false, true), new BeeItemTinter(false, false), new BeeItemTinter(false, false), new BeeItemTinter(false, false))), ClientItem.Properties.DEFAULT));
    itemModels.itemModelOutput.register(Apicurious.createIdentifier("species/default_princess"), new ClientItem(new CompositeModel.Unbaked(List.of(new CuboidItemModelWrapper.Unbaked(Apicurious.createIdentifier("item/species/default_bee"), Optional.empty(), List.of(new BeeItemTinter(true, false), new BeeItemTinter(false, true), new BeeItemTinter(false, false), new BeeItemTinter(false, false), new BeeItemTinter(false, false))), new CuboidItemModelWrapper.Unbaked(Apicurious.createIdentifier("item/species/princess_crown"), Optional.empty(), List.of())), Optional.empty()), ClientItem.Properties.DEFAULT));
    itemModels.itemModelOutput.register(Apicurious.createIdentifier("species/default_queen"), new ClientItem(new CompositeModel.Unbaked(List.of(new CuboidItemModelWrapper.Unbaked(Apicurious.createIdentifier("item/species/default_bee"), Optional.empty(), List.of(new BeeItemTinter(true, false), new BeeItemTinter(false, true), new BeeItemTinter(false, false), new BeeItemTinter(false, false), new BeeItemTinter(false, false))), new CuboidItemModelWrapper.Unbaked(Apicurious.createIdentifier("item/species/queen_crown"), Optional.empty(), List.of())), Optional.empty()), ClientItem.Properties.DEFAULT));
    // Models > Item
    ApicuriousModelTemplates.BEE_TEMPLATE.create(Apicurious.createIdentifier("item/species/default_bee"), ApicuriousModelTemplates.createBeeTextures(), itemModels.modelOutput);
    ModelTemplates.FLAT_ITEM.create(Apicurious.createIdentifier("item/species/princess_crown"), TextureMapping.layer0(new Material(Apicurious.createIdentifier("item/bee/princess_crown"))), itemModels.modelOutput);
    ModelTemplates.FLAT_ITEM.create(Apicurious.createIdentifier("item/species/queen_crown"), TextureMapping.layer0(new Material(Apicurious.createIdentifier("item/bee/queen_crown"))), itemModels.modelOutput);
  }

  private static void generateFrames(ItemModelGenerators itemModels)
  {
    // TODO: Textures for Creative Frame, Royal Frame
    ItemRegistrar.FRAMES_LIST.forEach(holder -> itemModels.generateFlatItem(holder.get(), ModelTemplates.FLAT_ITEM));
  }

  private static void generateDrops(ItemModelGenerators itemModels)
  {
    ModelTemplates.FLAT_ITEM.create(Apicurious.createIdentifier("item/drop"), TextureMapping.layer0(new Material(Apicurious.createIdentifier("item/drop"))), itemModels.modelOutput);
    ModelTemplates.FLAT_ITEM.create(Apicurious.createIdentifier("item/drop_highlight"), TextureMapping.layer0(new Material(Apicurious.createIdentifier("item/drop_highlight"))), itemModels.modelOutput);
    ItemRegistrar.DROPS_LIST.forEach(holder ->
    {
      itemModels.itemModelOutput.accept(holder.get(), new CompositeModel.Unbaked(List.of(new CuboidItemModelWrapper.Unbaked(Apicurious.createIdentifier("item/drop"), Optional.empty(), List.of(new DropItemTinter(false))), new CuboidItemModelWrapper.Unbaked(Apicurious.createIdentifier("item/drop_highlight"), Optional.empty(), List.of(new DropItemTinter(true)))), Optional.empty()));
    });
  }

  private static void generateCombs(ItemModelGenerators itemModels)
  {
    ModelTemplates.FLAT_ITEM.create(Apicurious.createIdentifier("item/comb_outline"), TextureMapping.layer0(new Material(Apicurious.createIdentifier("item/comb_outline"))), itemModels.modelOutput);
    ModelTemplates.FLAT_ITEM.create(Apicurious.createIdentifier("item/comb_cells"), TextureMapping.layer0(new Material(Apicurious.createIdentifier("item/comb_cells"))), itemModels.modelOutput);
    ItemRegistrar.COMBS_LIST.forEach(holder ->
    {
      itemModels.itemModelOutput.accept(holder.get(), new CompositeModel.Unbaked(List.of(new CuboidItemModelWrapper.Unbaked(Apicurious.createIdentifier("item/comb_outline"), Optional.empty(), List.of(new CombItemTinter(true))), new CuboidItemModelWrapper.Unbaked(Apicurious.createIdentifier("item/comb_cells"), Optional.empty(), List.of(new CombItemTinter(false)))), Optional.empty()));
    });
  }

  private static void generatePollen(ItemModelGenerators itemModels)
  {
    ModelTemplates.FLAT_ITEM.create(Apicurious.createIdentifier("item/pollen_base"), TextureMapping.layer0(new Material(Apicurious.createIdentifier("item/pollen_base"))), itemModels.modelOutput);
    ModelTemplates.FLAT_ITEM.create(Apicurious.createIdentifier("item/pollen_highlight"), TextureMapping.layer0(new Material(Apicurious.createIdentifier("item/pollen_highlight"))), itemModels.modelOutput);

    ItemRegistrar.POLLEN_LIST.forEach(holder ->
    {
      itemModels.itemModelOutput.accept(holder.get(), new CompositeModel.Unbaked(List.of(new CuboidItemModelWrapper.Unbaked(Apicurious.createIdentifier("item/pollen_base"), Optional.empty(), List.of(new PollenItemTinter(false))), new CuboidItemModelWrapper.Unbaked(Apicurious.createIdentifier("item/pollen_highlight"), Optional.empty(), List.of(new PollenItemTinter(true)))), Optional.empty()));
    });
  }

  private static void generatePropolis(ItemModelGenerators itemModels)
  {
    ModelTemplates.FLAT_ITEM.create(Apicurious.createIdentifier("item/propolis"), TextureMapping.layer0(new Material(Apicurious.createIdentifier("item/propolis"))), itemModels.modelOutput);
    ItemRegistrar.PROPOLIS_LIST.forEach(holder ->
    {
      itemModels.itemModelOutput.accept(holder.get(), new CuboidItemModelWrapper.Unbaked(Apicurious.createIdentifier("item/propolis"), Optional.empty(), List.of(new PropolisItemTinter())), ClientItem.Properties.DEFAULT);
    });
  }

  private static void generateProducts(ItemModelGenerators itemModels)
  {
    // TODO: Textures for Saltpeter, Sulfur
    // Models - Item
    // ItemModels
    ItemRegistrar.PRODUCTS_LIST.forEach(holder ->
    {
      itemModels.generateFlatItem(holder.get(), ModelTemplates.FLAT_ITEM);
    });
  }

  private static void generateHives(BlockModelGenerators blockModels, ItemModelGenerators itemModels)
  {
    BlockRegistrar.HIVES.forEach(holder ->
    {
      Variant hive = new Variant(Apicurious.createIdentifier("block/hive"));
      // BlockState
      blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(holder.asBlock(), BlockModelGenerators.variant(hive)).with(BlockModelGenerators.ROTATION_FACING));
      // ItemModel
      itemModels.itemModelOutput.accept(holder.asItem(), ItemModelUtils.tintedModel(Apicurious.createIdentifier("block/hive"), new HiveItemTinter()));
    });
    // Block Model
    ApicuriousModelTemplates.HIVE_TEMPLATE.create(Apicurious.createIdentifier("block/hive"), ApicuriousModelTemplates.createHiveTextures(), blockModels.modelOutput);
  }

  private static void generateMachines(BlockModelGenerators blockModels, ItemModelGenerators itemModels)
  {
    // Apiary
    blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(BlockRegistrar.APIARY.asBlock()).with(BlockModelGenerators.createBooleanModelDispatch(BaseHousingBlock.ACTIVE, BlockModelGenerators.variant(new Variant(Apicurious.createIdentifier("block/apiary_active"))), BlockModelGenerators.variant(new Variant(Apicurious.createIdentifier("block/apiary"))))));
    ApicuriousModelTemplates.HOUSING_TEMPLATE.create(Apicurious.createIdentifier("block/apiary"), ApicuriousModelTemplates.createApiaryTextures(), blockModels.modelOutput);
    ApicuriousModelTemplates.HOUSING_TEMPLATE.create(Apicurious.createIdentifier("block/apiary_active"), ApicuriousModelTemplates.createActiveApiaryTextures(), blockModels.modelOutput);
    itemModels.itemModelOutput.accept(BlockRegistrar.APIARY.asItem(), ItemModelUtils.plainModel(Apicurious.createIdentifier("item/apiary")));
    ApicuriousModelTemplates.APIARY_ITEM_TEMPLATE.create(BlockRegistrar.APIARY.asItem(), new TextureMapping(), itemModels.modelOutput);

    // Bee Housing
    blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(BlockRegistrar.BEE_HOUSING.asBlock()).with(BlockModelGenerators.createBooleanModelDispatch(BaseHousingBlock.ACTIVE, BlockModelGenerators.variant(new Variant(Apicurious.createIdentifier("block/bee_housing_active"))), BlockModelGenerators.variant(new Variant(Apicurious.createIdentifier("block/bee_housing"))))));
    ApicuriousModelTemplates.HOUSING_TEMPLATE.create(Apicurious.createIdentifier("block/bee_housing"), ApicuriousModelTemplates.createBeeHousingTextures(), blockModels.modelOutput);
    ApicuriousModelTemplates.HOUSING_TEMPLATE.create(Apicurious.createIdentifier("block/bee_housing_active"), ApicuriousModelTemplates.createActiveBeeHousingTextures(), blockModels.modelOutput);
    itemModels.itemModelOutput.accept(BlockRegistrar.BEE_HOUSING.asItem(), ItemModelUtils.plainModel(Apicurious.createIdentifier("item/bee_housing")));
    ApicuriousModelTemplates.BEE_HOUSING_ITEM_TEMPLATE.create(BlockRegistrar.BEE_HOUSING.asItem(), new TextureMapping(), itemModels.modelOutput);

    // Centrifuge
    blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(BlockRegistrar.CENTRIFUGE.asBlock(), new MultiVariant(WeightedList.of(new Variant(Apicurious.createIdentifier("block/centrifuge"))))));
    //TODO: Replace with new Centrifuge textures
    ApicuriousModelTemplates.HOUSING_TEMPLATE.create(BlockRegistrar.CENTRIFUGE.asBlock(), ApicuriousModelTemplates.createApiaryTextures(), blockModels.modelOutput);
    itemModels.itemModelOutput.accept(BlockRegistrar.CENTRIFUGE.asItem(), ItemModelUtils.plainModel(Apicurious.createIdentifier("item/centrifuge")));
    ApicuriousModelTemplates.CENTRIFUGE_ITEM_TEMPLATE.create(BlockRegistrar.CENTRIFUGE.asItem(), new TextureMapping(), itemModels.modelOutput);
  }

  @Override
  protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels)
  {
    generateMachines(blockModels, itemModels);
    generateHives(blockModels, itemModels);
    generateProducts(itemModels);
    generateCombs(itemModels);
    generateDrops(itemModels);
    generatePropolis(itemModels);
    generatePollen(itemModels);
    generateFrames(itemModels);
    generateBees(itemModels);
    generateMisc(itemModels);
  }


}
