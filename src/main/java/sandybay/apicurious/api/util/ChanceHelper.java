package sandybay.apicurious.api.util;

public class ChanceHelper
{
  /**
   * Calculate the chance as a value between 0.0 and 1.0 such that the output occurs
   * approximately once every N minutes.
   *
   * @param timeInMinutes  Desired time interval in minutes for one output.
   * @param timePerAttempt Time per attempt in seconds (default is 27.5 seconds).
   * @return The probability (as a float) that an attempt results in an output.
   */
  public static float calculateChance(double timeInMinutes, double timePerAttempt)
  {
    // Convert timeInMinutes to seconds
    double totalTimeInSeconds = timeInMinutes * 60;

    // Calculate the number of attempts in the given time period
    double numberOfAttempts = totalTimeInSeconds / timePerAttempt;

    // Calculate the chance using the formula derived
    double chance = 1 - Math.exp(Math.log(0.5) / numberOfAttempts);

    return (float) chance;
  }
}
