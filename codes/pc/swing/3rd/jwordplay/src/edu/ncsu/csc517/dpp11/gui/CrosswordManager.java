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

import java.awt.*;
import javax.swing.*;
import java.util.*;

/**
 * Useful for Application and Applet versions...
 *
 * @author Scott Shindeldecker
 * @author James E. Scarborough
 */
public class CrosswordManager extends JComponent {
    private PuzzleScreen puzzleScreen;
    private WordListScreen wordListScreen;
    private boolean wordList=true;

    public CrosswordManager() throws NoSuchMethodException {
        setLayout(new CardLayout());
        
        wordListScreen=new WordListScreen(this);
        add("wls",getWordListScreen());

        puzzleScreen=new PuzzleScreen(this);
        add("ps",getPuzzleScreen());

    }

    PuzzleScreen getPuzzleScreen() {
        return puzzleScreen;
    }

    WordListScreen getWordListScreen() {
        return wordListScreen;
    }

    void switchScreens(Map data) {
        wordList=!wordList;
        if (wordList) {
            getWordListScreen().initialize(data);
        } else {
            getPuzzleScreen().initialize(data);
        }
        ((CardLayout)getLayout()).show(this,wordList?"wls":"ps");
        Container parent=getTop();
        if (parent instanceof Frame) {
            System.out.println("packing");
            ((Frame)parent).pack();
        }
    }

    Container getTop() {
        return getTop(this);
    }

    private Container getTop(Container parent) {
        Container p2=parent.getParent();
        if (p2!=null)
            return getTop(p2);
        else
            return parent;
    }



    public static void main (String[] argv) throws NoSuchMethodException{
        JFrame f=new JFrame();
        f.setTitle("Crossword Generator");
        Container c=f.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(new CrosswordManager(),BorderLayout.CENTER);
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

}

