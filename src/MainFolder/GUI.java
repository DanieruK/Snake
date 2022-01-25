package MainFolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GUI extends JFrame implements KeyListener {

    private int width;
    private int height;
    private JPanel[][] grid;
    private int[][] gridInfo;
    private Point applePos;
    private ArrayList<Point> snakePos = new ArrayList<>();
    private Control c;
    private Timer t;
    private int delay = 500;

    private boolean right = true;
    private boolean left = false;
    private boolean up = false;
    private boolean down = false;

    public GUI(int pWidth, int pHeight, Control pC) {
        c = pC;
        width = pWidth;
        height = pHeight;
        setTitle("Snake");
        setResizable(false);
        setFocusable(true);
        setSize(new Dimension(500, 500));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Container con = getContentPane();
        con.setLayout(new BorderLayout());

        JPanel mainPan = new JPanel();
        mainPan.setLayout(new GridLayout(width, height));
        mainPan.setBackground(Color.BLACK);
        con.add(mainPan, BorderLayout.CENTER);

        grid = new JPanel[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = new JPanel();
                grid[i][j].setBackground(Color.gray);
                mainPan.add(grid[i][j]);
            }
        }

        ActionListener taskManager = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (right) {
                    c.moveSnake(new Point(snakePos.get(0).x, snakePos.get(0).y + 1));
                    eventRoutine();
                } else if (up) {
                    c.moveSnake(new Point(snakePos.get(0).x - 1, snakePos.get(0).y));
                    eventRoutine();
                } else if (down) {
                    c.moveSnake(new Point(snakePos.get(0).x + 1, snakePos.get(0).y));
                    eventRoutine();
                } else if (left) {
                    c.moveSnake(new Point(snakePos.get(0).x, snakePos.get(0).y - 1));
                    eventRoutine();
                }
            }

        };

        addKeyListener(this);
        initGridinfo(width, height);
        updateGridInfoSnake();
        updateGridInfoApple();
        colorize();
        t = new Timer(delay, taskManager);
        t.start();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void setSnakePos() {
        snakePos = c.transferSnakePos();
    }

    public void colorize() {
        for (int i = 0; i < gridInfo.length; i++) {
            for (int j = 0; j < gridInfo[i].length; j++) {
                if (gridInfo[i][j] == 0) {
                    grid[i][j].setBackground(Color.BLACK);
                } else if (gridInfo[i][j] == 1) {
                    grid[i][j].setBackground(Color.GREEN);
                } else if (gridInfo[i][j] == 2) {
                    grid[i][j].setBackground(Color.red);
                }
            }
        }
    }

    public void initGridinfo(int pWidth, int pHeight) {
        gridInfo = new int[pWidth][pHeight];
        for (int i = 0; i < gridInfo.length; i++) {
            for (int j = 0; j < gridInfo[i].length; j++) {
                gridInfo[i][j] = 0;
            }
        }
    }

    public void updateGridInfoSnake() {
        setApplePos(c.getPointApple());
        setSnakePos();
        for (int i = 0; i < snakePos.size(); i++) {
            for (int j = 0; j < gridInfo.length; j++) {
                for (int k = 0; k < gridInfo[j].length; k++) {
                    if (snakePos.get(i).getX() == j && snakePos.get(i).getY() == k) {
                        gridInfo[j][k] = 2;
                    }
                }
            }
        }
    }

    public void setApplePos(Point applePos) {
        this.applePos = applePos;
    }

    public void clearGridInfo() {
        for (int i = 0; i < gridInfo.length; i++) {
            for (int j = 0; j < gridInfo[i].length; j++) {
                gridInfo[i][j] = 0;
            }
        }
    }

    public void updateGridInfoApple() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < gridInfo[i].length; j++) {
                if (i == applePos.x && j == applePos.y) {
                    gridInfo[i][j] = 1;
                }
            }
        }
    }

    public void checkCollision() {
        if (snakePos.get(0).x == -1 || snakePos.get(0).y == -1 || snakePos.get(0).x == grid.length || snakePos.get(0).y == grid[0].length) {
            t.stop();
            System.out.println("Collision Detectet");
        }
        for (int i = snakePos.size() - 1; i >= 1; i--) {
            if (snakePos.get(0) == snakePos.get(i)) {
                t.stop();
                System.out.println("Collision with Body");
                break;
            }
        }
    }

    public void checkAppleEaten() {
        if (snakePos.get(0) == applePos){
            //TODO
        }
    }

    public void eventRoutine() {
        setSnakePos();
        checkCollision();
        clearGridInfo();
        updateGridInfoApple();
        updateGridInfoSnake();
        colorize();
        repaint();
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W && !down) {
            left = false;
            right = false;
            up = true;
        } else if (e.getKeyCode() == KeyEvent.VK_S && !up) {
            left = false;
            right = false;
            down = true;
        } else if (e.getKeyCode() == KeyEvent.VK_A && !right) {
            up = false;
            down = false;
            left = true;
        } else if (e.getKeyCode() == KeyEvent.VK_D && !left) {
            up = false;
            down = false;
            right = true;
        }
    }

    public void keyReleased(KeyEvent e) {

    }

    public void keyTyped(KeyEvent e) {

    }

}
