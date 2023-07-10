package creatures;
import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.HugLifeUtils;
import java.awt.Color;
import java.util.Map;
import java.util.List;

/** An implementation of a motile pacifist photosynthesizer.
 *  @author Josh Hug
 */
public class Plip extends Creature {

    /** red color. */
    private int r = 99;
    /** green color. */
    private int g;
    /** blue color. */
    private int b = 76;

    /** creates plip with energy equal to E. */
    public Plip(double e) {
        super("plip");
        g = energy2green(e);
        energy = e;
    }

    /** creates a plip with energy equal to 1. */
    public Plip() {
        this(1);
    }

    /** Should return a color with red = 99, blue = 76, and green that varies
     *  linearly based on the energy of the Plip. If the plip has zero energy,
     *  it should have a green value of 63. If it has max energy, it should
     *  have a green value of 255. The green value should vary with energy
     *  linearly in between these two extremes. It's not absolutely vital
     *  that you get this exactly correct.
     */
    public Color color() {
        g = energy2green(energy);
        return color(r, g, b);
    }

    private int energy2green(double e) {
        int max = 255;
        int min = 63;
        int green = min + (int) (e / 2 * (max - min));
        green = Math.max(green, 63);
        green = Math.min(green, 255);
        return green;
    }

    private double validEnergy(double e) {
        e = Math.min(e, 2);
        e = Math.max(e, 0);
        return e;
    }
    /** Do nothing with C, Plips are pacifists. */
    public void attack(Creature c) {
    }

    /** Plips should lose 0.15 units of energy when moving. If you want to
     *  to avoid the magic number warning, you'll need to make a
     *  private static final variable. This is not required for this lab.
     */
    public void move() {
        energy -= 0.15;
        energy = validEnergy(energy);
    }


    /** Plips gain 0.2 energy when staying due to photosynthesis. */
    public void stay() {
        energy += 0.2;
        energy = validEnergy(energy);
    }

    /** Plips and their offspring each get 50% of the energy, with none
     *  lost to the process. Now that's efficiency! Returns a baby
     *  Plip.
     */
    public Plip replicate() {
        this.energy *= 0.5;
        return new Plip(energy);
    }

    /** Plips take exactly the following actions based on NEIGHBORS:
     *  1. If no empty adjacent spaces, STAY.
     *  2. Otherwise, if energy >= 1, REPLICATE.
     *  3. Otherwise, if any Cloruses, MOVE with 50% probability.
     *  4. Otherwise, if nothing else, STAY
     *
     *  Returns an object of type Action. See Action.java for the
     *  scoop on how Actions work. See SampleCreature.chooseAction()
     *  for an example to follow.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        List<Direction> cloruses = getNeighborsOfType(neighbors, "clorus");
        if (empties.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }
        if (energy >= 1.0) {
            Direction dir = HugLifeUtils.randomEntry(empties);
            return new Action(Action.ActionType.REPLICATE, dir);
        }
        if (cloruses.size() != 0) {
            if (HugLifeUtils.random() < 0.5) {
                Direction dir = HugLifeUtils.randomEntry(empties);
                return new Action(Action.ActionType.MOVE, dir);
            }
        }
        return new Action(Action.ActionType.STAY);
    }

}
