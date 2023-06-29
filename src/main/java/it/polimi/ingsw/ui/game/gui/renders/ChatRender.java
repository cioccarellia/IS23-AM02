package it.polimi.ingsw.ui.game.gui.renders;

import it.polimi.ingsw.model.game.GameMode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for rendering the chat in the GUI.
 */
public class ChatRender {
    public static final String EVERYONE = "Everyone";

    public static void renderChatComboItems(List<String> enemyList, ComboBox<String> chatSelectorComboBox, GameMode gameMode) {
        List<String> comboItems = new ArrayList<>();

        if (gameMode != GameMode.GAME_MODE_2_PLAYERS) {
            comboItems.add(EVERYONE);
        }

        comboItems.addAll(enemyList);

        ObservableList<String> options = FXCollections.observableArrayList(comboItems);
        chatSelectorComboBox.setItems(options);
        chatSelectorComboBox.getSelectionModel().selectFirst();
    }
}
