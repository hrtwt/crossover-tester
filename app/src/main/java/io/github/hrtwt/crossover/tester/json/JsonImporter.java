package io.github.hrtwt.crossover.tester.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
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
}
