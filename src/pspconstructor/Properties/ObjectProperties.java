/*
 * ObjectProperties.java
 *
 * Created on Jul 7, 2009, 2:49:12 PM
 */
package pspconstructor.Properties;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;
import pspconstructor.Components.Action;
import pspconstructor.Components.Event;
import pspconstructor.Components.GCHandler;
import pspconstructor.Components.GCObject;
import pspconstructor.Components.GCSprite;
import pspconstructor.IOHandler;
import pspconstructor.Main;

/**
 *
 * @author Carlo
 */
public class ObjectProperties extends javax.swing.JInternalFrame {

    int Index;
    ArrayList<JPanel> actionPanels = new ArrayList<JPanel>();
    ArrayList<JList> lstEvents = new ArrayList<JList>();
    ArrayList<DefaultListModel> lmlEvents = new ArrayList<DefaultListModel>();
    ArrayList<JScrollPane> scpEvents = new ArrayList<JScrollPane>();
    ArrayList<Action> Actions = new ArrayList<Action>();
    ArrayList<ArrayList<JPanel>> pnlArgs = new ArrayList<ArrayList<JPanel>>();

    public void createActionTabs() {
        File dir = new File("./Libraries/");
        String[] children = dir.list();
        if (children == null) {
            // Either dir does not exist or is not a directory
        } else {
            for (int i = 0; i < children.length; i++) {
                String filename = children[i];
            }
        }
        FilenameFilter filter = new FilenameFilter() {

            public boolean accept(File dir, String name) {
                return !name.startsWith(".");
            }
        };
        children = dir.list(filter);
        File[] files = dir.listFiles();
        FileFilter fileFilter = new FileFilter() {

            public boolean accept(File file) {
                return file.isDirectory();
            }
        };
        files = dir.listFiles(fileFilter);
        for (File lib : files) {
            actionPanels.add(new JPanel());
            tbpActions.addTab(lib.getName(), actionPanels.get(actionPanels.size() - 1));
            File[] actions = lib.listFiles();
            for (File action : actions) {
                actionPanels.get(actionPanels.size() - 1).add(addAction(lib.getName(), action.getName()));
            }
        }
    }

    public void btnActionPerformed(Action thisAction) {
        lmlEvents.get(tbpEvents.getSelectedIndex()).addElement(thisAction.Description);
        JPanel argPanels[] = new JPanel[9];
        for (int i = 0; i < thisAction.ArgLabels.size(); i++) {
            argPanels[i] = thisAction.ArgPanels.get(i);
        }
        for (int i = 0; i < thisAction.ArgLabels.size(); i++) {
            if (thisAction.ArgTypes.get(i).equals("Color")) {
                ((pnlColor) argPanels[i]).color = Color.RED;
            }
        }
        JOptionPane.showMessageDialog(null, argPanels, "Editing Action", JOptionPane.PLAIN_MESSAGE);
        for (int i = 0; i < thisAction.ArgLabels.size(); i++) {
            if (thisAction.ArgTypes.get(i).equals("String")) {
                thisAction.Args.add(((pnlString) argPanels[i]).txtStringArg.getText());
            } else if (thisAction.ArgTypes.get(i).equals("Color")) {
                Color clr = ((pnlColor) argPanels[i]).color;
                thisAction.Args.add("ARGB(255," + clr.getRed() + "," + clr.getGreen() + "," + clr.getBlue() + ")");
            } else if (thisAction.ArgTypes.get(i).equals("Sprite")) {
                String str = String.valueOf(((pnlComponent) argPanels[i]).cboComp.getSelectedItem());
                for (int j = 0; j < GCHandler.Sprites.size(); j++) {
                    if (str.equals(GCHandler.Sprites.get(j).Name)) {
                        thisAction.Args.add("sprTexs.at(" + GCHandler.Sprites.indexOf(GCHandler.Sprites.get(j)) + ")");
                    }
                }
            } else if (thisAction.ArgTypes.get(i).equals("Sound")) {
                String str = String.valueOf(((pnlComponent) argPanels[i]).cboComp.getSelectedItem());
                for (int j = 0; j < GCHandler.Sounds.size(); j++) {
                    if (str.equals(GCHandler.Sounds.get(j).Name)) {
                        thisAction.Args.add("sounds.at(" + GCHandler.Sounds.indexOf(GCHandler.Sounds.get(j)) + ")");
                    }
                }
            } else if (thisAction.ArgTypes.get(i).equals("Background")) {
                String str = String.valueOf(((pnlComponent) argPanels[i]).cboComp.getSelectedItem());
                for (int j = 0; j < GCHandler.Backgrounds.size(); j++) {
                    if (str.equals(GCHandler.Backgrounds.get(j).Name)) {
                        thisAction.Args.add("bgTexs.at(" + GCHandler.Backgrounds.indexOf(GCHandler.Backgrounds.get(j)) + ")");
                    }
                }
            } else if (thisAction.ArgTypes.get(i).equals("Particle")) {
                String str = String.valueOf(((pnlComponent) argPanels[i]).cboComp.getSelectedItem());
                for (int j = 0; j < GCHandler.Particles.size(); j++) {
                    if (str.equals(GCHandler.Particles.get(j).Name)) {
                        thisAction.Args.add("particles.at(" + GCHandler.Particles.indexOf(GCHandler.Particles.get(j)) + ")");
                    }
                }
            } else if (thisAction.ArgTypes.get(i).equals("Font")) {
                String str = String.valueOf(((pnlComponent) argPanels[i]).cboComp.getSelectedItem());
                for (int j = 0; j < GCHandler.Fonts.size(); j++) {
                    if (str.equals(GCHandler.Fonts.get(j).Name)) {
                        thisAction.Args.add("fonts.at(" + GCHandler.Fonts.indexOf(GCHandler.Fonts.get(j)) + ")");
                    }
                }
            } else if (thisAction.ArgTypes.get(i).equals("Object")) {
                String str = String.valueOf(((pnlComponent) argPanels[i]).cboComp.getSelectedItem());
                for (int j = 0; j < GCHandler.Objects.size(); j++) {
                    if (str.equals(GCHandler.Objects.get(j).Name)) {
                        thisAction.Args.add("" + GCHandler.Objects.get(j).Name);
                    }
                }
            }else if (thisAction.ArgTypes.get(i).equals("World")) {
                String str = String.valueOf(((pnlComponent) argPanels[i]).cboComp.getSelectedItem());
                for (int j = 0; j < GCHandler.Worlds.size(); j++) {
                    if (str.equals(GCHandler.Worlds.get(j).Name)) {
                        thisAction.Args.add("" + GCHandler.Worlds.indexOf(GCHandler.Worlds.get(j)));
                    }
                }
            }
        }
        GCHandler.Objects.get(Index).Actions.get(tbpEvents.getSelectedIndex()).add(thisAction);
    }

