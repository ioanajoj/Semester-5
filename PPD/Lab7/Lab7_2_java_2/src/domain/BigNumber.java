package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author joj on 12/13/2019
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

    public BigNumber(List<Integer> digits) {
        this.digits = digits;
        this.size = digits.size();
    }

    public int getDigit(int position) {
        return this.digits.get(position);
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return digits.stream().map(String::valueOf).collect(Collectors.joining(","));
    }
}