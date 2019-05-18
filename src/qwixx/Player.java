package qwixx;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.net.*;
import java.text.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;



public class Player {

    enum STRATEGY {
        Human,
        Computer
    }
    
    ArrayList<Move> whiteMoves;
    ArrayList<Move> colorMoves;
    SheetEntry thisOne = null;
    
    Game game;
    String name;
    Sheet sheet;
    
    boolean showComputerMoves = false;
    
    STRATEGY strategy;
    
    
    public Player(Game game, String name, STRATEGY s)
    {
        this.game = game;
        this.name = name;
        this.sheet = new Sheet(game, this);
        this.strategy = s;
        this.whiteMoves = new ArrayList<Move>();
        this.colorMoves = new ArrayList<Move>();
        
    }
    
    public boolean shouldLockReds()
    {
        return sheet.shouldLockReds();
    }

    public boolean shouldLockYellows()
    {
        return sheet.shouldLockYellows();
    }
    
    public boolean shouldLockGreens()
    {
        return sheet.shouldLockGreens();
    }

    public boolean shouldLockBlues()
    {
        return sheet.shouldLockBlues();
    }

    
    public void score()
    {
        sheet.score();
        
    }
    
    
    public void playWhites()
    {

        findWhiteMoves();
        
        // Based on Strategy, find the best move
        SheetEntry thisOne = null;
        if (strategy == STRATEGY.Human)
        {
            thisOne = chooseWhiteHuman();
        }
        if (strategy == STRATEGY.Computer)
        {
            thisOne = chooseWhiteComputer();
        }
        
        if (thisOne == null)
        {
            score();
            if (game.interactive && strategy == Player.STRATEGY.Computer)
            {
                var pcam = new PlayerChoseAMove(this, Move.MOVETYPE.WHITES, thisOne);
                pcam.buildAndShow();

                if (pcam.isQuit)
                {
                    game.gameover = Game.GAMEOVER.QUIT;
                }
                pcam.dispose();

            }
            return;
        }

        // MarkIt
        thisOne.markIt();
        score();
        
        if (game.interactive && strategy == Player.STRATEGY.Computer)
        {
            var pcam = new PlayerChoseAMove(this, Move.MOVETYPE.WHITES, thisOne);
            pcam.buildAndShow();

            if (pcam.isQuit)
            {
                game.gameover = Game.GAMEOVER.QUIT;
            }
            pcam.dispose();
        }
        
        return;
        
    }

    public void playWhitesConsideringColors()
    {

        findWhiteMoves();
        findColorMoves();
        
        // Based on Strategy, find the best move
        SheetEntry thisOne = null;
        if (strategy == STRATEGY.Human)
        {
            thisOne = chooseWhiteConsideringColorsHuman();
        }
        if (strategy == STRATEGY.Computer)
        {
            thisOne = chooseWhiteComputer();
        }
        
        if (thisOne == null)
        {
            score();
            if (game.interactive && strategy == Player.STRATEGY.Computer)
            {
                var pcam = new PlayerChoseAMove(this, Move.MOVETYPE.WHITES, thisOne);
                pcam.buildAndShow();

                if (pcam.isQuit)
                {
                    game.gameover = Game.GAMEOVER.QUIT;
                }
                pcam.dispose();
            }
            return;
        }

        // MarkIt
        thisOne.markIt();
        score();

        if (game.interactive && strategy == Player.STRATEGY.Computer)
        {
            var pcam = new PlayerChoseAMove(this, Move.MOVETYPE.WHITES, thisOne);
            pcam.buildAndShow();

            if (pcam.isQuit)
            {
                game.gameover = Game.GAMEOVER.QUIT;
            }
            pcam.dispose();
        }
        
        return;
        
    }

    
    public void playColors()
    {

        findColorMoves();
        
        // Based on Strategy, find the best move
        SheetEntry thisOne = null;
        if (strategy == STRATEGY.Human)
        {
            thisOne = chooseColorHuman();
        }
        if (strategy == STRATEGY.Computer)
        {
            thisOne = chooseColorComputer();
        }

        if (thisOne == null)
        {
            sheet.penalties++;
            score();

            if (game.interactive && strategy == Player.STRATEGY.Computer)
            {
                var pcam = new PlayerChoseAMove(this, Move.MOVETYPE.COLORS, thisOne);
                pcam.buildAndShow();

                if (pcam.isQuit)
                {
                    game.gameover = Game.GAMEOVER.QUIT;
                }
                pcam.dispose();
            }

            return;
        }
        
        // MarkIt
        thisOne.markIt();
        score();
        
        if (game.interactive && strategy == Player.STRATEGY.Computer)
        {
            var pcam = new PlayerChoseAMove(this, Move.MOVETYPE.COLORS, thisOne);
            pcam.buildAndShow();

            if (pcam.isQuit)
            {
                game.gameover = Game.GAMEOVER.QUIT;
            }
            pcam.dispose();
        }

        return;
        
    }

