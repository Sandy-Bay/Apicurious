package sandybay.apicurious.api.util;

public class AlleleNaming
{
  private AlleleNaming() {}

  public static String key(String category, String name)
  {
    return "apicurious." + category + "." + name;
  }
}
