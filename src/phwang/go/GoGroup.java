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

    GoGroupList theGroupListObject;
    private int maxX;
    private int minX;
    private int maxY;
    private int minY;
    private int indexNumber;
    int stoneCount;
    private int myColor;
    int hisColor;
    private Boolean[][] existMatrix;
    private Boolean[][] deadMatrix;

    public GoGroupList GroupListObject() { return this.theGroupListObject; }
    public GoConfig ConfigObject() { return this.theGroupListObject.ConfigObject(); }
    public int HisColor() { return this.hisColor; }
    public int MyColor() { return this.myColor; }
    public int StoneCount() { return this.stoneCount; }
    public int IndexNumber() { return this.indexNumber; }
    public Boolean ExistMatrix(int x_val, int y_val) { return this.existMatrix[x_val][y_val]; }
    public void SetIndexNumber(int val) { this.indexNumber = val; }
    public void SetGroupListObject(GoGroupList group_list_val) { this.theGroupListObject = group_list_val; }

    public GoGroup(GoGroupList group_list_object_val) {
        this.theGroupListObject = group_list_object_val;
        this.indexNumber = this.theGroupListObject.GroupCount();
        this.myColor = this.theGroupListObject.MyColor();
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
            this.maxX = x_val;
            this.minX = x_val;
            this.maxY = y_val;
            this.minY = y_val;
        }
        else {
            if (x_val > this.maxX) {
                this.maxX = x_val;
            }
            if (x_val < this.minX) {
                this.minX = x_val;
            }
            if (y_val > this.maxY) {
                this.maxY = y_val;
            }
            if (y_val < this.minY) {
                this.minY = y_val;
            }
        }

        this.stoneCount++;
        this.existMatrix[x_val] [y_val] = true;
        this.deadMatrix[x_val] [y_val] = dead_val;
    }

    public Boolean isCandidateGroup(int x_val, int y_val) {
        int i = this.minX;
        while (i <= this.maxX)
        {
            int j = this.minY;
            while (j <= this.maxY)
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
        int i = group2.minX;
        while (i <= group2.maxX) {
            int j = group2.minY;
            while (j <= group2.maxY) {
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

        if (this.maxX < group2.maxX) {
            this.maxX = group2.maxX;
        }
        if (this.minX > group2.minX) {
            this.minX = group2.minX;
        }
        if (this.maxY < group2.maxY) {
            this.maxY = group2.maxY;
        }
        if (this.minY > group2.minY) {
            this.minY = group2.minY;
        }

        if (group2.theGroupListObject.GroupArray(group2.indexNumber) != group2) {
            this.abend("mergeWithOtherGroup", "group2");
        }
    }

    public Boolean groupHasAir() {
        int i = this.minX;
        while (i <= this.maxX) {
            int j = this.minY;
            while (j <= this.maxY) {
                if (this.existMatrix[i][j]) {
                    if (this.theGroupListObject.FightObject().RootObject().goBoard().stoneHasAir(i, j)) {
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
        int i = this.minX;
        while (i <= this.maxX) {
            int j = this.minY;
            while (j <= this.maxY) {
                if (this.existMatrix[i][j]) {
                    this.theGroupListObject.FightObject().BoardObject().SetBoardArray(i, j, GoDefine.GO_EMPTY_STONE);
                    //this.debug(false, "removeDeadStoneFromBoard", "(" + i + "," + j + ")");
                }
                j += 1;
            }
            i += 1;
        }
    }

    public void markLastDeadInfo() {
        this.theGroupListObject.BoardObject().SetLastDeadStone(this.maxX, this.maxY);

        if (this.maxX != this.minX) {
            this.abend("MarkLastDeadInfo", "bad x");
        }
        if (this.maxY != this.minY) {
            this.abend("MarkLastDeadInfo", "bad y");
        }
        if (!this.existMatrix[this.maxX][this.maxY]) {
            this.abend("MarkLastDeadInfo", "exist_matrix");
        }
    }

    public void abendGroup() {
        int count = 0;
        int board_size = this.ConfigObject().BoardSize();
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
        int board_size = this.ConfigObject().BoardSize();
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
