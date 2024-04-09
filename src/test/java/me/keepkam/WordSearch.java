package me.keepkam;

import org.junit.jupiter.api.Test;

class WordSearch {
//     https://leetcode.com/problems/word-search/description/
    @Test
    void test() {
    char[][] board ={{'A','B','C','E'},
                    {'S','F','C','S'},
                    {'A','D','E','E'}};
        String word = "ABCCED";

        exist(board, word);
    }

    public boolean exist(char[][] board, String word) {
        int[] boardPointer = {0, 0};
        int wordPointer = 0;

        while (wordPointer < word.length()) { // till we reach end of the work
            contains(board, 'S', boardPointer);
            ++wordPointer;
        }

        return wordPointer == word.length();
    }

    /**
     * @param board  the letter board / matrix
     * @param letter the letter to be searched
     * @param from   the cell from which to start the search
     * @return array that contains the index which has the given letter, if the letter is not found index will be negative
     */
    public boolean contains(char[][] board, char letter, int[] from) {
        int fromRow = from[0];
        int fromCol = from[1];

        // search the row
        for(int col  = from[1]+1; col < board[0].length; col++) {
            if (board[fromRow][col] == letter) {
//                return new int[]{fromRow, col};
                return true;
            }
        }
        // search the col
        for(int row  = from[0]+1; row < board.length; row++) {
            if (board[row][fromCol] == letter) {
//                return new int[]{row, fromCol};
                return true;
            }
        }
//        return new int[]{-1, -1};
        return false;
    }
}
