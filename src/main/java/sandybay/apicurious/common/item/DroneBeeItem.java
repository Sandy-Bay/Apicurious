package sandybay.apicurious.common.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.Connection;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MinecartItem;
import net.minecraft.world.level.Level;
import net.neoforged.fml.loading.FMLLoader;
import sandybay.apicurious.api.item.BaseBeeItem;
import sandybay.apicurious.api.registry.ApicuriousRegistries;
import sandybay.apicurious.common.bee.ApicuriousSpecies;
import sandybay.apicurious.common.bee.species.BeeSpecies;
import sandybay.apicurious.common.datacomponent.ApicuriousDataComponents;

public class DroneBeeItem extends BaseBeeItem {

  public DroneBeeItem(Properties properties) {
    super(properties);
  }

  @Override
  public String getType() {
    return "drone";
  }

  @Override
  public ItemStack getDefaultInstance() {
    return BaseBeeItem.getBeeWithSpecies(null, ApicuriousSpecies.FOREST, ApicuriousItemRegistration.DRONE);
  }
}
