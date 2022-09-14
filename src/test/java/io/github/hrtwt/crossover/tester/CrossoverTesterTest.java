package io.github.hrtwt.crossover.tester;

import static org.assertj.core.api.Assertions.assertThat;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.Test;
import io.github.hrtwt.crossover.tester.kgp.CrossoverType;

class CrossoverTesterTest {
  CrossoverTester buildCrossoverTester(final Path project, final String jsonFileName) {
    final Path jsonPath = project.resolve(jsonFileName);
    final String json = Util.readString(jsonPath);
    return new CrossoverTester(project, json, 10, 0);
  }

  @Test
  void linkageCrossoverTest01() {
    final Path project = Paths.get("./example/ABC102A/");
    final CrossoverTester tester = buildCrossoverTester(project, "ABC102A-1-0.json");
    final List<CrossoverResults> r = tester.execCrossover(List.of(CrossoverType.Linkage));
    assertThat(r).isNotNull(); // todo
  }

  @Test
  void linkageCrossoverTest02() {
    final Path project = Paths.get("./example/ABC105A/");
    final CrossoverTester tester = buildCrossoverTester(project, "ABC105A-0-0.json");
    final List<CrossoverResults> r = tester.execCrossover(List.of(CrossoverType.Linkage));
    assertThat(r).isNotNull(); // todo
  }

  @Test
  void linkageCrossoverTest03() {
    final Path project = Paths.get("./example/ABC120A/");
    final CrossoverTester tester = buildCrossoverTester(project, "ABC120A-1-40.json");
    final List<CrossoverResults> r = tester.execCrossover(List.of(CrossoverType.Linkage));
    assertThat(r).isNotNull(); // todo
  }

  @Test
  void linkageCrossoverTest04() {
    final Path project = Paths.get("./example/ABC139A/");
    final CrossoverTester tester = buildCrossoverTester(project, "variants.json");
    final List<CrossoverResults> r = tester.execCrossover(List.of(CrossoverType.Linkage));
    assertThat(r).isNotNull(); // todo
  }
}
