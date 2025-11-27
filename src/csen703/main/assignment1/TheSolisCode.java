package csen703.main.assignment1;

import java.util.ArrayList;

public class TheSolisCode {

    /**
     * Divide and Conquer approach to count decode ways
     * Time Complexity: O(2^n) - exponential due to overlapping problems
     */
    public static Integer SolisDecodeDiv(String Code) {
        if (Code == null || Code.isEmpty()) {
            return 0;
        }
        return solisDecodeDivHelper(Code, 0);
    }

    private static int solisDecodeDivHelper(String code, int index) {
        // Base case: reached end of string
        if (index == code.length()) {
            return 1;
        }

        // Invalid: leading zero
        if (code.charAt(index) == '0') {
            return 0;
        }

        // Try single digit decode
        int count = solisDecodeDivHelper(code, index + 1);

        // Try two digit decode if possible
        if (index + 1 < code.length()) {
            int twoDigit = Integer.parseInt(code.substring(index, index + 2));
            if (twoDigit >= 10 && twoDigit <= 26) {
                count += solisDecodeDivHelper(code, index + 2);
            }
        }

        return count;
    }

    /**
     * Dynamic Programming approach to count decode ways
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public static Integer SolisDecodeDP(String Code) {
        if (Code == null || Code.isEmpty()) {
            return 0;
        }

        int n = Code.length();
        int[] dp = new int[n + 1];

        // Base case: empty string has 1 way
        dp[0] = 1;

        // First character
        dp[1] = Code.charAt(0) == '0' ? 0 : 1;

        // Fill the dp array
        for (int i = 2; i <= n; i++) {
            // Single digit decode
            int oneDigit = Integer.parseInt(Code.substring(i - 1, i));
            if (oneDigit >= 1 && oneDigit <= 9) {
                dp[i] += dp[i - 1];
            }

            // Two digit decode
            int twoDigit = Integer.parseInt(Code.substring(i - 2, i));
            if (twoDigit >= 10 && twoDigit <= 26) {
                dp[i] += dp[i - 2];
            }
        }

        return dp[n];
    }

    /**
     * Generate all possible decoded strings
     * Uses backtracking to explore all valid decode paths
     */
    public static ArrayList<String> SolisDecodedResults(String Code) {
        ArrayList<String> results = new ArrayList<>();

        if (Code == null || Code.isEmpty()) {
            return results;
        }

        // Check if decoding is possible
        if (SolisDecodeDP(Code) == 0) {
            return results;
        }

        solisDecodeResultsHelper(Code, 0, "", results);
        return results;
    }

    private static void solisDecodeResultsHelper(String code, int index,
                                                 String current, ArrayList<String> results) {
        // Base case: reached end of string
        if (index == code.length()) {
            results.add(current);
            return;
        }

        // Invalid: leading zero
        if (code.charAt(index) == '0') {
            return;
        }

        // Try single digit decode
        int oneDigit = Integer.parseInt(code.substring(index, index + 1));
        if (oneDigit >= 1 && oneDigit <= 9) {
            char letter = (char) ('a' + oneDigit - 1);
            solisDecodeResultsHelper(code, index + 1, current + letter, results);
        }

        // Try two digit decode if possible
        if (index + 1 < code.length()) {
            int twoDigit = Integer.parseInt(code.substring(index, index + 2));
            if (twoDigit >= 10 && twoDigit <= 26) {
                char letter = (char) ('a' + twoDigit - 1);
                solisDecodeResultsHelper(code, index + 2, current + letter, results);
            }
        }
    }
}