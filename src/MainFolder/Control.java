package MainFolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Control {

    private GuiMenu menu;
    private Snake snake;
    private Apple apple;
    private GUI gui;
    private DataBase db;
    private DBZugang dbGUI;

    private int delay;
    private double delayMultiplyer;
    private Timer timer, counter;
    private int gridWidth = 29;
    private int gridHeight = 29;
    private String username;
    private boolean timerstarted = false;
    private int[][] map = new int[29][29];
    private int easyMap = 1, mediumMap = 2, hardMap = 3;

    public Control() {
        db = new DataBase();
    }

    public void start() {
        menu = new GuiMenu(this);
    }

    public void initTimer() {
        switch (menu.getModi()) {
            case 0 -> {
                delay = 300;
                delayMultiplyer = 0.99;
            }
            case 1 -> {
                delay = 275;
                delayMultiplyer = 0.98;
            }
            case 2 -> {
                delay = 250;
                delayMultiplyer = 0.97;
            }
        }
        ActionListener taskManager = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                move();
            }
        };
        timer = new Timer(delay, taskManager);
        counter = new Timer(1000, e -> gui.updateTimeLabel());
    }

    public void startGame() {
        initTimer();
        menu.closeGUI();
        gui = new GUI(this);
        apple = new Apple(gridWidth, gridHeight);
        snake = new Snake(gridWidth, gridHeight);
        createDiffPattern(getMap());
        placeSnake();
        calculatePosApple();
        gui.setSingleCellStatus((int) apple.getPosition().getX(), (int) apple.getPosition().getY(), GridPanel.Status.APPLE);
        startTimer();
        db.savePlayer(menu.getUserNameInput().getText());
    }

    public void startTimer() {
        Thread waitForTimer = new Thread(() -> {
            if (gui.getGameStart() && !timerstarted) {
                timer.start();
                counter.start();
                timerstarted = true;
            } else {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                startTimer();
            }
        });
        waitForTimer.start();
    }

    public int getMap(){
        if (menu.getModi() == 0) return easyMap;
        else if (menu.getModi() == 1) return mediumMap;
        else return hardMap;
    }

    public void calculatePosApple() {
        apple.randPos();
        if (!gui.getSingleCellStatus((int) apple.getPosition().getX(), (int) apple.getPosition().getY()).equals(GridPanel.Status.EMPTY)) {
            calculatePosApple();
        }
        gui.setSingleCellStatus((int) apple.getPosition().getX(), (int) apple.getPosition().getY(), GridPanel.Status.APPLE);
    }

    public void eatApple() {
        if (apple.getPosition().getX() == snake.getPositionList().get(0).getX() && apple.getPosition().getY() == snake.getPositionList().get(0).getY()) {
            snake.addBodyPart(snake.getPositionList().get(snake.getPositionList().size() - 1));
            calculatePosApple();
            gui.incPunkte();
            timer.stop();
            delay = (int) (delay * delayMultiplyer);
            timer.setDelay(delay);
            timer.restart();
        }
    }

    public void createDiffPattern(int mapID) {
        int[][] map = Serializer.deserialize(db.searchForMap(mapID));
        for (int i = 0; i < gui.getGridCell().length; i++) {
            for (int j = 0; j < gui.getGridCell().length; j++) {
                if (map[i][j] == 1) gui.setSingleCellStatus(i, j, GridPanel.Status.BARRIER);
                else gui.setSingleCellStatus(i, j, GridPanel.Status.EMPTY);
            }
        }
    }

    public void placeSnake() {
        for (int i = 0; i < snake.getPositionList().size(); i++) {
            gui.setSingleCellStatus((int) snake.getPositionList().get(i).getX(), (int) snake.getPositionList().get(i).getY(), GridPanel.Status.SNAKEALIVE);
        }
    }

    public boolean checkCollision() {
        for (int i = 1; i < snake.getPositionList().size() - 1; i++) {
            if (snake.getPositionList().get(0).getX() == snake.getPositionList().get(i).getX() && snake.getPositionList().get(0).getY() == snake.getPositionList().get(i).getY() || gui.getSingleCellStatus((int) snake.getPositionList().get(0).getX(), (int) snake.getPositionList().get(0).getY()) == GridPanel.Status.BARRIER) {
                return true;
            }
        }
        return false;
    }

    public void collision() {
        timer.stop();
        counter.stop();
        for (int i = 0; i < snake.getPositionList().size() - 1; i++) {
            gui.setSingleCellStatus((int) snake.getPositionList().get(i).getX(), (int) snake.getPositionList().get(i).getY(), GridPanel.Status.SNAKEDEAD);
        }
        db.saveSpiel(menu.getUserNameInput().getText(), gui.getPunkte(), menu.getModi() + 1, gui.getTime(), getMap());
        gui.gameOverScreen();
        db.closeCon();
    }

    public void move() {
        if (snake.getDirection().equals(Snake.Direction.UP)) {
            snake.getPositionList().add(0, new Point((int) snake.getPositionList().get(0).getX() - 1, (int) snake.getPositionList().get(0).getY()));
        } else if (snake.getDirection().equals(Snake.Direction.DOWN)) {
            snake.getPositionList().add(0, new Point((int) snake.getPositionList().get(0).getX() + 1, (int) snake.getPositionList().get(0).getY()));
        } else if (snake.getDirection().equals(Snake.Direction.LEFT)) {
            snake.getPositionList().add(0, new Point((int) snake.getPositionList().get(0).getX(), (int) snake.getPositionList().get(0).getY() - 1));
        } else if (snake.getDirection().equals(Snake.Direction.RIGHT)) {
            snake.getPositionList().add(0, new Point((int) snake.getPositionList().get(0).getX(), (int) snake.getPositionList().get(0).getY() + 1));
        }
        if (checkCollision()) {
            collision();
        } else {
            gui.setSingleCellStatus((int) snake.getPositionList().get(0).getX(), (int) snake.getPositionList().get(0).getY(), GridPanel.Status.SNAKEALIVE);
        }
        gui.setSingleCellStatus((int) snake.getPositionList().get(snake.getPositionList().size() - 1).getX(), (int) snake.getPositionList().get(snake.getPositionList().size() - 1).getY(), GridPanel.Status.EMPTY);
        snake.getPositionList().remove(snake.getPositionList().size() - 1);
        eatApple();
    }

    public void closeDBcon() {
        db.closeCon();
    }

    public ArrayList getArrayList(String pName, String pSch) {
        return db.getbestenliste(pName, pSch);
    }

    public void openDatenbank() {
        menu.closeGUI();
        dbGUI = new DBZugang(this);
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public Snake getSnake() {
        return snake;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
