import java.util.ArrayList;

public class Outcast {
    private final WordNet wordnet;

    // Constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // Given array of WordNet nouns, return  outcast
    public String outcast(String[] nouns) {
        int maxDistance = -1;
        String outcast = null;

        // Loop through each noun in array
        for (String nounA : nouns) {
            if (!wordnet.isNoun(nounA)) {
                System.err.println("Invalid noun: " + nounA);
                continue; // I added this to skip invalid nouns
            }

            int distanceSum = 0;
            for (String nounB : nouns) {
                if (!wordnet.isNoun(nounB)) {
                    continue; // added to skip invalid nouns
                }
                // Calc dist between nounA and nounB
                distanceSum += wordnet.distance(nounA, nounB);
            }

            // Update outcast if current noun has higher distanceSum
            if (distanceSum > maxDistance) {
                maxDistance = distanceSum;
                outcast = nounA;
            }
        }
        // Retrun correct outcast
        return outcast;
    }

    // Test client
    public static void main(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Usage: java Outcast <synsets file> <hypernyms file> <outcast file>");
        }

        // Initialize WordNet with synsets/hypernyms files
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);

        // Read nouns from  outcast file
        In in = new In(args[2]);
        ArrayList<String> nouns = new ArrayList<>();
        while (!in.isEmpty()) {
            nouns.add(in.readString());
        }

        // Find outcast noun/ print result
        String result = outcast.outcast(nouns.toArray(new String[0]));
        StdOut.println("Outcast: " + result);
    }
}