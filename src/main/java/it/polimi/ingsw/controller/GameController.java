package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.connection.ClientConnection;
import it.polimi.ingsw.controller.connection.ConnectionStatus;
import it.polimi.ingsw.controller.result.ControllerRequest;
import it.polimi.ingsw.controller.result.SingleResult;
import it.polimi.ingsw.controller.result.TypedResult;
import it.polimi.ingsw.controller.result.failures.*;
import it.polimi.ingsw.controller.result.types.StatusSuccess;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.model.game.GameStatus;
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


    public void startCurrentTurn() {

    }


    // Connection
    public SingleResult<SignUpRequest> onPlayerSignUpRequest(String username) {
        if (connections.size() >= maxPlayerAmount) {
            return new SingleResult.Failure<>(SignUpRequest.MAX_PLAYER_REACHED);
        }

        if (connections.containsKey(username)) {
            return new SingleResult.Failure<>(SignUpRequest.USERNAME_ALREADY_IN_USE);
        }

        if (game.getGameStatus() == GameStatus.ENDED) {
            return new SingleResult.Failure<>(SignUpRequest.GAME_ALREADY_ENDED);
        }

        connections.put(username, new ClientConnection(username));
        game.addPlayer(username);

        return new SingleResult.Success<>();
    }

    public void onPlayerDisconnection(String username) {
        connections.get(username).setStatus(ConnectionStatus.DISCONNECTED);
    }

    public SingleResult<StatusError> onPlayerConnection(String username) {
        if (game.getGameStatus() == GameStatus.ENDED) {
            return new SingleResult.Failure<>(StatusError.GAME_ALREADY_ENDED);
        }

        connections.get(username).setStatus(ConnectionStatus.OPEN);

        return new SingleResult.Success<>();
    }


    // Game logic
    public boolean isUsernameActivePlayer(String username) {
        return username.equals(game.getCurrentPlayer().getUsername());
    }

    public boolean shouldStandbyGame() {
        return connections
                .values()
                .stream()
                .filter(player -> player.getStatus() == ConnectionStatus.DISCONNECTED)
                .count() >= maxPlayerAmount - 1;
    }


    public SingleResult<TileSelectionFailures> onPlayerTileSelectionRequest(String username, Set<Coordinate> selection) {
        if (!isUsernameActivePlayer(username)) {
            return new SingleResult.Failure<>(TileSelectionFailures.UNAUTHORIZED_PLAYER);
        }

        if (game.getCurrentPlayer().getPlayerCurrentGamePhase() != PlayerCurrentGamePhase.SELECTING) {
            return new SingleResult.Failure<>(TileSelectionFailures.UNAUTHORIZED_ACTION);
        }

        if (!game.isSelectionValid(selection)) {
            return new SingleResult.Failure<>(TileSelectionFailures.UNAUTHORIZED_SELECTION);
        }

        game.onPlayerSelectionPhase(selection);
        return new SingleResult.Success<>();
    }


    public SingleResult<BookshelfInsertionFailure> onPlayerBookshelfTileInsertionRequest(String username, int column, List<Tile> tiles) {
        if (!isUsernameActivePlayer(username)) {
            return new SingleResult.Failure<>(BookshelfInsertionFailure.WRONG_PLAYER);
        }

        if (game.getCurrentPlayer().getPlayerCurrentGamePhase() != PlayerCurrentGamePhase.INSERTING) {
            return new SingleResult.Failure<>(BookshelfInsertionFailure.WRONG_STATUS);
        }

        if (!game.getCurrentPlayer().getPlayerTileSelection().selectionEquals(tiles)) {
            return new SingleResult.Failure<>(BookshelfInsertionFailure.WRONG_SELECTION);
        }

        if (column < 0 || column >= BookshelfConfiguration.getInstance().cols()) {
            return new SingleResult.Failure<>(BookshelfInsertionFailure.ILLEGAL_COLUMN);
        }

        if (tiles.size() > 3) {
            return new SingleResult.Failure<>(BookshelfInsertionFailure.TOO_MANY_TILES);
        }

        if (!game.getCurrentPlayer().getBookshelf().canFit(column, tiles.size())) {
            return new SingleResult.Failure<>(BookshelfInsertionFailure.NO_FIT);
        }

        game.onPlayerInsertionPhase(column, tiles);

        onPlayerCheckingRequest();
        return new SingleResult.Success<>();
    }

    public void onPlayerCheckingRequest() {
        game.onPlayerCheckingPhase();
    }




}
