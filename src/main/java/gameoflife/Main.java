package gameoflife;

import javax.swing.JFrame;
import java.awt.EventQueue;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new GameOfLife();
            frame.setTitle("Game of Life");
        });
    }
}
