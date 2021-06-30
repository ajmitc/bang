package bang.view;

import bang.Model;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {
    private Model model;
    private View view;

    private JButton btnNewGame;
    private JButton btnExit;

    public MainMenuPanel(Model model, View view){
        super();
        this.model = model;
        this.view = view;

        btnNewGame = new JButton("New Game");
        btnExit = new JButton("Exit");

        JPanel buttonpanel = new JPanel();
        buttonpanel.setLayout(new BoxLayout(buttonpanel, BoxLayout.PAGE_AXIS));
        buttonpanel.add(btnNewGame);
        buttonpanel.add(btnExit);

        JLabel lblCover = new JLabel("BANG!");//new ImageIcon(ImageUtil.get("")));

        add(lblCover, BorderLayout.WEST);
        add(buttonpanel, BorderLayout.EAST);
    }

    public JButton getBtnNewGame() {
        return btnNewGame;
    }

    public JButton getBtnExit() {
        return btnExit;
    }
}
