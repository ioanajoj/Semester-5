package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author joj on 12/5/2019
 **/
public class SimpleTask extends Thread {
    List<IntPositionPair> queue;
    private boolean finished = false;

    public SimpleTask() {
        this.queue = new ArrayList<>();
    }

    public void enqueue(IntPositionPair pair) {
        this.queue.add(pair);
    }

    public void run(SimpleTask consumer) {
        int i = 0, carry = 0;
        while(this.queue.size() > 0) {
            int finalI = i;
            List<IntPositionPair> filtered = this.queue.stream().filter(pair -> pair.getPosition() == finalI).collect(Collectors.toList());
            this.queue.removeAll(filtered);
            int sum = filtered.stream().map(IntPositionPair::getValue).reduce(0, Integer::sum);
            sum += carry;
            consumer.enqueue(new IntPositionPair(i, sum % 10));
            carry = sum /10;
            i ++;
        }
    }
}