    public void findWhiteMoves()
    {
        whiteMoves.clear();
        
        Move m = null;
        SheetEntry first = null;
        SheetEntry found = null;
        Sum whites = null;
        
        int howManyRedsMarked = sheet.howManyRedsMarked();
        int howManyYellowsMarked = sheet.howManyYellowsMarked();
        int howManyGreensMarked = sheet.howManyGreensMarked();
        int howManyBluesMarked = sheet.howManyBluesMarked();
      
        whites = game.dice.whites;
        int whitesval = whites.getVal();
        int dist = 0;
        int firstval = 0;
        int foundval = 0;
        
        if (!game.redslocked)
        {
            first = sheet.findFirstRed();
            if (first != null)
            {
                firstval = first.getVal();
                if (whitesval >= firstval)
                {
                    found = sheet.getRedEntry(whitesval);
                    if (found != null)
                    {
                        foundval = found.getVal();
                        dist = foundval - firstval;
                        m = new Move(found, dist, howManyRedsMarked);
                        whiteMoves.add(m);
                    }
                }
            }
        }
            
        if (!game.yellowslocked)
        {
            first = sheet.findFirstYellow();
            if (first != null)
            {
                firstval = first.getVal();
                if (whitesval >= firstval)
                {
                    found = sheet.getYellowEntry(whitesval);
                    if (found != null)
                    {
                        foundval = found.getVal();
                        dist = foundval - firstval;
                        m = new Move(found, dist, howManyYellowsMarked);
                        whiteMoves.add(m);
                    }
                }
            }
        }

        if (!game.greenslocked)
        {
            first = sheet.findFirstGreen();
            if (first != null)
            {
                firstval = first.getVal();
                if (whitesval <= firstval)
                {
                    found = sheet.getGreenEntry(whitesval);
                    if (found != null)
                    {
                        foundval = found.getVal();
                        dist = firstval - foundval;
                        m = new Move(found, dist, howManyGreensMarked);
                        whiteMoves.add(m);
                    }
                }
            }
        }

        if (!game.blueslocked)
        {
            first = sheet.findFirstBlue();
            if (first != null)
            {
                firstval = first.getVal();
                if (whitesval <= firstval)
                {
                    found = sheet.getBlueEntry(whitesval);
                    if (found != null)
                    {
                        foundval = found.getVal();
                        dist = firstval - foundval;
                        m = new Move(found, dist, howManyBluesMarked);
                        whiteMoves.add(m);
                    }
                }
            }
        }

    }
    
    public void findColorMoves()
    {

        colorMoves.clear();
        Move m = null;
        SheetEntry first = null;
        SheetEntry found = null;
        Sum red1 = null;
        Sum red2 = null;
        Sum yellow1 = null;
        Sum yellow2 = null;
        Sum green1 = null;
        Sum green2 = null;
        Sum blue1 = null;
        Sum blue2 = null;
      
        red1 = game.dice.red1;
        red2 = game.dice.red2;
        yellow1 = game.dice.yellow1;
        yellow2 = game.dice.yellow2;
        green1 = game.dice.green1;
        green2 = game.dice.green2;
        blue1 = game.dice.blue1;
        blue2 = game.dice.blue2;
        
        int rolledval1 = 0;
        int rolledval2 = 0;
        int dist = 0;
        int firstval = 0;
        int foundval = 0;

        int howManyRedsMarked = sheet.howManyRedsMarked();
        int howManyYellowsMarked = sheet.howManyYellowsMarked();
        int howManyGreensMarked = sheet.howManyGreensMarked();
        int howManyBluesMarked = sheet.howManyBluesMarked();
      
        
        if (!game.redslocked)
        {
            first = sheet.findFirstRed();
            if (first != null)
            {
                firstval = first.getVal();
                
                rolledval1 = red1.getVal();
                if (rolledval1 >= firstval)
                {
                    found = sheet.getRedEntry(rolledval1);
                    if (found != null)
                    {
                        foundval = found.getVal();
                        dist = foundval - firstval;
                        m = new Move(found, dist, howManyRedsMarked);
                        colorMoves.add(m);
                    }
                }
               

                rolledval2 = red2.getVal();
                if (rolledval2 >= firstval && rolledval2 != rolledval1)
                {
                    found = sheet.getRedEntry(rolledval2);
                    if (found != null)
                    {
                        foundval = found.getVal();
                        dist = foundval - firstval;
                        m = new Move(found, dist, howManyRedsMarked);
                        colorMoves.add(m);
                    }
                }
            }
        }
            

        if (!game.yellowslocked)
        {
            first = sheet.findFirstYellow();
            if (first != null)
            {
                firstval = first.getVal();

                rolledval1 = yellow1.getVal();
                if (rolledval1 >= firstval)
                {
                    found = sheet.getYellowEntry(rolledval1);
                    if (found != null)
                    {
                        foundval = found.getVal();
                        dist = foundval - firstval;
                        m = new Move(found, dist, howManyYellowsMarked);
                        colorMoves.add(m);
                    }
                }

                rolledval2 = yellow2.getVal();
                if (rolledval2 >= firstval && rolledval2 != rolledval1)
                {
                    found = sheet.getYellowEntry(rolledval2);
                    if (found != null)
                    {
                        foundval = found.getVal();
                        dist = foundval - firstval;
                        m = new Move(found, dist, howManyYellowsMarked);
                        colorMoves.add(m);
                    }
                }
            }
        }
            
        if (!game.greenslocked)
        {
            first = sheet.findFirstGreen();
            if (first != null)
            {
                firstval = first.getVal();

                rolledval1 = green1.getVal();
                if (rolledval1 <= firstval)
                {
                    found = sheet.getGreenEntry(rolledval1);
                    if (found != null)
                    {
                        foundval = found.getVal();
                        dist = firstval - foundval;
                        m = new Move(found, dist, howManyGreensMarked);
                        colorMoves.add(m);
                    }
                }

                rolledval2 = green2.getVal();
                if (rolledval2 <= firstval && rolledval2 != rolledval1)
                {
                    found = sheet.getGreenEntry(rolledval2);
                    if (found != null)
                    {
                        foundval = found.getVal();
                        dist = firstval - foundval;
                        m = new Move(found, dist, howManyGreensMarked);
                        colorMoves.add(m);
                    }
                }
            }
        }
            
            
        if (!game.blueslocked)
        {
            first = sheet.findFirstBlue();
            if (first != null)
            {
                firstval = first.getVal();

                rolledval1 = blue1.getVal();
                if (rolledval1 <= firstval)
                {
                    found = sheet.getBlueEntry(rolledval1);
                    if (found != null)
                    {
                        foundval = found.getVal();
                        dist = firstval - foundval;
                        m = new Move(found, dist, howManyBluesMarked);
                        colorMoves.add(m);
                    }
                }

                rolledval2 = blue2.getVal();
                if (rolledval2 <= firstval && rolledval2 != rolledval1)
                {
                    found = sheet.getBlueEntry(rolledval2);
                    if (found != null)
                    {
                        foundval = found.getVal();
                        dist = firstval - foundval;
                        m = new Move(found, dist, howManyBluesMarked);
                        colorMoves.add(m);
                    }
                }
            }
        }
    }
    
    

