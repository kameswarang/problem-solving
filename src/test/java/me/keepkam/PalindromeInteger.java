package me.keepkam;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class PalindromeInteger {
    private static boolean isPalindrome(int n) {
        String nString = toString(n);
        return true;
    }

    private static String toString(int n) {
        StringBuilder num = new StringBuilder();
        while(n > 0) {
            num.append(n % 10);
            n /= 10;
        }

        num.reverse(); // TODO implement this as custom method

        return num.toString();
    }


    @ParameterizedTest
    @ValueSource(ints = {12321, 111111, 01345, 0, 9934574})
    void solve(int n) {
        log.info("{}: {}", n, isPalindrome(n) ? "is a palindrome" : "not a palindrome");
    }
}
