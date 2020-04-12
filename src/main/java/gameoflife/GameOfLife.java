package gameoflife;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

public class GameOfLife extends JFrame {

    private static final Font labelFont = new Font(Font.SANS_SERIF, Font.PLAIN, 16);

    private GameEngine gameEngine;

    private GamePanel gamePanel;

    private JLabel generationLabel;

    private JLabel aliveLabel;

    public GameOfLife() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationByPlatform(true);

        gameEngine = new GameEngine(20);

        generationLabel = new JLabel("Generation #" + gameEngine.getGeneration());
        generationLabel.setFont(labelFont);
        generationLabel.setName("GenerationLabel");

        aliveLabel = new JLabel("Alive: " + gameEngine.countAlive());
        aliveLabel.setFont(labelFont);
        aliveLabel.setName("AliveLabel");

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(2, 1));
        infoPanel.add(generationLabel);
        infoPanel.add(aliveLabel);
        add(infoPanel, BorderLayout.NORTH);

        gamePanel = new GamePanel(gameEngine);
        add(gamePanel, BorderLayout.CENTER);

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                gameEngine.calculateNextGeneration();
                updateGame();
            }
        }).start();

        pack();
        setVisible(true);
    }

    public void updateGame() {
        generationLabel.setText("Generation #" + gameEngine.getGeneration());
        aliveLabel.setText("Alive: " + gameEngine.countAlive());
        gamePanel.repaint();
    }
}