    // Human

    public SheetEntry chooseColorHuman()
    {
        SheetEntry thisOne = null;
        
        thisOne = chooseHumanMove(Move.MOVETYPE.COLORS, false);
        
        return thisOne;
    }

    public SheetEntry chooseWhiteHuman()
    {
        SheetEntry thisOne = null;
        
        thisOne = chooseHumanMove(Move.MOVETYPE.WHITES, false);
        
        return thisOne;
    }

    
    public SheetEntry chooseWhiteConsideringColorsHuman()
    {
        SheetEntry thisOne = null;
        
        thisOne = chooseHumanMove(Move.MOVETYPE.WHITES, true);
        
        return thisOne;
    }

    
    public SheetEntry chooseHumanMove(Move.MOVETYPE type, boolean consider)
    {

        SheetEntry thisOne = null;

        ArrayList<Move> theseMoves = null;
        
        if (type == Move.MOVETYPE.COLORS)
        {
            theseMoves = colorMoves;
        }
        else
        {
            theseMoves = whiteMoves;
        }

        PlayerChooseMove playerChooseMove = null;
        
        if (consider == true)
        {
            playerChooseMove = new PlayerChooseMove(this, type, whiteMoves, colorMoves);
        }
        else
        {
            playerChooseMove = new PlayerChooseMove(this, type, theseMoves);
        }

        playerChooseMove.buildAndShow();
        
        thisOne = playerChooseMove.thisOne;
        if (playerChooseMove.isquit)
        {
            game.gameover = Game.GAMEOVER.QUIT;
        }

        playerChooseMove.dispose();
            
        return thisOne;

    }

    
    public SheetEntry getHumanMove(Move.MOVETYPE type)
    {
        
    
        SheetEntry thisOne = null;
        
        String typeStr = null;
        if (type == Move.MOVETYPE.COLORS)
        {
            typeStr = "COLOR";
        }
        else
        {
            typeStr = "WHITE";
        }
        
        try {
            
            Game.COLORS color;
            Integer value;
            
            System.out.println(name + ": select a " + typeStr + " move:");
            BufferedReader br = null;
            br = new BufferedReader(new InputStreamReader(System.in));
            
            boolean done = false;
            while (!done)
            {
                String inStr = br.readLine();

                if (inStr == null || inStr.length() == 0)
                {
                    thisOne = null;
                    done = true;
                    continue;
                }

                if (inStr.toLowerCase().equals("done"))
                {
                    thisOne = null;
                    done = true;
                    continue;
                }

                if (inStr.toLowerCase().equals("none"))
                {
                    thisOne = null;
                    done = true;
                    continue;
                }
                
                String[] vals = inStr.split(" ");

                if (vals == null || vals.length != 2 || vals[0] == null || vals[0].length() == 0 || vals[1] == null || vals[1].length() == 0)
                {
                    System.out.println("Try again");
                    continue;
                }
                
                if (vals[0].toLowerCase().equals("r") || vals[0].toLowerCase().equals("red") )
                {
                    color = Game.COLORS.RED;
                }
                else if (vals[0].toLowerCase().equals("y") || vals[0].toLowerCase().equals("yellow") )
                {
                    color = Game.COLORS.YELLOW;
                } 
                else if (vals[0].toLowerCase().equals("g") || vals[0].toLowerCase().equals("green") )
                {
                    color = Game.COLORS.GREEN;
                } 
                else if (vals[0].toLowerCase().equals("b") || vals[0].toLowerCase().equals("blue") )
                {
                    color = Game.COLORS.BLUE;
                } 
                else
                {
                    System.out.println("Try again");
                    continue;
                }

                try
                {
                    value = Integer.parseInt(vals[1]);
                }
                catch (Exception e)
                {
                    System.out.println("Try again");
                    continue;
                }
                
                String cn = "ERROR";
                if (color == Game.COLORS.RED) cn = "RED";
                if (color == Game.COLORS.YELLOW) cn = "YELLOW";
                if (color == Game.COLORS.GREEN) cn = "GREEN";
                if (color == Game.COLORS.BLUE) cn = "BLUE";
                
                // check to see if selected move is in the list of moves
                boolean found = false;
                if (type == Move.MOVETYPE.COLORS)
                {
                    for (Move m : colorMoves)
                    {
                        if (m.se.color == color && m.se.val == value)
                        {
                            found = true;
                        }
                    }
                }
                if (type == Move.MOVETYPE.WHITES)
                {
                    for (Move m : whiteMoves)
                    {
                        if (m.se.color == color && m.se.val == value)
                        {
                            found = true;
                        }
                    }
                }
                
                if (found == false)
                {
                    System.out.println("CAN'T SELECT: " + cn + " " + value.toString());
                    continue;
                }
                

                // check to see if selected move is in the list of moves
                
                SheetEntry tmp = null;
                if (color == Game.COLORS.RED)
                {
                     tmp = sheet.getRedEntry(value);
                     if (tmp != null && tmp.marked == false)
                     {
                        // System.out.println("SELECTED: " + cn + " " + value.toString());
                        thisOne = tmp;
                        done = true;
                     }
                     else
                     {
                        System.out.println("CAN'T SELECT: " + cn + " " + value.toString());
                        continue;
                     }
                }

                if (color == Game.COLORS.YELLOW)
                {
                     tmp = sheet.getYellowEntry(value);
                     if (tmp != null && tmp.marked == false)
                     {
                        // System.out.println("SELECTED: " + cn + " " + value.toString());
                        thisOne = tmp;
                        done = true;
                     }
                     else
                     {
                        System.out.println("CAN'T SELECT: " + cn + " " + value.toString());
                        continue;
                     }
                }

                if (color == Game.COLORS.GREEN)
                {
                     tmp = sheet.getGreenEntry(value);
                     if (tmp != null && tmp.marked == false)
                     {
                        // System.out.println("SELECTED: " + cn + " " + value.toString());
                        thisOne = tmp;
                        done = true;
                     }
                     else
                     {
                        System.out.println("CAN'T SELECT: " + cn + " " + value.toString());
                        continue;
                     }
                }
                
                if (color == Game.COLORS.BLUE)
                {
                     tmp = sheet.getBlueEntry(value);
                     if (tmp != null && tmp.marked == false)
                     {
                        // System.out.println("SELECTED: " + cn + " " + value.toString());
                        thisOne = tmp;
                        done = true;
                     }
                     else
                     {
                        System.out.println("CAN'T SELECT: " + cn + " " + value.toString());
                        continue;
                     }
                }
            }
        }
        catch (IOException e)
        {
            System.err.println(e.toString());
            thisOne = null;
        }
        
        return thisOne;
    }
    

