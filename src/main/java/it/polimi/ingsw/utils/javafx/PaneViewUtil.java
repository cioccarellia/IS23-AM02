package it.polimi.ingsw.utils.javafx;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class PaneViewUtil {

    /**
     * Returns the matrix with the nodes contained in the gridPane
     */
    public static Node[][] matrixify(GridPane gridPane, int rows, int columns) {
        int rowCount = gridPane.getRowCount();
        int colCount = gridPane.getColumnCount();

        if (rowCount != rows || colCount != columns) {
            throw new IllegalStateException("The given rows and columns are wrong");
        }

        Node[][] matrix = new Node[rows][columns];

        for (Node child : gridPane.getChildren()) {
            Integer column = GridPane.getColumnIndex(child);
            Integer row = GridPane.getRowIndex(child);

            if (column != null && row != null) {
                matrix[row][column] = child;
            }
        }

        return matrix;
    }
}
