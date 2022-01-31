package MainFolder;

import java.awt.*;
import java.util.ArrayList;

public class Control {

    private int width = 30;
    private int height = 30;
    private Snake s = new Snake(width, height);
    private Apple a = new Apple(width, height);

    public Control() {
        GuiMenu guiMenu = new GuiMenu();
        //GUI gui = new GUI(width, height, this);
    }

    public Point getPointApple() {
        return a.getPosition();
    }

    public ArrayList<Point> transferSnakePos() {
        return s.getPosition();
    }

    public void moveSnake(Point pPoint) {
        s.move(pPoint);
    }

    public void addSnakeBodyPart(Point pPoint) {
        s.addBodyPoint(pPoint);
    }

    public void appleNewRandPos() {
        a.recyclePos();
    }



}
