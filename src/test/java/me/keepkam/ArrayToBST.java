package me.keepkam;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
class ArrayToBST {
    /**
     * Takes a sorted array and converts it to a BST
     * @param arr sorted array
     * @param range the range(start, end indices) of the array to consider
     * @return node of the binary search tree
     */
    private TreeNode toBST(int[] arr, Range range) {
        if(range.start == range.end) {
            return null;
        }

        int mid = range.start + (range.end - range.start) / 2;

        var left = new Range(range.start, mid);
        var right =  new Range(mid < range.end ? mid+1 : mid, range.end);

        return new TreeNode(arr[mid],
                toBST(arr, left),
                toBST(arr, right));
    }

    private record TreeNode(int val, TreeNode left, TreeNode right){}

    private record Range(int start, int end){}

    @ParameterizedTest
    @MethodSource("input")
    void test(int[] arr) {
        var bst = toBST(arr, new Range(0, arr.length));
        log.info("{}", lot(bst));
    }

    private static Stream<Arguments> input() {
        return Stream.of(
                Arguments.of((Object) new int[]{1, 2, 3}),
                Arguments.of((Object) new int[]{1, 2, 3, 4}),
                Arguments.of((Object) new int[]{-10,-3,0,5,9}),
                Arguments.of((Object) new int[]{})
        );
    }

    String lot(TreeNode node) {
        Queue<TreeNode> queue = new LinkedList<>();
            queue.add(node);

        var lot = new ArrayList<Integer>();

        while (!queue.isEmpty()) {
            var curr = queue.poll();
            if(nonNull(curr)) {
                lot.add(curr.val);
                if(isNull(curr.left) && isNull(curr.right)) {
                    lot.add(null);
                } else {
                    queue.add(curr.left);
                    queue.add(curr.right);
                }
            }
        }

        // remove the last null
        if(!lot.isEmpty() && isNull(lot.getLast())) {
            lot.removeLast();
        }

        return lot.toString();
    }
}
