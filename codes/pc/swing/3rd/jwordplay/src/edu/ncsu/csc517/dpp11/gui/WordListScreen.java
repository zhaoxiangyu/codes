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

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.lang.reflect.*;
import java.io.*;
import edu.ncsu.csc517.dpp11.data.MapTableModel;
import edu.ncsu.csc517.dpp11.data.Puzzle;
import edu.ncsu.csc517.dpp11.data.AngryPuzzle;

/**
 * Screen to allow word list viewing and editing, from which 
 * puzzles can be generated.
 *
 * @author Scott Shindeldecker
 * @author James E. Scarborough
 */
public class WordListScreen extends javax.swing.JComponent {
    private String[] COLUMN_NAMES=new String[]{ "Word", "Clue" };
    private Component tableView;
    private JMenuBar menuBar;
    private Properties wordList;
    private MapTableModel tableModel;
    private File file=null;
    private final JFileChooser fc = new JFileChooser();
    private final Random rand=new Random();
    private static JFrame f;
    private PuzzleScreen ps;
    private CrosswordManager mgr;

    public WordListScreen(CrosswordManager m) throws NoSuchMethodException {
        mgr=m;
        setLayout(new BorderLayout());
        add(getMenuBar(),BorderLayout.NORTH);
        add(getTable(),BorderLayout.CENTER);
    }

    void initialize(Map m) {
        System.out.println("Initializing");
        if (m.containsKey("wordList")) {
            System.out.println("SettingWordList");
            setWordList((Properties)m.get("wordList"));
        } else {
            setWordList(new Properties());
        }
    }

    private Object[][] importTableData() {
        return new Object[0][2];
    }

    private MapTableModel getTableModel() {
        if (tableModel==null) {
            tableModel=new MapTableModel(getWordList());
        }
        return tableModel;
    }

    private Properties getWordList() {
        if (wordList==null) {
            wordList=new Properties();
        }
        return wordList;
    }

    private void setWordList(Properties wl) {
        if (!getWordList().equals(wl)) {
            getWordList().clear();
            getWordList().putAll(wl);
            getTableModel().refresh();
        }
    }

    public void setTable(Properties w, MapTableModel t) {
       wordList = w;
       tableModel = t;
       f.repaint();
    }

    private TableColumnModel getTableColumnModel() {
        TableColumnModel tcm=new DefaultTableColumnModel();
        tcm.addColumn(genColumn(0,100,"Word"));
        tcm.addColumn(genColumn(1,400,"Clue"));
        return tcm;
    }

    private TableColumn genColumn(int modelIndex, int width, String label) {
        TableColumn tc=new TableColumn(modelIndex,width);
        tc.setHeaderValue(label);
        return tc;
    }

    private Component getTable() {
        if (tableView==null) {
            JTable table=new JTable(getTableModel(),getTableColumnModel());
            JScrollPane scrollPane = new JScrollPane(table);
            table.setPreferredScrollableViewportSize(new Dimension(500,
70));
            tableView=scrollPane;
        }
        return tableView;
    }

    public static void showFrame (boolean show) {
       f.setVisible(show);
       f.repaint();
       return;
    }

