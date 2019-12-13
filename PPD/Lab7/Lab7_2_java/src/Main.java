import domain.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author joj on 11/26/2019
 **/
public class Main {
    public static void main(String[] args) {
        BigNumber bigNumber1 = new BigNumber("1234");
        BigNumber bigNumber2 = new BigNumber("567");
        BigNumber bigNumber3 = new BigNumber("89");
        BigNumber bigNumber4 = new BigNumber("1023");
        BigNumber bigNumber5 = new BigNumber("456");
        BigNumber bigNumber6 = new BigNumber("789");
        BigNumber bigNumber7 = new BigNumber("2104");
        BigNumber bigNumber8 = new BigNumber("852");

        List<BigNumber> bigNumberList = new ArrayList<>();
        bigNumberList.add(bigNumber1);
        bigNumberList.add(bigNumber2);
        bigNumberList.add(bigNumber3);
        bigNumberList.add(bigNumber4);
        bigNumberList.add(bigNumber5);
        bigNumberList.add(bigNumber6);
        bigNumberList.add(bigNumber7);
        bigNumberList.add(bigNumber8);

        AddBigNumbers addBigNumbers = new AddBigNumbers(bigNumberList);
        addBigNumbers.sum();
    }
}
