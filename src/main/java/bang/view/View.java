package bang.view;

import bang.Model;

import javax.swing.*;
import java.awt.*;

public class View {
    private static final String MAIN_MENU = "mainmenu";
    private static final String GAME = "game";

    private Model model;
    private JFrame frame;

    private MainMenuPanel mainMenuPanel;
    private GamePanel gamePanel;

    public View(Model model){
        this.model = model;
        this.frame = new JFrame();

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        //frame.setSize(200, 200);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());

        mainMenuPanel = new MainMenuPanel(model, this);
        gamePanel = new GamePanel(model, this);

        frame.getContentPane().setLayout(new CardLayout());
        frame.getContentPane().add(mainMenuPanel, MAIN_MENU);
        frame.getContentPane().add(gamePanel, GAME);
    }

    public void showGame(){
        show(GAME);
    }

    public void showMainMenu(){
        show(MAIN_MENU);
    }

    private void show(String name){
        CardLayout cardLayout = (CardLayout) frame.getContentPane().getLayout();
        cardLayout.show(frame.getContentPane(), name);
    }

    public void refresh(){
        gamePanel.refresh();
    }

    public MainMenuPanel getMainMenuPanel() {
        return mainMenuPanel;
    }

    public JFrame getFrame() {
        return frame;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}
