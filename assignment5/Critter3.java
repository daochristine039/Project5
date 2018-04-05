package assignment5;

/**
 * Critter1 is represented by "3" on the world map.
 * Critter1 only fights when its opponent is of Critter1 type as well.
 * During a doTimeStep(), Critter3 runs if its energy level is bigger than 15.
 * @author Christine Dao
 * EID: cd33279
 */
public class Critter3 extends Critter {
    @Override
    public String toString() { return "3"; }
    @Override
    public CritterShape viewShape() { return CritterShape.SQUARE; }

    @Override
    public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.RED; }


    public boolean fight(String opponent) {
        return(opponent.equals("3"));
    }

    @Override
    public void doTimeStep() {
        if(getEnergy() > 15) {
            run(Critter.getRandomInt(this.getEnergy()));
        }
    }
}