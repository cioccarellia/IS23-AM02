package it.polimi.ingsw.ui.game.gui;


import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.SingleResult.Failure;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.config.logic.LogicConfiguration;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.ui.game.GameGateway;
import it.polimi.ingsw.ui.game.GameViewEventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static it.polimi.ingsw.model.game.goal.Token.FULL_SHELF_TOKEN;


public class GuiGameController implements GameGateway {

    private static final Logger logger = LoggerFactory.getLogger(GuiGameController.class);

    private static final int maxSelectionSize = LogicConfiguration.getInstance().maxSelectionSize();
    private static final int commonGoalCardsAmount = LogicConfiguration.getInstance().commonGoalCardAmount();

    private static int col = 0;
    private final List<Tile> orderedTiles = new ArrayList<>();
    private final Set<Coordinate> selectedCoordinates = new HashSet<>();

    @FXML
    public GridPane board;
    @FXML
    public GridPane myBookShelf;
    @FXML
    public GridPane player1BookShelf;
    @FXML
    public GridPane player2BookShelf;
    @FXML
    public GridPane player3BookShelf;
    @FXML
    public GridPane player4BookShelf;
    @FXML
    public ImageView endGameToken;
    @FXML
    public ImageView firstCommonGoalCardTopToken;
    @FXML
    public ImageView secondCommonGoalCardTopToken;
    @FXML
    public ImageView firstCommonGoalCard;
    @FXML
    public ImageView secondCommonGoalCard;
    @FXML
    public ImageView personalGoalCard;
    @FXML
    public Tab player1Button;
    @FXML
    public Tab player2Button;
    @FXML
    public Tab player3Button;
    @FXML
    public Tab player4Button;
    @FXML
    public Button selectingButton;
    @FXML
    public Button insertingButton;
    @FXML
    public ToggleGroup column;
    @FXML
    public RadioButton column1;
    @FXML
    public RadioButton column2;
    @FXML
    public RadioButton column3;
    @FXML
    public RadioButton column4;
    @FXML
    public RadioButton column5;
    @FXML
    public ImageView tile1Selected;
    @FXML
    public ImageView tile2Selected;
    @FXML
    public ImageView tile3Selected;
    @FXML
    public Label label1;
    @FXML
    public Label label2;
    @FXML
    public Label label3;
    @FXML
    public ImageView insertionCommonGoalCard1;
    @FXML
    public ImageView insertionCommonGoalCard2;
    @FXML
    public ImageView insertionPersonalGoalCard;
    @FXML
    public Label Status;
    @FXML
    public Label CurrentPlayer;
    @FXML
    public Label insertionStatus;
    @FXML
    public GridPane insertionBookshelf;

    private GameViewEventHandler server;
    private Game model;
    private String owner;

    public Scene scene;
    public Scene insertionScene; //to remember that insertion scene needs an initialization


    public void initModel(Game model, GameViewEventHandler handler, String owner) {
        this.model = model;
        this.server = handler;
        this.owner = owner;
    }

    @Override
    public void onGameCreated() {
        List<ImageView> selectedTiles = Arrays.asList(tile1Selected, tile2Selected, tile3Selected);
        List<ImageView> insertionCommonGoalCard = Arrays.asList(insertionCommonGoalCard1, insertionCommonGoalCard2);
        List<GridPane> bookshelves = Arrays.asList(player1BookShelf, player2BookShelf, player3BookShelf, player4BookShelf);
        List<ImageView> topTokens = Arrays.asList(firstCommonGoalCardTopToken, secondCommonGoalCardTopToken);
        List<ImageView> commonGoalCards = Arrays.asList(firstCommonGoalCard, secondCommonGoalCard);
        List<Tab> playersButtons = Arrays.asList(player1Button, player2Button, player3Button, player4Button);
        List<RadioButton> columnButtons = Arrays.asList(column1, column2, column3, column4, column5);
        List<Label> labels = Arrays.asList(label1, label2, label3);



        //game starting
        model.onGameStarted();

        //GUI initialization:
        //index scene

        for (int i = 0; i < model.getGameMode().maxPlayerAmount(); i++) {
            playersButtons.get(i).setText(model.getSessions().playerSessions().get(i).getUsername());
        }

        //PGC + CGC initialization
        for (int i = 0; i < commonGoalCardsAmount; i++) {
            commonGoalCards.set(i, scene.commonGoalCardUpdate(model.getCommonGoalCards().get(i).getCommonGoalCard()));
        }

        personalGoalCard = scene.personalGoalCardUpdate(model, owner);

        //insertion scene:

        // PGC +CGC initialization
        for (int i = 0; i < commonGoalCardsAmount; i++) {
            insertionCommonGoalCard.set(i, insertionScene.commonGoalCardUpdate(model.getCommonGoalCards().get(i).getCommonGoalCard()));
        }

        insertionPersonalGoalCard = insertionScene.personalGoalCardUpdate(model, owner);

        //owner's bookshelf inizialization
        insertionBookshelf = insertionScene.bookshelfUpdate(model.getSessions().getByUsername(owner).getBookshelf());


        //model update
        modelUpdate(model);

    }

