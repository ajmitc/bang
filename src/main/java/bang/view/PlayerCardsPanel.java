package bang.view;

import bang.Model;
import bang.game.card.Card;
import bang.game.player.Player;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PlayerCardsPanel extends JPanel {
    private Model model;
    private View view;
    private int cardWidth;

    public PlayerCardsPanel(Model model, View view){
        super();
        this.model = model;
        this.view = view;
    }

    public Card getSelectedCard(int mx, int my){
        int index = mx / cardWidth;
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
            graphics.drawImage(card.getImage(), i * card.getImage().getWidth(), 0, null);
            cardWidth = card.getImage().getWidth();
        }
    }
}
