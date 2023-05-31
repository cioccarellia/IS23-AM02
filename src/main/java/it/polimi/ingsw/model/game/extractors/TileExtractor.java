package it.polimi.ingsw.model.game.extractors;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.config.logic.LogicConfiguration;

import java.io.Serializable;
import java.util.*;

/**
 *
 */
public class TileExtractor extends ElementExtractor<Tile> implements Serializable {

    private static final int maxTilesPerType = LogicConfiguration.getInstance().maxTilesPerType();
    private static final int tileTypesAmount = LogicConfiguration.getInstance().tileTypesAmount();


    /**
     * Map used to store the state information about how many likewise items have already
     * been extracted.
     */
    private final Map<Tile, Integer> status = new HashMap<>();

    public TileExtractor() {
        for (Tile tile : Tile.values()) {
            status.put(tile, maxTilesPerType);
        }
    }

    /**
     * Returns the number of tiles that can be extracted from the sacchetto
     */
    public int leftoverCapacity() {
        return status.values().stream().mapToInt(i -> i).sum();
    }

    /**
     * Extracts a single tile, updating its associated status map entry.
     */
    @Override
    public Tile extract() {
        Random generator = new Random();

        Tile extractedTile = Tile.values()[generator.nextInt(tileTypesAmount)];
        int leftoverTiles = status.get(extractedTile);

        if (leftoverTiles > 0) {
            status.put(extractedTile, leftoverTiles - 1);
            return extractedTile;
        } else {
            return extract();
        }
    }

    /**
     * Puts back in the extractor (bag) the removed tiles
     *
     * @param removedTiles the tiles that have been removed from the board before refilling
     */
    public void putBackTiles(Map<Tile, Integer> removedTiles) {
        List<Tile> tileType = removedTiles.keySet().stream().toList();

        for (Tile tile : tileType) {
            int tileAmount = status.get(tile) + removedTiles.get(tile);
            status.put(tile, tileAmount);
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
    @Override
    public List<Tile> extractAmount(int amount) {
        List<Tile> extraction = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            extraction.add(extract());
        }

        return extraction;
    }

}