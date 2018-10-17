package com.example.java.regex;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

public class RegexDemo {

  @Test
  public void namedGroup() {
    Pattern pattern = Pattern.compile("(?<text>.*)");
    Matcher matcher = pattern.matcher("abc");
    if (matcher.matches()) {
      System.out.println(matcher.group("text"));
    }
  }

  @Test
  public void namedGroups() {
    Pattern pattern = Pattern.compile("(?<text>.*)");
    Matcher matcher = pattern.matcher("abc");
    Map<String, Integer> namedGroups = RegexHelper.namedGroups(pattern);
    if (matcher.matches()) {
      for (Entry<String, Integer> entry : namedGroups.entrySet()) {
        System.out
            .println("groupName:" + entry.getKey() + ",value:" + matcher.group(entry.getKey()));
      }
    }
  }

  public static class RegexHelper {

    private static final Method namedGroups;

    static {
      Method namedGroupsMethod = ReflectionUtils.findMethod(Pattern.class, "namedGroups");
      ReflectionUtils.makeAccessible(namedGroupsMethod);
      namedGroups = namedGroupsMethod;
    }

    public static Map<String, Integer> namedGroups(Pattern pattern) {
      Objects.requireNonNull(pattern, "pattern");
      try {
        return (Map<String, Integer>) namedGroups.invoke(pattern);
      } catch (IllegalAccessException | InvocationTargetException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
