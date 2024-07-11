package sandybay.apicurious.bee.characteristics;

public enum TemperatureTolerance {
  NO_TOLERANCE(0),
  LOW_TOLERANCE(1),
  HIGH_TOLERANCE(2);

  private final int toleranceModifier;

  TemperatureTolerance(int toleranceModifier) {
    this.toleranceModifier = toleranceModifier;
  }

  public int getToleranceModifier() {
    return toleranceModifier;
  }
}
