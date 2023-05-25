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

Game model;

    @Override
    public void start(Stage primaryStage) {

    }


    public void onGameEnded() {
        //Setting winner player
        winnerPlayer.setText(model.getRankings().get(0).username());
        //setting ranking
        Player1.setText(model.getRankings().get(0).username());
        Player2.setText(model.getRankings().get(1).username());

        Total1.setText(String.valueOf(model.getRankings().get(0).total()));
        Total2.setText(String.valueOf(model.getRankings().get(1).total()));

        if(model.getRankings().size()==3) {
            //3 players game mode
            Player3.setText(model.getRankings().get(2).username());

            Player4.setText(" ");
            Total4.setText(" ");
            Token4.setText(" ");
            Bookshelf4.setText(" ");
            Personal4.setText(" ");

        }else if (model.getRankings().size()==4) {
            //4 players game mode
            Player3.setText(model.getRankings().get(2).username());
            Total3.setText(" ");
            Token3.setText(" ");
            Bookshelf3.setText(" ");
            Personal3.setText(" ");

            Player4.setText(model.getRankings().get(3).username());
        }

    }

}
