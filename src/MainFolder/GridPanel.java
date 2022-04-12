package MainFolder;

import javax.swing.*;
import java.awt.*;

public class GridPanel extends JPanel {

    private Status status;

    public GridPanel() {
        status = Status.EMPTY;
    }

    public void setStatus(Status status) {
        this.status = status;
        colorize();
    }

    public Status getStatus() {
        return status;
    }

    public void colorize(){
        if (status.equals(Status.EMPTY)){
            setBackground(Color.BLACK);
        }else if (status.equals(Status.SNAKEALIVE)){
            setBackground(Color.GREEN);
        }else if (status.equals(Status.SNAKEDEAD)){
            setBackground(Color.RED);
        }else if (status.equals(Status.APPLE)){
            setBackground(Color.RED);
        }else if (status.equals(Status.BARRIER)){
            setBackground(Color.lightGray);
        }
    }

    enum Status{
        EMPTY, SNAKEALIVE, SNAKEDEAD, APPLE, BARRIER
    }
}
