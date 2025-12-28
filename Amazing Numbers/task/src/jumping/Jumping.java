package jumping;

import spy.Spy;

public class Jumping {
    public static boolean isJumping(long num) {
        int[] digits = Spy.getDigits(num);

        if (digits.length <= 3) {
            return false;
        }

        boolean diffNotOne = false;
        for (int i = 1; i < digits.length; i++) {
            if (Math.abs(digits[i] - digits[i - 1]) != 1) {
                diffNotOne = true;
                break;
            }
        }

        return !diffNotOne;
    }
}