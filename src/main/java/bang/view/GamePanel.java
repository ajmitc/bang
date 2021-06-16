package bang.view;

import bang.Model;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private Model model;
    private View view;

    private TablePanel tablePanel;
    private PlayerCardsPanel playerCardsPanel;

    private JButton btnPass;

    public GamePanel(Model model, View view){
        super();
        this.model = model;
        this.view = view;

        tablePanel = new TablePanel(model, view);
        playerCardsPanel = new PlayerCardsPanel(model, view);

        btnPass = new JButton("Pass");

        JPanel southpanel = new JPanel(new BorderLayout());
        southpanel.add(playerCardsPanel, BorderLayout.CENTER);
        southpanel.add(btnPass, BorderLayout.EAST);

        setLayout(new BorderLayout());
        add(tablePanel, BorderLayout.CENTER);
        add(southpanel, BorderLayout.SOUTH);
    }

    public void refresh(){
        tablePanel.refresh();
    }

    public TablePanel getTablePanel() {
        return tablePanel;
    }

    public PlayerCardsPanel getPlayerCardsPanel() {
        return playerCardsPanel;
    }

    public JButton getBtnPass() {
        return btnPass;
    }
}
