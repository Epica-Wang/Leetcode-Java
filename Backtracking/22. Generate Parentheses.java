Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.

For example, given n = 3, a solution set is:

[
  "((()))",
  "(()())",
  "(())()",
  "()(())",
  "()()()"
]

/**
original solution的简化思路：
只要leftRemain > 0 时，当前可以加入左括号
如果leftRemain < RightRemain, 当前可以加入右括号
*/
class Solution {
    public List<String> generateParenthesis(int n) {
      List<String> res = new LinkedList<>();
      helper(res, new StringBuilder(),n, n);
      return res;
    }

    public void helper(List<String> res, StringBuilder cur, int leftRemain,int rightRemain){
      if(leftRemain==0 && rightRemain ==0){
        res.add(cur.toString());
        return;
      }

      if (leftRemain > 0) {
        cur.append("(");
        helper(res, cur, leftRemain-1, rightRemain);
        cur.deleteCharAt(cur.length()-1);
      }

      if (leftRemain < rightRemain) {
        cur.append(")");
        helper(res, cur, leftRemain, rightRemain-1);
        cur.deleteCharAt(cur.length()-1);
      }
    }
}
/* Original Solution:
backtracking: 跟其他题不太一样的一点是，add时有左括号，右括号两种选择
helper function需要传入可添加的左括号，右括号剩余的数量。
任何时刻必须保证leftRemain <= rightRemain。
0. 当leftRemain = rightRemain = 0时 加入结果
1. 当leftRemain = rightRemain ！=0时，必须先添加左括号：因为之前的左右括号已经match了，开始新的括号 必须从左括号开始
2. 当leftRemain< rightRemain 且leftRemain！=0时，添加左括号右括号都可以。注意这一步 必须要把添加左，右的情况都recursion
3. leftRemain< rightRemain且leftRemain=0时 （因为左右括号剩余数量相等的时候总是先选择添加左括号，所以不可能有left>right的情况）：只添加右括号

*/
class Solution {
    public List<String> generateParenthesis(int n) {
      List<String> res = new LinkedList<>();
      helper(res, new StringBuilder(),n, n);
      return res;
    }

    public void helper(List<String> res, StringBuilder cur, int leftRemain,int rightRemain){
      if(leftRemain==0 && rightRemain ==0){
        res.add(cur.toString());
        return;
      }

      //因为在leftRemain<rightRemain的时候要把添加左括号，右括号的情况都递归下去  需要delete两次
      // 所以索性把每个情况的delete都写在自己的if block里面
      if(leftRemain==rightRemain){
        cur.append("(");
        helper(res, cur, leftRemain-1, rightRemain);
        cur.deleteCharAt(cur.length()-1);
      }else if(leftRemain<rightRemain && leftRemain>0){
        cur.append("(");
        helper(res,cur,leftRemain-1, rightRemain);
        cur.deleteCharAt(cur.length()-1);

        cur.append(")");
        helper(res, cur,leftRemain, rightRemain-1);
        cur.deleteCharAt(cur.length()-1);
      }else{
        cur.append(")");
        helper(res,cur,leftRemain,rightRemain-1);
        cur.deleteCharAt(cur.length()-1);
      }
    }
}
