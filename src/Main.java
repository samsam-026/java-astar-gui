
/* 
 * Student ID: 2016026
 * Name: Sameeha Rahman
 */

import java.text.DecimalFormat;
import java.util.ArrayList;

import application.*;
import controller.GUIController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.stage.Stage;
import model.Grid;
import model.GridPiece;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Main extends Application {

	TextField startXTxt = new TextField();
	TextField startYTxt = new TextField();
	TextField endXTxt = new TextField();
	TextField endYTxt = new TextField();

	boolean refreshClicked = true;

	// number of clicks on the grid
	int clicks;

	// if it is a colored grid
	boolean coloured = false;

	double recHeight = 19.5;
	double recWidth = 19.5;

	GridPane grid;
	BorderPane root;

	ToggleGroup radioGroup = new ToggleGroup();

	GUIController guiController;
	Grid gridview;
	GridPiece[][] array;
	Button gridTypeBtn;

	GridPane bottomPane = new GridPane();
	Stopwatch timer;
	Scene scene;
	Stage primaryStage = new Stage();

	@Override
	public void start(Stage primaryStage) {
		try {

			this.primaryStage.setMinHeight(600);
			this.primaryStage.setMinWidth(550);

			// create and show the start scene
			StartScene startScene = new StartScene(this.primaryStage);

			// get the play button
			Button play = startScene.getPlayButton();

			// add functionality to the play button to call the game view method
			play.setOnAction(event -> mainView());

			// close all child stages if the main stage is closed
			this.primaryStage.setOnHidden(event -> Platform.exit());

			this.primaryStage.setTitle("PathFinder!");
			this.primaryStage.setScene(startScene.getStartScene());
			this.primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void mainView() {
		/*
		 * change to 1, 2 or 4. 1 for a 20*20 grid, 2 for a 40*40 grid and 4 for a 80*80
		 * grid
		 */
		int gridSize = 1;

		grid = new GridPane();
		root = new BorderPane();

		// initialize controller and models
		this.guiController = new GUIController(gridSize);
		this.gridview = this.guiController.getGridview();
		array = this.gridview.getGrid();

		recHeight = recHeight / gridSize;
		recWidth = recWidth / gridSize;

		GridPane imageGrid = new GridPane();

		// add landscape image
		ImageView backdrop = new ImageView(new Image("file:src/images/grid.PNG"));

		GridPane.setConstraints(backdrop, 0, 0, 20, 20, HPos.CENTER, VPos.CENTER);

		imageGrid.getChildren().add(backdrop);

		loadGridView();

		GridPane leftPane = new GridPane();

		GridPane topPane = new GridPane();

		// create and initialize buttons
		Button goBtn = new Button("Go ->");

		goBtn.setOnAction(event -> goBtnAction());

		Button refreshBtn = new Button("Refresh");

		refreshBtn.setOnAction(event -> refreshBtnAction());

		gridTypeBtn = new Button("Grid");

		gridTypeBtn.setOnAction(event -> gridTypeBtnAction());

		//set pane alignments
		grid.setAlignment(Pos.CENTER);
		grid.setPadding(new Insets(10));

		topPane.setAlignment(Pos.CENTER);
		topPane.setHgap(10);
		topPane.setVgap(10);
		topPane.setPadding(new Insets(10));

		leftPane.setAlignment(Pos.CENTER);
		leftPane.setHgap(10);
		leftPane.setVgap(10);
		leftPane.setPadding(new Insets(10));

		bottomPane.setAlignment(Pos.TOP_LEFT);
		bottomPane.setHgap(10);
		bottomPane.setVgap(10);
		bottomPane.setPadding(new Insets(10));

		Label pointA = new Label("Start Point:");
		Label pointB = new Label("End Point");

		// create and initialize radio buttons
		RadioButton manhattanRadio = new RadioButton("Manhattan");
		RadioButton chebychevRadio = new RadioButton("Chebyshev");
		RadioButton euclideanRadio = new RadioButton("Euclidean");
		RadioButton noneRadio = new RadioButton("None");

		manhattanRadio.setSelected(true);

		// add all radio buttons to a toggle group
		manhattanRadio.setToggleGroup(radioGroup);
		chebychevRadio.setToggleGroup(radioGroup);
		euclideanRadio.setToggleGroup(radioGroup);
		noneRadio.setToggleGroup(radioGroup);

		// give constraints for the radio button
		GridPane.setConstraints(manhattanRadio, 0, 0, 1, 1, HPos.LEFT, VPos.CENTER);
		GridPane.setConstraints(chebychevRadio, 0, 1, 1, 1, HPos.LEFT, VPos.CENTER);
		GridPane.setConstraints(euclideanRadio, 0, 2, 1, 1, HPos.LEFT, VPos.CENTER);
		GridPane.setConstraints(noneRadio, 0, 3, 1, 1, HPos.LEFT, VPos.CENTER);
		GridPane.setConstraints(goBtn, 0, 10, 1, 1, HPos.CENTER, VPos.CENTER);
		GridPane.setConstraints(refreshBtn, 0, 12, 1, 1, HPos.CENTER, VPos.CENTER);
		GridPane.setConstraints(gridTypeBtn, 0, 14, 1, 1, HPos.CENTER, VPos.CENTER);

		Label lblStartX = new Label("X");
		Label lblStartY = new Label("Y");
		Label lblEndX = new Label("X");
		Label lblEndY = new Label("Y");

		// add styling to the text fields
		startXTxt.getStyleClass().add("textfield");
		startYTxt.getStyleClass().add("textfield");
		endXTxt.getStyleClass().add("textfield");
		endYTxt.getStyleClass().add("textfield");

		// give constraints for the labels and text fields
		GridPane.setConstraints(lblStartX, 1, 0, 1, 1, HPos.CENTER, VPos.CENTER);
		GridPane.setConstraints(lblStartY, 2, 0, 1, 1, HPos.CENTER, VPos.CENTER);
		GridPane.setConstraints(lblEndX, 4, 0, 1, 1, HPos.CENTER, VPos.CENTER);
		GridPane.setConstraints(lblEndY, 5, 0, 1, 1, HPos.CENTER, VPos.CENTER);
		GridPane.setConstraints(pointA, 0, 1, 1, 1, HPos.CENTER, VPos.CENTER);
		GridPane.setConstraints(startXTxt, 1, 1, 1, 1, HPos.CENTER, VPos.CENTER);
		GridPane.setConstraints(startYTxt, 2, 1, 1, 1, HPos.CENTER, VPos.CENTER);
		GridPane.setConstraints(pointB, 3, 1, 1, 1, HPos.CENTER, VPos.CENTER);
		GridPane.setConstraints(endXTxt, 4, 1, 1, 1, HPos.CENTER, VPos.CENTER);
		GridPane.setConstraints(endYTxt, 5, 1, 1, 1, HPos.CENTER, VPos.CENTER);

		bottomPane.add(new Label("Results:"), 0, 0);

		// add all children to the respective panes
		topPane.getChildren().addAll(lblEndX, lblEndY, lblStartX, lblStartY, pointA, startXTxt, startYTxt, pointB,
				endXTxt, endYTxt);

		leftPane.getChildren().addAll(manhattanRadio, chebychevRadio, euclideanRadio, noneRadio, goBtn, refreshBtn,
				gridTypeBtn);

		root.getStyleClass().add("mainBack");

		// add grid on top of image
		StackPane mainStack = new StackPane(grid);

		GridPane.setConstraints(mainStack, 0, 0, 20, 20, HPos.CENTER, VPos.CENTER);

		imageGrid.getChildren().add(mainStack);

		root.setBottom(bottomPane);
		root.setLeft(leftPane);
		root.setTop(topPane);
		root.setCenter(imageGrid);
		scene = new Scene(root, 550, 600);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		this.primaryStage.setScene(scene);
	}

	private void gridTypeBtnAction() {
		
		// if the grid has colored rectangles
		if (coloured) {
			
			// change to the image view
			coloured = false;
			GridPane imageGrid = new GridPane();

			// add the image and constraints
			ImageView backdrop = new ImageView(new Image("file:src/images/grid.PNG"));

			GridPane.setConstraints(backdrop, 0, 0, 20, 20, HPos.CENTER, VPos.CENTER);

			// put image in pane
			imageGrid.getChildren().add(backdrop);

			grid.getChildren().clear();

			// load new grid and add over image
			loadGridView();
			StackPane mainStack = new StackPane(grid);

			GridPane.setConstraints(mainStack, 0, 0, 20, 20, HPos.CENTER, VPos.CENTER);
			imageGrid.getChildren().add(mainStack);

			gridTypeBtn.setText("Grid");

			// set image grid to center
			root.setCenter(imageGrid);
			
			// if no colored grid
		} else {
			
			//change to grid view 
			coloured = true;

			// clear the grid
			grid.getChildren().clear();

			//oad it again
			loadGridView();

			gridTypeBtn.setText("Image");

			// set grid to center
			root.setCenter(grid);
		}
	}

	private void refreshBtnAction() {

		// clear the grid pane of all elements
		grid.getChildren().clear();

		// clear the results pane
		bottomPane.getChildren().clear();

		bottomPane.add(new Label("Results:"), 0, 0);

		// load the grid
		loadGridView();

		refreshClicked = true;

	}

	public void goBtnAction() {

		// if the view is refreshed
		if (refreshClicked) {

			// if there are values in the text fields
			if (!startXTxt.getText().equals("") && !startYTxt.getText().equals("") && !endXTxt.getText().equals("")
					&& !endYTxt.getText().equals("")) {

				try {

					// if these values are integers
					int startX = Integer.parseInt(this.startXTxt.getText());
					int startY = Integer.parseInt(this.startYTxt.getText());
					int endX = Integer.parseInt(this.endXTxt.getText());
					int endY = Integer.parseInt(this.endYTxt.getText());

					// if these integers are within the range of 0 to the length of one side of the
					// grid
					if (startX < array.length && startX >= 0 && startY < array.length && startY >= 0
							&& endX < array.length && endX >= 0 && endY < array.length && endY >= 0) {

						// get the source and destination nodes
						GridPiece startNode = array[startY][startX];
						GridPiece endNode = array[endY][endX];

						// if source is not blocked
						if (startNode.getWeight() != 0) {

							// if destination is not blocked
							if (endNode.getWeight() != 0) {

								// refresh needs to be clicked before the next run
								refreshClicked = false;

								// get the heuristic name
								RadioButton selectedRadioButton = (RadioButton) radioGroup.getSelectedToggle();
								String heuType = selectedRadioButton.getText();

								// start timer
								timer = new Stopwatch();

								// get the path returned from the path finding algorithm
								ArrayList<GridPiece> path = guiController.search(startNode, endNode, heuType);

								drawPath(path);
							} else {
								// alert user destination is blocked
								AlertModal alert = new AlertModal("The destination node is blocked");
								alert.getAlert();
							}
						} else {
							// alert user source is blocked
							AlertModal alert = new AlertModal("The source node is blocked!");
							alert.getAlert();
						}

					} else {
						// alert user to enter correct values
						AlertModal alert = new AlertModal(
								"Please enter numbers from 0 to " + (array.length - 1) + " only!");
						alert.getAlert();
					}

				} catch (NumberFormatException e) {
					// alert user to enter correct values
					AlertModal alert = new AlertModal(
							"Please enter only numbers from 0 to " + (array.length - 1) + "!");
					alert.getAlert();
				}

			} else {
				// alert user to enter all values
				AlertModal alert = new AlertModal("Please enter all fields!");
				alert.getAlert();
			}
		} else {
			// alert user to refresh grid before use
			AlertModal alert = new AlertModal("Please click the refresh button before running again!");
			alert.getAlert();
		}
	}

	private void drawPath(ArrayList<GridPiece> path) {

		double totalCost = 0;

		// display the source position
		bottomPane.add(
				new Label(
						"Source (pink): " + path.get(path.size() - 1).getY() + ", " + path.get(path.size() - 1).getX()),
				1, 0);

		// get the final cost of the source node
		totalCost += path.get(path.size() - 1).getWeight();

		// add a rectangle on the grid in pink to denote the source position
		Rectangle sourecRec = new Rectangle();
		sourecRec.setFill(Color.DEEPPINK);
		sourecRec.setHeight(recHeight);
		sourecRec.setWidth(recWidth);

		grid.add(sourecRec, path.get(path.size() - 1).getY(), path.get(path.size() - 1).getX());

		// for each node in the path list which is neither the source or destination
		for (int i = path.size() - 2; i > 0; i--) {

			// get the final cost of all
			totalCost += path.get(i).getWeight();

			// add a rectangle on the grid in orange to denote the node path taken to the
			// destination

			Rectangle recAnswer = new Rectangle();
			recAnswer.setFill(Color.ORANGERED);
			recAnswer.setHeight(recHeight);
			recAnswer.setWidth(recWidth);

			grid.add(recAnswer, path.get(i).getY(), path.get(i).getX());

		}

		bottomPane.add(new Label("Destination (purple): " + path.get(0).getY() + ", " + path.get(0).getX()), 3, 0);
		totalCost += path.get(0).getWeight();

		bottomPane.add(new Label("Path in orange"), 2, 0);

		DecimalFormat df = new DecimalFormat("#.00");

		bottomPane.add(new Label("Estimated Cost: " + df.format(path.get(path.size() - 1).getHeuristic())), 1, 1);

		bottomPane.add(new Label("Total Cost: " + df.format(totalCost)), 2, 1);

		bottomPane.add(new Label("Time Taken: " + timer.elapsedTime()), 3, 1);

		// add a rectangle on the grid in purple to denote the destination position
		Rectangle destRec = new Rectangle();
		destRec.setFill(Color.PURPLE);
		destRec.setHeight(recHeight);
		destRec.setWidth(recWidth);

		StackPane destStack = new StackPane();

		destStack.getStyleClass().add("dest");
		grid.add(destRec, path.get(0).getY(), path.get(0).getX());
	}

	private void loadGridView() {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				Rectangle rec = new Rectangle();
				rec.setHeight(recHeight);
				rec.setWidth(recWidth);

				// if grid vies is chosen
				if (coloured) {
					// ad colors to the rectangles
					switch ((int) array[i][j].getWeight()) {
					case 0:
						rec.setFill(Color.BLUE);
						break;
					case 1:
						rec.setFill(Color.CHARTREUSE);
						break;
					case 2:
						rec.setFill(Color.LIMEGREEN);
						break;
					case 3:
						rec.setFill(Color.FORESTGREEN);
						break;
					case 4:
						rec.setFill(Color.DARKGREY);
						break;
					}
				} else {
					// for image view, keep them transparent
					rec.setFill(Color.TRANSPARENT);
				}
				int rowIndex = i;
				int colIndex = j;
				
				// add a click listener to each rectangle if they are unblocked
				rec.setOnMouseClicked(e -> {
					if (array[rowIndex][colIndex].getWeight() != 0) {
						clicks++;
						//set the destination and start nodes according to the number of clicks on the grid
						if (clicks % 2 == 0) {
							endXTxt.setText("" + colIndex);
							endYTxt.setText("" + rowIndex);
						} else {
							startXTxt.setText("" + colIndex);
							startYTxt.setText("" + rowIndex);
						}
					}
				});

				// add rectangle to the grid
				grid.add(rec, j, i);
			}
		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}
