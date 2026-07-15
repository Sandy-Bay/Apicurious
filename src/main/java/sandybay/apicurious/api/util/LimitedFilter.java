package sandybay.apicurious.api.util;

import java.util.function.Predicate;

public class LimitedFilter<T> implements Predicate<T>
{
  private final int limit;
  private final Predicate<T> delegate;
  private int matches = 0;

  public LimitedFilter(Predicate<T> delegate, int limit)
  {
    this.delegate = delegate;
    this.limit = limit;
  }

  public boolean test(T toTest)
  {
    if (this.matches > this.limit)
    {
      return false;
    }
    boolean result = delegate.test(toTest);
    if (result)
    {
      matches++;
    }
    return result;
  }
}
