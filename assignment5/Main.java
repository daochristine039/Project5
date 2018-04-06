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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Main extends Application {
    public static BorderPane screen;
    public static GridPane critterWorld;
    String critterToGetStatsFor = "";
    String critterMaking = "";
    String statsResultDisplay = "";

    /**
     * start is where the program truly begins execution
     * @param primaryStage stage for program
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        screen = new BorderPane(); //entire simulation in BorderPane
        Scene scene = new Scene(screen, 1400, 700);
        HBox makeRow = makeCommandsRow();
        HBox stepsRow = stepCommandsRow();
        GridPane controls = new GridPane();    //controls for step and make are at top
        controls.add(makeRow, 1, 0);
        controls.add(stepsRow, 1, 1);
        screen.setTop(controls);   //set top to control panel
        primaryStage.setTitle("Critters World");
        critterWorld = getCritterWorld();
        screen.setCenter(critterWorld);
        GridPane statsDisplay = getStatsDisplay();
        HBox seedBox = getSeedBox();
        statsDisplay.add(seedBox, 1, 4);
        screen.setLeft(statsDisplay);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * getSeedBox builds the component that allows user to input seed
     * @return HBox with seed command prompts
     */
    public HBox getSeedBox() {
        Label seedLabel = new Label("Enter Seed for Sequence: ");
        seedLabel.setTextFill(Color.WHITE);
        TextField inputSeed = new TextField();
        Button generalStepButton = new Button("Seed");
        generalStepButton.setOnAction(new EventHandler<ActionEvent>() {
            boolean failed = false;
            @Override
            public void handle(ActionEvent event) {
                try {
                    int userInSeed = Integer.parseInt(inputSeed.getText());
                    Critter.setSeed(userInSeed);
                } catch(Exception e) {
                    inputSeed.setText("Enter an Integer");
                    inputSeed.setStyle("-fx-text-fill: red");
                }
            }
        });
        HBox seedBox = new HBox();
        seedBox.setPadding(new Insets(15, 12, 14, 12));    //general formatting
        seedBox.setSpacing(10);
        seedBox.setStyle("-fx-background-color: #336699;");
        seedBox.getChildren().addAll(seedLabel, inputSeed, generalStepButton); //providing for seed functionality
        return seedBox;
    }

    /**
     * getStatsDisplay builds the component that allows user to request stats
     * @return GridPane with stats components on display
     */
    public GridPane getStatsDisplay() {
        GridPane statsDisplay = new GridPane();
        critterToGetStatsFor = "";
        Label statsLabel = new Label("Show me the stats for: ");   //Critter options to get stats of
        statsLabel.setTextFill(Color.WHITE);
        MenuItem menuItem1 = new MenuItem("Craig");
        MenuItem menuItem2 = new MenuItem("Algae");
        MenuItem menuItem3 = new MenuItem("Algaephobic Critter");
        MenuItem menuItem4 = new MenuItem("Tragic Critter");
        MenuItem menuItem5 = new MenuItem("Critter1");
        MenuItem menuItem6 = new MenuItem("Critter2");
        MenuItem menuItem7 = new MenuItem("Critter3");
        MenuItem menuItem8 = new MenuItem("Critter4");
        MenuButton statsRequestMenu = new MenuButton(("Choose Critter"), null, menuItem1, menuItem2, menuItem3, menuItem4, menuItem5, menuItem6, menuItem7, menuItem8);
        statsDisplay = new GridPane();
        menuItem8.setOnAction(new EventHandler<ActionEvent>() {    //set proper values depending on user selection
            @Override
            public void handle(ActionEvent event) {
                statsRequestMenu.setText("Critter4");
                critterToGetStatsFor = "Critter4";
            }
        });
        menuItem7.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                statsRequestMenu.setText("Critter3");
                critterToGetStatsFor = "Critter3";
            }
        });
        menuItem6.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                statsRequestMenu.setText("Critter2");
                critterToGetStatsFor = "Critter2";
            }
        });
        menuItem5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                statsRequestMenu.setText("Critter1");
                critterToGetStatsFor = "Critter1";
            }
        });
        menuItem4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                statsRequestMenu.setText("Tragic Critter");
                try {
                    critterToGetStatsFor = "TragicCritter";
                } catch(Exception e) {
                    System.err.println("issue- stats Tragic Critter");
                }
            }
        });
        menuItem3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                statsRequestMenu.setText("Algaephobic Critter");
                try {
                    critterToGetStatsFor = "AlgaephobicCritter";
                }
                catch(Exception e) {
                    System.err.println("issue- stats Algaephobic Critter");
                }
            }
        });
        menuItem2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                statsRequestMenu.setText("Algae");
                try {
                    critterToGetStatsFor = "Algae";
                }
                catch(Exception e) {
                    System.err.println("issue making algae");
                }
            }
        });
        menuItem1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                statsRequestMenu.setText("Craig");
                try {
                    critterToGetStatsFor = "Craig";
                } catch(Exception e) {
                    System.err.println("issue making Craig");
                }
            }
        });
        Button getStatsButton = new Button("Get My Stats");
        Label statsResultsLabel = new Label("Stats Results: ");
        getStatsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    statsRequestMenu.setText("Choose Critter");
                    statsLabel.setText("Show me the stats for: ");
                    List<Critter> instances = Critter.getInstances(critterToGetStatsFor);  //getting stats for specified critter
                    statsResultDisplay = Critter.runStats(instances);
                    statsResultsLabel.setText(statsResultDisplay);
                } catch(Exception e) {
                    statsRequestMenu.setText("Choose Valid Critter");
                    System.err.println("unable to retrieve instances");
                }
            }
        });
        HBox statsRequestRow = new HBox();
        statsRequestRow.setPadding(new Insets(15, 12, 14, 12));
        statsRequestRow.setSpacing(10);
        statsRequestRow.setStyle("-fx-background-color: #336699;");
        statsRequestRow.getChildren().addAll(statsLabel, statsRequestMenu, getStatsButton);
        statsResultsLabel.setText(statsResultDisplay);
        HBox statsResultsBox = new HBox();
        statsResultsBox.getChildren().add(statsResultsLabel);
        statsDisplay.add(statsRequestRow, 1, 2);
        statsDisplay.add(statsResultsBox, 1, 3);
        Button quit = new Button("Quit");
        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        statsDisplay.add(quit, 1, 8);
        return statsDisplay;
    }

    /**
     * getCritterWorld returns new Critter World
     * @return new crittle world
     */
    public static GridPane getCritterWorld() {
        critterWorld = new GridPane();
        critterWorld.setGridLinesVisible(true);
        final int numCols = Params.world_width;
        final int numRows = Params.world_height;
        for(int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();  //set format for critterWorld
            colConst.setPercentWidth(100.0 / numCols);
            critterWorld.getColumnConstraints().add(colConst);
        }
        for(int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numRows);
            critterWorld.getRowConstraints().add(rowConst);
        }
        return critterWorld;
    }

    /**
     * make the step command options
     * @return HBox with step command options
     */
    public HBox stepCommandsRow() {
        HBox stepCommands = new HBox();
        stepCommands.setPadding(new Insets(15, 12, 14, 12));
        stepCommands.setSpacing(10);
        stepCommands.setStyle("-fx-background-color: #336699;");   //general formatting
        Button singleStepButton = new Button("Single Step");
        singleStepButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {    //single world time step
                Critter.worldTimeStep();
                Critter.displayWorld();
            }
        });
        Label labelNumSteps = new Label("Number of time steps to simulate: ");
        labelNumSteps.setTextFill(Color.WHITE);
        TextField inputNumSteps = new TextField();
        Button generalStepButton = new Button("Step");
        generalStepButton.setOnAction(new EventHandler<ActionEvent>() {    //cycle through proper number of steps
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
                    Critter.displayWorld();
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

    /**
     * makeCommandsRow makes the HBox with the making critter functionality
     * @return HBox that holds the make Critter command box
     */
    public HBox makeCommandsRow() {
        //ChoiceBox<String> critterOptions = new ChoiceBox<>();
        //        Set<Class<? extends Critter>> critterInstances = reflections.getSubTypesOf(Critter.class); REFLECTIONS is for automatically getting subclasses of critter
//    ArrayList<String> sortedCritters = new ArrayList<>();
//    //ChoiceBox<String> listOfCritters = new ChoiceBox<>();
//    for(Class c: critterInstances) {
//       String s = c.getName();
//       s = s.substring(s.indexOf(".") + 1);
//       sortedCritters.add(s);
//    }
//    Collections.sort(sortedCritters);
//    statsRequestMenu.getItems().addAll(sortedCritters);
        MenuItem menuItem1 = new MenuItem("Craig");    //Types of Critters to make
        MenuItem menuItem2 = new MenuItem("Algae");
        MenuItem menuItem3 = new MenuItem("Algaephobic Critter");
        MenuItem menuItem4 = new MenuItem("Tragic Critter");
        MenuItem menuItem5 = new MenuItem("Critter1");
        MenuItem menuItem6 = new MenuItem("Critter2");
        MenuItem menuItem7 = new MenuItem("Critter3");
        MenuItem menuItem8 = new MenuItem("Critter4");
        MenuButton makeCritter = new MenuButton(("Choose Critter to Make"), null, menuItem1, menuItem2, menuItem3, menuItem4, menuItem5, menuItem6, menuItem7, menuItem8);
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 14, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");
        menuItem8.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                makeCritter.setText("Critter4");
            }
        });
        menuItem7.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                makeCritter.setText("Critter3");
            }
        });
        menuItem6.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                makeCritter.setText("Critter2");
            }
        });
        menuItem5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                makeCritter.setText("Critter1");
            }
        });
        menuItem4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                makeCritter.setText("Tragic Critter");
            }
        });
        menuItem3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                makeCritter.setText("Algaephobic Critter");
            }
        });
        menuItem2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                makeCritter.setText("Algae");
            }
        });
        menuItem1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                makeCritter.setText("Craig");
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
                //Critter.displayWorld();
                try {
                    int userInNumCritters = Integer.parseInt(numCritters.getText());
                    if(userInNumCritters < 0) {
                        numCritters.setText("Negative critters not possible");
                    }
                    critterMaking = makeCritter.getText();
                    if(critterMaking.equals("Algaephobic Critter")) {
                        critterMaking = "AlgaephobicCritter";
                    }
                    if(critterMaking.equals("Tragic Critter")) {
                        critterMaking = "TragicCritter";
                    }
                    for(int i = 0; i < userInNumCritters; i++) {
                        try {
                            Critter.makeCritter(critterMaking);
                        } catch(Exception e) {
                            failed = true;
                            System.err.println("error making critter");
                            break;
                        }
                    }
                    Critter.displayWorld();  //ADDED
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
        hbox.getChildren().addAll(makeCritter, numCrittersToMake, numCritters, makeButton);
        return hbox;
    }

    /**
     * showWorld called to update world show
     * @param newGrid is the GridPane with updated critters and locations
     */
    public static void showWorld(GridPane newGrid){
        screen.setCenter(newGrid);
    }

    /**
     * All program execution starts from here
     */
    public static void main(String[] args) {
        launch(args);
    }
}