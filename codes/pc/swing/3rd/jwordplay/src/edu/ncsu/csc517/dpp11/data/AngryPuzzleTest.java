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
import java.awt.Dimension;

/**
 * Exercise AngryPuzzle
 *
 * @author James E. Scarborough
 */
public class AngryPuzzleTest extends TestCase {
    private static final String s31s="SUPERCALIFRAGILISTICEXPIALIDOCIOUS";
    private static final String EOL=System.getProperty("line.separator");

    public AngryPuzzleTest() {
        this("AngryPuzzleTest");
    }

    public AngryPuzzleTest(String name) {
        super(name);
    }

    public void testOneWord() {
        Properties wordList=new Properties();
        wordList.put("A","article");
        AngryPuzzle p=new AngryPuzzle(wordList,0,true);
        p.genMatrix(10,10);
        assertEquals(wordList,p.getWordList());
        Dimension dim=p.getDimension();
        assertTrue(dim.width>0);
        assertTrue(dim.height>0);
        for (int i=0;i<dim.width;i++) {
            for (int j=0;j<dim.height;j++) {
                assertNotNull(p.getCell(i,j));
            }
        }
    }

    /**
     * This tests internals of the puzzle.
     */
    public void testPuzzleSize() {
        // true is for "testing" mode so we don't build the whole puzzle.
        Properties wordList=new Properties();
        AngryPuzzle p=new AngryPuzzle(wordList,0,true);
        p.genMatrix(6,6);
        assertEquals(new Dimension(6,6),p.getDimension());
        p.genMatrix(16,16);
        assertEquals(new Dimension(16,16),p.getDimension());
        assertNotNull(p.getCell(15,15));

        p.genMatrix(3,5);
        assertEquals(new Dimension(3,5),p.getDimension());
    }

    public void testPlaceWord() {
        // true is for "testing" mode so we don't build the whole puzzle.
        Properties wordList=new Properties();
        AngryPuzzle p=new AngryPuzzle(wordList,0,true);
        p.genMatrix(6,6);
        String word="WORD";
        p.placeWord(word,0,0,Direction.ACROSS);
        //assertEquals(Direction.ACROSS,p.lastDirection);
        for (int i=0;i<word.length();i++) {
            assertEquals(word.charAt(i),(char)p.getCell(i,0).getContents());
        }
        p.placeWord(word,0,2,Direction.DOWN);
        //assertEquals(Direction.DOWN,p.lastDirection);
        for (int i=0;i<word.length();i++) {
            assertEquals(word.charAt(i),(char)p.getCell(0,i+2).getContents());
        }

        p.genMatrix(1,word.length());
        p.placeWord(word,0,0,Direction.DOWN);
        for (int i=0;i<word.length();i++) {
            assertEquals(word.charAt(i),(char)p.getCell(0,i).getContents());
        }

        p=new AngryPuzzle(wordList,0,true);
        p.genMatrix(6,6);
        word="word";
        p.placeWord(word,0,0,Direction.ACROSS);
        assertEquals(word,p.getWordAtLocation(0,0,Direction.ACROSS));
        word=word.toUpperCase();
        for (int i=0;i<word.length();i++) {
            assertEquals(word.charAt(i),(char)p.getCell(i,0).getContents());
        }

    }

    public void testWordFits() {
        // true is for "testing" mode so we don't build the whole puzzle.
        Properties wordList=new Properties();
        AngryPuzzle p=new AngryPuzzle(wordList,0,true);
        p.genMatrix(6,6);
        String word="WORD";
        assertTrue(p.wordFits(word,0,0,Direction.ACROSS));
        assertFalse(p.wordFits(word,5,5,Direction.ACROSS));
        assertTrue(p.wordFits(word,0,0,Direction.DOWN));
        assertFalse(p.wordFits(word,0,3,Direction.DOWN));

        p.genMatrix(1,6);
        assertTrue(p.wordFits(word,0,0,Direction.DOWN));
        assertFalse(p.wordFits(word,0,0,Direction.ACROSS));
    }

