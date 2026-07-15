package sandybay.apicurious.api.bee.genetic.allele;

import net.minecraft.network.chat.Component;

import java.util.Objects;

public abstract class AbstractAllele<T extends IAllele<T>> implements IAllele<T>
{
  private final boolean isDominantTrait;
  private final String name;
  private Component readableName;

  protected AbstractAllele(boolean isDominantTrait, String name)
  {
    this.isDominantTrait = isDominantTrait;
    this.name = name;
  }

  @Override
  public boolean isDominantTrait()
  {
    return isDominantTrait;
  }

  public String getName()
  {
    return name;
  }

  @Override
  public Component getReadableName()
  {
    if (readableName == null)
    {
      readableName = Component.translatable(this.name);
    }
    return readableName;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (o == null || getClass() != o.getClass())
    {
      return false;
    }
    AbstractAllele<?> that = (AbstractAllele<?>) o;
    return isDominantTrait == that.isDominantTrait && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(isDominantTrait, name);
  }
}