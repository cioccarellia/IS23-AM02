package it.polimi.ingsw.ui.game.gui.renders;

import it.polimi.ingsw.app.model.PlayerInfo;
import it.polimi.ingsw.model.player.PlayerSession;
import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EnemyStatusLabelRender {

    public static void renderEnemyStatusLabel(@NotNull Label enemyStatusLabel, @NotNull PlayerSession enemySession, int points, PlayerInfo playerInfo) {
        StringBuilder labelText = new StringBuilder();

        if (playerInfo != null) {
            labelText.append("\n\nConnection status: ").append(playerInfo.status());
            if (playerInfo.isHost()) {
                labelText.append("This player is the host.\n");
            }
        }

        labelText.append("Player game phase: ").append(enemySession.getPlayerCurrentGamePhase().toString().toLowerCase());
        labelText.append("\n\nObtained common goal cards: ").append(renderEnemyAchievedCommonGoalCards(enemySession));
        labelText.append("\n\nCurrent points: ").append(points).append("\n\n");

        // has the enemy played their last turn already?
        if (enemySession.noMoreTurns) {
            labelText.append("They have played their last turn already");
        }

        enemyStatusLabel.setText(labelText.toString());
    }

    public static String renderEnemyAchievedCommonGoalCards(@NotNull PlayerSession enemySession) {
        List<String> enemyAchievedCommonGoalCards = enemySession.getAchievedCommonGoalCards().stream().map(CommonGoalCardNameRender::renderCommonGoalCardName).toList();

        switch (enemyAchievedCommonGoalCards.size()) {
            case 0 -> {
                return "none";
            }
            case 1 -> {
                return enemyAchievedCommonGoalCards.get(0);
            }
            case 2 -> {
                return enemyAchievedCommonGoalCards.get(0) + ", " + enemyAchievedCommonGoalCards.get(1);
            }
            default -> throw new IllegalStateException("Unexpected value: " + enemyAchievedCommonGoalCards.size());
        }
    }
}
