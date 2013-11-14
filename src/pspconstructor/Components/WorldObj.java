/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pspconstructor.Components;

import javax.swing.ImageIcon;

/**
 *
 * @author Carlo
 */
public class WorldObj {

    public ImageIcon IIcon;
    public int X, Y, originX, originY, spriteID, ID;

    public WorldObj(ImageIcon ii, int x, int y) {
        IIcon = ii;
        X = x;
        Y = y;
    }
}
