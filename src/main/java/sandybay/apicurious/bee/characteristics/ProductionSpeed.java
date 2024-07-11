package sandybay.apicurious.bee.characteristics;

public enum ProductionSpeed {
  SLOWEST(600),
  SLOWER(500),
  SLOW(400),
  NORMAL(300),
  FAST(200),
  FASTER(100),
  FASTEST(50);

  private final int productionDuration;

  ProductionSpeed(int productionDuration) {
    this.productionDuration = productionDuration;
  }

  public int getProductionDuration() {
    return productionDuration;
  }
}
