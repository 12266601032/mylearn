package com.example.java.math;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 溢出检查算法
 */
public class OverflowCheckDemo {

  private static int addExact(int x, int y) {
    int r = x + y;
    // HD 2-12 Overflow iff both arguments have the opposite sign of the result
    if (((x ^ r) & (y ^ r)) < 0) {
      throw new ArithmeticException("integer overflow");
    }
    return r;
  }

  private static int subtractExact(int x, int y) {
    int r = x - y;
    // HD 2-12 Overflow iff the arguments have different signs and
    // the sign of the result is different than the sign of x
    if (((x ^ y) & (x ^ r)) < 0) {
      throw new ArithmeticException("integer overflow");
    }
    return r;
  }

  public static int multiplyExact(int x, int y) {
    long r = (long) x * (long) y;
    if ((int) r != r) {
      throw new ArithmeticException("integer overflow");
    }
    return (int) r;
  }

  public static long multiplyExact(long x, long y) {
    long r = x * y;
    long ax = Math.abs(x);
    long ay = Math.abs(y);
    if (((ax | ay) >>> 31 != 0)) {
      // Some bits greater than 2^31 that might cause overflow
      // Check the result using the divide operator
      // and check for the special case of Long.MIN_VALUE * -1
      if (((y != 0) && (r / y != x)) ||
          (x == Long.MIN_VALUE && y == -1)) {
        throw new ArithmeticException("long overflow");
      }
    }
    return r;
  }


  @Test
  public void testAddExact() {
    try {
      addExact(Integer.MAX_VALUE, 1);
      fail();
    } catch (Exception e) {
      assertTrue(e instanceof ArithmeticException);
    }

    try {
      addExact(Integer.MIN_VALUE, -1);
      fail();
    } catch (Exception e) {
      assertTrue(e instanceof ArithmeticException);
    }
  }

  @Test
  public void valueOf() {
    Integer.valueOf("999999999999999999999999999999999");
  }
}
