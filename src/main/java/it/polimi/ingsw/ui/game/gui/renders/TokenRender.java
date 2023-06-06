package it.polimi.ingsw.ui.game.gui.renders;

import it.polimi.ingsw.model.game.goal.Token;
import it.polimi.ingsw.ui.game.gui.resources.ResourcePathConstants;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;

public class TokenRender {
    public static void renderToken(@NotNull ImageView topTokenImageView, Token token) {
        Image topTokenImage = null;

        if (token != null) {
            String url = ResourcePathConstants.Tokens.mapTokenToImagePath(token);
            topTokenImage = new Image(url);
        }

        topTokenImageView.setImage(topTokenImage);
    }
}
