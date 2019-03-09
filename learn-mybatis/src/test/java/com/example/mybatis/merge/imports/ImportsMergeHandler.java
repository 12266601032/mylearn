package com.example.mybatis.merge.imports;

import com.example.mybatis.merge.NodeMergeHandler;
import com.example.mybatis.utils.NodeMergeUtils;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import java.util.Optional;

public class ImportsMergeHandler implements
    NodeMergeHandler<ImportDeclaration, NodeList<ImportDeclaration>> {

  @Override
  public void merge(ImportDeclaration newNode, NodeList<ImportDeclaration> existsNodes,
      CompilationUnit targetUnit) {

    Optional<ImportDeclaration> existsImport = existsNodes
        .stream()
        .filter(el -> NodeMergeUtils.isSameImport(newNode, el))
        .findFirst();

    if (!existsImport.isPresent()) {
      //不存在老的，直接插入到导入列表
      doInsertNewImport(newNode, existsNodes);
    } else if (newNode.isAsterisk() && existsImport.get().isAsterisk() != newNode.isAsterisk()) {
      //新的是通配符导入，直接替换掉老的
      doReplaceImport(newNode, existsNodes, existsImport.get());
    }
  }

  protected void doReplaceImport(ImportDeclaration newNode, NodeList<ImportDeclaration> existsNodes,
      ImportDeclaration existsNode) {
    existsNodes.replace(existsNode, newNode);
  }

  protected void doInsertNewImport(ImportDeclaration newNode,
      NodeList<ImportDeclaration> existsNodes) {
    existsNodes.addLast(newNode);
  }
}
