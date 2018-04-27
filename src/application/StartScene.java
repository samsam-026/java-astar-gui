
/* 
 * Student ID: 2016026
 * Name: Sameeha Rahman
 */

package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class StartScene {

	private Scene startScene;
	private Button start;

	public StartScene(Stage primaryStage) {

		BorderPane startPanel = new BorderPane();

		start = new Button("Start!!");

		startPanel.setPadding(new Insets(20));
		BorderPane.setAlignment(start, Pos.CENTER);

		// the image logo for the start page
		ImageView logo = new ImageView(new Image("file:src/images/pathfinding.png"));

		// set the fixed size of the image
		logo.setFitHeight(250);
		logo.setFitWidth(500);

		// add the nodes to the start panel
		startPanel.setCenter(logo);
		startPanel.setBottom(start);

		// make the scene as large as the parent stage
		startScene = new Scene(startPanel, primaryStage.getMinWidth(), primaryStage.getMinHeight());

		// link the style sheet
		startScene.getStylesheets().add("application.css");

		// add background color
		startPanel.getStyleClass().add("mainBack");

	}

	// get the start scene
	public Scene getStartScene() {
		return startScene;
	}

	// return the button so that the functionality can be added in the main program
	public Button getPlayButton() {
		return start;
	}

}
