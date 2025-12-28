package happy;

import spy.Spy;

import java.util.Arrays;

public class Happy {
    public static boolean isHappy(long num) {
        int[] digits = Spy.getDigits(num);

        int sum = Arrays.stream(digits).map(d -> d * d).sum();

        if (sum == 1) {
            return true;
        }

        if (sum == num) {
            return false;
        }

        return isHappy(num);
    }
}
