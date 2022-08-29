package io.github.hrtwt.crossover.tester.json;

import java.lang.reflect.Type;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import io.github.hrtwt.crossover.tester.json.JsonVariant.JsonBase;
import io.github.hrtwt.crossover.tester.json.JsonVariant.JsonBase.JsonBaseBuilder;
import io.github.hrtwt.crossover.tester.kgp.OperationType;

public class JsonBaseDeserializer implements JsonDeserializer<JsonVariant.JsonBase> {

  @Override
  public JsonBase deserialize(
      final JsonElement json, final Type type, final JsonDeserializationContext context)
      throws JsonParseException {
    final JsonObject obj = (JsonObject) json;

    final JsonBaseBuilder baseBuilder =
        JsonBase.builder()
            .operationType(OperationType.getByName(obj.get("name").getAsString()))
            .sourcePath(obj.get("fileName").getAsString())
            .snippet(obj.get("snippet").getAsString());

    final JsonObject lineRange = (JsonObject) obj.get("lineNumberRange");
    baseBuilder
        .startLineNo(lineRange.get("start").getAsInt())
        .endLineNo(lineRange.get("end").getAsInt())
        .baseIdentityHashCode(obj.get("identityHashCode").getAsInt());
    return baseBuilder.build();
  }
}
