package gameoflife;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        GameOfLife game = new GameOfLife(size);

        int generation = 1;
        while (generation <= 10) {
            clearScreen();
            printGeneration(game);
            game.calculateNextGeneration();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            generation++;
        }
    }

    private static void printGeneration(GameOfLife game) {
        System.out.printf("Generation #%d%n", game.getGeneration());
        System.out.printf("Alive: %d%n", game.countAlive());
        for (boolean[] row : game.getBoard()) {
            for (boolean cell : row) {
                System.out.print(cell ? 'O' : ' ');
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
