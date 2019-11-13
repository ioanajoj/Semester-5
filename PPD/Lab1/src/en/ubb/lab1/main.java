package en.ubb.lab1;

import en.ubb.lab1.domain.BankAccount;
import en.ubb.lab1.domain.OperationRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author joj on 10/2/2019
 **/
public class main {
    public static void main(String[] args) {

        final Semaphore bankMutex = new Semaphore(1);

        // initialize bank
        final List<BankAccount> bankAccounts = new ArrayList<>();
        for( int i = 0; i < 10; i++ )
            bankAccounts.add( new BankAccount(i, bankMutex) );

        // randomly pick accounts and perform operations
        final List<OperationRecord> operationRecords = new ArrayList<>();
        for( int i = 0; i < 7000000; i++ ) {
            final int sourceId = (int) (Math.random() * 10);
            int destinationID = (int) (Math.random() * 10);
            while( sourceId == destinationID )
                destinationID = (int) (Math.random() * 10);
            final int amount = (int) (Math.random() * 500 + 1);
            final OperationRecord operationRecord =
                    new OperationRecord(bankAccounts.get(sourceId), bankAccounts.get(destinationID), amount);
            operationRecords.add(operationRecord);
        }

//        ExecutorService executorService = Executors.newFixedThreadPool(1);

//        try {
//            executorService.invokeAll(operationRecords);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        for(OperationRecord operationRecord: operationRecords)
//            executorService.submit(operationRecord);
//        // Recomended bai Orăcăl
//        executorService.shutdown();
//

        List<Thread> threads = new ArrayList<>();
        final int noOfThreads = 8;
        final int noOfOperations = operationRecords.size() / noOfThreads;
        for(int i = 0; i < noOfThreads; i++) {
            final int x = noOfOperations * i;
            int y = noOfOperations * i - 1;
            if (y < noOfThreads) {
                y = noOfOperations - 1;
            }
            int finalY = y;
            threads.add(new Thread(() -> {
                for(int j = x; j <= finalY; j++) {
                    operationRecords.get(j).call();
                }
            }));
        }

        long startTime = System.currentTimeMillis();
        threads.forEach(Thread::start);
        try {
            for (Thread thread : threads) {
                    thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        long endTime = System.currentTimeMillis();

        boolean validation = true;
        for(BankAccount bankAccount : bankAccounts) {
            validation = validation && bankAccount.consistencyCheck();
        }
        System.out.println("Validation completed with status: " + validation);

        long time = endTime - startTime;

        System.out.println("Ended in " + time + " milliseconds");
    }
}
