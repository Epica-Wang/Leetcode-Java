import java.util.*;

class Solution {
    public String alienOrder(String[] words){
      // use a hashmap to store adj.
      HashMap<Character, HashSet<Character>> adj = new HashMap<Character, HashSet<Character>>();
      HashMap<Character, Integer> indegree = new HashMap<Character, Integer>();
      Queue<Character> zeroIndegree = new LinkedList<>();
      ArrayList<Character> resultOrder = new ArrayList<>();

      if(words.length==1) return words[0];
      // then words has at length least 2
      // initialize adj. Make sure each char appear as key
      for(int index=0;index<words.length;index++){
        for(int i=0;i<words[index].length();i++){
          char c = words[index].charAt(i);
          adj.put(c, new HashSet<Character>());
        }
      }
      // construct the graph i.e. adj
      // by iteratively compare two consecutive words char by char
      for(int index=0;index<words.length-1;index++){
        String word1 = words[index];
        String word2 = words[index+1];
        for(int i=0;i<Math.min(word1.length(),word2.length());i++){
          if(word1.charAt(i)==word2.charAt(i)){
/** 04/03/2020 重写
比之前新加了几个test case 所以多加了这个if
input: ["abc", "ab"]
原来output:["abc"]
但Expect:[""]  实际上字典应该是不合法的  ab应该出现在abc之前

input: ["z", "z"]
expect: ["z"]

所以新加这个条件：当Word2已经走到尽头的时候word1还没走完，说明word1里完全包含了word2但居然出现在word2前面  违法！
*/
              if (i == word2.length() - 1 && word1.length() > word2.length()) {
                  return "";
              }
            continue;
          }else{ // there should be an edge word1.charAt(i) -> word2.charAt(i)
            HashSet<Character> out = adj.get(word1.charAt(i));
            out.add(word2.charAt(i));
            adj.put(word1.charAt(i),out);
            break;
          }
        }
      }

      // construct indegree
      for(char u: adj.keySet()){
        indegree.put(u,indegree.getOrDefault(u,0));
        for(char v:adj.get(u)){
          indegree.put(v, indegree.getOrDefault(v,0)+1);
        }
      }

      // construct zeroIndegree
      for(char u: indegree.keySet()){
        if(indegree.get(u)==0){
          zeroIndegree.add(u);
        }
      }

      // construct topological order
      while(!zeroIndegree.isEmpty()){
        char u = zeroIndegree.poll();
        for(char v: adj.get(u)){
          if(indegree.get(v)-1 ==0){
            zeroIndegree.add(v);
          }
          indegree.put(v,indegree.get(v)-1);
        }
        resultOrder.add(u);
      }

      if(resultOrder.size()!= adj.keySet().size()) return "";

      StringBuilder res = new StringBuilder();
      for(char ch:resultOrder){
        res.append(ch);
      }
      return res.toString();
    }

    public static void main(String[] args){
      String[] words =  new String[]{"z","z"};
      System.out.println(alienOrder(words));
    }
}
