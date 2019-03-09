package com.example.mybatis;

import static org.mybatis.dynamic.sql.SqlBuilder.select;

import static com.example.generated.simple.mapper.VoteDynamicSqlSupport.*;

import com.example.generated.simple.mapper.VoteMapper;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import java.io.File;
import java.io.FileNotFoundException;
import org.junit.Test;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.SelectDSL;

public class JavaParserAstMergerTest {

  @Test
  public void mergeCompilationUnit() throws Exception {
    File exists = new File(
        "D:\\workspace\\mylearn\\learn-mybatis\\src\\test\\java\\com\\example\\mybatis\\test\\exists\\JavaSource.java");
    File newCode = new File(
        "D:\\workspace\\mylearn\\learn-mybatis\\src\\test\\java\\com\\example\\mybatis\\test\\newcode\\JavaSource.java");
    JavaParserAstMerger javaparserAstMerger = new JavaParserAstMerger();
    CompilationUnit unit = javaparserAstMerger
        .mergeCompilationUnit(JavaParser.parse(newCode), JavaParser.parse(exists));
    System.out.println(unit);
    VoteMapper mapper = null;
    mapper.selectMany(select(id).from(vote).build().render(RenderingStrategy.MYBATIS3));
  }
}
