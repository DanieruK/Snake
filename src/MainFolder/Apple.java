package MainFolder;

import java.awt.*;

public class Apple {

    private Point position;
    private int width;
    private int height;

    public Apple(int pWidth, int pHeight){
        width = pWidth;
        height = pHeight;
        position = randPos();
    }

    public Point randPos(){
        return new Point(((int)(Math.random()*width)), ((int)(Math.random()*height)));
    }

    public void recyclePos(){
        position = randPos();
    }

    public Point getPosition() {
        return position;
    }

}
