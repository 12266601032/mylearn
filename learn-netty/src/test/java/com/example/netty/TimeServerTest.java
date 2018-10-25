package com.example.netty;

import static org.junit.Assert.*;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;

/**
 * Date:     2018年10月18日 17:49 <br/>
 *
 * @author lcc
 * @see
 * @since
 */
public class TimeServerTest {

  @Test
  public void name() {
    AtomicInteger atomicInteger = new AtomicInteger(Integer.MAX_VALUE - 1);
    atomicInteger.getAndIncrement();
    atomicInteger.getAndIncrement();
    System.out.println(atomicInteger.getAndIncrement());
  }

  public static void main(String[] args) {
    System.out.println(add(BigInteger.valueOf(Long.MAX_VALUE), 64));
  }

  public static BigInteger add(BigInteger num, int pow) {
    if (pow < 0) {
      return num;
    }
    BigInteger added = num.add(BigInteger.valueOf((long) Math.pow(2, pow)));
    return add(added, --pow);
  }
}