    public void testPrePostLetters() {
        // true is for "testing" mode so we don't build the whole puzzle.
        Properties wordList=new Properties();
        AngryPuzzle p=new AngryPuzzle(wordList,0,true);
        p.genMatrix(6,6);
        String word="WORD";
        assertFalse(p.checkPrePostLetters(word,1,1,Direction.ACROSS));
        assertFalse(p.checkPrePostLetters(word,0,0,Direction.ACROSS));
        assertFalse(p.checkPrePostLetters(word,2,0,Direction.ACROSS));
        assertFalse(p.checkPrePostLetters(word,1,1,Direction.DOWN));
        assertFalse(p.checkPrePostLetters(word,0,0,Direction.DOWN));
        assertFalse(p.checkPrePostLetters(word,0,2,Direction.DOWN));

        p.placeWord(word,0,0,Direction.DOWN);
        assertTrue(p.checkPrePostLetters(word,1,0,Direction.ACROSS));

        p.placeWord(word,5,0,Direction.DOWN);
        assertTrue(p.checkPrePostLetters(word,1,0,Direction.ACROSS));

        p.genMatrix(10,1);
        assertFalse(p.checkPrePostLetters(word,0,0,Direction.ACROSS));
    }

    public void testLettersMatch() {
        Properties wordList=new Properties();
        AngryPuzzle p=new AngryPuzzle(wordList,0,true);
        p.genMatrix(6,6);
        String word="WORD";
        assertTrue(p.lettersMatch(word,0,0,Direction.ACROSS));

        p.placeWord(word,0,0,Direction.ACROSS);
        assertTrue(p.lettersMatch(word,0,0,Direction.ACROSS));

        p.placeWord(word,5,0,Direction.DOWN);
        assertFalse(p.lettersMatch(word,2,2,Direction.ACROSS));

        // test DOWN
        assertTrue(p.lettersMatch(word,2,2,Direction.DOWN));

        p.placeWord(word,0,0,Direction.DOWN);
        assertTrue(p.lettersMatch(word,0,0,Direction.DOWN));

        p.placeWord(word,0,5,Direction.ACROSS);
        assertFalse(p.lettersMatch(word,2,2,Direction.DOWN));

    }

    public void testAdjacentLetters() {
        Properties wordList=new Properties();
        AngryPuzzle p=new AngryPuzzle(wordList,0,true);
        p.genMatrix(6,6);

        // ACROSS
        String word="WORD";
        assertFalse(p.checkAdjacentLetters(word,1,1,Direction.ACROSS));

        p.placeWord(word,1,0,Direction.ACROSS);
        assertTrue(p.checkAdjacentLetters(word,1,1,Direction.ACROSS));

        p.placeWord(word,1,5,Direction.ACROSS);
        assertTrue(p.checkAdjacentLetters(word,1,4,Direction.ACROSS));

        assertTrue(p.checkAdjacentLetters(word,0,1,Direction.ACROSS));

        p.genMatrix(6,6);
        p.placeWord("CROSS",1,0,Direction.DOWN);
        assertFalse(p.checkAdjacentLetters("WORD",0,2,Direction.ACROSS));

        p.genMatrix(40,3);
        word=s31s;
        assertFalse(p.checkAdjacentLetters(word,0,2,Direction.ACROSS));

        p.placeWord("X",27,0,Direction.ACROSS);
        assertTrue(p.checkAdjacentLetters(word,0,1,Direction.ACROSS));

        assertFalse(p.checkAdjacentLetters(word,0,0,Direction.ACROSS));

        // DOWN
        p.genMatrix(6,6);
        word="WORD";
        assertFalse(p.checkAdjacentLetters(word,1,1,Direction.DOWN));

        p.placeWord(word,0,1,Direction.DOWN);
        assertTrue(p.checkAdjacentLetters(word,1,1,Direction.DOWN));

        p.placeWord(word,5,1,Direction.DOWN);
        assertTrue(p.checkAdjacentLetters(word,4,1,Direction.DOWN));

        assertTrue(p.checkAdjacentLetters(word,1,0,Direction.DOWN));

        p.genMatrix(6,6);
        p.placeWord("CROSS",0,1,Direction.ACROSS);
        assertFalse(p.checkAdjacentLetters("WORD",2,0,Direction.DOWN));

        p.genMatrix(3,40);
        word=s31s;
        assertFalse(p.checkAdjacentLetters(word,2,0,Direction.DOWN));

        p.placeWord("X",0,27,Direction.ACROSS);
        assertTrue(p.checkAdjacentLetters(word,1,0,Direction.DOWN));

        assertFalse(p.checkAdjacentLetters(word,0,0,Direction.DOWN));
    }