    @Override
    public void modelUpdate(Game game) {
        List<ImageView> selectedTiles = Arrays.asList(tile1Selected, tile2Selected, tile3Selected);
        List<ImageView> insertionCommonGoalCard = Arrays.asList(insertionCommonGoalCard1, insertionCommonGoalCard2);
        List<GridPane> bookshelves = Arrays.asList(player1BookShelf, player2BookShelf, player3BookShelf, player4BookShelf);
        List<ImageView> topTokens = Arrays.asList(firstCommonGoalCardTopToken, secondCommonGoalCardTopToken);
        List<ImageView> commonGoalCards = Arrays.asList(firstCommonGoalCard, secondCommonGoalCard);
        List<Tab> playersButtons = Arrays.asList(player1Button, player2Button, player3Button, player4Button);
        List<RadioButton> columnButtons = Arrays.asList(column1, column2, column3, column4, column5);
        List<Label> labels = Arrays.asList(label1, label2, label3);

        this.model = game;

        //INDEX UPDATE:

        //board Update
        board = scene.boardUpdate(model);

        //owner's bookshelf update
        myBookShelf = scene.bookshelfUpdate(model.getCurrentPlayerSession().getBookshelf());

        //player's bookshelf update
        for (int i = 0; i < model.getGameMode().maxPlayerAmount(); i++) {
            bookshelves.set(i, scene.bookshelfUpdate(model.getSessions().playerSessions().get(i).getBookshelf()));
        }


        // CGC token update
        endGameToken.setImage(GuiResources.getToken(FULL_SHELF_TOKEN));

        for (int i = 0; i < commonGoalCardsAmount; i++) {
            topTokens.set(i, scene.CommonGoalCardTokenUpdate(model.getCommonGoalCards().get(i)));
        }

        CurrentPlayer.setText(model.getCurrentPlayerSession().getUsername());

        //INSERTION UPDATE:

        //tile update
        for (int i = 0; i < model.getSessions().getByUsername(owner).getPlayerTileSelection().getSelectedTiles().size(); i++) {
            selectedTiles.get(i).setImage(GuiResources.getTile(model.getSessions().getByUsername(owner).getPlayerTileSelection().getSelectedTiles().get(i)));
        }

        //bookshelf update
        insertionBookshelf = insertionScene.bookshelfUpdate(model.getSessions().getByUsername(owner).getBookshelf());

    }


    @Override
    public void onGameSelectionReply(SingleResult<TileSelectionFailures> turnResult) {
        switch (turnResult) {
            case Failure<TileSelectionFailures> failure -> {
                switch (failure.error()) {
                    case WRONG_GAME_PHASE -> {
                        Status.setOpacity(1);
                        Status.setText("Error, wrong game phase");

                    }
                    case UNAUTHORIZED_SELECTION -> {
                        Status.setOpacity(1);
                        Status.setText("Error, unauthorized selection");

                    }
                    case UNAUTHORIZED_PLAYER -> {
                        Status.setOpacity(1);
                        Status.setText("Error, player not authorized");
                    }
                }
            }
            case SingleResult.Success<TileSelectionFailures> success -> {
                Status.setOpacity(0);
            }

        }

    }

    @Override
    public void onGameInsertionReply(SingleResult<BookshelfInsertionFailure> turnResult) {
        switch (turnResult) {
            case Failure<BookshelfInsertionFailure> failure -> {
                switch (failure.error()) {
                    case WRONG_SELECTION -> {
                        insertionStatus.setOpacity(1);
                        insertionStatus.setText("Error, wrong selection");

                    }
                    case ILLEGAL_COLUMN -> {
                        insertionStatus.setOpacity(1);
                        insertionStatus.setText("Error, column out of bounds");

                    }
                    case TOO_MANY_TILES -> {
                        insertionStatus.setOpacity(1);
                        insertionStatus.setText("Error, too many tiles selected");

                    }
                    case NO_FIT -> {
                        insertionStatus.setOpacity(1);
                        insertionStatus.setText("Error, selected tiles can't fit in this columns");

                    }
                    case WRONG_PLAYER -> {
                        insertionStatus.setOpacity(1);
                        insertionStatus.setText("Error, unauthorized action from non active player");

                    }
                    case WRONG_GAME_PHASE -> {
                        insertionStatus.setOpacity(1);
                        insertionStatus.setText("Error, wrong game phase");

                    }
                }
            }
            case SingleResult.Success<BookshelfInsertionFailure> success -> {
                insertionStatus.setOpacity(0);

            }
        }


    }


