package sandybay.apicurious.bee.characteristics;

public enum Lifespan {
  SHORTEST(400),
  SHORTER(900),
  SHORT(1200),
  NORMAL(1500),
  LONG(1800),
  LONGER(2100),
  LONGEST(2400);

  private int lifespan;

  Lifespan(int lifespan) {
    this.lifespan = lifespan;
  }

  public int getLifespan() {
    return lifespan;
  }
}
