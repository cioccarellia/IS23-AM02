package it.polimi.ingsw.ui.game.gui.renders;

import javafx.scene.control.MenuItem;

public class ChatRender {
    public static void renderChatMenuItemOnSelection(MenuItem item, String name){
        item.setDisable(false);
        item.setVisible(true);
        item.setText(name);
    }
}
