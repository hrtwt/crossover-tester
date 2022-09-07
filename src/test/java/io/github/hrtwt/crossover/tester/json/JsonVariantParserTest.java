package io.github.hrtwt.crossover.tester.json;

import static org.assertj.core.api.Assertions.assertThat;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.Test;

public class JsonVariantParserTest {

  @Test
  public void testParseAdam() throws Exception {
    final String json = Files.readString(Paths.get("./example/ABC139A/variants.json"));
    final JsonVariant adam = JsonVariantParser.parseAdam(json);
  }

  @Test
  public void testParseComplementaryVariants() throws Exception {
    final String json = Files.readString(Paths.get("./example/ABC139A/variants.json"));
    final List<JsonVariant> parents = JsonVariantParser.parseComplementaryVariants(json);
    assertThat(parents).hasSize(2);
  }
}
