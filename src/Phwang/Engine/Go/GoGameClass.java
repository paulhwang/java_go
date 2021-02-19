/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Engine.Go;

import Phwang.Utils.AbendClass;

public class GoGameClass {
    private String objectName() {return "GoGameClass";}
    private static final int GO_GAME_CLASS_MAX_MOVES_ARRAY_SIZE = 1024;

    private GoRootClass theRootObject;
    private int theTotalMoves;
    private int theMaxMove;
    private int theNextColor;
    private Boolean thePassReceived;
    private Boolean theGameIsOver;
    private GoMoveClass[] theMovesArray;

    public GoConfigClass ConfigObject() { return this.theRootObject.ConfigObject();  }
    public GoBoardClass BoardObject() { return this.theRootObject.BoardObject(); }
    public GoFightClass FightObject() { return this.theRootObject.FightObject(); }
    public int TotalMoves() { return this.theTotalMoves; }
    public int NextColor() { return this.theNextColor; }

    public GoGameClass(GoRootClass go_root_object_val)
    {
        this.theRootObject = go_root_object_val;
        this.theMovesArray = new GoMoveClass[GO_GAME_CLASS_MAX_MOVES_ARRAY_SIZE];
    }

    private void resetGameObjectPartialData()
    {
        this.theNextColor = GoDefineClass.GO_BLACK_STONE;
        this.thePassReceived = false;
        this.theGameIsOver = false;
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
