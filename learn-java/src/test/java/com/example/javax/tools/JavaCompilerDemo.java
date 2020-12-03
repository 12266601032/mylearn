package com.example.javax.tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date:     2019年09月27日 10:39 <br/>
 *
 * @author lcc
 * @since 1.1.0
 */
public class JavaCompilerDemo {

  public static void main(String[] args) throws ClassNotFoundException {
    new TestDynamicCompiler().compileSourceCode();
  }

  public static class TestDynamicCompiler {


    @Test
    public void compileSourceCode() throws ClassNotFoundException {
      Class<?> aClass = TestDynamicCompiler
          .compile(
              "package com.example.javax.tools;\n"
                  + "\n"
                  + "import com.example.java.SystemDemo;\n"
                  + "\n"
                  + "public class SourceClass {\n"
                  + "\n"
                  + "\n"
                  + "  public static void main(String[] args) throws Exception {\n"
                  + "    new SystemDemo().runtime();\n"
                  + "  }\n"
                  + "\n"
                  + "}", "com.example.javax.tools.SourceClass"
          );
      System.out.println(aClass);
    }

    private static final Logger logger = LoggerFactory.getLogger(TestDynamicCompiler.class);

    private static final JavaCompiler _compiler = ToolProvider.getSystemJavaCompiler();
    private static final DiagnosticCollector<JavaFileObject> collector = new DiagnosticCollector<>();
    private static final CubeJavaFileManager manager = new CubeJavaFileManager(
        _compiler.getStandardFileManager(collector, null, null));
    private static final Map<String, JavaFileObject> fileObjectMap = new ConcurrentHashMap<>();
    private static List<String> options = new ArrayList<>();

    static {
      options.add("-Xlint:unchecked");
      options.add("-target");
      options.add("1.8");
    }

    public static Class<?> compile(String code, String className) throws ClassNotFoundException {
      String qualified = className.substring(className.lastIndexOf('.') + 1, className.length());
      CubeJavaObject cubeJavaObject = new CubeJavaObject(qualified, code);
      JavaCompiler.CompilationTask task = _compiler
          .getTask(null, manager, collector, options, null, Arrays.asList(cubeJavaObject));
      task.call();
      //输出诊断信息
      for (Diagnostic<? extends JavaFileObject> diagnostic : collector.getDiagnostics()) {
        try {
          logger.error("编译错误:{}", diagnostic.toString());
        } catch (Exception e) {
          logger.error("输出内容错误", e);
        }
      }
      return cubeJavaClassLoader.loadClass(className);
    }

    private static ClassLoader cubeJavaClassLoader = new ClassLoader() {

      @Override
      protected Class<?> findClass(String name) throws ClassNotFoundException {
        JavaFileObject fileObject = fileObjectMap.get(name);
        if (fileObject != null) {
          byte[] bytes = ((CubeJavaObject) fileObject).getCompiledBytes();
          return defineClass(name, bytes, 0, bytes.length);
        }
        try {
          return ClassLoader.getSystemClassLoader().loadClass(name);
        } catch (Exception e) {
          logger.error("加载类失败,{}", name, e);
          return super.findClass(name);
        }
      }

    };

    private static class CubeJavaObject extends SimpleJavaFileObject {

      private String code;
      private ByteArrayOutputStream outPutStream;

      public CubeJavaObject(String qualified, String code) {
        super(URI.create("string:///" + qualified.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
        this.code = code;
      }

      public CubeJavaObject(String qualified, Kind kind) {
        super(URI.create("string:///" + qualified.replace('.', '/') + kind.extension), kind);
      }

      @Override
      public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        if (code == null) {
          throw new IllegalArgumentException("code required");
        }
        return code;
      }

      @Override
      public OutputStream openOutputStream() throws IOException {
        outPutStream = new ByteArrayOutputStream();
        return outPutStream;
      }

      public byte[] getCompiledBytes() {
        return outPutStream.toByteArray();
      }
    }

    private static class CubeJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {

      public CubeJavaFileManager(JavaFileManager fileManager) {
        super(fileManager);
      }

      @Override
      public JavaFileObject getJavaFileForInput(Location location, String className,
          JavaFileObject.Kind kind) throws IOException {
        JavaFileObject javaFileObject = fileObjectMap.get(className);
        if (javaFileObject == null) {
          super.getJavaFileForInput(location, className, kind);
        }
        return javaFileObject;
      }

      @Override
      public JavaFileObject getJavaFileForOutput(Location location, String className,
          JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        JavaFileObject javaFileObject = new CubeJavaObject(className, kind);
        fileObjectMap.put(className, javaFileObject);
        return javaFileObject;
      }
    }
  }

}
