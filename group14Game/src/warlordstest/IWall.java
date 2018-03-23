package warlordstest;

/***
 * Interface for use in testing the behaviour of a wall in a Warlords-like game.
 *
 */
public interface IWall {

    /***
     *  Set the horizontal position of the wall to the given value.
     * @param x
     */
    void setXPos(int x);

    /***
     *  Set the vertical position of the warlord to the given value.
     * @param y
     */
    void setYPos(int y);

    /***
     * Determine if this wall has been destroyed.
     *
     * @return true if the ball has collided with this wall. Otherwise, return false.
     */
    boolean isDestroyed();
}
