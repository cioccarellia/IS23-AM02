package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.board.Tile;

import java.util.*;

/**
 *
 */
public class TileExtractor {

    private static final int MAX_TILE_AMOUNT = 22;

    /**
     * Map used to store the state information about how many likewise items have already
     * been extracted.
     */
    private final Map<Tile, Integer> status = new HashMap<>();

    public TileExtractor() {
        for (Tile tile : Tile.values()) {
            status.put(tile, MAX_TILE_AMOUNT);
        }
    }

    /**
     * Extracts a single tile, updating its associated status map entry.
     */
    private Tile extractTile() {
        Random generator = new Random();

        Tile extractedTile = Tile.values()[generator.nextInt(6)];
        int leftoverTiles = status.get(extractedTile);

        if (leftoverTiles > 0) {
            status.put(extractedTile, leftoverTiles - 1);
            return extractedTile;
        } else {
            return extractTile();
        }
    }


    /**
     * Extracts a number of {@link it.polimi.ingsw.model.board.Tile}s, and marks them
     * in the internal state.
     * This runs under the assumption that the number of extractable tiles is greater than the number
     * of tiles that will ever be requested to the extractor.
     *
     * @param amount number of elements to be extracted
     * @return a list of randomly-extracted and coherent tiles
     */
    public List<Tile> generateRandomTiles(int amount) {
        List<Tile> extraction = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            extraction.add(extractTile());
        }

        return extraction;
    }

}