package com.java.functional.ch03_strings_comparators_filters;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileHandler {
  public static void main(String[] args) throws IOException {
    FileHandler app = new FileHandler();
    app.printFileNames(".");
    app.listSelectFiles("src/main/java/com/java/functional/ch01_intro", ".java");
    app.listHiddenFiles(".");
    app.listImmediateDirectories(".");
  }

  private void printFileNames(String path) throws IOException {
    Files.list(Paths.get(path)).forEach(System.out::println);
  }

  private void printDirectoryNames(String path) throws IOException {
    System.out.println(String.format("Contents of %s", path));
    Files.list(Paths.get(path)).filter(Files::isDirectory).forEach(System.out::println);
  }

  private void listSelectFiles(String path, String extension) throws IOException {
    System.out.println(String.format("%s files under %s", extension, path));
    Files.newDirectoryStream(Paths.get(path), p -> p.toString().endsWith(extension))
        .forEach(System.out::println);
  }

  private void listHiddenFiles(String path) throws IOException {
    System.out.println(String.format("Hidden files under %s", path));
    Files.newDirectoryStream(Paths.get(path), Files::isHidden)
        .forEach(System.out::println);
  }

  private void listImmediateDirectories(String path) throws IOException {
    System.out.println(String.format("Immediate directories under %s", path));
    List<File> files = Stream.of(new File(path).listFiles())
        .flatMap(file -> file.listFiles() == null ?
            Stream.of(file) : Stream.of(file.listFiles()))
        .collect(Collectors.toList());
    System.out.println(files);
  }
}
