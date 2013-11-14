package pspconstructor.Components;

import java.util.ArrayList;

/**
 *
 * @author Carlo
 */
public class GCObject extends GCBase {

    public GCSprite Sprite = new GCSprite("");
    public ArrayList<ArrayList<Action>> Actions = new ArrayList<ArrayList<Action>>();
    public ArrayList<Event> Events = new ArrayList<Event>();

    public GCObject(String name) {
        super(name);
        Actions.add(new ArrayList<Action>());
        Events.add(new Event("Do", ""));
    }

    public void setSprite(GCSprite spr) {
        Sprite = spr;
    }
}
