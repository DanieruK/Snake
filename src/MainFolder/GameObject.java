package MainFolder;

import java.awt.*;

public abstract class GameObject {

    protected Point position;
    protected int gridWith, gridHeight;

    public GameObject(int gridWidth, int gridHeight){
        this.gridWith = gridWidth;
        this.gridHeight = gridHeight;
    }

}
