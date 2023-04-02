package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.connection.ClientConnection;
import it.polimi.ingsw.controller.connection.ConnectionStatus;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.model.player.action.PlayerCurrentGamePhase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GameController {

    /**
     * Maps a username to the details of its connection
     */
    private final Map<String, ClientConnection> connections = new HashMap<>();

    /**
     *
     */
    private final Game game;

    private final int maxPlayerAmount;

    GameController(GameMode mode) {
        game = new Game(mode);
        maxPlayerAmount = mode.playerCount();
    }


    // Connection
    public void onPlayerSignUpRequest(String username) {
        if (connections.size() >= maxPlayerAmount) {
            return; // MAX_PLAYER_REACHED
        }

        if (connections.containsKey(username)) {
            return; // ALREADY_CONNECTED_PLAYER
        }

        connections.put(username, new ClientConnection(username));
        game.addPlayer(username);
    }

    public boolean shouldStandbyGame() {
        return connections
                .values()
                .stream()
                .filter(player -> player.getStatus() == ConnectionStatus.DISCONNECTED)
                .count() >= maxPlayerAmount - 1;
    }

    public void onPlayerDisconnection(String username) {
        connections.get(username).setStatus(ConnectionStatus.DISCONNECTED);

        if (shouldStandbyGame()) {
            game.onStandby();
        } else {
            game.onRunning();
        }
    }

    public void onPlayerConnection(String username) {
        connections.get(username).setStatus(ConnectionStatus.OPEN);

        if (shouldStandbyGame()) {
            game.onStandby();
        } else {
            game.onRunning();
        }
    }


    // Game logic
    public boolean isUsernameActivePlayer(String username) {
        return username.equals(game.getCurrentPlayer().getUsername());
    }

    public void onPlayerTileSelectionRequest(String username, Set<Coordinate> selection) {
        if (!isUsernameActivePlayer(username)) {
            return;
        }

        if (game.getCurrentPlayer().getPlayerCurrentGamePhase() != PlayerCurrentGamePhase.SELECTING) {
            return;
        }

        if (!game.isSelectionValid(selection)) {
            return;
        }

        game.onPlayerSelectionPhase(selection);
    }


    public void onPlayerBookshelfTileInsertionRequest(String username, int column, List<Tile> tiles) {
        if (!isUsernameActivePlayer(username)) {
            return;
        }

        if (game.getCurrentPlayer().getPlayerCurrentGamePhase() != PlayerCurrentGamePhase.INSERTING) {
            return;
        }

        if (!game.getCurrentPlayer().getPlayerTileSelection().selectionEquals(tiles)) {
            return; // WRONG_SELECTION
        }

        if (column < 0 || column >= BookshelfConfiguration.getInstance().cols()) {
            return; // ILLEGAL_COLUMN
        }

        if (tiles.size() > 3) {
            return; // TOO_MANY_TILES
        }

        if (!game.getCurrentPlayer().getBookshelf().canFit(column, tiles.size())) {
            return; // NO_FIT
        }

        game.onPlayerInsertionPhase(column, tiles);

        onPlayerCheckingRequest(username);
    }

    public void onPlayerCheckingRequest(String username) {
        if (!isUsernameActivePlayer(username)) {
            return;
        }

        game.onPlayerCheckingPhase();
    }

    public void onTurnChangeRequest(String username) {
        // todo checking logic, determining next player / standby game

        game.onNextTurn(username);
    }

}
