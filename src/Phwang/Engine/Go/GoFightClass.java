/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Engine.Go;

import Phwang.Utils.AbendClass;

public class GoFightClass {
    private String objectName() {return "GoFightClass";}

    private static final int GO_FIGHT_CLASS_GROUP_LIST_ARRAY_SIZE = 7;

    private GoRootClass theRootObject;
    private Boolean abendEngineOn;
    GoGroupListClass[] theGroupListArray;
    String theCaptureCount;
    String theLastDeadStone;

    public GoRootClass RootObject() { return this.theRootObject; }
    public GoBoardClass BoardObject() { return this.theRootObject.BoardObject(); }
    public GoConfigClass ConfigObject() { return this.theRootObject.ConfigObject(); }

    GoGroupListClass emptyGroupList() { return this.theGroupListArray[0]; }
    GoGroupListClass blackGroupList() { return this.theGroupListArray[1]; }
    GoGroupListClass whiteGroupList() { return this.theGroupListArray[2]; }
    GoGroupListClass blackDeadGroupList() { return this.theGroupListArray[3]; }
    GoGroupListClass whiteDeadGroupList() { return this.theGroupListArray[4]; }
    GoGroupListClass blackEmptyGroupList() { return this.theGroupListArray[5]; }
    GoGroupListClass whiteEmptyGroupList() { return this.theGroupListArray[6]; }

    public GoFightClass(GoRootClass go_root_object_val)
    {
        this.theRootObject = go_root_object_val;
        this.theGroupListArray = new GoGroupListClass[GO_FIGHT_CLASS_GROUP_LIST_ARRAY_SIZE];
        this.resetEngineObjectData();
    }

    private void resetEngineObjectData()
    {
        //this.BoardObject().ResetBoardObjectData();

        //this.theGroupListArray[1] = new GoGroupListClass(this, 1, GoDefineClass.GO_BLACK_STONE, false, null, null);
        //this.theGroupListArray[2] = new GoGroupListClass(this, 2, GoDefineClass.GO_WHITE_STONE, false, null, null);
        this.resetMarkedGroupLists();
        this.resetEmptyGroupLists();

        this.theCaptureCount = null;
        this.theLastDeadStone = null;
    }
    private void resetMarkedGroupLists()
    {
        //this.theGroupListArray[3] = new GoGroupListClass(this, 3, GoDefineClass.GO_BLACK_STONE, true, "black", "gray");
        //this.theGroupListArray[4] = new GoGroupListClass(this, 4, GoDefineClass.GO_WHITE_STONE, true, "white", "gray");
        //this.BoardObject().ResetMarkedBoardObjectData();
    }

    private void resetEmptyGroupLists()
    {
        //this.theGroupListArray[0] = new GoGroupListClass(this, 0, GoDefineClass.GO_EMPTY_STONE, false, null, null);
        //this.theGroupListArray[5] = new GoGroupListClass(this, 5, GoDefineClass.GO_EMPTY_STONE, false, null, "black");
        //this.theGroupListArray[6] = new GoGroupListClass(this, 6, GoDefineClass.GO_EMPTY_STONE, false, null, "white");
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
