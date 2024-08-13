package com.java.functional.ch05_designing_lambda;

import com.java.functional.ch05_designing_lambda.Asset.AssetType;
import java.util.Arrays;
import java.util.List;

public class SeparatingConcerns {

  public static void main(String[] args) {
    SeparatingConcerns app = new SeparatingConcerns();
    List<Asset> assets = app.getAsset();
    System.out.println(String.format("Total of all assets: %d%n",
        AssetUtility.totalAssetValues(assets, asset -> true)));
    System.out.println(String.format("Total of all bonds: %d%n",
        AssetUtility.totalAssetValues(assets, asset -> asset.getType() == AssetType.BOND)));
    System.out.println(String.format("Total of all stock: %d%n",
        AssetUtility.totalAssetValues(assets, asset -> asset.getType() == AssetType.STOCK)));
  }

  final List<Asset> getAsset() {
    return Arrays.asList(
        new Asset(AssetType.BOND, 1000),
        new Asset(AssetType.BOND, 2000),
        new Asset(AssetType.STOCK, 3000),
        new Asset(AssetType.STOCK, 4000)
    );
  }
}


