package it.polimi.ingsw.utils.javafx;

import javafx.scene.Node;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class UiUtils {

    public static void setVisible(boolean isVisible, Node... role) {
        Arrays.stream(role).forEach(it -> it.setVisible(isVisible));
    }


    public static void visible(@NotNull Node @NotNull ... nodeList) {
        for (Node node : nodeList) {
            node.setVisible(true);
        }
    }

    public static void invisible(@NotNull Node @NotNull ... nodeList) {
        for (Node node : nodeList) {
            node.setVisible(false);
        }
    }


    public static void visible(@NotNull List<Node> nodeList) {
        for (Node node : nodeList) {
            node.setVisible(true);
        }
    }

    public static void invisible(@NotNull List<Node> nodeList) {
        for (Node node : nodeList) {
            node.setVisible(false);
        }
    }


    public static void enable(@NotNull Node @NotNull ... nodeList) {
        for (Node node : nodeList) {
            node.setDisable(false);
        }
    }

    public static void disable(@NotNull Node @NotNull ... nodeList) {
        for (Node node : nodeList) {
            node.setDisable(true);
        }
    }


    public static void enable(@NotNull List<Node> nodeList) {
        for (Node node : nodeList) {
            node.setDisable(false);
        }
    }

    public static void disable(@NotNull List<Node> nodeList) {
        for (Node node : nodeList) {
            node.setDisable(true);
        }
    }


    public static void manage(@NotNull Node @NotNull ... nodeList) {
        for (Node node : nodeList) {
            node.setManaged(true);
        }
    }

    public static void unmanage(@NotNull Node @NotNull ... nodeList) {
        for (Node node : nodeList) {
            node.setManaged(false);
        }
    }


    public static void manage(@NotNull List<Node> nodeList) {
        for (Node node : nodeList) {
            node.setManaged(true);
        }
    }

    public static void unmanage(@NotNull List<Node> nodeList) {
        for (Node node : nodeList) {
            node.setManaged(false);
        }
    }


    public static void visibleAndManage(@NotNull Node @NotNull ... nodeList) {
        for (Node node : nodeList) {
            node.setManaged(true);
            node.setVisible(true);
        }
    }

    public static void invisibleAndUnmanage(@NotNull Node @NotNull ... nodeList) {
        for (Node node : nodeList) {
            node.setManaged(false);
            node.setVisible(false);
        }
    }

    public static void visibleAndManage(@NotNull List<Node> nodeList) {
        for (Node node : nodeList) {
            node.setManaged(true);
            node.setVisible(true);
        }
    }

    public static void invisibleAndUnmanage(@NotNull List<Node> nodeList) {
        for (Node node : nodeList) {
            node.setManaged(false);
            node.setVisible(false);
        }
    }
}
