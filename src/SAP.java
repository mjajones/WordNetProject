import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class SAP {
    private final Digraph graph;

    // Constructor initializes SAP with copy of given digraph
    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException("Graph cannot be null");
        this.graph = new Digraph(G); // copy
    }

    // Computer length of shortest ancestral path between two vertices v and w
    public int length(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(graph, w);

        int shortestLength = Integer.MAX_VALUE;
        for (int i = 0; i < graph.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                int length = bfsV.distTo(i) + bfsW.distTo(i);
                if (length < shortestLength) {
                    shortestLength = length;
                }
            }
        }
        // Return shortest length
        return shortestLength == Integer.MAX_VALUE ? -1 : shortestLength;
    }

    // Find common ancestor in shortest ancestral path
    public int ancestor(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(graph, w);

        int shortestLength = Integer.MAX_VALUE;
        int commonAncestor = -1;
        for (int i = 0; i < graph.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                int length = bfsV.distTo(i) + bfsW.distTo(i);
                if (length < shortestLength) {
                    shortestLength = length;
                    commonAncestor = i;
                }
            }
        }
        // Return common ancestor
        return commonAncestor;
    }

    // Validate vertex is within bounds
    private void validateVertex(int vertex) {
        if (vertex < 0 || vertex >= graph.V()) {
            throw new IllegalArgumentException("Vertex out of bounds: " + vertex);
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        // Read vertex pairs from input and compute SAP
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}


