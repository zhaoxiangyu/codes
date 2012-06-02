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
import edu.ncsu.csc517.dpp11.data.PuzzleStub;
import edu.ncsu.csc517.dpp11.data.PuzzleCell;


import javax.swing.*;
import java.awt.*;

/** 
 * Represents the grid of squares in which letters appear.
 *
 * @author Scott Shindeldecker
 * @author James E. Scarborough
 */
public class PuzzleBox extends JComponent {

    private Puzzle puzzle;

    public PuzzleBox(Puzzle p) {
        puzzle=p;
        Dimension size=p.getDimension();
        setLayout(new GridLayout(size.height,size.width));
        for (int i=0;i<size.height;i++) {
            for (int j=0;j<size.width;j++) {
                PuzzleCell pc=p.getCell(j,i);
                PuzzleBoxCell pbc=new PuzzleBoxCell(pc);
                pbc.setKeyShown(false);
                add(pbc);
            }
        }
    }

    public Puzzle getPuzzle(){return puzzle;}

    public boolean areKeysShown(){
        return ((PuzzleBoxCell)getComponent(0)).isKeyShown();
    }

    public void setKeysShown(boolean s) {
        if (s!=areKeysShown()) {
            Component[] c=getComponents();
            for (int i=0;i<c.length;i++) {
                ((PuzzleBoxCell)c[i]).setKeyShown(s);
            }
            repaint();
        }
    }

    public static void main(String argv[]) {
        JFrame f=new JFrame();
        Container c=f.getContentPane();
        c.setLayout(new BorderLayout());
        final PuzzleBox pb =new PuzzleBox(new PuzzleStub());
        Thread t=new Thread() {
            public void run() {
                while (true) {
                    pb.setKeysShown(!pb.areKeysShown());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException insomnia) {}
                }
            }
        };
        t.setDaemon(true);
        //t.start();
        c.add(pb);
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
