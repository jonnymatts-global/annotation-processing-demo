package org.global.dax.annotation.example;

import java.lang.Integer;
import java.lang.String;
import java.util.List;

public final class DemoBuilder {
  private Integer i;

  private String s;

  private List<String> l;

  public DemoBuilder i(final Integer i) {
    this.i = i;
    return this;
  }

  public DemoBuilder s(final String s) {
    this.s = s;
    return this;
  }

  public DemoBuilder l(final List<String> l) {
    this.l = l;
    return this;
  }

  public Demo build() {
    return new Demo(i, s, l);
  }
}
