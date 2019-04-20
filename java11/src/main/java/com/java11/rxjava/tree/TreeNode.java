package com.java11.rxjava.tree;

public class TreeNode {
  TreeNode left;
  TreeNode right;
  
  static int getDeep(TreeNode node) {
    if(node == null) {
      return 0;
    }
    int leftDeep = getDeep(node.left);
    int rightDeep = getDeep(node.right);
    return leftDeep>rightDeep?(leftDeep+1):(rightDeep+1);
  }
  
  public static void main(String[] args) {
    TreeNode root = new TreeNode();
    TreeNode left = new TreeNode();
    root.left = left;
    TreeNode left2 = new TreeNode();
    left.left = left2;
    int deep = getDeep(root);
    System.out.println("deep = " + deep);
  }
}
