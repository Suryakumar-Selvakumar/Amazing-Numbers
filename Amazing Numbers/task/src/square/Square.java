package square;

public class Square {
    public static boolean isSquare(long num) {
        double sqrt = Math.sqrt((double) num);

        return (int) sqrt == sqrt;
    }
}
