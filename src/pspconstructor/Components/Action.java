package pspconstructor.Components;

import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Carlo
 */
public class Action {

    public int ID = -1;
    public String Name, Description, Create, Destroy, Update, Render, Pause, Resume;
    public ArrayList<String> Args = new ArrayList<String>();
    public ArrayList<String> ArgLabels = new ArrayList<String>();
    public ArrayList<String> ArgTypes = new ArrayList<String>();
    public ArrayList<JPanel> ArgPanels = new ArrayList<JPanel>();
}
