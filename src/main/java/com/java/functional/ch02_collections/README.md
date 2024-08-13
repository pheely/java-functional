# Using Collections

## Create an immutable list

```java
List<String> friends = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");
```

## Iterating through a List

The `forEach` method of the `Iterable` interface is the functional style method 
introduced in Java 8. It accepts a 
parameter of type `Consumer`, and performs the 
`Consumer`'s `accept` method on each element of the `Iterable`.

For example, the following statement will print each element:

```java
friends.forEach(name -> System.out.println(name));
```

Here we are using a lambada expression. The former expression should be like 
```java
(String name) -> System.out.println(name)
```

However, Java compiler can infer the types, and does not require the 
brackets around the single parameter.

The limitation of the `forEach` method is that when it starts, we can't 
break out of the iteration.

## Transforming a List

The `map` method of the `Stream` interface can be used to map or transform 
the elements. It takes a `Function` as the parameter.

For example, the following statements convert a list of strings to uppercase 
and print them.

```java
final List<String> friends = 
    Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");
friends.stream()
    .map(name -> name.toUpperCase())
    .forEach(name -> System.out.print(name + " "));
```

The `stream` method is available on all collection in the JDK and it wraps 
the collection into an instance of `Stream`. The `map` method applies the 
given lambda expression or a block of code, as the parameter, on each 
element in the `Stream`. It also collects the result of running the lambda 
expression and returns the resulting collection.

The `map` method is useful to map or transfer an input collection into a new 
output collection. It ensures the same number of elements exists in the 
input and output sequence. But the element types in the input don't have to 
be the same as those in the output collection. For example, in the following 
code, the input is a list of strings while the output is a list of numbers.

```java
friends.stream()
    .map(name -> name.length())
    .forEach(count -> System.out.print(count + " "));
```

The Java compiler can take either a lambda expression or a reference to a 
method in place of an implementation of a functional interface. This feature 
is called *method reference*.

For our uppercase conversion example above, we can use a shorter form as below:
```java
friends.stream()
    .map(String::toUpperCase)
    .forEach(System.out::println);
```

Java knows to invoke the `String` class's given method `toUpperCase` on the 
parameter passed into the synthesized method - the implementation of the 
functional interface's `asbtract` method. That parameter reference is 
implicit here.  

Method references can refer to instance methods (such as `toUpperCase` here) 
as well as `static` methods and methods that take parameters.

## Finding Elements

The `filter` method can be used for picking elements from a collection.

```java
friends.stream()
    .filter(name -> name.startsWith("N"))
    .collect(Collectors.toList());
```

The `filter` method expects a lambda expression that returns a `boolean` 
result. If the lambda expression returns a `true` for an element, that 
element is added to a result collection; it's skipped otherwise. The method 
returns a `Stream` with only elements for which the lambda expression 
yielded a `true`.

The `collect` method transform a `Stream` instance into a `List`.

## Skipping Values

Use the `skip` or `dropWhile` method to skip values:

```java
friends.stream()
    .skip(3)
    .forEach(System.out::println);
```

```java
friends.stream()
    .dropWhile(name -> name.length() > 4)
    .forEach(System.out::println);
```

## Terminating Iterations

Use the `limit` or `takeWhile` method to terminate iterations:

```java
friends.stream()
    .limit(4)
    .forEach(System.out::println);

```

```java
friends.stream()
    .takeWhile(name -> name.length() > 4)
    .forEach(System.out::println);
```

## Reducing a Collection to a Single Value

The `sum` method of the `IntStream` interface is one of the many methods 
that can reduce an Integer type collection into a single value - the sum of 
the elements. Other methods include `max`, `min`, `average`, etc.

```java
getFriends().stream().mapToInt(name -> name.length()).sum());
```

The more generic way to reduce a collection to a single value is to use the 
`reduce` method.

The following code produces the longest name from a list of names.

```java
final Option<String> longName =
  getFriends().stream()
      .reduce((name1, name2) -> name1.length() >= name2.length() ? name1 : name2);

longName.ifPresent(name -> 
    System.out.print(String.format("A longest name: %s%n", name)));
```

The result of the `reduce` method is an `Optional` because the collection 
could be empty. We can use a different version of `reduce` that takes a 
default value.

```java
final String steveOrLonger =
  getFriends().stream()
      .reduce("Steve", (name1, name2) -> name1.length() >= name2.length() ? name1 : name2);
```

## Joining Elements

We can use the `String.join` static method to join a collection of strings.

```java
System.out.println(String.join(", ", getFriends()));
```

Or we can use a special form of `reduce` - `collect`. It collects values 
into a target destination by delegating the actual implementation to a 
collector. Here we use the `Collector` returned by the `Collectors.joining` 
method.

```java
System.out.println(getFriends().stream().collect(Collectors.joining(", ")));
```