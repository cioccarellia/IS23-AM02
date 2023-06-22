package it.polimi.ingsw.ui.game.gui.renders;

import it.polimi.ingsw.model.game.score.PlayerScore;
import it.polimi.ingsw.utils.javafx.PaneViewUtil;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static it.polimi.ingsw.ui.game.gui.utils.GameUiConstants.RANKING_HEIGHT;
import static it.polimi.ingsw.ui.game.gui.utils.GameUiConstants.RANKING_WIDTH;

public class FinalRankingRender {

    public static final int TABLE_COLUMNS = 5;
    public static final int GAP = 40;
    public static final int BIG_MARGIN = 50;
    public static final int FONT_SIZE = 25;
    public static final int SMALL_MARGIN = 20;


    public static void renderRanking(List<PlayerScore> playersRanking) {
        //set up dialog
        Dialog rankingWindow = new Dialog();
        BackgroundImage background = new BackgroundImage(
                new Image("/img/misc/wood_background.jpg", RANKING_WIDTH, RANKING_HEIGHT, false, false),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        rankingWindow.setTitle("Game Over - Ranking");
        rankingWindow.getDialogPane().setMinWidth(RANKING_WIDTH);
        rankingWindow.getDialogPane().setMinHeight(RANKING_HEIGHT);
        rankingWindow.getDialogPane().setBackground(new Background(background));
        rankingWindow.setHeaderText(null);

        // set up dialog content: ImageView of MyShelfie, Winner label, ranking GridPane
        // ImageView
        ImageView myshelfieImageView = new ImageView(new Image("img/publisher_material/title_2000x618px.png"));

        // Winner label
        Label winnerLabel = new Label("The winner is: " + playersRanking.get(0).username());
        winnerLabel.setFont(Font.font("Monospaced", FontWeight.BOLD, FontPosture.REGULAR, FONT_SIZE * 2));
        winnerLabel.setTextFill(Color.valueOf("#900000"));

        // ranking GridPane
        GridPane rankingGridPane = new GridPane();

        settingUpRankingGridPane(rankingGridPane, playersRanking);

        // put everything in VBox
        VBox rankingVBox = new VBox();
        rankingVBox.setAlignment(Pos.TOP_CENTER);

        rankingVBox.getChildren().add(myshelfieImageView);
        rankingVBox.getChildren().add(winnerLabel);
        rankingVBox.getChildren().add(rankingGridPane);

        //insets(top, right, bottom, left)
        VBox.setMargin(myshelfieImageView, new Insets(SMALL_MARGIN, 0, BIG_MARGIN, 0));
        VBox.setMargin(winnerLabel, new Insets(BIG_MARGIN, 0, BIG_MARGIN, 0));
        VBox.setMargin(rankingGridPane, new Insets(BIG_MARGIN, 0, SMALL_MARGIN, 0));

        // add VBox to dialog
        rankingWindow.getDialogPane().setContent(rankingVBox);

        rankingWindow.showAndWait();
    }

    public static void settingUpRankingGridPane(GridPane grid, List<PlayerScore> playersRanking) {
        grid.setAlignment(Pos.CENTER);
        //grid.setGridLinesVisible(true);
        grid.setHgap(GAP);

        for (int i = 0; i < playersRanking.size() + 1; i++) {
            for (int j = 0; j < TABLE_COLUMNS; j++) {
                grid.add(new Label(), j, i);
            }
        }

        Node[][] gridPaneNodes = PaneViewUtil.matrixify(grid, playersRanking.size() + 1, TABLE_COLUMNS);

        for (int i = 0; i < playersRanking.size() + 1; i++) {
            Label labelPlayers = (Label) gridPaneNodes[i][0];
            Label labelTokenPoints = (Label) gridPaneNodes[i][1];
            Label labelBookshelfPoints = (Label) gridPaneNodes[i][2];
            Label labelPersonalGCPoints = (Label) gridPaneNodes[i][3];
            Label labelTotalPoints = (Label) gridPaneNodes[i][4];

            labelPlayers.setText(i == 0 ? "Players" : playersRanking.get(i - 1).username());
            labelTokenPoints.setText(i == 0 ? "Tokens" : String.valueOf(playersRanking.get(i - 1).getTokenPoints()));
            labelBookshelfPoints.setText(i == 0 ? "Bookshelf" : String.valueOf(playersRanking.get(i - 1).getBookshelfPoints()));
            labelPersonalGCPoints.setText(i == 0 ? "Personal goal card" : String.valueOf(playersRanking.get(i - 1).getPersonalGoalCardsPoints()));
            labelTotalPoints.setText(i == 0 ? "Total points" : String.valueOf(playersRanking.get(i - 1).total()));

            // set up font for each label
            if (i == 0) {
                settingUpFontWeightAndAlignmentForLabels(labelPlayers, labelTokenPoints, labelBookshelfPoints, labelPersonalGCPoints, labelTotalPoints);
            } else {
                settingUpFontAndAlignmentForLabels(labelPlayers, labelTokenPoints, labelBookshelfPoints, labelPersonalGCPoints, labelTotalPoints);
            }
        }
    }

    public static void settingUpFontAndAlignmentForLabels(@NotNull Label @NotNull ... labelList) {
        for (Label label : labelList) {
            label.setFont(Font.font("Monospaced", FontWeight.NORMAL, FontPosture.REGULAR, FONT_SIZE));
            label.setAlignment(Pos.CENTER);
        }
    }

    public static void settingUpFontWeightAndAlignmentForLabels(@NotNull Label @NotNull ... labelList) {
        for (Label label : labelList) {
            label.setFont(Font.font("Monospaced", FontWeight.BOLD, FontPosture.REGULAR, FONT_SIZE));
            label.setAlignment(Pos.CENTER_LEFT);
        }
    }
}
