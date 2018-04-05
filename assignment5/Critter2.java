package assignment5;

/**
 * Critter2 fights an opponent only when it has energy less than or equal to 15
 * Critter2 is more likely to die in an encounter than Critter 1.
 * With each time step, the critter walks in direction 1.
 * Critter2 is represented by "2" on the view
 * @author Isabel Li
 * UTEID: ijl283
 *
 */
public class Critter2 extends Critter {
    @Override
    public CritterShape viewShape() { return CritterShape.DIAMOND; }

    @Override
    public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.BLACK; }

    @Override
    public String toString() {
        return "2";
    }

    /**
     * determines what Critter2 does in an encounter
     */
    public boolean fight(String opponent) {
        look(3, false);
        if(this.getEnergy() > 15) {	//don't fight if critter has energy greater than 15
            return false;
        } else {
            return true;
        }
    }

    /**
     * critter 2 walks in direction 1 with each time step
     */
    public void doTimeStep() {
        walk(1);
    }
}