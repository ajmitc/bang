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

import java.util.*;

public class Controller {
    private Model model;
    private View view;

    private boolean currentPlayerPassed = false;

    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
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

    /**
     * Have the computer take the current player's turn
     * @return true if a card was played, false if the player passes
     */
    private boolean computerPlayCard(){
        return true;
    }

    private void computerDiscard(){

    }
}
