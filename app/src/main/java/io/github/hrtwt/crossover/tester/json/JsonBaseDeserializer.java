package io.github.hrtwt.crossover.tester.json;

import java.lang.reflect.Type;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import io.github.hrtwt.crossover.tester.json.JsonVariant.JsonBase;
import io.github.hrtwt.crossover.tester.kgp.OperationType;

public class JsonBaseDeserializer implements JsonDeserializer<JsonVariant.JsonBase> {

  @Override
  public JsonBase deserialize(
      final JsonElement json, final Type type, final JsonDeserializationContext context)
      throws JsonParseException {
    final JsonObject obj = (JsonObject) json;
    final JsonBase jb = new JsonBase();

    jb.operationType = OperationType.getByName(obj.get("name").getAsString());
    jb.sourcePath = obj.get("fileName").getAsString();
    jb.snippet = obj.get("snippet").getAsString();
    final JsonObject lineRange = (JsonObject) obj.get("lineNumberRange");
    jb.startLineNo = lineRange.get("start").getAsInt();
    jb.endLineNo = lineRange.get("end").getAsInt();
    jb.baseIdentityHashCode = obj.get("identityHashCode").getAsInt();
    return jb;
  }
}
