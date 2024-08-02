package sandybay.apicurious.api.bee.output;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.apache.commons.compress.utils.Lists;
import sandybay.apicurious.api.housing.blockentity.BaseHousingBE;
import sandybay.apicurious.api.housing.blockentity.SimpleBlockHousingBE;

import java.util.Arrays;
import java.util.List;

public class OutputContext {

  private final Level levelContext;
  private final BlockPos positionContext;
  private final SimpleBlockHousingBE housingContext; // TODO: Keep in mind compatibility with multiblock alveary later.
  private final ItemStack queenContext;
  private final List<ItemStack> framesContext;

  OutputContext(Level levelContext, BlockPos positionContext, SimpleBlockHousingBE housingContext, ItemStack queenContext, List<ItemStack> framesContext) {
    this.levelContext = levelContext;
    this.positionContext = positionContext;
    this.housingContext = housingContext;
    this.queenContext = queenContext;
    this.framesContext = framesContext;
  }

  public Level getLevelContext() {
    return levelContext;
  }

  public BlockPos getPositionContext() {
    return positionContext;
  }

  public BaseHousingBE getHousingContext() {
    return housingContext;
  }

  public ItemStack getQueenContext() {
    return queenContext;
  }

  public List<ItemStack> getFramesContext() {
    return framesContext;
  }

  public static OutputContext.Builder context() {
    return new Builder();
  }

  public static OutputContext simpleContext(SimpleBlockHousingBE housing, ItemStack queen, ItemStack... frames) {
    return new Builder(housing.getLevel(), housing.getBlockPos(), housing, queen, frames).build();
  }

  static class Builder {
    private Level level;
    private BlockPos position;
    private SimpleBlockHousingBE housing;
    private ItemStack queen;
    private final List<ItemStack> frames;

    Builder() {
      this.frames = Lists.newArrayList();
    }

    Builder(Level level, BlockPos position, SimpleBlockHousingBE housing, ItemStack queen, ItemStack... frames) {
      this.level = level;
      this.position = position;
      this.housing = housing;
      this.queen = queen;
      this.frames = Arrays.asList(frames);
    }

    public Builder withLevel(Level level) {
      this.level = level;
      return this;
    }

    public Builder withPosition(BlockPos position) {
      this.position = position;
      return this;
    }

    public Builder withHousing(SimpleBlockHousingBE housing) {
      this.housing = housing;
      return this;
    }

    public Builder withQueen(ItemStack queen) {
      this.queen = queen;
      return this;
    }

    public Builder withFrames(ItemStack... frames) {
      this.frames.addAll(Arrays.asList(frames));
      return this;
    }

    public OutputContext build() {
      return new OutputContext(this.level, this.position, this.housing, this.queen, this.frames);
    }

  }
}
