package com.java.functional.ch03_strings_comparators_filters;

import javax.xml.stream.events.Characters;

public class Strings {

  public static void main(String[] args){
    Strings strings = new Strings();
    strings.iterateAsInt();
    strings.iterateAsChar();
    strings.iterateAsChar2();
    strings.filtering();
  }

  private void iterateAsInt() {
    final String str = "w00t";

    System.out.println("==== Print as int ====");
    str.chars().forEach(System.out::println);
  }

  private static void printChar(int aChar) {
    System.out.println((char) aChar);
  }

  private void iterateAsChar() {
    final String str = "w00t";

    System.out.println("==== Print as char ====");
    str.chars().forEach(Strings::printChar);
  }

  private void iterateAsChar2() {
    final String str = "w00t";

    System.out.println("==== Print as char ====");
    str.chars().map(ch -> Character.valueOf((char) ch)) // the result is still a IntStream
        .forEach(System.out::println);

    str.chars().mapToObj(ch -> Character.valueOf((char) ch)) // the result is now a Stream<Character>
        .forEach(System.out::println);
  }

  private void filtering() {
    final String str = "w00t";

    System.out.println("==== Print digits ====");
    str.chars().filter(Character::isDigit)
        .forEach(Strings::printChar);
  }
}
