package it.polimi.ingsw.ui.game.gui.render;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.config.board.BoardConfiguration;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.ui.game.gui.utils.GuiResources;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class BoardRender {
    private static final int dimension = BoardConfiguration.getInstance().getDimension();

    public static Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                return node;
            }
        }

        return null;
    }

    public static ImageView generateImageViewForTile(Tile tile) {
        Image tileImage = new Image(GuiResources.getTile(tile));

        ImageView tileImageView = new ImageView();

        tileImageView.setImage(tileImage);

        return tileImageView;
    }

    public static void renderBoard(Game model, GridPane boardPane) {
        boardPane.getChildren();

        for (int i = 0; i < dimension; i++ ) {
            for (int j = 0; j < dimension; j++ ) {
                try {
                    ImageView n = (ImageView) getNodeByRowColumnIndex(i, j, boardPane);

                    var matrix = model.getBoard().getTileMatrix();
                    Image gridNodeImage = generateImageViewForTile(matrix[i][j]).getImage();

                    if (n != null) {
                        n.setImage(gridNodeImage);
                    }

                } catch (NullPointerException ignored) {

                }
            }
        }

    }

}
