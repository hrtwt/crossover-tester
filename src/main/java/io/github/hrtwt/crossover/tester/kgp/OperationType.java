package io.github.hrtwt.crossover.tester.kgp;

import java.util.Arrays;
import org.eclipse.jdt.core.dom.ASTNode;
import jp.kusumotolab.kgenprog.project.NoneOperation;
import jp.kusumotolab.kgenprog.project.Operation;
import jp.kusumotolab.kgenprog.project.jdt.DeleteOperation;
import jp.kusumotolab.kgenprog.project.jdt.InsertAfterOperation;
import jp.kusumotolab.kgenprog.project.jdt.InsertBeforeOperation;
import jp.kusumotolab.kgenprog.project.jdt.InsertIntoBlockOperation;
import jp.kusumotolab.kgenprog.project.jdt.ReplaceOperation;

public enum OperationType {
  INSERT_INTO_BLOCK(new InsertIntoBlockOperation(null)) {
    @Override
    public Operation constructOperation(final ASTNode node) {
      return new InsertIntoBlockOperation(node);
    }
  },
  DELETE(new DeleteOperation()) {
    @Override
    public Operation constructOperation(final ASTNode node) {
      return new DeleteOperation();
    }
  },

  INSERT_AFTER(new InsertAfterOperation(null)) {
    @Override
    public Operation constructOperation(final ASTNode node) {
      return new InsertAfterOperation(node);
    }
  },
  INSERT_BEFORE(new InsertBeforeOperation(null)) {
    @Override
    public Operation constructOperation(final ASTNode node) {
      return new InsertBeforeOperation(node);
    }
  },
  REPLACE(new ReplaceOperation(null)) {
    @Override
    public Operation constructOperation(final ASTNode node) {
      return new ReplaceOperation(node);
    }
  },
  NONE(new NoneOperation()) {
    @Override
    public Operation constructOperation(final ASTNode node) {
      return new NoneOperation();
    }
  };

  public final String operationName;

  OperationType(Operation op) {
    this.operationName = op.getName();
  }

  public static OperationType getByName(String name) {
    return Arrays.stream(OperationType.values())
        .filter(e -> e.operationName.equals(name))
        .findFirst()
        .orElse(NONE);
  }

  public abstract Operation constructOperation(final ASTNode node);
}
