package domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author joj on 12/5/2019
 **/
public class SimpleTask extends Thread {
    List<IntPositionPair> queue;
    private int level;

    public SimpleTask(int level) {
        this.level = level;
        this.queue = new ArrayList<>();
    }

    public void enqueue(IntPositionPair pair) {
        this.queue.add(pair);
    }

    public void run(SimpleTask consumer) {
        int i = 0, carry = 0;
        do {
            int finalI = i;
            List<IntPositionPair> filtered = this.queue.stream()
                    .filter(pair -> pair.getPosition() == finalI).collect(Collectors.toList());
            this.queue.removeAll(filtered);
            int sum = filtered.stream().map(IntPositionPair::getValue).reduce(0, Integer::sum);
            sum += carry;
            consumer.enqueue(new IntPositionPair(i, sum % 10));
            carry = sum / 10;
            i++;
        } while (!finalQueue(2));
        consumer.enqueue(new IntPositionPair(-1, -1));
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