    public void testWordAtLocation() {
        Properties wordList=new Properties();
        AngryPuzzle p=new AngryPuzzle(wordList,0,true);
        p.genMatrix(6,6);

        assertNull(p.getWordAtLocation(1,1,Direction.ACROSS));

        p.placeWord("WORD",1,1,Direction.ACROSS);
        assertEquals("WORD",p.getWordAtLocation(1,1,Direction.ACROSS));

        p.placeWord("CROSS",1,3,Direction.ACROSS);
        assertEquals("CROSS",p.getWordAtLocation(1,3,Direction.ACROSS));

        assertEquals("WORD",p.getWordAtLocation(1,1,Direction.ACROSS));

        assertNull(p.getWordAtLocation(1,1,Direction.DOWN));

        p.placeWord("WE",1,1,Direction.DOWN);
        assertEquals("WORD",p.getWordAtLocation(1,1,Direction.ACROSS));
        assertEquals("WE",p.getWordAtLocation(1,1,Direction.DOWN));

        assertNull(p.getWordAtLocation(1,2,Direction.DOWN));
        assertNull(p.getWordAtLocation(1,2,Direction.ACROSS));
        assertNull(p.getWordAtLocation(2,1,Direction.DOWN));
        assertNull(p.getWordAtLocation(1,2,Direction.ACROSS));
    }

    public void testWordAtLocationT() {
        Properties wordList=new Properties();
        AngryPuzzle p=new AngryPuzzle(wordList,0,true);
        p.genMatrix(6,6);

        assertNull(p.getWordAtLocation(1,1,Direction.DOWN));

        p.placeWord("WORD",1,1,Direction.DOWN);
        assertEquals("WORD",p.getWordAtLocation(1,1,Direction.DOWN));

        p.placeWord("CROSS",3,1,Direction.DOWN);
        assertEquals("CROSS",p.getWordAtLocation(3,1,Direction.DOWN));

        assertEquals("WORD",p.getWordAtLocation(1,1,Direction.DOWN));

        assertNull(p.getWordAtLocation(1,1,Direction.ACROSS));
    }

    public void testClearPathForWord() {
        Properties wordList=new Properties();
        AngryPuzzle p=new AngryPuzzle(wordList,0,true);
        p.genMatrix(6,6);

        assertTrue(p.clearPathForWord("WORD",1,1,Direction.DOWN));

        p.placeWord("WORD",1,1,Direction.ACROSS);
        assertFalse(p.clearPathForWord("WORD",1,1,Direction.ACROSS));

        assertFalse(p.clearPathForWord("SWORD",0,1,Direction.ACROSS));

        assertTrue(p.clearPathForWord("WORD",1,1,Direction.DOWN));

        p.placeWord("WORD",1,1,Direction.DOWN);
        assertFalse(p.clearPathForWord("WORD",1,1,Direction.DOWN));
        assertFalse(p.clearPathForWord("SWORD",1,0,Direction.DOWN));

        p.genMatrix(10,10);
        p.placeWord("WORD",8,3,Direction.DOWN);
        assertFalse(p.clearPathForWord("SWORD",8,2,Direction.DOWN));
    }

