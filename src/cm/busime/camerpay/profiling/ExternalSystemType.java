package cm.busime.camerpay.profiling;

public enum ExternalSystemType {
  DATABASE("database"),
  FILE("file"),
  HTTP("http"),
  OTHER("other");

  private String name;

  private ExternalSystemType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
