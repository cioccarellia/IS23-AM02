package it.polimi.ingsw.ui.game.gui.renders;

import it.polimi.ingsw.model.cards.common.CommonGoalCard;
import it.polimi.ingsw.ui.game.gui.resources.ResourcePathConstants;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;

/**
 * Class responsible for rendering the common goal cards in the GUI.
 */
public class CommonGoalCardImageViewRender {

    public static void renderCommonGoalCard(@NotNull ImageView commonGoalCardImageView, @NotNull CommonGoalCard commonGoalCard) {
        String url = ResourcePathConstants.Commons.mapCommonsToImagePath(commonGoalCard.getId());

        try {
            Image commonGoalCardImage = new Image(url);
            commonGoalCardImageView.setImage(commonGoalCardImage);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

}
