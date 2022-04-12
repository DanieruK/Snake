package MainFolder;

import java.awt.*;

public abstract class GameObject {

    protected Point position;
    protected int gridWith, gridHeight;

    public GameObject(int gridWidth, int gridHeight){
        this.gridWith = gridWidth;
        this.gridHeight = gridHeight;
    }

    public void randPos(){
        position = new Point((int) (Math.random()*gridWith),(int) (Math.random()*gridHeight));
    }

    public void midPos(){
        position = new Point((int) (gridWith/2), (int) (gridHeight/2));
    }

    public Point getPosition() {
        return position;
    }
}
