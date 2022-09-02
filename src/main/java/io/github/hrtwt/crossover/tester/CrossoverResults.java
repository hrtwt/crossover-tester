package io.github.hrtwt.crossover.tester;

import java.util.ArrayList;
import java.util.List;
import io.github.hrtwt.crossover.tester.kgp.CrossoverType;
import jp.kusumotolab.kgenprog.ga.variant.Variant;
import lombok.RequiredArgsConstructor;

public class CrossoverResults {
  public final CrossoverType crossoverType;
  public final List<Variant> parents;
  public final List<VariantWithDominance> children = new ArrayList<>();
  private long dominateChildrenCount = -1;
  private long buildSuccessChildrenCount = -1;
  private long syntaxValidChildrenCount = -1;

  public CrossoverResults(final CrossoverType type, final List<Variant> parents) {
    this.crossoverType = type;
    this.parents = parents;
  }

  public void addChild(final Variant child, final boolean isDominateParents) {
    final VariantWithDominance v = new VariantWithDominance(child, isDominateParents);
    children.add(v);
    updateCounts();
  }

  public void updateCounts() {
    updateDominateChildrenCount();
    updateBuildSuccessChildrenCount();
    updateSyntaxValidChildrenCount();
  }

  private void updateDominateChildrenCount() {
    dominateChildrenCount =
        children.stream().filter(VariantWithDominance::isDominateParents).count();
  }

  private void updateBuildSuccessChildrenCount() {
    buildSuccessChildrenCount =
        children.stream().filter(VariantWithDominance::isBuildSuccess).count();
  }

  private void updateSyntaxValidChildrenCount() {
    syntaxValidChildrenCount =
        children.stream().filter(VariantWithDominance::isSyntaxValid).count();
  }

  public long getDominateChildrenCount() {
    return dominateChildrenCount;
  }

  public long getBuildSuccessChildrenCount() {
    return buildSuccessChildrenCount;
  }

  public long getSyntaxValidChildrenCount() {
    return syntaxValidChildrenCount;
  }

  @RequiredArgsConstructor
  public static class VariantWithDominance {
    public final Variant child;
    public final boolean isDominateParents;

    public boolean isDominateParents() {
      return isDominateParents;
    }

    public boolean isBuildSuccess() {
      return child.isBuildSucceeded();
    }

    public boolean isSyntaxValid() {
      return child.isSyntaxValid();
    }
  }
}
