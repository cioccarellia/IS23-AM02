package it.polimi.ingsw.model.board.cell;

import it.polimi.ingsw.model.board.Tile;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Optional;

import static it.polimi.ingsw.model.board.cell.CellPattern.NORMAL;

public class Cell implements Serializable {

    private final CellPattern pattern;
    private final boolean isAlive;
    private Tile content;

    /**
     * Builds an empty cell
     *
     * @param pattern Defines the cell pattern
     * @param isAlive Defines if the cell is dead or alive
     */
    public Cell(CellPattern pattern, boolean isAlive) {
        this.pattern = pattern;
        this.isAlive = isAlive;
    }

    /**
     * Builds a cell containing a given tile
     *
     * @param pattern Defines the cell pattern
     * @param content Defines the cell content
     */
    public Cell(CellPattern pattern, @Nullable Tile content) {
        this.pattern = pattern;
        this.isAlive = true;

        this.content = content;
    }

    /**
     * @return the Cell contractor, setting isAlive as false
     */
    static Cell createDeadCell() {
        return new Cell(NORMAL, false);
    }

    /**
     * @return the pattern of the cell
     */
    public CellPattern getPattern() {
        return pattern;
    }

    /**
     * @return the cell attribute isAlive as false
     */
    public boolean isDead() {
        return !isAlive;
    }

    /**
     * @return the cell attribute content as null
     */
    public boolean isEmpty() {
        return content == null;
    }

    /**
     * @return the content of the cell, it can be empty (null)
     */
    public Optional<Tile> getContent() {
        return Optional.ofNullable(content);
    }

    /**
     * It sets the content of a cell, with the given content
     *
     * @param content what we need to insert in the cell
     */
    public void setContent(Tile content) {
        this.content = content;
    }

    /**
     * Empties the cell
     */
    public void clear() {
        content = null;
    }
}
