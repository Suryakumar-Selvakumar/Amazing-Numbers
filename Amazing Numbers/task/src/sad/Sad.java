package sad;

import happy.Happy;

public class Sad {
    public static boolean isSad(long num) {
        return !Happy.isHappy(num);
    }
}
