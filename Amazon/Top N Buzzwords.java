/*
We must store both the frequency and the times it appears in different quotes for each word. 
Thus for each word we create an entry in HashMap, with the word being the key, and an int array being the value indicating how frequent it got mentioned and how many time it appears in different quotes.
First we need to go through all the quotes to construct that HashMap.
Then in order to output the top N toys, we push each word to a min PriorityQueue with custom comparator that first comparing the frequence and then the quote number and then the string itself.
We maintain the min PQ to have the size of N.
Finally when outputing the result, we have to reverse the the output array since the PQ is a min heap.
*/

// "static void main" must be defined in a public class.
public class Main {
  public static void main(String[] args) {
    System.out.println(
      topToys(6, 2, new String[]{"elmo", "elsa", "legos", "drone", "tablet", "warcraft"}, 6,
        new String[]{
          "Elmo is the hottest of the season! Elmo will be on every kid's wishlist!",
          "The new Elmo dolls are super high quality",
          "Expect the Elsa dolls to be very popular this year, Elsa!",
          "Elsa and Elmo are the toys I'll be buying for my kids, Elsa is good",
          "For parents of older kids, look into buying them a drone",
          "Warcraft is slowly rising in popularity ahead of the holiday season"
        }));
  }

  public static List<String> topToys(int numToys, int topToys, String[] toys, int numQuotes,
    String[] quotes) {
    Map<String, int[]> freq = new HashMap<>();
    for (String toy : toys) {
      freq.put(toy, new int[]{0, 0});
    }

    for (String quote : quotes) {
      Set<String> used = new HashSet<>();

      String[] words = quote.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\W+");
      for (String word : words) {
        if (!freq.containsKey(word)) {
          continue;
        }

        int[] nums = freq.get(word);

        nums[0]++;
        if (!used.contains(word)) {
          nums[1]++;
        }

        used.add(word);
      }
    }

    PriorityQueue<String> pq = new PriorityQueue<>((t1, t2) -> {
      if (freq.get(t1)[0] != freq.get(t2)[0]) {
        return freq.get(t1)[0] - freq.get(t2)[0];
      }

      if (freq.get(t1)[1] != freq.get(t2)[1]) {
        return freq.get(t1)[1] - freq.get(t2)[1];
      }

      return t2.compareTo(t1);
    });

    if (topToys > numToys) {
      for (String toy : freq.keySet()) {
        if (freq.get(toy)[0] > 0) {
          pq.add(toy);
        }
      }
    } else {
      for (String toy : toys) {
        pq.add(toy);

        if (pq.size() > topToys) {
          pq.poll();
        }
      }
    }

    List<String> output = new ArrayList<>();
    while (!pq.isEmpty()) {
      output.add(pq.poll());
    }

    Collections.reverse(output);

    return output;
  }
}