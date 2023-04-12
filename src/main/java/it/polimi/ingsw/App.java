package it.polimi.ingsw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Program entry point. Depending on what is requested, either the client,
 * the server, or a configuration of them may be started.
 */
public class App {

    protected static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("Starting app");
    }
}
