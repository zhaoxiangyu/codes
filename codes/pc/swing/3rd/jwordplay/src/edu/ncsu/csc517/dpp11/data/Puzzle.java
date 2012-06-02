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

import java.util.Map;
import java.util.*;

/** 
 * Each puzzle is expected to have a constructor that takes a Map wordList 
 * and a long random number seed.
 *
 * @author Scott Shindeldecker
 * @author James E. Scarborough
 */
public interface Puzzle extends java.io.Serializable {

    public java.util.Map getWordList();

    public PuzzleCell getCell(int x, int y);

    public java.util.Map getClues(Direction d);

    public java.awt.Dimension getDimension ();


}
