package it.polimi.ingsw.ui.game.gui.render;

import it.polimi.ingsw.model.game.goal.CommonGoalCardStatus;
import it.polimi.ingsw.model.game.goal.Token;
import it.polimi.ingsw.ui.game.gui.utils.GuiResources;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static it.polimi.ingsw.model.game.goal.Token.FULL_SHELF_TOKEN;

public class EndGameTokenRender {
    public static ImageView generateEndTokenImageView() {
        ImageView tokenImageView = new ImageView();

        Image tokenImage = new Image(GuiResources.getToken(FULL_SHELF_TOKEN));

        tokenImageView.setImage(tokenImage);

        return tokenImageView;
    }
}
