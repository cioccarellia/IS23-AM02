package it.polimi.ingsw.ui.game.gui.renders;

import it.polimi.ingsw.model.cards.personal.PersonalGoalCard;
import it.polimi.ingsw.ui.game.gui.resources.ResourcePathConstants;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;

/**
 * Class responsible for rendering the personal goal cards in the GUI.
 */
public class PersonalGoalCardRender {

    /**
     * Renders a personal goal card on the specified ImageView.
     */
    public static void renderPersonalGoalCard(@NotNull ImageView personalGoalCardImageView, @NotNull PersonalGoalCard personalGoalCard) {
        String url = ResourcePathConstants.Personals.mapPersonalsToImagePath(personalGoalCard.getId());

        Image personalGoalCardImage = new Image(url);
        personalGoalCardImageView.setImage(personalGoalCardImage);
    }
}

