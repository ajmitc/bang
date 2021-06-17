package bang.game;

import bang.game.card.*;
import bang.game.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Game {
    private Phase phase;
    private PhaseStep phaseStep;

    private List<Player> players = new ArrayList<>();
    private int currentPlayerIndex = 0;
    private Deck<Card> deck = new Deck<>();

    public Game(){
        phase = Phase.SETUP;
        phaseStep = PhaseStep.START_PHASE;

        // Bang cards
        deck.add(new Card(CardType.BANG, CardEffect.BANG_2C, PokerCardSuit.CLUBS, 2));
        deck.add(new Card(CardType.BANG, CardEffect.BANG_2D, PokerCardSuit.DIAMONDS, 2));
        deck.add(new Card(CardType.BANG, CardEffect.BANG_3C, PokerCardSuit.CLUBS, 3));
        deck.add(new Card(CardType.BANG, CardEffect.BANG_3D, PokerCardSuit.DIAMONDS, 3));

        // Missed cards
        deck.add(new Card(CardType.MISSED, CardEffect.MISSED_2S, PokerCardSuit.SPADES, 2));
        deck.add(new Card(CardType.MISSED, CardEffect.MISSED_3S, PokerCardSuit.SPADES, 3));
        deck.add(new Card(CardType.MISSED, CardEffect.MISSED_4S, PokerCardSuit.SPADES, 4));
        deck.add(new Card(CardType.MISSED, CardEffect.MISSED_5S, PokerCardSuit.SPADES, 5));
        deck.add(new Card(CardType.MISSED, CardEffect.MISSED_6S, PokerCardSuit.SPADES, 6));
        deck.add(new Card(CardType.MISSED, CardEffect.MISSED_7S, PokerCardSuit.SPADES, 7));
        deck.add(new Card(CardType.MISSED, CardEffect.MISSED_8S, PokerCardSuit.SPADES, 8));
        deck.add(new Card(CardType.MISSED, CardEffect.MISSED_TC, PokerCardSuit.CLUBS, 10));
        deck.add(new Card(CardType.MISSED, CardEffect.MISSED_JC, PokerCardSuit.CLUBS, Card.JACK));
        deck.add(new Card(CardType.MISSED, CardEffect.MISSED_QC, PokerCardSuit.CLUBS, Card.QUEEN));
        deck.add(new Card(CardType.MISSED, CardEffect.MISSED_KC, PokerCardSuit.CLUBS, Card.KING));
        deck.add(new Card(CardType.MISSED, CardEffect.MISSED_AC, PokerCardSuit.CLUBS, Card.ACE));

        // Panic Cards
        deck.add(new Card(CardType.PANIC, CardEffect.PANIC_8D, PokerCardSuit.DIAMONDS, 8));
        deck.add(new Card(CardType.PANIC, CardEffect.PANIC_AH, PokerCardSuit.HEARTS, Card.ACE));
        deck.add(new Card(CardType.PANIC, CardEffect.PANIC_JH, PokerCardSuit.HEARTS, Card.JACK));
        deck.add(new Card(CardType.PANIC, CardEffect.PANIC_QH, PokerCardSuit.HEARTS, Card.QUEEN));

        // Mustang
        deck.add(new Card(CardType.MUSTANG, CardEffect.MUSTANG_8H, PokerCardSuit.HEARTS, 8));
        deck.add(new Card(CardType.MUSTANG, CardEffect.MUSTANG_9H, PokerCardSuit.HEARTS, 9));

        // Weapons (Each player can only have 1 Weapon in play at a time)
        deck.add(new Card(CardType.WEAPON, CardEffect.REMINGTON, PokerCardSuit.CLUBS, Card.KING, 3, false));
        deck.add(new Card(CardType.WEAPON, CardEffect.REV_CARABINA, PokerCardSuit.SPADES, 4, 4, false));
        deck.add(new Card(CardType.WEAPON, CardEffect.SCHOFIELD_JC, PokerCardSuit.CLUBS, Card.JACK, 2, false));
        deck.add(new Card(CardType.WEAPON, CardEffect.SCHOFIELD_QC, PokerCardSuit.CLUBS, Card.QUEEN, 2, false));
        deck.add(new Card(CardType.WEAPON, CardEffect.SCHOFIELD_KC, PokerCardSuit.CLUBS, Card.KING, 2, false));
        deck.add(new Card(CardType.WEAPON, CardEffect.VOLCANIC_TC, PokerCardSuit.CLUBS, 10, 1, false));
        deck.add(new Card(CardType.WEAPON, CardEffect.VOLCANIC_TS, PokerCardSuit.SPADES, 10, 1, false));
        deck.add(new Card(CardType.WEAPON, CardEffect.WINCHESTER, PokerCardSuit.SPADES, 8, 5, false));

        // Indians
        deck.add(new Card(CardType.INDIANS, CardEffect.INDIANS_AD, PokerCardSuit.DIAMONDS, Card.ACE));
        deck.add(new Card(CardType.INDIANS, CardEffect.INDIANS_KD, PokerCardSuit.DIAMONDS, Card.KING));

        // Special cards
        deck.add(new Card(CardType.SPECIAL, CardEffect.SCOPE, PokerCardSuit.SPADES, Card.ACE));

        deck.add(new Card(CardType.SPECIAL, CardEffect.BARREL_K, PokerCardSuit.SPADES, Card.KING));
        deck.add(new Card(CardType.SPECIAL, CardEffect.BARREL_Q, PokerCardSuit.SPADES, Card.QUEEN));

        deck.add(new Card(CardType.SPECIAL, CardEffect.BEER_6, PokerCardSuit.HEARTS, 6));
        deck.add(new Card(CardType.SPECIAL, CardEffect.BEER_7, PokerCardSuit.HEARTS, 7));
        deck.add(new Card(CardType.SPECIAL, CardEffect.BEER_8, PokerCardSuit.HEARTS, 8));
        deck.add(new Card(CardType.SPECIAL, CardEffect.BEER_9, PokerCardSuit.HEARTS, 9));
        deck.add(new Card(CardType.SPECIAL, CardEffect.BEER_T, PokerCardSuit.HEARTS, 10));
        deck.add(new Card(CardType.SPECIAL, CardEffect.BEER_J, PokerCardSuit.HEARTS, Card.JACK));

        deck.add(new Card(CardType.SPECIAL, CardEffect.GATLING, PokerCardSuit.HEARTS, 10));

        deck.add(new Card(CardType.SPECIAL, CardEffect.GENERAL_STORE_9C, PokerCardSuit.CLUBS, 9));
        deck.add(new Card(CardType.SPECIAL, CardEffect.GENERAL_STORE_QS, PokerCardSuit.SPADES, Card.QUEEN));

        deck.add(new Card(CardType.SPECIAL, CardEffect.DUEL_8C, PokerCardSuit.CLUBS, 8));
        deck.add(new Card(CardType.SPECIAL, CardEffect.DUEL_JS, PokerCardSuit.SPADES, Card.JACK));
        deck.add(new Card(CardType.SPECIAL, CardEffect.DUEL_QD, PokerCardSuit.DIAMONDS, Card.QUEEN));

        deck.add(new Card(CardType.SPECIAL, CardEffect.STAGECOACH, PokerCardSuit.SPADES, 9));
        deck.add(new Card(CardType.SPECIAL, CardEffect.WELLS_FARGO, PokerCardSuit.HEARTS, 3));

        deck.add(new Card(CardType.SPECIAL, CardEffect.CAT_BALOU_TD, PokerCardSuit.DIAMONDS, 10));
        deck.add(new Card(CardType.SPECIAL, CardEffect.CAT_BALOU_9D, PokerCardSuit.DIAMONDS, 9));
        deck.add(new Card(CardType.SPECIAL, CardEffect.CAT_BALOU_JD, PokerCardSuit.DIAMONDS, Card.JACK));
        deck.add(new Card(CardType.SPECIAL, CardEffect.CAT_BALOU_KD, PokerCardSuit.DIAMONDS, Card.KING));

        deck.add(new Card(CardType.SPECIAL, CardEffect.DYNAMITE, PokerCardSuit.HEARTS, 2, 1, false));

        deck.add(new Card(CardType.SPECIAL, CardEffect.SALOON, PokerCardSuit.HEARTS, 5));

        deck.add(new Card(CardType.SPECIAL, CardEffect.JAIL_4H, PokerCardSuit.HEARTS, 4));
        deck.add(new Card(CardType.SPECIAL, CardEffect.JAIL_TH, PokerCardSuit.HEARTS, 10));
        deck.add(new Card(CardType.SPECIAL, CardEffect.JAIL_JH, PokerCardSuit.HEARTS, Card.JACK));
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

    public Player getHumanPlayer(){
        return players.stream().filter(player -> !player.isComputerControlled()).findFirst().get();
    }

    public List<Player> inPlayerOrder(Player startingPlayer){
        int index = players.indexOf(startingPlayer);
        List<Player> orderedPlayers = new ArrayList<>();
        while (orderedPlayers.size() < players.size()){
            orderedPlayers.add(players.get(index));
            index = (index + 1) % players.size();
        }
        return orderedPlayers;
    }

    public List<Player> getOtherPlayers(Player player){
        return players.stream().filter(player1 -> player1 != player).collect(Collectors.toList());
    }

    public Deck<Card> getDeck() {
        return deck;
    }
}
