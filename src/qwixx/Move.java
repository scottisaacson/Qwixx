package qwixx;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.net.*;
import java.text.*;


public class Move {
    
    // don't add BOTH yet, will do that later
    // maybe nextweek
    enum MOVETYPE {
        COLORS,
        WHITES
    };
    
    MOVETYPE type;
    SheetEntry se;
    int distance;
    int howManyMarked;
    
    public Move (SheetEntry se, int distance, int howManyMarked)
    {
        this.se = se;
        this.distance = distance;
        this.howManyMarked = howManyMarked;
    }
    
}

