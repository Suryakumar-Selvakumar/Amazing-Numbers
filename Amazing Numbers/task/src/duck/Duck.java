package duck;

public class Duck {
    public static boolean isDuck(long num) {
        return String.valueOf(num).matches("\\d+0\\d*");
    }
}
