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
import junit.framework.*;

/**
 * @author Scott Shindeldecker
 * @author James E. Scarborough
 */
public class MapTableModelTest extends TestCase {

    public MapTableModelTest() {
        this("MapTableModelTest");
    }

    public MapTableModelTest(String name) {
        super(name);
    }

    public void testSimpleInsert() {
        Map m=new Properties();
        MapTableModel mtm=new MapTableModel(m);
        assertEquals(1,mtm.getRowCount());
        assertEquals(2,mtm.getColumnCount());
        mtm.setValueAt("alpha",0,0);
        assertEquals("alpha",mtm.getValueAt(0,0));
        assertEquals(2,mtm.getRowCount());
        assertTrue(m.containsKey("alpha"));
        mtm.setValueAt("a",0,1);
        assertEquals("a",mtm.getValueAt(0,1));
        assertEquals(2,mtm.getRowCount());
        assertEquals("a",m.get("alpha"));
    }

    public void testExistingData() {
        Map m=new Properties();
        m.put("alpha","a");
        m.put("beta" ,"b");
        m.put("gamma","c");
        MapTableModel mtm=new MapTableModel(m);
        assertEquals(4,mtm.getRowCount());
        assertEquals(2,mtm.getColumnCount());
        assertEquals("alpha",mtm.getValueAt(0,0));
        assertEquals(4,mtm.getRowCount());
        assertTrue(m.containsKey("alpha"));
        mtm.setValueAt("a",0,1);
        assertEquals("a",mtm.getValueAt(0,1));
        assertEquals(4,mtm.getRowCount());
        assertEquals("a",m.get("alpha"));
    }

    public void testOverwritingData() {
        Map m=new Properties();
        m.put("alpha","a");
        m.put("beta" ,"b");
        m.put("gamma","c");
        MapTableModel mtm=new MapTableModel(m);
        assertEquals(4,mtm.getRowCount());
        assertEquals(2,mtm.getColumnCount());
        mtm.setValueAt("Theta",0,0);
        assertEquals("Theta",mtm.getValueAt(0,0));
        assertTrue(m.containsKey("Theta"));
        mtm.setValueAt("T",0,1);
        assertEquals(4,mtm.getRowCount());
        assertEquals("T",mtm.getValueAt(0,1));
        assertEquals("T",m.get("Theta"));

    }

    public void testValueFirstData() {
        Map m=new Properties();
        MapTableModel mtm=new MapTableModel(m);
        assertEquals(1,mtm.getRowCount());
        assertEquals(2,mtm.getColumnCount());
        mtm.setValueAt("T",0,1);
        assertEquals("T",mtm.getValueAt(0,1));
        assertEquals(2,mtm.getRowCount());
        assertTrue(m.containsValue("T"));
        mtm.setValueAt("Theta",0,0);
        assertEquals("Theta",mtm.getValueAt(0,0));
        assertEquals(2,mtm.getRowCount());
        assertEquals("T",m.get("Theta"));

    }





    public static Test suite() {
        return new TestSuite(MapTableModelTest.class);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}
