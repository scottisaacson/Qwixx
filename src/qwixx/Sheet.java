package qwixx;

import java.awt.Color;
import java.util.*;
import java.lang.*;
import java.io.*;
import java.net.*;
import java.text.*;


public class Sheet {

    // Globals
    public final static int NONE = 0;
    public final static int MAXDIST = 20;
    
    public static final int FIRSTENTRY = 0;
    public static final int LASTENTRY = 10;
    public static final int ROWSIZE = 11;
    
    
    // Fields
    ArrayList<SheetEntry> reds;
    ArrayList<SheetEntry> yellows;
    ArrayList<SheetEntry> greens;
    ArrayList<SheetEntry> blues;
    
    int penalties;
    
    int redsScore;
    int yellowsScore;
    int greensScore;
    int bluesScore;
    int score;
    
    int redsMarked;
    int yellowsMarked;
    int greensMarked;
    int bluesMarked;
    
    Game game;
    Player player;

    public Sheet(Game g, Player p)
    {
        player = p;
        game = g;

        score = 0;
        redsScore = 0;
        yellowsScore = 0;
        greensScore = 0;
        bluesScore = 0;

        penalties = 0;
        
        reds = new ArrayList<SheetEntry>();
        for (int val = SheetEntry.MINVAL, count = 0; count < ROWSIZE; count++, val++)
        {
            SheetEntry se = new SheetEntry(this, Game.COLORS.RED, false, val);
            reds.add(se);
        }
        redsMarked = 0;
        
        yellows = new ArrayList<SheetEntry>();
        for (int val = SheetEntry.MINVAL, count = 0; count < ROWSIZE; count++, val++)
        {
            SheetEntry se = new SheetEntry(this, Game.COLORS.YELLOW, false, val);
            yellows.add(se);
        }
        yellowsMarked = 0;

        greens = new ArrayList<SheetEntry>();
        for (int val = SheetEntry.MAXVAL, count = 0; count < ROWSIZE; count++, val--)
        {
            SheetEntry se = new SheetEntry(this, Game.COLORS.GREEN, false, val);
            greens.add(se);
        }
        greensMarked = 0;

        blues = new ArrayList<SheetEntry>();
        for (int val = SheetEntry.MAXVAL, count = 0; count < ROWSIZE; count++, val--)
        {
            SheetEntry se = new SheetEntry(this, Game.COLORS.BLUE, false, val);
            blues.add(se);
        }
        bluesMarked = 0;
        


    }
    
    

    public SheetEntry findFirst(ArrayList<SheetEntry> l)
    {

        SheetEntry ret = null;

        for (SheetEntry s : l)
        {
            if (s.marked)
            {
                ret = null;
            }
            else
            {
                if (ret == null)
                {
                    ret = s;
                }
            }
        }

        return ret;
        
    }
    
    
    public SheetEntry findFirstRed()
    {
        if (game.redslocked) return null;
        return findFirst(reds);
    }

    public SheetEntry findFirstYellow()
    {
        if (game.yellowslocked) return null;
        return findFirst(yellows);
    }

    public SheetEntry findFirstGreen()
    {
        if (game.greenslocked) return null;
        return findFirst(greens);
    }
    
    public SheetEntry findFirstBlue()
    {
        if (game.blueslocked) return null;
        return findFirst(blues);
    }
    
    
    public SheetEntry getEntry(Game.COLORS color, ArrayList<SheetEntry> l, Integer val)
    {

        SheetEntry ret = null;
        
        if (l == null)
        {
            return ret;
        }

        String colorStr = null;
        int count = 0;
        int extreme = 0;
        
        switch (color) {
            case RED:
                colorStr = "Red";
                extreme = 12;
                count = howManyRedsMarked();
                break;
            case YELLOW:
                colorStr = "Yellow";
                extreme = 12;
                count = howManyYellowsMarked();
                break;
            case GREEN:
                colorStr = "Green";
                extreme = 2;
                count = howManyGreensMarked();
                break;
            case BLUE:
                colorStr = "Blue";
                extreme = 2;
                count = howManyBluesMarked();
                break;
            default:
                System.out.println("ERROR in getEntry: wrong color");
                break;
        }

        if (count < 5 && val == extreme)
        {
            // System.out.println("EXTREME: Can't get " + colorStr + ":" + val.toString() + " with only " + count + " marked.");
            return ret;
        }
        
        for (SheetEntry s : l)
        {
            if (s.val == val)
            {
                ret = s;
            }
        }

        return ret;
        
    }

    public SheetEntry getRedEntry(Integer val)
    {
        return getEntry(Game.COLORS.RED, reds, val);
    }
    
    public SheetEntry getYellowEntry(Integer val)
    {
        return getEntry(Game.COLORS.YELLOW, yellows, val);
    }
    
    public SheetEntry getGreenEntry(Integer val)
    {
        return getEntry(Game.COLORS.GREEN, greens, val);
    }

    public SheetEntry getBlueEntry(Integer val)
    {
        return getEntry(Game.COLORS.BLUE, blues, val);
    }

    public SheetEntry findEntry(Game.COLORS color, Integer val)
    {
        SheetEntry ret = null;
        
        if (color == Game.COLORS.RED) ret = findEntry(reds, val);
        if (color == Game.COLORS.YELLOW) ret = findEntry(yellows, val);
        if (color == Game.COLORS.GREEN) ret = findEntry(greens, val);
        if (color == Game.COLORS.BLUE) ret = findEntry(blues, val);
        
        return ret;
    }
    
