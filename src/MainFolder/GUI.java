package MainFolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GUI extends JFrame implements KeyListener{

    private int width, height;
    private Container cp;
    private JPanel gamePanel;
    private GridPanel[][] gridCell;
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
        timerPanel.setBackground(Color.BLUE);
        timerPanel.setLayout(new GridLayout());
        cp.add(BorderLayout.PAGE_START,timerPanel);

        JLabel timerLabel = new JLabel("Timer: ");
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setFont(textFont);
        timerPanel.add(timerLabel);

        JLabel timeLabel = new JLabel(" XX:XX");
        timeLabel.setFont(textFont);
        timeLabel.setForeground(Color.WHITE);
        timerPanel.add(timeLabel);

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

    public void setSingleCellStatus(int gridX, int gridY, GridPanel.Status status){
        gridCell[gridX][gridY].setStatus(status);
        this.repaint();
    }

    public GridPanel.Status getSingleCellStatus(int pX, int pY){
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

    public void keyReleased(KeyEvent e) {

    }

    public void keyTyped(KeyEvent e) {

    }
}
