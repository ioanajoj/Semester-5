package domain;

import java.util.*;

/**
 * @author joj on 12/6/2019
 **/
public class DirectedGraph {
    private List<Integer> nodes;
    private Map<Integer, List<Integer>> edges;

    public DirectedGraph(int noOfNodes, boolean random) {
        if (random)
            this.makeRandomGraph(noOfNodes);
        else
            this.makeSimpleGraph();
    }

    private void makeEmptyGraph(int noOfNodes) {
        this.nodes = new ArrayList<>(noOfNodes);
        this.edges = new HashMap<>(noOfNodes);
        for (int node = 0; node < noOfNodes; node++) {
            this.nodes.add(node);
            this.edges.put(node, new ArrayList<>());
        }
    }

    public void addEdge(int node1, int node2) {
        this.edges.get(node1).add(node2);
    }

    public List<Integer> getNeighbourhood(int node) {
        return this.edges.get(node);
    }

    public int getSize() {
        return this.nodes.size();
    }

    public List<Integer> getNodes() {
        return this.nodes;
    }

    public void makeRandomGraph(int noOfNodes) {
        this.makeEmptyGraph(noOfNodes);

        java.util.Collections.shuffle(nodes);

        int node;
        for (node = 1; node < this.getSize(); node++)
            this.addEdge(node - 1, node);
        this.addEdge(node - 1, 0);

        Random random = new Random();
        int node1, node2;
        for (int i = 0; i < getSize()/2; i++) {
            node1 = random.nextInt(getSize() - 1);
            node2 = random.nextInt(getSize() - 1);
            this.addEdge(node1, node2);
        }
    }

    public void makeSimpleGraph() {
        int noOfNodes = 6;
        this.makeEmptyGraph(noOfNodes);
        this.addEdge(0, 1);
        this.addEdge(0, 2);
        this.addEdge(1, 2);
        this.addEdge(2, 4);
        this.addEdge(3, 0);
        this.addEdge(3, 2);
        this.addEdge(4, 1);
        this.addEdge(4, 3);
        this.addEdge(4, 5);
        this.addEdge(5, 3);
    }
}
