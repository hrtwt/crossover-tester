package io.github.hrtwt.crossover.tester.kgp;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import jp.kusumotolab.kgenprog.ga.crossover.Crossover;
import jp.kusumotolab.kgenprog.ga.crossover.Crossover.Type;
import jp.kusumotolab.kgenprog.ga.crossover.FirstVariantRandomSelection;
import jp.kusumotolab.kgenprog.ga.crossover.LinkageCrossover;
import jp.kusumotolab.kgenprog.ga.crossover.SecondVariantRandomSelection;
import jp.kusumotolab.kgenprog.ga.variant.Variant;
import jp.kusumotolab.kgenprog.ga.variant.VariantStore;

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
        final Method execMethod =
            co.getClass()
                .getDeclaredMethod("makeVariant", Variant.class, Variant.class, VariantStore.class);
        execMethod.setAccessible(true);
        final Variant v = (Variant) execMethod.invoke(co, v1, v2, vs);
        return List.of(v);
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
      final Method execMethod =
          co.getClass().getDeclaredMethod("makeVariants", List.class, VariantStore.class);
      execMethod.setAccessible(true);
      final List<?> v = (List<?>) execMethod.invoke(co, List.of(v1, v2), vs);
      return (List<Variant>) v;
    } catch (final ReflectiveOperationException e) {
      throw new IllegalStateException("can not crossover with" + this.name(), e);
    }
  }

  public Crossover newInstance(final Random random) {
    return kgpCrossoverType.initialize(
        random,
        new FirstVariantRandomSelection(random),
        new SecondVariantRandomSelection(random),
        10,
        10);
  }
}
