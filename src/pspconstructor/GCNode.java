/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pspconstructor;

import javax.swing.JMenuItem;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Carlo
 */
public class GCNode extends DefaultMutableTreeNode {

    GCPopup Menu = new GCPopup();

    public GCNode(Object Name, JMenuItem[] items) {
        super(Name);
        this.Menu.setItems(items);
    }
}
