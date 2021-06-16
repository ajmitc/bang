package bang.view;

import bang.Model;

import javax.swing.*;
import java.awt.*;

public class View {
    private Model model;
    private JFrame frame;

    private GamePanel gamePanel;

    public View(Model model){
        this.model = model;
        this.frame = new JFrame();

        gamePanel = new GamePanel(model, this);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(gamePanel, BorderLayout.CENTER);
    }

    public void refresh(){
        gamePanel.refresh();
    }

    public JFrame getFrame() {
        return frame;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}
