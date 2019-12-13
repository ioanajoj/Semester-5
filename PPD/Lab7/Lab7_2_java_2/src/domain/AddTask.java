package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author joj on 12/13/2019
 **/
public class AddTask extends Thread {
    private BigNumber bigNumber;
    private List<PositionValuePair> queue;
    private AddTask consumer;
    private Lock lock;
    private int number;
    private List<Integer> result;

    public AddTask(BigNumber bigNumber, int number) {
        this.bigNumber = bigNumber;
        this.queue = new ArrayList<>();
        this.consumer = null;
        this.lock = new ReentrantLock();
        this.number = number;
        this.result = new ArrayList<>();
    }

    private void enqueue(PositionValuePair pair) {
        lock.lock();
        this.queue.add(pair);
        if (!pair.isDefault())
            System.out.println("T" + this.number + " received - Pos: " + pair.getPosition() + " Sum: " + pair.getValue());
        lock.unlock();
    }

    public void setConsumer(AddTask consumer) {
        this.consumer = consumer;
    }

    public void initializeQueue(BigNumber bigNumber) {
        for (int i = 0; i < bigNumber.getSize(); i++) {
            this.queue.add(new PositionValuePair(i, bigNumber.getDigit(i)));
        }
        this.queue.add(PositionValuePair.getDefault());
    }


    public void run() {
        System.out.println("Thread " + this.number + " started.");
        int i = 0, carry = 0;
        do {
            PositionValuePair pair = null;
            while (pair == null) {
                lock.lock();
                for (PositionValuePair positionValuePair : this.queue) {
                    if (positionValuePair.getPosition() == i) {
                        pair = positionValuePair;
                    }
                }
                lock.unlock();
            }
            this.queue.remove(pair);
            int sum = pair.getValue() + carry;
            if (this.bigNumber.getSize() > i)
                sum += this.bigNumber.getDigit(i);
            if (this.consumer != null)
                this.consumer.enqueue(new PositionValuePair(i, sum % 10));
            else {
                this.result.add(sum % 10);
                System.out.println(sum % 10);
            }
            carry = sum / 10;
            i++;
        } while(!isFinalQueue());
        while (this.bigNumber.getSize() > i) {
            int sum = this.bigNumber.getSize() + carry;
            if (this.consumer != null)
                this.consumer.enqueue(new PositionValuePair(i, sum % 10));
            else {
                this.result.add(sum % 10);
                System.out.println(sum % 10);
            }
            carry = sum / 10;
            i++;
        }
        if (carry > 0) {
            if (this.consumer != null)
                this.consumer.enqueue(PositionValuePair.getDefault());
            else {
                this.result.add(carry);
                System.out.println(carry);
            }
        }
        if (this.consumer != null)
            consumer.enqueue(PositionValuePair.getDefault());
        System.out.println("Thread " + this.number + " finished.");
    }

    private boolean isFinalQueue() {
        return this.queue.size() == 1 && this.queue.get(0).isDefault();
    }

    public BigNumber getResult() {
        return new BigNumber(this.result);
    }
}
