/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.go;

import com.phwang.core.utils.*;

public class GoGroupList {
    private String objectName() {return "GoGroupList";}

    private static final int GO_GROUP_LIST_CLASS_GROUP_ARRAY_SIZE = 400;

    private GoFight goFight_;
    private int indexNumber;
    private int myColor_;
    private int hisColor_;
    private Boolean isDead;
    private String bigStoneColor;
    private String smallStoneColor;
    private int isMarkedDead;
    private int groupCount_;
    private GoGroup[] groupArray_;

    public GoFight goFight() { return this.goFight_; }
    public GoRoot goRoot() { return this.goFight_.goRoot(); }
    public GoBoard goBoard() { return goRoot().goBoard(); }
    public GoConfig goConfig() { return goRoot().goConfig(); }
    public int myColor() { return this.myColor_; }
    public int groupCount() { return this.groupCount_; }
    public GoGroup groupArray(int index_val) { return this.groupArray_[index_val]; }

    public GoGroupList(GoFight fight_val,
                   int index_val,
                   int color_val,
                   Boolean is_dead_val,
                   String big_stone_val,
                   String small_stone_val)
    {
        this.goFight_ = fight_val;
        this.groupArray_ = new GoGroup[GO_GROUP_LIST_CLASS_GROUP_ARRAY_SIZE];
        this.indexNumber = index_val;
        this.myColor_ = color_val;
        this.isDead = is_dead_val;
        this.bigStoneColor = big_stone_val;
        this.smallStoneColor = small_stone_val;
        this.groupCount_ = 0;
        this.isMarkedDead = 0;
    }

    public int totalStoneCount() {
        int count = 0;
        for (int i = 0; i < this.groupCount_; i++) {
            count += this.groupArray_[i].stoneCount();
        }
        return count;
    }

    public void insertGroupToGroupList(GoGroup group_val) {
        this.groupArray_[this.groupCount_] = group_val;
        group_val.setIndexNumber(this.groupCount_);
        this.groupCount_++;
        group_val.setGroupListObject(this);
    }

    public GoGroup findCandidateGroup(int x_val, int y_val) {
        int i = 0;
        while (i < this.groupCount_) {
            if (this.groupArray_[i].isCandidateGroup(x_val, y_val)) {
                return this.groupArray_[i];
            }
            i += 1;
        }
        return null;
    }

    public GoGroup findOtherCandidateGroup(GoGroup group_val, int x_val, int y_val) {
        int i = 0;
        while (i < this.groupCount_) {
            if (this.groupArray_[i] != group_val) {
                if (this.groupArray_[i].isCandidateGroup(x_val, y_val)) {
                    return this.groupArray_[i];
                }
            }
            i += 1;
        }
        return null;
    }

    public void removeGroupFromGroupList(GoGroup group_val) {
        this.groupCount_--;
        if (group_val.indexNumber() != this.groupCount_) {
            this.groupArray_[this.groupCount_].setIndexNumber(group_val.indexNumber());
            this.groupArray_[group_val.indexNumber()] = this.groupArray_[this.groupCount_];
        }
        this.groupArray_[this.groupCount_] = null;
    }

    public Boolean stoneExistWithinMe(int x_val, int y_val) {
        int i = 0;
        while (i < this.groupCount_) {
        	GoGroup group = this.groupArray_[i];
            if (group.existMatrix(x_val, y_val)) {
                return true;
            }
            i += 1;
        }
        return false;
    }

    public void abendGroupList() {
        int i = 0;
        while (i < this.groupCount_) {
        	GoGroup group = this.groupArray_[i];
            if (group == null) {
                this.abend("abendGroupList", "null group");
                return;
            }
            if (group.goGroupList() != this) {
                this.abend("abendGroupList", "groupListObject");
                return;
            }
            if (group.indexNumber() != i) {
                this.abend("abendGroupList", "index ");
                return;
            }

            group.abendGroup();

            int j = i + 1;
            while (j < this.groupCount_) {
                group.abendOnGroupConflict(this.groupArray_[j]);
                j = j + 1;
            }
            i += 1;
        }
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
