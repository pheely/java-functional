package com.java.functional.ch05_designing_lambda;

import java.math.BigDecimal;
import java.util.function.Function;

public class CalculateNAV {
  private Function<String, BigDecimal> priceFinder;

  public CalculateNAV(Function<String, BigDecimal> priceFinder) {
    this.priceFinder = priceFinder;
  }

  public BigDecimal calculateStockWorth(final String ticker, final int shares) {
    return priceFinder.apply(ticker).multiply(BigDecimal.valueOf(shares));
  }
}
