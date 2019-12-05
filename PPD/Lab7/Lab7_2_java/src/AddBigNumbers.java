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
        List<TwoNumbersTask> twoNumbersTasks = new ArrayList<>();
        for (int i = 0; i < this.bigNumbers.size() - 1; i+=2) {
            TwoNumbersTask task = new TwoNumbersTask(this.bigNumbers.get(i), this.bigNumbers.get(i + 1));
            twoNumbersTasks.add(task);
        }

        List<SimpleTask> simpleTasks = new ArrayList<>();
        for (int i = 0; i < twoNumbersTasks.size() - 1; i+=2) {
            SimpleTask task = new SimpleTask(2);
            twoNumbersTasks.get(i).run(task);
            twoNumbersTasks.get(i + 1).run(task);
            simpleTasks.add(task);
        }

        int level = 4;
        for (int i = 0; i < simpleTasks.size() - 1; i+=2) {
            SimpleTask task = new SimpleTask(level);
            simpleTasks.get(i).run(task);
            simpleTasks.get(i + 1).run(task);
            simpleTasks.add(task);
            level *= 2;
        }

        try{
            for(SimpleTask task : twoNumbersTasks)
                task.join();
            for(SimpleTask task : simpleTasks)
                task.join();
            if (this.bigNumbers.size() % 2 != 0) {
                NumberAndQueueTask numberAndQueueTask =
                        new NumberAndQueueTask(this.bigNumbers.get(this.bigNumbers.size() - 1));
                simpleTasks.get(simpleTasks.size() - 1).run(numberAndQueueTask);
                simpleTasks.get(simpleTasks.size() - 1).join();
                System.out.println(numberAndQueueTask.getResult());
            }
            else {
                System.out.println(simpleTasks.get(simpleTasks.size() - 1).getResult());
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
