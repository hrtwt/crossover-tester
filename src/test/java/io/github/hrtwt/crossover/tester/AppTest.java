package io.github.hrtwt.crossover.tester;

import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

class AppTest {
  @Test
  void testLunch() {
    App.lunch(Paths.get("./example/ABC139A/"), Paths.get("./example/ABC139A/variants.json"), 10, 0);
  }
}
