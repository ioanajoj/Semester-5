package domain;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * @author joj on 12/5/2019
 **/
public class SimpleTask extends Thread {
    List<IntPositionPair> queue;
    private Lock lock;
    private int number;

    public SimpleTask(int number) {
        this.queue = new ArrayList<>();
        this.lock = new ReentrantLock();
        this.number = number;
    }

    public void enqueue(IntPositionPair pair) {
        lock.lock();
        this.queue.add(pair);
        if (!pair.isDefault())
            System.out.println("T" + this.number + " received - Pos: " + pair.getPosition() + " Sum: " + pair.getValue());
        lock.unlock();
    }

    public int getRandomIndex() {
        List<IntPositionPair> filtered = this.queue.stream()
                .filter(pair -> pair.getPosition() != -1).collect(Collectors.toList());
        if (filtered.size() == 0)
            return -1;
        Collections.shuffle(filtered);
        return filtered.get(0).getPosition();
    }

    public void run(SimpleTask consumer) {
        int i = 0, carry = 0;
        do {
            int finalI = i;
            List<IntPositionPair> filtered;
            do {
                filtered = this.queue.stream()
                        .filter(pair -> pair.getPosition() == finalI).collect(Collectors.toList());
            } while (filtered.size() < 2);
            this.queue.removeAll(filtered);
            int sum = filtered.stream().map(IntPositionPair::getValue).reduce(0, Integer::sum);
            sum += carry;
            consumer.enqueue(new IntPositionPair(i, sum % 10));
            carry = sum / 10;
            i++;
        } while (!finalQueue(2));
        if (carry > 0) {
            consumer.enqueue(new IntPositionPair(i, carry));
        }
        consumer.enqueue(IntPositionPair.getDefault());
        System.out.println("Simple task finished");
    }

    public BigNumber getResult() {
        this.queue.sort(Comparator.comparingInt(IntPositionPair::getPosition));
        int i = 0, carry = 0;
        StringBuilder stringBuilder = new StringBuilder();
        do {
            int finalI = i;
            List<IntPositionPair> filtered = this.queue.stream().filter(pair -> pair.getPosition() == finalI).collect(Collectors.toList());
            this.queue.removeAll(filtered);
            int sum = filtered.stream().map(IntPositionPair::getValue).reduce(0, Integer::sum);
            sum += carry;
            stringBuilder.append(sum % 10);
            carry = sum /10;
            i ++;
        } while(!finalQueue(2));
        if (carry != 0)
            stringBuilder.append(carry);
        return new BigNumber(stringBuilder.reverse().toString());
    }

    boolean finalQueue(int count) {
        return this.queue.size() == count && this.queue.stream().filter(IntPositionPair::isDefault).count() == count;
    }
}
