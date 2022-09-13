package io.github.hrtwt.crossover.tester;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import io.github.hrtwt.crossover.tester.kgp.CrossoverType;
import jp.kusumotolab.kgenprog.ga.variant.Variant;
import jp.kusumotolab.kgenprog.project.test.EmptyTestResults;

public class CrossoverResults {
  public final CrossoverType crossoverType;
  public final List<Variant> parents;
  public final List<VariantWithDominance> children = new ArrayList<>();
  private long dominateAllParentsCount = -1;
  private long dominateParentsCount = -1;
  private long dominatedByParentsCount = -1;
  private long dominatedByAllParentsCount = -1;

  private long complementaryParentsCount = -1;
  private long complementaryAllParentsCount = -1;
  private long buildSuccessChildrenCount = -1;
  private long syntaxValidChildrenCount = -1;
  private long makeChildrenCount = -1;
  private long buildFailedChildrenCount = -1;

  public CrossoverResults(final CrossoverType type, final List<Variant> parents) {
    this.crossoverType = type;
    this.parents = parents;
  }

  public void addChild(final Variant child, final Collection<Variant> parents) {
    final VariantWithDominance v = new VariantWithDominance(child, parents);
    children.add(v);
    updateCounts();
  }

  public void updateCounts() {
    updateDominateAllParentsCount();
    updateDominateParentsCount();
    updateDominatedByParentsCount();
    updateDominatedByAllParentsCount();

    updateComplementaryParentsCount();
    updateComplementaryAllParentsCount();

    updateMakeChildrenCount();
    updateBuildSuccessChildrenCount();
    updateBuildFailedChildrenCount();
    updateSyntaxValidChildrenCount();
  }

  private void updateDominateAllParentsCount() {
    dominateAllParentsCount =
        children.stream()
            .filter(VariantWithDominance::isBuildSuccess)
            .filter(VariantWithDominance::isDominateAllParents)
            .count();
  }

  private void updateDominateParentsCount() {
    dominateParentsCount =
        children.stream()
            .filter(VariantWithDominance::isBuildSuccess)
            .filter(VariantWithDominance::isDominateParents)
            .count();
  }

  private void updateDominatedByParentsCount() {
    dominatedByParentsCount =
        children.stream()
            .filter(VariantWithDominance::isBuildSuccess)
            .filter(VariantWithDominance::isDominatedByParents)
            .count();
  }

  private void updateDominatedByAllParentsCount() {
    dominatedByAllParentsCount =
        children.stream()
            .filter(VariantWithDominance::isBuildSuccess)
            .filter(VariantWithDominance::isDominatedByAllParents)
            .count();
  }

  private void updateComplementaryParentsCount() {
    complementaryParentsCount =
        children.stream()
            .filter(VariantWithDominance::isBuildSuccess)
            .filter(VariantWithDominance::isComplementaryParents)
            .count();
  }

  private void updateComplementaryAllParentsCount() {
    complementaryAllParentsCount =
        children.stream()
            .filter(VariantWithDominance::isBuildSuccess)
            .filter(VariantWithDominance::isComplementaryAllParents)
            .count();
  }

  private void updateBuildSuccessChildrenCount() {
    buildSuccessChildrenCount =
        children.stream().filter(VariantWithDominance::isBuildSuccess).count();
  }

  private void updateSyntaxValidChildrenCount() {
    syntaxValidChildrenCount =
        children.stream().filter(VariantWithDominance::isSyntaxValid).count();
  }

  private void updateMakeChildrenCount() {
    makeChildrenCount = children.size();
  }

  private void updateBuildFailedChildrenCount() {
    buildFailedChildrenCount =
        children.stream().filter(Predicate.not(VariantWithDominance::isBuildSuccess)).count();
  }

  public long getDominateAllParentsCount() {
    return dominateAllParentsCount;
  }

  public long getDominateParentsCount() {
    return dominateParentsCount;
  }

  public long getDominatedByParentsCount() {
    return dominatedByParentsCount;
  }

  public long getDominatedByAllParentsCount() {
    return dominatedByAllParentsCount;
  }

  public long getComplementaryParentsCount() {
    return complementaryParentsCount;
  }

  public long getComplementaryAllParentsCount() {
    return complementaryAllParentsCount;
  }

  public long getBuildSuccessChildrenCount() {
    return buildSuccessChildrenCount;
  }

  public long getSyntaxValidChildrenCount() {
    return syntaxValidChildrenCount;
  }

  public long getMakeChildrenCount() {
    return makeChildrenCount;
  }

  public long getBuildFailedChildrenCount() {
    return buildFailedChildrenCount;
  }

  public static class VariantWithDominance {
    public final Variant child;
    public final String failedCause;
    public final boolean isDominateAllParents;
    public final boolean isDominateParents;
    public final boolean isDominatedByParents;
    public final boolean isDominatedByAllParents;
    public final boolean isComplementaryParents;
    public final boolean isComplementaryAllParents;

    public final boolean isBuildSuccess;
    public final boolean isSyntaxValid;

    public final long numberOfLines;

    public VariantWithDominance(final Variant child, final Collection<Variant> parents) {
      this.child = child;
      if (child.getTestResults() instanceof EmptyTestResults) {
        failedCause = ((EmptyTestResults) child.getTestResults()).getCause();
      } else {
        failedCause = "";
      }
      this.isDominateAllParents = Util.isDominateAllParents(parents, child);
      this.isDominateParents = Util.isDominateParents(parents, child);
      this.isDominatedByParents = Util.isDominatedByParents(parents, child);
      this.isDominatedByAllParents = Util.isDominatedByAllParents(parents, child);
      this.isComplementaryParents = Util.isComplementaryParents(parents, child);
      this.isComplementaryAllParents = Util.isComplementaryAllParents(parents, child);
      this.isBuildSuccess = child.isBuildSucceeded();
      this.isSyntaxValid = child.isSyntaxValid();
      if (isBuildSuccess) {
        numberOfLines = child.getGeneratedSourceCode().getProductAsts().get(0).getNumberOfLines();
      } else {
        numberOfLines = -1;
      }
    }

    public boolean isDominateParents() {
      return isDominateParents;
    }

    public boolean isDominatedByParents() {
      return isDominatedByParents;
    }

    public boolean isDominatedByAllParents() {
      return isDominatedByAllParents;
    }

    public boolean isDominateAllParents() {
      return isDominateAllParents;
    }

    public boolean isComplementaryParents() {
      return isComplementaryParents;
    }

    public boolean isComplementaryAllParents() {
      return isComplementaryAllParents;
    }

    public boolean isBuildSuccess() {
      return isBuildSuccess;
    }

    public boolean isSyntaxValid() {
      return isSyntaxValid;
    }
  }
}
