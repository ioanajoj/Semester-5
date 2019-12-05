package domain;

/**
 * @author joj on 11/26/2019
 **/
public class IntPositionPair {
    private int position;
    private int value;

    public IntPositionPair(int position, int value) {
        this.position = position;
        this.value = value;
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
