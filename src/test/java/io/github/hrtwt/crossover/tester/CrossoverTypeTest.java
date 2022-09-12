package io.github.hrtwt.crossover.tester;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;
import io.github.hrtwt.crossover.tester.json.JsonVariant;
import io.github.hrtwt.crossover.tester.json.JsonVariantParser;
import io.github.hrtwt.crossover.tester.kgp.CrossoverType;
import jp.kusumotolab.kgenprog.ga.variant.Variant;
import jp.kusumotolab.kgenprog.ga.variant.VariantStore;

public class CrossoverTypeTest {

  List<JsonVariant> parsedVariants(Path path) throws IOException {
    final String json = Files.readString(path);
    return JsonVariantParser.parseComplementaryVariants(json);
  }

  @Test
  void linkageCrossoverTest() throws Exception {
    final Path project = Paths.get("./example/ABC102A/");
    final List<JsonVariant> jsons = parsedVariants(project.resolve("ABC102A-1-0.json"));
    final Variant v1 = VariantBuilder.makeVariant(jsons.get(0), Util.createVariantStore(project));
    final Variant v2 = VariantBuilder.makeVariant(jsons.get(1), Util.createVariantStore(project));
    final List<Variant> children =
        CrossoverType.Linkage.makeVariants(v1, v2, new Random(0), Util.createVariantStore(project));
    assertThat(children).isNotNull(); // todo

    VariantStore vs = Util.createVariantStore(project);
    final List<Variant> minified1 =
        CrossoverType.Linkage.makeVariants(v1, vs.getInitialVariant(), new Random(0), vs);
    assertThat(minified1).isNotNull(); // todo

    vs = Util.createVariantStore(project);
    final List<Variant> minified2 =
        CrossoverType.Linkage.makeVariants(v2, vs.getInitialVariant(), new Random(0), vs);
    assertThat(minified2).isNotNull(); // todo
  }

  @Test
  void SinglePointCrossoverTest() throws Exception {
    final Path project = Paths.get("./example/ABC105A");
    final List<JsonVariant> jsons = parsedVariants(project.resolve("ABC105A-0-0.json"));
    final Variant v1 = VariantBuilder.makeVariant(jsons.get(0), Util.createVariantStore(project));
    final Variant v2 = VariantBuilder.makeVariant(jsons.get(1), Util.createVariantStore(project));
    final List<Variant> children =
        CrossoverType.SinglePoint.makeVariants(
            v1, v2, new Random(0), Util.createVariantStore(project));
    assertThat(children).isNotNull();
  }

  @Test
  void UniformCrossoverTest() throws Exception {
    final Path project = Paths.get("./example/ABC120A");
    final List<JsonVariant> jsons = parsedVariants(project.resolve("ABC120A-1-40.json"));
    final Variant v1 = VariantBuilder.makeVariant(jsons.get(0), Util.createVariantStore(project));
    final Variant v2 = VariantBuilder.makeVariant(jsons.get(1), Util.createVariantStore(project));
    final CrossoverResults r = new CrossoverTester(project, null, 10, 0).execCrossover(v1, v2, CrossoverType.Uniform);
    System.out.println(r);
  }
}
