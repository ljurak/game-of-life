package gameoflife;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel {

    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 400;

    private final GameEngine gameEngine;

    public GamePanel(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int size = gameEngine.getSize();
        boolean[][] board = gameEngine.getBoard();

        int cellWidth = getWidth() / size;
        int cellHeight = getHeight() / size;

        g2.setPaint(Color.BLACK);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                g2.drawRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);

                if (board[i][j]) {
                    g2.fillRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}
