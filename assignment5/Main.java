package assignment5;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Set;

public class Main extends Application {
    public static GridPane critterWorld;
    String critterMaking = "";
    @Override
    public void start(Stage primaryStage) throws Exception{
//		int numCols = Params.world_width;
//		int numRows = Params.world_height;
//		GridPane rootGrid = new GridPane();
//		for(int row = 0; row < numRows; row++) {
//			for(int col = 0; col < numCols; col++) {
//
//			}
//		}

        BorderPane screen = new BorderPane();
        HBox hbox = makeCommandsRow();
        HBox stepsRow = stepCommandsRow();
        GridPane controls = new GridPane();
        //screen.setTop(hbox);
        controls.add(hbox, 1, 0);
        controls.add(stepsRow, 1, 1);
        //controls.getColumnConstraints().add(new ColumnConstraints(800));
        screen.setTop(controls);
        //screen.setCenter(critterWorld);
        Scene scene = new Scene(screen, 1400, 700);
        //Parent root = FXMLLoader.load(getClass().getResource("../assignment5/sample.fxml"));
        primaryStage.setTitle("Critters World");
        critterWorld = new GridPane();
        critterWorld.setGridLinesVisible(true);
        final int numCols = Params.world_width;
        final int numRows = Params.world_height;
        for(int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / numCols);
            critterWorld.getColumnConstraints().add(colConst);
        }
        for(int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numRows);
            critterWorld.getRowConstraints().add(rowConst);
        }
        screen.setCenter(critterWorld);
        //primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public HBox stepCommandsRow() {
        HBox stepCommands = new HBox();
        stepCommands.setPadding(new Insets(15, 12, 14, 12));
        stepCommands.setSpacing(10);
        stepCommands.setStyle("-fx-background-color: #336699;");
        Button singleStepButton = new Button("Single Step");
        singleStepButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Critter.worldTimeStep();
            }
        });
        Label labelNumSteps = new Label("Number of time steps to simulate: ");
        labelNumSteps.setTextFill(Color.WHITE);
        TextField inputNumSteps = new TextField();
        Button generalStepButton = new Button("Step");
        generalStepButton.setOnAction(new EventHandler<ActionEvent>() {
            boolean failed = false;
            @Override
            public void handle(ActionEvent event) {
                try {
                    int userInNumSteps = Integer.parseInt(inputNumSteps.getText());
                    if(userInNumSteps < 0) {
                        inputNumSteps.setText("Negative steps not possible");
                    }
                    for(int i = 0; i < userInNumSteps; i++) {
                        Critter.worldTimeStep();
                    }
                    inputNumSteps.setText("");
                    inputNumSteps.setStyle("-fx-text-fill: black");
                } catch(Exception e) {
                    inputNumSteps.setText("Enter an Integer");
                    inputNumSteps.setStyle("-fx-text-fill: red");
                }
            }
        });
        stepCommands.getChildren().addAll(singleStepButton, labelNumSteps, inputNumSteps, generalStepButton);
        return stepCommands;
    }
    //HBox has all of the command options
    public HBox makeCommandsRow() {
        MenuItem menuItem1 = new MenuItem("Craig");
        MenuItem menuItem2 = new MenuItem("Algae");
        MenuItem menuItem3 = new MenuItem("Algaephobic Critter");
        MenuButton makeCritter = new MenuButton(("Choose Critter to Make"), null, menuItem1, menuItem2, menuItem3);
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 14, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");
        menuItem3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                makeCritter.setText("Algaephobic Critter");
                try {
                    //Critter.makeCritter("AlgaephobicCritter");
                }
                catch(Exception e) {
                    System.err.println("issue making AlgaephobicCritter");
                }
            }
        });
        menuItem2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                makeCritter.setText("Algae");
                try {
                    //Critter.makeCritter("Algae");
                }
                catch(Exception e) {
                    System.err.println("issue making algae");
                }
            }
        });
        menuItem1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                makeCritter.setText("Craig");
                try {
                    //Critter.makeCritter("Craig");
                } catch(Exception e) {
                    System.err.println("issue making Craig");
                }
            }
        });

        Label numCrittersToMake = new Label("Number of critters to make: ");
        numCrittersToMake.setTextFill(Color.WHITE);
        TextField numCritters = new TextField();
        Button makeButton = new Button("Make");
        makeButton.setOnAction(new EventHandler<ActionEvent>() {
            boolean failed = false;
            @Override
            public void handle(ActionEvent event) {
                try {
                    int userInNumCritters = Integer.parseInt(numCritters.getText());
                    if(userInNumCritters < 0) {
                        numCritters.setText("Negative critters not possible");
                    }
                    critterMaking = makeCritter.getText();
                    for(int i = 0; i < userInNumCritters; i++) {
                        try {
                            Critter.makeCritter(critterMaking);
                        } catch(Exception e) {
                            failed = true;
                            System.err.println("error making critter");
                            break;
                        }
                    }
                    numCritters.setText("");
                    makeCritter.setText("Choose Critter to Make");
                    if(failed) {
                        makeCritter.setText("Choose Valid Critter");
                        failed = false;
                    }
                    numCritters.setStyle("-fx-text-fill: black");
                    System.out.println(Critter.getInstances("Algae").size());
                } catch(Exception e) {
                    numCritters.setText("Enter an Integer");
                    numCritters.setStyle("-fx-text-fill: red");
                }
            }
        });
        Button quit = new Button("Quit");
        hbox.getChildren().addAll(makeCritter, numCrittersToMake, numCritters, makeButton, quit);
        return hbox;
    }



    public static void main(String[] args) {
        launch(args);
    }
}
