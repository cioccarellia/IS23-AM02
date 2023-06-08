package it.polimi.ingsw.ui.game.gui.renders;

import it.polimi.ingsw.model.cards.personal.PersonalGoalCard;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;
import it.polimi.ingsw.ui.game.gui.resources.ResourcePathConstants;

public class PersonalGoalCardRender {

    public static void renderPersonalGoalCard(@NotNull ImageView personalGoalCardImageView, @NotNull PersonalGoalCard personalGoalCard) {
        String url = ResourcePathConstants.Personals.mapPersonalsToImagePath(personalGoalCard.getId());

        Image personalGoalCardImage = new Image(url);
        personalGoalCardImageView.setImage(personalGoalCardImage);
    }
}

