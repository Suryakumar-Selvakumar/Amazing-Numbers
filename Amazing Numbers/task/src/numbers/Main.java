package numbers;

import happy.Happy;
import jumping.Jumping;
import sad.Sad;
import spy.Spy;
import buzz.Buzz;
import duck.Duck;
import gapful.Gapful;
import palindrome.Palindrome;
import sunny.Sunny;
import square.Square;

import java.util.*;
import java.text.NumberFormat;

public class Main {
    static boolean isProgramOn;
    static String[] properties;
    static ArrayList<String[]> mutuallyExclusiveProperties;
    static NumberFormat formatter;

    static {
        isProgramOn = true;
        properties = new String[]{"BUZZ", "DUCK", "PALINDROMIC", "GAPFUL", "SPY", "SQUARE", "SUNNY", "JUMPING", "HAPPY", "SAD", "EVEN", "ODD"};
        mutuallyExclusiveProperties = new ArrayList<>();
        mutuallyExclusiveProperties.add(new String[]{"ODD", "EVEN"});
        mutuallyExclusiveProperties.add(new String[]{"DUCK", "SPY"});
        mutuallyExclusiveProperties.add(new String[]{"SUNNY", "SQUARE"});
        formatter = NumberFormat.getNumberInstance(Locale.US);
    }

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
                                       \nProperties of %s
                                       buzz: %b
                                       duck: %b
                                palindromic: %b
                                     gapful: %b
                                        spy: %b
                                     square: %b
                                      sunny: %b
                                    jumping: %b
                                      happy: %b
                                        sad: %b
                                       even: %b
                                        odd: %b
                                
                                """, formatter.format(num), Buzz.isBuzz(num),
                        Duck.isDuck(num),
                        Palindrome.isPalindrome(num),
                        Gapful.isGapful(num),
                        Spy.isSpy(num),
                        Square.isSquare(num),
                        Sunny.isSunny(num),
                        Jumping.isJumping(num),
                        Happy.isHappy(num),
                        Sad.isSad(num),
                        isEven(num),
                        isOdd(num));
                continue;
            }

            int count = processSecondParameter(inputs[1]);
            if (count == Integer.MIN_VALUE) {
                continue;
            }

            if (inputs.length == 2) {
                processNumbers(num, count, null);
                continue;
            }

            ArrayList<String> propertyFilters = new ArrayList<>(Arrays.stream(Arrays.copyOfRange(inputs, 2, inputs.length)).toList());
            String processingResult = processMultipleProperties(propertyFilters);

            if (processingResult.equals("WRONG") || processingResult.equals("MUTUALLY_EXCLUSIVE")) {
                continue;
            }

            processNumbers(num, count, propertyFilters);
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

    public static boolean doesPropertyExist(String input) {
        input = input.toUpperCase();

        return Arrays.asList(properties).contains(input);
    }

    public static String processMultipleProperties(ArrayList<String> propertyFilters) {
        ArrayList<String> wrongProperties = new ArrayList<>();

        for (int i = 0; i < propertyFilters.size(); i++) {
            propertyFilters.set(i, propertyFilters.get(i).toUpperCase()); // Convert case to upper

            // Add wrong ones to wrongProperties
            if (!doesPropertyExist(propertyFilters.get(i))) {
                wrongProperties.add(propertyFilters.get(i));
            }
        }

        // Wrong properties check
        if (!wrongProperties.isEmpty()) {
            printErrorMessage(wrongProperties, "WRONG");
            return "WRONG";
        }

        // Remove Equal properties
        Set<String> seen = new HashSet<>();
        propertyFilters.removeIf(e -> !seen.add(e));

        // Mutually Exclusive properties check
        for (String[] combination : mutuallyExclusiveProperties) {
            if (propertyFilters.containsAll(Arrays.asList(combination))) {
                printErrorMessage(new ArrayList<>(Arrays.asList(combination)), "MUTUALLY_EXCLUSIVE");
                return "MUTUALLY_EXCLUSIVE";
            }
        }

        return "OKAY";
    }

    public static void processNumbers(long num, int count, ArrayList<String> propertyFilters) {
        System.out.println();

        int i = 0;
        while (i < count) {
            if (propertyFilters != null && !satisfiesFilters(num, propertyFilters)) {
                num++;
                continue;
            }

            String numIs = String.format("%" + 16 + "s is", formatter.format(num));
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

            if (!Jumping.isJumping(num)) {
                numProperties.remove("JUMPING");
            }

            if (!Happy.isHappy(num)) {
                numProperties.remove("HAPPY");
            }

            if (!Sad.isSad(num)) {
                numProperties.remove("SAD");
            }

            System.out.printf("%s %s\n", numIs, String.join(", ", numProperties).toLowerCase());
            num++;
            i++;
        }

        System.out.println();
    }

    static boolean satisfiesFilters(long num, ArrayList<String> propertyFilters) {
        return propertyFilters.stream().allMatch((property -> switch (property) {
            case "ODD" -> isOdd(num);
            case "EVEN" -> isEven(num);
            case "BUZZ" -> Buzz.isBuzz(num);
            case "PALINDROMIC" -> Palindrome.isPalindrome(num);
            case "DUCK" -> Duck.isDuck(num);
            case "GAPFUL" -> Gapful.isGapful(num);
            case "SPY" -> Spy.isSpy(num);
            case "SQUARE" -> Square.isSquare(num);
            case "SUNNY" -> Sunny.isSunny(num);
            case "JUMPING" -> Jumping.isJumping(num);
            default -> false;
        }));
    }

    public static void printInstructions() {
        System.out.println("""
                \nSupported requests:
                  - enter a natural number to know its properties;
                  - enter two natural numbers to obtain the properties of the list:
                    * the first parameter represents a starting number;
                    * the second parameter shows how many consecutive numbers are to be printed;
                  - two natural numbers and properties to search for;
                  - separate the parameters with one space;
                  - enter 0 to exit.
                """);
    }

    public static void printErrorMessage(ArrayList<String> wrongProperties, String error) {
        if (error.equals("MUTUALLY_EXCLUSIVE")) {
            System.out.printf("""
                    \nThe request contains mutually exclusive properties: %s
                    There are no numbers with these properties.
                    
                    """, wrongProperties.toString());
            return;
        }

        System.out.printf("""
                \nThe property %s %s wrong.
                Available properties: %s
                
                """, wrongProperties.toString(), wrongProperties.size() > 1 ? "are" : "is", Arrays.toString(properties));
    }
}