package com.example.mybatis;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class JavaparserDemo {


  @Test
  public void name() {
    String sourceCode = String.join("\n",
        "public class HelloWorld {",
        "    @NotNull(value=\"123\")",
        " public static void main(String[] args) {",
        // Insert the following statement.
        // System.out.println("Hello, World");
        "    }",
        "}");
    CompilationUnit unit = JavaParser.parse(sourceCode);
    List<Node> methods = new ArrayList<>();
    unit.accept(new VoidVisitorAdapter<Void>() {
      @Override
      public void visit(MethodDeclaration n, Void arg) {
        System.out.println(n.getName());
        super.visit(n, arg);
      }

      @Override
      public void visit(NormalAnnotationExpr n, Void arg) {
        for (MemberValuePair pair : n.getPairs()) {
          System.out.println(pair.getName() + ":" + pair.getValue());
        }
        super.visit(n, arg);
      }
    }, null);
    CompilationUnit newUnit = unit.clone();
    System.out.println(unit.toString());
  }
}
