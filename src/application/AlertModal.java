
/* 
 * Student ID: 2016026
 * Name: Sameeha Rahman
 */

package application;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.*;

public class AlertModal extends Stage {

	private String message;

	public AlertModal(String message) {

		this.message = message;

	}

	public void getAlert() {

		// button to close pop up
		Button button = new Button("OK");
		button.setOnAction(event -> AlertModal.this.close());

		// main pane
		BorderPane borderPane = new BorderPane();

		// setting padding and alignment
		borderPane.setPadding(new Insets(20));
		BorderPane.setAlignment(button, Pos.CENTER);
		borderPane.setBottom(button);

		Text text = new Text(message);

		// styling the text
		text.setStyle("-fx-font-size:1.5em;");
		text.setWrappingWidth(350);

		borderPane.setCenter(text);

		// set the test color
		text.setFill(Color.RED);
		setTitle("Error!");

		Scene scene = new Scene(borderPane, 400, 150);

		// link the style sheet
		scene.getStylesheets().add("application.css");

		// ad background color
		borderPane.getStyleClass().add("mainBack");

		// fix the size of the stage
		setResizable(false);
		setScene(scene);
		show();
	}

}
