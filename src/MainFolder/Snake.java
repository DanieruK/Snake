package MainFolder;

import java.awt.*;
import java.util.ArrayList;

public class Snake extends GameObject{

    private Direction direction;
    private ArrayList<Point> positionList = new ArrayList<>();

    public Snake(int gridWith, int gridHeight) {
        super(gridWith, gridHeight);
        midPos();
        direction = Direction.RIGHT;
        positionList.add(this.position);
        positionList.add(new Point((int) position.getX(),(int)(position.getY()-1)));
        positionList.add(new Point((int) position.getX(),(int)(position.getY()-2)));
    }

    public void setDirection(Direction direction) {
        if (direction.equals(Direction.UP) && !this.direction.equals(Direction.DOWN)){
            this.direction = Direction.UP;
        }else if (direction.equals(Direction.DOWN) && !this.direction.equals(Direction.UP)){
            this.direction = Direction.DOWN;
        }else if(direction.equals(Direction.LEFT) && !this.direction.equals(Direction.RIGHT)){
            this.direction = Direction.LEFT;
        }else if(direction.equals(Direction.RIGHT) && !this.direction.equals(Direction.LEFT)){
            this.direction = Direction.RIGHT;
        }
    }

    public void addBodyPart(Point pPoint) {
        positionList.add(pPoint);
    }

    public Direction getDirection() {
        return direction;
    }

    public ArrayList<Point> getPositionList() {
        return positionList;
    }



    enum Direction{
        UP, DOWN, LEFT, RIGHT
    }

}
