package MainFolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class GUI extends JFrame implements KeyListener {

    private int width, height;
    private Container cp;
    private JPanel gamePanel;
    private GridPanel[][] gridCell;
    private JLabel timeLabel, punkteLabel;
    private int activeTimeSec = 0, activeTimeMin = 0, punkte = 0;
    private boolean gameStart =false;
    private final Font textFont = new Font("comic sans", Font.PLAIN, 18);

    private Control control;

    public GUI(Control control) {
        this.control = control;
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) ((int) size.getWidth() - size.getWidth() / 2);
        height = (int) ((int) size.getHeight() - size.getHeight() / 5);
        cp = this.getContentPane();
        cp.setLayout(new BorderLayout());
        cp.setBackground(Color.lightGray);

        gamePanel = new JPanel();
        gamePanel.setBackground(Color.lightGray);
        gamePanel.setLayout(new GridLayout(control.getGridWidth(), control.getGridHeight()));
        cp.add(BorderLayout.CENTER, gamePanel);

        JPanel timerPanel = new JPanel();
        timerPanel.setBackground(Color.lightGray);
        cp.add(BorderLayout.PAGE_START, timerPanel);

        JPanel blackbox = new JPanel();
        blackbox.setBackground(Color.lightGray);
        blackbox.setSize(5,5);
        cp.add(BorderLayout.WEST,blackbox);

        JPanel blackbox2 = new JPanel();
        blackbox2.setBackground(Color.lightGray);
        blackbox2.setSize(5,5);
        cp.add(BorderLayout.EAST,blackbox2);

        JLabel timerLabel = new JLabel("Timer: ");
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setForeground(Color.BLACK);
        timerLabel.setFont(textFont);
        timerPanel.add(timerLabel);

        timeLabel = new JLabel("0:00");
        timeLabel.setFont(textFont);
        timeLabel.setForeground(Color.BLACK);
        timerPanel.add(timeLabel);

        punkteLabel = new JLabel("Punkte: 0");
        punkteLabel.setForeground(Color.BLACK);
        punkteLabel.setFont(textFont);
        timerPanel.add(punkteLabel);

        gridCell = new GridPanel[control.getGridWidth()][control.getGridHeight()];
        for (int i = 0; i < control.getGridWidth(); i++) {
            for (int j = 0; j < control.getGridHeight(); j++) {
                gridCell[i][j] = new GridPanel();
                if (i == 0 || j == 0 || i == control.getGridWidth() - 1 || j == control.getGridHeight() - 1) {
                    gridCell[i][j].setStatus(GridPanel.Status.BARRIER);
                }
                gridCell[i][j].colorize();
                gamePanel.add(gridCell[i][j]);
            }
        }

        setTitle("Snake");
        setSize(width, height);
        setResizable(false);
        setLocationRelativeTo(null);
        addKeyListener(this);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                control.closeDBcon();
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
        setVisible(true);
    }

    public GridPanel[][] getGridCell() {
        return gridCell;
    }

    public void setSingleCellStatus(int gridX, int gridY, GridPanel.Status status) {
        gridCell[gridX][gridY].setStatus(status);
        this.repaint();
    }

    public GridPanel.Status getSingleCellStatus(int pX, int pY) {
        return gridCell[pX][pY].getStatus();
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            control.getSnake().setDirection(Snake.Direction.UP);
            gameStart = true;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            control.getSnake().setDirection(Snake.Direction.DOWN);
            gameStart = true;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            control.getSnake().setDirection(Snake.Direction.LEFT);
            gameStart = true;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            control.getSnake().setDirection(Snake.Direction.RIGHT);
            gameStart = true;
        }
    }

    public void incPunkte() {
        punkte++;
        punkteLabel.setText("Punkte: " + punkte);
    }

    public int getActiveTimeMin() {
        return activeTimeMin;
    }

    public int getActiveTimeSec() {
        return activeTimeSec;
    }

    public int getPunkte() {
        return punkte;
    }

    public int getTime(){
        return activeTimeMin*60 + activeTimeSec;
    }

    public void updateTimeLabel() {
        if (activeTimeSec < 9) {
            activeTimeSec++;
            timeLabel.setText(activeTimeMin + ":0" + activeTimeSec);
        } else if (activeTimeSec < 59){
            activeTimeSec++;
            timeLabel.setText(activeTimeMin + ":" + activeTimeSec);
        } else {
            activeTimeSec = 0;
            activeTimeMin++;
            timeLabel.setText(activeTimeMin + ":" + activeTimeSec);
        }
    }

    public void gameOverScreen(){
        gamePanel.setVisible(false);

        JPanel gameOverPanel = new JPanel();
        gameOverPanel.setBackground(Color.lightGray);
        gameOverPanel.setLayout(null);
        cp.add(gameOverPanel);

        JPanel grayBox = new JPanel();
        grayBox.setBackground(Color.darkGray);
        grayBox.setBounds(0,gamePanel.getHeight()/3,gamePanel.getWidth(),gamePanel.getHeight()/4);
        grayBox.setLayout(null);
        gameOverPanel.add(grayBox);

        JPanel gameOverTextBox = new JPanel();
        gameOverTextBox.setBackground(Color.darkGray);
        gameOverTextBox.setBounds(grayBox.getWidth()/4,grayBox.getHeight()/3,grayBox.getWidth()/2, grayBox.getHeight()/2);
        grayBox.add(gameOverTextBox);

        JLabel gameOverText = new JLabel("GAME OVER");
        gameOverText.setFont(new Font("Times new Roman", Font.PLAIN, this.getHeight() / 12));
        gameOverText.setForeground(Color.red.darker());
        gameOverTextBox.add(gameOverText);
    }

    public boolean getGameStart(){
        return gameStart;
    }

    public void keyReleased(KeyEvent e) {

    }

    public void keyTyped(KeyEvent e) {

    }
}
