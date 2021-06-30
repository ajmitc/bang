package bang.game.card;

public class Card {
    public static final int JACK  = 11;
    public static final int QUEEN = 12;
    public static final int KING  = 13;
    public static final int ACE   = 14;

    private CardType type;
    private CardEffect effect;
    private PokerCardSuit suit;
    private int value;
    private int range = 1;  // Modified by weapon cards
    private boolean discardOnPlay = true;

    public Card(CardType type, CardEffect effect){
        this(type, effect, null, 0);
    }

    public Card(CardType type, CardEffect effect, PokerCardSuit suit, int value){
        this(type, effect, suit, value, 1, true);
    }

    public Card(CardType type, CardEffect effect, PokerCardSuit suit, int value, int range, boolean discardOnPlay){
        this.type = type;
        this.effect = effect;
        this.suit = suit;
        this.value = value;
        this.range = range;
        this.discardOnPlay = discardOnPlay;
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

    public String toString(){
        return effect.name();
    }
}
