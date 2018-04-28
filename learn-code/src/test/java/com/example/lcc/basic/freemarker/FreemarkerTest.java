package com.example.lcc.basic.freemarker;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import org.junit.Test;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class FreemarkerTest {
	
	@Test
	public void testFreemarker() throws Exception{
		Configuration configuration = new Configuration(new Version("2.3.26"));
		TemplateLoader fileTemplateLoader = new FileTemplateLoader(new File(this.getClass().getClassLoader().getResource("").getPath()));
		configuration.setTemplateLoader(fileTemplateLoader);
		Template template = configuration.getTemplate("freemarkerDemoPage.html", "UTF-8");
		Map<String,Object> map = new HashMap<>();
		map.put("hello", "你好");
		map.put("repayDay",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-04-16 15:33:01"));
		Writer out = new OutputStreamWriter(System.out);
		template.process(map, out);
	}
	

}