    // Computer

    public SheetEntry chooseComputer(Move.MOVETYPE type)
    {

        int maxwval = 0;
        int thiswval = 0;
        
        SheetEntry thisOne = null;
        
        ArrayList<Move> theseMoves = null;
        
        if (type == Move.MOVETYPE.WHITES)
        {
            theseMoves = whiteMoves;
        }
        else
        {
            theseMoves = colorMoves;
        }
        
        if (theseMoves == null || theseMoves.size() == 0)
        {
            return thisOne;
        }
        
        ArrayList<Move> tmp = new ArrayList<Move> ();
        
        for (Move m : theseMoves)
        {
            
            // weight = 0 : 100, 100 is better
            int distval;
            int markedval;
            int extremeval;
            
            if (m.distance == 0)
            {
                distval = 3;
            }
            else if (m.distance == 1)
            {
                distval = 2;
            }
            else if (m.distance == 2)
            {
                distval = 1;
            }
            else
            {
                distval = 0;
            }
            
            markedval = m.howManyMarked;
            
            if (m.se.val == 2 || m.se.val == 12)
            {
                extremeval = 6;
            }
            else if (m.se.val == 3 || m.se.val == 11)
            {
                extremeval = 5;
            }
            else if (m.se.val == 4 || m.se.val == 10)
            {
                extremeval = 4;
            }
            else if (m.se.val == 5 || m.se.val == 9)
            {
                extremeval = 3;
            }
            else if (m.se.val == 6 || m.se.val == 8)
            {
                extremeval = 2;
            }
            else
            {
                extremeval = 1;
            }
            
            if (distval != 0)
            {
                int tmpval = 0;
                
                int dw = 1;
                int ew = 10;
                int mw = 1;
                
                tmpval = dw * (distval * 33);   // each point 100 / 3 
                thiswval = thiswval + tmpval;
                tmpval = ew * (tmpval + extremeval * 16);   // each point 100 / 6 
                thiswval = thiswval + tmpval;
                tmpval = mw * (tmpval + markedval * 9);   // each point 100 / 11 
                thiswval = thiswval + tmpval;
                
                thiswval = (thiswval / (dw + ew + mw));
            
            }
            else
            {
                thiswval = 0;
            }
            
            if (thiswval > 0)
            {
                if (thiswval > maxwval)
                {
                    tmp.clear();
                    maxwval = thiswval;
                    tmp.add(m);
                }
                else if (thiswval == maxwval)
                {
                    tmp.add(m);
                }
                else
                {
                    // 
                }
            }
        }
        
        Random rand = new Random();
        int index = 0;
        
        if (tmp.size() != 0)
        {
            index = rand.nextInt(tmp.size());
            thisOne = tmp.get(index).se;
            return thisOne;
        }

        return thisOne;
    }

    
    public SheetEntry chooseColorComputer()
    {
        SheetEntry thisOne = null;
        
        thisOne = chooseComputer(Move.MOVETYPE.COLORS);

        return thisOne;
    }
    
