package io.github.hrtwt.crossover.tester;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.hrtwt.crossover.tester.json.JsonVariant;
import io.github.hrtwt.crossover.tester.json.JsonVariantParser;
import io.github.hrtwt.crossover.tester.kgp.CrossoverType;
import jp.kusumotolab.kgenprog.ga.variant.Variant;
import jp.kusumotolab.kgenprog.ga.variant.VariantStore;

public class CrossoverTester implements Runnable {

  private final Path projectRootPath;
  private final int randomSeed;

  private final String inputJson;
  private final List<Variant> parents = new ArrayList<>();
  private final int generatingVariants;

  public CrossoverTester(
      final Path projectRootPath,
      final String inputJson,
      final int generatingVariants,
      final int seed) {
    this.projectRootPath = projectRootPath;
    this.randomSeed = seed;
    this.inputJson = inputJson;
    this.generatingVariants = generatingVariants;
  }

  public List<CrossoverResults> execCrossover(final Collection<CrossoverType> types) {
    return execCrossover(parents.get(0), parents.get(1), types);
  }

  public List<CrossoverResults> execCrossover(
      final Variant v1, final Variant v2, final Collection<CrossoverType> types) {
    return types.stream().map(type -> execCrossover(v1, v2, type)).collect(Collectors.toList());
  }

  public CrossoverResults execCrossover(
      final Variant v1, final Variant v2, final CrossoverType type) {
    final ArrayList<Variant> children = new ArrayList<>();
    final Random random = new Random(randomSeed); // reuse Random to generate various variants
    while (children.size() < this.generatingVariants) {
      final List<Variant> v = type.makeVariants(v1, v2, random, createInitialVariantStore());
      children.addAll(v);
    }

    final List<Variant> parents = List.of(v1, v2);

    final CrossoverResults results = new CrossoverResults(type, parents);

    children.subList(0, this.generatingVariants).forEach(v -> results.addChild(v, parents));

    return results;
  }

  private VariantStore createInitialVariantStore() {
    return Util.createVariantStore(projectRootPath);
  }

  private List<Variant> makeVariant(final Collection<JsonVariant> variants) {
    return variants.stream().map(this::makeVariant).collect(Collectors.toList());
  }

  private Variant makeVariant(final JsonVariant variant) {
    return VariantBuilder.makeVariant(variant, createInitialVariantStore());
  }

  private Gson retrieveGsonFromKGP() {
    Gson gson = null;
    try {
      Class<?> clazz = Class.forName("jp.kusumotolab.kgenprog.output.JSONExporter");

      Constructor<?> constructor = clazz.getDeclaredConstructor(Path.class);
      constructor.setAccessible(true);
      Object obj = constructor.newInstance(Path.of(""));

      Method method = clazz.getDeclaredMethod("setupGson");
      method.setAccessible(true);
      gson = (Gson) method.invoke(obj);
    } catch (final ReflectiveOperationException e) {
      e.printStackTrace();
    }

    return gson;
  }

  private String toJson(List<CrossoverResults> results) {
    final Gson gson = retrieveGsonFromKGP();
    final JsonObject ret = new JsonObject();

    ret.add("config", buildMetaInfoAsJson());

    final List<JsonElement> stats =
        results.stream().map(this::buildCrossoverStatsAsJson).collect(Collectors.toList());
    ret.add("stats", gson.toJsonTree(stats));

    ret.add("results", gson.toJsonTree(results));

    return gson.toJson(ret);
  }

  private JsonElement buildCrossoverStatsAsJson(final CrossoverResults results) {
    final JsonObject ret = new JsonObject();

    ret.addProperty("crossoverType", results.crossoverType.name());

    ret.addProperty("makeChildrenCount", results.getMakeChildrenCount());
    ret.addProperty("buildSuccessChildrenCount", results.getBuildSuccessChildrenCount());
    ret.addProperty("buildFailedChildrenCount", results.getBuildFailedChildrenCount());
    ret.addProperty("syntaxValidChildrenCount", results.getSyntaxValidChildrenCount());

    ret.addProperty("dominateAllParentsCount", results.getDominateAllParentsCount());
    ret.addProperty("dominateParentsCount", results.getDominateParentsCount());
    ret.addProperty("dominatedByParentsCount", results.getDominatedByParentsCount());
    ret.addProperty("dominatedByAllParentsCount", results.getDominatedByAllParentsCount());

    return ret;
  }

  private JsonElement buildMetaInfoAsJson() {
    final JsonObject ret = new JsonObject();

    ret.addProperty("projectRootPath", this.projectRootPath.toString());
    ret.addProperty("generatingVariants", this.generatingVariants);
    ret.addProperty("randomSeed", this.randomSeed);

    return ret;
  }

  @Override
  public void run() {
    final List<JsonVariant> jsonVariants = JsonVariantParser.parseComplementaryVariants(inputJson);
    parents.addAll(makeVariant(jsonVariants));

    final List<CrossoverResults> results = execCrossover(List.of(CrossoverType.values()));

    final String out = toJson(results);
    System.out.println(Util.formatJson(out));
  }
}
