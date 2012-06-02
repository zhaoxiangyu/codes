package edu.ncsu.csc517.dpp11.gui;
/*
 * Copyright (C) 2002 by Yi Gao, James E. Scarborough,
 * Scott Shindeldecker, and Minjie Ye
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

import edu.ncsu.csc517.dpp11.data.Puzzle;
import edu.ncsu.csc517.dpp11.data.AngryPuzzle;
import edu.ncsu.csc517.dpp11.data.PuzzleStub;
import edu.ncsu.csc517.dpp11.data.MapTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.lang.reflect.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

/**  
 * Screen offering a display of the puzzle.
 *
 * @author Scott Shindeldecker
 * @author James E. Scarborough
 */
public class PuzzleScreen extends javax.swing.JComponent implements
Printable{
    private JMenuBar menuBar;
    private PuzzleDisplay puzzleDisplay;
    private Puzzle puzzle;
    private File file=null;
    private final JFileChooser fc = new JFileChooser();
    private CrosswordManager mgr;
    private Map statefulMenuItemList=new HashMap();

    public PuzzleScreen(CrosswordManager mgr) throws NoSuchMethodException {
        this.mgr=mgr;
        setLayout(new BorderLayout());
        add(getMenuBar(),BorderLayout.NORTH);
    }

    /**
     * For testing only.
     */
    private PuzzleScreen(Puzzle p) throws NoSuchMethodException {
        this((CrosswordManager)null);
        setPuzzle(p);
    }

    public void initialize(Map m) {
        if (m.containsKey("puzzle")) {
            Puzzle p=(Puzzle)m.get("puzzle");
            System.out.println("Setting puzzle");
            setPuzzle(p);
            getStatefulMenuItem("Show Key").setSelected(false);
            setKeyShown(false);
            getStatefulMenuItem("Show Clues").setSelected(true);
            setCluesShown(true);
        } else {
            load((File)m.get("file"));
        }
    }

    private JCheckBoxMenuItem getStatefulMenuItem(String name) {
        return (JCheckBoxMenuItem)statefulMenuItemList.get(name);
    }

    public PuzzleDisplay getPuzzleDisplay() {
        if (puzzleDisplay==null) {
            puzzleDisplay=new PuzzleDisplay(puzzle);
        }
        return puzzleDisplay;
    }

    public void setPuzzle(Puzzle p) {
        puzzle=p;
        PuzzleDisplay pd=getPuzzleDisplay();
        if(pd!=null) {
            remove(pd);
            puzzleDisplay=null;
        }
        add(getPuzzleDisplay(),BorderLayout.CENTER);
        // Shouldn't we update the clues, too?
    }

    public void setCluesShown(boolean s) {
        getPuzzleDisplay().setCluesShown(s);
    }

    public void setKeyShown(boolean k){
        getPuzzleDisplay().setKeysShown(k);
    }

    private Component getMenuBar() throws NoSuchMethodException {
        if (menuBar==null) {
            menuBar=new JMenuBar();

            JMenu menu = addMenu(menuBar,"File",KeyEvent.VK_F,
                "Load and save word lists");
            addMenuItem(menu,"Create New Puzzle",
                                            KeyEvent.VK_N,null,"newPuzzle");
            addMenuItem(menu,"Load Puzzle...",KeyEvent.VK_O,null,"load");
            addMenuItem(menu,"Save Puzzle As...", KeyEvent.VK_S,null,"saveAs");
            addMenuItem(menu,"Print...",KeyEvent.VK_P,null,"printScreen");
            addMenuItem(menu,"Exit",KeyEvent.VK_X,null,"exit");

            menu = addMenu(menuBar,"Edit",KeyEvent.VK_E,
                    "Edit the words and clues");
            addMenuItem(menu,"Edit Current Puzzle",KeyEvent.VK_E,null,"edit");

            menu = addMenu(menuBar,"View",KeyEvent.VK_V,
                    "Change display options..");
            addCheckBoxMenuItem(menu,"Show Key",KeyEvent.VK_K,null,
                    "showKeys", false);
            addCheckBoxMenuItem(menu,"Show Clues",KeyEvent.VK_C,null,
                    "showClues", true);
        }
        return menuBar;
    }

    private JMenu addMenu(JMenuBar menuBar,String label,int mnemonic,
        String accessibleDescription) {
        JMenu menu = new JMenu(label);
        menu.setMnemonic(mnemonic);
        menu.getAccessibleContext().setAccessibleDescription(
        accessibleDescription);
        menuBar.add(menu);
        return menu;
    }

    private void addCheckBoxMenuItem(JMenu menu, String label,
        int mnemonic, String accessibleDescription,
        String itemCallbackName, boolean initialState)
        throws NoSuchMethodException {
        JCheckBoxMenuItem checkBoxMenuItem =
            new JCheckBoxMenuItem(label,initialState);
        statefulMenuItemList.put(label,checkBoxMenuItem);
        checkBoxMenuItem.setMnemonic(mnemonic);
        checkBoxMenuItem.getAccessibleContext().setAccessibleDescription(
        (accessibleDescription!=null)?accessibleDescription:label);
        final Method callback=getClass().getMethod(itemCallbackName,
              new Class[] { ItemEvent.class });
        checkBoxMenuItem.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                try {
                    callback.invoke(PuzzleScreen.this,new Object[] {e});
                } catch (InvocationTargetException ex) {
                    handleException(ex.getTargetException());
                } catch (IllegalAccessException ex) {
                    handleException(ex);
                }
            }
        });
        menu.add(checkBoxMenuItem);

    }

    private void addMenuItem(JMenu menu, String label,
        int mnemonic, String accessibleDescription,
        String actionCallbackName)throws NoSuchMethodException {
        JMenuItem menuItem = new JMenuItem(label,mnemonic);
        menuItem.getAccessibleContext().setAccessibleDescription(
        (accessibleDescription!=null)?accessibleDescription:label);
        final Method callback=getClass().getMethod(actionCallbackName,
              new Class[] { ActionEvent.class });
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    callback.invoke(PuzzleScreen.this,new Object[] {e});
                } catch (InvocationTargetException ex) {
                    ex.getTargetException().printStackTrace();
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        });
        menu.add(menuItem);
    }


    public void saveAs(ActionEvent e) {
        // Prompt
        int returnVal = fc.showSaveDialog(this);
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }
        file = fc.getSelectedFile();
        try {
        // Save
            BufferedOutputStream bos=new BufferedOutputStream(
            new FileOutputStream(file)
            );
            ObjectOutputStream oos=new ObjectOutputStream(bos);
            oos.writeObject(puzzle);
            oos.flush();
            oos.close();
        } catch (IOException ex) {
            handleException(ex);
        }
    }

    public void load(ActionEvent e) {
        // Prompt
        int returnVal = fc.showOpenDialog(this);
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }
        file = fc.getSelectedFile();
        load(file);
     }

     public void load(File f) {
        // Load
        try {
            BufferedInputStream bis=new BufferedInputStream(
            new FileInputStream(f)
            );
            ObjectInputStream ois=new ObjectInputStream(bis);
            setPuzzle((Puzzle)ois.readObject());
            //testLoad();
            ois.close();
            System.out.println(puzzle.getWordList());
            //invalidate();
            //repaint();
            ((Frame)mgr.getTop()).pack(); // XXXXX <- Why?  This isn't right.
        } catch (IOException ex) {
            handleException(ex);
        } catch (ClassNotFoundException ex) {
            handleException(ex);
//        } catch (NoSuchMethodException ex) {
  //          handleException(ex);
        }
    }

    public void printScreen(ActionEvent e) {
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(PuzzleScreen.this);
        if (pj.printDialog()) {
            try { pj.print(); }
            catch (PrinterException pe) {
                System.out.println(pe);
            }
        }
    }

    public int print(Graphics g, PageFormat pf, int pageIndex) {
        if (pageIndex != 0) return NO_SUCH_PAGE;
        Graphics2D g2 = (Graphics2D)g;
        g2.translate(pf.getImageableX(), pf.getImageableY());
        getPuzzleDisplay().paint(g2);
        return PAGE_EXISTS;
    }

    public void newPuzzle(ActionEvent e) {
        mgr.switchScreens(new HashMap());
    }

    public void edit(ActionEvent e) {
        Map m=new HashMap();
        m.put("wordList",puzzle.getWordList());
        System.out.println(puzzle.getWordList());
        mgr.switchScreens(m);
    }

    public void exit(ActionEvent e) {
        System.exit(0);
    }

    public void showKeys(ItemEvent e) {
        int state=e.getStateChange();
        setKeyShown(state==ItemEvent.SELECTED);
    }

    public void showClues(ItemEvent e) {
        int state=e.getStateChange();
        setCluesShown(state==ItemEvent.SELECTED);
    }

    private void handleException(Throwable ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, ex.toString(),
            ex.getClass().getName(), JOptionPane.ERROR_MESSAGE);        
    }

    /**
     * What's this about?
    public void testLoad() throws NoSuchMethodException {
        f=new JFrame();
        Container c=f.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(new PuzzleScreen(puzzle),BorderLayout.CENTER);
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showFrame(true);
    }
     */
/*
    public static void showFrame (boolean show) {
       f.setVisible(show);
       f.repaint();
       return;
    }
*/
    public static void main(String[] argv) throws NoSuchMethodException {
        JFrame f=new JFrame();
        Container c=f.getContentPane();
        c.setLayout(new BorderLayout());
        long seed=0;
        if (argv.length>0) {
            seed=Long.parseLong(argv[0]);
        }
        c.add(new PuzzleScreen(new PuzzleStub(seed)),BorderLayout.CENTER);
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

}

