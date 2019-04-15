package com.java11.rxjava;

import java.util.ArrayList;

public class Java10Test {
  public static void main(String[] args) {
//    var();
    var i = 0;
    String var = "str";
    
  }

  private static void var() {
//    Object objList = new ArrayList<String>();
//    objList.add("hello，world！");//这句代码会出现编译异常
    
    var varList = new ArrayList<>();
    System.out.println(varList.getClass());
    varList.add("str");
    varList.add(1);
    System.out.println(varList.get(0).getClass());
    System.out.println(varList.get(1).getClass());
  }
}
