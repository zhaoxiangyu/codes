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

/**
 * The Direction class allows type checking on constant directions.
 *
 * @author Scott Shindeldecker
 * @author James E. Scarborough
 */
public class Direction {
    public static final Direction ACROSS=new Direction("Across"),
                                  DOWN=new Direction("Down");
    private String name;

    private Direction(String name) { 
        this.name=name; 
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "Direction:" + name;
    }

    /**
     * Iterate over the possible directions.
     */
    public static Iterator iterator() {
        return Arrays.asList(new Object[] { ACROSS, DOWN }).iterator();
    }
}


