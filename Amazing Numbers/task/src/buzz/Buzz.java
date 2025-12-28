package buzz;

public class Buzz {
    public static boolean isBuzz(long num) {
        boolean isDivisibleBySeven = num % 7 == 0;
        boolean isEndingWithSeven = num % 10 == 7;

        return isDivisibleBySeven || isEndingWithSeven;
    }
}