    public void testLegalPosition() {
        Properties wordList=new Properties();
        AngryPuzzle p=new AngryPuzzle(wordList,0,true);
        p.genMatrix(6,6);

        assertTrue(p.legalPosition("WORD",1,1,Direction.DOWN));
        assertFalse(p.legalPosition(s31s,
            0,0,Direction.ACROSS));

        p.placeWord("SWORD",0,0,Direction.DOWN);
        assertFalse(p.legalPosition("WORD",1,0,Direction.ACROSS));

        assertFalse(p.legalPosition("WORD",1,0,Direction.DOWN));

        // S _ _ C _ _
        // W _ _ R _ _
        // O _ W O R D
        // R _ _ S _ _
        // D _ _ S _ _
        // _ _ _ _ _ _

        p.placeWord("CROSS",3,0,Direction.DOWN);
        assertTrue(p.legalPosition("WORD",2,2,Direction.ACROSS));
        assertFalse(p.legalPosition("WORD",2,1,Direction.ACROSS));

        p.genMatrix(6,6);
        p.placeWord("WORD",0,1,Direction.DOWN);
        assertFalse(p.legalPosition("SWORD",0,0,Direction.DOWN));
    }

    public void testSetUserIndices() {
        Properties wordList=new Properties();
        AngryPuzzle p=new AngryPuzzle(wordList,0,true);
        p.genMatrix(6,6);

        assertEquals(-1, p.getCell(0,0).getUserIndex());

        // W _ F O U R
        // O _ _ D _ A
        // R _ E D I T
        // D _ _ _ _ E
        // _ _ _ _ _ _
        // _ _ _ _ _ _

        p.placeWord("WORD",0,0,Direction.DOWN);
        p.setUserIndices();

        assertEquals(1,p.getCell(0,0).getUserIndex());

        assertEquals(-1,p.getCell(2,0).getUserIndex());

        p.placeWord("FOUR",2,0,Direction.ACROSS);
        p.setUserIndices();
        assertEquals(2,p.getCell(2,0).getUserIndex());

        p.placeWord("ODD",3,0,Direction.DOWN);
        p.setUserIndices();
        assertEquals(3,p.getCell(3,0).getUserIndex());

        p.placeWord("EDIT",2,2,Direction.ACROSS);
        p.setUserIndices();
        assertEquals(4,p.getCell(2,2).getUserIndex());

        p.placeWord("RATE",5,0,Direction.DOWN);
        p.setUserIndices();
        assertEquals(4,p.getCell(5,0).getUserIndex());
        assertEquals(5,p.getCell(2,2).getUserIndex());

        p.genMatrix(40,3);
        String word=s31s;
        p.placeWord(word,0,0,Direction.ACROSS);
        p.placeWord("RAT",4,0,Direction.DOWN);
        p.setUserIndices();
        assertEquals(1,p.getCell(0,0).getUserIndex());
        assertEquals(2,p.getCell(4,0).getUserIndex());
    }

