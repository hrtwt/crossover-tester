package io.github.hrtwt.crossover.tester.json;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.google.common.reflect.TypeToken;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import io.github.hrtwt.crossover.tester.json.JsonVariant.JsonVariantBuilder;
import jp.kusumotolab.kgenprog.project.TestFullyQualifiedName;

public class VariantDeserializer implements JsonDeserializer<JsonVariant> {

  @Override
  public JsonVariant deserialize(
      final JsonElement json, final Type type, final JsonDeserializationContext context)
      throws JsonParseException {
    JsonObject obj = (JsonObject) json;
    final JsonVariantBuilder variantBuilder =
        JsonVariant.builder()
            .id(obj.get("id").getAsLong())
            .generationNumber(obj.get("generationNumber").getAsInt())
            .bases(
                context.deserialize(
                    obj.get("bases"),
                    new TypeToken<Collection<JsonVariant.JsonBase>>() {}.getType()))
            .generatedSourceCode(
                context.deserialize(
                    obj.get("generatedSourceCode"),
                    new TypeToken<Collection<String>>() {}.getType()));

    final JsonObject testResults = (JsonObject) obj.get("testResults");
    variantBuilder
        .testSuccessRate(testResults.get("successRate").getAsDouble())
        .executedTestsCount(testResults.get("executedTestsCount").getAsInt())
        .testResults(parseTestResults(testResults.get("testResults")));

    variantBuilder
        .fitness(obj.get("fitness").getAsDouble())
        .selectionCount(obj.get("selectionCount").getAsInt());

    return variantBuilder.build();
  }

  private Map<TestFullyQualifiedName, Boolean> parseTestResults(final JsonElement je) {
    final Map<TestFullyQualifiedName, Boolean> testResults = new HashMap<>();
    for (final JsonElement testResultJE : je.getAsJsonArray()) {
      final JsonObject testResultJO = testResultJE.getAsJsonObject();
      final TestFullyQualifiedName fqn =
          new TestFullyQualifiedName(testResultJO.get("fqn").getAsString());
      final Boolean isSuccess = testResultJO.get("isSuccess").getAsBoolean();
      testResults.put(fqn, isSuccess);
    }
    return testResults;
  }
}
