import domain.DirectedGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author joj on 12/6/2019
 **/
public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello World!");

        DirectedGraph directedGraph = new DirectedGraph(1000, true);

        findHamiltonianCycle(directedGraph, 1);
        findHamiltonianCycle(directedGraph, 2);
        findHamiltonianCycle(directedGraph, 4);
        findHamiltonianCycle(directedGraph, 8);

        System.out.println("Bye World!");
    }

    public static void findHamiltonianCycle(DirectedGraph graph, int threadCount) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(threadCount);
        List<Integer> result = new ArrayList<>(graph.getSize());
        Boolean found = false;
        Lock lock = new ReentrantLock();

        long start = System.currentTimeMillis();

        for (int node = 0; node < graph.getSize(); node++)
            pool.execute(new FindHamiltonianCycle(graph, node, result, found, lock));

        shutdownAndAwaitTermination(pool);

        long end = System.currentTimeMillis();
        System.out.println("Threads: " + threadCount + " time: " + (end - start));
    }

    private static void shutdownAndAwaitTermination(ExecutorService pool) {
        pool.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                pool.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!pool.awaitTermination(60, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            pool.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }
}
