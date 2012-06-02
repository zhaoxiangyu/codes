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

import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;
import edu.ncsu.csc517.dpp11.data.Direction;
import edu.ncsu.csc517.dpp11.data.Puzzle;
import edu.ncsu.csc517.dpp11.data.PuzzleStub;

/**
 * @author Scott Shindeldecker
 * @author James E. Scarborough
 */
public class ClueBox extends JComponent  {
    private Puzzle puzzle;
    private static final String EOL=System.getProperty("line.separator");
    private static final double PREFERRED_RATIO=1.61803398874989484820; // w:h

    public ClueBox(Puzzle p) {
        JTextPane testPane=new JTextPane();
        puzzle=p;
        initStylesForTextPane(testPane);
        Iterator directions=Direction.iterator();
        Document doc=testPane.getDocument();
        ArrayList lines=new ArrayList();
        while (directions.hasNext()) {
            Direction d=(Direction)directions.next();
            lines.add(appendString(doc,testPane, d.getName() + EOL,"bold"));
            Iterator clues=puzzle.getClues(d).entrySet().iterator();
            while (clues.hasNext()) {
                Map.Entry clue=(Map.Entry)clues.next();
                lines.add(appendString(doc,testPane,clue.getKey() + "  " +
                    clue.getValue() + EOL,
                    "regular"));
            }
            if (directions.hasNext())
                lines.add(appendString(doc,testPane,EOL,"regular"));
        }
        Dimension referenceSize=testPane.getPreferredSize();
        int columns=(int)Math.sqrt(
            PREFERRED_RATIO * referenceSize.height /
            referenceSize.width
        );
        double linesPerColumn=lines.size()/(double)columns;

        setLayout(new GridLayout(1,columns));
        for (int i=0;i<columns;i++) {
            JTextPane tp=new JTextPane();
            initStylesForTextPane(tp);
            Document displayDoc=tp.getDocument();
            int startLine=(int)(i*linesPerColumn);
            int endLine=(int)((i+1)*linesPerColumn);
            for (int j=startLine;j<endLine;j++) {
                appendString(displayDoc,tp,(String[])lines.get(j));
            }
            tp.setEditable(false);
            add(tp);
        }
    }


    private String[] appendString(Document d, JTextPane pane,
        String[] stringAndStyle) {
        return appendString(d,pane,stringAndStyle[0],stringAndStyle[1]);
    }

    private String[] appendString(Document d, JTextPane pane,
        String s, String style) {
        try {
            d.insertString(d.getLength(),s,pane.getStyle(style));
        } catch (BadLocationException e) {
            throw new RuntimeException("Modified document.");
        }
        return new String[]{s,style};
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    protected void initStylesForTextPane(JTextPane p) {
        //Initialize some styles.
        Style def = StyleContext.getDefaultStyleContext().
           getStyle(StyleContext.DEFAULT_STYLE);

        Style regular = p.addStyle("regular", def);
        StyleConstants.setFontFamily(def, "SansSerif");

        Style s = p.addStyle("bold", regular);
        StyleConstants.setBold(s, true);
    }

    public static void main(String argv[]) {
        JFrame f=new JFrame();
        Container c=f.getContentPane();
        c.setLayout(new BorderLayout());
        final ClueBox cb =new ClueBox(new PuzzleStub());
        c.add(cb);
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

}
