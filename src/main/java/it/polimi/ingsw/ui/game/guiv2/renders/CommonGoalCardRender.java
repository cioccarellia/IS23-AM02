package it.polimi.ingsw.ui.game.guiv2.renders;

import it.polimi.ingsw.model.cards.common.CommonGoalCard;
import it.polimi.ingsw.ui.game.guiv2.resources.ResourcePathConstants;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;

public class CommonGoalCardRender {

    public static void renderCommonGoalCard(@NotNull ImageView commonGoalCardImageView, @NotNull CommonGoalCard commonGoalCard) {
        String url = ResourcePathConstants.Commons.mapCommonsToImagePath(commonGoalCard.getId());

        Image commonGoalCardImage = new Image(url);
        commonGoalCardImageView.setImage(commonGoalCardImage);
    }

}
