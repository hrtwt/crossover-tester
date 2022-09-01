package io.github.hrtwt.crossover.tester;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import jp.kusumotolab.kgenprog.Configuration;
import jp.kusumotolab.kgenprog.Strategies;
import jp.kusumotolab.kgenprog.fl.Ochiai;
import jp.kusumotolab.kgenprog.ga.codegeneration.DefaultSourceCodeGeneration;
import jp.kusumotolab.kgenprog.ga.selection.DefaultVariantSelection;
import jp.kusumotolab.kgenprog.ga.validation.MultiObjectiveValidation;
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
}
