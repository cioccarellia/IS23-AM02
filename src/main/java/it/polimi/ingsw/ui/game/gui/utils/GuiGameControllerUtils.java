package it.polimi.ingsw.ui.game.gui.utils;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.cards.common.CommonGoalCard;
import it.polimi.ingsw.model.cards.common.CommonGoalCardIdentifier;
import it.polimi.ingsw.model.config.board.BoardConfiguration;
import it.polimi.ingsw.model.config.logic.LogicConfiguration;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.goal.Token;
import it.polimi.ingsw.model.player.PlayerSession;
import it.polimi.ingsw.model.player.selection.PlayerTileSelection;
import it.polimi.ingsw.ui.game.gui.renders.CommonGoalCardDescriptionRender;
import it.polimi.ingsw.ui.game.gui.renders.CommonGoalCardRender;
import it.polimi.ingsw.ui.game.gui.renders.TokenRender;
import it.polimi.ingsw.ui.game.gui.resources.ResourcePathConstants;
import it.polimi.ingsw.utils.javafx.PaneViewUtil;
import it.polimi.ingsw.utils.javafx.UiUtils;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.List;

public class GuiGameControllerUtils {
    private static final int maxTokensPerPlayer = 3;
    private static final int commonGoalCardsAmount = LogicConfiguration.getInstance().commonGoalCardAmount();
    private static final int maxSelectionSize = LogicConfiguration.getInstance().maxSelectionSize();
    private static final int dimension = BoardConfiguration.getInstance().getDimension();


    // onGameCreated
    public static void commonGoalCardsOnCreation(Game model, List<ImageView> commonGoalCards, List<Label> commonGoalCardDescriptions, List<ImageView> topTokens) {
        for (int i = 0; i < commonGoalCardsAmount; i++) {
            CommonGoalCard currentCommonGoalCard = model.getCommonGoalCards().get(i).getCommonGoalCard();

            CommonGoalCardRender.renderCommonGoalCard(commonGoalCards.get(i), currentCommonGoalCard);
            commonGoalCardDescriptions.get(i).setText(CommonGoalCardDescriptionRender.renderCommonGoalCardDescription(currentCommonGoalCard.getId()));

            Token topToken = model.getCommonGoalCards().get(i).getCardTokens().lastElement();
            TokenRender.renderToken(topTokens.get(i), topToken);
        }
    }


    // render
    public static void tokenUpdate(PlayerSession session, List<ImageView> obtainedTokens) {
        for (int i = 0; i < maxTokensPerPlayer; i++) {
            List<Token> playerTokens = session.getAcquiredTokens();
            Token token = null;
            if (i < playerTokens.size()) {
                token = playerTokens.get(i);
            }

            TokenRender.renderToken(obtainedTokens.get(i), token);
        }
    }


    public static void checkAchievedCommonGoalCards(PlayerSession session, Game model, List<Label> commonGoalCardDescriptions) {
        List<CommonGoalCardIdentifier> ownerAchievedCommonGoalCards = session.getAchievedCommonGoalCards();
        List<CommonGoalCardIdentifier> gameCommonGoalCards = model.getCommonGoalCards().stream()
                .map(card -> card.getCommonGoalCard().getId()).toList();

        for (int i = 0; i < commonGoalCardsAmount && !ownerAchievedCommonGoalCards.isEmpty(); i++) {
            if (ownerAchievedCommonGoalCards.contains(gameCommonGoalCards.get(i))) {
                commonGoalCardDescriptions.get(i)
                        .setText(commonGoalCardDescriptions.get(i).getText() +
                                "\nYou already achieved the token for this card!");
            }
        }
    }

    public static void topTokenUpdate(Game model, List<ImageView> topTokens) {
        for (int i = 0; i < commonGoalCardsAmount; i++) {
            Token topToken = model.getCommonGoalCards().get(i).getCardTokens().lastElement();
            TokenRender.renderToken(topTokens.get(i), topToken);
        }
    }


    public static void setSelectedTiles(List<ImageView> selectedTiles, PlayerSession player) {
        PlayerTileSelection playerTileSelection = player.getPlayerTileSelection();
        List<Tile> playerSelectedTiles = null;

        if (playerTileSelection != null) {
            playerSelectedTiles = player.getPlayerTileSelection().getSelectedTiles();
        }

        for (int i = 0; i < maxSelectionSize; i++) {
            Image tileImage = null;
            if (playerSelectedTiles != null && playerSelectedTiles.size() < i) {
                String url = ResourcePathConstants.Tiles.mapTileToImagePath(playerSelectedTiles.get(i));
                tileImage = new Image(url);
            }

            selectedTiles.get(i).setImage(tileImage);
            setDarkeningEffect(selectedTiles.get(i));
        }

    }

    public static void setDarkeningEffect(ImageView selectedTile) {
        Light.Distant light = new Light.Distant();
        light.setColor(Color.DARKGREY);

        Lighting effect = new Lighting(light);
        selectedTile.setEffect(effect);
    }

    public static void makeNonSelectableTilesDark(GridPane gridBoard, Board board) {
        Node[][] gridPaneNodes = PaneViewUtil.matrixify(gridBoard, dimension, dimension);

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                // if either no image is present or board doesn't have any content for (i,j)
                if (gridPaneNodes[i][j] == null) {
                    continue;
                }

                // ImageView containing our bookshelf tile[i][j]
                ImageView imageView = (ImageView) gridPaneNodes[i][j];

                if (board.getTileAt(i, j).isPresent() && !board.hasAtLeastOneFreeEdge(new Coordinate(i, j))) {
                    setDarkeningEffect(imageView);
                }
            }
        }
    }


    // insertion

    public static void manageTileSelectionForInsertion(Label selectedTileLabel, ImageView selectedTileImageView, List<Tile> orderedTiles, List<Tile> playerSelectedTiles, List<Label> iterSelectedTilesLabel, int cursor) {
        if (Integer.parseInt(selectedTileLabel.getText()) == 0) {
            // if it still has the default value of 0

            // remove tile darkened effect and make visible
            selectedTileImageView.setEffect(null);
            UiUtils.visible(selectedTileLabel);

            // add the tile to the orderedTiles list
            orderedTiles.add(playerSelectedTiles.get(cursor));

            // set the label to the current orderedTiles size
            selectedTileLabel.setText(String.valueOf(orderedTiles.size()));
        } else {
            // if it didn't have the default value, it means it's being deselected

            // change all the other labels
            int currentLabelValue = Integer.parseInt(selectedTileLabel.getText());

            for (int i = 0; i < iterSelectedTilesLabel.size(); i++) {
                int otherLabelValue = Integer.parseInt(iterSelectedTilesLabel.get(i).getText());
                if (i != cursor && otherLabelValue > currentLabelValue) {
                    iterSelectedTilesLabel.get(i).setText(String.valueOf(otherLabelValue - 1));
                }
            }

            // remove the tile from the orderedTiles list
            orderedTiles.remove(playerSelectedTiles.get(0));

            // set the darkened effect to the ImageView
            setDarkeningEffect(selectedTileImageView);

            // make the label invisible and set it to 0 again
            UiUtils.invisible(selectedTileLabel);
            selectedTileLabel.setText("0");
        }
    }
}
