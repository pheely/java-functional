# Designing with Lambda Expressions

## Separating Concerns

Using higher-order functions to accomplish code reuse without a hierarchy of 
classes.

We can implement the **Strategy** pattern using lambda expressions instead 
of creating interfaces and classes. Here we separate a concern from a method.

```java
public class AssetUtility {
  public static int totalAssetValues(final List<Asset> assets,
      Predicate<Asset> assetSelector) {
    return assets.stream().filter(assetSelector).mapToInt(Asset::getValue).sum();
  }
}
```

We want to calculate total asset by asset type. Here we define a high-order 
function `totalAsset` which takes in a list of assets and a predicate. The 
predicate is used by the `filter` method to pick only the assets we are 
interested in. In this case, we turned the three normal methods into one 
higher-order function that depends on a lightweight strategy to handle a 
configurable concern.

## Delegating Using Lambda Expressions

We can also separate a concern from a class. From a reuse point of view, 
delegation is a better design tool than inheritance. With delegation, it’s 
easier to vary the implementation we rely on, and we can plug in a different 
behavior more dynamically. This can help vary the behavior of classes 
independent of the behavior of the parts they depend on, and make the design 
more flexible without forcing a deep class hierarchy.

Rather than delegating part of the responsibility to another class, we can 
delegate it to lambda expressions and method references.

To calculate the net asset value of a stock, we need to know the stock price 
and the number of shares. We would encapsulate the logic of getting stock 
prices into a new class. However, we can use a functional interface. Here we 
inject a `Function<T, R>` into the constructor of the `CalculateNAV` class. 

```java
public class CalculateNAV {
  private Function<String, BigDecimal> priceFinder;

  public CalculateNAV(Function<String, BigDecimal> priceFinder) {
    this.priceFinder = priceFinder;
  }

  public BigDecimal calculateStockWorth(final String ticker, final int shares) {
    return priceFinder.apply(ticker).multiply(BigDecimal.valueOf(shares));
  }
}
```

Then we define a `FinanceData` class that can call a web service to get 
stock prices. Its `getPrice` method takes a stock ticker and returns its 
closing price as a `BigDecimal`. 

```java
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
```

With all these are set, we can create an instance of `CalculateNAV` by 
passing a method reference - `FinanceData::getPrice`.

```java
    CalculateNAV calculateNAV = new CalculateNAV(FinanceData::getPrice);
    System.out.println(String.format("100 shares of Apple worth: $%.2f",
        calculateNAV.calculateStockWorth("AAPL", 100)));
```

## Decorating Using Lambda Expressions

## `default` Methods

## Creating Fluent Interfaces Using Lambda Expressions
