package io.github.hrtwt.crossover.tester;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import io.github.hrtwt.crossover.tester.kgp.CrossoverType;
import jp.kusumotolab.kgenprog.ga.variant.Variant;

public class CrossoverResults {
  public final CrossoverType crossoverType;
  public final List<Variant> parents;
  public final List<VariantWithDominance> children = new ArrayList<>();
  private long dominateAllParentsCount = -1;
  private long dominateParentsCount = -1;
  private long dominatedByParentsCount = -1;
  private long dominatedByAllParentsCount = -1;
  private long buildSuccessChildrenCount = -1;
  private long syntaxValidChildrenCount = -1;

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

    updateBuildSuccessChildrenCount();
    updateSyntaxValidChildrenCount();
  }

  private void updateDominateAllParentsCount() {
    dominateAllParentsCount =
        children.stream().filter(VariantWithDominance::isDominateAllParents).count();
  }

  private void updateDominateParentsCount() {
    dominateParentsCount =
        children.stream().filter(VariantWithDominance::isDominateParents).count();
  }

  private void updateDominatedByParentsCount() {
    dominatedByParentsCount =
        children.stream().filter(VariantWithDominance::isDominatedByParents).count();
  }

  private void updateDominatedByAllParentsCount() {
    dominatedByAllParentsCount =
        children.stream().filter(VariantWithDominance::isDominatedByAllParents).count();
  }

  private void updateBuildSuccessChildrenCount() {
    buildSuccessChildrenCount =
        children.stream().filter(VariantWithDominance::isBuildSuccess).count();
  }

  private void updateSyntaxValidChildrenCount() {
    syntaxValidChildrenCount =
        children.stream().filter(VariantWithDominance::isSyntaxValid).count();
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

  public long getBuildSuccessChildrenCount() {
    return buildSuccessChildrenCount;
  }

  public long getSyntaxValidChildrenCount() {
    return syntaxValidChildrenCount;
  }

  public static class VariantWithDominance {
    public final Variant child;
    public final boolean isDominateAllParents;
    public final boolean isDominateParents;
    public final boolean isDominatedByParents;
    public final boolean isDominatedByAllParents;

    public VariantWithDominance(final Variant child, final Collection<Variant> parents) {
      this.child = child;
      this.isDominateAllParents = Util.isDominateAllParents(parents, child);
      this.isDominateParents = Util.isDominateParents(parents, child);
      this.isDominatedByParents = Util.isDominatedByParents(parents, child);
      this.isDominatedByAllParents = Util.isDominatedByAllParents(parents, child);
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

    public boolean isBuildSuccess() {
      return child.isBuildSucceeded();
    }

    public boolean isSyntaxValid() {
      return child.isSyntaxValid();
    }
  }
}