    private Component getMenuBar() throws NoSuchMethodException {
        if (menuBar==null) {
            menuBar=new JMenuBar();

            JMenu menu = addMenu(menuBar,"File",KeyEvent.VK_F,
                "Load and save word lists");
            addMenuItem(menu,"Load Words...",KeyEvent.VK_O,null,"load");
            addMenuItem(menu,"Save Words",KeyEvent.VK_S,null,"save");
            addMenuItem(menu,"Save Words As...", KeyEvent.VK_A,null,"saveAs");
            addMenuItem(menu,"Exit",KeyEvent.VK_X,null,"exit");

            menu = addMenu(menuBar,"Crossword",KeyEvent.VK_C,
                    "Generate crossword puzzles.");
            addMenuItem(menu,"Load Puzzle",KeyEvent.VK_P,null,
                    "loadPuzzle");

            addMenuItem(menu,"Generate Random Puzzle",KeyEvent.VK_R,null,
                    "genRandom");
            addMenuItem(menu,"Generate Puzzle by Seed",KeyEvent.VK_R,null,
                    "genSeed");
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

    public void save(ActionEvent e) {
        if (file == null) {
            saveAs(e);
        }
        try {
            OutputStream out=new BufferedOutputStream(
                new FileOutputStream(file)
            );
            wordList.store(out,"Generated by JWordPlay");
            out.flush();
            out.close();
        } catch (IOException ex) {
            handleException(ex);
        }
    }

    public void saveAs(ActionEvent e) {
        int returnVal = fc.showSaveDialog(this);
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }
        file = fc.getSelectedFile();
        save(e);
    }

    public void load(ActionEvent e) {
        int returnVal = fc.showOpenDialog(this);
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }
        file = fc.getSelectedFile();
        try {
            InputStream in=new BufferedInputStream(
                new FileInputStream(file)
            );
            Properties newWl=new Properties();
            newWl.load(in);
            in.close();
            setWordList(newWl);
        } catch (IOException ex) {
            handleException(ex);
        }
    }

    public void loadPuzzle(ActionEvent e) throws NoSuchMethodException{
        int returnVal = fc.showOpenDialog(this);
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }
        file = fc.getSelectedFile();
        Map m=new HashMap();
        m.put("file",file);
        mgr.switchScreens(m);
    }

    public void setPuzzleScreen(PuzzleScreen p) {
        ps = p;
    }

    public void genRandom(ActionEvent e) {
        genRandom();
    }

    public void genSeed(ActionEvent e) {
        long seed=0;
        boolean redo=false;
        do {
            redo=false;
            String seedString=JOptionPane.showInputDialog(
                this, "Enter a number:",
                Long.toString(rand.nextLong(),36)
                //"Random Seed", JOptionPane.QUESTION_MESSAGE
            );
            if (seedString==null) return;
            try {
                seed=Long.parseLong(seedString,36);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                    "Use only letters and numbers, max 12 characters."
                );
                redo=true;
            }
        } while (redo);
        genSeed(seed);
    }

    public void genRandom() {
        genSeed(rand.nextLong());
    }

    public void genSeed(long seed) {
        System.out.println(Long.toString(seed,36));
        showPuzzle(new AngryPuzzle(wordList,seed));
    }
    
    private void showPuzzle(Puzzle p) {
        Map m=new HashMap();
        m.put("puzzle",p);
        showPuzzle(m);
    }
    
    private void showPuzzle(File f) {
        Map m=new HashMap();
        m.put("file",f);
        showPuzzle(m);
    }
    
    private void showPuzzle(Map m) {
        mgr.switchScreens(m);
    }

    public void exit(ActionEvent e) {
        System.exit(0);
    }

    private void handleException(Throwable ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, ex.toString(),
            ex.getClass().getName(), JOptionPane.ERROR_MESSAGE);
    }

    private void addMenuItem(JMenu menu, String label,
        int mnemonic, String accessibleDescription,
        String actionCallbackName) throws NoSuchMethodException {
        JMenuItem menuItem = new JMenuItem(label,mnemonic);
        menuItem.getAccessibleContext().setAccessibleDescription(
        (accessibleDescription!=null)?accessibleDescription:label);
        final Method callback=getClass().getMethod(actionCallbackName,
              new Class[] { ActionEvent.class });
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    callback.invoke(WordListScreen.this,new Object[] {e});
                } catch (InvocationTargetException ex) {
                    handleException(ex.getTargetException());
                } catch (IllegalAccessException ex) {
                    handleException(ex);
                }
            }
        });
        menu.add(menuItem);
    }

    public static void main(String[] argv) throws NoSuchMethodException {
        f=new JFrame();
        Container c=f.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(new WordListScreen(null),BorderLayout.CENTER);
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showFrame(true);
    }

}

