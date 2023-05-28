package it.polimi.ingsw.ui.ranking;

import it.polimi.ingsw.model.game.Game;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class RankingController extends Application {
    @FXML
    public Label winnerPlayer;
    @FXML
    public Label Player1;
    @FXML
    public Label Player2;
    @FXML
    public Label Player3;
    @FXML
    public Label Player4;
    public List<Label> players = List.of(Player1, Player2, Player3, Player4);
    @FXML
    public Label Total1;
    @FXML
    public Label Total2;
    @FXML
    public Label Total3;
    @FXML
    public Label Total4;
    public List<Label> totals = List.of(Total1, Total2, Total3, Total4);
    @FXML
    public Label Token1;
    @FXML
    public Label Token2;
    @FXML
    public Label Token3;
    @FXML
    public Label Token4;
    public List<Label> tokens = List.of(Token1, Token2, Token3, Token4);
    @FXML
    public Label Bookshelf1;
    @FXML
    public Label Bookshelf2;
    @FXML
    public Label Bookshelf3;
    @FXML
    public Label Bookshelf4;
    public List<Label> bookshelves = List.of(Bookshelf1, Bookshelf2, Bookshelf3, Bookshelf4);
    @FXML
    public Label Personal1;
    @FXML
    public Label Personal2;
    @FXML
    public Label Personal3;
    @FXML
    public Label Personal4;
    public List<Label> personals = List.of(Personal1, Personal2, Personal3, Personal4);

    private final Game model;

    public RankingController(Game game) {
        this.model = game;
    }

    @Override
    public void start(Stage rankingStage) {

        rankingStage.setMaximized(true);
        rankingStage.setFullScreen(false);
        rankingStage.setFullScreenExitHint("");

        // Load root layout from fxml file.

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/game/Ranking.fxml"));

        RankingController javaFxController = loader.getController();
        Parent rootLayout = null;

        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            System.exit(1);
        }

        rankingStage.setScene(rootLayout.getScene());
        rankingStage.setTitle("RANKING PAGE");
        rankingStage.show();

        //Setting winner player
        winnerPlayer.setText(model.getRankings().get(0).username());

        /*
        for (int i = 0; i < model.getSessions().size(); i++){
            players.get(i).setText(model.getRankings().get(i).username());
            totals.get(i).setText(String.valueOf(model.getRankings().get(i).total()));
            tokens.get(i).setText(String.valueOf(model.getRankings().get(i).getTokenPoints()));
            bookshelves.get(i).setText(String.valueOf(model.getRankings().get(i).getBookshelfPoints()));
            personals.get(i).setText(String.valueOf(model.getRankings().get(i).getPersonalGoalCardsPoints()));
        }
        */

        //setting ranking 2 players game mode
        Player1.setText(model.getRankings().get(0).username());
        Total1.setText(String.valueOf(model.getRankings().get(0).total()));
        Token1.setText(String.valueOf(model.getRankings().get(0).getTokenPoints()));
        Bookshelf1.setText(String.valueOf(model.getRankings().get(0).getBookshelfPoints()));
        Personal1.setText(String.valueOf(model.getRankings().get(0).getPersonalGoalCardsPoints()));

        Player2.setText(model.getRankings().get(1).username());
        Total2.setText(String.valueOf(model.getRankings().get(1).total()));
        Token2.setText(String.valueOf(model.getRankings().get(1).getTokenPoints()));
        Bookshelf2.setText(String.valueOf(model.getRankings().get(1).getBookshelfPoints()));
        Personal2.setText(String.valueOf(model.getRankings().get(1).getPersonalGoalCardsPoints()));

        //Player 3
        Player3.setText(" ");
        Total3.setText(" ");
        Token3.setText(" ");
        Bookshelf3.setText(" ");
        Personal3.setText(" ");

        //Player 4
        Player4.setText(" ");
        Total4.setText(" ");
        Token4.setText(" ");
        Bookshelf4.setText(" ");
        Personal4.setText(" ");


        if (model.getRankings().size() == 3) {
            //3 players game mode

            //Player 3
            Player3.setText(model.getRankings().get(2).username());
            Total3.setText(String.valueOf(model.getRankings().get(2).total()));
            Token3.setText(String.valueOf(model.getRankings().get(2).getTokenPoints()));
            Bookshelf3.setText(String.valueOf(model.getRankings().get(2).getBookshelfPoints()));
            Personal3.setText(String.valueOf(model.getRankings().get(2).getPersonalGoalCardsPoints()));

            //Player 4
            Player4.setText(" ");
            Total4.setText(" ");
            Token4.setText(" ");
            Bookshelf4.setText(" ");
            Personal4.setText(" ");

        } else if (model.getRankings().size() == 4) {
            //4 players game mode

            //Player 3
            Player3.setText(model.getRankings().get(2).username());
            Total3.setText(String.valueOf(model.getRankings().get(2).total()));
            Token3.setText(String.valueOf(model.getRankings().get(2).getTokenPoints()));
            Bookshelf3.setText(String.valueOf(model.getRankings().get(2).getBookshelfPoints()));
            Personal3.setText(String.valueOf(model.getRankings().get(2).getPersonalGoalCardsPoints()));

            //Player 4
            Player4.setText(model.getRankings().get(3).username());
            Total4.setText(String.valueOf(model.getRankings().get(3).total()));
            Token4.setText(String.valueOf(model.getRankings().get(3).getTokenPoints()));
            Bookshelf4.setText(String.valueOf(model.getRankings().get(3).getBookshelfPoints()));
            Personal4.setText(String.valueOf(model.getRankings().get(3).getPersonalGoalCardsPoints()));

        }

    }

}
