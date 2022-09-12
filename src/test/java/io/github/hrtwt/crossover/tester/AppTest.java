package io.github.hrtwt.crossover.tester;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AppTest {

  public static Stream<Arguments> exampleProvider() {
    return Stream.of(
        arguments(Paths.get("./example/ABC102A/"), Paths.get("./example/ABC102A/ABC102A-0-1.json")),
        arguments(Paths.get("./example/ABC105A/"), Paths.get("./example/ABC105A/ABC105A-0-0.json")),
        arguments(Paths.get("./example/ABC139A/"), Paths.get("./example/ABC139A/variants.json")));
  }

  @ParameterizedTest
  @MethodSource("exampleProvider")
  void testLunch(final Path project, final Path json) {
    App.lunch(project, json, 10, 0);
  }
}
