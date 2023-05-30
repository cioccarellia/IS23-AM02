package it.polimi.ingsw.ui.game.gui.render;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.bookshelf.Bookshelf;
import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;
import it.polimi.ingsw.ui.game.gui.GuiHelper;
import it.polimi.ingsw.ui.game.gui.utils.GuiResources;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class BookshelfRender {
    private final static int rows = BookshelfConfiguration.getInstance().rows();
    private final static int cols = BookshelfConfiguration.getInstance().cols();

    /**
     * Generates an ImageView representing a tile in the GUI.
     *
     * @param tile The tile to generate the ImageView for.
     * @return The ImageView representing the tile.
     */
    public static ImageView generateImageViewForTile(Tile tile) {
        Image tileImage = new Image(GuiResources.getTile(tile));

        ImageView tileImageView = new ImageView();

        tileImageView.setImage(tileImage);

        return tileImageView;
    }
    /**
     * Regenerates the GridPane representing a bookshelf in the GUI based on the provided bookshelf.
     *
     * @param bookshelf The bookshelf to regenerate the GridPane for.
     * @return The regenerated GridPane representing the bookshelf.
     */
    public static void regenerateBookshelfGridPane(Bookshelf bookshelf, GridPane bookshelfPane) {

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Tile tile = bookshelf.getShelfMatrix()[i][j];

                if (tile != null) {
                    bookshelfPane.add(generateImageViewForTile(tile), i, j);
                }
            }
        }

    }
}