    public SheetEntry findEntry(ArrayList<SheetEntry> l, Integer val)
    {
        SheetEntry ret = null;
    
        if (l == null)
        {
            return ret;
        }

        for (SheetEntry s : l)
        {
            if (s.val == val)
            {
                ret = s;
            }
        }

        return ret;
        
    }


    public int scoreThisCount(int count)
    {
        int ret;
        switch (count) {
            case 0:
                ret = 0;
                break;
            case 1:
                ret = 1;
                break;
            case 2:
                ret = 3;
                break;
            case 3:
                ret = 6;
                break;
            case 4:
                ret = 10;
                break;
            case 5:
                ret = 15;
                break;
            case 6:
                ret = 21;
                break;
            case 7:
                ret = 28;
                break;
            case 8:
                ret = 36;
                break;
            case 9:
                ret = 45;
                break;
            case 10:
                ret = 55;
                break;
            case 11:
                ret = 66;
                break;
            default:
                ret = 0;
                break;
        }
        return ret;
    }
    
    

    public int howManyPenalties()
    {
        return this.penalties;
    }

    
    public int howManyMarked(ArrayList<SheetEntry> l)
    {
        int count = 0;
        for (SheetEntry e : l)
        {
            if (e.marked == true)
            {
                count++;
            }
        }
        
        return count;
    }
    
    public int howManyRedsMarked()
    {
        return howManyMarked(reds);
    }

    public int howManyYellowsMarked()
    {
        return howManyMarked(yellows);
    }

    public int howManyGreensMarked()
    {
        return howManyMarked(greens);
    }

    public int howManyBluesMarked()
    {
        return howManyMarked(blues);
    }


    public String printBetter(ArrayList<SheetEntry> row, ArrayList<Move> moves)
    {

        char flag = 0x2691;
        char star = 0x2605;
        char whitecircle = 0x26AA;
        char blackcircle = 0x26AB;
        char checkx = 'x';
        char asterisk = '*';
        
        char marked = checkx;
        char possible = asterisk;
        
        StringBuffer out = new StringBuffer();
        
        int count = 0;
        for (SheetEntry e : row)
        {
            // Print the Value
            out.append(String.format("%2d:", e.val));
            if (e.marked)
            {
                out.append(marked);
            }
            else
            {
                boolean isMove = false;
                for (Move m : moves)
                {
                    if (m.se.equals(e))
                    {
                        isMove = true;
                    }
                }
                if (isMove)
                {
                    out.append(possible);
                }
                else
                {
                    out.append(" ");
                }
            }
            count++;
            if (count < 12)
            {
                out.append("  ");
            }
            
        }

        String ret = out.toString();
        return ret;

    }


    public void printBetter(ArrayList<Move> moves) {
        
        StringBuffer sb;
        String r1;
        
        int redsmarked = howManyRedsMarked();
        int yellowsmarked = howManyYellowsMarked();
        int greensmarked = howManyGreensMarked();
        int bluesmarked = howManyBluesMarked();
        
        sb = new StringBuffer();
        sb.append("RED(" + redsmarked + ")    ");
        r1 = printBetter(reds, moves);
        sb.append(r1);
        System.out.println(sb.toString());

        sb = new StringBuffer();
        sb.append("YELLOW(" + yellowsmarked + ") ");
        r1 = printBetter(yellows, moves);
        sb.append(r1);
        System.out.println(sb.toString());

        sb = new StringBuffer();
        sb.append("GREEN(" + greensmarked + ")  ");
        r1 = printBetter(greens, moves);
        sb.append(r1);
        System.out.println(sb.toString());

        sb = new StringBuffer();
        sb.append("BLUE(" + bluesmarked + ")   ");
        r1 = printBetter(blues, moves);
        sb.append(r1);
        System.out.println(sb.toString());
 
        System.out.println("PENALTIES: " + penalties);
        System.out.println("SCORE: " + score);
        System.out.println("");
        
    }
    
    public boolean shouldLock(ArrayList<SheetEntry> l, Game.COLORS c)
    {
        
        /*
        SheetEntry first = l.get(FIRSTENTRY);
        SheetEntry last = l.get(LASTENTRY);

        if (c == Game.COLORS.RED || c == Game.COLORS.YELLOW)
        {
            if (first.marked) return true;
        if (last.marked) return true;
        return false;
        */
        SheetEntry last = l.get(LASTENTRY);
        if (last.marked) return true;
        return false;
    }
            
    public boolean shouldLockReds()
    {
        return shouldLock(reds, Game.COLORS.RED);
    }

    public boolean shouldLockYellows()
    {
        return shouldLock(yellows, Game.COLORS.YELLOW);
    }

    public boolean shouldLockGreens()
    {
        return shouldLock(greens, Game.COLORS.GREEN);
    }

    public boolean shouldLockBlues()
    {
        return shouldLock(blues, Game.COLORS.BLUE);
    }
    
    public void score()
    {
        score = 0;
        
        redsMarked = howManyRedsMarked();
        redsScore = scoreThisCount(redsMarked);
        score = score + redsScore;

        yellowsMarked = howManyYellowsMarked();
        yellowsScore = scoreThisCount(yellowsMarked);
        score = score + yellowsScore;

        greensMarked = howManyGreensMarked();
        greensScore = scoreThisCount(greensMarked);
        score = score + greensScore;

        bluesMarked = howManyBluesMarked();
        bluesScore = scoreThisCount(bluesMarked);
        score = score + bluesScore;
        
        int penaltyScore = penalties * 5;
        score = score - penaltyScore;
      
    }
    
    

}


