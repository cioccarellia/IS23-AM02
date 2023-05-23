package it.polimi.ingsw.ui.lobby.cli;

import it.polimi.ingsw.app.model.PlayerInfo;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.TypedResult;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameCreationError;
import it.polimi.ingsw.controller.server.result.types.GameConnectionSuccess;
import it.polimi.ingsw.controller.server.result.types.GameCreationSuccess;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.ui.cli.Console;
import it.polimi.ingsw.ui.lobby.LobbyGateway;
import it.polimi.ingsw.ui.lobby.LobbyViewEventHandler;

import java.util.ArrayList;
import java.util.List;

public class CliLobby implements LobbyGateway {

    private final LobbyViewEventHandler handler;

    private ServerStatus currentState = null;
    private List<PlayerInfo> playerInfo = new ArrayList<>();

    private String owner = null;

    public CliLobby(LobbyViewEventHandler handler) {
        this.handler = handler;
    }


    @Override
    public synchronized void onServerStatusUpdate(ServerStatus status, List<PlayerInfo> playerInfo) {
        currentState = status;
        this.playerInfo = playerInfo;

        renderModelUpdate();
    }

    @Override
    public synchronized void onServerCreationReply(TypedResult<GameCreationSuccess, GameCreationError> result) {
        switch (result) {
            case TypedResult.Failure<GameCreationSuccess, GameCreationError> failure -> {
                switch (failure.error()) {
                    case GAME_ALREADY_STARTED -> {
                        Console.out("A game is already running, you can't enter. Change server if you want to play.");
                    }
                }
            }
            case TypedResult.Success<GameCreationSuccess, GameCreationError> success -> {
                owner = success.value().username();
                playerInfo = success.value().playerInfo();
            }
        }

        renderModelUpdate();
    }

    @Override
    public synchronized void onServerConnectionReply(TypedResult<GameConnectionSuccess, GameConnectionError> result) {
        switch (result) {
            case TypedResult.Failure<GameConnectionSuccess, GameConnectionError> failure -> {
                switch (failure.error()) {
                    case ALREADY_CONNECTED_PLAYER -> {
                        Console.out("You are already connected to this game.\n");
                    }
                    case USERNAME_ALREADY_IN_USE -> {
                        Console.out("This username is already taken.\n");
                    }
                    case MAX_PLAYER_REACHED -> {
                        Console.out("The maximum amount of players for this game has been reached already.\n");
                    }
                    case GAME_ALREADY_STARTED -> {
                        Console.out("A game is already running, you can't enter. Change server if you want to play.\n");
                    }
                    case GAME_ALREADY_ENDED -> {
                        Console.out("The game has already ended.\n");
                    }
                }

            }
            case TypedResult.Success<GameConnectionSuccess, GameConnectionError> success -> {
                owner = success.value().username();
                playerInfo = success.value().playerInfo();
            }
        }

        renderModelUpdate();
    }



    @Override
    public void run() {

    }

    private void renderModelUpdate() {
        if (owner != null) {
            Console.out("List of players currently in this game:\n");
            for (PlayerInfo info : playerInfo) {
                Console.out(info.username() + " " + info.status());

                if (info.isHost()) {
                    Console.out(" Game Host");
                }
                Console.printnl();
            }
            return;
        }

        switch (currentState) {
            case NO_GAME_STARTED -> {
                GameMode mode;

                Console.out("""
                        No game currently started. To create one, you have to give me the number of players
                        for this game (between 2 and 4).
                        """);

                String in = Console.in();

                int playersAmount = Integer.parseInt(in);

                if (playersAmount >= 2 && playersAmount <= 4) {
                    mode = GameMode.numberToMode(playersAmount);
                } else {
                    Console.out("You need to select a number between 2 and 4.\n");
                    renderModelUpdate();
                    return;
                }

                Console.out("Now give me your username.\n");

                String username = Console.in();

                handler.sendGameStartRequest(username, mode);
            }
            case GAME_INITIALIZING -> {
                Console.out("A game has been started already, if you want to join give me your username.\n");

                String username = Console.in();

                handler.sendGameConnectionRequest(username);
            }
            case GAME_RUNNING -> {
                Console.out("A game is already running, you can't enter. Change server if you want to play.\n");
            }
        }
    }

}
