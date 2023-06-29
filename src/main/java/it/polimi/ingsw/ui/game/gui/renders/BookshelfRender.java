package it.polimi.ingsw.ui.game.gui.renders;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.bookshelf.Bookshelf;
import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;
import it.polimi.ingsw.ui.game.gui.resources.ResourcePathConstants;
import it.polimi.ingsw.utils.javafx.PaneViewUtil;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * Class responsible for rendering the given bookshelf in the GUI.
 */
public class BookshelfRender {

    private static final int rows = BookshelfConfiguration.getInstance().rows();
    private static final int cols = BookshelfConfiguration.getInstance().cols();

    public static void renderBookshelf(GridPane bookshelfGridPane, Bookshelf bookshelf) {
        // All nodes can be safely cast to ImageView(s)
        Node[][] gridPaneNodes = PaneViewUtil.matrixify(bookshelfGridPane, rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // if either no image is present or bookshelf doesn't have any content for (i,j)
                if (gridPaneNodes[i][j] == null) {
                    continue;
                }

                // ImageView containing our bookshelf tile[i][j]
                ImageView imageView = (ImageView) gridPaneNodes[i][j];

                if (bookshelf.getShelfMatrix()[i][j] == null) {
                    // no data, null image
                    imageView.setImage(null);
                } else {
                    // get the matching tile
                    Tile tile = bookshelf.getShelfMatrix()[i][j];

                    // we get the tile URL
                    String imageResource = ResourcePathConstants.Tiles.mapTileToImagePath(tile);

                    // and set it as the active image
                    imageView.setImage(new Image(imageResource));
                }
            }
        }
    }
}
