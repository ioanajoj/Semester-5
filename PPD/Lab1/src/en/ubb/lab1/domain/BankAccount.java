package en.ubb.lab1.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * @author joj on 10/2/2019
 **/
public class BankAccount {

    private final int id;

    private List<OperationRecord> log;

    private int balance;

    private Semaphore mutex;

    public BankAccount(int id, final Semaphore mutex) {
        this.id = id;
        this.log = new ArrayList<>();
        this.balance = 0;
        this.mutex = mutex;
//        this.mutex = new Semaphore(1);
    }

    public void addOperationRecord( final OperationRecord operationRecord ) {
        try {
            this.mutex.acquire();
            this.log.add(operationRecord);
            for(int i = 0; i < 20; i++)
                Math.cosh(i);
            if( operationRecord.getSourceAccount().getId() == this.id )
                this.balance -= operationRecord.getAmount();
            else
                this.balance += operationRecord.getAmount();
            this.mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean consistencyCheck() {
        final int extractedAmount = this.log.stream()
                .filter(operationRecord -> operationRecord.getSourceAccount().getId() == id)
                .map(OperationRecord::getAmount).reduce(0, Integer::sum);
        final int addedAmount = this.log.stream()
                .filter(operationRecord -> operationRecord.getDestinationAccount().getId() == id)
                .map(OperationRecord::getAmount).reduce(0, Integer::sum);
        return addedAmount - extractedAmount == this.balance;
    }

    public int getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public List<OperationRecord> getLog() {
        return log;
    }

    @Override
    public String toString() {
        return "BankAccount " + id +
                " balance: " + balance +
                " log: " + log;
    }
}