    public SheetEntry chooseWhiteComputer()
    {
        SheetEntry thisOne = null;
        
        thisOne = chooseComputer(Move.MOVETYPE.WHITES);

        return thisOne;
    }
    
}

    
    




/************************************************************************
 * 
 * @author sisaacson
 */

/*
public class Player {

    enum STRATEGY {
        Human,
        Computer
    }
    
    ArrayList<Move> whiteMoves;
    ArrayList<Move> colorMoves;
    
    Game game;
    String name;
    Sheet sheet;
    
    STRATEGY strategy;
    
    
    public Player(Game game, String name, STRATEGY s)
    {
        this.game = game;
        this.name = name;
        this.sheet = new Sheet(game, this);
        this.strategy = s;
        this.whiteMoves = new ArrayList<Move>();
        this.colorMoves = new ArrayList<Move>();
        
    }
    
    public boolean shouldLockReds()
    {
        return sheet.shouldLockReds();
    }

    public boolean shouldLockYellows()
    {
        return sheet.shouldLockYellows();
    }
    
    public boolean shouldLockGreens()
    {
        return sheet.shouldLockGreens();
    }

    public boolean shouldLockBlues()
    {
        return sheet.shouldLockBlues();
    }

    
    
    public void printSummary()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(name + " SCORE=" + sheet.score + ", PENALTIES=" + sheet.howManyPenalties() + ", ");
        sb.append("RED=" + sheet.howManyRedsMarked() + ", ");
        sb.append("YELLOW=" + sheet.howManyYellowsMarked() + ", ");
        sb.append("GREEN=" + sheet.howManyGreensMarked() + ", ");
        sb.append("BLUE=" + sheet.howManyBluesMarked());
        System.out.println(sb.toString());
    }
    
    public void score()
    {
        sheet.score();
        
    }
    
    
    public void playWhites()
    {

        findWhiteMoves();
        if (whiteMoves.size() == 0)
        {
            return;
        }
        
        // Based on Strategy, find the best move
        SheetEntry thisOne = null;
        if (strategy == STRATEGY.Human)
        {
            thisOne = chooseWhiteHuman();
        }
        else if (strategy == STRATEGY.Computer)
        {
            thisOne = chooseWhiteComputer();
        }
        else
        {
            return;
        }
        
        if (thisOne == null)
        {
            return;
        }

        // MarkIt
        thisOne.markIt();
        
    }
    
    public void playColors()
    {

        findColorMoves();
        if (colorMoves.size() == 0)
        {
            sheet.penalties++;
            return;
        }

        // Based on Strategy, find the best move
        SheetEntry thisOne = null;
        if (strategy == STRATEGY.Human)
        {
            thisOne = chooseColorHuman();
        }
        else if (strategy == STRATEGY.Computer)
        {
            thisOne = chooseColorComputer();
        }
        else
        {
            sheet.penalties++;
            return;
        }
        
        if (thisOne == null)
        {
            sheet.penalties++;
            return;
        }
        
        // MarkIt
        thisOne.markIt();
        
    }

    public void findWhiteMoves()
    {
        whiteMoves.clear();
        
        Move m = null;
        SheetEntry first = null;
        SheetEntry found = null;
        Sum whites = null;
        
        int howManyRedsMarked = sheet.howManyRedsMarked();
        int howManyYellowsMarked = sheet.howManyYellowsMarked();
        int howManyGreensMarked = sheet.howManyGreensMarked();
        int howManyBluesMarked = sheet.howManyBluesMarked();
      
        whites = game.dice.whites;
        int whitesval = whites.getVal();
        int dist = 0;
        int firstval = 0;
        int foundval = 0;
        
        if (!game.redslocked)
        {
            first = sheet.findFirstRed();
            if (first != null)
            {
                firstval = first.getVal();
                if (whitesval >= firstval)
                {
                    found = sheet.getRedEntry(whitesval);
                    if (found != null)
                    {
                        foundval = found.getVal();
                        dist = foundval - firstval;
                        m = new Move(found, dist, howManyRedsMarked);
                        whiteMoves.add(m);
                    }
                }
            }
        }
            
        if (!game.yellowslocked)
        {
            first = sheet.findFirstYellow();
            if (first != null)
            {
                firstval = first.getVal();
                if (whitesval >= firstval)
                {
                    found = sheet.getYellowEntry(whitesval);
                    if (found != null)
                    {
                        foundval = found.getVal();
                        dist = foundval - firstval;
                        m = new Move(found, dist, howManyYellowsMarked);
                        whiteMoves.add(m);
                    }
                }
            }
        }

        if (!game.greenslocked)
        {
            first = sheet.findFirstGreen();
            if (first != null)
            {
                firstval = first.getVal();
                if (whitesval <= firstval)
                {
                    found = sheet.getGreenEntry(whitesval);
                    if (found != null)
                    {
                        foundval = found.getVal();
                        dist = firstval - foundval;
                        m = new Move(found, dist, howManyGreensMarked);
                        whiteMoves.add(m);
                    }
                }
            }
        }

        if (!game.blueslocked)
        {
            first = sheet.findFirstBlue();
            if (first != null)
            {
                firstval = first.getVal();
                if (whitesval <= firstval)
                {
                    found = sheet.getBlueEntry(whitesval);
                    if (found != null)
                    {
                        foundval = found.getVal();
                        dist = firstval - foundval;
                        m = new Move(found, dist, howManyBluesMarked);
                        whiteMoves.add(m);
                    }
                }
            }
        }

    }
    
    public void findColorMoves()
    {

        colorMoves.clear();
        Move m = null;
        SheetEntry first = null;
        SheetEntry found = null;
        Sum red1 = null;
        Sum red2 = null;
        Sum yellow1 = null;
        Sum yellow2 = null;
        Sum green1 = null;
        Sum green2 = null;
        Sum blue1 = null;
        Sum blue2 = null;
      
        red1 = game.dice.red1;
        red2 = game.dice.red2;
        yellow1 = game.dice.yellow1;
        yellow2 = game.dice.yellow2;
        green1 = game.dice.green1;
        green2 = game.dice.green2;
        blue1 = game.dice.blue1;
        blue2 = game.dice.blue2;
        
        int rolledval1 = 0;
        int rolledval2 = 0;
        int dist = 0;
        int firstval = 0;
        int foundval = 0;

        int howManyRedsMarked = sheet.howManyRedsMarked();
        int howManyYellowsMarked = sheet.howManyYellowsMarked();
        int howManyGreensMarked = sheet.howManyGreensMarked();
        int howManyBluesMarked = sheet.howManyBluesMarked();
      
        
        if (!game.redslocked)
        {
            first = sheet.findFirstRed();
            if (first != null)
            {
                firstval = first.getVal();
                
                rolledval1 = red1.getVal();
                if (rolledval1 >= firstval)
                {
                    found = sheet.getRedEntry(rolledval1);
                    if (found != null)
                    {
                        foundval = found.getVal();
                        dist = foundval - firstval;
                        m = new Move(found, dist, howManyRedsMarked);
                        colorMoves.add(m);
                    }
                }
               

                rolledval2 = red2.getVal();
                if (rolledval2 >= firstval && rolledval2 != rolledval1)
                {
                    found = sheet.getRedEntry(rolledval2);
                    if (found != null)
                    {
                        foundval = found.getVal();
                        dist = foundval - firstval;
                        m = new Move(found, dist, howManyRedsMarked);
                        colorMoves.add(m);
                    }
                }
            }
        }
            

        if (!game.yellowslocked)
        {
            first = sheet.findFirstYellow();
            if (first != null)
            {
                firstval = first.getVal();

                rolledval1 = yellow1.getVal();
                if (rolledval1 >= firstval)
                {
                    found = sheet.getYellowEntry(rolledval1);
                    if (found != null)
                    {
                        foundval = found.getVal();
                        dist = foundval - firstval;
                        m = new Move(found, dist, howManyYellowsMarked);
                        colorMoves.add(m);
                    }
                }

                rolledval2 = yellow2.getVal();
                if (rolledval2 >= firstval && rolledval2 != rolledval1)
                {
                    found = sheet.getYellowEntry(rolledval2);
                    if (found != null)
                    {
                        foundval = found.getVal();
                        dist = foundval - firstval;
                        m = new Move(found, dist, howManyYellowsMarked);
                        colorMoves.add(m);
                    }
                }
            }
        }
            
        if (!game.greenslocked)
        {
            first = sheet.findFirstGreen();
            if (first != null)
            {
                firstval = first.getVal();

                rolledval1 = green1.getVal();
                if (rolledval1 <= firstval)
                {
                    found = sheet.getGreenEntry(rolledval1);
                    if (found != null)
                    {
                        foundval = found.getVal();
                        dist = firstval - foundval;
                        m = new Move(found, dist, howManyGreensMarked);
                        colorMoves.add(m);
                    }
                }

                rolledval2 = green2.getVal();
                if (rolledval2 <= firstval && rolledval2 != rolledval1)
                {
                    found = sheet.getGreenEntry(rolledval2);
                    if (found != null)
                    {
                        foundval = found.getVal();
                        dist = firstval - foundval;
                        m = new Move(found, dist, howManyGreensMarked);
                        colorMoves.add(m);
                    }
                }
            }
        }
            
            
        if (!game.blueslocked)
        {
            first = sheet.findFirstBlue();
            if (first != null)
            {
                firstval = first.getVal();

                rolledval1 = blue1.getVal();
                if (rolledval1 <= firstval)
                {
                    found = sheet.getBlueEntry(rolledval1);
                    if (found != null)
                    {
                        foundval = found.getVal();
                        dist = firstval - foundval;
                        m = new Move(found, dist, howManyBluesMarked);
                        colorMoves.add(m);
                    }
                }

                rolledval2 = blue2.getVal();
                if (rolledval2 <= firstval && rolledval2 != rolledval1)
                {
                    found = sheet.getBlueEntry(rolledval2);
                    if (found != null)
                    {
                        foundval = found.getVal();
                        dist = firstval - foundval;
                        m = new Move(found, dist, howManyBluesMarked);
                        colorMoves.add(m);
                    }
                }
            }
        }
    }
    
    

    // Human

    public SheetEntry chooseColorHuman()
    {
        SheetEntry thisOne = null;
        
        thisOne = chooseHumanMove(Move.MOVETYPE.COLORS);
        
        return thisOne;
    }

    public SheetEntry chooseWhiteHuman()
    {
        SheetEntry thisOne = null;
        
        thisOne = chooseHumanMove(Move.MOVETYPE.WHITES);
        
        return thisOne;
    }
    
    public SheetEntry chooseHumanMove(Move.MOVETYPE type)
    {
        SheetEntry thisOne = null;
        
        ArrayList<Move> theseMoves = null;
        
        if (type == Move.MOVETYPE.COLORS)
        {
            theseMoves = colorMoves;
        }
        else
        {
            theseMoves = whiteMoves;
        }

        if (theseMoves == null || theseMoves.size() == 0)
        {
            return thisOne;
        }
        
        sheet.printBetter(theseMoves);

        
        for (Player p1 : game.players)
        {
            if (!name.equals(p1.name))
            {
                p1.printSummary();
            }
        }
        System.out.println("");
        
        String tmp = printPossibleMoves(type);
        System.out.println(tmp);

        var playerDetails = new PlayerDetails(this);
        playerDetails.buildAndShow();

        playerDetails.dispose();

        
        thisOne = getHumanMove(type);
        
        return thisOne;
    }

    public String printPossibleMoves(Move.MOVETYPE type)
    {
        StringBuffer sb = new StringBuffer();
        if (type == Move.MOVETYPE.COLORS)
        {
            sb.append(name + "'s possible " + "COLOR" + " moves: ");
            for (Move m : colorMoves)
            {
                sb.append(m.se.toString() + ",  ");
            }
        }
        if (type == Move.MOVETYPE.WHITES)
        {
            sb.append(name + "'s possible " + "WHITE" + " moves: ");
            for (Move m : whiteMoves)
            {
                sb.append(m.se.toString() + ",  ");
            }
        }
        return sb.toString();
    }

    public SheetEntry getHumanMove(Move.MOVETYPE type)
    {
        
        

        SheetEntry thisOne = null;
        
        String typeStr = null;
        if (type == Move.MOVETYPE.COLORS)
        {
            typeStr = "COLOR";
        }
        else
        {
            typeStr = "WHITE";
        }
        
        try {
            
            Game.COLORS color;
            Integer value;
            
            System.out.println(name + ": select a " + typeStr + " move:");
            BufferedReader br = null;
            br = new BufferedReader(new InputStreamReader(System.in));
            
            boolean done = false;
            while (!done)
            {
                String inStr = br.readLine();

                if (inStr == null || inStr.length() == 0)
                {
                    thisOne = null;
                    done = true;
                    continue;
                }

                if (inStr.toLowerCase().equals("done"))
                {
                    thisOne = null;
                    done = true;
                    continue;
                }

                if (inStr.toLowerCase().equals("none"))
                {
                    thisOne = null;
                    done = true;
                    continue;
                }
                
                String[] vals = inStr.split(" ");

                if (vals == null || vals.length != 2 || vals[0] == null || vals[0].length() == 0 || vals[1] == null || vals[1].length() == 0)
                {
                    System.out.println("Try again");
                    continue;
                }
                
                if (vals[0].toLowerCase().equals("r") || vals[0].toLowerCase().equals("red") )
                {
                    color = Game.COLORS.RED;
                }
                else if (vals[0].toLowerCase().equals("y") || vals[0].toLowerCase().equals("yellow") )
                {
                    color = Game.COLORS.YELLOW;
                } 
                else if (vals[0].toLowerCase().equals("g") || vals[0].toLowerCase().equals("green") )
                {
                    color = Game.COLORS.GREEN;
                } 
                else if (vals[0].toLowerCase().equals("b") || vals[0].toLowerCase().equals("blue") )
                {
                    color = Game.COLORS.BLUE;
                } 
                else
                {
                    System.out.println("Try again");
                    continue;
                }

                try
                {
                    value = Integer.parseInt(vals[1]);
                }
                catch (Exception e)
                {
                    System.out.println("Try again");
                    continue;
                }
                
                String cn = "ERROR";
                if (color == Game.COLORS.RED) cn = "RED";
                if (color == Game.COLORS.YELLOW) cn = "YELLOW";
                if (color == Game.COLORS.GREEN) cn = "GREEN";
                if (color == Game.COLORS.BLUE) cn = "BLUE";
                
                // check to see if selected move is in the list of moves
                boolean found = false;
                if (type == Move.MOVETYPE.COLORS)
                {
                    for (Move m : colorMoves)
                    {
                        if (m.se.color == color && m.se.val == value)
                        {
                            found = true;
                        }
                    }
                }
                if (type == Move.MOVETYPE.WHITES)
                {
                    for (Move m : whiteMoves)
                    {
                        if (m.se.color == color && m.se.val == value)
                        {
                            found = true;
                        }
                    }
                }
                
                if (found == false)
                {
                    System.out.println("CAN'T SELECT: " + cn + " " + value.toString());
                    continue;
                }
                

                // check to see if selected move is in the list of moves
                
                SheetEntry tmp = null;
                if (color == Game.COLORS.RED)
                {
                     tmp = sheet.getRedEntry(value);
                     if (tmp != null && tmp.marked == false)
                     {
                        // System.out.println("SELECTED: " + cn + " " + value.toString());
                        thisOne = tmp;
                        done = true;
                     }
                     else
                     {
                        System.out.println("CAN'T SELECT: " + cn + " " + value.toString());
                        continue;
                     }
                }

                if (color == Game.COLORS.YELLOW)
                {
                     tmp = sheet.getYellowEntry(value);
                     if (tmp != null && tmp.marked == false)
                     {
                        // System.out.println("SELECTED: " + cn + " " + value.toString());
                        thisOne = tmp;
                        done = true;
                     }
                     else
                     {
                        System.out.println("CAN'T SELECT: " + cn + " " + value.toString());
                        continue;
                     }
                }

                if (color == Game.COLORS.GREEN)
                {
                     tmp = sheet.getGreenEntry(value);
                     if (tmp != null && tmp.marked == false)
                     {
                        // System.out.println("SELECTED: " + cn + " " + value.toString());
                        thisOne = tmp;
                        done = true;
                     }
                     else
                     {
                        System.out.println("CAN'T SELECT: " + cn + " " + value.toString());
                        continue;
                     }
                }
                
                if (color == Game.COLORS.BLUE)
                {
                     tmp = sheet.getBlueEntry(value);
                     if (tmp != null && tmp.marked == false)
                     {
                        // System.out.println("SELECTED: " + cn + " " + value.toString());
                        thisOne = tmp;
                        done = true;
                     }
                     else
                     {
                        System.out.println("CAN'T SELECT: " + cn + " " + value.toString());
                        continue;
                     }
                }
            }
        }
        catch (IOException e)
        {
            System.err.println(e.toString());
            thisOne = null;
        }
        
        return thisOne;
    }
    

    // DISTGREEDYEXTREME

    public SheetEntry chooseComputer(Move.MOVETYPE type)
    {

        int maxwval = 0;
        int thiswval = 0;
        
        SheetEntry thisOne = null;
        
        ArrayList<Move> theseMoves = null;
        
        if (type == Move.MOVETYPE.WHITES)
        {
            theseMoves = whiteMoves;
        }
        else
        {
            theseMoves = colorMoves;
        }
        
        if (theseMoves == null || theseMoves.size() == 0)
        {
            return thisOne;
        }
        
        ArrayList<Move> tmp = new ArrayList<Move> ();
        
        for (Move m : theseMoves)
        {
            
            // weight = 0 : 100, 100 is better
            int distval;
            int markedval;
            int extremeval;
            
            if (m.distance == 0)
            {
                distval = 3;
            }
            else if (m.distance == 1)
            {
                distval = 2;
            }
            else if (m.distance == 2)
            {
                distval = 1;
            }
            else
            {
                distval = 0;
            }
            
            markedval = m.howManyMarked;
            
            if (m.se.val == 2 || m.se.val == 12)
            {
                extremeval = 6;
            }
            else if (m.se.val == 3 || m.se.val == 11)
            {
                extremeval = 5;
            }
            else if (m.se.val == 4 || m.se.val == 10)
            {
                extremeval = 4;
            }
            else if (m.se.val == 5 || m.se.val == 9)
            {
                extremeval = 3;
            }
            else if (m.se.val == 6 || m.se.val == 8)
            {
                extremeval = 2;
            }
            else
            {
                extremeval = 1;
            }
            
            if (distval != 0)
            {
                int tmpval = 0;
                
                int dw = 1;
                int ew = 10;
                int mw = 1;
                
                tmpval = dw * (distval * 33);   // each point 100 / 3 
                thiswval = thiswval + tmpval;
                tmpval = ew * (tmpval + extremeval * 16);   // each point 100 / 6 
                thiswval = thiswval + tmpval;
                tmpval = mw * (tmpval + markedval * 9);   // each point 100 / 11 
                thiswval = thiswval + tmpval;
                
                thiswval = (thiswval / (dw + ew + mw));
            
            }
            else
            {
                thiswval = 0;
            }
            
            if (thiswval > 0)
            {
                if (thiswval > maxwval)
                {
                    tmp.clear();
                    maxwval = thiswval;
                    tmp.add(m);
                }
                else if (thiswval == maxwval)
                {
                    tmp.add(m);
                }
                else
                {
                    // 
                }
            }
        }
        
        Random rand = new Random();
        int index = 0;
        
        if (tmp.size() != 0)
        {
            index = rand.nextInt(tmp.size());
            thisOne = tmp.get(index).se;
            return thisOne;
        }

        return thisOne;
    }

    
    public SheetEntry chooseColorComputer()
    {
        SheetEntry thisOne = null;
        
        thisOne = chooseComputer(Move.MOVETYPE.COLORS);

        return thisOne;
    }
    
    public SheetEntry chooseWhiteComputer()
    {
        SheetEntry thisOne = null;
        
        thisOne = chooseComputer(Move.MOVETYPE.WHITES);

        return thisOne;
    }

    
    
}
    
*/

