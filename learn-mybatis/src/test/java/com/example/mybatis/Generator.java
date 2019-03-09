package com.example.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.VerboseProgressCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

public class Generator {

  @Test
  public void name() {

    try (InputStream configurationFile = Generator.class.getClassLoader()
        .getResourceAsStream("generatorConfig.xml")) {
      List<String> warnings = new ArrayList<>();
      //配置解析
      ConfigurationParser cp = new ConfigurationParser(warnings);
      Configuration config = cp.parseConfiguration(configurationFile);
      //
      DefaultShellCallback shellCallback = new DefaultShellCallback(true);

      MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);

      ProgressCallback progressCallback = new VerboseProgressCallback();

      myBatisGenerator.generate(progressCallback);

      warnings.forEach(System.out::print);
    } catch (IOException | XMLParserException | InterruptedException | InvalidConfigurationException | SQLException e) {
      e.printStackTrace();
    }
  }

}
