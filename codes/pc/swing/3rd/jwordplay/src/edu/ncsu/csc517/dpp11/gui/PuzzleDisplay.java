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


import javax.swing.JComponent;
import edu.ncsu.csc517.dpp11.data.Puzzle;
import edu.ncsu.csc517.dpp11.data.PuzzleStub;
import java.awt.*;
import javax.swing.*;

/**
 * Shows the PuzzleBox and clues.
 *
 * @author Scott Shindeldecker
 * @author James E. Scarborough
 */
public class PuzzleDisplay extends JComponent {
    private PuzzleBox puzzleBox;
    private ClueBox clueBox;
    private Puzzle puzzle;

    public PuzzleDisplay(Puzzle p) {
        puzzle=p;
        GridBagLayout gbl=new GridBagLayout();
        setLayout(gbl);
        GridBagConstraints gbc=new GridBagConstraints();

        gbc.weightx=gbc.weighty=1;
        gbc.gridx=gbc.gridy=0;
        PuzzleBox pb=getPuzzleBox();
        gbl.setConstraints(pb,gbc);
        add(pb);

        gbc.gridy++;
        ClueBox cb=getClueBox();
        gbl.setConstraints(cb,gbc);
        add(cb);
    }

    private PuzzleBox getPuzzleBox() {
        if (puzzleBox==null) {
            puzzleBox=new PuzzleBox(getPuzzle());
        }
        return puzzleBox;
    }

    private ClueBox getClueBox() {
        if (clueBox==null) {
            clueBox=new ClueBox(getPuzzle());
        }
        return clueBox;
    }

    public boolean areCluesShown() {
        return getClueBox().isVisible();
    }

    public void setCluesShown(boolean cs) {
        getClueBox().setVisible(cs);
    }

    public boolean areKeysShown() {
        return getPuzzleBox().areKeysShown();
    }

    public void setKeysShown(boolean s) {
        getPuzzleBox().setKeysShown(s);
    }

    public void print() {
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public static void main(String argv[]) {
        JFrame f=new JFrame();
        Container c=f.getContentPane();
        c.setLayout(new BorderLayout());
        final PuzzleDisplay pd =new PuzzleDisplay(new PuzzleStub());
        Thread t=new Thread() {
            public void run() {
                boolean keys=true;
                while (true) {
                    if (keys)
                        pd.setKeysShown(!pd.areKeysShown());
                    else
                        pd.setCluesShown(!pd.areCluesShown());
                    keys=!keys;
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException insomnia) {}
                }
            }
        };
        t.setDaemon(true);
        t.start();
        c.add(pd);
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

}
