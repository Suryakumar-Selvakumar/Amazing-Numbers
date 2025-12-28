package palindrome;

public class Palindrome {
    public static boolean isPalindrome(long num) {
        String input = String.valueOf(num);
        int l = 0, r = input.length() - 1;

        while (l < r) {
            if (input.charAt(l) != input.charAt(r)) {
                return false;
            }

            l++;
            r--;
        }

        return true;
    }
}
