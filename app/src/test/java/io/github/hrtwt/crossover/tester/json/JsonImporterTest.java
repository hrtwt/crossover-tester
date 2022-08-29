package io.github.hrtwt.crossover.tester.json;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Test;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JsonImporterTest {

  @Test
  public void testImport() throws Exception {
    final String json = Files.readString(Paths.get("../example/ABC139_A/variants.json"));
    final JsonObject variants = new Gson().fromJson(json, JsonObject.class);
    // not throws any exception
    final JsonVariant adam = JsonImporter.importVariant(variants.get("adam"));

    final JsonArray parents = variants.get("parents").getAsJsonArray();

    final JsonVariant parent1 = JsonImporter.importVariant(parents.get(0));
    final JsonVariant parent2 = JsonImporter.importVariant(parents.get(1));
  }
}
