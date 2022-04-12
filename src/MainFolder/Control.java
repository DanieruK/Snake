package MainFolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Control {

    private GuiMenu menu;
    private Snake snake;
    private Apple apple;
    private GUI gui;

    private int delay;
    private double delayMultiplyer;
    private Timer timer;
    private int gridWidth = 21;
    private int gridHeight = 21;

    public Control() {
    }

    public void start() {
        menu = new GuiMenu(this);
    }

    public void initTimer(){
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
                delayMultiplyer = 0.95;
            }
        }
        ActionListener taskManager = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                move();
            }
        };
        timer = new Timer(delay, taskManager);
    }

    public void startGame() {
        initTimer();
        System.out.println(delay + " " + delayMultiplyer);
        menu.closeGUI();
        gui = new GUI(this);
        apple = new Apple(gridWidth, gridHeight);
        snake = new Snake(gridWidth, gridHeight);
        placeSnake();
        calculatePosApple();
        gui.setSingleCellStatus((int) apple.getPosition().getX(), (int) apple.getPosition().getY(), GridPanel.Status.APPLE);
        timer.start();
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
            timer.stop();
            delay = (int) (delay * delayMultiplyer);
            timer.setDelay(delay);
            timer.restart();
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
        for (int i = 0; i < snake.getPositionList().size() - 1; i++) {
            gui.setSingleCellStatus((int) snake.getPositionList().get(i).getX(), (int) snake.getPositionList().get(i).getY(), GridPanel.Status.SNAKEDEAD);
        }
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

    public int getGridHeight() {
        return gridHeight;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public Apple getApple() {
        return apple;
    }

    public Snake getSnake() {
        return snake;
    }

}
