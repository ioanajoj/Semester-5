import domain.DirectedGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 * @author joj on 12/6/2019
 **/
public class FindHamiltonianCycle implements Runnable{
    private DirectedGraph graph;
    private int startingNode;
    private List<Integer> result;
    private Lock lock;

    public FindHamiltonianCycle(DirectedGraph graph, int startingNode, List<Integer> result, Lock lock) {
        this.graph = graph;
        this.startingNode = startingNode;
        this.result = result;
        this.lock = lock;
    }

    @Override
    public void run() {
        List<Integer> workingPath = new ArrayList<>();
        visit(workingPath, startingNode);
    }

    private void visit(List<Integer> path, int node) {
        path.add(node);
        if (path.size() == graph.getSize())
            if (graph.getNeighbourhood(node).contains(startingNode)) {
                setResult(path);
            }
        for (int neighbour : graph.getNeighbourhood(node)) {
            if (!path.contains(neighbour))
                visit(new ArrayList<>(path), neighbour);
        }
    }

    private void setResult(List<Integer> path) {
        this.lock.lock();
        this.result.clear();
        this.result = new ArrayList<>(path);
        this.result.addAll(path);
        this.lock.unlock();
    }
}
