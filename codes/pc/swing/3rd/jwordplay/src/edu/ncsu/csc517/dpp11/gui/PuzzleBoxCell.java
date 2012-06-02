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

import javax.swing.*;
import java.awt.*;

import edu.ncsu.csc517.dpp11.data.PuzzleCell;
import edu.ncsu.csc517.dpp11.data.PuzzleCellImpl;

/**
 * @author Scott Shindeldecker
 * @author James E. Scarborough
 */
public class PuzzleBoxCell extends JComponent {
    private static final Font NUMBER_FONT=new Font("SansSerif",Font.PLAIN,10);
    private static final Font KEY_FONT=new Font("SansSerif",Font.PLAIN,16);
    private boolean keyShown;
    private PuzzleCell cell;
    private char[] numCa, keyCa;

    public PuzzleBoxCell(PuzzleCell c) {
        cell=c;
        String ui=String.valueOf(c.getUserIndex());
        numCa=new char[ui.length()];
        ui.getChars(0,ui.length(),numCa,0);

        keyCa=new char[]{ (char)c.getContents() };
    }

    public void paintComponent(Graphics g) {
        Dimension size=getSize();
        g.setColor(Color.black);
        if (cell.getContents()==-1) { // blank
            g.fillRect(0,0,size.width,size.height);
           // draw black box
        } else {
            g.drawRect(0,0,size.width-1,size.height-1);
            if (cell.getUserIndex()>=0) {
                g.setFont(NUMBER_FONT);
                int ascent=g.getFontMetrics(NUMBER_FONT).getAscent();
                g.drawChars(numCa,0,numCa.length,2,2+ascent);
            }
            if (keyShown) {
                g.setFont(KEY_FONT);
                FontMetrics fm=g.getFontMetrics(KEY_FONT);
                int ascent=fm.getAscent();
                int width=fm.charsWidth(keyCa,0,keyCa.length);
                g.drawChars(keyCa,0,keyCa.length,
                    (size.width-width)/2,
                    // i said height/2 + ascent/2 so...
                    (size.height+ascent)/2
                );
            }
        }

    }

    public void setKeyShown(boolean ks) {
        keyShown=ks;
    }
    
    public boolean isKeyShown() {
        return keyShown;
    }

    public Dimension getPreferredSize() {
        return new Dimension(33,33);
    }

    public static void main(String argv[]) {
        JFrame f=new JFrame();
        Container c=f.getContentPane();
        c.setLayout(new GridLayout(10,10));
        int letter=(int)'A';
        for (int i=0;i<10;i++) {
            for (int j=0;j<10;j++) {
                int showLetter=-1;
                if (letter%3!=0) {
                    showLetter=letter;
                }
                letter++;
                PuzzleCell pc=new PuzzleCellImpl(
                    showLetter,letter-(int)'A'
                );
                PuzzleBoxCell pbc=new PuzzleBoxCell(pc);
                pbc.setKeyShown(true);
                c.add(pbc);
            }
        }
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

    }
}
