package domain;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author joj on 12/5/2019
 **/
public class NumberAndQueueTask extends SimpleTask {
    private BigNumber bigNumber;

    public NumberAndQueueTask(BigNumber bigNumber) {
        super(0);
        this.bigNumber = bigNumber;
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
            if (i < this.bigNumber.getSize())
                sum += this.bigNumber.getDigit(finalI);
            stringBuilder.append(sum % 10);
            carry = sum /10;
            i ++;
        } while(!finalQueue(1));
        if (carry != 0)
            stringBuilder.append(carry);
        return new BigNumber(stringBuilder.reverse().toString());
    }
}
