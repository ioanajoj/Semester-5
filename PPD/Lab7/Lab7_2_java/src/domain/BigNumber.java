package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author joj on 11/26/2019
 **/
public class BigNumber {
    private List<Integer> digits;
    private int size;

    public BigNumber(String str) {
        this.size = str.length();
        this.digits = new ArrayList<>(size);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.reverse();
        str = sb.toString();
        for (int i = 0; i < size; i++)
            this.digits.add(i, Character.getNumericValue(str.charAt(i)));
    }

    public int getNumber() {
        int result = 0, position = 1, carry = 0, current;
        for (int i = 0; i < size; i++) {
            current = this.digits.get(i) + carry;
            result = result + ( position * ( current % 10 ) );
            carry = current / 10;
            position *= 10;
        }
        if (carry != 0)
            result += position * carry;
        return result;
    }

    public int getSize() {
        return size;
    }
}
