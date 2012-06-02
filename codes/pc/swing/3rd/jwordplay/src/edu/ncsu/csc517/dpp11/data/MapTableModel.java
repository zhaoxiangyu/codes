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

import javax.swing.table.*;
import java.util.*;

/**
 * @author Scott Shindeldecker
 * @author James E. Scarborough
 */
public class MapTableModel extends AbstractTableModel {
    private Map map;
    private List mapEntries;
    private long tempKeyCount=0L;
    private static final Comparator KEY_COMPARATOR=new Comparator() {
        public int compare(Object o1, Object o2) {
            Object k1=((Map.Entry)o1).getKey();
            Object k2=((Map.Entry)o2).getKey();
            return ((Comparable)k1).compareTo(k2);
        }
    };

    public MapTableModel(Map m) {
        map=m;
        refresh();
    }

    public void refresh() {
        int oldSize=0;
        if (mapEntries!=null) {
           oldSize=mapEntries.size()+1;
        }
        Set s=map.entrySet();
        TreeSet ts=new TreeSet(KEY_COMPARATOR);
        ts.addAll(s);
        mapEntries=new ArrayList();
        mapEntries.addAll(ts);
        int newSize=getRowCount();

        fireTableDataChanged();
        if (oldSize>newSize) {
            fireTableRowsDeleted(newSize,oldSize-1);
        } else if (oldSize<newSize) {
            fireTableRowsInserted(oldSize,newSize-1);
        }
    }

    public int getRowCount() {
        return map.size() + 1;
    }

    public int getColumnCount() {
        return 2;
    }

    private Map.Entry getEntry(int row) {
        return (Map.Entry)mapEntries.get(row);
    }

    public Object getValueAt(int row, int col) {
        if (row<map.size()) {
            Map.Entry entry=getEntry(row);
            return (col==0)?entry.getKey():entry.getValue();
        } else {
            return "";
        }
    }

    public boolean isCellEditable(int row, int col) {
        return (col==0) || (row<map.size());
    }

    private Map.Entry getEntry(final Object key) {
        return new Map.Entry() {
            public Object getKey() {
                return key;
            }
            public Object getValue() {
                return map.get(key);
            }
            public Object setValue(Object o) {
                return map.put(key,o);
            }
        };
    }

    public void setValueAt(Object o, int row, int col) {
        if (col==1) {
            if (row<map.size()) {
                Map.Entry entry=getEntry(row);
                entry.setValue(o);
            } else {
                String key="@tempKey" + Long.toString(tempKeyCount++,36);
                map.put(key,o);
                mapEntries.add(getEntry(key));
            }
        } else {
            if (row<map.size()) {
                Map.Entry entry=getEntry(row);
                Object oldKey=entry.getKey();
                Object oldValue=entry.getValue();
                map.remove(oldKey);
                map.put(o,oldValue);
                mapEntries.set(row,getEntry(o));
            } else {
                map.put(o,"");
                mapEntries.add(getEntry(o));
            }
        }
    }
}

