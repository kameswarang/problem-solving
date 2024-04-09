package me.keepkam;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;

@Slf4j
class PowerSet {
    private static class Attempt_1 {
        private static List<int[]> power(int i, List<int[]> power, int[] arr) {

            return power;
        }
    }

    @ParameterizedTest
    @MethodSource("provideInputs")
    void solve(Input input) {
        List<int[]> powerSet = Attempt_1.power(0, Arrays.asList(new int[]{}), input.array);
        powerSet.forEach(p -> log.info("{}", p));

        Assertions.assertEquals(input.expected, powerSet.size());
    }


    private record Input(int[] array, long expected) {};
    private static List<Input> provideInputs() {
        return List.of(
                new Input(new int[]{1, 2, 3}, twoPowerN(3)),
                new Input(new int[]{1, 2, 3, 4}, twoPowerN(4))
        );
    }

    private static long twoPowerN(int n) {
        if(n == 0) return 1;
        return 2 * twoPowerN(n-1);
    }

}
