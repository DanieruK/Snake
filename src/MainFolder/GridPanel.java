package MainFolder;

import javax.swing.*;

public class GridPanel extends JPanel {

    private boolean snakeOnPanel = false;
    private boolean appleOnPanel = false;
    private boolean snakeDead = false;
    private boolean blockOnPanel = false;

    public GridPanel() {

    }

    public void setAppleOnPanel(boolean appleOnPanel) {
        this.appleOnPanel = appleOnPanel;
    }

    public void setSnakeOnPanel(boolean snakeOnPanel) {
        this.snakeOnPanel = snakeOnPanel;
    }

    public void setSnakeDead(boolean snakeDead) {
        this.snakeDead = snakeDead;
    }

    public void setBlockOnPanel(boolean blockOnPanel) {
        this.blockOnPanel = blockOnPanel;
    }

    public boolean isSnakeOnPanel() {
        return snakeOnPanel;
    }

    public boolean isAppleOnPanel() {
        return appleOnPanel;
    }

    public boolean isSnakeDead() {
        return snakeDead;
    }

    public boolean isBlockOnPanel() {
        return blockOnPanel;
    }
}
