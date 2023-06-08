package it.polimi.ingsw.ui.game.gui.utils;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.cards.common.CommonGoalCard;
import it.polimi.ingsw.model.cards.common.CommonGoalCardIdentifier;
import it.polimi.ingsw.model.config.logic.LogicConfiguration;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.goal.Token;
import it.polimi.ingsw.model.player.PlayerSession;
import it.polimi.ingsw.model.player.selection.PlayerTileSelection;
import it.polimi.ingsw.ui.game.gui.renders.CommonGoalCardDescriptionRender;
import it.polimi.ingsw.ui.game.gui.renders.CommonGoalCardRender;
import it.polimi.ingsw.ui.game.gui.renders.TokenRender;
import it.polimi.ingsw.ui.game.gui.resources.ResourcePathConstants;
import javafx.scene.control.Label;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public class GuiGameControllerUtils {
    private static final int maxTokensPerPlayer = 3;
    private static final int commonGoalCardsAmount = LogicConfiguration.getInstance().commonGoalCardAmount();
    private static final int maxSelectionSize = LogicConfiguration.getInstance().maxSelectionSize();


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
            setLightingEffect(selectedTiles.get(i));
        }

    }

    public static void setLightingEffect(ImageView selectedTile) {
        Lighting effect = new Lighting();

        selectedTile.setEffect(effect);
    }

}
