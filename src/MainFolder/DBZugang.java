package MainFolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DBZugang extends JFrame {

    private Control control;
    private Font titleFont = new Font("Times New Roman", Font.PLAIN, 80);
    private Font standartFont = new Font("Times New Roman", Font.PLAIN, 30);
    private JToggleButton easyButton, mediumButton, hardButton;

    public DBZugang(Control control) {
        super("Snake Game Datenbank");
        this.control = control;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(600, 800));
        setLocationRelativeTo(null);

        Container con = getContentPane();
        con.setLayout(null);
        con.setBackground(Color.black);

        JPanel titelPanel = new JPanel();
        titelPanel.setBounds(0, getHeight() / 25, getWidth(), getHeight() / 6);
        titelPanel.setBackground(Color.BLACK);
        con.add(titelPanel);

        JLabel title = new JLabel("DATENBANK");
        title.setFont(titleFont);
        title.setForeground(Color.WHITE);
        titelPanel.add(title);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setBounds(0, getHeight() / 4, getWidth(), getHeight() / 4);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        con.add(buttonPanel);

        JLabel listenLabel = new JLabel("AUSWAHL SCHWIERIGKEIT");
        listenLabel.setFont(standartFont);
        listenLabel.setForeground(Color.WHITE);
        listenLabel.setSize(80,20);
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

        JPanel searchPlayer = new JPanel();
        searchPlayer.setBounds(100, (int) (getHeight()*0.55),getWidth()-200,getHeight()/5);
        searchPlayer.setBackground(Color.BLACK);
        searchPlayer.setLayout(null);
        con.add(searchPlayer);

        JLabel searchLabel = new JLabel("SPIELER SUCHEN");
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setFont(standartFont);
        searchLabel.setBounds(searchPlayer.getWidth()/5,30,searchPlayer.getWidth(),30);
        searchPlayer.add(searchLabel);

        JTextField searchField = new JTextField();
        searchField.setFont(standartFont);
        searchField.setBounds((int) (searchPlayer.getWidth()/8.5),80, (int) (searchPlayer.getWidth()*0.8),40);
        searchPlayer.add(searchField);

        JButton searchButton = new JButton("SUCHEN");
        searchButton.setBackground(Color.BLACK);
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(standartFont);
        searchButton.setBounds((int) (getWidth()*0.26), (int) (getHeight()*0.8),getWidth()/2,50);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        con.add(searchButton);

        setVisible(true);
    }

    public static void main(String[] args) {
        Control c = new Control();
        DBZugang testWindow = new DBZugang(c);
    }
}
