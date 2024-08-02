package sandybay.apicurious.api.util;

import java.util.Objects;

public class Coloring
{
  private final int color;

  private Coloring(int color)
  {
    this.color = color;
  }

  public static Coloring fromHex(String text)
  {
    if (text.length() == 6)
    {
      text = "FF" + text;
    }
    return new Coloring((int) Long.parseLong(text, 16));
  }

  public static Coloring fromInt(int number)
  {
    return fromHex(Integer.toHexString(number));
  }

  public int getIntColor()
  {
    return this.color;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Coloring coloring = (Coloring) o;
    return color == coloring.color;
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(color);
  }
}
