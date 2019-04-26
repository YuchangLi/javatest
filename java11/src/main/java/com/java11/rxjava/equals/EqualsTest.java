package com.java11.rxjava.equals;

import java.util.HashMap;
import java.util.Map;

public class EqualsTest {
  private int id;

  @Override
  public int hashCode() {
//    final int prime = 31;
//    int result = 1;
//    result = prime * result + id;
//    return result;
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    EqualsTest other = (EqualsTest) obj;
    if (id != other.id)
      return false;
    return true;
  }

  public static void main(String[] args) {
    EqualsTest t1 = new EqualsTest();
    EqualsTest t2 = new EqualsTest();
    System.out.println(t1.hashCode());
    System.out.println(t2.hashCode());
    Map<EqualsTest, String> map = new HashMap<>();
    map.put(t1, "1");
    map.put(t2, "2");
    System.out.println(map.size());
  }
}
