package bang.game.player;

import bang.game.card.Card;
import bang.game.card.CardEffect;
import bang.game.card.CardType;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

public class Player {
    private Role role;
    private Character character;
    private int maxHitpoints = 0;
    private int hitpoints = 0;   // also hand limit
    private List<Card> cards = new ArrayList<>();
    private List<Card> cardsInPlay = new ArrayList<>(); // Cards in play on table
    private boolean computerControlled = true;

    private boolean skipTurn = false;

    public Player(){

    }

    public Player(Role role, Character character){
        setRole(role);
        setCharacter(character);
    }

    /**
     * Get the max range from this player's weapons
     * @return
     */
    public int getMaximumRange(){
        OptionalInt max = cardsInPlay.stream().filter(card -> card.getType() == CardType.WEAPON).mapToInt(Card::getRange).max();
        int range = max.isPresent()? max.getAsInt(): 1;
        for (Card card: cardsInPlay){
            if (card.getEffect() == CardEffect.SCOPE){
                range += 1;
            }
        }
        if (character == Character.ROSE_DOOLAN)
            range += 1;
        return range;
    }

    /**
     * Get the modifier when another player is targeting this player
     * For example, a MUSTANG will cause this player to be +1 distance from other players
     * @return
     */
    public int getOtherPlayerRangeModifier(){
        int mod = 0;
        for (Card card: cardsInPlay){
            if (card.getType() == CardType.MUSTANG){
                mod += 1;
            }
        }
        return mod;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
        if (role == Role.SHERIFF) {
            this.maxHitpoints += 1;
            this.hitpoints += 1;
        }
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
        this.maxHitpoints += character.getMaxHitpoints();
        this.hitpoints += character.getMaxHitpoints();
    }

    public int getMaxHitpoints() {
        return maxHitpoints;
    }

    public void setHitpoints(int hitpoints) {
        this.hitpoints = hitpoints;
    }

    public void adjHitpoints(int amount) {
        this.hitpoints += amount;
        if (this.hitpoints > this.maxHitpoints)
            this.hitpoints = this.maxHitpoints;
        if (this.hitpoints < 0)
            this.hitpoints = 0;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public List<Card> getCards() {
        return cards;
    }

    public boolean isComputerControlled() {
        return computerControlled;
    }

    public void setComputerControlled(boolean computerControlled) {
        this.computerControlled = computerControlled;
    }

    public List<Card> getCardsInPlay() {
        return cardsInPlay;
    }

    public boolean isSkipTurn() {
        return skipTurn;
    }

    public void setSkipTurn(boolean skipTurn) {
        this.skipTurn = skipTurn;
    }

    public String toString(){
        return character.getName();
    }
}
