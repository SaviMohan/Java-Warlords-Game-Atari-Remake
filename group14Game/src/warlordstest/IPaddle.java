package warlordstest;

/***
 * Interface for use in testing the behaviour of a paddle in a Warlords-like game.
 */
public interface IPaddle {

    /***
     *  Set the horizontal position of the paddle to the given value.
     * @param x
     */
    void setXPos(int x);

    /***
     *  Set the vertical position of the paddle to the given value.
     * @param y
     */
    void setYPos(int y);
}
