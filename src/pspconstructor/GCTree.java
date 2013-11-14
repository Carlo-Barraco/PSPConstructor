package pspconstructor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import pspconstructor.Components.GCHandler;
import pspconstructor.Components.GCSprite;
import pspconstructor.Properties.BackgroundProperties;
import pspconstructor.Properties.FontProperties;
import pspconstructor.Properties.ObjectProperties;
import pspconstructor.Properties.ParticleProperties;
import pspconstructor.Properties.SoundProperties;
import pspconstructor.Properties.SpriteProperties;
import pspconstructor.Properties.WorldProperties;

public class GCTree extends javax.swing.JTree implements MouseListener {

    JMenuItem mniGC = new JMenuItem("No Options Available", new ImageIcon(getClass().getResource("/pspconstructor/Images/Exit.png")));
    JMenuItem[] misGC = {
        mniGC
    };
    public GCNode tnGC = new GCNode("Game Components", misGC);
    DefaultTreeModel MyModel = new DefaultTreeModel(tnGC);
    MyTreeModelListener MyListener = new MyTreeModelListener();

    GCTree() {
        super();
        this.setRootVisible(false);
        JMenuItem mniSprite = new JMenuItem("No Options Available", new ImageIcon(getClass().getResource("/pspconstructor/Images/Exit.png")));
        JMenuItem[] misSprite = {
            mniSprite
        };
        addObject("Sprites", "Sprites", misSprite);
        JMenuItem mniSound = new JMenuItem("No Options Available", new ImageIcon(getClass().getResource("/pspconstructor/Images/Exit.png")));
        JMenuItem[] misSound = {
            mniSound
        };
        addObject("Sounds", "Sounds", misSound);
        JMenuItem mniBackground = new JMenuItem("No Options Available", new ImageIcon(getClass().getResource("/pspconstructor/Images/Exit.png")));
        JMenuItem[] misBackground = {
            mniBackground
        };
        addObject("Backgrounds", "Backgrounds", misBackground);
        JMenuItem mniParticles = new JMenuItem("No Options Available", new ImageIcon(getClass().getResource("/pspconstructor/Images/Exit.png")));
        JMenuItem[] misParticles = {
            mniParticles
        };
        addObject("Particles", "Particles", misParticles);
        JMenuItem mniFonts = new JMenuItem("No Options Available", new ImageIcon(getClass().getResource("/pspconstructor/Images/Exit.png")));
        JMenuItem[] misFonts = {
            mniFonts
        };
        addObject("Fonts", "Fonts", misFonts);
        JMenuItem mniObjects = new JMenuItem("No Options Available", new ImageIcon(getClass().getResource("/pspconstructor/Images/Exit.png")));
        JMenuItem[] misObjects = {
            mniObjects
        };
        addObject("Objects", "Objects", misObjects);
        JMenuItem mniWorlds = new JMenuItem("No Options Available", new ImageIcon(getClass().getResource("/pspconstructor/Images/Exit.png")));
        JMenuItem[] misWorlds = {
            mniWorlds
        };
        addObject("Worlds", "Worlds", misWorlds);
        MyModel.addTreeModelListener(MyListener);
        this.setModel(MyModel);
        this.addMouseListener(this);
        this.setEditable(true);
        //this.setDragEnabled(true);
        this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        this.setShowsRootHandles(true);
        this.setBackground(Color.WHITE);
        this.setCellRenderer(new MyRenderer());
    }

