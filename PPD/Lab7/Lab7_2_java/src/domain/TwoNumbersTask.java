package domain;

/**
 * @author joj on 12/5/2019
 **/
public class TwoNumbersTask extends SimpleTask {
    private BigNumber number1;
    private BigNumber number2;

    public TwoNumbersTask(BigNumber number1, BigNumber number2) {
        super(2);
        this.number1 = number1;
        this.number2 = number2;
    }

    @Override
    public void run(SimpleTask consumer) {
        int position = 0, carry = 0;
        while(position < this.number1.getSize() && position < this.number2.getSize()) {
            int sum = this.number1.getDigit(position) + this.number2.getDigit(position) + carry;
            IntPositionPair pair =
                    new IntPositionPair(position, sum % 10);
            consumer.enqueue(pair);
            carry = sum / 10;
            position ++;
        }
        while( position < this.number1.getSize() ) {
            int sum = this.number1.getDigit(position) + carry;
            IntPositionPair pair =
                    new IntPositionPair(position, sum % 10);
            consumer.enqueue(pair);
            carry = sum / 10;
            position ++;
        }
        while( position < this.number2.getSize() ) {
            int sum = this.number2.getDigit(position) + carry;
            IntPositionPair pair =
                    new IntPositionPair(position, sum % 10);
            consumer.enqueue(pair);
            carry = sum / 10;
            position ++;
        }
        if (carry > 0) {
            consumer.enqueue(new IntPositionPair(position, carry));
        }
        consumer.enqueue(IntPositionPair.getDefault());
        System.out.println("Task finished: " + number1 + " + " + number2);
    }

}
