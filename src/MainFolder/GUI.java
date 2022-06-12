package MainFolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GUI extends JFrame implements KeyListener {

    private int width, height;
    private Container cp;
    private JPanel gamePanel;
    private GridPanel[][] gridCell;
    private JLabel timeLabel, punkteLabel;
    private int activeTimeSec = 0, activeTimeMin = 0, punkte = 0;
    private final Font textFont = new Font("comic sans", Font.PLAIN, 18);

    private Control control;

    public GUI(Control control) {
        this.control = control;
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) ((int) size.getWidth() - size.getWidth() / 2);
        height = (int) ((int) size.getHeight() - size.getHeight() / 5);
        cp = this.getContentPane();
        cp.setLayout(new BorderLayout());
        cp.setBackground(Color.black);

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
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            control.getSnake().setDirection(Snake.Direction.DOWN);
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            control.getSnake().setDirection(Snake.Direction.LEFT);
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            control.getSnake().setDirection(Snake.Direction.RIGHT);
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

    public void keyReleased(KeyEvent e) {

    }

    public void keyTyped(KeyEvent e) {

    }
}