    public void removeCurrentNode() {
        TreePath currentSelection = getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());
            if (parent != null) {
                MyModel.removeNodeFromParent(currentNode);
                return;
            }
        }
    }

    public GCNode addObject(Object child, String Text, JMenuItem[] items) {
        GCNode parentNode = null;
        TreePath parentPath = getSelectionPath();
        if (parentPath == null) {
            parentNode = tnGC;
        } else {
            parentNode = (GCNode) (parentPath.getLastPathComponent());
        }
        return addObject(parentNode, child, true, Text, items);
    }

    public GCNode addObject(GCNode parent, Object child, String Text, JMenuItem[] items) {
        return addObject(parent, child, false, Text, items);
    }

    public GCNode addObject(GCNode parent, Object child, boolean shouldBeVisible, String Text, JMenuItem[] items) {
        GCNode childNode = new GCNode(child, items);
        if (parent == null) {
            parent = tnGC;
        }
        MyModel.insertNodeInto(childNode, parent, parent.getChildCount());
        if (shouldBeVisible) {
            scrollPathToVisible(new TreePath(childNode.getPath()));
        }
        return childNode;
    }

    public void renameCurrentNode() {
        this.setEditable(true);
        TreePath path = this.getSelectionPath();
        this.startEditingAtPath(path);
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        int selRow = this.getRowForLocation(e.getX(), e.getY());
        TreePath selPath = this.getSelectionPath();
        if (selRow != -1) {
            if (e.getClickCount() == 2) {
                DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (selPath.getLastPathComponent());
                if (currentNode.getParent() != null && !currentNode.getParent().equals(tnGC)) {
                    if (currentNode.getParent().equals(tnGC.getChildAt(0))) {
                        int index = Main.logMain.tnGC.getChildAt(0).getIndex((TreeNode) (Main.logMain.getSelectionPath().getLastPathComponent()));
                        SpriteProperties SP = new SpriteProperties(index);
                        Main.dskMain.add(SP);
                        SP.setVisible(true);
                    } else if (currentNode.getParent().equals(tnGC.getChildAt(1))) {
                        int index = Main.logMain.tnGC.getChildAt(1).getIndex((TreeNode) (Main.logMain.getSelectionPath().getLastPathComponent()));
                        SoundProperties SP = new SoundProperties(index);
                        Main.dskMain.add(SP);
                        SP.setVisible(true);
                    } else if (currentNode.getParent().equals(tnGC.getChildAt(2))) {
                        int index = Main.logMain.tnGC.getChildAt(2).getIndex((TreeNode) (Main.logMain.getSelectionPath().getLastPathComponent()));
                        BackgroundProperties BP = new BackgroundProperties(index);
                        Main.dskMain.add(BP);
                        BP.setVisible(true);
                    } else if (currentNode.getParent().equals(tnGC.getChildAt(3))) {
                        int index = Main.logMain.tnGC.getChildAt(3).getIndex((TreeNode) (Main.logMain.getSelectionPath().getLastPathComponent()));
                        ParticleProperties PP = new ParticleProperties(index);
                        Main.dskMain.add(PP);
                        PP.setVisible(true);
                    } else if (currentNode.getParent().equals(tnGC.getChildAt(4))) {
                        int index = Main.logMain.tnGC.getChildAt(4).getIndex((TreeNode) (Main.logMain.getSelectionPath().getLastPathComponent()));
                        FontProperties FP = new FontProperties(index);
                        Main.dskMain.add(FP);
                        FP.setVisible(true);
                    } else if (currentNode.getParent().equals(tnGC.getChildAt(5))) {
                        int index = Main.logMain.tnGC.getChildAt(5).getIndex((TreeNode) (Main.logMain.getSelectionPath().getLastPathComponent()));
                        ObjectProperties OP = new ObjectProperties(index);
                        Main.dskMain.add(OP);
                        OP.setVisible(true);
                    } else if (currentNode.getParent().equals(tnGC.getChildAt(6))) {
                        int index = Main.logMain.tnGC.getChildAt(6).getIndex((TreeNode) (Main.logMain.getSelectionPath().getLastPathComponent()));
                        WorldProperties WP = new WorldProperties(index);
                        Main.dskMain.add(WP);
                        WP.setVisible(true);
                    }
                }
            }
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            if (getRowForLocation(e.getX(), e.getY()) == -1) {
                return;
            }
            TreePath curPath = getPathForLocation(e.getX(), e.getY());
            setComponentPopupMenu(((GCNode) curPath.getLastPathComponent()).Menu);
        }
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}

class MyTreeModelListener implements TreeModelListener {

