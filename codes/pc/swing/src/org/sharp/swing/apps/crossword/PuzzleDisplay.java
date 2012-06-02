package org.sharp.swing.apps.crossword;
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


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;

import edu.ncsu.csc517.dpp11.data.Puzzle;
import edu.ncsu.csc517.dpp11.gui.ClueBox;
import edu.ncsu.csc517.dpp11.gui.PuzzleBox;

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

        gbc.gridx++;
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

}
