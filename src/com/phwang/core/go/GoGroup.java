/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.go;

import com.phwang.core.utils.Abend;

public class GoGroup {
    private String objectName() {return "GoGroup";}

    private GoGroupList goGroupList_;
    private int maxX_;
    private int minX_;
    private int maxY_;
    private int minY_;
    private int indexNumber_;
    private int stoneCount_;
    private int myColor_;
    private int hisColor_;
    private Boolean[][] existMatrix_;
    private Boolean[][] deadMatrix;

    public GoGroupList goGroupList() { return this.goGroupList_; }
    public GoConfig goConfig() { return this.goGroupList().goConfig(); }
    public int hisColor() { return this.hisColor_; }
    public int myColor() { return this.myColor_; }
    public int stoneCount() { return this.stoneCount_; }
    public int indexNumber() { return this.indexNumber_; }
    public Boolean existMatrix(int x_val, int y_val) { return this.existMatrix_[x_val][y_val]; }
    public void setIndexNumber(int val) { this.indexNumber_ = val; }
    public void setGroupListObject(GoGroupList group_list_val) { this.goGroupList_ = group_list_val; }

    public GoGroup(GoGroupList group_list_val) {
        this.goGroupList_ = group_list_val;
        this.indexNumber_ = this.goGroupList_.groupCount();
        this.myColor_ = this.goGroupList_.myColor();
        this.stoneCount_ = 0;

        this.existMatrix_ = new Boolean[GoDefine.MAX_BOARD_SIZE] [GoDefine.MAX_BOARD_SIZE];
        this.deadMatrix = new Boolean[GoDefine.MAX_BOARD_SIZE] [GoDefine.MAX_BOARD_SIZE];
        for (int i = 0; i < GoDefine.MAX_BOARD_SIZE; i++) {
        	for (int j = 0; j < GoDefine.MAX_BOARD_SIZE; j++) {
        		this.existMatrix_[i][j] = false;
        		this.deadMatrix[i][j] = false;
        	}
        }
        
        this.hisColor_ = (this.myColor_ == GoDefine.GO_EMPTY_STONE)
            ? GoDefine.GO_EMPTY_STONE
            : GoDefine.getOppositeColor(this.myColor_);
    }

    public void insertStoneToGroup(int x_val, int y_val, Boolean dead_val) {
        if (this.existMatrix_[x_val][y_val]) {
            this.abend("insertStoneToGroup", "stone already exists in group");
        }

        if (this.stoneCount_ == 0) {
            this.maxX_ = x_val;
            this.minX_ = x_val;
            this.maxY_ = y_val;
            this.minY_ = y_val;
        }
        else {
            if (x_val > this.maxX_) {
                this.maxX_ = x_val;
            }
            if (x_val < this.minX_) {
                this.minX_ = x_val;
            }
            if (y_val > this.maxY_) {
                this.maxY_ = y_val;
            }
            if (y_val < this.minY_) {
                this.minY_ = y_val;
            }
        }

        this.stoneCount_++;
        this.existMatrix_[x_val] [y_val] = true;
        this.deadMatrix[x_val] [y_val] = dead_val;
    }

    public Boolean isCandidateGroup(int x_val, int y_val) {
        int i = this.minX_;
        while (i <= this.maxX_)
        {
            int j = this.minY_;
            while (j <= this.maxY_)
            {
                if (this.existMatrix_[i][j])
                {
                    //this.debug(false, "isCandidateGroup", "(" + x_val + "," + y_val + ") (" + i + "," + j + ")");
                    if (this.isNeighborStone(i, j, x_val, y_val))
                    {
                        return true;
                    }
                }
                j += 1;
            }
            i += 1;
        }
        return false;
    }
    
    private Boolean isNeighborStone(int x1_val, int y1_val, int x2_val, int y2_val) {
        if (x1_val == x2_val) {
            if ((y1_val + 1 == y2_val) || (y1_val - 1 == y2_val)) {
                return true;
            }
        }
        if (y1_val == y2_val) {
            if ((x1_val + 1 == x2_val) || (x1_val - 1 == x2_val)) {
                return true;
            }
        }
        return false;
    }

