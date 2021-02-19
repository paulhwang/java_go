/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Engine.Go;

import Phwang.Utils.AbendClass;

public class GoGroupListClass {
    private String objectName() {return "GoGroupListClass";}

    private static final int GO_GROUP_LIST_CLASS_GROUP_ARRAY_SIZE = 400;

    private GoFightClass theFightObject;
    private int indexNumber;
    private int myColor;
    int theHisColor;
    private Boolean isDead;
    private String bigStoneColor;
    private String smallStoneColor;
    private int isMarkedDead;
    private int groupCount;
    private GoGroupClass[] groupArray;

    public GoFightClass FightObject() { return this.theFightObject; }
    public GoRootClass RootObject() { return this.theFightObject.RootObject(); }
    public GoBoardClass BoardObject() { return RootObject().BoardObject(); }
    public GoConfigClass ConfigObject() { return RootObject().ConfigObject(); }
    public int MyColor() { return this.myColor; }
    public int GroupCount() { return this.groupCount; }
    public GoGroupClass GroupArray(int index_val) { return this.groupArray[index_val]; }

    public GoGroupListClass(GoFightClass fight_object_val,
                   int index_val,
                   int color_val,
                   Boolean is_dead_val,
                   String big_stone_val,
                   String small_stone_val)
    {
        this.theFightObject = fight_object_val;
        this.groupArray = new GoGroupClass[GO_GROUP_LIST_CLASS_GROUP_ARRAY_SIZE];
        this.indexNumber = index_val;
        this.myColor = color_val;
        this.isDead = is_dead_val;
        this.bigStoneColor = big_stone_val;
        this.smallStoneColor = small_stone_val;
        this.groupCount = 0;
        this.isMarkedDead = 0;
    }

    public int TotalStoneCount()
    {
        int count = 0;
        for (int i = 0; i < this.groupCount; i++)
        {
            //count += this.groupArray[i].StoneCount();
        }
        return count;
    }

    public void InsertGroupToGroupList(GoGroupClass group_val)
    {
        this.groupArray[this.groupCount] = group_val;
        //group_val.SetIndexNumber(this.groupCount);
        this.groupCount++;
        //group_val.SetGroupListObject(this);
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
