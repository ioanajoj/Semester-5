package domain;

/**
 * @author joj on 12/13/2019
 **/
public class PositionValuePair {
    private int position;
    private int value;

    public PositionValuePair(int position, int value) {
        this.position = position;
        this.value = value;
    }

    public static PositionValuePair getDefault() {
        return new PositionValuePair(-1, -1);
    }

    public int getPosition() {
        return position;
    }

    public int getValue() {
        return value;
    }

    public boolean isDefault() {
        return position == -1 && value == -1;
    }

    @Override
    public String toString() {
        return "(" + position + ", " + value + ')';
    }
}
