package it.polimi.ingsw.ui.game.gui.renders;

import it.polimi.ingsw.model.game.goal.Token;
import it.polimi.ingsw.ui.game.gui.resources.ResourcePathConstants;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;

public class TokenRender {
    public static void renderToken(@NotNull ImageView tokenImageView, Token token) {
        Image tokenImage = null;

        if (token != null) {
            String url = ResourcePathConstants.Tokens.mapTokenToImagePath(token);
            tokenImage = new Image(url);
        }

        tokenImageView.setImage(tokenImage);
    }
}
