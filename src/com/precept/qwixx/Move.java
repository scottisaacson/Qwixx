package com.precept.qwixx;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.net.*;
import java.text.*;


public class Move {
    
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

