package bang.game.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck<T> {
    private List<T> cards = new ArrayList<>();
    private List<T> discardPile = new ArrayList<>();

    public Deck(){

    }

    public T draw(){
        if (cards.size() == 0)
            return null;
        return cards.remove(0);
    }

    public T drawBottom(){
        return cards.remove(cards.size() - 1);
    }

    public void discard(T card){
        discardPile.add(card);
    }

    public List<T> getCards() {
        return cards;
    }

    public List<T> getDiscardPile() {
        return discardPile;
    }

    public void add(T card){
        this.cards.add(card);
    }

    public void shuffle(){
        Collections.shuffle(this.cards);
    }
}
