package io.github.hrtwt.crossover.tester;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import io.github.hrtwt.crossover.tester.json.JsonVariant;
import io.github.hrtwt.crossover.tester.json.JsonVariantParser;
import jp.kusumotolab.kgenprog.ga.variant.Variant;
import jp.kusumotolab.kgenprog.ga.variant.VariantStore;

public class CrossoverTesterTest {

  VariantStore store;

  @Before
  public void setupVariantStore() {
    store = Util.createVariantStore(Paths.get("../example/ABC139_A/"));
  }

  public List<Variant> variants() throws IOException {
    final String json = Files.readString(Paths.get("../example/ABC139_A/variants.json"));
    final List<JsonVariant> jsons = JsonVariantParser.parseComplementaryVariants(json);

    final Variant v0 = VariantBuilder.makeVariant(jsons.get(0), store);

    setupVariantStore();
    final Variant v1 = VariantBuilder.makeVariant(jsons.get(1), store);

    return List.of(v0, v1);
  }

  @Test
  public void testIsDominateParents() throws Exception {
    final List<Variant> parents = variants();
    assertThat(CrossoverTester.isDominateParents(List.of(parents.get(0)), parents.get(1)))
        .isFalse();
    assertThat(CrossoverTester.isDominateParents(List.of(parents.get(1)), parents.get(0)))
        .isFalse();
  }
}
