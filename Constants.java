public enum Constants {
  INPUT(1),
  CALC(2),
  OUTPUT(3),
  EXIT(4),
  MAX_SIZE(100),
  COLUMN(1),
  ROW(2);

  private int value;

  Constants(int value) {
    this.value = value;
  }

  public int getValue() {
    return this.value;
  }
}
