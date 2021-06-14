package bang.game;

import bang.game.card.Card;
import bang.game.card.Deck;
import bang.game.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Phase phase;
    private PhaseStep phaseStep;

    private List<Player> players = new ArrayList<>();
    private int currentPlayerIndex = 0;
    private Deck<Card> deck = new Deck<>();

    public Game(){
        phase = Phase.SETUP;
        phaseStep = PhaseStep.START_PHASE;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
        this.setPhaseStep(PhaseStep.START_PHASE);
    }

    public PhaseStep getPhaseStep() {
        return phaseStep;
    }

    public void setPhaseStep(PhaseStep phaseStep) {
        this.phaseStep = phaseStep;
    }

    public boolean isGameOver(){
        return this.phase == Phase.GAMEOVER;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setNextPlayer(){
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public Player getCurrentPlayer(){
        return players.get(currentPlayerIndex);
    }

    public Deck<Card> getDeck() {
        return deck;
    }
}
