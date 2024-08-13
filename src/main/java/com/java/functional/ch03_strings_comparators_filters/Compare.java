package com.java.functional.ch03_strings_comparators_filters;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Compare {
  public static void main(String[] args){
    Compare app = new Compare();
    app.sortByAge();
    app.sortUsingComparing();
  }

  private List<Person> getPeople() {
    return Arrays.asList(
        new Person("John", 20),
        new Person("Sara", 22),
        new Person("Jane", 22),
        new Person("Greg", 35)
    );
  }

  private void sortByAge() {
    List<Person> people = getPeople();
    printPeople("Sorted in ascending order by age - lambda: ",
        people.stream().sorted((p1, p2) -> p1.ageDifference(p2)).collect(Collectors.toList()));

    printPeople("Sorted in ascending order by age - method reference: ",
        people.stream().sorted(Person::ageDifference).collect(Collectors.toList()));

    Comparator<Person> compareAscending = (p1, p2) -> p1.ageDifference(p2);
    printPeople("Sorted in ascending order by age - comparator ascending: ",
        people.stream().sorted(compareAscending).collect(Collectors.toList()));

    printPeople("Sorted in ascending order by age - comparator descending: ",
        people.stream().sorted(compareAscending.reversed()).collect(Collectors.toList()));
  }

  private void printPeople(final String message, final List<Person> people) {
    System.out.println(message);
    people.forEach(System.out::println);
  }

  private void sortUsingComparing() {
    final Function<Person, String> byName = person -> person.getName();
    final Function<Person, Integer> byAge = person -> person.getAge();
    List<Person> people = getPeople();
    printPeople("Sorted by name: ",
        people.stream().sorted(Comparator.comparing(byName)).collect(Collectors.toList()));
    printPeople("Sorted by age and then name: ",
        people.stream()
            .sorted(Comparator.comparing(byAge).thenComparing(byName))
            .collect(Collectors.toList()));
  }
}
