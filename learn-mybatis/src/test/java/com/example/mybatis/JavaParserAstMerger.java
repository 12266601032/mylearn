package com.example.mybatis;

import com.example.mybatis.merge.NodeMergeHandler;
import com.example.mybatis.merge.field.FieldMergeHandler;
import com.example.mybatis.merge.imports.ImportsMergeHandler;
import com.example.mybatis.merge.method.MethodMergeHandler;
import com.example.mybatis.merge.type.TopLevelTypeMergeHandler;
import com.example.mybatis.merge.type.TypeMergeHandler;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.TypeDeclaration;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import javax.annotation.Generated;
import org.mybatis.generator.exception.ShellException;

public class JavaParserAstMerger implements JavaFileMerger {

  private NodeMergeHandler<ImportDeclaration, NodeList<ImportDeclaration>> importMergeHandler
      = new ImportsMergeHandler();
  private NodeMergeHandler<TypeDeclaration<?>, NodeList<TypeDeclaration<?>>> topLevelMergeHandler
      = new TopLevelTypeMergeHandler(
      new TypeMergeHandler(new FieldMergeHandler()
          .replaceableFunction(exists -> exists.getAnnotationByClass(Generated.class).isPresent()),
          new MethodMergeHandler()
              .replaceableFunction(
                  exists -> exists.getAnnotationByClass(Generated.class).isPresent())));

  @Override
  public String merge(String newFileSource, File existingFile, String[] javadocTags,
      String fileEncoding) throws ShellException {
    try {
      CompilationUnit newUnit = JavaParser.parse(newFileSource);
      CompilationUnit existsUnit = JavaParser.parse(existingFile, Charset.forName(fileEncoding));
      return mergeCompilationUnit(newUnit, existsUnit).toString();
    } catch (FileNotFoundException e) {
      throw new ShellException(e);
    }
  }

  public CompilationUnit mergeCompilationUnit(CompilationUnit newUnit, CompilationUnit existsUnit) {
    //导包合并
    NodeList<ImportDeclaration> existsImports = existsUnit.getImports();
    NodeList<ImportDeclaration> newImports = newUnit.getImports();
    doMergeImports(newImports, existsImports, existsUnit);
    //顶层类合并
    NodeList<TypeDeclaration<?>> existsTypes = existsUnit.getTypes();
    NodeList<TypeDeclaration<?>> newTypes = newUnit.getTypes();
    doMergeTopLevelTypes(newTypes, existsTypes, existsUnit);
    //导包优化
    return existsUnit;
  }

  protected void doMergeImports(
      NodeList<ImportDeclaration> newImports, NodeList<ImportDeclaration> existsImports,
      CompilationUnit existsUnit) {

    for (ImportDeclaration newImport : newImports) {
      importMergeHandler.merge(newImport, existsImports, existsUnit);
    }
  }

  protected void doMergeTopLevelTypes(NodeList<TypeDeclaration<?>> newTypes,
      NodeList<TypeDeclaration<?>> existsTypes,
      CompilationUnit existsUnit) {
    for (TypeDeclaration<?> newType : newTypes) {
      topLevelMergeHandler.merge(newType, existsTypes, existsUnit);
    }
  }
}
