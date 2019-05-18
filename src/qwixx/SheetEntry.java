package qwixx;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.net.*;
import java.text.*;


public class SheetEntry {

    public static final int MINVAL = 2;
    public static final int MAXVAL = 12;
    
    
    Sheet sheet;
    Game.COLORS color;
    boolean marked;
    int val;
    
    public SheetEntry(Sheet sheet, Game.COLORS color, boolean marked, int val)
    {
        this.sheet = sheet;
        this.color = color;
        this.marked = marked;
        this.val = val;
    }

    public void markIt()
    {
        marked = true;
    }
    
    public boolean isMarked()
    {
        return marked;
    }
    
    public int getVal()
    {
        return val;
    }
    
    public Game.COLORS getColor()
    {
        return color;
    }
    
    public String toString () 
    {
    
        String ret;
        
        StringBuffer sb = new StringBuffer();
        
        switch (color)
        {
            case RED:
                sb.append("RED");
                break;
            case YELLOW:
                sb.append("YELLOW");
                break;
            case GREEN:
                sb.append("GREEN");
                break;
            case BLUE:
                sb.append("BLUE");
                break;
            default:
                sb.append("ERROR");
                break;
        }
        
        sb.append(" " + val);
   
        ret = sb.toString();
        return ret;
    }

    public void print() 
    {
    
        String ret;
        
        StringBuffer sb = new StringBuffer();
        
        switch (color)
        {
            case RED:
                sb.append("RED");
                break;
            case YELLOW:
                sb.append("YELLOW");
                break;
            case GREEN:
                sb.append("GREEN");
                break;
            case BLUE:
                sb.append("BLUE");
                break;
            default:
                sb.append("ERROR");
                break;
        }
        
        sb.append(" " + val);
        if (marked == true)
        {
           sb.append("X");
        }
   
        ret = sb.toString();
        System.out.println(ret);
        
    }

    
    public boolean equals(SheetEntry other)
    {
        if (val == other.val && color == other.color)
        {
            return true;
        }
        
        return false;
        
    }
}

