package com.java.functional.ch02_collections;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class MoreOnLambda {
  private List<String> getFriends() {
    return Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");
  }

  private List<String> getEditors() {
    return Arrays.asList("Brian", "Jackie", "John", "Mike");
  }

  private List<String> getComrades() {
    return Arrays.asList("Kate", "Ken", "Nick", "Paula", "Zach");
  }

  private void filterByStartWith() {
    List<String> friends = getFriends();
    List<String> editors = getEditors();
    List<String> comrades = getComrades();

    final Predicate<String> startWithN = name -> name.startsWith("N");

    // reuse a lambda
    friends.stream().filter(startWithN).forEach(System.out::println);
    editors.stream().filter(startWithN).forEach(System.out::println);
    comrades.stream().filter(startWithN).forEach(System.out::println);
  }

  private Predicate<String> checkIfStartsWith(final String letter) {
    // lexical scoping
    return name -> name.startsWith(letter);
  }

  private void filterByStartWith2() {
    List<String> friends = getFriends();

    friends.stream().filter(checkIfStartsWith("N")).forEach(System.out::println);
    friends.stream().filter(checkIfStartsWith("B")).forEach(System.out::println);
    friends.stream().filter(checkIfStartsWith("S")).forEach(System.out::println);
  }

  public static void main(String[] args){
    MoreOnLambda app = new MoreOnLambda();
    app.filterByStartWith();
    app.filterByStartWith2();
  }
}
