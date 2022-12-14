package io.github.hrtwt.crossover.tester;

public class CrossoverSummary {

  public final String crossoverType;
  public final long makeChildrenCount;
  public final long syntaxValidChildrenCount;
  public final long buildSuccessChildrenCount;
  public final long dominateAllParentsCount;
  public final long dominatedByAllParentsCount;
  public final long equalAllParentsCount;
  public final long complementaryAllParentsCount;

  public final long dominateDominatedCount;
  public final long dominateComplementaryCount;
  public final long dominatedComplementaryCount;
  public final long dominateEqualCount;
  public final long dominatedEqualCount;
  public final long complementaryEqualCount;

  public CrossoverSummary(final CrossoverResults results) {
    crossoverType = results.getCrossoverType().name();
    makeChildrenCount = results.getMakeChildrenCount();
    syntaxValidChildrenCount = results.getSyntaxValidChildrenCount();
    buildSuccessChildrenCount = results.getBuildSuccessChildrenCount();
    dominateAllParentsCount = results.getDominateAllParentsCount();
    dominatedByAllParentsCount = results.getDominatedByAllParentsCount();
    complementaryAllParentsCount = results.getComplementaryAllParentsCount();
    equalAllParentsCount = results.getEqualAllParentsCount();
    dominateDominatedCount = results.getDominateDominatedCount();
    dominateComplementaryCount = results.getDominateComplementaryCount();
    dominatedComplementaryCount = results.getDominatedComplementaryCount();
    dominateEqualCount = results.getDominateEqualCount();
    dominatedEqualCount = results.getDominatedEqualCount();
    complementaryEqualCount = results.getComplementaryEqualCount();
  }
}
