package io.github.hrtwt.crossover.tester;

import java.nio.file.Paths;
import org.junit.Test;

public class AppTest {
  @Test
  public void testLunch() {
    App.lunch(
        Paths.get("./example/ABC139A/"), Paths.get("./example/ABC139A/variants.json"), 10, 0);
  }
}
