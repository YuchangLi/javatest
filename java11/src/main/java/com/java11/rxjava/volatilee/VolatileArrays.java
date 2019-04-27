package com.java11.rxjava.volatilee;

/**
 * 
 * @ClassName: VolatileArrays
 * @Description: volatile 修饰数组时，对数组元素的更新会立刻可见于其他线程
 * @author liyuchang
 * @date 2019年4月27日
 */
public class VolatileArrays extends Thread {
  private boolean[] isRunning = new boolean[1];

  public void setRunning(boolean isRunning) {
    this.isRunning[0] = isRunning;

  }

  public void run() {
    System.out.println(Thread.currentThread().getName()+"进入run了");
    while (isRunning[0] == true) {
      try {
        Thread.sleep(100);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } ;
    System.out.println("线程被停止了");
  }


  public static void main(String[] args) {
    try {
      VolatileArrays t = new VolatileArrays();
      t.setRunning(true);
      t.start();
      Thread.sleep(1000);
      t.setRunning(false);
      System.out.println(Thread.currentThread().getName()+"已赋值为false");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
