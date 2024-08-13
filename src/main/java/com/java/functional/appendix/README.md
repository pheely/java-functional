# Appendixes

## Starter Set of Functional Interfaces

| Name             | Description                                                                                                                     | Abstract method | `default` method(s)       | Popular usage                                                                                       | Primitive specifications                                                                        |
|------------------|---------------------------------------------------------------------------------------------------------------------------------|-----------------|---------------------------|-----------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------|
| `Consumer<T>`    | Represents an operation that will accept an input and returns nothing. For this to be useful, it'll have to cause side effects. | `accept`        | `andThen`                 | As a parameter to the `forEach` method                                                              | `IntConsumer`, `LongConsumer`, `DoubleConsumer`...                                              |
| `Supplier<T>`    | A factory that's expected to return either a new instance or a pre-created instance.                                            | `get`           | -                         | To create lazy infinite `Stream`s and as the parameter to the `Optional` class's `orElseGet` method | `IntSuuplier`, `LongSupplier`, `DoubleSupplier`...                                              |
| `Predicate<T>`   | Useful to checking if an input argument satisfies some condition                                                                | `test`          | `and`, `negate`, and `or` | As a parameter to `Stream`s methods such as `filter` and `anyMatch`                                 | `IntPredicate`, `LongPredicate`, `DoublePredicate`...                                           |
| `Function<T, R>` | A transformational interface that represents operation intended to take in an argument and return an appropriate result         | `apply`         | `andThen`, `compose`       | As a parameter to `Stream`s `map` method                                                            | `IntFunction`, `LongFunction`, `DoubleFunction`, `IntToDoubleFunction`, `DoubleToIntFunction`... |

## Syntax

### Defining a Functional Interface

```java
@FunctionalInterface
public interface TailCall<T> {
  TailCall<T> apply();
  
  default boolan isComplete() { return false; }
  //...
}
```

A functional interface must have one `abstract` - unimplemented - method. It 
may have zero or more `default` methods. It may also have `static` methods.

### Creating Non-Parameter Lambda Expressions

```java
lazyEvaluation(() -> evaluation(1), () -> evaluation(2));
```

The parentheses around the empty parameters list are required if the lambda 
expression takes no parameters. The `->` separates the parameters from the 
body of a lambda expression.

### Creating a Single-Parameter Lambda Expression

```java
firends.forEach((final String name) -> System.out.println(name));
```

The Java compiler can infer the type of lambda expression based on the 
context. In some situations where the context isn't adequate for it to infer 
or we want better clarity, we can specify the type in front of the parameter 
names. 

### Inferring a Lambda Expression's Parameter Type 

```java
friends.forEach((name) -> System.out.println(name));
```

The Java compiler will try to infer the type for parameters if we don't 
provide them. Using inferred types is less noisy and requires less effort, 
but if we specify the type of one parameter, we have to specify it for all 
parameters in a lambda expression.

### Dropping Parentheses for a Single-Parameter Inferred Type

```java
friends.forEach(name -> System.out.println(name));
```

The parentheses around the parameter are optional if the lambda expression 
takes only one parameter and its type is inferred. 

### Creating a Multiple-Parameter Lambda Expression

```java
friends.stream()
    .reduce((name1, name2) -> name1.length() >= name2.length() ? name1 : name2);
```

The parentheses around the parameter list are required if the lambda 
expression takes multiple parameters or no parameters.

### Calling a Method with Mixed Parameters

```java
friends.stream()
    .reduce("Steve", (name1, name2) -> name1.lenghth() >= name2.length() ?  name1 : name2);
```

Methods can have a mixture of regular classes, primitive types, and 
functional interfaces as parameters. Any parameter of a method may be a 
functional interface, and we can send a lambda expression or a method 
reference as an argument in its place.

### Storing a Lambda Expression

```java
import java.util.function.Predicate;

final Predicate<String> startWithN = name -> name.startsWith("N");
```

To aid reuse and avoid duplication, we often want to store lambda expression 
in variables.

### Creating a Multiline Lambda Expression

```java
FileWriterEAM.use("eam2.txt", writeEAM -> {
      writeEAM.writeStuff("how");
      writeEAM.writeStuff("sweet")
}));
```

We should keep the lambda expression short, but it's easy to sneak in a few 
lines of code.

### Returning a Lambda Expression

```java
public static Predicate<String> checkIfStartsWith(final String letter) {
  return name -> name.startsWith(letter);
}
```

If a method's return type is a functional interface, we can return a lambda 
expression from within its implementation.

## Returning a Lambda Expression from a Lambda Expression

```java
import java.util.function.Predicate;

final Funtion<String, Predicate<String>> startsWithLetter = 
  letter -> name -> name.startsWith(letter);
```

We can build lambda expressions that themselves return lambda expressions. 
The implementation of the `Function` interface here takes in a `String 
letter` and returns a lambda expression that conforms to the `Predicate` 
interface.

### Lexical Scoping in Closures

```java
public static Predicate<String> checkIfStartsWith(final String letter) {
  return name -> name.startsWith(letter);
}
```

From within a lambda expression, we can access variables that are in the 
enclosing method's scope. For example, the variable `letter` in the 
`checkIfStartsWith` is accessed within the lambda expression. Lambda 
expressions that bind to variables in enclosing scopes are called _closures_.

### Passing a Method Reference of an Instance Method

```java
friends.stream().map(String::toUpperCase);
```

We can replace a lambda expression with a method reference if it directly 
routes the parameter as a target to a simple method call. The preceding 
sample code is equivalent to this:
```java
friends.stream().map(name -> name.toUpperCase());
```

### Passing a Method Reference to a static Method

```java
str.chars().filter(Character::isDigit);
```

We can replace a lambda expression with a method reference if it directly 
routes the parameter as an argument to a `static` method. The preceding 
sample code is equivalent to this:
```java
str.chars().filter(ch -> Character.isDigit(ch));
```

### Passing a Method Reference to a Method on Another Instance

```java
str.chars().forEach(System.out.println);
```

We can replace a lambda expression with a method reference if it directly 
routes the parameter as an argument to a method on another instance; for 
example, `println` on `System.out`.

### Passing a Reference of a Method that Takes Parameters

```java
people.stream().sorted(Person::ageDifference);
```

We can replace a lambda expression with a method reference if it directly 
routes the first parameter as a target of a method call and the remaining 
parameters as this method's arguments. The preceding sample code is 
equivalent to this:

```java
people.stream().sorted(
    (person1, person2) -> person1.ageDifference(person2)
);
```

### Using a Constructor Reference

```java
Supplier<Heavy> supplier = Heavy::new;
```

Instead of invoking a constructor, we can ask the Java compiler to create 
the calls to the appropriate constructor from the concise 
constructor-reference syntax. These work much like method references, 
except they refer to a constructor and result in object instantiation. The 
preceding sample code is equivalent to this:

```java
Supplier<Heavy> supplier = () -> new Heavy();
```

### Functional Composition

```java
smbols.map(StockUtil::getPrice)
    .filter(StockUtil::isPriceLessThan(500))
    .reduce(StockUtil::pickHigh)
    .get();
```

We can compose functions to transform objects through a series of operations 
like in this example. In the functional style of programming, function 
composition or chaining is a powerful construct to implement associative 
operations.