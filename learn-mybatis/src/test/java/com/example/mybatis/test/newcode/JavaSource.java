package com.example.mybatis.test.newcode;

import com.example.mybatis.*;
import java.lang.reflect.Array;

public class JavaSource<T> {

  private int[][] a = {{1, 2}};

  T aa;

  private String forceReplace;

  public interface InterfaceA {

    void newMethod();
  }

  public class ClassA {

    private String newField;

    public class ClassAA {

      private String newField;

      public class ClassAAA {

        private String newField;

        private void newMethod() {
          System.out.println(111);
        }
      }

      public void forceReplace(String pp) {
        System.out.println("1232131");
      }
    }
  }
}
