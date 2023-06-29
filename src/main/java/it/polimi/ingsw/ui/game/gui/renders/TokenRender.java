package it.polimi.ingsw.ui.game.gui.renders;

import it.polimi.ingsw.model.game.goal.Token;
import it.polimi.ingsw.ui.game.gui.resources.ResourcePathConstants;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;

/**
 * Class responsible for rendering tokens in the GUI.
 */
public class TokenRender {

    /**
     * @param tokenImageView the ImageView to render the token on
     * @param token          the token that is used to get the corresponding
     *                       url for the Image that is then used for the ImageView
     */
    public static void renderToken(@NotNull ImageView tokenImageView, Token token) {
        Image tokenImage = null;

        if (token != null) {
            String url = ResourcePathConstants.Tokens.mapTokenToImagePath(token);
            tokenImage = new Image(url);
        }

        tokenImageView.setImage(tokenImage);
    }
}
