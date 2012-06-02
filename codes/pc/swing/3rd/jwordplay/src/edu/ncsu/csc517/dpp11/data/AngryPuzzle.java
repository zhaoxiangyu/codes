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
import java.awt.*;

/**
 * AngryPuzzle transforms a list of words and clues into a crossword puzzle,
 * then serves as a representation of that puzzle.
 *
 * This algorithm is based on angry.c by Andrew Tridgell,
 * http://samba.org/ftp/unpacked/junkcode/angry.c
 * accessed 2002-12-19.
 *
 * @author Yi Gao
 * @author Minjie Ye
 * @author James E. Scarborough
 */
public class AngryPuzzle implements Puzzle {
    private static final double SIZE_RETRIES=1000; // JES
    static final double LOAD_RATIO=1/3.0; // JES
    private static final String EOL=System.getProperty("line.separator"); // JES
    private Map wordList;
    private Random rand;
    private PuzzleCell[][] grid;
    private boolean growAcrossNext=true;


    /**
     *
     */
    public AngryPuzzle(Map wordList, long seed) {
        this(wordList,seed,true);
        updateSize();
        int retries=0;
        do {
            try {
                buildBestCrossword();
                retries=0;
            }
            catch (PuzzleFullException pfe) {

                clearPuzzle();
                retries++;
                if (retries>SIZE_RETRIES) {
                    retries=1;
                    updateSize();
                }
            }
        } while (retries>0);

        setUserIndices();
    }

    AngryPuzzle(Map wordList, long seed, boolean testing) {
        this.wordList=wordList;
        rand=new Random(seed);
    }

    void genMatrix(Dimension dim) {
        genMatrix(dim.width,dim.height);
    }

