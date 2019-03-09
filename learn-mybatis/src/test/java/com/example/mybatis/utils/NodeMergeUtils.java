package com.example.mybatis.utils;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.Type;
import java.util.Objects;

public abstract class NodeMergeUtils {

  public static boolean isSameField(FieldDeclaration aField, FieldDeclaration bField) {
    //直接比较等
    if (Objects.equals(aField, bField)) {
      return true;
    }
    if (aField == null || bField == null) {
      return false;
    }
    //比较变量声明相同
    NodeList<VariableDeclarator> aVariables = aField.getVariables();
    NodeList<VariableDeclarator> bVariables = bField.getVariables();
    for (int i = 0; i < aVariables.size(); i++) {
      VariableDeclarator aDeclarator = aVariables.get(i);
      if (i < bVariables.size()) {
        VariableDeclarator bDeclarator = bVariables.get(i);
        //声明不同则认为是不同字段
        if (!Objects.equals(aDeclarator.getName(), bDeclarator.getName())) {
          return false;
        }
        continue;
      }
      //第一相同则认为是同一个字段声明
      return i > 0;
    }
    return true;
  }

  private static boolean isSameType(Type aType, Type bType) {
    if (aType.isClassOrInterfaceType() && bType.isClassOrInterfaceType()) {
      ClassOrInterfaceType aClassOrInterfaceType = aType.asClassOrInterfaceType();
      ClassOrInterfaceType bClassOrInterfaceType = bType.asClassOrInterfaceType();
      return Objects.equals(aClassOrInterfaceType.getName().getIdentifier(),
          bClassOrInterfaceType.getName().getIdentifier());
    } else if (aType.isPrimitiveType() && bType.isPrimitiveType()) {
      PrimitiveType aPrimitiveType = aType.asPrimitiveType();
      PrimitiveType bPrimitiveType = bType.asPrimitiveType();
      return Objects
          .equals(aPrimitiveType.getType().asString(), bPrimitiveType.getType().asString());
    }
    return false;
  }

  public static boolean isSameMethod(MethodDeclaration aMethod, MethodDeclaration bMethod) {
    if (Objects.equals(aMethod, bMethod)) {
      return true;
    }
    if (aMethod == null || bMethod == null) {
      return false;
    }
    //方法名相同
    if (!Objects.equals(aMethod.getName(), bMethod.getName())) {
      return false;
    }
    //参数列表相同
    NodeList<Parameter> aParameters = aMethod.getParameters();
    NodeList<Parameter> bParameters = bMethod.getParameters();
    if (aParameters.size() != bParameters.size()) {
      return false;
    }
    for (int i = 0; i < aParameters.size(); i++) {
      Parameter aParameter = aParameters.get(i);
      Parameter bParameter = bParameters.get(i);
      Type aType = aParameter.getType();
      Type bType = bParameter.getType();
      //参数列表类型匹配
      if (!isSameType(aType, bType)) {
        return false;
      }
    }
    return true;
  }

  public static boolean isSameImport(ImportDeclaration aImport, ImportDeclaration bImport) {
    if (Objects.equals(aImport, bImport)) {
      return true;
    }
    if (aImport == null || bImport == null) {
      return false;
    }
    //同为静态或者非静态导入
    if (aImport.isStatic() != bImport.isStatic()) {
      return false;
    }
    Name aName = aImport.getName();
    Name bName = bImport.getName();
    if (aName.asString().contains(bName.asString()) && bImport.isAsterisk()) {
      return true;
    }
    if (bName.asString().contains(aName.asString()) && aImport.isAsterisk()) {
      return true;
    }
    //比较导入信息
    return Objects.equals(aImport.getName(), bImport.getName());
  }


}
