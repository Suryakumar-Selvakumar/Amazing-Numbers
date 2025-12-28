package jumping;

import spy.Spy;

class Jumping {
    public static boolean isJumping(long num) {
        int[] digits = Spy.getDigits(num);

        boolean diffGreaterThanOne = false;
        for (int i = 1; i < digits.length; i++) {
            if (Math.abs(digits[i] - digits[i - 1]) > 1) {
                diffGreaterThanOne = true;
                break;
            }
        }

        return diffGreaterThanOne || digits.length == 1;
    }
}