package com.example.mybatis.merge.type;

import com.example.mybatis.merge.NodeMergeHandler;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import java.util.Objects;

public class TypeMergeHandler implements NodeMergeHandler<TypeDeclaration<?>, TypeDeclaration<?>> {

  private NodeMergeHandler<FieldDeclaration, NodeList<BodyDeclaration<?>>> fieldMergeHandler;
  private NodeMergeHandler<MethodDeclaration, NodeList<BodyDeclaration<?>>> methodMergeHandler;

  public TypeMergeHandler(
      NodeMergeHandler<FieldDeclaration, NodeList<BodyDeclaration<?>>> fieldMergeHandler,
      NodeMergeHandler<MethodDeclaration, NodeList<BodyDeclaration<?>>> methodMergeHandler) {
    this.fieldMergeHandler = fieldMergeHandler;
    this.methodMergeHandler = methodMergeHandler;
  }

  @Override
  public void merge(TypeDeclaration<?> newNode, TypeDeclaration<?> existsNodes,
      CompilationUnit targetUnit) {
    Objects.requireNonNull(existsNodes, "target type must not be null.");
    NodeList<BodyDeclaration<?>> existsMembers = existsNodes.getMembers();
    for (BodyDeclaration<?> newMember : newNode.getMembers()) {
      if (newMember.isFieldDeclaration()) {
        fieldMergeHandler.merge(newMember.asFieldDeclaration(), existsMembers, targetUnit);
      } else if (newMember.isMethodDeclaration()) {
        methodMergeHandler.merge(newMember.asMethodDeclaration(), existsMembers, targetUnit);
      } else if (newMember.isTypeDeclaration()) {
        TypeDeclaration newTypeMember = newMember.asTypeDeclaration();
        TypeDeclaration existsTypeMember = existsMembers
            .stream()
            .filter(BodyDeclaration::isTypeDeclaration)
            .map(BodyDeclaration::asTypeDeclaration)
            .filter(el -> Objects.equals(el.getName(), newTypeMember.getName()))
            .findFirst()
            .orElse(null);
        if (existsTypeMember == null) {
          doInsertNewTypeMember(newTypeMember, existsMembers);
        } else {
          merge(newTypeMember, existsTypeMember, targetUnit);
        }
      }
    }
  }

  protected void doInsertNewTypeMember(TypeDeclaration newTypeMember,
      NodeList<BodyDeclaration<?>> existsMembers) {
    existsMembers.addLast(newTypeMember);
  }
}
