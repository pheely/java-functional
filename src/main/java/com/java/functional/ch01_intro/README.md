# Lambda Expressions

*Lambda expressions* are anonymous functions. Unlike methods, which belong to 
a class, lambdas are free-standing functions that are created within methods.

In the following example, there are two lambda expressions. One is passed to 
`filter`, another one to `mapToDouble`.

```java
final List<Integer> prices = Arrays.asList(10, 30, 17, 20, 18, 45, 12);
double total =
    prices.stream()
        .filter(p -> p > 20)
        .mapToDouble(p -> p * 0.9)
        .sum();
```

A *functional interface* is an interface with one `abstract` method. It may 
also have zero or more `static` methods and `default` methods. We can mark a 
functional interface with the `@FunctionalInterface` annotation. Examples of 
functional interfaces are `Function`, `Predicate`, `Consumer`, `Supplier`.

If a method takes a functional interface as a parameter, we can pass in any 
of the following:

- An anonymous inner class
- An lambda expression
- A method or constructor reference

When we pass a lambda expression to a method, the compiler will convert the 
lambda expression to an instance of the appropriate functional interface. 
The synthesized method of this instance conforms to the `abstract` method of 
the functional interface that corresponds to the argument. The parameters of 
the lambda expression must match the parameters of the interface's 
`abstract` method. The synthesized method returns the lambda expression's 
result. If the return type doesn't directly match that of the `abstract` 
method, the synthesized method may convert the return value to a proper 
assignable type. 

For example, the `map` method takes the functional interface `Function<T, R>` 
as its parameter. When the below lambda expression is passed in,

```java
price -> price * 0.9
```

The Java compiler synthesizes the `map` method as follows:
```java
...
Double apply(Integer param) {
  int price = Integer.initValue(param);
  return Double.valueOf(price * 0.9);
}
```

We can also pass a method reference as a functional interface parameter. In 
this case, the Java compiler simply replace the body of code with the method 
name of our choice. For example, instead of passing in lambda expressions 
like below:
```java
friends.stream()
  .map(name -> name.toUpperCase())
  .forEach(name -> System.out.println(name));
```

We can pass in method references as follows:
```java
friends.stream()
  .map(String::toUpperCase)
  .forEach(System.out::println);
```

Java knows to invoke the `String` class's given method `toUpperCase` on the
parameter passed into the synthesized method - the implementation of the
functional interface's `asbtract` method. That parameter reference is
implicit here.  

Method references can refer to instance methods (such as `String::toUpperCase`)
as well as `static` methods (such as `Character::isDigit`) and methods that 
take parameters. 

The syntax for an instance method and the one for a static method look the 
same structurally. To decide how to route the parameter, the Java compiler 
will check whether the method is an instance method or a `static` method. If 
it’s an instance method, then the synthesized method’s parameter becomes the 
call’s target, as in `parameter.toUpperCase()`; (the exception to this rule is 
if the target is already specified as in `System.out::println`). On the other 
hand, if the method is `static`, then the parameter to the synthesized method 
is routed as an argument to this method, as in `Character.isDigit(parameter);`.

While this parameter routing is convenient, there is one caveat—the ambiguity 
that results from method collisions. If there’s both a matching instance 
method and a static method, we’ll get a compilation error due to the reference’s
ambiguity. For example, if we write `Double::toString` to convert an instance 
of `Double` to a `String`, the compiler would get confused whether to use the 
`public String toString()` instance method or the `static` method 
`public static String toString(double value)`, both from the `Double` class. 
If we run into this, we simply switch back to using the appropriate 
lambda-expression version to move on.