package com.example.mybatis.merge.type;

import com.example.mybatis.merge.NodeMergeHandler;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.TypeDeclaration;
import java.util.Objects;
import java.util.Optional;
import org.springframework.util.Assert;

public class TopLevelTypeMergeHandler implements
    NodeMergeHandler<TypeDeclaration<?>, NodeList<TypeDeclaration<?>>> {

  private NodeMergeHandler<TypeDeclaration<?>, TypeDeclaration<?>> typeMergeHandler;

  public TopLevelTypeMergeHandler(
      NodeMergeHandler<TypeDeclaration<?>, TypeDeclaration<?>> typeMergeHandler) {
    Objects.requireNonNull(typeMergeHandler, "typeMergeHandler");
    this.typeMergeHandler = typeMergeHandler;
  }

  @Override
  public void merge(TypeDeclaration<?> newNode, NodeList<TypeDeclaration<?>> existsNodes,
      CompilationUnit targetUnit) {
    Assert.isTrue(newNode.isTopLevelType(), "must be a top level type declaration");
    Optional<TypeDeclaration<?>> existsType = existsNodes
        .stream()
        .filter(el -> Objects.equals(el.getName(), newNode.getName()))
        .findFirst();

    if (!existsType.isPresent()) {
      doInsertNewType(newNode, existsNodes, targetUnit);
    } else {
      typeMergeHandler.merge(newNode, existsType.get(), targetUnit);
    }

  }

  protected void doInsertNewType(TypeDeclaration<?> newNode,
      NodeList<TypeDeclaration<?>> existsNodes, CompilationUnit targetUnit) {
    existsNodes.addLast(newNode);
  }
}
