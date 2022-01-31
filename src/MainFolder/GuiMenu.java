package MainFolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiMenu extends JFrame{

    private final Font textFont = new Font("comic sans", Font.PLAIN, 18);
    private final JTextField userNameInput;
    private final JButton startButton = new JButton("Start");
    private final String[] modi = {"Easy", "Medium", " Hard"};
    private JComboBox<String> modusDropDown;

    public GuiMenu() {
        super("Snake Game Menu");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(400, 500));
        setLocationRelativeTo(null);

        Container con = getContentPane();
        con.setLayout(null);
        con.setBackground(Color.black);

        ImageIcon snakeIcon = new ImageIcon("src/img/Snake.png");
        JLabel iconLabel = new JLabel(snakeIcon);
        iconLabel.setBounds(50, 20, snakeIcon.getIconWidth(), snakeIcon.getIconHeight());
        con.add(iconLabel);

        JLabel userNameLabel = new JLabel("Username:");
        userNameLabel.setForeground(Color.white);
        userNameLabel.setFont(textFont);
        userNameLabel.setBounds(85, 130, 100, 30);
        con.add(userNameLabel);

        userNameInput = new JTextField();
        userNameInput.setFont(textFont);
        userNameInput.setBackground(Color.white);
        userNameInput.setForeground(Color.BLACK);
        userNameInput.setBounds(85, 170, 200, 30);
        con.add(userNameInput);

        JLabel modusLabel = new JLabel("Choose Difficulty:");
        modusLabel.setForeground(Color.white);
        modusLabel.setFont(textFont);
        modusLabel.setBounds(85, 220, 200, 30);
        con.add(modusLabel);

        modusDropDown = new JComboBox<String>(modi);
        modusDropDown.setBounds(85,260,200,30);
        modusDropDown.setBackground(Color.black);
        modusDropDown.setForeground(Color.white);
        modusDropDown.setVisible(true);
        modusDropDown.setFont(textFont);
        con.add(modusDropDown);

        startButton.setBackground(Color.BLACK);
        startButton.setForeground(Color.white);
        startButton.setBounds(85,320,200,30);
        startButton.setFont(textFont);
        con.add(startButton);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButtonActionPerformed(e);
            }
        });

        setVisible(true);
    }


    private void startButtonActionPerformed(ActionEvent e){

    }
}
