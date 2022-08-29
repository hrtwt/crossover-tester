package io.github.hrtwt.crossover.tester.json;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.hrtwt.crossover.tester.json.JsonVariant.JsonBase;

public class JsonImporter {

  public static Gson setupGson() {
    return new GsonBuilder()
        .registerTypeAdapter(JsonVariant.class, new VariantDeserializer())
        .registerTypeAdapter(JsonBase.class, new JsonBaseDeserializer())
        .create();
  }

  public static JsonVariant importVariant(final String json) {
    return setupGson().fromJson(json, JsonVariant.class);
  }

  public static JsonVariant importVariant(final JsonElement json) {
    return setupGson().fromJson(json, JsonVariant.class);
  }

  public static JsonVariant importAdam(final String json) {
    final JsonObject variants = new Gson().fromJson(json, JsonObject.class);
    return JsonImporter.importVariant(variants.get("adam"));
  }

  public static List<JsonVariant> importComplementaryVariants(final String json) {
    final JsonObject variants = new Gson().fromJson(json, JsonObject.class);
    final JsonArray parents = variants.get("parents").getAsJsonArray();

    final List<JsonVariant> ret = new ArrayList<>();
    for (final JsonElement e : parents) {
      final JsonVariant parent = JsonImporter.importVariant(e);
      ret.add(parent);
    }
    return ret;
  }
}
