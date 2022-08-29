package io.github.hrtwt.crossover.tester.json;

import static org.assertj.core.api.Assertions.assertThat;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.Test;

public class JsonImporterTest {

  @Test
  public void testImportAdam() throws Exception {
    final String json = Files.readString(Paths.get("../example/ABC139_A/variants.json"));
    final JsonVariant adam = JsonImporter.importAdam(json);
  }

  @Test
  public void testImportComplementaryVariants() throws Exception {
    final String json = Files.readString(Paths.get("../example/ABC139_A/variants.json"));
    final List<JsonVariant> parents = JsonImporter.importComplementaryVariants(json);
    assertThat(parents).hasSize(2);
  }
}
