package io.github.hrtwt.crossover.tester;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import io.github.hrtwt.crossover.tester.kgp.CrossoverType;
import jp.kusumotolab.kgenprog.ga.variant.Variant;
import jp.kusumotolab.kgenprog.project.test.EmptyTestResults;

public class CrossoverResults {
  public final CrossoverType crossoverType;
  public final List<Variant> parents;
  public final List<VariantWithDominance> children = new ArrayList<>();
  private long makeChildrenCount = -1;
  private long syntaxValidChildrenCount = -1;
  private long buildSuccessChildrenCount = -1;
  private long dominateAllParentsCount = -1;
  private long dominatedByAllParentsCount = -1;
  private long complementaryAllParentsCount = -1;
  private long equalAllParentsCount = -1;

  private long dominateDominatedCount = -1;
  private long dominateComplementaryCount = -1;
  private long dominatedComplementaryCount = -1;
  private long dominateEqualCount = -1;
  private long dominatedEqualCount = -1;
  private long complementaryEqualCount = -1;

  public CrossoverResults(final CrossoverType type, final List<Variant> parents) {
    this.crossoverType = type;
    this.parents = parents;
  }

  public void addChild(final Variant child, final List<Variant> parents) {
    final VariantWithDominance v = new VariantWithDominance(child, parents);
    children.add(v);
    updateCounts();
  }

  public void updateCounts() {
    makeChildrenCount = children.size();

    syntaxValidChildrenCount =
        children.stream().filter(VariantWithDominance::isSyntaxValid).count();

    buildSuccessChildrenCount =
        children.stream().filter(VariantWithDominance::isBuildSuccess).count();

    final List<VariantWithDominance> buildableChildren =
        children.stream().filter(VariantWithDominance::isBuildSuccess).collect(Collectors.toList());

    dominateAllParentsCount =
        buildableChildren.stream().filter(VariantWithDominance::isDominateAllParents).count();

    dominatedByAllParentsCount =
        buildableChildren.stream().filter(VariantWithDominance::isDominatedByAllParents).count();

    complementaryAllParentsCount =
        buildableChildren.stream().filter(VariantWithDominance::isComplementaryAllParents).count();

    equalAllParentsCount =
        buildableChildren.stream().filter(VariantWithDominance::isEqualAllParents).count();

    dominateDominatedCount =
        buildableChildren.stream()
            .filter(c -> c.isDominateParents() && c.isDominatedByParents())
            .count();

    dominateComplementaryCount =
        buildableChildren.stream()
            .filter(c -> c.isDominateParents() && c.isComplementaryParents())
            .count();

    dominatedComplementaryCount =
        buildableChildren.stream()
            .filter(c -> c.isDominatedByParents() && c.isComplementaryParents())
            .count();

    dominateEqualCount =
        buildableChildren.stream().filter(c -> c.isDominateParents() && c.isEqualParents()).count();

    dominatedEqualCount =
        buildableChildren.stream()
            .filter(c -> c.isDominatedByParents() && c.isEqualParents())
            .count();

    complementaryEqualCount =
        buildableChildren.stream()
            .filter(c -> c.isComplementaryParents() && c.isEqualParents())
            .count();
  }

  public long getMakeChildrenCount() {
    return makeChildrenCount;
  }

  public long getSyntaxValidChildrenCount() {
    return syntaxValidChildrenCount;
  }

  public long getBuildSuccessChildrenCount() {
    return buildSuccessChildrenCount;
  }

  public long getDominateAllParentsCount() {
    return dominateAllParentsCount;
  }

  public long getDominatedByAllParentsCount() {
    return dominatedByAllParentsCount;
  }

  public long getComplementaryAllParentsCount() {
    return complementaryAllParentsCount;
  }

  public long getEqualAllParentsCount() {
    return equalAllParentsCount;
  }

  public long getDominateDominatedCount() {
    return dominateDominatedCount;
  }

  public long getDominateComplementaryCount() {
    return dominateComplementaryCount;
  }

  public long getDominatedComplementaryCount() {
    return dominatedComplementaryCount;
  }

  public long getDominateEqualCount() {
    return dominateEqualCount;
  }

  public long getDominatedEqualCount() {
    return dominatedEqualCount;
  }

  public long getComplementaryEqualCount() {
    return complementaryEqualCount;
  }

  public static class VariantWithDominance {
    public final Variant child;
    public final String failedCause;
    public final List<VariantRelation> parentRelation;
    public final boolean isBuildSuccess;
    public final boolean isSyntaxValid;

    public final long numberOfLines;

    public VariantWithDominance(final Variant child, final List<Variant> parents) {
      this.child = child;

      if (child.getTestResults() instanceof EmptyTestResults) {
        failedCause = ((EmptyTestResults) child.getTestResults()).getCause();
      } else {
        failedCause = "";
      }

      this.isBuildSuccess = child.isBuildSucceeded();
      this.isSyntaxValid = child.isSyntaxValid();

      if (isBuildSuccess) {
        this.parentRelation =
            parents.stream()
                .map(p -> VariantRelation.compare(child, p))
                .collect(Collectors.toList());

        numberOfLines = child.getGeneratedSourceCode().getProductAsts().get(0).getNumberOfLines();
      } else {
        this.parentRelation = Collections.emptyList();
        numberOfLines = -1;
      }
    }

    public boolean isDominateParents() {
      return parentRelation.contains(VariantRelation.DOMINATE);
    }

    public boolean isDominatedByParents() {
      return parentRelation.contains(VariantRelation.DOMINATED);
    }

    public boolean isDominatedByAllParents() {
      return VariantRelation.isDominatedAll(parentRelation);
    }

    public boolean isDominateAllParents() {
      return VariantRelation.isDominateAll(parentRelation);
    }

    public boolean isComplementaryParents() {
      return parentRelation.contains(VariantRelation.COMPLEMENT);
    }

    public boolean isComplementaryAllParents() {
      return VariantRelation.isComplementAll(parentRelation);
    }

    public boolean isEqualAllParents() {
      return VariantRelation.isEqualAll(parentRelation);
    }

    public boolean isEqualParents() {
      return parentRelation.contains(VariantRelation.EQUAL);
    }

    public boolean isBuildSuccess() {
      return isBuildSuccess;
    }

    public boolean isSyntaxValid() {
      return isSyntaxValid;
    }
  }
}
