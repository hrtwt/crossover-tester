package io.github.hrtwt.crossover.tester.kgp;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.apache.commons.lang3.reflect.MethodUtils;
import jp.kusumotolab.kgenprog.ga.crossover.Crossover;
import jp.kusumotolab.kgenprog.ga.crossover.Crossover.Type;
import jp.kusumotolab.kgenprog.ga.crossover.FirstVariantRandomSelection;
import jp.kusumotolab.kgenprog.ga.crossover.LinkageCrossover;
import jp.kusumotolab.kgenprog.ga.crossover.SecondVariantRandomSelection;
import jp.kusumotolab.kgenprog.ga.validation.MultiObjectiveFitness;
import jp.kusumotolab.kgenprog.ga.variant.Variant;
import jp.kusumotolab.kgenprog.ga.variant.VariantStore;
import jp.kusumotolab.kgenprog.project.GenerationFailedSourceCode;
import jp.kusumotolab.kgenprog.project.test.EmptyTestResults;
import jp.kusumotolab.kgenprog.project.test.TestResults;

public enum CrossoverType {
  Random(Type.Random) {},
  SinglePoint(Type.SinglePoint) {},
  Uniform(Type.Uniform) {},
  Cascade(Type.Cascade) {},
  Linkage(Type.Linkage) {
    @Override
    public List<Variant> makeVariants(
        final Variant v1, final Variant v2, final Random random, final VariantStore vs) {
      final LinkageCrossover co = (LinkageCrossover) this.newInstance(random);
      try {
        return (List<Variant>) MethodUtils.invokeMethod(co, true, "makeVariant", v1, v2, vs);
      } catch (final ReflectiveOperationException e) {
        throw new IllegalStateException("can not crossover with" + this.name(), e);
      }
    }
  };

  private final Crossover.Type kgpCrossoverType;

  CrossoverType(final Crossover.Type type) {
    this.kgpCrossoverType = type;
  }

  public static List<Crossover> newInstances(final int seed) {
    return Arrays.stream(CrossoverType.values())
        .map(co -> co.newInstance(new Random(seed)))
        .collect(Collectors.toList());
  }

  public List<Variant> makeVariants(
      final Variant v1, final Variant v2, Random random, final VariantStore vs) {
    final Crossover co = this.newInstance(random);
    try {
      return (List<Variant>) MethodUtils.invokeMethod(co, true, "makeVariants", List.of(v1, v2), vs);
    } catch (final ReflectiveOperationException e) {
      final String errMsg = "can not crossover with" + this.name() + ": " + e.getMessage();
      final TestResults tr = new EmptyTestResults(errMsg);
      final Variant emptyVariant =
          new Variant(
              -1,
              -1,
              null,
              new GenerationFailedSourceCode(errMsg),
              tr,
              new MultiObjectiveFitness(tr),
              null,
              null);
      return List.of(emptyVariant);
    }
  }

  public Crossover newInstance(final Random random) {
    return kgpCrossoverType.initialize(
        random,
        new FirstVariantRandomSelection(random),
        new SecondVariantRandomSelection(random),
        10, // number of variants per generation
        Integer.MAX_VALUE); // number of required solution
  }
}
