package com.java.functional.ch05_designing_lambda;

import java.awt.Color;
import java.util.function.Function;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public class Camera {
  private Function<Color, Color> filter;

  public Camera() {
    setFilters();
  }
  
  public Color capture(final Color inputColor) {
    final Color processedColor = filter.apply(inputColor);
    // more processing of color
    return processedColor;
  }

  public void setFilters(final Function<Color, Color>... filters) {
    filter =
        Stream.of(filters).reduce((first, next) -> first.andThen(next)).orElse(color -> color);
  }
}
