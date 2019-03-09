package com.example.mybatis.merge.method;

import com.example.mybatis.merge.NodeMergeHandler;
import com.example.mybatis.utils.NodeMergeUtils;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import java.util.Optional;

public class MethodMergeHandler implements
    NodeMergeHandler<MethodDeclaration, NodeList<BodyDeclaration<?>>> {

  private ReplaceableFunction<MethodDeclaration> methodReplaceable;

  public MethodMergeHandler replaceableFunction(
      ReplaceableFunction<MethodDeclaration> methodReplaceable) {
    this.methodReplaceable = methodReplaceable;
    return this;
  }

  @Override
  public void merge(MethodDeclaration newNode, NodeList<BodyDeclaration<?>> existsNodes,
      CompilationUnit targetUnit) {
    Optional<MethodDeclaration> existsMethod = existsNodes
        .stream()
        .filter(BodyDeclaration::isMethodDeclaration)
        .map(BodyDeclaration::asMethodDeclaration)
        .filter(el -> NodeMergeUtils.isSameMethod(el, newNode))
        .findFirst();
    if (!existsMethod.isPresent()) {
      doInsertNewMethod(newNode, existsNodes);
    } else {
      boolean canReplace = Optional.ofNullable(methodReplaceable)
          .map(fun -> fun.apply(existsMethod.get()))
          .orElse(false);
      if (canReplace) {
        doReplaceMethod(newNode, existsMethod.get(), existsNodes);
      }
    }
  }

  protected void doReplaceMethod(MethodDeclaration newNode, MethodDeclaration existsMethod,
      NodeList<BodyDeclaration<?>> existsNodes) {
    existsNodes.replace(existsMethod, newNode);
  }

  private void doInsertNewMethod(MethodDeclaration newNode,
      NodeList<BodyDeclaration<?>> existsNodes) {
    existsNodes.addLast(newNode);
  }
}
