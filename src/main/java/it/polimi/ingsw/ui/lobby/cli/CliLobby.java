package it.polimi.ingsw.ui.lobby.cli;

import com.github.tomaslanger.chalk.Chalk;
import it.polimi.ingsw.app.model.PlayerInfo;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.TypedResult;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameCreationError;
import it.polimi.ingsw.controller.server.result.types.GameConnectionSuccess;
import it.polimi.ingsw.controller.server.result.types.GameCreationSuccess;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.ui.Renderable;
import it.polimi.ingsw.ui.game.cli.Console;
import it.polimi.ingsw.ui.lobby.LobbyGateway;
import it.polimi.ingsw.ui.lobby.LobbyViewEventHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * The CliLobby class represents the CLI implementation of the lobby gateway, which allows players to interact
 * with the lobby and join/create games.
 */
public class CliLobby implements LobbyGateway, Renderable {

    private final LobbyViewEventHandler handler;

    private ServerStatus currentState = null;
    private List<PlayerInfo> playerInfo = new ArrayList<>();

    /**
     * Keeps a reference to the owner (which can be null at the beginning) of this client instance.
     */
    private String owner = null;

    boolean isKilled = false;

    /**
     * Constructs a CliLobby instance with the specified LobbyViewEventHandler.
     *
     * @param handler the LobbyViewEventHandler to handle lobby events
     */
    public CliLobby(LobbyViewEventHandler handler) {
        this.handler = handler;
    }

    /**
     * Notifies the CliLobby instance about a server status update, including the current status and player information.
     *
     * @param status     the updated server status
     * @param playerInfo the list of player information
     */
    @Override
    public void onServerStatusUpdate(ServerStatus status, List<PlayerInfo> playerInfo) {
        if (isKilled)
            return;

        currentState = status;
        this.playerInfo = playerInfo;

        render();
    }

    /**
     * Notifies the CliLobby instance about a server creation reply, including the result of the game creation request.
     *
     * @param result the typed result of the game creation request
     */
    @Override
    public void onServerCreationReply(TypedResult<GameCreationSuccess, GameCreationError> result) {
        if (isKilled)
            return;

        switch (result) {
            case TypedResult.Failure<GameCreationSuccess, GameCreationError> failure -> {
                switch (failure.error()) {
                    case GAME_ALREADY_INITIALIZING -> {
                        Console.out("A game has been initialized already. You can't choose the number of players.");

                        // out of phase model, requiring manual model update since server isn't aware of our existence yet
                        handler.sendStatusUpdateRequest();
                    }
                    case GAME_ALREADY_RUNNING -> {
                        Console.out("A game is already running, you can't enter. Change server if you want to play.\n");
                    }
                    case INVALID_USERNAME -> {
                        Console.out("The username is invalid.\n");
                        render();
                    }
                }

            }
            case TypedResult.Success<GameCreationSuccess, GameCreationError> success -> {
                owner = success.value().username();
                playerInfo = success.value().playerInfo();
                render();
            }
        }
    }

    /**
     * Notifies the CliLobby instance about a server connection reply, including the result of the game connection request.
     *
     * @param result the typed result of the game connection request
     */
    @Override
    public void onServerConnectionReply(TypedResult<GameConnectionSuccess, GameConnectionError> result) {
        if (isKilled)
            return;

        switch (result) {
            case TypedResult.Failure<GameConnectionSuccess, GameConnectionError> failure -> {
                switch (failure.error()) {
                    case ALREADY_CONNECTED_PLAYER -> Console.out("You are already connected to this game.\n");
                    case USERNAME_ALREADY_IN_USE -> Console.out("This username is already taken.\n");
                    case MAX_PLAYER_AMOUNT_EACHED ->
                            Console.out("The maximum amount of players for this game has been reached already.\n");
                    case NO_GAME_TO_JOIN -> Console.out("No game has been started.\n");
                    case GAME_ALREADY_STARTED ->
                            Console.out("A game is already running, you can't enter. Change server if you want to play.\n");
                    case GAME_ALREADY_ENDED -> Console.out("The game has already ended.\n");
                    case INVALID_USERNAME -> Console.out("The username is invalid.\n");
                }

            }
            case TypedResult.Success<GameConnectionSuccess, GameConnectionError> success -> {
                owner = success.value().username();
                playerInfo = success.value().playerInfo();
            }
        }

        render();
    }

    /**
     * Renders the model update based on the current state and owner information.
     */
    @Override
    public void render() {
        if (isKilled)
            return;

        if (owner != null) {
            Console.out("List of players currently in this game:\n");

            for (PlayerInfo info : playerInfo) {
                if (info.isHost()) {
                    Console.out(Chalk.on("[H]").red().toString());
                } else {
                    Console.out("[P]");
                }

                Console.out(" @" + info.username() + ", " + info.status().toHumanReadable());

                Console.outln();
            }
            return;
        }

        switch (currentState) {
            case NO_GAME_STARTED -> {
                GameMode mode;

                String in = Console.in("Game not started. To create one, enter the number of players for this game [2,4]");

                int playersAmount;

                try {
                    playersAmount = Integer.parseInt(in);
                } catch (NumberFormatException e) {
                    render();
                    return;
                }


                if (playersAmount >= 2 && playersAmount <= 4) {
                    mode = GameMode.numberToMode(playersAmount);
                } else {
                    Console.out("You need to select a number between 2 and 4.\n");
                    render();
                    return;
                }


                String username = Console.in("Input username");

                handler.sendGameStartRequest(username, mode);
            }
            case GAME_INITIALIZING -> {
                String username = Console.in("A game has been started already. Insert username to join");

                handler.sendGameConnectionRequest(username);
            }
            case GAME_RUNNING ->
                    Console.out("A game is already running, you can't enter. Change server if you want to play.\n");
        }
    }

    /**
     * Kills the CliLobby instance and stops further processing.
     */
    @Override
    public void kill() {
        isKilled = true;
    }
}
