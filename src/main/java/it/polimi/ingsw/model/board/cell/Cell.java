package it.polimi.ingsw.model.board.cell;

import it.polimi.ingsw.model.board.Tile;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class Cell {

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

    static Cell createDeadCell() {
        return new Cell(CellPattern.NORMAL, false);
    }

    public CellPattern getPattern() {
        return pattern;
    }

    public boolean isDead() {
        return !isAlive;
    }

    public boolean isEmpty() {
        return content == null;
    }

    public Optional<Tile> getContent() {
        return Optional.ofNullable(content);
    }

    public void setContent(Tile content) {
        this.content = content;
    }

    public void clear() {
        content = null;
    }
}
