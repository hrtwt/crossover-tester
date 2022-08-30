package io.github.hrtwt.crossover.tester.json;

import java.util.List;
import java.util.Map;
import io.github.hrtwt.crossover.tester.kgp.OperationType;
import jp.kusumotolab.kgenprog.project.TestFullyQualifiedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@Builder
@EqualsAndHashCode
public class JsonVariant {

  public final long id;
  public final int generationNumber;

  public final List<JsonBase> bases;

  public final List<String> generatedSourceCode;

  public final double testSuccessRate;
  public final int executedTestsCount;
  public final Map<TestFullyQualifiedName, Boolean> testResults;
  public final double fitness;
  public final int selectionCount;

  @Builder
  @EqualsAndHashCode
  public static class JsonBase {

    public final OperationType operationType;
    public final String sourcePath;
    public final String snippet;
    public final int startLineNo;
    public final int endLineNo;
    public final int baseIdentityHashCode;
    public final int snippetNodeType;
  }
}
