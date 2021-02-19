/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Engine.Go;

import Phwang.Utils.AbendClass;

public class GoBoardClass {
    private String objectName() {return "GoBoardClass";}
    
    //private int[,] theBoardArray;
    //private int[,] theMarkedBoardArray;
    private String theBoardOutputBuffer;
    int theBlackCapturedStones;
    int theWhiteCapturedStones;
    int theLastDeadX;
    int theLastDeadY;
    private GoRootClass theRootObject;

    public GoConfigClass ConfigObject() { return this.theRootObject.ConfigObject(); }
    public GoGameClass GameObject() { return this.theRootObject.GameObject(); }
    public String BoardOutputBuffer() { return this.theBoardOutputBuffer; }
    //public int BoardArray(int x_val, int y_val) { return this.theBoardArray[x_val, y_val]; }
    public void AddBlackCapturedStones(int val) { this.theBlackCapturedStones += val; }
    public void AddWhiteCapturedStones(int val) { this.theWhiteCapturedStones += val; }
    //public void SetBoardArray(int x_val, int y_val, int data_val) { this.theBoardArray[x_val, y_val] = data_val; }
    public void SetLastDeadStone(int x_val, int y_val) { this.theLastDeadX = x_val; this.theLastDeadY = y_val; }

    public GoBoardClass(GoRootClass root_object_val)
    {
        this.theRootObject = root_object_val;
        //this.theBoardArray = new int[GoDefineClass.MAX_BOARD_SIZE, GoDefineClass.MAX_BOARD_SIZE];
        //this.theMarkedBoardArray = new int[GoDefineClass.MAX_BOARD_SIZE, GoDefineClass.MAX_BOARD_SIZE];
        //this.ResetBoardObjectData();
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