    public void testScoring() {
        Properties wordList=new Properties();
        AngryPuzzle p=new AngryPuzzle(wordList,0,true);
        p.genMatrix(6,6);

        assertEquals(0,p.scoreWord("WORD",0,0,Direction.ACROSS));
        assertEquals(1,p.scoreWord("WORD",0,1,Direction.ACROSS));
        assertEquals(0,p.scoreWord("WORD",0,5,Direction.ACROSS));

        // + 1 for down
        assertEquals(1,p.scoreWord("WORD",0,0,Direction.DOWN));
        assertEquals(2,p.scoreWord("WORD",1,0,Direction.DOWN));
        assertEquals(1,p.scoreWord("WORD",5,0,Direction.DOWN));

        // _ _ C _ _ _
        // _ _ R _ _ _
        // _ W O R D _
        // _ _ S _ _ _
        // _ _ S _ _ _
        // _ _ _ _ _ _

        p.placeWord("WORD",1,2,Direction.ACROSS);

        // 1 point for not on left or right, 6 points for the single cross + 1
        // for down
        assertEquals(8,p.scoreWord("CROSS",2,0,Direction.DOWN));

        // _ _ _ _ _ _
        // _ _ _ C _ _
        // _ W O R D _
        // _ _ _ O _ _
        // _ W E S T _
        // _ _ _ S _ _

        p.placeWord("WEST",1,4,Direction.ACROSS);
        // 1 not edge + 12 for crosses + 1 down
        assertEquals(14,p.scoreWord("CROSS",3,1,Direction.DOWN));


        // transpose of:
        //  T B   A A R B L
        // SUPERCALIFRAGILISTICEXPIALIDOCIOUS
        //  B T   E T T T T

        p.genMatrix(3,40);
        p.placeWord("TUB",0,1,Direction.ACROSS);
        p.placeWord("BET",0,3,Direction.ACROSS);
        p.placeWord("ALE",0,7,Direction.ACROSS);
        p.placeWord("AFT",0,9,Direction.ACROSS);
        p.placeWord("RAT",0,11,Direction.ACROSS);
        p.placeWord("BIT",0,13,Direction.ACROSS);
        p.placeWord("LIT",0,15,Direction.ACROSS);

        // 1 + 7 * 6 +1 = 44
        assertEquals(44,p.scoreWord(s31s,
            1,0,Direction.DOWN));

        p.genMatrix(6,6);
        p.placeWord("WORD",0,1,Direction.ACROSS);
        assertEquals(7,p.scoreWord("AWARD",0,0,Direction.DOWN));

        p.genMatrix(6,6);
        p.placeWord("WORD",0,0,Direction.ACROSS);
        // It gets docked 1 point because the match is on the first letter
        assertEquals(6,p.scoreWord("WISE",0,0,Direction.DOWN));

        p.placeWord("WORD",2,5,Direction.ACROSS);
        // It gets docked 1 point because the match is on the last letter
        assertEquals(6,p.scoreWord("HEAD",5,2,Direction.DOWN));

        // TEST SCORING FOR ACROSS
        // Funny thing here - it's exactly the same, except the basic score for
        // a cross is 3 instead of 6.
        p.genMatrix(6,6);
        p.placeWord("WORD",0,0,Direction.DOWN);

        // 3 points for cross, -1 for cross on first letter
        assertEquals(2,p.scoreWord("WISE",0,0,Direction.ACROSS));

        // W _ _
        // A _ _
        // T _ _
        // E _ _
        // R I M

        p.genMatrix(3,5);
        p.placeWord("WATER",0,0,Direction.DOWN);
        // 3 points for cross, -1 for cross on first letter
        assertEquals(2,p.scoreWord("RIM",0,4,Direction.ACROSS));

        // test case insensitivity
        p.genMatrix(2,2);
        p.placeWord("HE",0,0,Direction.ACROSS);
        // 5 for cross + 1 for down
        assertEquals(6,p.scoreWord("he",0,0,Direction.DOWN));
    }