    public JButton addAction(final String Library, final String Action) {
        final JButton btn = new JButton(new ImageIcon("./Libraries/" + Library + "/" + Action + "/Icon.png"));
        int size = 24;
        btn.setPreferredSize(new Dimension(size, size));
        btn.setMaximumSize(new Dimension(size, size));
        btn.setMinimumSize(new Dimension(size, size));
        btn.setBorder(new LineBorder(Color.BLACK));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setToolTipText(IOHandler.getText(new File("./Libraries/" + Library + "/" + Action + "/Description.txt")));
        btn.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Action thisAction = new Action();
                thisAction.Description = IOHandler.getText(new File("./Libraries/" + Library + "/" + Action + "/Description.txt"));
                thisAction.Create = IOHandler.getText(new File("./Libraries/" + Library + "/" + Action + "/Code/Create.txt"));
                thisAction.Destroy = IOHandler.getText(new File("./Libraries/" + Library + "/" + Action + "/Code/Destroy.txt"));
                thisAction.Update = IOHandler.getText(new File("./Libraries/" + Library + "/" + Action + "/Code/Update.txt"));
                thisAction.Render = IOHandler.getText(new File("./Libraries/" + Library + "/" + Action + "/Code/Render.txt"));
                thisAction.Pause = IOHandler.getText(new File("./Libraries/" + Library + "/" + Action + "/Code/Pause.txt"));
                thisAction.Resume = IOHandler.getText(new File("./Libraries/" + Library + "/" + Action + "/Code/Resume.txt"));
                File Args = new File("./Libraries/" + Library + "/" + Action + "/Code/Arguments.txt");
                FileReader in;
                BufferedReader Info;
                try {
                    in = new FileReader(Args);
                    Info = new BufferedReader(in);
                    String Type;
                    while ((Type = Info.readLine()) != null) {
                        thisAction.ArgTypes.add(Type);
                        if (Type.equals("String")) {
                            String Label = Info.readLine();
                            pnlString stringPanel = new pnlString();
                            stringPanel.lblArg.setText(Label);
                            thisAction.ArgLabels.add(Label);
                            thisAction.ArgPanels.add(stringPanel);
                        } else if (Type.equals("Color")) {
                            String Label = Info.readLine();
                            pnlColor colorPanel = new pnlColor();
                            colorPanel.lblArg.setText(Label);
                            thisAction.ArgLabels.add(Label);
                            thisAction.ArgPanels.add(colorPanel);
                        } else {
                            String Label = Info.readLine();
                            pnlComponent compPanel = new pnlComponent(Type);
                            compPanel.lblArg.setText(Label);
                            thisAction.ArgLabels.add(Label);
                            thisAction.ArgPanels.add(compPanel);
                        }
                    }
                    Info.close();
                    in.close();
                } catch (FileNotFoundException e) {
                } catch (IOException e) {
                }
                btnActionPerformed(thisAction);
            }
        });
        btn.setFocusable(false);
        return btn;
    }

    public void addEvent(String name) {
        lstEvents.add(new JList());
        lmlEvents.add(new DefaultListModel());
        scpEvents.add(new JScrollPane());
        scpEvents.get(scpEvents.size() - 1).setViewportView(lstEvents.get(lstEvents.size() - 1));
        lstEvents.get(lstEvents.size() - 1).setModel(lmlEvents.get(lmlEvents.size() - 1));
        lstEvents.get(lstEvents.size() - 1).addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                JList list = lstEvents.get(lstEvents.size() - 1);
                if (evt.getClickCount() == 2) {
                    int index = list.locationToIndex(evt.getPoint());
                    if (index != -1) {
                        Action thisAction = GCHandler.Objects.get(Index).Actions.get(tbpEvents.getSelectedIndex()).get(index);
                        JPanel argPanels[] = new JPanel[9];
                        for (int i = 0; i < thisAction.ArgLabels.size(); i++) {
                            argPanels[i] = thisAction.ArgPanels.get(i);
                        }
                        JOptionPane.showMessageDialog(null, argPanels, "Editing Action", JOptionPane.PLAIN_MESSAGE);
                        for (int i = 0; i < thisAction.Args.size(); i++) {
                            if (thisAction.ArgTypes.get(i).equals("String")) {
                                thisAction.Args.set(i, (((pnlString) argPanels[i]).txtStringArg.getText()));
                            } else if (thisAction.ArgTypes.get(i).equals("Color")) {
                                Color clr = ((pnlColor) argPanels[i]).color;
                                thisAction.Args.set(i, ("ARGB(255," + clr.getRed() + "," + clr.getGreen() + "," + clr.getBlue() + ")"));
                            } else if (thisAction.ArgTypes.get(i).equals("Sound")) {
                                String str = String.valueOf(((pnlComponent) argPanels[i]).cboComp.getSelectedItem());
                                for (int j = 0; j < GCHandler.Sounds.size(); j++) {
                                    if (str.equals(GCHandler.Sounds.get(j).Name)) {
                                        thisAction.Args.set(i, "sounds.at(" + GCHandler.Sounds.get(j).ID + ")");
                                    }
                                }
                            } else if (thisAction.ArgTypes.get(i).equals("Particle")) {
                                String str = String.valueOf(((pnlComponent) argPanels[i]).cboComp.getSelectedItem());
                                for (int j = 0; j < GCHandler.Particles.size(); j++) {
                                    if (str.equals(GCHandler.Particles.get(j).Name)) {
                                        thisAction.Args.set(i, "particles.at(" + GCHandler.Particles.get(j).ID + ")");
                                    }
                                }
                            } else if (thisAction.ArgTypes.get(i).equals("Font")) {
                                String str = String.valueOf(((pnlComponent) argPanels[i]).cboComp.getSelectedItem());
                                for (int j = 0; j < GCHandler.Fonts.size(); j++) {
                                    if (str.equals(GCHandler.Fonts.get(j).Name)) {
                                        thisAction.Args.set(i, "fonts.at(" + GCHandler.Fonts.indexOf(GCHandler.Fonts.get(j)) + ")");
                                    }
                                }
                            } else if (thisAction.ArgTypes.get(i).equals("Background")) {
                                String str = String.valueOf(((pnlComponent) argPanels[i]).cboComp.getSelectedItem());
                                for (int j = 0; j < GCHandler.Backgrounds.size(); j++) {
                                    if (str.equals(GCHandler.Backgrounds.get(j).Name)) {
                                        thisAction.Args.set(i, "bgTexs.at(" + GCHandler.Backgrounds.indexOf(GCHandler.Backgrounds.get(j)) + ")");
                                    }
                                }
                            } else if (thisAction.ArgTypes.get(i).equals("Sprite")) {
                                String str = String.valueOf(((pnlComponent) argPanels[i]).cboComp.getSelectedItem());
                                for (int j = 0; j < GCHandler.Sprites.size(); j++) {
                                    if (str.equals(GCHandler.Sprites.get(j).Name)) {
                                        thisAction.Args.set(i, "sprTexs.at(" + GCHandler.Sprites.indexOf(GCHandler.Sprites.get(j)) + ")");
                                    }
                                }
                            } else if (thisAction.ArgTypes.get(i).equals("Object")) {
                                String str = String.valueOf(((pnlComponent) argPanels[i]).cboComp.getSelectedItem());
                                for (int j = 0; j < GCHandler.Objects.size(); j++) {
                                    if (str.equals(GCHandler.Objects.get(j).Name)) {
                                        thisAction.Args.set(i, "" + GCHandler.Objects.get(j).Name);
                                    }
                                }
                            }else if (thisAction.ArgTypes.get(i).equals("World")) {
                                String str = String.valueOf(((pnlComponent) argPanels[i]).cboComp.getSelectedItem());
                                for (int j = 0; j < GCHandler.Worlds.size(); j++) {
                                    if (str.equals(GCHandler.Worlds.get(j).Name)) {
                                        thisAction.Args.set(i, "" + GCHandler.Worlds.indexOf(GCHandler.Worlds.get(j)));
                                    }
                                }
                            }
                        }
                        GCHandler.Objects.get(Index).Actions.get(tbpEvents.getSelectedIndex()).set(index, thisAction);
                    }
                }
            }
        });
        tbpEvents.addTab(name, scpEvents.get(scpEvents.size() - 1));
    }

    /** Creates new form ObjectProperties */
    public ObjectProperties(int index) {
        initComponents();
        Index = index;
        for (Event s : GCHandler.Objects.get(index).Events) {
            addEvent(s.Name);
        }
        for (GCSprite s : GCHandler.Sprites) {
            cboSprite.addItem(s.Name);
        }
        for (GCObject o : GCHandler.Objects) {
            JMenuItem mniTemp = new JMenuItem(o.Name,new ImageIcon(o.Sprite.Image.getImage().getScaledInstance(16, 16, Image.SCALE_AREA_AVERAGING)));
            mniTemp.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    addEvent(evt.getActionCommand());
                    GCObject objTarget = new GCObject("");
                    for (GCObject obj : GCHandler.Objects) {
                        if (obj.Name.equals(evt.getActionCommand())) {
                            objTarget = obj;
                        }
                    }
                    GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
                    GCObject objThis = GCHandler.Objects.get(Index);
                    String condition = "X - " + objThis.Sprite.OriginX + " < " + objTarget.Name + "s[WorldIndex].at(j)->X - " + objTarget.Sprite.OriginX + " + " + objTarget.Sprite.Width;
                    condition += " && X - " + objThis.Sprite.OriginX + " + " + objThis.Sprite.Width + " > " + objTarget.Name + "s[WorldIndex].at(j)->X - " + objTarget.Sprite.OriginX;
                    condition += " && Y - " + objThis.Sprite.OriginY + " < " + objTarget.Name + "s[WorldIndex].at(j)->Y - " + objTarget.Sprite.OriginY + " + " + objTarget.Sprite.Height;
                    condition += " && Y - " + objThis.Sprite.OriginY + " + " + objThis.Sprite.Height + " > " + objTarget.Name + "s[WorldIndex].at(j)->Y - " + objTarget.Sprite.OriginY;
                    GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), condition));
                }
            });
            mnuCollision.add(mniTemp);
        }
        for (GCSprite s : GCHandler.Sprites) {
            if (s.ID == GCHandler.Objects.get(index).Sprite.ID) {
                cboSprite.setSelectedItem(s.Name);
            }
            if (s.ID == -1) {
                cboSprite.setSelectedIndex(0);
            }
        }
        btnAdd.setComponentPopupMenu(pumEvents);
        createActionTabs();
        if (GCHandler.Objects.get(Index).Actions.size() > 0) {
            for (ArrayList<Action> aa : GCHandler.Objects.get(index).Actions) {
                for (Action a : aa) {
                    lmlEvents.get(GCHandler.Objects.get(index).Actions.indexOf(aa)).addElement(a.Description);
                }
            }
        }
        for (JList l : lstEvents) {
        }
        cboSpriteRenderer renderer = new cboSpriteRenderer();
        cboSprite.setRenderer(renderer);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pumEvents = new javax.swing.JPopupMenu();
        mnuInput = new javax.swing.JMenu();
        mnuAnalog = new javax.swing.JMenu();
        mniAUp = new javax.swing.JMenuItem();
        mniADown = new javax.swing.JMenuItem();
        mniALeft = new javax.swing.JMenuItem();
        mniARight = new javax.swing.JMenuItem();
        mnuButtons = new javax.swing.JMenu();
        mnuPressed = new javax.swing.JMenu();
        mnuShoulderP = new javax.swing.JMenu();
        mniSLeftP = new javax.swing.JMenuItem();
        mniSRightP = new javax.swing.JMenuItem();
        mnuPadP = new javax.swing.JMenu();
        mniTriangleP = new javax.swing.JMenuItem();
        mniCrossP = new javax.swing.JMenuItem();
        mniSquareP = new javax.swing.JMenuItem();
        mniCircleP = new javax.swing.JMenuItem();
        mnuDPadP = new javax.swing.JMenu();
        mniDUpP = new javax.swing.JMenuItem();
        mniDDownP = new javax.swing.JMenuItem();
        mniDLeftP = new javax.swing.JMenuItem();
        mniDRightP = new javax.swing.JMenuItem();
        mnuControlP = new javax.swing.JMenu();
        mnuNoteP = new javax.swing.JMenuItem();
        mnuSelectP = new javax.swing.JMenuItem();
        mnuStartP = new javax.swing.JMenuItem();
        mnuHomeP = new javax.swing.JMenuItem();
        mnuHoldP = new javax.swing.JMenuItem();
        mnuHeld = new javax.swing.JMenu();
        mnuShoulderH = new javax.swing.JMenu();
        mniSLeftH = new javax.swing.JMenuItem();
        mniSRightH = new javax.swing.JMenuItem();
        mnuPadH = new javax.swing.JMenu();
        mniTriangleH = new javax.swing.JMenuItem();
        mniCrossH = new javax.swing.JMenuItem();
        mniSquareH = new javax.swing.JMenuItem();
        mniCircleH = new javax.swing.JMenuItem();
        mnuDPadH = new javax.swing.JMenu();
        mniDUpH = new javax.swing.JMenuItem();
        mniDDownH = new javax.swing.JMenuItem();
        mniDLeftH = new javax.swing.JMenuItem();
        mniDRightH = new javax.swing.JMenuItem();
        mnuControlH = new javax.swing.JMenu();
        mnuNoteH = new javax.swing.JMenuItem();
        mnuSelectH = new javax.swing.JMenuItem();
        mnuStartH = new javax.swing.JMenuItem();
        mnuHomeH = new javax.swing.JMenuItem();
        mnuHoldH = new javax.swing.JMenuItem();
        mnuUnheld = new javax.swing.JMenu();
        mnuShoulderU = new javax.swing.JMenu();
        mniSLeftU = new javax.swing.JMenuItem();
        mniSRightU = new javax.swing.JMenuItem();
        mnuPadU = new javax.swing.JMenu();
        mniTriangleU = new javax.swing.JMenuItem();
        mniCrossU = new javax.swing.JMenuItem();
        mniSquareU = new javax.swing.JMenuItem();
        mniCircleU = new javax.swing.JMenuItem();
        mnuDPadU = new javax.swing.JMenu();
        mniDUpU = new javax.swing.JMenuItem();
        mniDDownU = new javax.swing.JMenuItem();
        mniDLeftU = new javax.swing.JMenuItem();
        mniDRightU = new javax.swing.JMenuItem();
        mnuControlU = new javax.swing.JMenu();
        mnuNoteU = new javax.swing.JMenuItem();
        mnuSelectU = new javax.swing.JMenuItem();
        mnuStartU = new javax.swing.JMenuItem();
        mnuHomeU = new javax.swing.JMenuItem();
        mnuHoldU = new javax.swing.JMenuItem();
        mnuCollision = new javax.swing.JMenu();
        pnlInput = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();
        cboSprite = new javax.swing.JComboBox();
        tbpActions = new javax.swing.JTabbedPane();
        tbpEvents = new javax.swing.JTabbedPane();

        mnuInput.setText("Input");

        mnuAnalog.setText("Analog");

        mniAUp.setText("AUp");
        mniAUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniAUpActionPerformed(evt);
            }
        });
        mnuAnalog.add(mniAUp);

        mniADown.setText("ADown");
        mniADown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniADownActionPerformed(evt);
            }
        });
        mnuAnalog.add(mniADown);

        mniALeft.setText("ALeft");
        mniALeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniALeftActionPerformed(evt);
            }
        });
        mnuAnalog.add(mniALeft);

        mniARight.setText("ARight");
        mniARight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniARightActionPerformed(evt);
            }
        });
        mnuAnalog.add(mniARight);

        mnuInput.add(mnuAnalog);

        mnuButtons.setText("Buttons");

        mnuPressed.setText("Pressed");

        mnuShoulderP.setText("Shoulder");

        mniSLeftP.setText("SLeft[P]");
        mniSLeftP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniSLeftPActionPerformed(evt);
            }
        });
        mnuShoulderP.add(mniSLeftP);

        mniSRightP.setText("SRight[P]");
        mniSRightP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniSRightPActionPerformed(evt);
            }
        });
        mnuShoulderP.add(mniSRightP);

        mnuPressed.add(mnuShoulderP);

        mnuPadP.setText("Pad");

        mniTriangleP.setText("Triangle[P]");
        mniTriangleP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTrianglePActionPerformed(evt);
            }
        });
        mnuPadP.add(mniTriangleP);

        mniCrossP.setText("Cross[P]");
        mniCrossP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniCrossPActionPerformed(evt);
            }
        });
        mnuPadP.add(mniCrossP);

        mniSquareP.setText("Square[P]");
        mniSquareP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniSquarePActionPerformed(evt);
            }
        });
        mnuPadP.add(mniSquareP);

        mniCircleP.setText("Circle[P]");
        mniCircleP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniCirclePActionPerformed(evt);
            }
        });
        mnuPadP.add(mniCircleP);

        mnuPressed.add(mnuPadP);

        mnuDPadP.setText("D Pad");

        mniDUpP.setText("DUp[P]");
        mniDUpP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDUpPActionPerformed(evt);
            }
        });
        mnuDPadP.add(mniDUpP);

        mniDDownP.setText("DDown[P]");
        mniDDownP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDDownPActionPerformed(evt);
            }
        });
        mnuDPadP.add(mniDDownP);

        mniDLeftP.setText("DLeft[P]");
        mniDLeftP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDLeftPActionPerformed(evt);
            }
        });
        mnuDPadP.add(mniDLeftP);

        mniDRightP.setText("DRight[P]");
        mniDRightP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDRightPActionPerformed(evt);
            }
        });
        mnuDPadP.add(mniDRightP);

        mnuPressed.add(mnuDPadP);

        mnuControlP.setText("Control");

        mnuNoteP.setText("Note[P]");
        mnuNoteP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNotePActionPerformed(evt);
            }
        });
        mnuControlP.add(mnuNoteP);

        mnuSelectP.setText("Select[P]");
        mnuSelectP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSelectPActionPerformed(evt);
            }
        });
        mnuControlP.add(mnuSelectP);

        mnuStartP.setText("Start[P]");
        mnuStartP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuStartPActionPerformed(evt);
            }
        });
        mnuControlP.add(mnuStartP);

        mnuHomeP.setText("Home[P]");
        mnuHomeP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuHomePActionPerformed(evt);
            }
        });
        mnuControlP.add(mnuHomeP);

        mnuHoldP.setText("Hold[P]");
        mnuHoldP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuHoldPActionPerformed(evt);
            }
        });
        mnuControlP.add(mnuHoldP);

        mnuPressed.add(mnuControlP);

        mnuButtons.add(mnuPressed);

        mnuHeld.setText("Held");

        mnuShoulderH.setText("Shoulder");

        mniSLeftH.setText("SLeft[H]");
        mniSLeftH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniSLeftHActionPerformed(evt);
            }
        });
        mnuShoulderH.add(mniSLeftH);

        mniSRightH.setText("SRight[H]");
        mniSRightH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniSRightHActionPerformed(evt);
            }
        });
        mnuShoulderH.add(mniSRightH);

        mnuHeld.add(mnuShoulderH);

        mnuPadH.setText("Pad");

        mniTriangleH.setText("Triangle[H]");
        mniTriangleH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTriangleHActionPerformed(evt);
            }
        });
        mnuPadH.add(mniTriangleH);

        mniCrossH.setText("Cross[H]");
        mniCrossH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniCrossHActionPerformed(evt);
            }
        });
        mnuPadH.add(mniCrossH);

        mniSquareH.setText("Square[H]");
        mniSquareH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniSquareHActionPerformed(evt);
            }
        });
        mnuPadH.add(mniSquareH);

        mniCircleH.setText("Circle[H]");
        mniCircleH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniCircleHActionPerformed(evt);
            }
        });
        mnuPadH.add(mniCircleH);

        mnuHeld.add(mnuPadH);

        mnuDPadH.setText("D Pad");

        mniDUpH.setText("DUp[H]");
        mniDUpH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDUpHActionPerformed(evt);
            }
        });
        mnuDPadH.add(mniDUpH);

        mniDDownH.setText("DDown[H]");
        mniDDownH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDDownHActionPerformed(evt);
            }
        });
        mnuDPadH.add(mniDDownH);

        mniDLeftH.setText("DLeft[H]");
        mniDLeftH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDLeftHActionPerformed(evt);
            }
        });
        mnuDPadH.add(mniDLeftH);

        mniDRightH.setText("DRight[H]");
        mniDRightH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDRightHActionPerformed(evt);
            }
        });
        mnuDPadH.add(mniDRightH);

        mnuHeld.add(mnuDPadH);

        mnuControlH.setText("Control");

        mnuNoteH.setText("Note[H]");
        mnuNoteH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNoteHActionPerformed(evt);
            }
        });
        mnuControlH.add(mnuNoteH);

        mnuSelectH.setText("Select[H]");
        mnuSelectH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSelectHActionPerformed(evt);
            }
        });
        mnuControlH.add(mnuSelectH);

        mnuStartH.setText("Start[H]");
        mnuStartH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuStartHActionPerformed(evt);
            }
        });
        mnuControlH.add(mnuStartH);

        mnuHomeH.setText("Home[H]");
        mnuHomeH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuHomeHActionPerformed(evt);
            }
        });
        mnuControlH.add(mnuHomeH);

        mnuHoldH.setText("Hold[H]");
        mnuHoldH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuHoldHActionPerformed(evt);
            }
        });
        mnuControlH.add(mnuHoldH);

        mnuHeld.add(mnuControlH);

        mnuButtons.add(mnuHeld);

        mnuUnheld.setText("Unheld");

        mnuShoulderU.setText("Shoulder");

        mniSLeftU.setText("SLeft[U]");
        mniSLeftU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniSLeftUActionPerformed(evt);
            }
        });
        mnuShoulderU.add(mniSLeftU);

        mniSRightU.setText("SRight[U]");
        mniSRightU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniSRightUActionPerformed(evt);
            }
        });
        mnuShoulderU.add(mniSRightU);

        mnuUnheld.add(mnuShoulderU);

        mnuPadU.setText("Pad");

        mniTriangleU.setText("Triangle[U]");
        mniTriangleU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTriangleUActionPerformed(evt);
            }
        });
        mnuPadU.add(mniTriangleU);

        mniCrossU.setText("Cross[U]");
        mniCrossU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniCrossUActionPerformed(evt);
            }
        });
        mnuPadU.add(mniCrossU);

        mniSquareU.setText("Square[U]");
        mniSquareU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniSquareUActionPerformed(evt);
            }
        });
        mnuPadU.add(mniSquareU);

        mniCircleU.setText("Circle[U]");
        mniCircleU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniCircleUActionPerformed(evt);
            }
        });
        mnuPadU.add(mniCircleU);

        mnuUnheld.add(mnuPadU);

        mnuDPadU.setText("D Pad");

        mniDUpU.setText("DUp[U]");
        mniDUpU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDUpUActionPerformed(evt);
            }
        });
        mnuDPadU.add(mniDUpU);

        mniDDownU.setText("DDown[U]");
        mniDDownU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDDownUActionPerformed(evt);
            }
        });
        mnuDPadU.add(mniDDownU);

        mniDLeftU.setText("DLeft[U]");
        mniDLeftU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDLeftUActionPerformed(evt);
            }
        });
        mnuDPadU.add(mniDLeftU);

        mniDRightU.setText("DRight[U]");
        mniDRightU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDRightUActionPerformed(evt);
            }
        });
        mnuDPadU.add(mniDRightU);

        mnuUnheld.add(mnuDPadU);

        mnuControlU.setText("Control");

        mnuNoteU.setText("Note[U]");
        mnuNoteU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNoteUActionPerformed(evt);
            }
        });
        mnuControlU.add(mnuNoteU);

        mnuSelectU.setText("Select[U]");
        mnuSelectU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSelectUActionPerformed(evt);
            }
        });
        mnuControlU.add(mnuSelectU);

        mnuStartU.setText("Start[U]");
        mnuStartU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuStartUActionPerformed(evt);
            }
        });
        mnuControlU.add(mnuStartU);

        mnuHomeU.setText("Home[U]");
        mnuHomeU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuHomeUActionPerformed(evt);
            }
        });
        mnuControlU.add(mnuHomeU);

        mnuHoldU.setText("Hold[U]");
        mnuHoldU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuHoldUActionPerformed(evt);
            }
        });
        mnuControlU.add(mnuHoldU);

        mnuUnheld.add(mnuControlU);

        mnuButtons.add(mnuUnheld);

        mnuInput.add(mnuButtons);

        pumEvents.add(mnuInput);

        mnuCollision.setText("Collision");
        pumEvents.add(mnuCollision);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Object Properties");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/pspconstructor/Images/Object.png"))); // NOI18N
        setMinimumSize(new java.awt.Dimension(527, 327));

        pnlInput.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlInput.setMinimumSize(new java.awt.Dimension(150, 300));
        pnlInput.setPreferredSize(new java.awt.Dimension(150, 300));

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pspconstructor/Images/Add.png"))); // NOI18N
        btnAdd.setText("Add Event");
        btnAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddMouseClicked(evt);
            }
        });
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pspconstructor/Images/Check.png"))); // NOI18N
        btnOk.setText("Ok");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        cboSprite.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No Sprite" }));
        cboSprite.setMaximumSize(new java.awt.Dimension(136, 26));
        cboSprite.setMinimumSize(new java.awt.Dimension(136, 26));
        cboSprite.setPreferredSize(new java.awt.Dimension(136, 26));

        javax.swing.GroupLayout pnlInputLayout = new javax.swing.GroupLayout(pnlInput);
        pnlInput.setLayout(pnlInputLayout);
        pnlInputLayout.setHorizontalGroup(
            pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInputLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboSprite, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOk, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                    .addGroup(pnlInputLayout.createSequentialGroup()
                        .addComponent(btnDelete)
                        .addGap(2, 2, 2)
                        .addComponent(btnClear, 0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlInputLayout.setVerticalGroup(
            pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInputLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboSprite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btnAdd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelete)
                    .addComponent(btnClear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 176, Short.MAX_VALUE)
                .addComponent(btnOk)
                .addContainerGap())
        );

        tbpActions.setTabPlacement(javax.swing.JTabbedPane.RIGHT);
        tbpActions.setMaximumSize(new java.awt.Dimension(222, 32767));
        tbpActions.setMinimumSize(new java.awt.Dimension(222, 5));
        tbpActions.setPreferredSize(new java.awt.Dimension(222, 300));

        tbpEvents.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tbpEvents.setAutoscrolls(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tbpEvents, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tbpActions, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlInput, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
            .addComponent(tbpActions, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
            .addComponent(tbpEvents, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        if (cboSprite.getSelectedIndex() != 0) {
            GCHandler.Objects.get(Index).setSprite(GCHandler.Sprites.get(cboSprite.getSelectedIndex() - 1));
            GCHandler.Objects.get(Index).Sprite.ID = GCHandler.Sprites.get(cboSprite.getSelectedIndex() - 1).ID;
        } else {
            GCHandler.Objects.get(Index).setSprite(new GCSprite(""));
            GCHandler.Objects.get(Index).Sprite.ID = -1;
        }
        Main.logMain.repaint();
        this.dispose();
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddMouseClicked
        btnAdd.getComponentPopupMenu().show(btnAdd, evt.getX(), evt.getY());
    }//GEN-LAST:event_btnAddMouseClicked

    private void mniDUpPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDUpPActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetButtonClick(PSP_CTRL_UP)"));
    }//GEN-LAST:event_mniDUpPActionPerformed

    private void mniDDownPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDDownPActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetButtonClick(PSP_CTRL_DOWN)"));
    }//GEN-LAST:event_mniDDownPActionPerformed

    private void mniDLeftPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDLeftPActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetButtonClick(PSP_CTRL_LEFT)"));
    }//GEN-LAST:event_mniDLeftPActionPerformed

    private void mniDUpHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDUpHActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetButtonState(PSP_CTRL_UP)"));
    }//GEN-LAST:event_mniDUpHActionPerformed

    private void mniDDownHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDDownHActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetButtonState(PSP_CTRL_DOWN)"));
    }//GEN-LAST:event_mniDDownHActionPerformed

    private void mniDLeftHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDLeftHActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetButtonState(PSP_CTRL_LEFT)"));
    }//GEN-LAST:event_mniDLeftHActionPerformed

    private void mniDUpUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDUpUActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "!JGE::GetInstance()->GetButtonState(PSP_CTRL_UP)"));
    }//GEN-LAST:event_mniDUpUActionPerformed

    private void mniDDownUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDDownUActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "!JGE::GetInstance()->GetButtonState(PSP_CTRL_DOWN)"));
    }//GEN-LAST:event_mniDDownUActionPerformed

    private void mniDLeftUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDLeftUActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "!JGE::GetInstance()->GetButtonState(PSP_CTRL_LEFT)"));
    }//GEN-LAST:event_mniDLeftUActionPerformed

    private void mniSLeftPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniSLeftPActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetButtonClick(PSP_CTRL_LTRIGGER)"));
    }//GEN-LAST:event_mniSLeftPActionPerformed

    private void mniSRightPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniSRightPActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetButtonClick(PSP_CTRL_RTRIGGER)"));
    }//GEN-LAST:event_mniSRightPActionPerformed

    private void mniTrianglePActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniTrianglePActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetButtonClick(PSP_CTRL_TRIANGLE)"));
    }//GEN-LAST:event_mniTrianglePActionPerformed

    private void mniCrossPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniCrossPActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetButtonClick(PSP_CTRL_CROSS)"));
    }//GEN-LAST:event_mniCrossPActionPerformed

    private void mniSquarePActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniSquarePActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetButtonClick(PSP_CTRL_SQUARE)"));
    }//GEN-LAST:event_mniSquarePActionPerformed

    private void mniCirclePActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniCirclePActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetButtonClick(PSP_CTRL_CIRCLE)"));
    }//GEN-LAST:event_mniCirclePActionPerformed

    private void mniDRightPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDRightPActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetButtonClick(PSP_CTRL_RIGHT)"));
    }//GEN-LAST:event_mniDRightPActionPerformed

    private void mniSLeftHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniSLeftHActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetButtonState(PSP_CTRL_LTRIGGER)"));
    }//GEN-LAST:event_mniSLeftHActionPerformed

    private void mniSRightHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniSRightHActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetButtonState(PSP_CTRL_RTRIGGER)"));
    }//GEN-LAST:event_mniSRightHActionPerformed

    private void mniTriangleHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniTriangleHActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetButtonState(PSP_CTRL_TRIANGLE)"));
    }//GEN-LAST:event_mniTriangleHActionPerformed

    private void mniCrossHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniCrossHActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetButtonState(PSP_CTRL_CROSS)"));
    }//GEN-LAST:event_mniCrossHActionPerformed

    private void mniSquareHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniSquareHActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetButtonState(PSP_CTRL_SQUARE)"));
    }//GEN-LAST:event_mniSquareHActionPerformed

    private void mniCircleHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniCircleHActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetButtonState(PSP_CTRL_CIRCLE)"));
    }//GEN-LAST:event_mniCircleHActionPerformed

    private void mniDRightHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDRightHActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetButtonState(PSP_CTRL_RIGHT)"));
    }//GEN-LAST:event_mniDRightHActionPerformed

    private void mnuNoteHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNoteHActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetButtonState(PSP_CTRL_NOTE)"));
    }//GEN-LAST:event_mnuNoteHActionPerformed

    private void mnuSelectHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSelectHActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetButtonState(PSP_CTRL_SELECT)"));
    }//GEN-LAST:event_mnuSelectHActionPerformed

    private void mnuStartHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuStartHActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetButtonState(PSP_CTRL_START)"));
    }//GEN-LAST:event_mnuStartHActionPerformed

    private void mnuHomeHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuHomeHActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetButtonState(PSP_CTRL_HOME)"));
    }//GEN-LAST:event_mnuHomeHActionPerformed

    private void mnuHoldHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuHoldHActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetButtonState(PSP_CTRL_HOLD)"));
    }//GEN-LAST:event_mnuHoldHActionPerformed

    private void mniSLeftUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniSLeftUActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "!JGE::GetInstance()->GetButtonState(PSP_CTRL_LTRIGGER)"));
    }//GEN-LAST:event_mniSLeftUActionPerformed

    private void mniSRightUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniSRightUActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "!JGE::GetInstance()->GetButtonState(PSP_CTRL_RTRIGGER)"));
    }//GEN-LAST:event_mniSRightUActionPerformed

    private void mniTriangleUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniTriangleUActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "!JGE::GetInstance()->GetButtonState(PSP_CTRL_TRIANGLE)"));
    }//GEN-LAST:event_mniTriangleUActionPerformed

    private void mniCrossUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniCrossUActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "!JGE::GetInstance()->GetButtonState(PSP_CTRL_CROSS)"));
    }//GEN-LAST:event_mniCrossUActionPerformed

    private void mniSquareUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniSquareUActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "!JGE::GetInstance()->GetButtonState(PSP_CTRL_SQUARE)"));
    }//GEN-LAST:event_mniSquareUActionPerformed

    private void mniCircleUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniCircleUActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "!JGE::GetInstance()->GetButtonState(PSP_CTRL_CIRCLE)"));
    }//GEN-LAST:event_mniCircleUActionPerformed

    private void mniDRightUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDRightUActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "!JGE::GetInstance()->GetButtonState(PSP_CTRL_RIGHT)"));
    }//GEN-LAST:event_mniDRightUActionPerformed

    private void mnuNoteUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNoteUActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "!JGE::GetInstance()->GetButtonState(PSP_CTRL_NOTE)"));
    }//GEN-LAST:event_mnuNoteUActionPerformed

    private void mnuSelectUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSelectUActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "!JGE::GetInstance()->GetButtonState(PSP_CTRL_SELECT)"));
    }//GEN-LAST:event_mnuSelectUActionPerformed

    private void mnuStartUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuStartUActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "!JGE::GetInstance()->GetButtonState(PSP_CTRL_START)"));
    }//GEN-LAST:event_mnuStartUActionPerformed

    private void mnuHomeUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuHomeUActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "!JGE::GetInstance()->GetButtonState(PSP_CTRL_HOME)"));
    }//GEN-LAST:event_mnuHomeUActionPerformed

    private void mnuHoldUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuHoldUActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "!JGE::GetInstance()->GetButtonState(PSP_CTRL_HOLD)"));
    }//GEN-LAST:event_mnuHoldUActionPerformed

    private void mniAUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniAUpActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetAnalogY()<100"));
    }//GEN-LAST:event_mniAUpActionPerformed

    private void mniADownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniADownActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetAnalogY()>155"));
    }//GEN-LAST:event_mniADownActionPerformed

    private void mniALeftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniALeftActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetAnalogX()<100"));
    }//GEN-LAST:event_mniALeftActionPerformed

    private void mniARightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniARightActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetAnalogX()>155"));
    }//GEN-LAST:event_mniARightActionPerformed

    private void mnuHoldPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuHoldPActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetButtonClick(PSP_CTRL_HOLD)"));
}//GEN-LAST:event_mnuHoldPActionPerformed

    private void mnuHomePActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuHomePActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetButtonClick(PSP_CTRL_HOME)"));
}//GEN-LAST:event_mnuHomePActionPerformed

    private void mnuStartPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuStartPActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetButtonClick(PSP_CTRL_START)"));
}//GEN-LAST:event_mnuStartPActionPerformed

    private void mnuSelectPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSelectPActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetButtonClick(PSP_CTRL_SELECT)"));
}//GEN-LAST:event_mnuSelectPActionPerformed

    private void mnuNotePActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNotePActionPerformed
        addEvent(evt.getActionCommand());
        GCHandler.Objects.get(Index).Actions.add(new ArrayList<Action>());
        GCHandler.Objects.get(Index).Events.add(new Event(evt.getActionCommand(), "JGE::GetInstance()->GetButtonClick(PSP_CTRL_NOTE)"));
}//GEN-LAST:event_mnuNotePActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        GCHandler.Objects.get(Index).Actions.get(tbpEvents.getSelectedIndex()).remove(lstEvents.get(tbpEvents.getSelectedIndex()).getSelectedIndex());
        lmlEvents.get(tbpEvents.getSelectedIndex()).removeElementAt(lstEvents.get(tbpEvents.getSelectedIndex()).getSelectedIndex());
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        GCHandler.Objects.get(Index).Actions.get(tbpEvents.getSelectedIndex()).clear();
        lmlEvents.get(tbpEvents.getSelectedIndex()).clear();
    }//GEN-LAST:event_btnClearActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnOk;
    public static javax.swing.JComboBox cboSprite;
    private javax.swing.JMenuItem mniADown;
    private javax.swing.JMenuItem mniALeft;
    private javax.swing.JMenuItem mniARight;
    private javax.swing.JMenuItem mniAUp;
    private javax.swing.JMenuItem mniCircleH;
    private javax.swing.JMenuItem mniCircleP;
    private javax.swing.JMenuItem mniCircleU;
    private javax.swing.JMenuItem mniCrossH;
    private javax.swing.JMenuItem mniCrossP;
    private javax.swing.JMenuItem mniCrossU;
    private javax.swing.JMenuItem mniDDownH;
    private javax.swing.JMenuItem mniDDownP;
    private javax.swing.JMenuItem mniDDownU;
    private javax.swing.JMenuItem mniDLeftH;
    private javax.swing.JMenuItem mniDLeftP;
    private javax.swing.JMenuItem mniDLeftU;
    private javax.swing.JMenuItem mniDRightH;
    private javax.swing.JMenuItem mniDRightP;
    private javax.swing.JMenuItem mniDRightU;
    private javax.swing.JMenuItem mniDUpH;
    private javax.swing.JMenuItem mniDUpP;
    private javax.swing.JMenuItem mniDUpU;
    private javax.swing.JMenuItem mniSLeftH;
    private javax.swing.JMenuItem mniSLeftP;
    private javax.swing.JMenuItem mniSLeftU;
    private javax.swing.JMenuItem mniSRightH;
    private javax.swing.JMenuItem mniSRightP;
    private javax.swing.JMenuItem mniSRightU;
    private javax.swing.JMenuItem mniSquareH;
    private javax.swing.JMenuItem mniSquareP;
    private javax.swing.JMenuItem mniSquareU;
    private javax.swing.JMenuItem mniTriangleH;
    private javax.swing.JMenuItem mniTriangleP;
    private javax.swing.JMenuItem mniTriangleU;
    private javax.swing.JMenu mnuAnalog;
    private javax.swing.JMenu mnuButtons;
    private javax.swing.JMenu mnuCollision;
    private javax.swing.JMenu mnuControlH;
    private javax.swing.JMenu mnuControlP;
    private javax.swing.JMenu mnuControlU;
    private javax.swing.JMenu mnuDPadH;
    private javax.swing.JMenu mnuDPadP;
    private javax.swing.JMenu mnuDPadU;
    private javax.swing.JMenu mnuHeld;
    private javax.swing.JMenuItem mnuHoldH;
    private javax.swing.JMenuItem mnuHoldP;
    private javax.swing.JMenuItem mnuHoldU;
    private javax.swing.JMenuItem mnuHomeH;
    private javax.swing.JMenuItem mnuHomeP;
    private javax.swing.JMenuItem mnuHomeU;
    private javax.swing.JMenu mnuInput;
    private javax.swing.JMenuItem mnuNoteH;
    private javax.swing.JMenuItem mnuNoteP;
    private javax.swing.JMenuItem mnuNoteU;
    private javax.swing.JMenu mnuPadH;
    private javax.swing.JMenu mnuPadP;
    private javax.swing.JMenu mnuPadU;
    private javax.swing.JMenu mnuPressed;
    private javax.swing.JMenuItem mnuSelectH;
    private javax.swing.JMenuItem mnuSelectP;
    private javax.swing.JMenuItem mnuSelectU;
    private javax.swing.JMenu mnuShoulderH;
    private javax.swing.JMenu mnuShoulderP;
    private javax.swing.JMenu mnuShoulderU;
    private javax.swing.JMenuItem mnuStartH;
    private javax.swing.JMenuItem mnuStartP;
    private javax.swing.JMenuItem mnuStartU;
    private javax.swing.JMenu mnuUnheld;
    private javax.swing.JPanel pnlInput;
    private javax.swing.JPopupMenu pumEvents;
    private javax.swing.JTabbedPane tbpActions;
    private javax.swing.JTabbedPane tbpEvents;
    // End of variables declaration//GEN-END:variables
}

class cboSpriteRenderer extends JLabel implements ListCellRenderer {

    public cboSpriteRenderer() {
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
        setPreferredSize(new Dimension(100, 24));
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        if (index == -1) {
            if (ObjectProperties.cboSprite.getSelectedIndex() == 0) {
                setIcon(new ImageIcon(new GCSprite("").Image.getImage().getScaledInstance(16, 16, Image.SCALE_AREA_AVERAGING)));
            } else {
                setIcon(new ImageIcon(GCHandler.Sprites.get(ObjectProperties.cboSprite.getSelectedIndex() - 1).Image.getImage().getScaledInstance(16, 16, Image.SCALE_AREA_AVERAGING)));
            }
            setText(String.valueOf(value));
        } else if (index == 0) {
            setIcon(new ImageIcon(new GCSprite("").Image.getImage().getScaledInstance(16, 16, Image.SCALE_AREA_AVERAGING)));
            setText(String.valueOf(value));
        } else {
            setIcon(new ImageIcon(GCHandler.Sprites.get(index - 1).Image.getImage().getScaledInstance(16, 16, Image.SCALE_AREA_AVERAGING)));
            setText(String.valueOf(value));
        }
        return this;
    }
}
