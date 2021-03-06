/****************************************************************************
 * 2019 The Precept Group, LLC. 
 * See LICENSE for license information.
 ***************************************************************************/

package com.precept.qwixx;


public class Sum {

    enum COLOR {
        RED,
        YELLOW,
        GREEN,
        BLUE,
        WHITE
    };
    
    COLOR color;
    Integer val;
    
    public Sum(Sum.COLOR color, Integer val)
    {
        this.color = color;
        this.val = val;
    }
    
    public int getVal()
    {
        return val;
    }
}

