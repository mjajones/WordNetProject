import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* Below is my noun mapping, synset mapping, SAP object for calcs
 directed graph for WordNet structure and numberOfSynsets for keeping
 track of the number of synsets. */
public class WordNet {
    private final Map<String, List<Integer>> nounToSynsetIds;
    private final Map<Integer, String> idToSynset;
    private final SAP sap;
    private Digraph wordNetGraph;
    private int numberOfSynsets;

    // Constructor to initialize WordNet data structure
    public WordNet(String synsetsFile, String hypernymsFile) {
        if (synsetsFile == null || hypernymsFile == null) {
            throw new IllegalArgumentException("Input files cannot be null");
        }

        nounToSynsetIds = new HashMap<>();
        idToSynset = new HashMap<>();

        // Parse input files
        parseSynsets(synsetsFile);
        parseHypernyms(hypernymsFile);
        validateGraph();

        // Initialize SAP object
        sap = new SAP(wordNetGraph);
    }

    // Parse synsets file and build data structure
    private void parseSynsets(String synsetsFile) {
        In in = new In(synsetsFile);
        while (in.hasNextLine()) {
            String[] parts = in.readLine().split(",");
            int id = Integer.parseInt(parts[0]);
            String synset = parts[1];
            idToSynset.put(id, synset);

            for (String noun : synset.split(" ")) {
                nounToSynsetIds.computeIfAbsent(noun, k -> new ArrayList<>()).add(id);
            }
            numberOfSynsets++;
        }
    }

    // Parse hypernyms file and build directed graph
    private void parseHypernyms(String hypernymsFile) {
        wordNetGraph = new Digraph(numberOfSynsets);
        In in = new In(hypernymsFile);

        while (in.hasNextLine()) {
            String[] parts = in.readLine().split(",");
            int synsetId = Integer.parseInt(parts[0]);

            for (int i = 1; i < parts.length; i++) {
                int hypernymId = Integer.parseInt(parts[i]);
                wordNetGraph.addEdge(synsetId, hypernymId);
            }
        }
    }

    // Validate that graph is a DAG and has one root
    private void validateGraph() {
        DirectedCycle cycleFinder = new DirectedCycle(wordNetGraph);
        if (cycleFinder.hasCycle()) {
            throw new IllegalArgumentException("The digraph is not a DAG (contains cycles)");
        }

        int rootCount = 0;
        for (int v = 0; v < wordNetGraph.V(); v++) {
            if (wordNetGraph.outdegree(v) == 0) {
                rootCount++;
            }
        }

        if (rootCount != 1) {
            throw new IllegalArgumentException("The digraph must have exactly one root");
        }
    }


    // Returns all nouns
    public Iterable<String> nouns() {
        return nounToSynsetIds.keySet();
    }

    // Checks if a word is a noun
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException("Word cannot be null");
        return nounToSynsetIds.containsKey(word);
    }

    // Compute the shortest path length between two nouns
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("Both inputs must be valid WordNet nouns");
        }
        // Calculate shortest ancestral path SAP
        int shortestLength = Integer.MAX_VALUE;
        for (int idA : nounToSynsetIds.get(nounA)) {
            for (int idB : nounToSynsetIds.get(nounB)) {
                int length = sap.length(idA, idB);
                if (length != -1 && length < shortestLength) {
                    shortestLength = length;
                }
            }
        }
        return shortestLength == Integer.MAX_VALUE ? -1 : shortestLength;
    }

    // Find the common ancestor of two nouns in the shortest path
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("Both inputs must be valid WordNet nouns");
        }
        // Find the common ancestor
        int shortestLength = Integer.MAX_VALUE;
        int commonAncestor = -1;
        for (int idA : nounToSynsetIds.get(nounA)) {
            for (int idB : nounToSynsetIds.get(nounB)) {
                int length = sap.length(idA, idB);
                if (length != -1 && length < shortestLength) {
                    shortestLength = length;
                    commonAncestor = sap.ancestor(idA, idB);
                }
            }
        }
        return commonAncestor == -1 ? null : idToSynset.get(commonAncestor);
    }

    // Main method for testing
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java WordNet <synsets file> <hypernyms file>");
            return;
        }

        WordNet wordnet = new WordNet(args[0], args[1]);
        System.out.println("Nouns: " + wordnet.nouns());
    }
}