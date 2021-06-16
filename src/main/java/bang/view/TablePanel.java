package bang.view;

import bang.Model;
import bang.game.player.Player;
import bang.util.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Draw the table with players
 */
public class TablePanel extends JPanel {
    private static final int PLAYER_DRAW_AREA_WIDTH  = 200;
    private static final int PLAYER_DRAW_AREA_HEIGHT = 200;
    private Model model;
    private View view;

    // {Number of Players: [Human-Player Point, Comp Player Point, ...]}
    private Map<Integer, List<Point>> playerDrawAreas = new HashMap<>();

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
            playerDrawAreas.put(4, new ArrayList<>());
            // Human player
            playerDrawAreas.get(4).add(new Point((getWidth() - PLAYER_DRAW_AREA_WIDTH) / 2, getHeight() - PLAYER_DRAW_AREA_HEIGHT));
            playerDrawAreas.get(4).add(new Point(0, (getHeight() - PLAYER_DRAW_AREA_HEIGHT) / 2));
            playerDrawAreas.get(4).add(new Point((getWidth() - PLAYER_DRAW_AREA_WIDTH) / 2, 0));
            playerDrawAreas.get(4).add(new Point(getWidth() - PLAYER_DRAW_AREA_WIDTH, (getHeight() - PLAYER_DRAW_AREA_HEIGHT) / 2));

            playerDrawAreas.get(5).add(new Point((getWidth() - PLAYER_DRAW_AREA_WIDTH) / 2, getHeight() - PLAYER_DRAW_AREA_HEIGHT));
            // TODO Finish this
            playerDrawAreas.get(6).add(new Point((getWidth() - PLAYER_DRAW_AREA_WIDTH) / 2, getHeight() - PLAYER_DRAW_AREA_HEIGHT));
            // TODO Finish this
            playerDrawAreas.get(7).add(new Point((getWidth() - PLAYER_DRAW_AREA_WIDTH) / 2, getHeight() - PLAYER_DRAW_AREA_HEIGHT));
            // TODO Finish this
        }

        int playerIndex = model.getGame().getPlayers().indexOf(model.getGame().getCurrentPlayer());
        List<Player> orderedPlayers = new ArrayList<>();
        while (orderedPlayers.size() < model.getGame().getPlayers().size()){
            orderedPlayers.add(model.getGame().getPlayers().get(playerIndex));
            playerIndex = (playerIndex + 1) % model.getGame().getPlayers().size();
        }

        for (int i = 0; i < orderedPlayers.size(); ++i){
            Point pt = playerDrawAreas.get(model.getGame().getPlayers().size()).get(i);
            drawPlayer(graphics, orderedPlayers.get(i), pt);
        }
    }

    private void drawPlayer(Graphics graphics, Player player, Point pt){
        boolean onLeft = pt.x < (getWidth() / 2);
        boolean onTop  = pt.y < (getHeight() / 2);

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
