package io.github.hrtwt.crossover.tester.json;

import java.util.List;
import java.util.Map;
import io.github.hrtwt.crossover.tester.kgp.OperationType;
import jp.kusumotolab.kgenprog.project.TestFullyQualifiedName;

public class JsonVariant {

  public long id;
  public int generationNumber;

  public List<JsonBase> bases;

  public List<String> generatedSourceCode;

  public double testSuccessRate;
  public int executedTestsCount;
  public Map<TestFullyQualifiedName, Boolean> testResults;
  public double fitness;
  public int selectionCount;

  public static class JsonBase {

    public OperationType operationType;
    public String sourcePath;
    public String snippet;
    public int startLineNo;
    public int endLineNo;
    public int baseIdentityHashCode;
  }
}
