package MainFolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DBZugang extends JFrame {

    private Control control;
    private JPanel titelPanel, buttonPanel, searchPlayer;
    private Font titleFont = new Font("Times New Roman", Font.PLAIN, 80);
    private Font standartFont = new Font("Times New Roman", Font.PLAIN, 30);
    private JToggleButton easyButton, mediumButton, hardButton;
    private JTextField searchField;
    private JButton searchButton;
    private Container con;

    public DBZugang(Control control) {
        super("Snake Game Datenbank");
        this.control = control;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(600, 800));
        setLocationRelativeTo(null);

        con = getContentPane();
        con.setLayout(null);
        con.setBackground(Color.black);

        titelPanel = new JPanel();
        titelPanel.setBounds(0, getHeight() / 25, getWidth(), getHeight() / 6);
        titelPanel.setBackground(Color.BLACK);
        con.add(titelPanel);

        JLabel title = new JLabel("DATENBANK");
        title.setFont(titleFont);
        title.setForeground(Color.WHITE);
        titelPanel.add(title);

        buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setBounds(0, getHeight() / 4, getWidth(), getHeight() / 4);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        con.add(buttonPanel);

        JLabel listenLabel = new JLabel("AUSWAHL SCHWIERIGKEIT");
        listenLabel.setFont(standartFont);
        listenLabel.setForeground(Color.WHITE);
        listenLabel.setSize(80, 20);
        listenLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(listenLabel);
        buttonPanel.add(Box.createVerticalGlue());

        easyButton = new JToggleButton("LEICHT");
        easyButton.setFont(standartFont);
        easyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediumButton.setSelected(false);
                hardButton.setSelected(false);
            }
        });
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(easyButton);
        buttonPanel.add(Box.createVerticalGlue());

        mediumButton = new JToggleButton("MITTEL");
        mediumButton.setFont(standartFont);
        mediumButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                easyButton.setSelected(false);
                hardButton.setSelected(false);
            }
        });
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(mediumButton);
        buttonPanel.add(Box.createVerticalGlue());

        hardButton = new JToggleButton("SCHWER");
        hardButton.setFont(standartFont);
        hardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                easyButton.setSelected(false);
                mediumButton.setSelected(false);
            }
        });
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(hardButton);
        buttonPanel.add(Box.createVerticalGlue());

        searchPlayer = new JPanel();
        searchPlayer.setBounds(100, (int) (getHeight() * 0.55), getWidth() - 200, getHeight() / 5);
        searchPlayer.setBackground(Color.BLACK);
        searchPlayer.setLayout(null);
        con.add(searchPlayer);

        JLabel searchLabel = new JLabel("SPIELER SUCHEN");
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setFont(standartFont);
        searchLabel.setBounds(searchPlayer.getWidth() / 5, 30, searchPlayer.getWidth(), 30);
        searchPlayer.add(searchLabel);

        searchField = new JTextField();
        searchField.setFont(standartFont);
        searchField.setBounds((int) (searchPlayer.getWidth() / 8.5), 80, (int) (searchPlayer.getWidth() * 0.8), 40);
        searchPlayer.add(searchField);

        searchButton = new JButton("SUCHEN");
        searchButton.setBackground(Color.BLACK);
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(standartFont);
        searchButton.setBounds((int) (getWidth() * 0.26), (int) (getHeight() * 0.8), getWidth() / 2, 50);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                titelPanel.setVisible(false);
                buttonPanel.setVisible(false);
                searchPlayer.setVisible(false);
                searchButton.setVisible(false);
                openTable();
            }
        });
        con.add(searchButton);

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
        String data[][] = new String[al.size()][4];
        String column[] = {"Spielername", "Schwierigkeit", "Zeit", "Punkte"};

        for (int i = 0; i < data.length; i++) {
            data[i] = al.get(i);
        }

        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(Color.GREEN);
        tablePanel.setBounds(20, 20, getWidth() - 60, getHeight() - 80);
        tablePanel.setVisible(false);
        tablePanel.setLayout(new GridLayout(1, 1));
        con.add(tablePanel);

        JTable table = new JTable(data, column);

        JScrollPane scrollPane = new JScrollPane(table,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        tablePanel.add(scrollPane);
        tablePanel.setVisible(true);
    }
}
