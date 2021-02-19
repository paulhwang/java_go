/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Engine.Go;

import Phwang.Utils.AbendClass;

public class GoConfigClass {
    private String objectName() {return "GoConfigClass";}

    private GoRootClass theRootObject;
    private int boardSize;
    private int handicapPoint;
    private int komiPoint;

    public int BoardSize() { return this.boardSize; }
    public int HandicapPoint() { return this.handicapPoint; }
    public int KomiPoint() { return this.komiPoint; }

    public GoConfigClass(GoRootClass root_object_val)
    {
        this.theRootObject = root_object_val;
    }


    private void debugIt(Boolean on_off_val, String str0_val, String str1_val)
    {
        if (on_off_val)
            this.logitIt(str0_val, str1_val);
    }

    private void logitIt(String str0_val, String str1_val)
    {
        AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val);
    }

    private void abendIt(String str0_val, String str1_val)
    {
        AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val);
    }
}
