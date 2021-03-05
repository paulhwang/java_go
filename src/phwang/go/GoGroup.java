/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.go;

import phwang.utils.*;

public class GoGroup {
    private String objectName() {return "GoGroup";}

    GoGroupList goGroupList_;
    private int maxX_;
    private int minX_;
    private int maxY_;
    private int minY_;
    private int indexNumber_;
    int stoneCount;
    private int myColor;
    int hisColor;
    private Boolean[][] existMatrix;
    private Boolean[][] deadMatrix;

    public GoGroupList goGroupList() { return this.goGroupList_; }
    public GoConfig goConfig() { return this.goGroupList().goConfig(); }
    public int HisColor() { return this.hisColor; }
    public int MyColor() { return this.myColor; }
    public int StoneCount() { return this.stoneCount; }
    public int IndexNumber() { return this.indexNumber_; }
    public Boolean ExistMatrix(int x_val, int y_val) { return this.existMatrix[x_val][y_val]; }
    public void SetIndexNumber(int val) { this.indexNumber_ = val; }
    public void SetGroupListObject(GoGroupList group_list_val) { this.goGroupList_ = group_list_val; }

    public GoGroup(GoGroupList group_list_val) {
        this.goGroupList_ = group_list_val;
        this.indexNumber_ = this.goGroupList_.GroupCount();
        this.myColor = this.goGroupList_.MyColor();
        this.stoneCount = 0;

        this.existMatrix = new Boolean[GoDefine.MAX_BOARD_SIZE] [GoDefine.MAX_BOARD_SIZE];
        this.deadMatrix = new Boolean[GoDefine.MAX_BOARD_SIZE] [GoDefine.MAX_BOARD_SIZE];
        for (int i = 0; i < GoDefine.MAX_BOARD_SIZE; i++) {
        	for (int j = 0; j < GoDefine.MAX_BOARD_SIZE; j++) {
        		this.existMatrix[i][j] = false;
        		this.deadMatrix[i][j] = false;
        	}
        }
        
        this.hisColor = (this.myColor == GoDefine.GO_EMPTY_STONE)
            ? GoDefine.GO_EMPTY_STONE
            : GoDefine.getOppositeColor(this.myColor);
    }

    public void insertStoneToGroup(int x_val, int y_val, Boolean dead_val) {
        if (this.existMatrix[x_val][y_val]) {
            this.abend("insertStoneToGroup", "stone already exists in group");
        }

        if (this.stoneCount == 0) {
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

        this.stoneCount++;
        this.existMatrix[x_val] [y_val] = true;
        this.deadMatrix[x_val] [y_val] = dead_val;
    }

    public Boolean isCandidateGroup(int x_val, int y_val) {
        int i = this.minX_;
        while (i <= this.maxX_)
        {
            int j = this.minY_;
            while (j <= this.maxY_)
            {
                if (this.existMatrix[i][j])
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
                if (group2.existMatrix[i][j]) {
                    //this.debug(false, "mergeWithOtherGroup", "i=" + i + " j=" + j);
                    if (this.existMatrix[i][j]) {
                        this.abend("mergeWithOtherGroup", "already exist");
                    }
                    this.existMatrix[i][j] = group2.existMatrix[i][j];
                    this.stoneCount++;

                    group2.existMatrix[i][j] = false;
                    group2.stoneCount--;
                }
                j += 1;
            }
            i += 1;
        }
        if (group2.stoneCount != 0) {
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

        if (group2.goGroupList_.GroupArray(group2.indexNumber_) != group2) {
            this.abend("mergeWithOtherGroup", "group2");
        }
    }

    public Boolean groupHasAir() {
        int i = this.minX_;
        while (i <= this.maxX_) {
            int j = this.minY_;
            while (j <= this.maxY_) {
                if (this.existMatrix[i][j]) {
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
                if (this.existMatrix[i][j]) {
                    this.goGroupList_.goFight().goBoard().SetBoardArray(i, j, GoDefine.GO_EMPTY_STONE);
                    //this.debug(false, "removeDeadStoneFromBoard", "(" + i + "," + j + ")");
                }
                j += 1;
            }
            i += 1;
        }
    }

    public void markLastDeadInfo() {
        this.goGroupList_.goBoard().SetLastDeadStone(this.maxX_, this.maxY_);

        if (this.maxX_ != this.minX_) {
            this.abend("MarkLastDeadInfo", "bad x");
        }
        if (this.maxY_ != this.minY_) {
            this.abend("MarkLastDeadInfo", "bad y");
        }
        if (!this.existMatrix[this.maxX_][this.maxY_]) {
            this.abend("MarkLastDeadInfo", "exist_matrix");
        }
    }

    public void abendGroup() {
        int count = 0;
        int board_size = this.goConfig().boardSize();
        for (int i = 0; i < board_size; i++) {
            for (int j = 0; j < board_size; j++) {
                if (this.existMatrix[i][j]) {
                    count++;
                }
            }
        }
        if (this.stoneCount != count) {
            this.abend("AbendGroup", "stone count");
        }
    }

    public void abendOnGroupConflict(GoGroup other_group_val) {
        int board_size = this.goConfig().boardSize();
        for (int i = 0; i < board_size; i++) {
            for (int j = 0; j < board_size; j++) {
                if (this.existMatrix[i][j]) {
                    if (other_group_val.existMatrix[i][j]) {
                        this.abend("AbendOnGroupConflict", "stone  exists in 2 groups");
                        //this->abend("abendOnGroupConflict", "stone (" + i + "," + j + ") exists in 2 groups: (" + this.myColor() + ":" + this.indexNumber() + ":" + this.stoneCount() + ") ("
                        //    + other_group_val.myColor() + ":" + other_group_val.indexNumber() + ":" + other_group_val.stoneCount() + ")");
                    }
                }
            }
        }
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
