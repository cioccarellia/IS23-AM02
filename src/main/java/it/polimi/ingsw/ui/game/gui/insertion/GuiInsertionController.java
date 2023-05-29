package it.polimi.ingsw.ui.game.gui.insertion;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.ui.game.gui.utils.GuiResources;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The controller class for the GUI tile insertion screen.
 */
public class GuiInsertionController {
    private static int col = 0;
    private final List<Tile> orderedTiles = new ArrayList<>();
    @FXML
    public ImageView tile1Selected;
    @FXML
    public ImageView tile2Selected;
    @FXML
    public ImageView tile3Selected;
    @FXML
    public ToggleGroup column;
    @FXML
    public RadioButton column1;
    @FXML
    public RadioButton column2;
    @FXML
    public RadioButton column3;
    @FXML
    public RadioButton column4;
    @FXML
    public RadioButton column5;
    @FXML
    public Label label1;
    @FXML
    public Label label2;
    @FXML
    public Label label3;

    private List<ImageView> selectedTileList() {

        return Arrays.asList(tile1Selected, tile2Selected, tile3Selected);

    }

    private List<RadioButton> columnButtons() {

        return Arrays.asList(column1, column2, column3, column4, column5);

    }

    private List<Label> labels() {

        return Arrays.asList(label1, label2, label3);

    }

    /**
     * Event handler for radio button click.
     */
    public void onRadioButtonClick() {
        List<RadioButton> columnButtons = Arrays.asList(column1, column2, column3, column4, column5);

        for (int i = 0; i < columnButtons.size(); i++) {
            RadioButton columnButton = columnButtons.get(i);
            int value = i;
            columnButton.setOnMouseClicked(mouseEvent -> {
                column.selectToggle(columnButton);
                col = value;
            });
        }

    }

    /**
     * Event handler for ordered tile insertion.
     */
    public void onOrderedTileInsertion() {
        /* se dovesse funzionare con il for loop
        List<ImageView> selectedTiles = Arrays.asList(tile1Selected, tile2Selected, tile3Selected);
        List<Label> labels = Arrays.asList(label1, label2, label3);
        for (int i = 0; i < selectedTiles.size(); i++) {
            ImageView selTile = selectedTiles.get(i);
            Label label = labels.get(i);
            int value = i;

            selTile.setOnMouseClicked(mouseEvent -> {
                if (orderedTiles.size() == 0) {
                    orderedTiles.add(GuiResources.getTileType(selTile.getImage()));
                    label.setText(String.valueOf(value + 1));
                } else if (orderedTiles.size() == 1) {
                    orderedTiles.add(GuiResources.getTileType(selTile.getImage()));
                    label.setText(String.valueOf(value + 2));
                } else if (orderedTiles.size() == 2) {
                    orderedTiles.add(GuiResources.getTileType(selTile.getImage()));
                    label.setText(String.valueOf(value + 3));
                }
            });
        }
        */


        if (orderedTiles.size() == 0) {
            orderedTiles.add(GuiResources.getTileType(tile1Selected.getImage()));
            label1.setText(String.valueOf(1));
        } else if (orderedTiles.size() == 1) {
            orderedTiles.add(GuiResources.getTileType(tile1Selected.getImage()));
            label1.setText(String.valueOf(2));
        } else if (orderedTiles.size() == 2) {
            orderedTiles.add(GuiResources.getTileType(tile1Selected.getImage()));
            label1.setText(String.valueOf(3));
        }


    }
}
