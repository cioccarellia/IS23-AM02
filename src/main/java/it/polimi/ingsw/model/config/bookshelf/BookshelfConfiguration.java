package it.polimi.ingsw.model.config.bookshelf;

import it.polimi.ingsw.model.config.Configuration;
import it.polimi.ingsw.utils.resources.ResourceReader;

/**
 * Manages configuration parameters for {@link it.polimi.ingsw.model.bookshelf.Bookshelf}, according to
 * the matching specification {@link BookshelfSpecifics}.
 * The parameters are rows and columns.
 */
public class BookshelfConfiguration extends Configuration<BookshelfSpecifics> {

    // used for singleton pattern
    private static BookshelfConfiguration instance;

    // deserializes and stores the game specifics (from json)
    private final BookshelfSpecifics specs = ResourceReader.readAndDeserialize(provideResourcePath(), BookshelfSpecifics.class);

    public static BookshelfConfiguration getInstance() {
        if (instance == null) {
            instance = new BookshelfConfiguration();
        }

        return instance;
    }

    public int rows() {
        return specs.rows();
    }

    public int cols() {
        return specs.cols();
    }

    @Override
    protected BookshelfSpecifics provideSpecs() {
        return specs;
    }

    @Override
    protected String provideResourcePath() {
        return "bookshelf/bookshelf.json";
    }
}
