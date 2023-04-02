package it.polimi.ingsw.groupfinder;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * This class implements a group-finding algorithm to partition a matrix into a list of {@link Group}s.
 * Each group represents a sector of contiguous {@link Tile}s.
 */
public class GroupFinder {

    private final static int rows = BookshelfConfiguration.getInstance().rows();
    private final static int cols = BookshelfConfiguration.getInstance().cols();

    /**
     * Matrix containing the elements to be grouped.
     */
    private final Tile[][] matrix;

    /**
     * Contains the markers for duplication checks.
     */
    private boolean[][] markers = new boolean[rows][cols];

    /**
     * Initializes the class and sets the matrix for the algorithm.
     *
     * @param shelfMatrix the matrix to be used in the group finder algorithm.
     */
    public GroupFinder(@NotNull Tile[][] shelfMatrix) {
        matrix = shelfMatrix;
    }


    /**
     * Returns a {@link Map} of all the groups found having a specific {@link Group#type()}.
     */
    public Map<Tile, List<Group>> computeGroupPartitionMap() {
        List<Group> partitions = computeGroupPartition();
        return partitions.stream().collect(Collectors.groupingBy(Group::type));
    }


    /**
     * Returns the list of groups for the given matrix.
     *
     * @implNote a {@link Set} isn't used because a certain group may appear more than once
     */
    public List<Group> computeGroupPartition() {
        markers = new boolean[rows][cols];
        List<Group> results = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boolean marker = markers[i][j];
                Tile value = matrix[i][j];

                if (marker || value == null) {
                    continue;
                }

                int count = pathfind(i, j, value);
                Group group = new Group(value, count);

                results.add(group);
            }
        }

        return results;
    }

    private int pathfind(int i, int j, Tile value) {
        if (i < 0 || i >= rows || j < 0 || j >= cols)
            return 0;

        boolean marker = markers[i][j];
        Tile element = matrix[i][j];

        if (marker || element == null || element != value) {
            return 0;
        } else {
            markers[i][j] = true;

            int north = pathfind(i + 1, j, value);
            int east = pathfind(i, j + 1, value);
            int south = pathfind(i - 1, j, value);
            int west = pathfind(i, j - 1, value);

            return 1 + north + east + south + west;
        }
    }
}
