package com.example.mybatis;

import java.io.File;
import org.mybatis.generator.exception.ShellException;

public interface JavaFileMerger {

  String merge(String newFileSource,
      File existingFile, String[] javadocTags, String fileEncoding) throws ShellException;

}
