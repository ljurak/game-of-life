package gameoflife;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

public class GameOfLife extends JFrame {

    private static final Font labelFont = new Font(Font.SANS_SERIF, Font.PLAIN, 16);

    private volatile int interval = 500;

    private Thread gameThread;

    private GameEngine gameEngine;

    private GamePanel gamePanel;

    private JLabel generationLabel;

    private JLabel aliveLabel;

    public GameOfLife() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationByPlatform(true);

        gameEngine = new GameEngine(30);
        gamePanel = new GamePanel(gameEngine);

        JPanel controlPanel = buildControlPanel();

        setLayout(new GridBagLayout());
        add(controlPanel, new GBC(0, 0).setAnchor(GBC.NORTH).setInsets(5, 5, 5, 5));
        add(gamePanel, new GBC(1, 0).setAnchor(GBC.NORTH).setFill(GBC.BOTH).setWeight(1, 1).setInsets(5, 5, 5, 5));

        pauseResumeGame();

        pack();
        setVisible(true);
    }

    private JPanel buildControlPanel() {
        JButton startButton = new JButton("New game");
        startButton.addActionListener(event -> startNewGame());
        startButton.setName("ResetButton");

        JButton pauseResumeButton = new JButton("Pause/Resume");
        pauseResumeButton.addActionListener(event -> pauseResumeGame());
        pauseResumeButton.setName("PlayToggleButton");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 5, 0));
        buttonPanel.add(startButton);
        buttonPanel.add(pauseResumeButton);

        generationLabel = new JLabel("Generation #" + gameEngine.getGeneration());
        generationLabel.setFont(labelFont.deriveFont(Font.BOLD));
        generationLabel.setName("GenerationLabel");

        aliveLabel = new JLabel("Alive: " + gameEngine.countAlive());
        aliveLabel.setFont(labelFont.deriveFont(Font.BOLD));
        aliveLabel.setName("AliveLabel");

        JLabel sliderLabel = new JLabel("Speed mode");
        sliderLabel.setFont(labelFont);

        JSlider speedSlider = new JSlider(0, 1000);
        speedSlider.setValue(interval);
        speedSlider.setMajorTickSpacing(200);
        speedSlider.setMinorTickSpacing(100);
        speedSlider.setPaintLabels(true);
        speedSlider.setPaintTicks(true);
        speedSlider.setSnapToTicks(true);
        speedSlider.addChangeListener(event -> interval = speedSlider.getValue());

        JLabel colorLabel = new JLabel("Cell color");
        colorLabel.setFont(labelFont);

        JComboBox<ComboItem> colorList = new JComboBox<>();
        colorList.addItem(new ComboItem(Color.BLACK, "BLACK"));
        colorList.addItem(new ComboItem(Color.RED, "RED"));
        colorList.addItem(new ComboItem(Color.GREEN, "GREEN"));
        colorList.addItem(new ComboItem(Color.BLUE, "BLUE"));
        colorList.addItem(new ComboItem(Color.ORANGE, "ORANGE"));
        colorList.addItem(new ComboItem(Color.YELLOW, "YELLOW"));
        colorList.addItem(new ComboItem(Color.CYAN, "CYAN"));
        colorList.addItem(new ComboItem(Color.MAGENTA, "MAGENTA"));
        colorList.addActionListener(event -> gamePanel.setCellColor(colorList.getItemAt(colorList.getSelectedIndex()).getColor()));

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
        controlPanel.add(buttonPanel, new GBC(0, 0).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL).setInsets(0, 5, 20, 0));
        controlPanel.add(generationLabel, new GBC(0, 1).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL).setInsets(0, 5, 10, 0));
        controlPanel.add(aliveLabel, new GBC(0, 2).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL).setInsets(0, 5, 20, 0));
        controlPanel.add(sliderLabel, new GBC(0, 3).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL).setInsets(0, 20, 0, 0));
        controlPanel.add(speedSlider, new GBC(0, 4).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL).setInsets(0, 5, 20, 0));
        controlPanel.add(colorLabel, new GBC(0,5).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL).setInsets(0, 20, 0, 0));
        controlPanel.add(colorList, new GBC(0, 6).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL).setInsets(0, 15, 0, 15));

        return controlPanel;
    }

    private void updateGame() {
        generationLabel.setText("Generation #" + gameEngine.getGeneration());
        aliveLabel.setText("Alive: " + gameEngine.countAlive());
        gamePanel.repaint();
    }

    private void startNewGame() {
        if (gameThread != null) {
            gameThread.interrupt();
            gameThread = null;
        }

        gameEngine = new GameEngine(30);
        gamePanel.setGameEngine(gameEngine);
        updateGame();
        pauseResumeGame();
    }

    private void pauseResumeGame() {
        if (gameThread != null) {
            gameThread.interrupt();
            gameThread = null;
        } else {
            gameThread = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Thread.sleep(interval);
                    } catch (InterruptedException e) {
                        break;
                    }

                    gameEngine.calculateNextGeneration();
                    updateGame();
                }
            });
            gameThread.start();
        }
    }

    private static class ComboItem {

        private final Color color;

        private final String label;

        public ComboItem(Color color, String label) {
            this.color = color;
            this.label = label;
        }

        public Color getColor() {
            return color;
        }

        @Override
        public String toString() {
            return label;
        }
    }
}
