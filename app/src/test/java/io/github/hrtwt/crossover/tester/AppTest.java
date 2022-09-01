package io.github.hrtwt.crossover.tester;

import org.junit.Test;
import java.nio.file.Paths;

public class AppTest {
  @Test
  public void testLunch() {
    App.lunch(Paths.get("../example/ABC139_A/"), Paths.get("../example/ABC139_A/variants.json"), 0);
  }
}
