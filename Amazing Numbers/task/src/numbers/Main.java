package numbers;

import spy.Spy;
import buzz.Buzz;
import duck.Duck;
import gapful.Gapful;
import palindrome.Palindrome;
import sunny.Sunny;
import square.Square;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static boolean isProgramOn = true;
    public static String[] properties = new String[]{"BUZZ", "DUCK", "PALINDROMIC", "GAPFUL", "SPY", "SQUARE", "SUNNY", "EVEN", "ODD"};

    public static void main(String[] args) {
//        write your code here
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to Amazing Numbers!");
        printInstructions();

        while (isProgramOn) {
            System.out.print("Enter a request: ");

            String[] inputs = sc.nextLine().trim().split(" ");

            if (inputs[0].isEmpty()) {
                printInstructions();
                continue;
            }

            long num = processFirstParameter(inputs[0]);
            if (num == Long.MIN_VALUE) {
                continue;
            }

            if (inputs.length == 1) {
                System.out.printf("""
                                       \nProperties of %d
                                       buzz: %b
                                       duck: %b
                                palindromic: %b
                                     gapful: %b
                                        spy: %b
                                     square: %b
                                      sunny: %b
                                       even: %b
                                        odd: %b
                                
                                """, num, Buzz.isBuzz(num),
                        Duck.isDuck(num),
                        Palindrome.isPalindrome(num),
                        Gapful.isGapful(num),
                        Spy.isSpy(num),
                        Square.isSquare(num),
                        Sunny.isSunny(num),
                        isEven(num),
                        isOdd(num));
                continue;
            }

            int count = processSecondParameter(inputs[1]);
            if (count == Integer.MIN_VALUE) {
                continue;
            }

            if (inputs.length == 2) {
                processNumbers(num, count, null, null);
                continue;
            }

            String propertyFilter1 = processPropertyParameter(inputs[2]);

            if (inputs.length == 3) {
                if (propertyFilter1.equals("WRONG")) {
                    printErrorMessage(inputs[2], null);
                    continue;
                }
                processNumbers(num, count, propertyFilter1, null);
                continue;
            }

            String propertyFilter2 = processPropertyParameter(inputs[3]);

            if (propertyFilter1.equals("WRONG") && propertyFilter2.equals("WRONG")) {
                printErrorMessage(inputs[2], inputs[3]);
                continue;
            }

            if (propertyFilter1.equals("WRONG")) {
                printErrorMessage(inputs[2], null);
                continue;
            }

            if (propertyFilter2.equals("WRONG")) {
                printErrorMessage(inputs[3], null);
                continue;
            }

            String processingResult = processMultipleProperties(propertyFilter1, propertyFilter2);
            if (processingResult.equals("MUTUALLY_EXCLUSIVE")) {
                System.out.printf("""
                        \nThe request contains mutually exclusive properties: [%s, %s]
                        There are no numbers with these properties.
                        
                        """, propertyFilter1, propertyFilter2);
                continue;
            }

            if (processingResult.equals("EQUAL")) {
                processNumbers(num, count, propertyFilter1, null);
                continue;
            }

            processNumbers(num, count, propertyFilter1, propertyFilter2);
        }
    }

    public static boolean isEven(long num) {
        return num % 2 == 0;
    }

    public static boolean isOdd(long num) {
        return num % 2 != 0;
    }

    public static boolean isNatural(long num) {
        return num >= 1;
    }

    public static long processFirstParameter(String input) {
        boolean isDecimal = false;
        long num = Long.MIN_VALUE;

        try {
            num = Long.parseLong(input);
        } catch (NumberFormatException ignored) {
            isDecimal = true;
        }

        if (isDecimal || (!isNatural(num) && num < 0)) {
            System.out.println("\nThe first parameter should be a natural number or zero.\n");
            return Long.MIN_VALUE;
        } else if (num == 0) {
            System.out.println("\nGoodbye!");
            isProgramOn = false;
            return Long.MIN_VALUE;
        }

        return num;
    }

    public static int processSecondParameter(String input) {
        boolean isInvalidNum = false;
        int count = Integer.MIN_VALUE;

        try {
            count = Integer.parseInt(input);
        } catch (NumberFormatException ignored) {
            isInvalidNum = true;
        }

        if (isInvalidNum || !isNatural(count)) {
            System.out.println("The second parameter should be a natural number.");
            count = Integer.MIN_VALUE;
        }

        return count;
    }

    public static String processPropertyParameter(String input) {
        input = input.toUpperCase();

        if (Arrays.stream(properties).noneMatch(input::equals)) {
            return "WRONG";
        }

        return input;
    }

    public static String processMultipleProperties(String property1, String property2) {
        if (property1.equals(property2)) {
            return "EQUAL";
        }

        String combination = property1 + "_" + property2;

        return switch (combination) {
            case "ODD_EVEN", "EVEN_ODD", "DUCK_SPY", "SPY_DUCK", "SUNNY_SQUARE", "SQUARE_SUNNY" -> "MUTUALLY_EXCLUSIVE";
            default -> "";
        };

    }

    public static void processNumbers(long num, int count, String propertyFilter1, String propertyFilter2) {
        System.out.println();

        int i = 0;
        while (i < count) {
            if (propertyFilter2 != null && propertyFilter1 != null &&
                    (!containsFilterProperty(num, propertyFilter1) ||
                            !containsFilterProperty(num, propertyFilter2))) {
                num++;
                continue;
            }

            if (propertyFilter1 != null && !containsFilterProperty(num, propertyFilter1)) {
                num++;
                continue;
            }

            String numIs = String.format("%" + 16 + "s is", num);
            ArrayList<String> numProperties = new ArrayList<>(Arrays.asList(properties));

            if (!isEven(num)) {
                numProperties.remove("EVEN");
            }

            if (!isOdd(num)) {
                numProperties.remove("ODD");
            }

            if (!Buzz.isBuzz(num)) {
                numProperties.remove("BUZZ");
            }

            if (!Duck.isDuck(num)) {
                numProperties.remove("DUCK");
            }

            if (!Palindrome.isPalindrome(num)) {
                numProperties.remove("PALINDROMIC");
            }

            if (!Gapful.isGapful(num)) {
                numProperties.remove("GAPFUL");
            }

            if (!Spy.isSpy(num)) {
                numProperties.remove("SPY");
            }

            if (!Sunny.isSunny(num)) {
                numProperties.remove("SUNNY");
            }

            if (!Square.isSquare(num)) {
                numProperties.remove("SQUARE");
            }

            System.out.printf("%s %s\n", numIs, String.join(", ", numProperties).toLowerCase());
            num++;
            i++;
        }

        System.out.println();
    }

    static boolean containsFilterProperty(long num, String property) {
        return switch (property) {
            case "ODD" -> isOdd(num);
            case "EVEN" -> isEven(num);
            case "BUZZ" -> Buzz.isBuzz(num);
            case "PALINDROMIC" -> Palindrome.isPalindrome(num);
            case "DUCK" -> Duck.isDuck(num);
            case "GAPFUL" -> Gapful.isGapful(num);
            case "SPY" -> Spy.isSpy(num);
            case "SQUARE" -> Square.isSquare(num);
            case "SUNNY" -> Sunny.isSunny(num);
            default -> false;
        };
    }

    public static void printInstructions() {
        System.out.println("""
                \nSupported requests:
                  - enter a natural number to know its properties;\s
                  - enter two natural numbers to obtain the properties of the list:
                    * the first parameter represents a starting number;
                    * the second parameter shows how many consecutive numbers are to be printed;
                  - two natural numbers and a property to search for;
                  - two natural numbers and two properties to search for;
                  - separate the parameters with one space;
                  - enter 0 to exit.
                """);
    }

    public static void printErrorMessage(String property1, String property2) {
        if (property2 == null) {
            System.out.printf("""
                    \nThe property [%s] is wrong.
                    Available properties: %s
                    
                    """, property1.toUpperCase(), Arrays.toString(properties));
            return;
        }

        System.out.printf("""
                \nThe property [%s, %s] are wrong.
                Available properties: %s
                
                """, property1.toUpperCase(), property2.toUpperCase(), Arrays.toString(properties));
    }
}