    public void treeNodesChanged(TreeModelEvent e) {
        DefaultMutableTreeNode node;
        node = (DefaultMutableTreeNode) (e.getTreePath().getLastPathComponent());
        try {
            int index = e.getChildIndices()[0];
            node = (DefaultMutableTreeNode) (node.getChildAt(index));
        } catch (NullPointerException exc) {
        }
        if (node.getParent().equals(Main.logMain.tnGC.getChildAt(0))) {
            boolean Error = false;
            for (GCSprite s : GCHandler.Sprites) {
                if (s.Name.equals(node.toString())) {
                    Error = true;
                }
            }
            if (!Error) {
                GCHandler.Sprites.get(Main.logMain.tnGC.getChildAt(0).getIndex(node)).setName(node.toString());
            } else {
                JOptionPane.showMessageDialog(null, "Name already taken, Please rename this sprite", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (node.getParent().equals(Main.logMain.tnGC.getChildAt(1))) {
            GCHandler.Sounds.get(Main.logMain.tnGC.getChildAt(1).getIndex(node)).setName(node.toString());
        } else if (node.getParent().equals(Main.logMain.tnGC.getChildAt(2))) {
            GCHandler.Backgrounds.get(Main.logMain.tnGC.getChildAt(2).getIndex(node)).setName(node.toString());
        } else if (node.getParent().equals(Main.logMain.tnGC.getChildAt(3))) {
            GCHandler.Particles.get(Main.logMain.tnGC.getChildAt(3).getIndex(node)).setName(node.toString());
        } else if (node.getParent().equals(Main.logMain.tnGC.getChildAt(4))) {
            GCHandler.Fonts.get(Main.logMain.tnGC.getChildAt(4).getIndex(node)).setName(node.toString());
        }
    }

    public void treeNodesInserted(TreeModelEvent e) {
    }

    public void treeNodesRemoved(TreeModelEvent e) {
    }

    public void treeStructureChanged(TreeModelEvent e) {
    }
}

class MyRenderer extends DefaultTreeCellRenderer {

    public MyRenderer() {
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        if (isGCNode(value)) {
            setIcon(this.getClosedIcon());
        }
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        try {
            if (node.getParent().equals(Main.logMain.tnGC.getChildAt(0))) {
                if (!getIcon().equals(new ImageIcon(GCHandler.Sprites.get(Main.logMain.tnGC.getChildAt(0).getIndex(node)).Image.getImage().getScaledInstance(16, 16, Image.SCALE_AREA_AVERAGING)))) {
                    setIcon(new ImageIcon(GCHandler.Sprites.get(Main.logMain.tnGC.getChildAt(0).getIndex(node)).Image.getImage().getScaledInstance(16, 16, Image.SCALE_AREA_AVERAGING)));
                }
            }
        } catch (NullPointerException e) {
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            if (node.getParent().equals(Main.logMain.tnGC.getChildAt(1))) {
                setIcon(new ImageIcon(getClass().getResource("/pspconstructor/Images/Sound.png")));
            }
        } catch (NullPointerException e) {
        }
        try {
            if (node.getParent().equals(Main.logMain.tnGC.getChildAt(2))) {
                setIcon(new ImageIcon(GCHandler.Backgrounds.get(Main.logMain.tnGC.getChildAt(2).getIndex(node)).Image.getScaledInstance(16, 16, Image.SCALE_AREA_AVERAGING)));
            }
        } catch (NullPointerException e) {
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            if (node.getParent().equals(Main.logMain.tnGC.getChildAt(3))) {
                setIcon(new ImageIcon(getClass().getResource("/pspconstructor/Images/Particle.png")));
            }
        } catch (NullPointerException e) {
        }
        try {
            if (node.getParent().equals(Main.logMain.tnGC.getChildAt(4))) {
                setIcon(new ImageIcon(getClass().getResource("/pspconstructor/Images/Font.png")));
            }
        } catch (NullPointerException e) {
        }
        try {
            if (node.getParent().equals(Main.logMain.tnGC.getChildAt(5))) {
                setIcon(new ImageIcon(GCHandler.Objects.get(Main.logMain.tnGC.getChildAt(5).getIndex(node)).Sprite.Image.getImage().getScaledInstance(16, 16, Image.SCALE_AREA_AVERAGING)));
            }
        } catch (NullPointerException e) {
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            if (node.getParent().equals(Main.logMain.tnGC.getChildAt(6))) {
                setIcon(new ImageIcon(GCHandler.Worlds.get(Main.logMain.tnGC.getChildAt(6).getIndex(node)).Background.Image.getScaledInstance(16, 16, Image.SCALE_AREA_AVERAGING)));
            }
        } catch (NullPointerException e) {
        } catch (IndexOutOfBoundsException e) {
        }
        return this;
    }

    protected boolean isGCNode(Object value) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        try {
            if (node.getParent().equals(node.getRoot())) {
                return true;
            }
        } catch (NullPointerException e) {
        }
        return false;
    }
}
