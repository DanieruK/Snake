package MainFolder;

import java.awt.*;
import java.util.ArrayList;

public class Control {

    private int width = 21;
    private int height = 21;
    private Snake s = new Snake(width,height);;
    private Apple a = new Apple(randPos(width,height));;

    public Control(){
        GUI gui = new GUI(width,height,this);
    }

    public Point randPos(int maxValueX, int maxValueY){
        return new Point(((int)(Math.random()*maxValueX)), ((int)(Math.random()*maxValueY)));
    }

    public Point getPointApple(){
        return a.getPosition();
    }

    public ArrayList<Point> transferSnakePos(){
        return s.getPosition();
    }

    public void moveSnake(Point pPoint){
        s.move(pPoint);
    }

}