    public void testGetClues() {
        Properties wordList=new Properties();
        wordList.put("WORD","palabra");
        wordList.put("DANK","dark and wet");
        wordList.put("OGGLE","stare");
        AngryPuzzle p=new AngryPuzzle(wordList,0,true);
        p.genMatrix(6,6);

        p.placeWord("WORD",0,0,Direction.DOWN);
        TreeMap expectedDown=new TreeMap();
        expectedDown.put(new Integer(1),"palabra");
        p.setUserIndices();
        assertEquals(expectedDown,p.getClues(Direction.DOWN));

        p.placeWord("DANK",0,3,Direction.ACROSS);
        p.setUserIndices();
        assertEquals(expectedDown,p.getClues(Direction.DOWN));

        TreeMap expectedAcross=new TreeMap();
        expectedAcross.put(new Integer(2),"dark and wet");
        assertEquals(expectedAcross,p.getClues(Direction.ACROSS));

        p.placeWord("OGGLE",0,1,Direction.ACROSS);
        p.setUserIndices();
        assertEquals(expectedDown,p.getClues(Direction.DOWN));

        assertEquals(2,p.getClues(Direction.ACROSS).size());
        assertTrue(p.getClues(Direction.ACROSS).containsKey(new Integer(2)));
        assertTrue(p.getClues(Direction.ACROSS).containsKey(new Integer(3)));

        expectedAcross.put(new Integer(2),"stare");
        expectedAcross.put(new Integer(3),"dark and wet");
        assertEquals(expectedAcross,p.getClues(Direction.ACROSS));

        wordList=new Properties();
        wordList.put(s31s,
                     "Mary Poppins' favourite");
        wordList.put("RAT","rodent");
        p=new AngryPuzzle(wordList,0,true);

        p.genMatrix(40,3);
        String word=s31s;
        p.placeWord(word,0,0,Direction.ACROSS);
        p.placeWord("RAT",4,0,Direction.DOWN);
        p.setUserIndices();
        expectedAcross=new TreeMap();
        expectedAcross.put(new Integer(1),"Mary Poppins' favourite");
        assertEquals(expectedAcross,p.getClues(Direction.ACROSS));
        expectedDown.clear();
        expectedDown.put(new Integer(2),"rodent");
        assertEquals(expectedDown,p.getClues(Direction.DOWN));

    }

    public void testBestPosition() {
        Properties wordList=new Properties();
        AngryPuzzle p=new AngryPuzzle(wordList,0,true);
        p.genMatrix(6,6);

        assertNull(p.getBestPosition(s31s));

        AngryPuzzle.Position pos=p.getBestPosition("WORD");
        if (pos.direction==Direction.ACROSS) {
            assertBetween(0,pos.x,2);
            assertBetween(1,pos.y,4);
        } else {
            assertEquals(Direction.DOWN,pos.direction);
            assertBetween(0,pos.y,2);
            assertBetween(1,pos.x,4);
        }

        p.placeWord("WORD",pos);
        AngryPuzzle.Position pos2= p.getBestPosition("AWL");
        assertTrue(!pos.direction.equals(pos2.direction));
        if (pos2.direction==Direction.ACROSS) {
            // A W L
            //   O
            //   R
            //   D
            assertEquals(pos.x-1,pos2.x);
            assertEquals(pos.y,pos2.y);
        } else {
            // A
            // W O R D
            // L
            assertEquals(pos.x,pos2.x);
            assertEquals(pos.y-1,pos2.y);
        }

        // test the random component.
        boolean differed=false;
        for (int i=1;i<1000;i++) {
            p=new AngryPuzzle(wordList,i*5000,true);
            p.genMatrix(6,6);

            AngryPuzzle.Position pos3=p.getBestPosition("WORD");
            differed|=!pos3.equals(pos);
            if (differed) break;
        }
        assertTrue(differed);

        // test rectangular
        p.genMatrix(s31s.length(),3);
        String word=s31s;
        pos=p.getBestPosition(word);
        assertEquals(1,pos.y);
        assertEquals(0,pos.x);

        // test case insensitivity
        p.placeWord(s31s,pos);
        pos=p.getBestPosition("axe");
        assertNotNull(pos);
        assertEquals(Direction.DOWN,pos.direction);

        // test bias for crosses
        p.genMatrix(s31s.length(),10);
        pos=p.getBestPosition(s31s);
        p.placeWord(s31s,pos);
        pos2=p.getBestPosition("VEX");
        assertEquals(pos.y-1,pos2.y);
        assertEquals(Direction.DOWN,pos2.direction);
    }

