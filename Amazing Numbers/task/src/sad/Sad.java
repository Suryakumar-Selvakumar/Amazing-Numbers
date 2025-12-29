package sad;

import happy.Happy;

public class Sad {
    public static boolean isSad(long num) {
        if (num < 7) {
            return false;
        }

        return !Happy.isHappy(num);
    }
}
