package warlordstest;

/***
 * Interface for use in testing the behaviour of the ball in a Warlords-like game.
 *
 */
public interface IBall {

    /***
     *  Set the horizontal position of the ball to the given value.
     * @param x
     */
    void setXPos(int x);

    /***
     * Set the vertical position of the ball to the given value.
     * @param y
     */
    void setYPos(int y);

    /***
     * @return the horizontal position of the ball.
     */
    int getXPos();

    /***
     * @return the vertical position of the ball.
     */
    int getYPos();

    /***
     *  Set the horizontal velocity of the ball to the given value.
     * @param dX
     */
    void setXVelocity(int dX);

    /***
     *  Set the vertical velocity of the ball to the given value.
     * @param dY
     */
    void setYVelocity(int dY);

    /***
     * @return the horizontal velocity of the ball.
     */
    int getXVelocity();

    /***
     * @return the vertical velocity of the ball.
     */
    int getYVelocity();
}
