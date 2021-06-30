package bang.view;

import bang.Model;
import bang.game.player.Player;
import bang.game.player.Role;
import bang.util.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Draw the table with players
 */
public class TablePanel extends JPanel {
    private static final int PLAYER_CARD_WIDTH = 150;
    private static final int PLAYER_DRAW_AREA_WIDTH  = PLAYER_CARD_WIDTH * 2;
    private static final int PLAYER_DRAW_AREA_HEIGHT = 220;

    private static final String ROLE_CARD_BACK = "RETRO_C.JPG";

    private Model model;
    private View view;

    // {Number of Players: [Human-Player Point, Comp Player Point, ...]}
    private Map<Integer, List<Point>> playerDrawAreas = new HashMap<>();

    private int mx, my;

    public TablePanel(Model model, View view){
        super();
        this.model = model;
        this.view = view;

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                mx = e.getX();
                my = e.getY();
                refresh();
            }
        });
    }

    public Player getSelectedPlayer(int mx, int my){
        if (!playerDrawAreas.isEmpty()) {
            List<Player> orderedPlayers = model.getGame().inPlayerOrderHumanFirst();
            List<Point> points = playerDrawAreas.get(model.getGame().getPlayers().size());
            for (int i = 0; i < points.size(); ++i) {
                Point p = points.get(i);
                if (mx >= p.x && mx < p.x + PLAYER_DRAW_AREA_WIDTH && my >= p.y && my < p.y + PLAYER_DRAW_AREA_HEIGHT) {
                    return orderedPlayers.get(i);
                }
            }
        }
        return null;
    }

    public void refresh(){
        repaint();
    }

    public void paintComponent(Graphics graphics){
        if (model.getGame() == null)
            return;
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, getWidth(), getHeight());

        if (playerDrawAreas.isEmpty()){
            playerDrawAreas.put(4, new ArrayList<>());
            playerDrawAreas.put(5, new ArrayList<>());
            playerDrawAreas.put(6, new ArrayList<>());
            playerDrawAreas.put(7, new ArrayList<>());

            // Human player
            playerDrawAreas.get(4).add(new Point((getWidth() - PLAYER_DRAW_AREA_WIDTH) / 2, getHeight() - PLAYER_DRAW_AREA_HEIGHT));
            // Computer Players
            // Left side of screen
            playerDrawAreas.get(4).add(new Point(0, (getHeight() - PLAYER_DRAW_AREA_HEIGHT) / 2));
            // Top of screen
            playerDrawAreas.get(4).add(new Point((getWidth() - PLAYER_DRAW_AREA_WIDTH) / 2, 0));
            // Right side of screen
            playerDrawAreas.get(4).add(new Point(getWidth() - PLAYER_DRAW_AREA_WIDTH, (getHeight() - PLAYER_DRAW_AREA_HEIGHT) / 2));

            // 5 Player Coordinates
            playerDrawAreas.get(5).add(new Point((getWidth() - PLAYER_DRAW_AREA_WIDTH) / 2, getHeight() - PLAYER_DRAW_AREA_HEIGHT));
            // TODO Finish this

            // 6 Player Coordinates
            playerDrawAreas.get(6).add(new Point((getWidth() - PLAYER_DRAW_AREA_WIDTH) / 2, getHeight() - PLAYER_DRAW_AREA_HEIGHT));
            // TODO Finish this

            // 7 Player Coordinates
            playerDrawAreas.get(7).add(new Point((getWidth() - PLAYER_DRAW_AREA_WIDTH) / 2, getHeight() - PLAYER_DRAW_AREA_HEIGHT));
            // TODO Finish this
        }

        List<Player> orderedPlayers = model.getGame().inPlayerOrderHumanFirst();
        for (int i = 0; i < orderedPlayers.size(); ++i){
            Point pt = playerDrawAreas.get(model.getGame().getPlayers().size()).get(i);
            drawPlayer(graphics, orderedPlayers.get(i), pt);
        }

        graphics.setColor(Color.BLACK);
        graphics.drawString(mx + ", " + my, 20, 20);
    }

    private void drawPlayer(Graphics graphics, Player player, Point pt){
        int x = pt.x;
        int y = pt.y;

        // Draw character card
        BufferedImage image = ImageUtil.get(player.getCharacter().getImageFilename(), PLAYER_CARD_WIDTH);
        graphics.drawImage(image, x, y, null);

        // If player is dead, draw X over character card
        if (player.getHitpoints() == 0){
            graphics.setColor(Color.RED);
            graphics.drawLine(x, y, x + PLAYER_CARD_WIDTH, y + image.getHeight());
            graphics.drawLine(x + PLAYER_CARD_WIDTH, y, x, y + image.getHeight());
        }

        // Display Role Card next to Character card
        image = ImageUtil.get(player.getRole() == Role.SHERIFF || player.getHitpoints() == 0? player.getRole().getImageFilename(): ROLE_CARD_BACK, PLAYER_CARD_WIDTH);
        graphics.drawImage(image, x + PLAYER_CARD_WIDTH, y, null);

        // Display hitpoints in top-right corner of character card
        graphics.setColor(Color.RED);
        graphics.drawString(player.getHitpoints() + "/" + player.getMaxHitpoints(), x + PLAYER_CARD_WIDTH - 15, y + 10);

        // TODO Display cards in hand

        // Indicate current player
        graphics.setColor(Color.RED);
        graphics.drawString("*", x, y + 10);

        // TODO Display cards in play
    }
}
