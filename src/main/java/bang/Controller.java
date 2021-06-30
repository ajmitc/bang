package bang;

import bang.game.Game;
import bang.game.Phase;
import bang.game.PhaseStep;
import bang.game.card.Card;
import bang.game.card.CardEffect;
import bang.game.card.CardType;
import bang.game.card.PokerCardSuit;
import bang.game.player.Character;
import bang.game.player.Player;
import bang.game.player.Role;
import bang.util.Util;
import bang.view.View;
import bang.view.ViewUtil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.stream.Collectors;

public class Controller {
    private Model model;
    private View view;

    private boolean currentPlayerPassed = false;

    public Controller(Model model, View view){
        this.model = model;
        this.view = view;

        view.getGamePanel().getPlayerCardsPanel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (model.getGame().getPhaseStep() == PhaseStep.PLAY_CARDS && e.getClickCount() >= 2){
                    Card card = view.getGamePanel().getPlayerCardsPanel().getSelectedCard(e.getX(), e.getY());
                    if (card != null){
                        playerPlayCard(card);
                        run();
                    }
                }
            }
        });

        view.getGamePanel().getBtnPass().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (model.getGame().getPhaseStep() == PhaseStep.PLAY_CARDS) {
                    currentPlayerPassed = true;
                    run();
                }
            }
        });

        view.getMainMenuPanel().getBtnExit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        view.getMainMenuPanel().getBtnNewGame().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setGame(new Game());
                view.showGame();
                run();
            }
        });
    }

    public void run(){
        while (this.model.getGame() != null && !this.model.getGame().isGameOver()){
            view.refresh();
            switch (this.model.getGame().getPhase()){
                case SETUP: {
                    switch (this.model.getGame().getPhaseStep()){
                        case START_PHASE: {
                            // Assign roles and characters
                            for (int i = 0; i < 4; ++i) {
                                model.getGame().getPlayers().add(new Player());
                            }
                            model.getGame().getPlayers().get(0).setComputerControlled(false);
                            assignPlayerRolesCharacters();

                            // Deal cards
                            model.getGame().getDeck().shuffle();
                            for (Player player: model.getGame().getPlayers()){
                                for (int i = 0; i < player.getHitpoints(); ++i){
                                    Card card = model.getGame().getDeck().draw();
                                    player.getCards().add(card);
                                }
                            }
                            model.getGame().setPhaseStep(PhaseStep.END_PHASE);
                            break;
                        }
                        case END_PHASE: {
                            model.getGame().setPhase(Phase.PLAY);
                            break;
                        }
                    }
                    break;
                }
                case PLAY: {
                    switch (this.model.getGame().getPhaseStep()) {
                        case START_PHASE: {
                            ViewUtil.popupNotify(model.getGame().getCurrentPlayer().getCharacter() + "'s turn");
                            currentPlayerPassed = false;
                            model.getGame().setPhaseStep(PhaseStep.PLAY_DRAW_2_CARDS);
                            break;
                        }
                        case PLAY_DYNAMITE:{
                            Optional<Card> dynamite = model.getGame().getCurrentPlayer().getCardsInPlay().stream().filter(card -> card.getEffect() == CardEffect.DYNAMITE).findFirst();
                            if (dynamite.isPresent()){
                                handleDynamite(model.getGame().getCurrentPlayer());
                            }
                            if (model.getGame().getCurrentPlayer().isSkipTurn()){
                                model.getGame().getCurrentPlayer().setSkipTurn(false);
                                model.getGame().setPhaseStep(PhaseStep.END_PHASE);
                            }
                            else
                                model.getGame().setPhaseStep(PhaseStep.PLAY_DRAW_2_CARDS);
                            break;
                        }
                        case PLAY_DRAW_2_CARDS:{
                            Card card = model.getGame().getDeck().draw();
                            model.getGame().getCurrentPlayer().getCards().add(card);

                            card = model.getGame().getDeck().draw();
                            model.getGame().getCurrentPlayer().getCards().add(card);

                            model.getGame().setPhaseStep(PhaseStep.PLAY_CARDS);
                            break;
                        }
                        case PLAY_CARDS:{
                            // TODO Only 1 BANG! card, unless they have Volcanic weapon or Willy the Kid, then any number of BANG cards
                            if (model.getGame().getCurrentPlayer().isComputerControlled()){
                                if (!computerPlayCard()){
                                    model.getGame().setPhaseStep(PhaseStep.PLAY_DISCARD);
                                }
                            }
                            else {
                                if (currentPlayerPassed)
                                    model.getGame().setPhaseStep(PhaseStep.PLAY_DISCARD);
                                else
                                    return;
                            }
                            break;
                        }
                        case PLAY_DISCARD:{
                            int handLimit = model.getGame().getCurrentPlayer().getHitpoints();
                            if (model.getGame().getCurrentPlayer().getCards().size() > handLimit){
                                if (model.getGame().getCurrentPlayer().isComputerControlled()){
                                    computerDiscard();
                                }
                                else
                                    return;
                            }
                            model.getGame().setPhaseStep(PhaseStep.END_PHASE);
                            break;
                        }
                        case END_PHASE: {
                            model.getGame().setNextPlayer();
                            model.getGame().setPhaseStep(PhaseStep.START_PHASE);
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }

    private void assignPlayerRolesCharacters(){
        List<Role> roles = new ArrayList<>();

        roles.add(Role.SHERIFF);
        roles.add(Role.RENEGADE);
        switch (model.getGame().getPlayers().size()){
            case 4: {
                roles.add(Role.OUTLAW);
                roles.add(Role.OUTLAW);
                break;
            }
            case 5: {
                roles.add(Role.OUTLAW);
                roles.add(Role.OUTLAW);
                roles.add(Role.DEPUTY);
                break;
            }
            case 6: {
                roles.add(Role.OUTLAW);
                roles.add(Role.OUTLAW);
                roles.add(Role.OUTLAW);
                roles.add(Role.DEPUTY);
                break;
            }
            case 7: {
                roles.add(Role.OUTLAW);
                roles.add(Role.OUTLAW);
                roles.add(Role.OUTLAW);
                roles.add(Role.DEPUTY);
                roles.add(Role.DEPUTY);
                break;
            }
        }

        Collections.shuffle(roles);
        Set<Character> usedCharacters = new HashSet<>();
        for (Player player: model.getGame().getPlayers()){
            player.setRole(roles.remove(0));

            int index = Util.getRandInt(0, Character.values().length);
            Character character = Character.values()[index];
            while (usedCharacters.contains(character)){
                index = Util.getRandInt(0, Character.values().length);
                character = Character.values()[index];
            }
            player.setCharacter(character);
            usedCharacters.add(character);
        }
    }

    private void playerPlayCard(Card card){
        // Remove from player's hand
        model.getGame().getHumanPlayer().getCards().remove(card);

        // Handle card effect
        handleCardType(card, model.getGame().getHumanPlayer());

        // Discard card
        if (card.isDiscardOnPlay()) {
            model.getGame().getDeck().discard(card);
        }
        // Or play on table
        else {
            model.getGame().getHumanPlayer().getCardsInPlay().add(card);
        }
    }

    private void handleCardType(Card card, Player cardOwner){
        switch (card.getEffect()){
            case BEER_6:
            case BEER_7:
            case BEER_8:
            case BEER_9:
            case BEER_T:
            case BEER_J:
                // Gain 1 health
                cardOwner.adjHitpoints(1);
                break;
            case PANIC_8D:
            case PANIC_AH:
            case PANIC_JH:
            case PANIC_QH:
                // Panic!  Draw a card from a person 1 seat away
                break;
            case BARREL_K:
            case BARREL_Q:{
                if (pokerDraw(card)) {
                    // TODO Miss!
                }
                break;
            }
            case GATLING:{
                // All other players discard BANG or lose 1 life
                for (Player player: model.getGame().getPlayers()){
                    if (player == cardOwner)
                        continue;
                    if (player.isComputerControlled()){
                        if (!computerDiscardBang(player)){
                            player.adjHitpoints(-1);
                        }
                    }
                    else {
                        humanDiscardBangOrLose1Life(player);
                    }
                }
                break;
            }
            case GENERAL_STORE_9C:
            case GENERAL_STORE_QS:{
                // Reveal as many cards as players and let each player draw 1
                handleGeneralStore(cardOwner);
                break;
            }
            case CAT_BALOU_JD:
            case CAT_BALOU_9D:
            case CAT_BALOU_TD:
            case CAT_BALOU_KD:{
                handleCatBalou(cardOwner);
                break;
            }
            case SALOON:{
                // All players regain a life
                for (Player player: model.getGame().getPlayers()){
                    if (player.getHitpoints() > 0)
                        player.adjHitpoints(1);
                }
                break;
            }
            case JAIL_4H:
            case JAIL_TH:
            case JAIL_JH:{
                if (pokerDraw(PokerCardSuit.HEARTS, -1)){
                    model.getGame().getDeck().discard(card);
                }
                else {
                    // Skip next turn
                    cardOwner.setSkipTurn(true);
                }
                break;
            }
            case STAGECOACH:{
                cardOwner.getCards().add(model.getGame().getDeck().draw());
                cardOwner.getCards().add(model.getGame().getDeck().draw());
                break;
            }
            case WELLS_FARGO:{
                cardOwner.getCards().add(model.getGame().getDeck().draw());
                cardOwner.getCards().add(model.getGame().getDeck().draw());
                cardOwner.getCards().add(model.getGame().getDeck().draw());
                break;
            }
            case DYNAMITE:
            case REMINGTON:
            case MUSTANG_8H:
            case MUSTANG_9H:
                break;
        }
    }

    /**
     * Have the computer take the current player's turn
     * @return true if a card was played, false if the player passes
     */
    private boolean computerPlayCard(){
        // TODO Computer choose card to play
        int index = Util.getRandInt(0, model.getGame().getCurrentPlayer().getCards().size());
        Card card = model.getGame().getCurrentPlayer().getCards().remove(index);

        handleCardType(card, model.getGame().getCurrentPlayer());

        // Discard card
        if (card.isDiscardOnPlay()) {
            model.getGame().getDeck().discard(card);
        }
        // Or play on table
        else {
            model.getGame().getCurrentPlayer().getCardsInPlay().add(card);
        }

        return true;
    }

    private void computerDiscard(){
        while (model.getGame().getCurrentPlayer().getCards().size() > model.getGame().getCurrentPlayer().getHitpoints()){
            Card card = computerChooseCardToDiscard(model.getGame().getCurrentPlayer());
            model.getGame().getCurrentPlayer().getCards().remove(card);
            model.getGame().getDeck().discard(card);
        }
    }


    private Card computerChooseCardToDiscard(Player player){
        if (player.getCards().isEmpty())
            return null;
        // TODO Add smarts to this
        return player.getCards().get(0);
    }

    private boolean computerDiscardBang(Player player){
        List<Card> bangCards = player.getCards().stream().filter(card -> card.getType() == CardType.BANG).collect(Collectors.toList());
        if (bangCards.isEmpty())
            return false;
        Card card = bangCards.get(0);
        player.getCards().remove(card);
        model.getGame().getDeck().discard(card);
        return true;
    }


    private boolean pokerDraw(Card origCard){
        return pokerDraw(origCard.getSuit(), origCard.getValue());
    }

    /**
     * Draw the top card and compare with the given suit/value.  Return true if match, false otherwise.
     *
     * @param suit Suit to match, null if doesn't matter
     * @param value Value to match, -1 if doesn't matter
     * @return
     */
    private boolean pokerDraw(PokerCardSuit suit, int value){
        Card topCard = model.getGame().getDeck().draw();
        model.getGame().getDeck().discard(topCard);
        return (suit == null || suit == topCard.getSuit()) && (value < 0 || value == topCard.getValue());
    }


    private void humanDiscardBangOrLose1Life(Player player){
        List<Card> bangCards = player.getCards().stream().filter(card -> card.getType() == CardType.BANG).collect(Collectors.toList());
        if (bangCards.isEmpty()) {
            player.adjHitpoints(-1);
            return;
        }

        if (ViewUtil.popupConfirm("Gatling", "Discard BANG card? (OK to discard, Cancel to lose 1 life)")){
            Card card = bangCards.get(0);
            player.getCards().remove(card);
            model.getGame().getDeck().discard(card);
        }
        else {
            player.adjHitpoints(-1);
        }
    }


    /**
     * Reveal as many cards as players and let each player take 1 (in turn order)
     */
    private void handleGeneralStore(Player player){
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < model.getGame().getPlayers().size(); ++i){
            cards.add(model.getGame().getDeck().draw());
        }

        for (Player player1: model.getGame().inPlayerOrder(player)){
            if (player1.isComputerControlled()){
                // Choose 1 card
                Card chosen = cards.remove(0);
                player1.getCards().add(chosen);
            }
            else {
                Card chosen = cards.get(0);
                if (cards.size() > 1)
                    chosen = (Card) ViewUtil.popupDropdown("General Store", "Chose a card to take", cards.toArray(new Card[0]));
                cards.remove(chosen);
                player1.getCards().add(chosen);
            }
        }
    }

    private void handleDynamite(Player cardOwner){
        Card topCard = model.getGame().getDeck().draw();
        model.getGame().getDeck().discard(topCard);
        if (topCard.getValue() <= 9 && topCard.getSuit() == PokerCardSuit.SPADES){
            ViewUtil.popupNotify("Dymanite EXPLODES!  -3 life!");
            model.getGame().getCurrentPlayer().adjHitpoints(-3);
        }
        else {
            Card dynamite = cardOwner.getCardsInPlay().stream().filter(card -> card.getEffect() == CardEffect.DYNAMITE).findFirst().get();
            cardOwner.getCardsInPlay().remove(dynamite);

            int index = model.getGame().getPlayers().indexOf(cardOwner);
            index = (index + 1) % model.getGame().getPlayers().size();
            model.getGame().getPlayers().get(index).getCardsInPlay().add(dynamite);

        }
    }


    private void handleCatBalou(Player cardOwner){
        if (cardOwner.isComputerControlled()){
            int index = Util.getRandInt(0, model.getGame().getPlayers().size());
            while (model.getGame().getPlayers().get(index) == cardOwner){
                index = Util.getRandInt(0, model.getGame().getPlayers().size());
            }
            Player targetPlayer = model.getGame().getPlayers().get(index);
            if (targetPlayer.isComputerControlled()){
                // Choose card to discard
                if (!targetPlayer.getCards().isEmpty()) {
                    Card card = computerChooseCardToDiscard(targetPlayer);
                    targetPlayer.getCards().remove(card);
                }
            }
        }
        else {
            // Let user choose player to discard a card
            List<Player> otherPlayers = model.getGame().getOtherPlayers(cardOwner);
            Player targetPlayer = (Player) ViewUtil.popupDropdown("Cat Balou", "Choose player to discard 1 card", otherPlayers.toArray(new Player[0]));
            // Choose card to discard
            if (!targetPlayer.getCards().isEmpty()) {
                Card card = computerChooseCardToDiscard(targetPlayer);
                targetPlayer.getCards().remove(card);
            }
        }
    }
}
