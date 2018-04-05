package assignment5;

/**
 * Critter1 is represented by "1" on the world map.
 * Critter1 only fights when its opponent is of Critter1 type as well.
 * During a doTimeStep(), Critter1 runs if its energy level is bigger than 15.
 * @author Christine Dao
 * EID: cd33279
 */

public class Critter4 extends Critter{
    @Override
    public CritterShape viewShape() { return CritterShape.SQUARE; }

    @Override
    public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.RED; }

    @Override
    public String toString() { return "P"; }

    public boolean fight(String opponent) {
        if(opponent.equals("Protector")){
            walk(Critter.getRandomInt(getEnergy()));
            return false;
        } else{
            return true;
        }
    }

    @Override
    public void doTimeStep() {
        for(int i = 0; i < this.getEnergy(); i++) {
            Critter4 child = new Critter4();
            reproduce(child, this.getEnergy() - 4);
        }
    }
}