package domain;

/**
 * @author joj on 12/5/2019
 **/
public class NumberTask extends SimpleTask {

    private BigNumber provider;

    public NumberTask(BigNumber provider) {
        this.provider = provider;
    }

    @Override
    public void run(SimpleTask consumer) {
        int number = this.provider.getNumber();
        int position = 0;
        while(number > 0) {
            int digit = number % 10;
            consumer.enqueue(new IntPositionPair(position, digit));
            number /= 10;
            position ++;
        }
    }
}
