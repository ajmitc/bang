package bang.view;

import bang.Model;
import bang.game.card.Card;
import bang.game.player.Player;
import bang.util.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class PlayerCardsPanel extends JPanel {
    private static final int CARD_WIDTH = 150;

    private Model model;
    private View view;

    public PlayerCardsPanel(Model model, View view){
        super();
        setMinimumSize(new Dimension(CARD_WIDTH, 240));
        setPreferredSize(new Dimension(CARD_WIDTH, 240));
        this.model = model;
        this.view = view;
    }

    public Card getSelectedCard(int mx, int my){
        int index = mx / CARD_WIDTH;
        if (index >= 0 && index < model.getGame().getHumanPlayer().getCards().size()){
            return model.getGame().getHumanPlayer().getCards().get(index);
        }
        return null;
    }

    public void paintComponent(Graphics graphics){
        if (model.getGame() == null || model.getGame().getHumanPlayer() == null)
            return;

        Player player = model.getGame().getHumanPlayer();
        List<Card> cards = player.getCards();

        for (int i = 0; i < cards.size(); ++i){
            Card card = cards.get(i);
            BufferedImage bufferedImage = ImageUtil.get(card.getEffect().getImageFilename(), CARD_WIDTH, card.getEffect().getImageFilename() + "-" + CARD_WIDTH);
            graphics.drawImage(bufferedImage, i * bufferedImage.getWidth(), 0, null);
        }
    }
}
