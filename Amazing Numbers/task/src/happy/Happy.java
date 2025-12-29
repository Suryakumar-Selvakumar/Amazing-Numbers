package happy;

import spy.Spy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Happy {
    public static boolean isHappy(long num) {

        if (num < 7) {
            return false;
        }

        Set<Integer> seen = new HashSet<>();

        int[] digits;
        while (true) {
            digits = Spy.getDigits(num);

            int sum = Arrays.stream(digits).map(d -> d * d).sum();

            if (sum == 1) {
                return true;
            }

            if (seen.contains(sum)) {
                return false;
            }

            seen.add(sum);
            num = sum;
        }
    }
}
