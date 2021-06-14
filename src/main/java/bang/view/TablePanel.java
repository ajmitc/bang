package bang.view;

import bang.Model;
import bang.game.player.Player;
import bang.util.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Draw the table with players
 */
public class TablePanel extends JPanel {
    private Model model;
    private View view;

    private Map<Player, Point> playerDrawAreas = new HashMap<>();

    public TablePanel(Model model, View view){
        super();
        this.model = model;
        this.view = view;
    }

    public void refresh(){
        repaint();
    }

    public void paintComponent(Graphics graphics){
        if (playerDrawAreas.isEmpty()){
            for (int i = 0; i < model.getGame().getPlayers().size(); ++i){
                // Calculate upper-left corner of drawing area for this player and record in playerDrawAreas
                // TODO Player is always at bottom of screen
            }
        }

        for (Player player: model.getGame().getPlayers()){
            drawPlayer(graphics, player);
        }
    }

    private void drawPlayer(Graphics graphics, Player player){
        Point pt = playerDrawAreas.get(player);

        // TODO Draw player
        BufferedImage image = ImageUtil.get(player.getCharacter().getImageFilename());
        graphics.drawImage(image, pt.x, pt.y, null);

        if (player.getHitpoints() == 0){
            // Display Role Card
            image = ImageUtil.get(player.getRole().getImageFilename());
            // TODO Rotate image 45 degrees
            graphics.drawImage(image, pt.x, pt.y, null);
        }

        // TODO Display hitpoints
        // TODO Display cards in hand
        // TODO Indicate current player
    }
}
