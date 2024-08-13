package com.java.functional.ch04_transform_data;

import java.util.List;
import java.util.stream.Collectors;

public class Statistics {
  public static void main(String[] args){
    Statistics app = new Statistics();
    app.method1();
    app.method2();
    app.method3();
    app.method4();
  }

  private void method1() {
    System.out.println("Evolution 1");
    System.out.println("Average number of email addresses: " +
      Person.SAMPLE_DATA.stream()
          .map(Person::emailAddress)
          .mapToDouble(List::size)
          .sum()/(Person.SAMPLE_DATA.size() * 1.0));
  }

  private void method2() {
    System.out.println("Evolution 2 - using average");
    System.out.println("Average number of email addresses: " +
        Person.SAMPLE_DATA.stream()
            .map(Person::emailAddress)
            .mapToDouble(List::size)
            .average()
            .orElse(0));
  }

  private void method3() {
    System.out.println("Evolution 3 - using Collector convenience methods");
    System.out.println("Average number of email addresses: " +
        Person.SAMPLE_DATA.stream()
            .collect(Collectors.averagingDouble(person -> person.emailAddress().size())));
  }

  private void method4() {
    System.out.println("Evolution 4 - using Collector convenience methods with multiple statistics");
    var statistics = Person.SAMPLE_DATA.stream()
        .collect(Collectors.summarizingDouble(person -> person.emailAddress().size()));
    System.out.println("Number of email addresses: " + statistics.getSum());
    System.out.println("Average number of email addresses: " + statistics.getAverage());
    System.out.println("Max number of email addresses: " + statistics.getMax());
    System.out.println("Min number of email addresses: " + statistics.getMin());
  }
}