    public void mergeWithOtherGroup(GoGroup group2) {
        this.debug(false, "mergeWithOtherGroup", "");
        int i = group2.minX_;
        while (i <= group2.maxX_) {
            int j = group2.minY_;
            while (j <= group2.maxY_) {
                if (group2.existMatrix_[i][j]) {
                    //this.debug(false, "mergeWithOtherGroup", "i=" + i + " j=" + j);
                    if (this.existMatrix_[i][j]) {
                        this.abend("mergeWithOtherGroup", "already exist");
                    }
                    this.existMatrix_[i][j] = group2.existMatrix_[i][j];
                    this.stoneCount_++;

                    group2.existMatrix_[i][j] = false;
                    group2.stoneCount_--;
                }
                j += 1;
            }
            i += 1;
        }
        if (group2.stoneCount_ != 0) {
            this.abend("mergeWithOtherGroup", "theStoneCount");
        }

        if (this.maxX_ < group2.maxX_) {
            this.maxX_ = group2.maxX_;
        }
        if (this.minX_ > group2.minX_) {
            this.minX_ = group2.minX_;
        }
        if (this.maxY_ < group2.maxY_) {
            this.maxY_ = group2.maxY_;
        }
        if (this.minY_ > group2.minY_) {
            this.minY_ = group2.minY_;
        }

        if (group2.goGroupList_.groupArray(group2.indexNumber_) != group2) {
            this.abend("mergeWithOtherGroup", "group2");
        }
    }

    public Boolean groupHasAir() {
        int i = this.minX_;
        while (i <= this.maxX_) {
            int j = this.minY_;
            while (j <= this.maxY_) {
                if (this.existMatrix_[i][j]) {
                    if (this.goGroupList_.goFight().goRoot().goBoard().stoneHasAir(i, j)) {
                        return true;
                    }
                }
                j += 1;
            }
            i += 1;
        }
        return false;
    }

    public void removeDeadStoneFromBoard() {
        int i = this.minX_;
        while (i <= this.maxX_) {
            int j = this.minY_;
            while (j <= this.maxY_) {
                if (this.existMatrix_[i][j]) {
                    this.goGroupList_.goFight().goBoard().setBoardArray(i, j, GoDefine.GO_EMPTY_STONE);
                    //this.debug(false, "removeDeadStoneFromBoard", "(" + i + "," + j + ")");
                }
                j += 1;
            }
            i += 1;
        }
    }

    public void markLastDeadInfo() {
        this.goGroupList_.goBoard().setLastDeadStone(this.maxX_, this.maxY_);

        if (this.maxX_ != this.minX_) {
            this.abend("MarkLastDeadInfo", "bad x");
        }
        if (this.maxY_ != this.minY_) {
            this.abend("MarkLastDeadInfo", "bad y");
        }
        if (!this.existMatrix_[this.maxX_][this.maxY_]) {
            this.abend("MarkLastDeadInfo", "exist_matrix");
        }
    }

    public void abendGroup() {
        int count = 0;
        int board_size = this.goConfig().boardSize();
        for (int i = 0; i < board_size; i++) {
            for (int j = 0; j < board_size; j++) {
                if (this.existMatrix_[i][j]) {
                    count++;
                }
            }
        }
        if (this.stoneCount_ != count) {
            this.abend("AbendGroup", "stone count");
        }
    }

    public void abendOnGroupConflict(GoGroup other_group_val) {
        int board_size = this.goConfig().boardSize();
        for (int i = 0; i < board_size; i++) {
            for (int j = 0; j < board_size; j++) {
                if (this.existMatrix_[i][j]) {
                    if (other_group_val.existMatrix_[i][j]) {
                        this.abend("AbendOnGroupConflict", "stone  exists in 2 groups");
                        //this->abend("abendOnGroupConflict", "stone (" + i + "," + j + ") exists in 2 groups: (" + this.myColor() + ":" + this.indexNumber() + ":" + this.stoneCount() + ") ("
                        //    + other_group_val.myColor() + ":" + other_group_val.indexNumber() + ":" + other_group_val.stoneCount() + ")");
                    }
                }
            }
        }
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
