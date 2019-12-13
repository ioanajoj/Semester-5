import domain.BigNumber;
import domain.NumberAndQueueTask;
import domain.SimpleTask;
import domain.TwoNumbersTask;

import java.util.ArrayList;
import java.util.List;

/**
 * @author joj on 11/26/2019
 **/
public class AddBigNumbers {
    private List<BigNumber> bigNumbers;
    private List<SimpleTask> threads;
    private int noOfThreads;

    public AddBigNumbers(List<BigNumber> bigNumbers) {
        this.bigNumbers = bigNumbers;
        this.noOfThreads = bigNumbers.size() - 1;
        this.threads = new ArrayList<>(this.noOfThreads);
    }

    public void sum() {
        List<SimpleTask> tasks = new ArrayList<>();
        for (int i = 0; i < this.bigNumbers.size() - 1; i+=2) {
            TwoNumbersTask task = new TwoNumbersTask(this.bigNumbers.get(i), this.bigNumbers.get(i + 1));
            tasks.add(task);
        }

        int thNumber = 2;
        for (int i = 0; i < tasks.size() - 1; i+=2) {
            SimpleTask task = new SimpleTask(thNumber);
            tasks.get(i).run(task);
            tasks.get(i + 1).run(task);
            tasks.add(task);
            thNumber += 1;
        }

        try{
            for(SimpleTask task : tasks)
                task.join();
            if (this.bigNumbers.size() % 2 != 0) {
                NumberAndQueueTask numberAndQueueTask =
                        new NumberAndQueueTask(this.bigNumbers.get(this.bigNumbers.size() - 1));
                tasks.get(tasks.size() - 1).run(numberAndQueueTask);
                tasks.get(tasks.size() - 1).join();
                System.out.println(numberAndQueueTask.getResult());
            }
            else {
                System.out.println(tasks.get(tasks.size() - 1).getResult());
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