    public void testToString() {
        Properties wordList=new Properties();
        AngryPuzzle p=new AngryPuzzle(wordList,0,true);
        p.genMatrix(1,1);

        assertEquals("AngryPuzzle:" + EOL + "_" + EOL,p.toString());
        p.placeWord("A",0,0,Direction.ACROSS);
        assertEquals("AngryPuzzle:" + EOL + "A" + EOL,p.toString());

        p.genMatrix(2,2);
        p.placeWord("HE",0,0,Direction.ACROSS);
        p.placeWord("HA",0,0,Direction.DOWN);
        assertEquals(
           "AngryPuzzle:" + EOL +
           "H E" + EOL + 
           "A _" + EOL,
           p.toString()
        );


    }

    public void testPositionEquality() {
        assertTrue(new AngryPuzzle.Position(1,2,Direction.ACROSS).equals(
          new AngryPuzzle.Position(1,2,Direction.ACROSS)));
        assertFalse(new AngryPuzzle.Position(1,2,Direction.ACROSS).equals(
          new AngryPuzzle.Position(1,2,Direction.DOWN)));
    }

    public void testPositionToString() {
        assertEquals("Position: (1,3 Direction:Down)",
            new AngryPuzzle.Position(1,3,Direction.DOWN).toString());
    }
    
    public void testClearPuzzle() {
        AngryPuzzle p=new AngryPuzzle(null,0,true);
        p.genMatrix(6,10);
        p.placeWord("WORD",1,1,Direction.DOWN);
        p.clearPuzzle();
        for (int x=0;x<6;x++) {
            for (int y=0;y<10;y++) {
                assertEquals(-1,p.getCell(x,y).getContents());
                assertNull(p.getWordAtLocation(x,y,Direction.DOWN));
                assertNull(p.getWordAtLocation(x,y,Direction.ACROSS));
            }
        }
    }

    public void testBuildCrossword() throws AngryPuzzle.PuzzleFullException {
        Properties wordList=new Properties();
        wordList.put("WORD","palabra");
        AngryPuzzle p=new AngryPuzzle(wordList,0,true);
        p.genMatrix(6,6);

        p.buildCrossword();
        assertContains("WORD",p);

        // Check that the word is in a best position
        AngryPuzzle.Position pos=p.getBestPosition("WORD");
        if (pos.direction==Direction.ACROSS) {
            assertBetween(0,pos.x,2);
            assertBetween(1,pos.y,4);
        } else {
            assertEquals(Direction.DOWN,pos.direction);
            assertBetween(0,pos.y,2);
            assertBetween(1,pos.x,4);
        }

        // If the word doesn't fit, we need to throw an exception.
        p.genMatrix(3,3);
        try {
            p.buildCrossword();
            fail("No exception thrown");
        } catch (AngryPuzzle.PuzzleFullException e) {
            // pass
        }

        p.genMatrix(6,6);
        wordList.put("CROSS","angry");
        p.buildCrossword();
        assertContains("WORD",p);
        assertContains("CROSS",p);

    }
    
    public void testCountLetters() {
        Properties wordList=new Properties();
        AngryPuzzle p=new AngryPuzzle(wordList,0,true);
        assertEquals(0,p.countLetters());
        wordList.put("WORD","palabra");
        assertEquals(4,p.countLetters());
        wordList.put("FIRETRUCK","Guy Montag's vehicle");
        assertEquals(9+4,p.countLetters());
    }

    public void testGetLongestWordLength() {
        Properties wordList=new Properties();
        AngryPuzzle p=new AngryPuzzle(wordList,0,true);
        assertEquals(0,p.getLongestWordLength());
        wordList.put("WORD","palabra");
        assertEquals(4,p.getLongestWordLength());
        wordList.put("FIRETRUCK","Guy Montag's vehicle");
        assertEquals(9,p.getLongestWordLength());
    }

