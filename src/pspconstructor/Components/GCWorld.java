package pspconstructor.Components;

import java.util.ArrayList;

/**
 *
 * @author Carlo
 */
public class GCWorld extends GCBase{

    public GCBackground Background = new GCBackground("");
    public ArrayList<WorldObj> objs = new ArrayList<WorldObj>();
    public int backgroundID = -1;

    public GCWorld(String name) {
        super(name);
    }

    public void setBackground(GCBackground bkgd) {
        Background = bkgd;
    }
}
