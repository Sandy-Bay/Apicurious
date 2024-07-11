package sandybay.apicurious.bee.characteristics;

public enum PollinationRate {
  SLOWEST(600),
  SLOWER(500),
  SLOW(400),
  NORMAL(300),
  FAST(200),
  FASTER(100),
  FASTEST(50);

  private final int pollinationDuration;

  PollinationRate(int pollinationDuration) {
    this.pollinationDuration = pollinationDuration;
  }

  public int getPollinationDuration() {
    return pollinationDuration;
  }
}
