package io.github.hrtwt.crossover.tester;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.github.hrtwt.crossover.tester.json.JsonVariant;
import io.github.hrtwt.crossover.tester.json.JsonVariantParser;
import jp.kusumotolab.kgenprog.ga.variant.Variant;
import jp.kusumotolab.kgenprog.ga.variant.VariantStore;

class VariantBuilderTest {

  private VariantStore store;

  @BeforeEach
  void setupVariantStore() {
    store = Util.createVariantStore(Paths.get("./example/ABC139A/"));
  }

  List<JsonVariant> parsedVariants() throws IOException {
    final String json = Files.readString(Paths.get("./example/ABC139A/variants.json"));
    return JsonVariantParser.parseComplementaryVariants(json);
  }

  @Test
  void testMakeVariant01() throws Exception {
    final JsonVariant jsonVariant = parsedVariants().get(0);
    final Variant v = VariantBuilder.makeVariant(jsonVariant, store);
    assertThat(v).isNotNull(); // todo strict check
  }

  @Test
  void testMakeVariant02() throws Exception {
    final JsonVariant jsonVariant0 = parsedVariants().get(0);
    final Variant v0 = VariantBuilder.makeVariant(jsonVariant0, store);
    assertThat(v0).isNotNull(); // todo strict check

    setupVariantStore(); // need to reset variantStore
    final JsonVariant jsonVariant1 = parsedVariants().get(1);
    final Variant v1 = VariantBuilder.makeVariant(jsonVariant1, store);
    assertThat(v1).isNotNull(); // todo strict check
  }

  @Test
  void testHasSameTestResults01() throws Exception {
    final List<JsonVariant> jsonVariants = parsedVariants();
    final Variant v0 = VariantBuilder.makeVariant(jsonVariants.get(0), store);
    assertThat(VariantBuilder.hasSameTestResults(v0, jsonVariants.get(0))).isTrue();
    assertThat(VariantBuilder.hasSameTestResults(v0, jsonVariants.get(1))).isFalse();
  }
}
