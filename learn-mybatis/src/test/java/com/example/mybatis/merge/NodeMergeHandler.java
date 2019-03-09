package com.example.mybatis.merge;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import java.util.function.Function;

public interface NodeMergeHandler<N extends Node, NL> {

  void merge(N newNode, NL existsNodes, CompilationUnit targetUnit);

  @FunctionalInterface
  interface ReplaceableFunction<N extends Node> extends Function<N, Boolean> {

  }

}
