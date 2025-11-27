package csen703.main.assignment1;

import java.util.ArrayList;

/**
 * Comprehensive test suite for TheSolisCode
 * Tests edge cases, valid inputs, and invalid inputs
 */
public class ComprehensiveTest {

    private static int testsPassed = 0;
    private static int testsFailed = 0;

    public static void main(String[] args) {
        System.out.println("=== The Solis Code - Comprehensive Test Suite ===\n");

        // Test the provided examples
        testProvidedExamples();

        // Test edge cases
        testEdgeCases();

        // Test invalid inputs
        testInvalidInputs();

        // Test valid inputs
        testValidInputs();

        // Summary
        System.out.println("\n=== Test Summary ===");
        System.out.println("Tests Passed: " + testsPassed);
        System.out.println("Tests Failed: " + testsFailed);
        System.out.println("Total Tests: " + (testsPassed + testsFailed));
    }

    private static void testProvidedExamples() {
        System.out.println("--- Provided Examples ---\n");

        // Test Case 1: "4182113"
        testCase("4182113", 10, new String[]{
                "drum", "druac", "drbkc", "drbam", "drbaac",
                "dahum", "dahuac", "dahbkc", "dahbam", "dahbaac"
        });

        // Test Case 2: "2025" - Note: Expected output seems incorrect
        // Actual valid decodings: "ty" (20-25), "tbe" (20-2-5)
        // "bbe" would require "2-2-5" which doesn't match "2025"
        System.out.println("Test: Code = \"2025\"");
        System.out.println("Note: Problem statement may have error - 'bbe' not possible from '2025'");
        testCase("2025", 2, new String[]{"ty", "tbe"});

        // Test Case 3: "80"
        testCase("80", 0, new String[]{});

        System.out.println();
    }

    private static void testEdgeCases() {
        System.out.println("--- Edge Cases ---\n");

        // Empty string
        testCase("", 0, new String[]{});

        // Single valid digit
        testCase("1", 1, new String[]{"a"});
        testCase("9", 1, new String[]{"i"});

        // Single invalid digit
        testCase("0", 0, new String[]{});

        // Minimum valid two-digit
        testCase("10", 1, new String[]{"j"});

        // Maximum valid two-digit
        testCase("26", 2, new String[]{"bf", "z"});

        // Just above maximum
        testCase("27", 1, new String[]{"bg"});

        System.out.println();
    }

    private static void testInvalidInputs() {
        System.out.println("--- Invalid Inputs ---\n");

        // Leading zeros
        testCase("01", 0, new String[]{});
        testCase("001", 0, new String[]{});

        // Isolated zeros - but some are actually valid!
        testCase("102", 1, new String[]{"jb"}); // "10-2" is valid
        testCase("1002", 0, new String[]{}); // "10-0-2" invalid, "100-2" invalid
        testCase("10203", 1, new String[]{"jtc"}); // "10-20-3" is valid

        // Invalid two-digit combinations with zero
        testCase("30", 0, new String[]{});
        testCase("50", 0, new String[]{});
        testCase("90", 0, new String[]{});

        System.out.println();
    }

    private static void testValidInputs() {
        System.out.println("--- Valid Inputs ---\n");

        // Simple cases
        testCase("12", 2, new String[]{"ab", "l"});
        testCase("123", 3, new String[]{"abc", "aw", "lc"});
        testCase("226", 3, new String[]{"bbf", "bz", "vf"});

        // All single digits
        testCase("111", 3, new String[]{"aaa", "ak", "ka"});

        // Mix of single and double
        testCase("1234", 3, new String[]{"abcd", "awd", "lcd"});

        // Edge with 26
        testCase("1226", 5, new String[]{"abbf", "abz", "avf", "lbf", "lz"});

        System.out.println();
    }

    private static void testCase(String code, int expectedCount, String[] expectedResults) {
        System.out.println("Testing: \"" + code + "\"");

        // Test Divide & Conquer
        Integer divResult = TheSolisCode.SolisDecodeDiv(code);
        System.out.println("  Div Result: " + divResult);

        // Test Dynamic Programming
        Integer dpResult = TheSolisCode.SolisDecodeDP(code);
        System.out.println("  DP Result: " + dpResult);

        // Test Results Generation
        ArrayList<String> results = TheSolisCode.SolisDecodedResults(code);
        System.out.println("  Decoded Results: " + results);
        System.out.println("  Expected Count: " + expectedCount);

        // Verify
        boolean passed = true;

        if (!divResult.equals(expectedCount)) {
            System.out.println("  ❌ Div count mismatch!");
            passed = false;
        }

        if (!dpResult.equals(expectedCount)) {
            System.out.println("  ❌ DP count mismatch!");
            passed = false;
        }

        if (results.size() != expectedCount) {
            System.out.println("  ❌ Results count mismatch! Got " + results.size());
            passed = false;
        }

        if (!divResult.equals(dpResult)) {
            System.out.println("  ❌ Div and DP results don't match!");
            passed = false;
        }

        // Check if all expected results are present (order may vary)
        if (expectedResults.length > 0 && results.size() == expectedResults.length) {
            for (String expected : expectedResults) {
                if (!results.contains(expected)) {
                    System.out.println("  ❌ Missing expected result: " + expected);
                    passed = false;
                }
            }
        }

        if (passed) {
            System.out.println("  ✓ PASSED");
            testsPassed++;
        } else {
            System.out.println("  ✗ FAILED");
            testsFailed++;
        }

        System.out.println();
    }
}