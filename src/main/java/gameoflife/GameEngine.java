package gameoflife;

import java.util.Random;

public class GameEngine {

    private boolean[][] board;

    private final int size;

    private int generation = 1;

    public GameEngine(int size) {
        this.board = new boolean[size][size];
        this.size = size;
        init();
    }

    private void init() {
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = random.nextBoolean();
            }
        }
    }

    public boolean[][] getBoard() {
        return board;
    }

    public int getSize() {
        return size;
    }

    public int getGeneration() {
        return generation;
    }

    public int countAlive() {
        int count = 0;
        for (boolean[] row : board) {
            for (boolean cell : row) {
                if (cell) {
                    count++;
                }
            }
        }

        return count;
    }

    public void calculateNextGeneration() {
        boolean[][] next = new boolean[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                next[i][j] = calculateCell(i, j);
            }
        }

        board = next;
        generation++;
    }

    private boolean calculateCell(int row, int col) {
        int countAlive = 0;

        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (board[(size + i) % size][(size + j) % size]) {
                    countAlive++;
                }
            }
        }

        if (board[row][col]) {
            countAlive--;
        }

        if (board[row][col]) {
            return countAlive == 2 || countAlive == 3;
        } else {
            return countAlive == 3;
        }
    }
}
