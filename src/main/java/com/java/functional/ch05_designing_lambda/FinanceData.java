package com.java.functional.ch05_designing_lambda;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Scanner;

public class FinanceData {
  public static BigDecimal getPrice(final String ticker) {
    try {
      final String urlString = "https://eodhistoricaldata.com/api/eod/%s.US?%s&%s&%s";
      final URL url = new URL(String.format(urlString,
          ticker,
          "fmt=json",
          "filter=last_close",
          "api_token=OeAFFmMliFG5orCUuwAKQ8l4WWFQ67YX"));
      try (Scanner scanner = new Scanner(url.openStream())) {
        return new BigDecimal(scanner.nextLine());
      }
    } catch(Exception exception) {
      throw new RuntimeException(exception);
    }
  }
}
