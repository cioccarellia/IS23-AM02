package it.polimi.ingsw.ui.lobby.gui;

import it.polimi.ingsw.app.model.PlayerInfo;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.TypedResult;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameCreationError;
import it.polimi.ingsw.controller.server.result.types.GameConnectionSuccess;
import it.polimi.ingsw.controller.server.result.types.GameCreationSuccess;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.ui.game.gui.SceneManager;
import it.polimi.ingsw.ui.lobby.LobbyGateway;
import it.polimi.ingsw.ui.lobby.LobbyViewEventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Keeps a reference to the owner (which can be null at the beginning) of this client instance.
 */

public class GuiLobbyController implements LobbyGateway {
    private String owner = null;

    private final LobbyViewEventHandler server;
    private boolean isKilled = false;

    private ServerStatus status = null;
    private List<PlayerInfo> playerInfo = new ArrayList<>();

    @FXML
    public TextField usernameField;
    @FXML
    public Label loginStatus;
    @FXML
    public Button loginButton;
    @FXML
    public RadioButton twoplayers;
    @FXML
    public RadioButton threeplayers;
    @FXML
    public RadioButton fourplayers;
    @FXML
    public Label nplayers;

    GameMode gameMode;

    public GuiLobbyController(LobbyViewEventHandler handler) {
        this.server = handler;

        setRadioButtonsClickListeners();
    }

    @Override
    public void onServerStatusUpdate(ServerStatus status, List<PlayerInfo> playerInfo) {
        if (isKilled)
            return;

        this.status = status;
        this.playerInfo = playerInfo;

        renderModelUpdate();
    }

    @Override
    public void onServerCreationReply(TypedResult<GameCreationSuccess, GameCreationError> result) {
        if (isKilled)
            return;

        switch (result) {
            case TypedResult.Failure<GameCreationSuccess, GameCreationError> failure -> {
                switch (failure.error()) {
                    case GAME_ALREADY_STARTED -> {
                        loginStatus.setText("GAME ALREADY STARTED, insert Username!");
                    }
                    case INVALID_USERNAME -> {

                        loginStatus.setText("loginStatus: invalid Username");
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
    public void onServerConnectionReply(TypedResult<GameConnectionSuccess, GameConnectionError> result) {
        if (isKilled)
            return;

        switch (result) {
            case TypedResult.Failure<GameConnectionSuccess, GameConnectionError> failure -> {
                switch (failure.error()) {
                    case ALREADY_CONNECTED_PLAYER -> {

                    }
                    case USERNAME_ALREADY_IN_USE -> {
                        loginStatus.setText("loginStatus: Username already in use");
                        loginButton.setOnMouseClicked(mouseEvent -> {
                            server.sendGameConnectionRequest(usernameField.getText());
                        });

                    }
                    case MAX_PLAYER_AMOUNT_EACHED -> {
                        loginStatus.setText("loginStatus: max player amount eached");
                        loginButton.setText("QUIT");
                        loginButton.setOnMouseClicked(mouseEvent -> {
                            kill();
                        });
                    }
                    case NO_GAME_TO_JOIN -> {
                        loginStatus.setText("loginStatus: no game to join");
                        loginButton.setText("QUIT");
                        loginButton.setOnMouseClicked(mouseEvent -> {
                            kill();
                        });
                    }
                    case GAME_ALREADY_STARTED -> {
                        loginStatus.setText("loginStatus: game already started");
                        loginButton.setText("QUIT");
                        loginButton.setOnMouseClicked(mouseEvent -> {
                            kill();
                        });
                    }
                    case GAME_ALREADY_ENDED -> {
                        loginStatus.setText("loginStatus: game already ended");
                        loginButton.setText("QUIT");
                        loginButton.setOnMouseClicked(mouseEvent -> {
                            kill();
                        });
                    }
                    case INVALID_USERNAME -> {
                        loginStatus.setText("loginStatus: invalid Username");
                        loginButton.setOnMouseClicked(mouseEvent -> {
                            server.sendGameConnectionRequest(usernameField.getText());
                        });
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



    private void renderModelUpdate() {
        if (isKilled)
            return;

        if (status == null) {
            return;
        }


        switch (status) {

            case NO_GAME_STARTED -> {

                if (gameMode == null) {
                    twoplayers.setSelected(true);
                    gameMode = GameMode.GAME_MODE_2_PLAYERS;
                }
                server.sendGameStartRequest(usernameField.getText(), gameMode);
            }
            case GAME_INITIALIZING -> {

                nplayers.setOpacity(0);

                twoplayers.setDisable(true);
                twoplayers.setOpacity(0);

                threeplayers.setDisable(true);
                threeplayers.setOpacity(0);

                fourplayers.setDisable(true);
                fourplayers.setOpacity(0);

                server.sendGameConnectionRequest(usernameField.getText());
            }
            case GAME_RUNNING -> {
                // no operation required, controller handles this case
            }
        }
    }

    private void setRadioButtonsClickListeners() {
        twoplayers.setOnMouseClicked(mouseEvent -> {
            gameMode = GameMode.GAME_MODE_2_PLAYERS;
        });
        threeplayers.setOnMouseClicked(mouseEvent -> {
            gameMode = GameMode.GAME_MODE_3_PLAYERS;
        });
        threeplayers.setOnMouseClicked(mouseEvent -> {
            gameMode = GameMode.GAME_MODE_4_PLAYERS;
        });
    }

    @Override
    public void kill() {
        isKilled = true;
    }


}
