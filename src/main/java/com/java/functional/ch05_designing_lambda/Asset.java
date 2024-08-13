package com.java.functional.ch05_designing_lambda;

public class Asset {
  public enum AssetType {BOND, STOCK};
  private AssetType type;
  private int value;

  public Asset(AssetType type, int value) {
    this.type = type;
    this.value = value;
  }

  public AssetType getType() {
    return type;
  }

  public int getValue() {
    return value;
  }
}
