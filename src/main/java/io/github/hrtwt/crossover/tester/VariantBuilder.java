package io.github.hrtwt.crossover.tester;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import io.github.hrtwt.crossover.tester.json.JsonVariant;
import io.github.hrtwt.crossover.tester.json.JsonVariant.JsonBase;
import jp.kusumotolab.kgenprog.ga.variant.Base;
import jp.kusumotolab.kgenprog.ga.variant.EmptyHistoricalElement;
import jp.kusumotolab.kgenprog.ga.variant.Gene;
import jp.kusumotolab.kgenprog.ga.variant.Variant;
import jp.kusumotolab.kgenprog.ga.variant.VariantStore;
import jp.kusumotolab.kgenprog.project.ASTLocation;
import jp.kusumotolab.kgenprog.project.ASTLocations;
import jp.kusumotolab.kgenprog.project.LineNumberRange;
import jp.kusumotolab.kgenprog.project.Operation;
import jp.kusumotolab.kgenprog.project.TestFullyQualifiedName;
import jp.kusumotolab.kgenprog.project.test.TestResults;

public class VariantBuilder {

  //  public static List<Variant> makeVariants(
  //      final Collection<JsonVariant> jsons, VariantStore store) {
  //    return jsons.stream()
  //        .map(v -> VariantBuilder.makeVariant(v, store)) //todo need reset variantStore
  //        .collect(Collectors.toList());
  //  }

  public static Variant makeVariant(final JsonVariant json, VariantStore store) {
    Variant parent = store.getInitialVariant();
    final List<Base> bases = new ArrayList<>();
    for (final JsonBase jb : json.bases) {
      final Base b = makeBase(jb, parent);
      bases.add(b);
      parent = store.createVariant(new Gene(bases), EmptyHistoricalElement.instance);

      if (!parent.isBuildSucceeded()) {
        throw new IllegalStateException("failed to build variant: while making base");
      }
    }

    if (!hasSameTestResults(parent, json)) {
      throw new IllegalStateException(
          "failed to build variant: generated variant does not have same test result");
    }

    return parent;
  }

  public static boolean hasSameTestResults(final Variant variant, final JsonVariant json) {
    final TestResults one = variant.getTestResults();
    final Map<TestFullyQualifiedName, Boolean> other = json.testResults;

    if (!Objects.equals(one.getExecutedTestFQNs(), other.keySet())) {
      return false;
    }

    return one.getExecutedTestFQNs().stream()
        .noneMatch(fqn -> one.getTestResult(fqn).failed == other.get(fqn));
  }

  public static Base makeBase(final JsonBase jsonBase, final Variant parent) {
    final Statement stmt = parseSnippet(jsonBase.snippet, jsonBase.snippetNodeType);
    final Operation operation = jsonBase.operationType.constructOperation(stmt);

    final ASTLocation location =
        findLocationByLineNo(parent, jsonBase.startLineNo, jsonBase.endLineNo);

    return new Base(location, operation);
  }

  public static Statement parseSnippet(final String snippet, final int nodeType) {
    // jdt ast parser can't parse code snippet that consists only statement
    // so adding class and method definition is needed.
    final String javaCode = "class Temp{void temp(){" + snippet + "}}";

    @SuppressWarnings({"deprecation"})
    final ASTParser parser = ASTParser.newParser(AST.JLS8);
    parser.setSource(javaCode.toCharArray());
    final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
    final TypeDeclaration type = (TypeDeclaration) cu.types().get(0);
    final MethodDeclaration method = type.getMethods()[0]; // the first method
    final Block block = method.getBody();
    final List<?> stmts = block.statements();

    final Statement theStatement = (Statement) stmts.get(0);
    if (theStatement.getNodeType() != nodeType) {
      throw new IllegalStateException("failed to parse snippet");
    }

    return theStatement;
  }

  public static ASTLocation findLocationByLineNo(
      final Variant v, final int startLineNo, final int endLineNo) {
    final Predicate<LineNumberRange> isSameRange =
        e -> e.start == startLineNo && e.end == endLineNo;
    final ASTLocations locs = v.getGeneratedSourceCode().getProductAsts().get(0).createLocations();
    final List<ASTLocation> candidates =
        IntStream.rangeClosed(startLineNo, endLineNo)
            .mapToObj(locs::infer)
            .flatMap(Collection::stream)
            .distinct()
            .filter(e -> isSameRange.test(e.inferLineNumbers()))
            .collect(Collectors.toList());
    return candidates.get(0); // todo
  }
}
