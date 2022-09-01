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
import jp.kusumotolab.kgenprog.ga.validation.Fitness;
import jp.kusumotolab.kgenprog.ga.variant.Variant;
import jp.kusumotolab.kgenprog.ga.variant.VariantStore;
import jp.kusumotolab.kgenprog.output.JSONExporter;

public class CrossoverTester implements Runnable {

  private final Path projectRootPath;
  private final int randomSeed;

  private final String json;

  private final List<Variant> parents = new ArrayList<>();

  public CrossoverTester(final Path projectRootPath, final String json, final int seed) {
    this.projectRootPath = projectRootPath;
    this.randomSeed = seed;
    this.json = json;
  }

  /** returns true if the child dominates all parents. */
  public static boolean isDominateParents(final Collection<Variant> parents, final Variant child) {
    final Fitness childFitness = child.getFitness();
    for (final Variant parent : parents) {
      final Fitness parentFitness = parent.getFitness();
      if (childFitness.compareTo(parentFitness) <= 0) {
        // if child is dominated by parents (in other words, parent is better than child)
        // or if there are no dominate relationships between child and parent
        return false;
      }
    }
    return true;
  }

  public List<CrossoverResult> execCrossover(
      final Variant v1, final Variant v2, final Collection<CrossoverType> types) {
    return types.stream()
        .map(type -> execCrossover(v1, v2, type))
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }

  public List<CrossoverResult> execCrossover(
      final Variant v1, final Variant v2, final CrossoverType type) {
    final VariantStore variantStore = createInitialVariantStore();
    final List<Variant> children = type.makeVariants(v1, v2, new Random(randomSeed), variantStore);

    final List<Variant> parents = List.of(v1, v2);

    return children.stream()
        .map(v -> new CrossoverResult(v, parents, type, isDominateParents(parents, v)))
        .collect(Collectors.toList());
  }

  private VariantStore createInitialVariantStore() {
    return Util.createVariantStore(projectRootPath);
  }

  private String toJson(List<CrossoverResult> results) {
    final Gson gson = JSONExporter.setupGson();

    return gson.toJson(results);
  }

  @Override
  public void run() {
    final List<JsonVariant> jsonVariants = JsonVariantParser.parseComplementaryVariants(json);

    final Variant parent1 =
        VariantBuilder.makeVariant(jsonVariants.get(0), createInitialVariantStore());
    final Variant parent2 =
        VariantBuilder.makeVariant(jsonVariants.get(1), createInitialVariantStore());

    final List<CrossoverResult> results =
        execCrossover(parent1, parent2, List.of(CrossoverType.values()));

    System.out.println(toJson(results));
  }
}
