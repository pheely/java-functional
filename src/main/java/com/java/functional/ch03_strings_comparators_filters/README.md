# Strings, Comparators, and Filters

We will look at the convenient methods in JDK that promotes functional style.

## Iterating a String

The `char` method of the `String` class returns a `IntStream`. To convert it 
to a `Stream<Character>`, use the `mapToObject` method.

## Comparator as a Functional Interface

The `sorted` method of the `Stream` interface takes a `Comparator` as its 
parameter. `Comparator` is a functional interface and can be in a lambda 
expression form. The `sorted` method iterates over each element in the 
target collection and apply the given `Comparator` to decide the logical 
order of the elements. 

```java
List<Person> people = getPeople();
people.stream()
  .sorted((p1, p2) -> p1.ageDifference(p2)).collect(Collectors.toList());
```

In the above lambda expression, we are routing two parameters - the first 
one as the target to the `ageDifference` method and the second as its 
argument. This gives the third way that the Java compiler routes parameters:
1. A single parameter as a target
2. A single parameter as an argument
3. Two parameters with one as a target and another as the argument of the target

This lambda expression can also be replaced with a method reference as follows:
```java
people.stream().sorted(Person::ageDifference);
```

The `Comparator` has a default `reversed` method that make easy to sort 
elements in a different order. For example, we can define a `Comparator` to 
compare in ascending order as follows:

```java
import java.util.Comparator;

Comparator<Person> compareAscending =
    (p1, p2) -> p1.ageDifference(p2);
```

To reverse the order of comparison, we can simply call the `reversed` on the 
previous `Comparator` to get another `Comparator`.

```java
Comparator<Person> compareDescending = compareAscending.revered();
```

With this, we can sort `People` in either order.
```java
people.stream().sorted(compareAscending).collect(toList());
people.stream().sorted(compareAscending.reversed()).collect(toList());
```

The static method `comparing` of the `Comparator` takes a functional 
interface and returns a `Comparator`. This can make the code more fluent to 
read.

```java
final Function<Person, String> byName = person -> person.getName();
people.stream().sorted(Comparator.comparing(byName)).collect(Collectors.toList()));
```

We can also use another static method `thenComparing` to create composite 
comparators.

```java
final Function<Person, String> byAge = person -> person.getAge();
final Function<Person, Integer> byName = person -> person.getName();
people.stream().
    sorted(Comparator.comparing(byAge).Comparator.thenComparing(byName))
    .collect(Collectors.toList()));
```

## The Stream.collect Method and Collector Class

The `collect` method gathers `Stream` elements into an `ArrayList`. It is a 
*reduce* operation that's useful to transform the collection into another 
form, often a mutable collection. When combined with the utility methods of 
the `Collection` class, the `collect` method provides a wealth of convenience.

The `Collector` interface accumulates input elements into a mutable result 
container, optionally transforming the accumulated result into a final 
representation. Reduction operations can be performed either sequentially or in 
parallel. Examples of mutable reduction operations include: accumulating 
elements into a `Collection`; concatenating strings using a StringBuilder; 
computing summary information about elements such as `sum`, `min`, `max`, or 
`average`, etc. 

A `Collector` is specified by four functions that work together:

- creation of a new result container (`supplier()`)
- incorporating a new data element into a result container (`accumulator()`)
- combining two result containers into one (`combiner()`)
- performing an optional final transform on the container (`finisher()`)

The `Collectors` utility class provides implementations of many common mutable 
reductions. For example, the `toList` convenience method creates an 
`Collector` implementation to accumulate elements into an `List`.

```java
people.stream()
    .filter(p -> p.getAge() > 20)
    .collect(Collectors.toList()));
```

In addition to `toList`, there is `toSet` to accumulate into a set, `toMap` to 
gather into a key-value collection, and `joining` to concatenate the 
elements  into a string. We can also join multiple combine operations using  
methods like `mapping`, `collectingAndThen`, `minBy`, `maxBy`, and `groupingBy`.

For example, we can group people by age as follows:
```java
Map<Integer, List<Person>> peopleByAge =
    people.stream().collect(Collectors.groupingBy(Person::getAge));
```

We can also just group people's name by age using a different version of the 
`groupingBy`:

```java
Map<Integer, List<String>> nameOfPeopleByName =
people.stream().collect(
    Collectors.groupingBy(Person::getAge,
        Collectors.mapping(Person::getName, Collectors.toList())));
```

Here `groupingBy` takes two parameters: the first is a classifier that is 
the criteria to group by; the second is a `Collector`, here that is the 
result of a call to the `mapping` function. The `mapping` method takes two 
parameters, the property on which to map, and the type of the object to 
collect into. 

## Files and Directories

The convenient class `Paths` and the utility class `Files` enable the 
functional style facility to iterate files in a directory.

The `list` method of the `Files` class returns a `Stream` of `Paths`.

```java
Files.list(Paths.get(".")).forEach(System.out::println);
```

We can also filter out the files and leave only subdirectories:
```java
Files.list(Paths.get(".")).filter(Files::isDirectory).forEach(System.out::println);
```

To only list files under a directory with a specific name, we can use the 
`DirectoryStream` facility. It can take a filter parameter. This is of the 
`DirectoryStream. Filter` interface which has one method, `accept`, that 
takes one parameter: `Path path`. We’d return a `true` from the `accept` 
method to include the given filename in the list, and a `false` otherwise. 

```java
Files.newDirectoryStream(Paths.get(path), p -> p.toString().endsWith(extension))
    .forEach(System.out::println);
```

We can also filter files based on their properties, for example if it is a 
hidden file.

```java
Files.newDirectoryStream(Paths.get(path), Files::isHidden)
    .forEach(System.out::println);
```

The `faltMap` method of the `Stream` interface maps each element in a 
collection into a `Stream`, and then flattens multiple streams into one flat 
stream. If any of the `Stream`s is null, it will merge them to an empty 
collection.

```java
List<File> files = Stream.of(new File(path).listFiles())
    .flatMap(file -> file.listFiles() == null ?
        Stream.of(file) : Stream.of(file.listFiles()))
    .collect(Collectors.toList());
System.out.println(files);
```

In this example, the lambda expression passed as a parameter to the `flatMap` 
method returned, for a given file, a `Stream` of its children (if any). 
Otherwise, it returned a stream with just the file. The `flatMap` method 
gracefully handled that and mapped these streams into a resulting collection of 
streams and finally flattened it into one final `Stream` of `File`s.
