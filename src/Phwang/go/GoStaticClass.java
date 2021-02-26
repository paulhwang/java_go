/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.go;

public class GoStaticClass {
    public static Boolean isNeighborStone(int x1_val, int y1_val, int x2_val, int y2_val)
    {
        if (x1_val == x2_val)
        {
            if ((y1_val + 1 == y2_val) || (y1_val - 1 == y2_val))
            {
                return true;
            }
        }
        if (y1_val == y2_val)
        {
            if ((x1_val + 1 == x2_val) || (x1_val - 1 == x2_val))
            {
                return true;
            }
        }
        return false;
    }
}
