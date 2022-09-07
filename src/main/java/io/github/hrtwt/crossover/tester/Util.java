package io.github.hrtwt.crossover.tester;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Random;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import jp.kusumotolab.kgenprog.Configuration;
import jp.kusumotolab.kgenprog.Strategies;
import jp.kusumotolab.kgenprog.fl.Ochiai;
import jp.kusumotolab.kgenprog.ga.codegeneration.DefaultSourceCodeGeneration;
import jp.kusumotolab.kgenprog.ga.selection.DefaultVariantSelection;
import jp.kusumotolab.kgenprog.ga.validation.MultiObjectiveFitness;
import jp.kusumotolab.kgenprog.ga.validation.MultiObjectiveValidation;
import jp.kusumotolab.kgenprog.ga.variant.Variant;
import jp.kusumotolab.kgenprog.ga.variant.VariantStore;
import jp.kusumotolab.kgenprog.project.factory.TargetProject;
import jp.kusumotolab.kgenprog.project.factory.TargetProjectFactory;
import jp.kusumotolab.kgenprog.project.jdt.JDTASTConstruction;
import jp.kusumotolab.kgenprog.project.test.LocalTestExecutor;

public class Util {

  public static VariantStore createVariantStore(final Path projectPath) {
    final TargetProject project = TargetProjectFactory.create(projectPath);
    final Configuration config = new Configuration.Builder(project).build();
    final VariantStore store = createVariantStoreWithDefaultStrategies(config);
    return store;
  }

  public static VariantStore createVariantStoreWithDefaultStrategies(final Configuration config) {
    final Strategies strategies = createDefaultStrategies(config);
    return new VariantStore(config, strategies);
  }

  public static Strategies createDefaultStrategies(final Configuration config) {
    return new Strategies(
        new Ochiai(),
        new JDTASTConstruction(),
        new DefaultSourceCodeGeneration(),
        new MultiObjectiveValidation(),
        new LocalTestExecutor(config),
        new DefaultVariantSelection(10, new Random(0)));
  }

  public static String readString(final Path path) {
    try {
      return Files.readString(path);
    } catch (final IOException e) {
      e.printStackTrace();
      System.exit(1);
      return null;
    }
  }

  public static String formatJson(final String json) {
    return new GsonBuilder().setPrettyPrinting().create().toJson(JsonParser.parseString(json));
  }

  /** returns true if the child dominates all parents. */
  public static boolean isDominateAllParents(
      final Collection<Variant> parents, final Variant child) {
    // In order to check the dominance, we must use MultiObjectiveFitness
    final MultiObjectiveFitness childFitness = (MultiObjectiveFitness) child.getFitness();
    for (final Variant parent : parents) {
      final MultiObjectiveFitness parentFitness = (MultiObjectiveFitness) parent.getFitness();
      if (childFitness.compareTo(parentFitness) <= 0) {
        // if child is dominated by parents (in other words, parent is better than child)
        // or if there are no dominate relationships between child and parent
        return false;
      }
    }
    return true;
  }

  public static boolean isDominatedByAllParents(
      final Collection<Variant> parents, final Variant child) {
    final MultiObjectiveFitness childFitness = (MultiObjectiveFitness) child.getFitness();
    for (final Variant parent : parents) {
      final MultiObjectiveFitness parentFitness = (MultiObjectiveFitness) parent.getFitness();
      if (childFitness.compareTo(parentFitness) >= 0) {
        return false;
      }
    }
    return true;
  }

  public static boolean isDominateParents(final Collection<Variant> parents, final Variant child) {
    final MultiObjectiveFitness childFitness = (MultiObjectiveFitness) child.getFitness();
    for (final Variant parent : parents) {
      final MultiObjectiveFitness parentFitness = (MultiObjectiveFitness) parent.getFitness();
      if (childFitness.compareTo(parentFitness) > 0) {
        return true;
      }
    }
    return false;
  }

  public static boolean isDominatedByParents(
      final Collection<Variant> parents, final Variant child) {
    final MultiObjectiveFitness childFitness = (MultiObjectiveFitness) child.getFitness();
    for (final Variant parent : parents) {
      final MultiObjectiveFitness parentFitness = (MultiObjectiveFitness) parent.getFitness();
      if (childFitness.compareTo(parentFitness) < 0) {
        return true;
      }
    }
    return false;
  }
}
