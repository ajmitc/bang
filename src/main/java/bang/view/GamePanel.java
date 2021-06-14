package bang.view;

import bang.Model;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private Model model;
    private View view;

    private TablePanel tablePanel;

    public GamePanel(Model model, View view){
        super();
        this.model = model;
        this.view = view;

        tablePanel = new TablePanel(model, view);

        setLayout(new BorderLayout());
        add(tablePanel, BorderLayout.CENTER);
    }

    public void refresh(){
        tablePanel.refresh();
    }

    public TablePanel getTablePanel() {
        return tablePanel;
    }
}
