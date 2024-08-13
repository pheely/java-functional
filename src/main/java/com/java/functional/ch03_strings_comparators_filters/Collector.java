package com.java.functional.ch03_strings_comparators_filters;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class Collector {
  public static void main(String[] args){
    Collector app = new Collector();
    app.olderThan20();
    app.peopleByAge();
    app.peopleNameByAge();
    app.oldestPersonOfEachLetter();
  }

  private List<Person> getPeople() {
    return Arrays.asList(
        new Person("John", 20),
        new Person("Sara", 22),
        new Person("Jane", 22),
        new Person("Greg", 35)
    );
  }

  private void olderThan20() {
    List<Person> people = getPeople();
    printPeople("People older than 20",
        people.stream()
            .filter(p -> p.getAge() > 20)
            .collect(Collectors.toList()));
  }

  private void peopleByAge() {
    List<Person> people = getPeople();
    Map<Integer, List<Person>> peopleByAge =
        people.stream().collect(Collectors.groupingBy(Person::getAge));
    System.out.println(peopleByAge);
  }

  private void peopleNameByAge() {
    List<Person> people = getPeople();
    Map<Integer, List<String>> nameOfPeopleByName =
        people.stream().collect(
            Collectors.groupingBy(Person::getAge,
                Collectors.mapping(Person::getName, Collectors.toList())));
    System.out.println(nameOfPeopleByName);
  }

  private void oldestPersonOfEachLetter() {
    List<Person> people = getPeople();
    final Comparator<Person> byAge = Comparator.comparing(Person::getAge);
    Map<Character, Optional<Person>> oldestPersonofEachLetter =
      people.stream().collect(
          Collectors.groupingBy(person -> person.getName().charAt(0),
              Collectors.reducing(BinaryOperator.maxBy(byAge))));
    System.out.println(oldestPersonofEachLetter);
  }

  private void printPeople(final String message, final List<Person> people) {
    System.out.println(message);
    people.forEach(System.out::println);
  }

}
