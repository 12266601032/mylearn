package com.example.groovy;

import groovy.lang.GroovyShell;
import org.junit.Test;

public class GroovyShellDemo {

  @Test
  public void groovyShell() {
    GroovyShell shell = new GroovyShell();
    shell.evaluate("println(\"hello\")");
  }
}
