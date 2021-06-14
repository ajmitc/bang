package bang.game.player;

import bang.game.card.Card;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private Role role;
    private Character character;
    private int maxHitpoints = 0;
    private int hitpoints = 0;   // also hand limit
    private List<Card> cards = new ArrayList<>();
    private boolean computerControlled = true;

    public Player(){

    }

    public Player(Role role, Character character){
        setRole(role);
        setCharacter(character);
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
}