    public void setInsertionButtonListener() {
        insertingButton.setOnMouseClicked(mouseEvent -> {
            server.onViewInsertion(col, orderedTiles);
            SceneManager.changeScene(SceneManager.getActualController(), "index.fxml");
        });
    }

    public void setRadioButtonInsertionListeners() {
        /* se dovesse funzionare con il for loop
        List<RadioButton> columnButtons = Arrays.asList(column1, column2, column3, column4, column5);
        for (int i = 0; i < columnButtons.size(); i++) {
            RadioButton columnButton = columnButtons.get(i);
            int value = i;
            columnButton.setOnMouseClicked(mouseEvent -> {
                column.selectToggle(columnButton);
                col = value;
            });
        }
        */

        column1.setOnMouseClicked(mouseEvent -> {
            column.selectToggle(column1);
            col = 0;
        });

        column2.setOnMouseClicked(mouseEvent -> {
            column.selectToggle(column2);
            col = 1;
        });

        column3.setOnMouseClicked(mouseEvent -> {
            column.selectToggle(column3);
            col = 2;
        });

        column4.setOnMouseClicked(mouseEvent -> {
            column.selectToggle(column4);
            col = 3;
        });

        column5.setOnMouseClicked(mouseEvent -> {
            column.selectToggle(column5);
            col = 4;
        });
    }

    public void setOrderedSelectionTIleInsertionListeners() {
        /* se dovesse funzionare con il for loop
        List<ImageView> selectedTiles = Arrays.asList(tile1Selected, tile2Selected, tile3Selected);
        List<Label> labels = Arrays.asList(label1, label2, label3);
        for (int i = 0; i < selectedTiles.size(); i++) {
            ImageView selTile = selectedTiles.get(i);
            Label label = labels.get(i);
            int value = i;

            selTile.setOnMouseClicked(mouseEvent -> {
                if (orderedTiles.size() == 0) {
                    orderedTiles.add(GuiResources.getTileType(selTile.getImage()));
                    label.setText(String.valueOf(value + 1));
                } else if (orderedTiles.size() == 1) {
                    orderedTiles.add(GuiResources.getTileType(selTile.getImage()));
                    label.setText(String.valueOf(value + 2));
                } else if (orderedTiles.size() == 2) {
                    orderedTiles.add(GuiResources.getTileType(selTile.getImage()));
                    label.setText(String.valueOf(value + 3));
                }
            });
        }
        */

        tile1Selected.setOnMouseClicked(mouseEvent -> {
            if (orderedTiles.size() == 0) {
                orderedTiles.add(GuiResources.getTileType(tile1Selected.getImage()));
                label1.setText(String.valueOf(1));
            } else if (orderedTiles.size() == 1) {
                orderedTiles.add(GuiResources.getTileType(tile1Selected.getImage()));
                label1.setText(String.valueOf(2));
            } else if (orderedTiles.size() == 2) {
                orderedTiles.add(GuiResources.getTileType(tile1Selected.getImage()));
                label1.setText(String.valueOf(3));
            }
        });
        tile2Selected.setOnMouseClicked(mouseEvent -> {
            if (orderedTiles.size() == 0) {
                orderedTiles.add(GuiResources.getTileType(tile2Selected.getImage()));
                label2.setText(String.valueOf(1));
            } else if (orderedTiles.size() == 1) {
                orderedTiles.add(GuiResources.getTileType(tile2Selected.getImage()));
                label2.setText(String.valueOf(2));
            } else if (orderedTiles.size() == 2) {
                orderedTiles.add(GuiResources.getTileType(tile2Selected.getImage()));
                label2.setText(String.valueOf(3));
            }
        });
        tile3Selected.setOnMouseClicked(mouseEvent -> {
            if (orderedTiles.size() == 0) {
                orderedTiles.add(GuiResources.getTileType(tile3Selected.getImage()));
                label3.setText(String.valueOf(1));
            } else if (orderedTiles.size() == 1) {
                orderedTiles.add(GuiResources.getTileType(tile3Selected.getImage()));
                label3.setText(String.valueOf(2));
            } else if (orderedTiles.size() == 2) {
                orderedTiles.add(GuiResources.getTileType(tile3Selected.getImage()));
                label3.setText(String.valueOf(3));
            }
        });
    }
}