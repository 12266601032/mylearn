package com.example.mybatis;

import java.io.File;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;

public class JavaFileMergeableCallback extends DefaultShellCallback {

  private boolean mergeSupport = false;

  private JavaFileMerger javaFileMerger;

  /**
   * Instantiates a new default shell callback.
   *
   * @param overwrite the overwrite
   */
  public JavaFileMergeableCallback(boolean overwrite) {
    super(overwrite);
  }

  public JavaFileMergeableCallback mergeSupport(boolean mergeSupport) {
    this.mergeSupport = mergeSupport;
    return this;
  }

  public JavaFileMergeableCallback javaFileMerger(JavaFileMerger javaFileMerger) {
    this.javaFileMerger = javaFileMerger;
    return this;
  }

  @Override
  public String mergeJavaFile(String newFileSource, File existingFile, String[] javadocTags,
      String fileEncoding) throws ShellException {
    if (this.javaFileMerger == null) {
      return super.mergeJavaFile(newFileSource, existingFile, javadocTags, fileEncoding);
    }
    return javaFileMerger.merge(newFileSource, existingFile, javadocTags, fileEncoding);
  }
}
