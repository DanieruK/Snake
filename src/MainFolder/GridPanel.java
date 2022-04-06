package MainFolder;

import javax.swing.*;

public class GridPanel extends JPanel {

    private Status status;

    public GridPanel() {
        status = Status.EMPTY;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    enum Status{
        EMPTY, SNAKEALIVE, SNAKEDEAD, APPLE, BARRIER
    }
}
