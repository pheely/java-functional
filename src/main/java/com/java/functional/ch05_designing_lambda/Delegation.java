package com.java.functional.ch05_designing_lambda;

public class Delegation {
  public static void main(String[] args){
    CalculateNAV calculateNAV = new CalculateNAV(FinanceData::getPrice);
    System.out.println(String.format("100 shares of Apple worth: $%.2f",
        calculateNAV.calculateStockWorth("AAPL", 100)));
  }

}
