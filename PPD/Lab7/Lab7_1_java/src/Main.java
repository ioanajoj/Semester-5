/**
 * @author joj on 11/26/2019
 **/
public class Main {
    public static void main(String[] args) {
        int size = 500000;

        Sequence sequence1 = new Sequence(size, 1);
        sequence1.prefixSum();

        Sequence sequence2 = new Sequence(size, 2);
        sequence2.prefixSum();

        Sequence sequence4 = new Sequence(size, 4);
        sequence4.prefixSum();

        Sequence sequence8 = new Sequence(size, 8);
        sequence8.prefixSum();
    }
}
