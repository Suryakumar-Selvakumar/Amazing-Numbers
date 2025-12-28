package sunny;

import square.Square;

public class Sunny {
    public static boolean isSunny(long num) {
        long nextNum = num + 1;

        return Square.isSquare(nextNum);
    }
}
