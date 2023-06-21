package it.polimi.ingsw.ui.game.gui.renders;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.List;


public class ChatRender {
    public static final String EVERYONE = "Everyone";

    public static void renderChatComboItems(List<String> enemyList, ComboBox<String> chatSelectorComboBox) {
        List<String> comboItems = new ArrayList<>();
        comboItems.add(EVERYONE);

        comboItems.addAll(enemyList);

        ObservableList<String> options = FXCollections.observableArrayList(comboItems);
        chatSelectorComboBox.setItems(options);
        chatSelectorComboBox.getSelectionModel().selectFirst();
    }
}
