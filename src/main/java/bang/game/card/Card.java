package bang.game.card;

import bang.util.ImageUtil;

import java.awt.image.BufferedImage;

public class Card {
    public static final int JACK  = 11;
    public static final int QUEEN = 12;
    public static final int KING  = 13;
    public static final int ACE   = 14;

    private boolean discardOnPlay = true;
    private CardType type;
    private CardEffect effect;
    private BufferedImage image;
    private PokerCardSuit suit;
    private int value;
    private int range = 1;  // Modified by weapon cards

    public Card(CardType type, CardEffect effect){
        this(type, effect, null, 0);
    }

    public Card(CardType type, CardEffect effect, PokerCardSuit suit, int value){
        this.type = type;
        this.effect = effect;
        this.image = effect.getImageFilename() != null? ImageUtil.get(effect.getImageFilename()): null;
        this.suit = suit;
        this.value = value;
    }

    public CardType getType() {
        return type;
    }

    public CardEffect getEffect() {
        return effect;
    }

    public boolean isDiscardOnPlay() {
        return discardOnPlay;
    }

    public void setDiscardOnPlay(boolean discardOnPlay) {
        this.discardOnPlay = discardOnPlay;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getValue() {
        return value;
    }

    public PokerCardSuit getSuit() {
        return suit;
    }

    public void setPokerCard(PokerCardSuit suit, int value){
        this.suit = suit;
        this.value = value;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }
}
