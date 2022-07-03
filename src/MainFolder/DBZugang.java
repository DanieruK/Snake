package MainFolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

public class DBZugang extends JFrame {

    private Control control;
    private JPanel titelPanel, buttonLabelPanel, searchPanel;
    private Font titleFont = new Font("Times New Roman", Font.PLAIN, 50);
    private Font standartFont = new Font("Times New Roman", Font.PLAIN, 20);
    private JToggleButton easyButton, mediumButton, hardButton;
    private JTextField searchField;
    private JButton searchButton;
    private Container con;

    public DBZugang(Control control) {
        super("Snake Game Datenbank");
        this.control = control;
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(new Dimension(600, 500));
        setLocationRelativeTo(null);

        con = getContentPane();
        con.setLayout(null);
        con.setBackground(Color.black);

        JPanel boxPanel = new JPanel();
        boxPanel.setLayout(null);
        boxPanel.setBackground(Color.BLACK);
        boxPanel.setBounds(150, 0, this.getWidth() - 300, this.getHeight() - 50);
        con.add(boxPanel);

        titelPanel = new JPanel();
        titelPanel.setBackground(Color.BLACK);
        titelPanel.setBounds(0, 0, boxPanel.getWidth(), 55);
        boxPanel.add(titelPanel);

        JLabel title = new JLabel("DATENBANK");
        title.setFont(titleFont);
        title.setForeground(Color.WHITE);
        titelPanel.add(title);

        buttonLabelPanel = new JPanel();
        buttonLabelPanel.setLayout(new GridLayout(2, 1));
        buttonLabelPanel.setBounds(0, 75, boxPanel.getWidth(), 150);
        buttonLabelPanel.setBackground(Color.BLACK);
        boxPanel.add(buttonLabelPanel);

        JLabel listenLabel = new JLabel("    AUSWAHL SCHWIERIGKEIT:");
        listenLabel.setFont(standartFont);
        listenLabel.setForeground(Color.WHITE);
        buttonLabelPanel.add(listenLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1));
        buttonLabelPanel.add(buttonPanel);

        easyButton = new JToggleButton("LEICHT");
        easyButton.setFont(standartFont);
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediumButton.setSelected(false);
                hardButton.setSelected(false);
            }
        });
        buttonPanel.add(easyButton);

        mediumButton = new JToggleButton("MITTEL");
        mediumButton.setFont(standartFont);
        mediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                easyButton.setSelected(false);
                hardButton.setSelected(false);
            }
        });
        buttonPanel.add(mediumButton);

        hardButton = new JToggleButton("SCHWER");
        hardButton.setFont(standartFont);
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                easyButton.setSelected(false);
                mediumButton.setSelected(false);
            }
        });
        buttonPanel.add(hardButton);

        searchPanel = new JPanel();
        searchPanel.setBackground(Color.BLACK);
        searchPanel.setBounds(0, 250, boxPanel.getWidth(), 30);
        boxPanel.add(searchPanel);

        JLabel searchLabel = new JLabel("SPIELER SUCHEN:");
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setFont(standartFont);
        searchPanel.add(searchLabel);

        searchField = new JTextField();
        searchField.setFont(standartFont);
        searchField.setBounds(0, 285, boxPanel.getWidth(), 30);
        boxPanel.add(searchField);

        searchButton = new JButton("SUCHEN");
        searchButton.setBackground(Color.BLACK);
        searchButton.setForeground(Color.WHITE);
        searchButton.setBounds(0, 340, boxPanel.getWidth(), 30);
        searchButton.setFont(standartFont);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boxPanel.setVisible(false);
                openTable();
            }
        });
        boxPanel.add(searchButton);

        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                control.closeDBcon();
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

        setVisible(true);
    }

    public void openTable() {
        String searchName;
        String schwName;
        if (searchField.getText() != "") {
            searchName = searchField.getText();
        } else searchName = "";

        if (easyButton.isSelected()) {
            schwName = "Leicht";
            System.out.println("Leicht");
        } else if (mediumButton.isSelected()) {
            schwName = "Mittel";
            System.out.println("Mittel");
        } else if (hardButton.isSelected()) {
            schwName = "Schwer";
        } else schwName = "";

        ArrayList<String[]> al = control.getArrayList(searchName, schwName);
        String data[][] = new String[al.size()][5];
        String column[] = {"Spielername", "Schwierigkeit", "Spielefeld", "Zeit", "Punkte"};

        for (int i = 0; i < data.length; i++) {
            data[i] = al.get(i);
        }

        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(Color.GREEN);
        tablePanel.setBounds(20, 20, getWidth() - 60, getHeight() - 80);
        tablePanel.setVisible(false);
        tablePanel.setLayout(new GridLayout(1, 1));
        con.add(tablePanel);

        JTable table = new JTable(data, column) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };

        JScrollPane scrollPane = new JScrollPane(table,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        tablePanel.add(scrollPane);
        tablePanel.setVisible(true);
    }
}
