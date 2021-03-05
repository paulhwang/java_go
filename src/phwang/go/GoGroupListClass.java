/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.go;

import phwang.utils.*;

public class GoGroupListClass {
    private String objectName() {return "GoGroupListClass";}

    private static final int GO_GROUP_LIST_CLASS_GROUP_ARRAY_SIZE = 400;

    private GoFight theFightObject;
    private int indexNumber;
    private int myColor;
    int theHisColor;
    private Boolean isDead;
    private String bigStoneColor;
    private String smallStoneColor;
    private int isMarkedDead;
    private int groupCount;
    private GoGroupClass[] groupArray;

    public GoFight FightObject() { return this.theFightObject; }
    public GoRoot RootObject() { return this.theFightObject.RootObject(); }
    public GoBoard BoardObject() { return RootObject().BoardObject(); }
    public GoConfig ConfigObject() { return RootObject().ConfigObject(); }
    public int MyColor() { return this.myColor; }
    public int GroupCount() { return this.groupCount; }
    public GoGroupClass GroupArray(int index_val) { return this.groupArray[index_val]; }

    public GoGroupListClass(GoFight fight_object_val,
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

    public int totalStoneCount() {
        int count = 0;
        for (int i = 0; i < this.groupCount; i++) {
            count += this.groupArray[i].StoneCount();
        }
        return count;
    }

    public void insertGroupToGroupList(GoGroupClass group_val) {
        this.groupArray[this.groupCount] = group_val;
        group_val.SetIndexNumber(this.groupCount);
        this.groupCount++;
        group_val.SetGroupListObject(this);
    }

    public GoGroupClass findCandidateGroup(int x_val, int y_val) {
        int i = 0;
        while (i < this.groupCount) {
            if (this.groupArray[i].isCandidateGroup(x_val, y_val)) {
                return this.groupArray[i];
            }
            i += 1;
        }
        return null;
    }

    public GoGroupClass findOtherCandidateGroup(GoGroupClass group_val, int x_val, int y_val) {
        int i = 0;
        while (i < this.groupCount) {
            if (this.groupArray[i] != group_val) {
                if (this.groupArray[i].isCandidateGroup(x_val, y_val)) {
                    return this.groupArray[i];
                }
            }
            i += 1;
        }
        return null;
    }

    public void removeGroupFromGroupList(GoGroupClass group_val) {
        this.groupCount--;
        if (group_val.IndexNumber() != this.groupCount) {
            this.groupArray[this.groupCount].SetIndexNumber(group_val.IndexNumber());
            this.groupArray[group_val.IndexNumber()] = this.groupArray[this.groupCount];
        }
        this.groupArray[this.groupCount] = null;
    }

    public Boolean stoneExistWithinMe(int x_val, int y_val) {
        int i = 0;
        while (i < this.groupCount) {
            GoGroupClass group = this.groupArray[i];
            if (group.ExistMatrix(x_val, y_val)) {
                return true;
            }
            i += 1;
        }
        return false;
    }

    public void abendGroupList() {
        int i = 0;
        while (i < this.groupCount) {
            GoGroupClass group = this.groupArray[i];
            if (group == null) {
                this.abend("abendGroupList", "null group");
                return;
            }
            if (group.GroupListObject() != this) {
                this.abend("abendGroupList", "groupListObject");
                return;
            }
            if (group.IndexNumber() != i) {
                this.abend("abendGroupList", "index ");
                return;
            }

            group.abendGroup();

            int j = i + 1;
            while (j < this.groupCount) {
                group.abendOnGroupConflict(this.groupArray[j]);
                j = j + 1;
            }
            i += 1;
        }
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
