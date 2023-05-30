package it.polimi.ingsw.ui.game.gui.render;

import it.polimi.ingsw.model.cards.common.CommonGoalCard;
import it.polimi.ingsw.ui.game.gui.utils.GuiResources;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CommonGoalCardsRender {
    /**
     * Generates an ImageView representing a common goal card in the GUI.
     *
     * @param commonGoalCard The common goal card to generate the ImageView for.
     * @return The ImageView representing the common goal card.
     */
    public static ImageView generateCommonGoalCardImageView(CommonGoalCard commonGoalCard) {
        ImageView commonGoalCardImageView = new ImageView();

        Image commonGoalCardImage = new Image(GuiResources.getCommonGC(commonGoalCard.getId()));

        commonGoalCardImageView.setImage(commonGoalCardImage);

        return commonGoalCardImageView;
    }
}