    void genMatrix(int x,int y) {
        grid = new PuzzleCell[x][y];
        for(int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                grid[i][j] = new Cell(-1, -1);
            }
        }
    }

    static class Position {
        Direction direction;
        int x;
        int y;

        public Position(int x, int y, Direction dir) {
            this.direction = dir;
            this.x = x;
            this.y = y;
        }
        public boolean equals(Position p) {
            return (x == p.x)&& (y==p.y) && (direction.equals(p.direction));
        }
        public String toString() {
            return "Position: (" + x + "," + y + " " + direction + ")";
        }

    }

    /**
     * Implementation note: There may be multiple locations within the puzzle
     * having the same highest score.  As the algorithm encounters another
     * position with a score as good as the current high score, it MAY update
     * the position to the new position, with a probability of (area-4)/area.
     */
    public Position getBestPosition(String word) {
        /*
         * Thoughts on improvements: This algorithm places exactly one word
         * at a time.  If a word isn't legal because of adjacent letters,
         * perhaps placing another word from the list would make it legal.
         * A recursive algorithm could easily place multiple words at once.
         */
        int x = -1;
        int y = -1;
        Direction dir = null;
        int score = -1;
        int quarterArea= grid.length * grid[0].length / 4;


        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {


                if (legalPosition(word, i, j, Direction.DOWN))
                {
                    int newScore = scoreWord(word, i, j, Direction.DOWN);

                    if ( newScore > score || (
                            (newScore == score) &&
                            (rand.nextInt(quarterArea)!=0)
                         )
                    )
                    {
                        score = newScore;

                        x = i;
                        y = j;
                        dir = Direction.DOWN;
                    }

                }
                 if (legalPosition(word, i, j, Direction.ACROSS))
                {
                    int newScore = scoreWord(word, i, j, Direction.ACROSS);

                    if ( (newScore > score) || (
                         (newScore == score) &&
                         (rand.nextInt(quarterArea)!=0)
                        )
                    ) {
                        score = newScore;
                        x = i;
                        y = j;
                        dir = Direction.ACROSS;

                    }

                }
           }
        }

        if (score == -1)
            return null;

        return new Position(x, y, dir);
    }

    void clearPuzzle() {
        for (int i = 0; i < grid.length; i++) {
            for (int j= 0; j < grid[0].length; j++) {
                Cell cell = (Cell)getCell(i, j);
                cell.setContents(-1);
                cell.setWord(null, Direction.ACROSS);
                cell.setWord(null, Direction.DOWN);
            }
        }
    }

    class PuzzleFullException extends Exception {

        public PuzzleFullException () {
        }
        public PuzzleFullException (String msg) {
            super(msg);
        }
    }

    /**
     * Generate a crossword puzzle by scoring all the words, picking
     * from among the highest scoring, and repeating until all the
     * words are used or the puzzle is full.
     *
     * Directly transcribed from angry.c by Andrew Tridgell.
     *
     * @author Scott Shindeldecker
     * @author Jim Scarborough
     */
    void buildBestCrossword() throws PuzzleFullException {
        /*
         * Transcription notes: This is the last thing we did, and we did
         * it in a hurry.  There are no tests directly for this method, so
         * refactor with care.  The code offers several interesting
         * opportunities - development of a priority queue with random
         * selection of the best pick is just one example.
         */
        String[] list= (String[])
            wordList.keySet().toArray(new String[wordList.keySet().size()]);
        int i,j;
        int remaining=list.length; // remaining counts the words left
        boolean[] used=new boolean[list.length];
        int[] scores=new int[list.length];

        for (i=0;i<list.length;i++)  // initialize all to unused
            used[i] = false;

        clearPuzzle(); // blank puzzle
        while (remaining > 0) // while there are words
        {
            int n; // index for words
            int choose=0; // the index of the lucky word for insertion.
            Position pos;
            for (i=0;i<list.length;i++) // init scores to -1
                scores[i] = -1;

            // score all the unused words that fit in the puzzle
            for (n=0;n<list.length;n++)
                if (!used[n] && (pos=getBestPosition(list[n]))!=null)
                    scores[n] = scoreWord(list[n],pos);

            int numbest=0,
                bestscore=scores[0];
            int k;

            // Count the number of words that share the max score
            // and note the best score
            for (n=0;n<list.length;n++) // loop all words
            {
                if (scores[n] == bestscore)
                    numbest++;

                if (scores[n] > bestscore)
                {
                    bestscore = scores[n];
                    numbest = 1;
                }
            }

            // nothing more fits, bail.
            if (bestscore < 0) {
               throw new PuzzleFullException();
            }

            // pick one of the best score items at random
            k = rand.nextInt(numbest);

            // identify the kth best score word
            numbest=0;
            for (n=0;n<list.length;n++)
            {
                if (scores[n] == bestscore)
                {
                    if (numbest == k) choose=n;
                    numbest++;
                }
            }

            // Find its position again
            pos=getBestPosition(list[choose]);
            // Place it there
            placeWord(list[choose],pos);
            // Mark it used
            used[choose] = true;
            // count it off the list.
            remaining--;
        } // while(remaining>0)
    }

    /**
     * This method isn't much good.
     */
    void buildCrossword() throws PuzzleFullException {
        ArrayList words=new ArrayList(wordList.keySet());
        Collections.shuffle(words,rand);
        Iterator it = words.iterator();

        while (it.hasNext()) {
            String nextWord = (String)it.next();
            Position pos = getBestPosition(nextWord);

            if (pos == null) {
                throw new PuzzleFullException();
            }

            placeWord(nextWord, pos);
        }
    }

    int countLetters() {
        int count = 0;

        Iterator it = wordList.keySet().iterator();
        while (it.hasNext()) {

            String nextWord = (String)it.next();

            count+= nextWord.length();
        }
        return count;
    }

    Dimension getMinimumSize() {
        int minArea=(int)Math.ceil(countLetters() * (1+LOAD_RATIO));
        int y = (int)Math.ceil(Math.sqrt(minArea));
        int x = Math.max(getLongestWordLength(),y);
        y=Math.min(y,(int)Math.ceil(minArea/(double)x));
        y=Math.max(y,getShortestWordLength());
        return new Dimension(x,y);
    }

    void updateSize() {
        if (grid == null) {
            Dimension dim=getMinimumSize();
            genMatrix(dim.width,dim.height);
        }
        else {
             Dimension dim=getDimension();
             if (growAcrossNext) {
                dim.width++;
             } else {
                dim.height++;
             }
             growAcrossNext=!growAcrossNext;
             genMatrix(dim);
        }

    }

    int getLongestWordLength() {
        int longestWordLength = 0;
        Iterator it = wordList.keySet().iterator();
        while (it.hasNext()) {

            String nextWord = (String)it.next();
            longestWordLength = Math.max(longestWordLength, nextWord.length());
        }
        return longestWordLength;
    }
    
    int getShortestWordLength() {
        int len = Integer.MAX_VALUE;
        if (wordList.size()==0) return 0;
        Iterator it = wordList.keySet().iterator();
        while (it.hasNext()) {
            len=Math.min(len,((String)it.next()).length());
        }
        return len;
    }

    private static class Cell implements PuzzleCell, java.io.Serializable  {
        private int contents;
        private int userIndex;
        private String wordAcross, wordDown;

        public Cell(int contents, int userIndex) {
            this.contents=contents;
            this.userIndex=userIndex;
        }

        public int getContents() {
            return contents;
        }

       public void setContents(int contents) {
            this.contents = contents;
        }

        public int getUserIndex() {
            return userIndex;
        }

        public void setUserIndex(int ui) {
            this.userIndex = ui;
        }

        public String getWord(Direction direction) {
            if (direction == Direction.ACROSS)
                return wordAcross;
            else
                return wordDown;
        }

        public void setWord(String word, Direction direction) {
            if (direction == Direction.ACROSS)
                wordAcross = word;
            else
                wordDown = word;
        }
    }


    void placeWord(String word, int x, int y, Direction direction) {
        ((Cell)getCell(x,y)).setWord(word, direction);
        word = word.toUpperCase();


        if (direction == Direction.ACROSS) {
            for (int i = 0; i < word.length(); i++)
                ((Cell)getCell(x+i,y)).setContents((int)word.charAt(i));

        }
        else {
            for (int j = 0; j < word.length(); j++)
                ((Cell)getCell(x,y+j)).setContents((int)word.charAt(j));
        }

    }

     void placeWord(String word, Position pos) {
        placeWord(word, pos.x, pos.y, pos.direction);
    }


    /**
     * Return true if the word is not too long to begin at the given location
     * and extend in the given direction.
     */
    boolean wordFits(String word, int x, int y, Direction direction) {
        int len = word.length();

        if (direction == Direction.ACROSS) {
            return ((x + len) <= grid.length);
        }
        else {
            return ((y + len) <= grid[0].length);
        }
    }

    /**
     * Return true if there are letters immediately before or after the given
     * word.
     */
    boolean checkPrePostLetters(String word, int x, int y, Direction direction)
    {
        int len = word.length();

        if (direction == Direction.ACROSS) {
            return (
                ((x >= 1) && (getCell(x-1,y).getContents() != -1)) ||
                (
                    (x+len<grid.length) &&
                    (getCell(x + len,y).getContents() != -1)
                )
            );
        }
        else {
            return (
                ((y >= 1) && (getCell(x,y - 1).getContents() != -1)) ||
                (
                    (y+len<grid[0].length) &&
                    (getCell(x,y + len).getContents() != -1)
                )
            );
        }

    }

    /**
     * Return true if all of the letters along the given path for the word
     * are either the same as the word or blank.
     */
    boolean lettersMatch(String word, int x, int y, Direction direction) {
        return (countCrossedLetters(word,x,y,direction)!=-1);
    }

    /**
     * Return the number of non-blank letters crossed if this word is placed
     * here, -1 if it's an invalid placement based on the letters along
     * the way.
     */
    private int countCrossedLetters(String word, int x, int y,
        Direction direction) {
        int len = word.length();
        int count = 0;

        if (direction == Direction.ACROSS) {
            for (int i = 0; i < len; i++) {
                if (getCell(x+i, y).getContents() != -1 ){
                    if (getCell(x+i, y).getContents() == 
                        Character.toUpperCase(word.charAt(i)))
                        count++;
                    else
                        return -1;
                }
            }
        }
        else {
            for (int i = 0; i < len; i++) {
                if (getCell(x, y + i).getContents() != -1 ){
                    if (getCell(x, y + i).getContents() ==
                        Character.toUpperCase(word.charAt(i)))
                        count++;
                    else
                        return -1;
                }
            }
        }

        return count;
    }

    /**
     * Return true if there are adjacent letters that should preclude the
     * placement of the word in the given location - that is, words to which
     * adjacent letters belong must cross this word to allow this word to
     * be placed in the given location.
     */
    boolean checkAdjacentLetters(String word, int x, int y, Direction direction)
    {
        if (direction == Direction.ACROSS) {
            for (int i = x; i < (x+word.length()); i++) {
                if (
                    (getCell(i,y).getContents()==-1)     &&
                    (
                        (
                            ( y>0 )                      &&
                            (getCell(i,y-1).getContents()!=-1)
                        )                                ||
                        (
                            ( (y+1) < grid[0].length )   &&
                            ( getCell(i,y+1).getContents()!=-1 )
                        )
                    )
                ) // if
                return true;
            } // for
            return false;
        }
        else
        {
            for (int i = y; i < (y+word.length()); i++) {
                if (
                    (getCell(x,i).getContents()==-1)     &&
                    (
                        (
                            ( x>0 )                      &&
                            ( getCell(x-1,i).getContents()!=-1)
                        )                                ||
                        (
                            ( (x+1) < grid.length )    &&
                            ( getCell(x+1,i).getContents()!=-1)
                        )
                    )
                ) // if
                return true;
            } // for
            return false;
        }
    }

    //return true if
    boolean clearPathForWord(String word, int x, int y,
                                    Direction direction)

    {
        int len = word.length();
        if (direction == Direction.ACROSS) {
           for (int i = x; i < (x + len); i++) {
                if (getWordAtLocation(i, y, direction) !=  null)
                    return false;

            }
        }
        else {
           for (int i = y; i < (y + len); i++) {
                if (getWordAtLocation(x, i, direction) !=  null)
                    return false;

            }
        }
        return true;
    }

    /**
     * Returns true if and only if the word is legal to be added
     * to the crossword puzzle at the given location.
     */
    boolean legalPosition(String word, int x, int y, Direction direction)
    {

        return wordFits(word, x, y, direction) &&
            !checkPrePostLetters(word, x, y, direction) &&
            !checkAdjacentLetters(word, x, y, direction) &&
            lettersMatch(word, x, y, direction) &&
            clearPathForWord(word, x, y,direction);

    }

    /**
     * Add user indices to the grid at the starts of words,
     * starting with 1 and increasing, left to right, top to
     * bottom.
     */
    void setUserIndices() {
        int k=1;

        for (int i = 0; i < grid[0].length; i++)
        {
            for (int j = 0; j < grid.length; j++) {
                if ((getWordAtLocation(j, i, Direction.ACROSS) != null) ||
                   (getWordAtLocation(j, i, Direction.DOWN) != null))

                    ((Cell)getCell(j,i)).setUserIndex(k++);

            }
        }


    }

    int scoreWord(String word, Position pos) {
        return scoreWord(word,pos.x,pos.y,pos.direction);
    }

    int scoreWord(String word, int x, int y, Direction direction) {
        word=word.toUpperCase();
        int score = (direction.equals(Direction.DOWN))?1:0; // JES
        int count = countCrossedLetters(word,x,y,direction);
        int len = word.length();

        if (direction == Direction.ACROSS){

            if (y != 0 && y != (grid[0].length - 1)) {
                 score += 1;
            }
            if (getCell(x, y).getContents() == word.charAt(0))
                score -= 1;
            if (getCell(x + len - 1, y).getContents() ==
                word.charAt(len-1))
                score -= 1;

            score += count * 3;


        }

        else {

            if (x != 0 && x != (grid.length - 1)) {
                 score += 1;
            }
            if (getCell(x, y).getContents() == word.charAt(0))
                score -= 1;
            if (getCell(x, y + len -1).getContents() == word.charAt(len-1))
                score -= 1;
            score += count * 6;


        }

        return score;
    }

    /**
     * Returns the word starting at the given location in the given direction.
     */
    String getWordAtLocation(int x, int y, Direction direction) {
        return ((Cell)getCell(x,y)).getWord(direction);
    }

    public java.util.Map getWordList() {
        return wordList;

    }

    public PuzzleCell getCell(int x, int y) {
        return grid[x][y];
    }

    public java.util.Map getClues(Direction d) {
        TreeMap expected=new TreeMap();


        for (int i = 0; i < grid[0].length; i++)
        {
            for (int j = 0; j < grid.length; j++) {
                String word = getWordAtLocation(j, i, d);
                if (word != null)
                     expected.put(
                         new Integer((getCell(j,i).getUserIndex())),
                         wordList.get(word)
                     );
            }
        }

        return expected;
    }


    public java.awt.Dimension getDimension () {
        return new Dimension(grid.length, grid[0].length);
    }

    /**
     * JES
     */
    public String toString() {
        Dimension dim=getDimension();
        StringBuffer sb=new StringBuffer(30+dim.width*2*(dim.height+1));
        sb.append("AngryPuzzle:");
        sb.append(EOL);
        for (int y=0;y<dim.height;y++) {
            for (int x=0;x<dim.width;x++) {
                if (x>0) sb.append(' ');
                int c=getCell(x,y).getContents();
                sb.append((c>-1)?(char)c:'_');
            }
            sb.append(EOL);
        }
        return sb.toString();
    }
}
