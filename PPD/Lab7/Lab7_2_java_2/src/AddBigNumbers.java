import domain.AddTask;
import domain.BigNumber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author joj on 12/13/2019
 **/
public class AddBigNumbers {
    private List<BigNumber> bigNumbers;
    private List<AddTask> threads;
    private int noOfThreads;

    public AddBigNumbers(List<BigNumber> bigNumbers) {
        this.bigNumbers = bigNumbers;
        this.noOfThreads = bigNumbers.size() - 1;
        this.threads = new ArrayList<>(this.noOfThreads);
    }

    public void sum() throws InterruptedException {
        AddTask initialTask = new AddTask(bigNumbers.get(1), 1);
        initialTask.initializeQueue(bigNumbers.get(0));
        this.threads.add(initialTask);

        for (int i = 1; i < this.noOfThreads; i++) {
            AddTask addTask = new AddTask(bigNumbers.get(i + 1), i + 1);
            this.threads.get(i - 1).setConsumer(addTask);
            this.threads.add(addTask);
        }

        Collections.shuffle(this.threads);
        ExecutorService service = Executors.newFixedThreadPool(this.threads.size());
        this.threads.forEach(service::execute);

        for (AddTask thread : this.threads) {
            thread.join();
        }

        BigNumber result = this.threads.get(this.noOfThreads - 1).getResult();
        System.out.println(result);
    }

}
