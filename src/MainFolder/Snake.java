package MainFolder;

import java.awt.*;
import java.util.ArrayList;

public class Snake extends GameObject{

    private Direction direction;
    private ArrayList<Point> positionList;

    public Snake(int gridWith, int gridHeight) {
        super(gridWith, gridHeight);
        direction = Direction.LEFT;
        positionList.add(this.position);
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

    public Direction getDirection() {
        return direction;
    }



    enum Direction{
        UP, DOWN, LEFT, RIGHT
    }

}
