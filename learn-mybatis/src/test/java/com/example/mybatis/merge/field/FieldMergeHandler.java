package com.example.mybatis.merge.field;

import com.example.mybatis.merge.NodeMergeHandler;
import com.example.mybatis.utils.NodeMergeUtils;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import java.util.Optional;

public class FieldMergeHandler implements
    NodeMergeHandler<FieldDeclaration, NodeList<BodyDeclaration<?>>> {

  private ReplaceableFunction<FieldDeclaration> fieldReplaceable;

  public FieldMergeHandler replaceableFunction(
      ReplaceableFunction<FieldDeclaration> fieldReplaceable) {
    this.fieldReplaceable = fieldReplaceable;
    return this;
  }

  @Override
  public void merge(FieldDeclaration newNode, NodeList<BodyDeclaration<?>> existsNodes,
      CompilationUnit targetUnit) {
    Optional<FieldDeclaration> existsField = existsNodes
        .stream()
        .filter(BodyDeclaration::isFieldDeclaration)
        .map(el -> (FieldDeclaration) el)
        .filter(el -> NodeMergeUtils.isSameField(el, newNode))
        .findFirst();
    if (!existsField.isPresent()) {
      doInsertNewField(newNode, existsNodes);
    } else {
      boolean canReplace = Optional.ofNullable(fieldReplaceable)
          .map(fun -> fun.apply(existsField.get()))
          .orElse(false);
      if (canReplace) {
        doReplaceField(newNode, existsField.get(), existsNodes);
      }
    }
  }

  protected void doReplaceField(FieldDeclaration newNode, FieldDeclaration existsField,
      NodeList<BodyDeclaration<?>> existsNodes) {
    existsNodes.replace(existsField, newNode);
  }

  protected void doInsertNewField(FieldDeclaration newNode,
      NodeList<BodyDeclaration<?>> existsNodes) {
    existsNodes.addFirst(newNode);
  }
}
