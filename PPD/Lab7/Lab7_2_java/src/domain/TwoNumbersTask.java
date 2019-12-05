package domain;

/**
 * @author joj on 12/5/2019
 **/
public class TwoNumbersTask extends SimpleTask {
    private BigNumber number1;
    private BigNumber number2;

    public TwoNumbersTask(BigNumber number1, BigNumber number2) {
        this.number1 = number1;
        this.number2 = number2;
    }

    @Override
    public void run(SimpleTask consumer) {
        int n1 = this.number1.getNumber();
        int n2 = this.number1.getNumber();
        int position = 0;
        while(n1 > 0 || n2 > 0) {
            if (n1 > 0)
                n1 = addToQueue(n1, position, consumer);
            if (n2 > 0)
                n2 = addToQueue(n2, position, consumer);
            position ++;
        }
    }

    private int addToQueue(int number, int position, SimpleTask consumer) {
        int digit = number % 10;
        consumer.enqueue(new IntPositionPair(position, digit));
        number /= 10;
        return number;
    }
}
