package it.polimi.ingsw.ui.game.gui.insertion;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.ui.game.GameViewEventHandler;
import it.polimi.ingsw.ui.game.gui.SceneManager;
import it.polimi.ingsw.ui.game.gui.utils.GuiResources;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;

import java.awt.event.MouseEvent;
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

    private GameViewEventHandler handler;

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
    public void onOrderedTileInsertion(MouseEvent e) {
        if (e.getSource().equals(tile1Selected)) {
            Tile tile = GuiResources.getTileType(tile1Selected.getImage().getUrl());

            orderedTiles.add(tile);
            label1.setText(String.valueOf(orderedTiles.size()));
        } else if (e.getSource().equals(tile2Selected)) {
            Tile tile = GuiResources.getTileType(tile2Selected.getImage().getUrl());

            orderedTiles.add(tile);
            label2.setText(String.valueOf(orderedTiles.size()));
        } else if (e.getSource().equals(tile3Selected)) {
            Tile tile = GuiResources.getTileType(tile3Selected.getImage().getUrl());

            orderedTiles.add(tile);
            label3.setText(String.valueOf(orderedTiles.size()));
        } else {
            throw new IllegalStateException();
        }


    }

    @FXML

    public void onInsertingButtonClick() {
        handler.onViewInsertion(col, orderedTiles);
        SceneManager.changeScene(SceneManager.getActualController(), "index.fxml");
    }
}
