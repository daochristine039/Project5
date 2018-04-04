package assignment5;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javafx.application.*;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.scene.shape.*;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.layout.*;

import javafx.scene.text.*;

import javafx.scene.control.*;

public abstract class Critter {
	/* NEW FOR PROJECT 5 */
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}
	
	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background 
	 * 
	 * critters must override at least one of the following three methods, it is not 
	 * proper for critters to remain invisible in the view
	 * 
	 * If a critter only overrides the outline color, then it will look like a non-filled 
	 * shape, at least, that's the intent. You can edit these default methods however you 
	 * need to, but please preserve that intent as you implement them. 
	 */
	public javafx.scene.paint.Color viewColor() { 
		return javafx.scene.paint.Color.WHITE; 
	}
	
	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }
	
	public abstract CritterShape viewShape();

	//private static GridPane gridpane = new GridPane();
	
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	private static Map<Critter, Coord> oldPopulation = new HashMap<>();


	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	protected final String look(int direction, boolean steps) {
	    this.energy -= Params.look_energy_cost;
	    int thisX, thisY;

	    thisX = oldPopulation.get(this).getX();
	    thisY = oldPopulation.get(this).getY();

        if(!this.lookInvoked){
            thisX = oldPopulation.get(this).getX();
            thisY = oldPopulation.get(this).getY();
        } else if(this.lookInvoked){
            thisX = this.x_coord;
            thisY = this.y_coord;
        }

        if(!steps){
            switch(direction){
                case 0:
                    thisX += 1;
                    break;
                case 1:
                    thisX += 1;
                    thisY -= 1;
                    break;
                case 2:
                    thisY -= 1;
                    break;
                case 3:
                    thisX -= 1;
                    thisY -= 1;
                    break;
                case 4:
                    thisX -= 1;
                    break;
                case 5:
                    thisX -= 1;
                    thisY += 1;
                    break;
                case 6:
                    thisY += 1;
                    break;
                case 7:
                    thisX += 1;
                    thisY += 1;
                    break;
                default:
                    break;
            }
        } else{
            switch(direction){
                case 0:
                    thisX += 2;
                    break;
                case 1:
                    thisX += 2;
                    thisY -= 2;
                    break;
                case 2:
                    thisY -= 2;
                    break;
                case 3:
                    thisX -= 2;
                    thisY -= 2;
                    break;
                case 4:
                    thisX -= 2;
                    break;
                case 5:
                    thisX -= 2;
                    thisY += 2;
                    break;
                case 6:
                    thisY += 2;
                    break;
                case 7:
                    thisX += 2;
                    thisY += 2;
                    break;
                default:
                    break;
            }
        }

        if(!lookInvoked) {
            for (Critter Return : oldPopulation.keySet()) {
                if (Return.x_coord == thisX && Return.y_coord == thisY) {
                    return Return.toString();
                }
            }
        } else{
            for(Critter Return : population){
                if(Return.x_coord == thisX && Return.y_coord == thisY && Return.energy > 0){
                    return Return.toString();
                }
            }
        }

        return null;
	}
	
	/* rest is unchanged from Project 4 */
	
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;

	private boolean moved;
	private boolean lookInvoked;
	
	protected final void walk(int direction) {
        energy -= Params.walk_energy_cost;  //Remove the cost of energy for walking from the critter
        moved = true;                       //The critter moves


        switch(direction){
            case 0:
                x_coord += 1;
                break;
            case 1:
                x_coord += 1;
                y_coord -= 1;
                break;
            case 2:
                y_coord -= 1;
                break;
            case 3:
                x_coord -= 1;
                y_coord -= 1;
                break;
            case 4:
                x_coord -= 1;
                break;
            case 5:
                x_coord -= 1;
                y_coord += 1;
                break;
            case 6:
                y_coord += 1;
                break;
            case 7:
                x_coord += 1;
                y_coord += 1;
                break;
            default:
                break;
        }

        if (x_coord > Params.world_width - 1) {     //Update x_coord if off the top of the world
            x_coord -= Params.world_width;
        } else if (x_coord < 0) {
            x_coord += Params.world_width;
        }

        if (y_coord > Params.world_height - 1) {    //Update y_coord if off the top of the world
            y_coord -= Params.world_height;
        } else if (y_coord < 0) {
            y_coord += Params.world_height;
        }
    }
	
	protected final void run(int direction) {
        energy -= Params.run_energy_cost;   //Remove cost of energy from running
        moved = true;                       //Critter moves


        switch(direction){
            case 0:
                x_coord += 2;
                break;
            case 1:
                x_coord += 2;
                y_coord -= 2;
                break;
            case 2:
                y_coord -= 2;
                break;
            case 3:
                x_coord -= 2;
                y_coord -= 2;
                break;
            case 4:
                x_coord -= 2;
                break;
            case 5:
                x_coord -= 2;
                y_coord += 2;
                break;
            case 6:
                y_coord += 2;
                break;
            case 7:
                x_coord += 2;
                y_coord += 2;
                break;
            default:
                break;
        }

        if (x_coord > Params.world_width - 1) {     //Update x_coord if off the top of the world
            x_coord -= Params.world_width;
        } else if (x_coord < 0) {
            x_coord += Params.world_width;
        }

        if (y_coord > Params.world_height - 1) {    //Update y_coord if off the top of the world
            y_coord -= Params.world_height;
        } else if (y_coord < 0) {
            y_coord += Params.world_height;
        }

    }
	
	protected final void reproduce(Critter offspring, int direction) {}

	public abstract void doTimeStep();
	public abstract boolean fight(String opponent);
	
	
	public static void worldTimeStep() {
	    for(Critter current: population){
	        current.lookInvoked = false;
        }

	    for(Critter current : population){
	        oldPopulation.put(current, new Coord(current.x_coord, current.y_coord));
        }

        for (int i = 0; i < population.size(); i++) {
            population.get(i).doTimeStep();                //Run doTimeStep of each critter in population list
        }

        for (int i = 0; i < population.size(); ) {                 //Remove all dead critters
            if (population.get(i).energy <= 0) {
                population.remove(population.get(i));
            } else {
                i++;
            }
        }

        for(Critter current: population){
            current.lookInvoked = true;
        }

        for (int i = 0; i < population.size(); i++) {                     //Resolve encounters
            Critter fighter1 = population.get(i);                         //Holds one critter
            boolean fight1, fight2;                                       //Hold whether the critters want to fight or not
            int dice1 = 0, dice2 = 0;                                     //Hold the number thrown by dice when resolving a fight

            if (fighter1.energy <= 0) {                                   //If the fighter1 is dead,
                continue;                                                 //Skip iteration
            }

            for (int k = 0; k < population.size(); k++) {                 //Find the critter with the same coordinates as fighter1
                if (k != i) {                                             //If it's not the same index as fighter1,
                    Critter fighter2 = population.get(k);                 //Hold it into fighter2

                    if (fighter2.energy <= 0) {                           //If dead, skip iteration
                        continue;
                    }

                    //If they both have the same coordinates
                    if ((fighter1.x_coord == fighter2.x_coord) && (fighter1.y_coord == fighter2.y_coord)) {
                        fight1 = fighter1.fight(fighter2.toString());      //fight1, fight2 hold whether they want to fight or not
                        fight2 = fighter2.fight(fighter1.toString());

                        if (fighter1.energy <= 0) {                        //If dead after fight method is called, leave the loop
                            break;
                        }
                        if (fighter2.energy <= 0) {
                            continue;
                        }
                        //If the critters are still in the same position after they fight, resolve the fight
                        if ((fighter1.x_coord == fighter2.x_coord) && (fighter1.y_coord == fighter2.y_coord)) {
                            if (fight1) {                                           //If fighter1 wants to fight,
                                dice1 = Critter.getRandomInt(fighter1.energy);      //Get a random number up to fighter's energy level
                            }
                            if (fight2) {
                                dice2 = Critter.getRandomInt(fighter2.energy);
                            }

                            if (dice1 < dice2) {      //If fighter2 throws a bigger number,
                                fighter2.energy += (fighter1.energy / 2);   //Fighter2 gets 1/2 of fighter1's energy
                                fighter1.energy = 0;                        //Set fighter1's energy to 0 to indicate its death
                            } else {                  //If fighter1 throws a bigger number,
                                fighter1.energy += (fighter2.energy / 2);   //Fighter1 gets 1/2 of fighter2's energy
                                fighter2.energy = 0;                        //Set fighter2's energy to 0 to indicate its death
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < population.size(); i++) {              //Remove energy cost of resting for each critter
            population.get(i).energy -= Params.rest_energy_cost;
        }

        for (int i = 0; i < population.size(); ) {                 //Remove all dead critters
            if (population.get(i).energy <= 0) {
                population.remove(population.get(i));
            } else {
                i++;
            }
        }

        for (int i = 0; i < population.size(); i++) {             //Set moved back to false
            population.get(i).moved = false;
        }

        for (int i = 0; i < population.size(); i++) {             //Set moved back to false
            population.get(i).lookInvoked = false;
        }

        for (int i = 0; i < Params.refresh_algae_count; i++) {    //Create new Algae critters as many times as the refresh_algae_count
            try {
                makeCritter("Algae");
            }catch(InvalidCritterException event){
                System.out.println("Invalid critter exception");
            }
        }

        population.addAll(babies);              //Add all the babies to the population
        babies.clear();                         //Clear all the babies from babies list
        oldPopulation.clear();

    }
	
	/*public static void displayWorld(Object pane) {

    }
    */

	/* Alternate displayWorld, where you use Main.<pane> to reach into your
	   display component.
	   // public static void displayWorld() {}
	*/
    public static void displayWorld() {
        Main.critterWorld.getChildren().clear();
        //gridpane.setGridLinesVisible(true);
        //Scene newScene = new Scene();


        /*for(int i = 0; i < Params.world_width; i++){
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(Params.world_width);
            gridpane.getColumnConstraints().add(col);
        }

        for(int i = 0; i < Params.world_height; i++){
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(Params.world_height);                   //Height or width?
            gridpane.getRowConstraints().add(row);
        }
        */

        //gridpane.getColumnConstraints().add(new ColumnConstraints(Params.world_width));
        //gridpane.getRowConstraints().add(new RowConstraints(Params.world_height));

        /*NumberBinding minSide = Bindings
                .min(Main.critterWorld.heightProperty(), Main.critterWorld.widthProperty())
                .divide(population.size());
        */
        int scaling = Math.min(Params.world_width / Params.world_height, Params.world_width / Params.world_height);

        for(int i = 0; i < population.size(); i++){         //Set dimensions for each shape
            switch(population.get(i).viewShape()){
                case CIRCLE:
                    Circle c = new Circle();
                    c.radiusProperty().bind((Main.critterWorld.widthProperty().divide(Params.world_width)).divide(2));
                    // ??
                    // ??
                    // ??
                    c.setFill(population.get(i).viewFillColor());
                    c.setStroke(population.get(i).viewOutlineColor());
                    c.setStrokeWidth(3);
                    Main.critterWorld.add(c, population.get(i).x_coord, population.get(i).y_coord);
                    break;
                case DIAMOND:
                    Polygon d1 = new Polygon();
                    d1.getPoints().addAll(
                            (double)scaling/2, 0.0,
                            0.0, (double) scaling/2,
                            (double) scaling/2, (double) scaling,
                            (double) scaling, (double) scaling/2
                    );
                    d1.setFill(population.get(i).viewFillColor());
                    d1.setStroke((population.get(i).viewOutlineColor()));
                    d1.setStrokeWidth(3);
                    Main.critterWorld.add(d1, population.get(i).x_coord, population.get(i).y_coord);
                    break;
                case SQUARE:
                    Rectangle r = new Rectangle();
                    r.widthProperty().bind(Main.critterWorld.widthProperty().divide(Params.world_width));
                    r.heightProperty().bind(Main.critterWorld.heightProperty().divide(Params.world_height));
                    r.setFill(population.get(i).viewFillColor());
                    r.setStroke(population.get(i).viewOutlineColor());
                    r.setStrokeWidth(3);
                    Main.critterWorld.add(r, population.get(i).x_coord, population.get(i).y_coord);
                    break;
                case STAR:
                    Polygon t1 = new Polygon();
                    Polygon t2 = new Polygon();
                    t1.getPoints().addAll(
                            (double) scaling/2,0.0,
                            0.0, (double)scaling,
                            (double)scaling, (double)scaling); //Need to put different parameters in addAll()
                    t1.setFill(population.get(i).viewFillColor());
                    t1.setStroke(population.get(i).viewOutlineColor());
                    t1.setStrokeWidth(3);
                    t2.getPoints().addAll(
                            0.0, 0.0,
                            (double)scaling, 0.0,
                            (double)scaling/2, (double)scaling); //Need to put different parameters in addAll()
                    t2.setFill(population.get(i).viewFillColor());
                    t2.setStroke(population.get(i).viewOutlineColor());
                    t2.setStrokeWidth(3);
                    Main.critterWorld.add(t1, population.get(i).x_coord, population.get(i).y_coord);
                    Main.critterWorld.add(t2, population.get(i).x_coord, population.get(i).y_coord);
                    break;
                case TRIANGLE:
                    Polygon t = new Polygon();
                    t.getPoints().addAll(
                            (double) scaling/2,0.0,
                            0.0, (double)scaling,
                            (double)scaling, (double)scaling);
                    t.setFill(population.get(i).viewFillColor());
                    t.setStroke(population.get(i).viewOutlineColor());
                    t.setStrokeWidth(3);
                    Main.critterWorld.add(t, population.get(i).x_coord, population.get(i).y_coord);
                    break;
            }
        }


    }
	
	/* create and initialize a Critter subclass
	 * critter_class_name must be the name of a concrete subclass of Critter, if not
	 * an InvalidCritterException must be thrown
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {}
	
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		return null;
	}
	
	public static String runStats(List<Critter> critters) {
	    return "";
    }
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure thath the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctup update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}
	
	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
	    population.clear();
	    babies.clear();
	    oldPopulation.clear();
	}
	
	
}
