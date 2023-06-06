package it.polimi.ingsw.ui.commons.gui;

public class AppManager {

    private static GuiApp appInstance;

    public static GuiApp getAppInstance() {
        return appInstance;
    }

    public static void setAppInstance(GuiApp appInstance) {
        AppManager.appInstance = appInstance;
    }
}
