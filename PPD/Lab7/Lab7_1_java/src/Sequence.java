import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author joj on 11/26/2019
 **/
public class Sequence {
    private List<Integer> sequence;
    private int size;
    private int noOfThreads;

    public Sequence(int size, int noOfThreads) {
        this.size = size;
        this.noOfThreads = noOfThreads;
        this.sequence = new ArrayList<>();
        for (int i = 0; i < size; i++)
            this.sequence.add(i + 1);
    }

    void prefixSum() {
        int k;
        long startTime = System.currentTimeMillis();

        for (k = 1; k < this.size; k *= 2) {

            ExecutorService pool = Executors.newFixedThreadPool(this.noOfThreads);

            for(int i = 2 * k; i <= this.size; i += 2 * k) {
                int finalI = i;
                int finalK = k;
                pool.submit( () ->
                        this.sequence.set(finalI - 1,
                                this.sequence.get(finalI - 1) + this.sequence.get(finalI - finalK - 1)));
            }

            pool.shutdown();

            try {
                pool.awaitTermination(100, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (k = k / 4; k > 0; k /= 2) {
            ExecutorService pool = Executors.newFixedThreadPool(this.noOfThreads);

            for (int i = 3 * k; i <= size; i += 2 * k) {
                int finalI = i;
                int finalK = k;
                pool.submit( () ->
                        this.sequence.set(finalI - 1,
                                this.sequence.get(finalI - 1) + this.sequence.get(finalI - finalK - 1)));
            }

            pool.shutdown();

            try {
                pool.awaitTermination(100, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        System.out.println("Computed prefix sums with " + this.noOfThreads + " threads in " + time + " milliseconds.");
    }

    @Override
    public String toString() {
        return "sequence=" + sequence +
                '}';
    }

    public int getValue(int index){
        return this.sequence.get(index);
    }

    public int getSize() {
        return size;
    }
}
