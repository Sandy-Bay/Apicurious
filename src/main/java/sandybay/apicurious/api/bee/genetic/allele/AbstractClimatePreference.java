package sandybay.apicurious.api.bee.genetic.allele;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import java.util.Objects;

public abstract class AbstractClimatePreference<T extends IAllele<T>> extends AbstractAllele<T>
{
  private final int value;
  private final TagKey<Biome> groupTag;

  protected AbstractClimatePreference(int value, TagKey<Biome> groupTag, boolean isDominantTrait, String name)
  {
    super(isDominantTrait, name);
    this.value = value;
    this.groupTag = groupTag;
  }

  public int getValue()
  {
    return value;
  }

  public TagKey<Biome> getGroupTag()
  {
    return groupTag;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (!super.equals(o)) return false;
    AbstractClimatePreference<?> that = (AbstractClimatePreference<?>) o;
    return value == that.value && Objects.equals(groupTag, that.groupTag);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(super.hashCode(), value, groupTag);
  }
}