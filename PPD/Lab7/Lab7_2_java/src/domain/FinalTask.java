package domain;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author joj on 12/5/2019
 **/
public class FinalTask extends SimpleTask {
    private BigNumber result;

    public FinalTask() { }

    public BigNumber getResult() {
        return result;
    }

    @Override
    public void run() {
        this.queue.sort(Comparator.comparingInt(IntPositionPair::getPosition));
        int i = 0, carry = 0;
        StringBuilder stringBuilder = new StringBuilder();
        while(this.queue.size() > 0) {
            int finalI = i;
            List<IntPositionPair> filtered = this.queue.stream().filter(pair -> pair.getPosition() == finalI).collect(Collectors.toList());
            this.queue.removeAll(filtered);
            int sum = filtered.stream().map(IntPositionPair::getValue).reduce(0, Integer::sum);
            sum += carry;
            stringBuilder.append(sum % 10);
            carry = sum /10;
            i ++;
        }
        if (carry != 0)
            stringBuilder.append(carry);
        this.result = new BigNumber(stringBuilder.reverse().toString());
    }
}
