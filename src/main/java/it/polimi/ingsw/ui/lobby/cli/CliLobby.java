package it.polimi.ingsw.ui.lobby.cli;

import it.polimi.ingsw.app.model.AggregatedPlayerInfo;
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

import static it.polimi.ingsw.model.game.GameMode.numberToMode;

public class CliLobby implements LobbyGateway {

    private final LobbyViewEventHandler handler;

    private ServerStatus currentState = null;
    private List<AggregatedPlayerInfo> playerInfo = new ArrayList<>();

    private String owner = null;

    public CliLobby(LobbyViewEventHandler handler) {
        this.handler = handler;
    }


    @Override
    public synchronized void onServerStatusUpdate(ServerStatus status, List<AggregatedPlayerInfo> playerInfo) {
        currentState = status;
        this.playerInfo = playerInfo;

        updateCliLobby();
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
            }
        }
    }

    @Override
    public synchronized void onServerConnectionReply(TypedResult<GameConnectionSuccess, GameConnectionError> result) {
        switch (result) {
            case TypedResult.Failure<GameConnectionSuccess, GameConnectionError> failure -> {
                switch (failure.error()){
                    case ALREADY_CONNECTED_PLAYER -> {
                        Console.out("You are alredy connected to this game.");
                    }
                    case USERNAME_ALREADY_IN_USE -> {
                        Console.out("This username is already taken.");
                    }
                    case MAX_PLAYER_REACHED -> {
                        Console.out("The maximum amount of players for this game has been reached already.");
                    }
                    case GAME_ALREADY_STARTED -> {
                        Console.out("A game is already running, you can't enter. Change server if you want to play.");
                    }
                    case GAME_ALREADY_ENDED -> {
                        Console.out("The game has already ended.");
                    }
                }
            }
            case TypedResult.Success<GameConnectionSuccess, GameConnectionError> success -> {
                owner = success.value().username();
            }
        }
    }

    @Override
    public void confirmOwner(String owner) {
        this.owner = owner;
    }


    @Override
    public void run() {
        while (currentState == null) {
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        updateCliLobby();
    }

    private void updateCliLobby() {
        if (owner != null) {
            Console.out("List of players currently in this game:\n");
            for (int i = 0; i < playerInfo.size(); i++) {
                Console.out(playerInfo.get(i).username() + " " + playerInfo.get(i).status());
                if (playerInfo.get(i).isHost()) {
                    Console.out(" Game Host");
                }
                Console.printnl();
            }
            return;
        }

        switch (currentState) {
            case NO_GAME_STARTED -> {
                GameMode mode;
                while (true) {
                    Console.out("""
                            No game currently started. To create one, you have to give me the number of players
                            for this game (between 2 and 4).""");

                    int playersAmount = Integer.parseInt(Console.in());

                    if (playersAmount >= 2 && playersAmount <= 4) {
                        mode = numberToMode(playersAmount);
                        break;
                    } else {
                        Console.out("You need to select a number between 2 and 4.");
                    }
                }

                Console.out("Now give me your username");

                String username = Console.in();

                handler.sendGameStartRequest(username, mode);

            }
            case GAME_INITIALIZING -> {
                Console.out("A game has been started already, if you want to join give me your username.");

                String username = Console.in();

                handler.sendGameConnectionRequest(username);
            }
            case GAME_RUNNING -> {
                Console.out("A game is already running, you can't enter. Change server if you want to play.");
            }
        }
    }

}
