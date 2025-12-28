package spy;

import java.util.Arrays;

public class Spy {
    public static boolean isSpy(long num) {
        int[] digits = getDigits(num);

        int sum = 0;
        int product = 1;

        for (int n : digits) {
            sum += n;
            product *= n;
        }

        return sum == product;
    }

    public static int[] getDigits(long num) {
        return Arrays.stream(String.valueOf(num).split("")).mapToInt(Integer::parseInt).toArray();
    }
}
