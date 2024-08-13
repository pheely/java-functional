package com.java.functional.ch05_designing_lambda;

import java.util.List;
import java.util.function.Predicate;

public class AssetUtility {
  public static int totalAssetValues(final List<Asset> assets,
      Predicate<Asset> assetSelector) {
    return assets.stream().filter(assetSelector).mapToInt(Asset::getValue).sum();
  }
}
