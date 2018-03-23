package warlordstest;

/***
 * Interface for use in testing the behaviour of a warlord in a Warlords-like game.
 *
 */
public interface IWarlord {

    /***
     *  Set the horizontal position of the warlord to the given value.
     * @param x
     */
    void setXPos(int x);

    /***
     *  Set the vertical position of the warlord to the given value.
     * @param y
     */
    void setYPos(int y);

    /***
     * Determine if this warlord has been killed.
     *
     * @return true if the ball has collided with this warlord. Otherwise, return false.
     */
    boolean isDead();

    /***
     * Determine if this warlord is the winner of the game. Results need only be valid before the start and after the end of a game tick.
     *
     * @return true if all other warlords are dead, or if time has run out and this warlord has the highest number of undestroyed walls. Otherwise, return false.
     */
    boolean hasWon();

}
