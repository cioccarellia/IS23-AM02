package it.polimi.ingsw.ui.Ranking;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.ui.game.GameGateway;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;


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
    @FXML
    public Label Total1;
    @FXML
    public Label Total2;
    @FXML
    public Label Total3;
    @FXML
    public Label Total4;
    @FXML
    public Label Token1;
    @FXML
    public Label Token2;
    @FXML
    public Label Token3;
    @FXML
    public Label Token4;
    @FXML
    public Label Bookshelf1;
    @FXML
    public Label Bookshelf2;
    @FXML
    public Label Bookshelf3;
    @FXML
    public Label Bookshelf4;
    @FXML
    public Label Personal1;
    @FXML
    public Label Personal2;
    @FXML
    public Label Personal3;
    @FXML
    public Label Personal4;

    private final Game model;

    public RankingController(Game game) {
        this.model = game;
    }

    @Override
    public void start(Stage primaryStage) {

        //Setting winner player
        winnerPlayer.setText(model.getRankings().get(0).username());

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
