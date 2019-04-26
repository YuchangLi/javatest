package com.java11.rxjava;

public class ConstantCacheTest {
  public static void main(String[] args) {
    stringTest();
//    intTest();
  }

  private static void stringTest() {
    String s1 = new String("计算机");
    String s2 = s1.intern();
    String s3 = "计算机";
    System.out.println(s2);//计算机
    System.out.println(s1 == s2);//false，因为一个是堆内存中的String对象一个是常量池中的String对象，
    System.out.println(s3 == s2);//true，因为两个都是常量池中的String对象
    System.out.println("===================");
    String str1 = "str";
    String str2 = "ing";
    String str3 = "str" + "ing";//常量池中的对象
    String str4 = str1 + str2; //在堆上创建的新的对象     
    String str5 = "string";//常量池中的对象
    System.out.println(str3 == str4);//false
    System.out.println(str3 == str5);//true
    System.out.println(str4 == str5);//false
    System.out.println(str3 == ("stri"+"ng"));//false
    System.out.println("===================");
    String t1 = "x";
    String t2 = t1+t1;
    String t3 = t1.intern()+t1.intern();
    System.out.println("xx" == ("x"+"x"));
    System.out.println("xx" == t2);
    System.out.println("xx" == t3);
  }

  private static void intTest() {
    Integer i1 = 40;
    Integer i2 = 40;
    Integer i3 = 0;
    Integer i4 = new Integer(40);
    Integer i5 = new Integer(40);
    Integer i6 = new Integer(0);
     
    System.out.println("i1=i2   " + (i1 == i2)); // true
    System.out.println("i1=i2+i3   " + (i1 == i2 + i3)); // true
    System.out.println("i1=i4   " + (i1 == i4)); // false
    System.out.println("i4=i5   " + (i4 == i5)); // false
    System.out.println("i4=i5+i6   " + (i4 == i5 + i6));  // true 
    System.out.println("40=i5+i6   " + (40 == i5 + i6)); // true
    
  }
}
