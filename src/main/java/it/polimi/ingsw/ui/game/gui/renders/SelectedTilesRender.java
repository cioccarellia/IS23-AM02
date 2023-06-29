package it.polimi.ingsw.ui.game.gui.renders;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.config.logic.LogicConfiguration;
import it.polimi.ingsw.model.game.CellInfo;
import it.polimi.ingsw.ui.game.gui.resources.ResourcePathConstants;
import it.polimi.ingsw.utils.javafx.UiUtils;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

import static it.polimi.ingsw.ui.game.gui.utils.GuiGameControllerUtils.enableDarkeningEffect;

/**
 * Class responsible for rendering the selected tile(s) in the GUI.
 */
public class SelectedTilesRender {

    private static final int maxSelectionSize = LogicConfiguration.getInstance().maxSelectionSize();

    /**
     * Renders the selected tiles on the specified list of ImageViews, when more than one is selected
     */
    public static void renderSelectedTiles(List<ImageView> selectedTilesImageViewsList, List<CellInfo> coordinatesAndValues) {
        List<Tile> selectedTiles = null;

        if (!coordinatesAndValues.isEmpty()) {
            selectedTiles = coordinatesAndValues.stream().map(CellInfo::tile).toList();
        }

        for (int i = 0; i < maxSelectionSize; i++) {
            // if either no image is present or board doesn't have any content for (i,j)
            if (selectedTilesImageViewsList.get(i) == null) {
                continue;
            }

            // ImageView containing our selected tile[0][i]
            ImageView imageView = selectedTilesImageViewsList.get(i);

            Image tileImage = null;

            if (selectedTiles != null && i < selectedTiles.size()) {
                String url = ResourcePathConstants.Tiles.mapTileToImagePath(selectedTiles.get(i));
                tileImage = new Image(url);
            }

            imageView.setImage(tileImage);

            UiUtils.visible(imageView);
            enableDarkeningEffect(imageView);
        }
    }

    /**
     * Renders the selected tiles on the specified list of ImageViews, when only one is selected
     */
    public static void renderSelectedTilesWhenOnlyOne(ImageView firstSelectedTile, Label firstSelectedTileLabel, List<CellInfo> coordinatesAndValues, List<Tile> playerOrderedTilesToBeInserted) {
        List<Tile> selectedTiles = null;

        if (!coordinatesAndValues.isEmpty()) {
            selectedTiles = coordinatesAndValues.stream().map(CellInfo::tile).toList();
        }
        if (coordinatesAndValues.size() == 1) {

            String url = ResourcePathConstants.Tiles.mapTileToImagePath(selectedTiles.get(0));
            Image tileImage = new Image(url);

            firstSelectedTile.setImage(new Image(url));

            UiUtils.visible(firstSelectedTile);
            firstSelectedTile.setEffect(null);

            playerOrderedTilesToBeInserted.add(selectedTiles.get(0));

            firstSelectedTileLabel.setText("1");

        }
    }
}
