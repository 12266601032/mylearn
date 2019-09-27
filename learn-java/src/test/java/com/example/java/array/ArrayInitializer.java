package com.example.java.array;

import org.junit.Test;

public class ArrayInitializer {

  @Test
  public void create() {
    //创建数组的基本方式
    int[] abc = {1, 3};
    abc = new int[0];
    abc = new int[]{1, 3, 5};
  }
}
