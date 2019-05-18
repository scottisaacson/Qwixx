package qwixx;

import java.util.*;


public class Die {
    
    public static final int MINVAL = 1;
    public static final int MAXVAL = 6;
    
    int val;
    Random rand;
    
    public Die()
    {
        this.rand = new Random();
        val = rand.nextInt(MAXVAL) + 1;
    }
    
    public void roll()
    {
        val = this.rand.nextInt(MAXVAL) + 1;
        // System.out.println("new die val = " + val);
    }
    
    public String toString()
    {
        String ret = Integer.toString(val);
        return ret;
    }
    
}

