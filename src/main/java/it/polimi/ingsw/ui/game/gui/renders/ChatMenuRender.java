package it.polimi.ingsw.ui.game.gui.renders;

import javafx.scene.control.MenuItem;

public class ChatMenuRender {
    public static void renderChatMenuItemSelectionButton(MenuItem item, String name) {
        item.setDisable(false);
        item.setVisible(true);
        item.setText(name);
    }
}
