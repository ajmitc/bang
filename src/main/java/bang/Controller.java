package bang;

import bang.game.Phase;
import bang.game.PhaseStep;
import bang.game.card.Card;
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
                        case PLAY_DRAW_2_CARDS:{
                            Card card = model.getGame().getDeck().draw();
                            model.getGame().getCurrentPlayer().getCards().add(card);

                            card = model.getGame().getDeck().draw();
                            model.getGame().getCurrentPlayer().getCards().add(card);

                            model.getGame().setPhaseStep(PhaseStep.PLAY_CARDS);
                            break;
                        }
                        case PLAY_CARDS:{
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
            case BARREL_K:
            case BARREL_Q:{
                if (draw(card)) {
                    // TODO Miss!
                }
                break;
            }
            case REMINGTON:{
                break;
            }
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
            Card card = model.getGame().getCurrentPlayer().getCards().remove(0);
            model.getGame().getDeck().discard(card);
        }
    }


    private boolean draw(Card origCard){
        Card topCard = model.getGame().getDeck().draw();
        model.getGame().getDeck().discard(topCard);
        return origCard.getSuit() == topCard.getSuit() && origCard.getValue() == topCard.getValue();
    }
}
