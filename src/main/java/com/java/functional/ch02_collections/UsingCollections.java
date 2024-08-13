package com.java.functional.ch02_collections;

import static java.util.stream.Collectors.joining;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UsingCollections {

  private List<String> getFriends() {
    return Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");
  }

  private void iterate() {
    System.out.println("====== iterate ======");
    List<String> friends = getFriends();
    friends.forEach(name -> System.out.println(name));
  }

  private void transform() {
    System.out.println("====== transform ======");
    List<String> friends = getFriends();
    friends.stream()
        .map(name -> name.toUpperCase())
        .forEach(name -> System.out.print(name + " "));
    System.out.println();

    friends.stream()
        .map(name -> name.length())
        .forEach(count -> System.out.print(count + " "));
    System.out.println();

    friends.stream()
        .map(String::toUpperCase)
        .forEach(System.out::println);
    System.out.println();
  }

  private void filter() {
    System.out.println("====== filter ======");
    List<String> friends = getFriends();
    friends.stream()
        .filter(name -> name.startsWith("N"))
        .collect(Collectors.toList())
        .forEach(System.out::println);
  }

  private void skipElements() {
    System.out.println("====== skip ======");
    List<String> friends = getFriends();
    // skip the first 3
    friends.stream()
        .skip(3)
        .forEach(System.out::println);

    System.out.println("====== drop ======");
    // using dropWhile
    friends.stream()
        .dropWhile(name -> name.length() > 4)
        .forEach(System.out::println);
  }

  private void terminate() {
    System.out.println("====== limit ======");
    List<String> friends = getFriends();
    // skip the first 3
    friends.stream()
        .limit(4)
        .forEach(System.out::println);

    System.out.println("====== takeWhile ======");
    // using dropWhile
    friends.stream()
        .takeWhile(name -> name.length() > 4)
        .forEach(System.out::println);
  }

  private void pick(final List<String> names, final String startingLetter) {
    System.out.println("====== pick ======");
    final Optional<String> foundName =
        names.stream().filter(name -> name.startsWith(startingLetter)).findFirst();
    System.out.println(String.format("A name starting with %s: %s",
        startingLetter, foundName.orElse("Not Found")));
  }

  private void reduce() {
    System.out.println("====== reduce - sum ======");
    System.out.println("Total number of characters in all names: " +
        getFriends().stream().mapToInt(name -> name.length()).sum());

    System.out.println("====== reduce - generic ======");
    final Optional<String> longName =
        getFriends().stream()
            .reduce((name1, name2) -> name1.length() >= name2.length() ? name1 : name2);

    longName.ifPresent(name ->
        System.out.print(String.format("A longest name: %s%n", name)));

    final String steveOrLonger =
        getFriends().stream()
            .reduce("Steve", (name1, name2) -> name1.length() >= name2.length() ? name1 : name2);
    System.out.println("====== reduce - default ======");
    System.out.println(steveOrLonger);
  }

  private void join() {
    System.out.println("====== join - String.join======");
    System.out.println(String.join(", ", getFriends()));

    System.out.println("====== join - collect ======");
    System.out.println(getFriends().stream().collect(joining(", ")));
  }

  public static void main(String[] args){
    UsingCollections app = new UsingCollections();
    app.iterate();
    app.transform();
    app.filter();
    app.skipElements();
    app.terminate();
    app.pick(app.getFriends(), "N");
    app.pick(app.getFriends(), "Z");
    app.reduce();
    app.join();
  }
}
