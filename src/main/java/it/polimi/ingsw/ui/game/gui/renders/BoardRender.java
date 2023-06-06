package it.polimi.ingsw.ui.game.gui.renders;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.config.board.BoardConfiguration;
import it.polimi.ingsw.ui.game.gui.resources.ResourcePathConstants;
import it.polimi.ingsw.utils.javafx.PaneViewUtil;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class BoardRender {
    private static final int dimension = BoardConfiguration.getInstance().getDimension();

    /**
     * Renders the contents of the {@link Board} according to the given matrix
     */
    public static void renderBoard(GridPane gridBoard, Board board) {
        // All nodes can be safely cast to ImageView(s)
        Node[][] gridPaneNodes = PaneViewUtil.matrixify(gridBoard, dimension, dimension);

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                // if either no image is present or board doesn't have any content for (i,j)
                if (gridPaneNodes[i][j] == null) {
                    continue;
                }

                // ImageView containing our bookshelf tile[i][j]
                ImageView imageView = (ImageView) gridPaneNodes[i][j];

                if (board.getTileAt(i, j).isEmpty()) {
                    // no data, null image
                    imageView.setImage(null);
                } else {
                    // get the matching tile
                    Tile tile = board.getTileAt(i, j).get();

                    // we get the tile URL
                    String imageResource = ResourcePathConstants.Tiles.mapTileToImagePath(tile);

                    // and set it as the active image
                    imageView.setImage(new Image(imageResource));
                }
            }
        }
    }
}