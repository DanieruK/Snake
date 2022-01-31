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
    private GridPanel[][] grid;
    private Point applePos;
    private ArrayList<Point> snakePos = new ArrayList<>();
    private Control c;
    private Timer t;
    private int delay = 200;

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

        grid = new GridPanel[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = new GridPanel();
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
        updateGridInfoSnake();
        initBlock();
        placeAppleAgain();
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
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].isSnakeOnPanel() && !grid[i][j].isAppleOnPanel() && !grid[i][j].isSnakeDead() && !grid[i][j].isBlockOnPanel()) {
                    grid[i][j].setBackground(Color.GREEN);
                } else if (!grid[i][j].isSnakeOnPanel() && grid[i][j].isAppleOnPanel() && !grid[i][j].isSnakeDead() && !grid[i][j].isBlockOnPanel()) {
                    grid[i][j].setBackground(Color.RED);
                } else if (grid[i][j].isSnakeOnPanel() && grid[i][j].isAppleOnPanel() && !grid[i][j].isSnakeDead() && !grid[i][j].isBlockOnPanel()) {
                    grid[i][j].setBackground(Color.GREEN);
                } else if (!grid[i][j].isSnakeOnPanel() && !grid[i][j].isAppleOnPanel() && !grid[i][j].isSnakeDead() && !grid[i][j].isBlockOnPanel()) {
                    grid[i][j].setBackground(Color.BLACK);
                } else if (!grid[i][j].isSnakeOnPanel() && grid[i][j].isBlockOnPanel()) {
                    grid[i][j].setBackground(Color.GRAY);
                } else if (grid[i][j].isSnakeDead()) {
                    grid[i][j].setBackground(Color.RED);
                }
            }
        }
    }

    public void initBlock() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (i == 0 || j == 0 || i == grid.length - 1 || j == grid[i].length - 1) {
                    grid[i][j].setBlockOnPanel(true);
                }
            }
        }
        for (int i = 0; i < 30; i++) {
            grid[((int) (Math.random() * width))][((int) (Math.random() * height))].setBlockOnPanel(true);
        }
    }

    public void updateGridInfoSnake() {
        setSnakePos();
        for (int i = 0; i < snakePos.size(); i++) {
            for (int j = 0; j < grid.length; j++) {
                for (int k = 0; k < grid[j].length; k++) {
                    if (snakePos.get(i).getX() == j && snakePos.get(i).getY() == k) {
                        grid[j][k].setSnakeOnPanel(true);
                    }
                }
            }
        }
    }

    public void setApplePos(Point applePos) {
        this.applePos = applePos;
    }

    public void clearGridInfo() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j].setSnakeOnPanel(false);
                grid[i][j].setAppleOnPanel(false);
            }
        }
    }

    public void updateGridInfoApple() {
        setApplePos(c.getPointApple());
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (i == applePos.x && j == applePos.y) {
                    grid[i][j].setAppleOnPanel(true);
                }
            }
        }
    }

    public void checkCollision() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].isSnakeOnPanel() && grid[i][j].isBlockOnPanel()) {
                    t.stop();
                    machItTot();
                    System.out.println("Collision with Block");
                    break;
                }
            }
        }
        for (int i = snakePos.size() - 1; i >= 1; i--) {
            if (snakePos.get(0).x == snakePos.get(i).x && snakePos.get(0).y == snakePos.get(i).y) {
                t.stop();
                machItTot();
                System.out.println("Collision with Body");
                break;
            }
        }
    }

    public void checkAppleEaten() {
        if (snakePos.get(0).x == applePos.x && snakePos.get(0).y == applePos.y) {
            c.addSnakeBodyPart(applePos);
            appleNewPos();
            placeAppleAgain();
            t.stop();
            delay = (int) (delay * 0.98);
            t.setDelay(delay);
            t.start();
        }
    }

    public void placeAppleAgain() {
        setApplePos(c.getPointApple());
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].isBlockOnPanel() && applePos.x == i && applePos.y == j) {
                    appleNewPos();
                    placeAppleAgain();
                }
            }
        }
    }

    public void eventRoutine() {
        setSnakePos();
        checkCollision();
        checkAppleEaten();
        clearGridInfo();
        updateGridInfoApple();
        updateGridInfoSnake();
        colorize();
        revalidate();
    }

    public void machItTot() {
        for (int i = snakePos.size() - 1; i > 0; i--) {
            grid[snakePos.get(i).x][snakePos.get(i).y].setSnakeDead(true);
        }
    }

    public void appleNewPos() {
        c.appleNewRandPos();
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
