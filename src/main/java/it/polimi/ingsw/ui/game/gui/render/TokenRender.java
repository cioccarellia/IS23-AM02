package it.polimi.ingsw.ui.game.gui.render;

import it.polimi.ingsw.model.game.goal.CommonGoalCardStatus;
import it.polimi.ingsw.model.game.goal.Token;
import it.polimi.ingsw.ui.game.gui.utils.GuiResources;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TokenRender {
    /**
     * Generates an ImageView representing a token in the GUI based on the provided common goal card status.
     *
     * @param commonGoalCard The common goal card status containing the token.
     * @return The ImageView representing the token.
     */
    public static ImageView generateTokenImageView(CommonGoalCardStatus commonGoalCard) {
        ImageView tokenImageView = new ImageView();

        Token id = commonGoalCard.getCardTokens().get(commonGoalCard.getCardTokens().size() - 1);

        Image tokenImage = new Image(GuiResources.getToken(id));

        tokenImageView.setImage(tokenImage);

        return tokenImageView;
    }
}
