package edu.ncsu.csc517.dpp11.data;
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
import java.awt.Point;
import java.awt.Dimension;

/**
 * @author Scott Shindeldecker
 * @author James E. Scarborough
 */
public class PuzzleStub implements Puzzle {

    private PuzzleCell[][] a;

    /**
     * For testing.
     */

    public PuzzleStub() {
        this(0);
    }

    public PuzzleStub (long seed) {
        Random rand=new Random(seed);
        a = new PuzzleCell[10][10];
        int letterA=(int)'A';
        int blocknum=1;
        for (int i=0;i<10;i++) {
            for (int j=0;j<10;j++) {
                int showLetter=-1;
                if (!prime(blocknum)) {
                    showLetter=rand.nextInt(26)+letterA;
                }
                int userIndex=-1;
                if ((i==0) || (j==0) ||
                    (a[i-1][j].getContents()==-1) ||
                    (a[i][j-1].getContents()==-1)) {
                    userIndex=blocknum;
                }
                PuzzleCell pc=new PuzzleCellImpl(showLetter,userIndex);
                a[i][j] = pc;
                blocknum++;
            }
        }
    }

    private boolean prime(int x) {
        int rooti=(int)Math.sqrt(x);
        for (int i=2;i<=rooti;i++) {
            if ((x/(double)i)==(x/i)) {
                return false;
            }
        }
        return true;
    }

    public PuzzleStub(Map wordList, long seed) {
        this(seed);
    }

    public java.util.Map getWordList() {
        Properties list=new Properties();
        Iterator directions=Direction.iterator();
        while (directions.hasNext()) {
            Iterator words=getClues((Direction)directions.next()).
                values().iterator();
            while (words.hasNext()) {
                String word=(String)words.next();
                list.put(word,word);
            }
        }
        return list;
    }

    public PuzzleCell getCell(int x, int y) {
        return a[y][x];
    }

    public java.util.Map getClues(Direction d) {
        Map list=new TreeMap();
        StringBuffer sb=new StringBuffer();
        int wordNum=-1;
        for (int i=0;i<10;i++) {
            for (int j=0;j<10;j++) {
                int x,y;
                if (d==Direction.ACROSS) {
                    x=i; y=j;
                } else {
                    x=j; y=i;
                }
                if (a[x][y].getContents()==-1) { // blank so EOW
                    if (sb.length()>0) {
                        list.put(new Integer(wordNum),sb.toString());
                        sb=new StringBuffer();
                    }
                } else {
                    if (sb.length()==0) {
                        wordNum=a[x][y].getUserIndex();
                    }
                    sb.append((char)a[x][y].getContents());
                }
            }
            if (sb.length()>0) {
                list.put(new Integer(wordNum),sb.toString());
                sb=new StringBuffer();
            }
        }
        return list;
    }

    public java.awt.Dimension getDimension() {
       return new Dimension(a[0].length, a.length);
    }
}
