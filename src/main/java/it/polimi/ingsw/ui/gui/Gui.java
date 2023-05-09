package it.polimi.ingsw.ui.gui;


import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Gui extends Application {


    @Override
    public void start(Stage primaryStage) {
        String file = "file:///Path-To-Your-Image/javafx-logo.png";
        Image image = new Image(file);

        // Create the Cursor
        Cursor myCur = Cursor.cursor(file);

        // Create the ImageView
        ImageView imageView = new ImageView();

        // Add the Image to the ImageView
        imageView.setImage(image);

        // Create the VBox
        VBox root = new VBox();

        // Set the width and height of the VBox
        root.setMinWidth(300);
        root.setMinHeight(200);

        // Add the ImageView to the VBox
        root.getChildren().add(imageView);

        // Set the Style-properties of the VBox
        root.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");

        // Create the Scene
        Scene scene = new Scene();
        // Set the Title of the Stage
        primaryStage.setTitle("Setting the Cursor for a Scene");
        // Display the Stage
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }

}

