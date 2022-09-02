package io.github.hrtwt.crossover.tester;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import com.google.gson.Gson;
import io.github.hrtwt.crossover.tester.json.JsonVariant;
import io.github.hrtwt.crossover.tester.json.JsonVariantParser;
import io.github.hrtwt.crossover.tester.kgp.CrossoverType;
import jp.kusumotolab.kgenprog.ga.validation.MultiObjectiveFitness;
import jp.kusumotolab.kgenprog.ga.variant.Variant;
import jp.kusumotolab.kgenprog.ga.variant.VariantStore;
import jp.kusumotolab.kgenprog.output.JSONExporter;

public class CrossoverTester implements Runnable {

  private final Path projectRootPath;
  private final int randomSeed;

  private final String json;
  private final List<Variant> parents = new ArrayList<>();
  private final int generatingVariants;

  public CrossoverTester(
      final Path projectRootPath, final String json, final int generatingVariants, final int seed) {
    this.projectRootPath = projectRootPath;
    this.randomSeed = seed;
    this.json = json;
    this.generatingVariants = generatingVariants;
  }

  /** returns true if the child dominates all parents. */
  public static boolean isDominateParents(final Collection<Variant> parents, final Variant child) {
    // In order to check the dominance, we must use MultiObjectiveFitness
    final MultiObjectiveFitness childFitness = (MultiObjectiveFitness) child.getFitness();
    for (final Variant parent : parents) {
      final MultiObjectiveFitness parentFitness = (MultiObjectiveFitness) parent.getFitness();
      if (childFitness.compareTo(parentFitness) <= 0) {
        // if child is dominated by parents (in other words, parent is better than child)
        // or if there are no dominate relationships between child and parent
        return false;
      }
    }
    return true;
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

    final CrossoverResults results = new CrossoverResults(type, List.of(v1, v2));

    children
        .subList(0, this.generatingVariants)
        .forEach(v -> results.addChild(v, isDominateParents(List.of(v1, v2), v)));

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

  private String toJson(List<CrossoverResults> results) {
    final Gson gson = JSONExporter.setupGson();

    return gson.toJson(results);
  }

  @Override
  public void run() {
    final List<JsonVariant> jsonVariants = JsonVariantParser.parseComplementaryVariants(json);
    parents.addAll(makeVariant(jsonVariants));

    final List<CrossoverResults> results = execCrossover(List.of(CrossoverType.values()));

    System.out.println(toJson(results));
  }
}
