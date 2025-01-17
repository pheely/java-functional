package com.java.functional.ch03_strings_comparators_filters;

public class Person {
  private final String name;
  private final int age;

  public Person(final String name, final int age) {
    this.name = name;
    this.age = age;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public int ageDifference(final Person other) {
    return age - other.age;
  }

  public String toString() {
    return String.format("%s - %s", name, age);
  }
}
