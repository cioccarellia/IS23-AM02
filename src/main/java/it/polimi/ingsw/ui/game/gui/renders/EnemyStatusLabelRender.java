package it.polimi.ingsw.ui.game.gui.renders;

import it.polimi.ingsw.model.player.PlayerSession;
import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EnemyStatusLabelRender {

    public static void renderEnemyStatusLabel(@NotNull Label enemyStatusLabel, @NotNull PlayerSession enemySession) {
        enemyStatusLabel.setText("Player game phase: " + enemySession.getPlayerCurrentGamePhase().toString().toLowerCase() +
                "\n\nConnection status: " + /* todo how do we deal with connection, since PlayerInfo seems useless outside of lobby !! add + once comment is removed */
                "\n\nObtained common goal cards: " + renderEnemyAchievedCommonGoalCards(enemySession) +
                "\n\n"
        );

        // has the enemy played their last turn already?
        if (enemySession.noMoreTurns) {
            enemyStatusLabel.setText(enemyStatusLabel.getText() + "They have played their last turn already");
        }
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
