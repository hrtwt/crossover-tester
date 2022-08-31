package io.github.hrtwt.crossover.tester;

import java.util.List;
import io.github.hrtwt.crossover.tester.kgp.CrossoverType;
import jp.kusumotolab.kgenprog.ga.variant.Variant;
import lombok.Builder;

@Builder
public class CrossoverResult {
  public final Variant child;
  public final List<Variant> parents;
  public final CrossoverType crossoverType;
  public final boolean isDominateParents;
}
