package gapful;

public class Gapful {
    public static boolean isGapful(long num) {
        if (num < 100) {
            return false;
        }

        String numStr = String.valueOf(num);
        long concatNum = Long.parseLong("" + numStr.charAt(0) + numStr.charAt(numStr.length() - 1));

        return num % concatNum == 0;
    }
}
