package io.github.hrtwt.crossover.tester;

import java.nio.file.Path;
import java.nio.file.Paths;

public class App {
  public static void main(String[] args) {
    if (args.length != 4) {
      throw new IllegalArgumentException();
    }

    final Path kgpRootPath = Paths.get(args[0]);
    final Path jsonPath = Paths.get(args[1]);
    final int generatingVariants = Integer.parseInt(args[2]);
    final int randomSeed = Integer.parseInt(args[3]);

    lunch(kgpRootPath, jsonPath, generatingVariants, randomSeed);
  }

  public static void lunch(
      final Path rootPath,
      final Path jsonPath,
      final int generatingVariants,
      final int randomSeed) {
    final String json = Util.readString(jsonPath);

    new CrossoverTester(rootPath, json, generatingVariants, randomSeed).run();
  }
}
