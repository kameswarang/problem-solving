package me.keepkam;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;

@Slf4j
class Permutations {
    private static class Attempt_1 {
        private static List<int[]> permutate(int i, int[] arr) {
            if (i == arr.length) {
                return List.of(arr.clone());
            }

            List<int[]> perms = new ArrayList<>();

            for (int j = i; j < arr.length; j++) {
                swap(i, j, arr);
                perms.addAll(permutate(i + 1, arr));
                swap(i, j, arr);
            }

            return perms;
        }

        private static void swap(int i, int j, int[] arr) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    @ParameterizedTest
    @MethodSource("provideInputs")
    void solve(Input input) {
        int[] array = input.array;

        List<int[]> ps = Attempt_1.permutate(0, array);
//        ps.forEach(arr -> log.info(Arrays.toString(arr)));

        Assertions.assertEquals(input.expected, ps.size());
    }

    private record Input(int[] array, long expected) {};
    private static List<Input> provideInputs() {
        return List.of(
                new Input(new int[]{1, 2, 3}, factorial(3)),
                new Input(new int[]{1, 2, 3, 4}, factorial(4))
        );
    }

    private static long factorial(int n) {
        if (n == 1) return n;
        return n * factorial(n-1);
    }
}
