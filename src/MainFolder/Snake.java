package MainFolder;

import java.awt.*;
import java.util.ArrayList;

public class Snake {

    private ArrayList<Point> position = new ArrayList<>();

    public Snake(int pWidth, int pHeight){
        init(pWidth,pHeight);
    }

    public void init(int width, int height){
        int startX = (int)(width/2);
        int startY= (int)(height/2);

        position.add(new Point(startX,startY));
        position.add(new Point(startX,startY-1));
        position.add(new Point(startX,startY-2));

    }

    public void move(Point pPosition){
        for (int i = position.size()-1; i >= 1 ; i--) {
            position.set(i,position.get(i-1));
        }
        position.set(0,pPosition);
    }

    public ArrayList<Point> getPosition() {
        return position;
    }

}