    public void testGetMinimumSize() {
        Properties wordList=new Properties();

        // Test for square puzzle
        wordList.put("HE","palabra");
        wordList.put("HAT","cap");

        AngryPuzzle p=new AngryPuzzle(wordList,0,true);
        int area=(int)Math.ceil(5 * (1+AngryPuzzle.LOAD_RATIO));
        int side=(int)Math.ceil(Math.sqrt(area));
        assertEquals(new Dimension(side,side),p.getMinimumSize());

        // Test for rectangular puzzle
        wordList=new Properties();
        wordList.put(s31s,"long");
        wordList.put("ANT","short");

        p=new AngryPuzzle(wordList,0,true);
        Dimension dim=p.getMinimumSize();
        assertEquals(s31s.length(),dim.width);
        assertEquals(3,dim.height);
    }

    public void testUpdateSize() {
        Properties wordList=new Properties();
        wordList.put("WORD","palabra");
        wordList.put("FLAT","thin");

        AngryPuzzle p=new AngryPuzzle(wordList,0,true);
        p.updateSize();

        Dimension dim=p.getMinimumSize();
        assertEquals(dim,p.getDimension());

        boolean across=true;
        do {
            if (across) {
                dim.width++;
            } else {
                dim.height++;
            }
            across=!across;
            p.updateSize();
            assertEquals(dim,p.getDimension());
        } while (dim.width<20);


    }

    public void testConstructor() {
        Properties wordList=new Properties();
        wordList.put("WORD","palabra");
        wordList.put("FLAT","thin");
        AngryPuzzle p=new AngryPuzzle(wordList,0);
        assertEquals(p.getMinimumSize(),p.getDimension());

        wordList=new Properties();
        wordList.put("WORD","palabra");
        p=new AngryPuzzle(wordList,0);
        assertContains("WORD",p);
        TreeMap acrossClues=(TreeMap)p.getClues(Direction.ACROSS);
        TreeMap downClues=(TreeMap)p.getClues(Direction.DOWN);
        assertEquals(1, acrossClues.size() + downClues.size());
        acrossClues.putAll(downClues);
        Properties expected=new Properties();
        expected.put(new Integer(1),"palabra");
        assertEquals(expected,acrossClues);

        wordList=new Properties();
        wordList.put(s31s,"long");
        wordList.put("HMMM","shorter");
        p=new AngryPuzzle(wordList,0);
        assertContains(s31s,p);
        assertContains("HMMM",p);

    }

    protected void assertContains(String word, AngryPuzzle p) {
        Dimension dim=p.getDimension();
        for (int x=0;x<dim.width;x++) {
            for (int y=0;y<dim.height;y++) {
                if (
                    word.equals(p.getWordAtLocation(x,y,Direction.DOWN)) ||
                    word.equals(p.getWordAtLocation(x,y,Direction.ACROSS))
                ) return;
            }
        }
        fail("Missing word \"" + word + "\"");
    }

    protected void assertBetween(int a, int test, int b) {
        if ((a>test) || (b<test)) {
            fail(test + " not between " + a + " and " + b + ", inclusive.");
        }
    }

    public static Test suite() {
        return new TestSuite(AngryPuzzleTest.class);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    private java.util.Map readPuzzle(Puzzle p, Direction d) {
        boolean across= d==Direction.ACROSS;
        Dimension dim=p.getDimension();
        Map list=new TreeMap();
        StringBuffer sb=new StringBuffer();
        int wordNum=-1;
        for (int i=0;i<(across?dim.width:dim.height);i++) {
            for (int j=0;j<(across?dim.height:dim.width);j++) {
                int x,y;
                if (across) {
                    x=i; y=j;
                } else {
                    x=j; y=i;
                }
                PuzzleCell cell=p.getCell(x,y);
                assertTrue(cell!=null);
                if (cell.getContents()==-1) { // blank so EOW
                    if (sb.length()>0) {
                        list.put(new Integer(wordNum),sb.toString());
                        sb=new StringBuffer();
                    }
                } else {
                    if (sb.length()==0) {
                        wordNum=cell.getUserIndex();
                    }
                    sb.append((char)cell.getContents());
                }
            }
            if (sb.length()>0) {
                list.put(new Integer(wordNum),sb.toString());
                sb=new StringBuffer();
            }
        }
        return list;
    }

}
