package MainFolder;

public class Control {

    private GuiMenu menu;
    private Snake snake;
    private Apple apple;
    private int gridWidth = 50;
    private int gridHeight = 50;

    public Control() {

    }

    public void start(){
        menu = new GuiMenu(this);
    }

    public void startGame(){
        menu.closeGUI();
    }

}
