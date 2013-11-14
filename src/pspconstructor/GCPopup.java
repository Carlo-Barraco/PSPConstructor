/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pspconstructor;

import java.awt.Component;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

/**
 *
 * @author Carlo
 */
public class GCPopup extends JPopupMenu {

    private TreePath path;

    public GCPopup() {
        super();
    }

    public void setItems(JMenuItem[] items) {
        this.removeAll();
        for (JMenuItem m : items) {
            add(m);
        }
    }

    @Override
    public void show(Component c, int x, int y) {
        JTree tree = (JTree) c;
        path = tree.getPathForLocation(x, y);
        if (path != null && path == tree.getSelectionPath()) {
            super.show(c, x, y);
        }
    }
}
