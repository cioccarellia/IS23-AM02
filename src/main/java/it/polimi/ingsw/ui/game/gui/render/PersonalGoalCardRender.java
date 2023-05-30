package it.polimi.ingsw.ui.game.gui.render;

import it.polimi.ingsw.model.cards.personal.PersonalGoalCard;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.ui.game.gui.utils.GuiResources;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PersonalGoalCardRender {

    /**
     * Generates an ImageView representing a personal goal card in the GUI.
     *
     * @param game  The game model.
     * @param owner The owner of the personal goal card.
     * @return The ImageView representing the personal goal card.
     */
    public static ImageView generatePersonalGoalCardImageView(Game game, String owner) {
        ImageView personalGoalCardImageView = new ImageView();

        PersonalGoalCard id = game.getSessions().getByUsername(owner).getPersonalGoalCard();

        Image personalGoalCardImage = new Image(GuiResources.getPersonalGC(id));

        personalGoalCardImageView.setImage(personalGoalCardImage);

        return personalGoalCardImageView;
    }
}
