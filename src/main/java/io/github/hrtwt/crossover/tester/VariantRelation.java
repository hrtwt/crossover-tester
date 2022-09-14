package io.github.hrtwt.crossover.tester;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import jp.kusumotolab.kgenprog.ga.variant.Variant;
import jp.kusumotolab.kgenprog.project.FullyQualifiedName;

public enum VariantRelation {
  DOMINATE,
  DOMINATED,
  EQUAL,
  COMPLEMENT;

  public static VariantRelation compare(final Variant one, final Variant another) {
    final Set<FullyQualifiedName> oneSucceededTests =
        new HashSet<>(one.getTestResults().getSucceededTestFQNs());
    final Set<FullyQualifiedName> anotherSucceededTests =
        new HashSet<>(another.getTestResults().getSucceededTestFQNs());

    boolean isDominant = oneSucceededTests.containsAll(anotherSucceededTests);
    boolean isDominated = anotherSucceededTests.containsAll(oneSucceededTests);

    if (isDominant && isDominated) {
      return EQUAL;
    }
    if (isDominant) {
      return DOMINATE;
    }
    if (isDominated) {
      return DOMINATED;
    }
    return COMPLEMENT;
  }

  public static boolean isDominateAll(final Collection<VariantRelation> relations) {
    return !relations.isEmpty() && relations.stream().noneMatch(r -> r != DOMINATE);
  }

  public static boolean isDominatedAll(final Collection<VariantRelation> relations) {
    return !relations.isEmpty() && relations.stream().noneMatch(r -> r != DOMINATED);
  }

  public static boolean isEqualAll(final Collection<VariantRelation> relations) {
    return !relations.isEmpty() && relations.stream().noneMatch(r -> r != EQUAL);
  }

  public static boolean isComplementAll(final Collection<VariantRelation> relations) {
    return !relations.isEmpty() && relations.stream().noneMatch(r -> r != COMPLEMENT);
  }
}
