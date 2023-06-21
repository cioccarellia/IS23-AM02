package it.polimi.ingsw.ui.game.gui.utils;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.cards.common.CommonGoalCard;
import it.polimi.ingsw.model.cards.common.CommonGoalCardIdentifier;
import it.polimi.ingsw.model.config.board.BoardConfiguration;
import it.polimi.ingsw.model.config.logic.LogicConfiguration;
import it.polimi.ingsw.model.game.goal.Token;
import it.polimi.ingsw.model.player.PlayerSession;
import it.polimi.ingsw.ui.game.gui.renders.CommonGoalCardDescriptionRender;
import it.polimi.ingsw.ui.game.gui.renders.CommonGoalCardRender;
import it.polimi.ingsw.ui.game.gui.renders.TokenRender;
import it.polimi.ingsw.utils.javafx.PaneViewUtil;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.apache.commons.lang.SerializationUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class GuiGameControllerUtils {
    private static final int maxTokensPerPlayer = 3;
    private static final int commonGoalCardsAmount = LogicConfiguration.getInstance().commonGoalCardAmount();
    private static final int dimension = BoardConfiguration.getInstance().getDimension();

    // on game created
    public static void commonGoalCardsOnCreation(GameModel model, List<ImageView> commonGoalCards, List<Label> commonGoalCardDescriptions, List<ImageView> topTokens) {
        for (int i = 0; i < commonGoalCardsAmount; i++) {
            CommonGoalCard currentCommonGoalCard = model.getCommonGoalCards().get(i).getCommonGoalCard();

            CommonGoalCardRender.renderCommonGoalCard(commonGoalCards.get(i), currentCommonGoalCard);
            commonGoalCardDescriptions.get(i).setText(CommonGoalCardDescriptionRender.renderCommonGoalCardDescription(currentCommonGoalCard.getId()));

            // no need to check if lastElement() is present, because this is done on game creation and there's always a token
            Token topToken = model.getCommonGoalCards().get(i).getCardTokens().lastElement();
            TokenRender.renderToken(topTokens.get(i), topToken);
        }
    }

    // game running updates
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

    public static void checkAchievedCommonGoalCards(PlayerSession session, GameModel model, List<Label> commonGoalCardDescriptions) {
        List<CommonGoalCardIdentifier> ownerAchievedCommonGoalCards = session.getAchievedCommonGoalCards();
        List<CommonGoalCardIdentifier> gameCommonGoalCards = model.getCommonGoalCards().stream().map(card -> card.getCommonGoalCard().getId()).toList();

        for (int i = 0; i < commonGoalCardsAmount && !ownerAchievedCommonGoalCards.isEmpty(); i++) {
            String labelCurrentText = commonGoalCardDescriptions.get(i).getText();
            String achievedText = "You already achieved the token for this card!";

            if (ownerAchievedCommonGoalCards.contains(gameCommonGoalCards.get(i)) && !labelCurrentText.contains(achievedText)) {
                commonGoalCardDescriptions.get(i).setText(labelCurrentText + "\n" + achievedText);
            }
        }
    }

    public static void topTokenUpdate(GameModel model, List<ImageView> topTokens) {
        for (int i = 0; i < commonGoalCardsAmount; i++) {
            Token topToken = null;

            // check that there still is a token, otherwise the image will be empty (in renderToken)
            if (!model.getCommonGoalCards().get(i).getCardTokens().isEmpty()) {
                topToken = model.getCommonGoalCards().get(i).getCardTokens().lastElement();
            }

            TokenRender.renderToken(topTokens.get(i), topToken);
        }
    }

    // utils
    public static void enableDarkeningEffect(ImageView selectedTile) {
        Light.Distant light = new Light.Distant();
        light.setColor(Color.valueOf("#444444"));

        Lighting effect = new Lighting(light);
        selectedTile.setEffect(effect);
    }

    public static void disableDarkeningEffectForAllTiles(GridPane gridBoard, Board board) {
        Node[][] gridPaneNodes = PaneViewUtil.matrixify(gridBoard, dimension, dimension);

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                // if either no image is present or board doesn't have any content for (i,j)
                if (gridPaneNodes[i][j] == null) {
                    continue;
                }

                // checks if the tile is present
                if (board.getTileAt(i, j).isEmpty()) {
                    continue;
                }

                // ImageView containing our bookshelf tile[i][j]
                ImageView imageView = (ImageView) gridPaneNodes[i][j];

                // un-darkens all the tiles
                imageView.setEffect(null);
            }
        }
    }

    public static void makeNonSelectableTilesDark(GridPane gridBoard, GameModel game, Set<Coordinate> selectedCoordinates) {
        Node[][] gridPaneNodes = PaneViewUtil.matrixify(gridBoard, dimension, dimension);

        Board board = game.getBoard();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                // if either no image is present or board doesn't have any content for (i,j)
                if (gridPaneNodes[i][j] == null) {
                    continue;
                }

                // checks that it is not the selected coordinate
                Coordinate currentCoordinate = new Coordinate(i, j);
                if (selectedCoordinates.stream().anyMatch(c -> c.equals(currentCoordinate))) {
                    continue;
                }

                // checks if the tile is present
                if (board.getTileAt(i, j).isEmpty()) {
                    continue;
                }

                // ImageView containing our bookshelf tile[i][j]
                ImageView imageView = (ImageView) gridPaneNodes[i][j];

                // darkens all the non-selectable ones that don't have a free edge
                if (!board.hasAtLeastOneFreeEdge(currentCoordinate)) {
                    enableDarkeningEffect(imageView);
                } else {
                    checkSelectedCoordinates(imageView, currentCoordinate, selectedCoordinates, game);
                }
            }
        }
    }

    public static void checkSelectedCoordinates(ImageView imageView, Coordinate currentCoordinate, Set<Coordinate> selectedCoordinates, GameModel game) {
        // darkens all the tiles that can't be selected with the currently selected tile and removes the effect from the acceptable ones
        Set<Coordinate> copy = (Set<Coordinate>) SerializationUtils.clone((Serializable) selectedCoordinates);

        if (!copy.isEmpty()) {
            copy.add(currentCoordinate);

            if (!game.isSelectionValid(copy)) {
                enableDarkeningEffect(imageView);
            } else if (game.isSelectionValid(copy)) imageView.setEffect(null);
        } else {
            imageView.setEffect(null);
        }
    }
}
