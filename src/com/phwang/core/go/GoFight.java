/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.go;

import com.phwang.core.utils.Abend;

public class GoFight {
    private String objectName() {return "GoFight";}

    private static final int GO_FIGHT_CLASS_GROUP_LIST_ARRAY_SIZE = 7;

    private GoRoot goRoot_;
    private Boolean abendEngineOn = false;
    private GoGroupList[] theGroupListArray;
    private String theCaptureCount;
    private String theLastDeadStone;

    public GoRoot goRoot() { return this.goRoot_; }
    public GoBoard goBoard() { return this.goRoot_.goBoard(); }
    public GoConfig goConfig() { return this.goRoot_.goConfig(); }

    GoGroupList emptyGroupList() { return this.theGroupListArray[0]; }
    GoGroupList blackGroupList() { return this.theGroupListArray[1]; }
    GoGroupList whiteGroupList() { return this.theGroupListArray[2]; }
    GoGroupList blackDeadGroupList() { return this.theGroupListArray[3]; }
    GoGroupList whiteDeadGroupList() { return this.theGroupListArray[4]; }
    GoGroupList blackEmptyGroupList() { return this.theGroupListArray[5]; }
    GoGroupList whiteEmptyGroupList() { return this.theGroupListArray[6]; }

    public GoFight(GoRoot go_root_val) {
        this.goRoot_ = go_root_val;
        this.theGroupListArray = new GoGroupList[GO_FIGHT_CLASS_GROUP_LIST_ARRAY_SIZE];
        this.resetEngineObjectData();
    }

    public void resetEngineObjectData() {
        this.goBoard().resetBoardObjectData();

        this.theGroupListArray[1] = new GoGroupList(this, 1, GoDefine.GO_BLACK_STONE, false, null, null);
        this.theGroupListArray[2] = new GoGroupList(this, 2, GoDefine.GO_WHITE_STONE, false, null, null);
        this.resetMarkedGroupLists();
        this.resetEmptyGroupLists();

        this.theCaptureCount = null;
        this.theLastDeadStone = null;
    }
    
    private void resetMarkedGroupLists() {
        this.theGroupListArray[3] = new GoGroupList(this, 3, GoDefine.GO_BLACK_STONE, true, "black", "gray");
        this.theGroupListArray[4] = new GoGroupList(this, 4, GoDefine.GO_WHITE_STONE, true, "white", "gray");
        this.goBoard().resetMarkedBoardObjectData();
    }

    private void resetEmptyGroupLists() {
        this.theGroupListArray[0] = new GoGroupList(this, 0, GoDefine.GO_EMPTY_STONE, false, null, null);
        this.theGroupListArray[5] = new GoGroupList(this, 5, GoDefine.GO_EMPTY_STONE, false, null, "black");
        this.theGroupListArray[6] = new GoGroupList(this, 6, GoDefine.GO_EMPTY_STONE, false, null, "white");
    }

    public void enterBattle(GoMove move_val) {
        this.debug(false, "enterBattle", move_val.moveInfo());

        this.goBoard().addStoneToBoard(move_val.X(), move_val.Y(), move_val.MyColor());
        GoGroup my_group = this.insertStoneToGroupList(move_val);
        if (my_group == null) {
            this.abend("enterBattle", "fail in insertStoneToGroupList");
            return;
        }

        int dead_count = this.killOtherColorGroups(move_val, my_group);

        if (!my_group.groupHasAir()) {
            this.removeDeadGroup(my_group);
        }

        if (dead_count != 0) {
            if (move_val.MyColor() == GoDefine.GO_BLACK_STONE) {
                this.goBoard().addBlackCapturedStones(dead_count);
            }
            else if (move_val.MyColor() == GoDefine.GO_WHITE_STONE) {
                this.goBoard().addWhiteCapturedStones(dead_count);
            }
            else {
                this.abend("enterBattle", "bad color");
            }
        }
        this.abendEngine();
    }

    private GoGroup insertStoneToGroupList(GoMove move_val) {
    	GoGroupList g_list;

        if (move_val.MyColor() == GoDefine.GO_BLACK_STONE) {
            g_list = this.blackGroupList();
        }
        else if (move_val.MyColor() == GoDefine.GO_WHITE_STONE) {
            g_list = this.whiteGroupList();
        }
        else {
            this.abend("insertStoneToGroupList", move_val.moveInfo());
            return null;
        }

        GoGroup group = g_list.findCandidateGroup(move_val.X(), move_val.Y());
        if (group == null) {
            group = new GoGroup(g_list);
            group.insertStoneToGroup(move_val.X(), move_val.Y(), false);
            g_list.insertGroupToGroupList(group);
            return group;
        }

        group.insertStoneToGroup(move_val.X(), move_val.Y(), false);

        int dummy_count = 0;
        GoGroup group2;
        while (true) {
            group2 = g_list.findOtherCandidateGroup(group, move_val.X(), move_val.Y());
            if (group2 == null) {
                break;
            }
            dummy_count += 1;
            group.mergeWithOtherGroup(group2);
            g_list.removeGroupFromGroupList(group2);
        }
        if (dummy_count > 3) {
            this.abend("insertStoneToGroupList", "dummy_count");
        }
        return group;
    }

    private int killOtherColorGroups(GoMove move_val, GoGroup my_group_val) {
        int count;
        count = this.killOtherColorGroup(my_group_val, move_val.X() - 1, move_val.Y());
        count += this.killOtherColorGroup(my_group_val, move_val.X() + 1, move_val.Y());
        count += this.killOtherColorGroup(my_group_val, move_val.X(), move_val.Y() - 1);
        count += this.killOtherColorGroup(my_group_val, move_val.X(), move_val.Y() + 1);
        return count;
    }

    private int killOtherColorGroup(GoGroup my_group_val, int x_val, int y_val) {
    	GoGroup his_group;

        if (!this.goConfig().isValidCoordinates(x_val, y_val)) {
            return 0;
        }

        if (this.goBoard().boardArray(x_val, y_val) != my_group_val.hisColor())
        {
            return 0;
        }

        his_group = this.getGroupByCoordinate(x_val, y_val, my_group_val.hisColor());
        if (his_group == null)
        {
            //this.debugIt(true, "killOtherColorGroup", "my_color=" + this.myColor + " his_color=" + this.hisColor);
            this.abend("killOtherColorGroup", "null his_group");
            return 0;
        }

        if (his_group.groupHasAir())
        {
            return 0;
        }

        int dead_count = his_group.stoneCount();
        if ((my_group_val.stoneCount() == 1) && (his_group.stoneCount() == 1))
        {
            his_group.markLastDeadInfo();
        }

        this.removeDeadGroup(his_group);
        return dead_count;
    }

    private GoGroup getGroupByCoordinate(int x_val, int y_val, int color_val) {
    	GoGroupList g_list;
        if ((color_val == GoDefine.GO_BLACK_STONE) || (color_val == GoDefine.GO_MARKED_DEAD_BLACK_STONE)) {
            g_list = this.blackGroupList();
        }
        else {
            g_list = this.whiteGroupList();
        }

        for (int i = 0; i < g_list.groupCount(); i++) {
            if (g_list.groupArray(i).existMatrix(x_val, y_val)){
                return g_list.groupArray(i);
            }
        }
        return null;
    }

    private void removeDeadGroup(GoGroup group)
    {
        group.removeDeadStoneFromBoard();
        if (group.myColor() == GoDefine.GO_BLACK_STONE)
        {
            this.blackGroupList().removeGroupFromGroupList(group);
        }
        else
        {
            this.whiteGroupList().removeGroupFromGroupList(group);
        }
     }

    private void markLastDeadInfo1111111111111111(GoGroup group_val)
    {
        /*
        this->theBaseObject->boardObject()->setLastDeadStone(group_val->maxX(), group_val->maxY());

        if (group_val->maxX() != group_val->minX())
        {
            this->abend("markLastDeadInfo", "bad x");
        }
        if (group_val->maxY() != group_val->minY())
        {
            this->abend("markLastDeadInfo", "bad y");
        }
        if (!group_val->existMatrix(group_val->maxX(), group_val->maxY()))
        {
            this->abend("markLastDeadInfo", "exist_matrix");
        }
        */
    }

    private void abendEngine() {
        if (!this.abendEngineOn) {
            return;
        }
        this.debug(false, "abendEngine", "is ON ***");

        /*
        int stones_count = 0;
        int i = 0;
        while (i < GO_FIGHT_CLASS_GROUP_LIST_ARRAY_SIZE) {
            GoGroupListClass *group_list = this->theGroupListArray[i];
            stones_count += group_list->theTotalStoneCount;
            i += 1;
        }
        */

        /* check if a stone exist in both black and white group_lists */
        int black_stone_count = 0;
        int white_stone_count = 0;
        int board_size = this.goConfig().boardSize();

        for (int x = 0; x < board_size; x++) {
            for (int y = 0; y < board_size; y++) {
                if (this.goBoard().boardArray(x, y) == GoDefine.GO_BLACK_STONE) {
                    black_stone_count++;
                    if (!this.blackGroupList().stoneExistWithinMe(x, y)) {
                        this.abend("abendEngine", "black stone does not exist in blackGroupList");
                    }
                }
                else if (this.goBoard().boardArray(x, y) == GoDefine.GO_WHITE_STONE) {
                    white_stone_count++;
                    if (!this.whiteGroupList().stoneExistWithinMe(x, y)) {
                        this.abend("abendEngine", "white stone does not exist in whiteGroupList");
                    }
                }
                else if (this.goBoard().boardArray(x, y) == GoDefine.GO_EMPTY_STONE) {
                }
                else {
                    this.abend("abendEngine", "bad color in theBoardArray");
                }
            }
        }

        int black_stone_count1 = 0;
        int white_stone_count1 = 0;
        for (int x = 0; x < board_size; x++) {
            for (int y = 0; y < board_size; y++) {
                if (this.blackGroupList().stoneExistWithinMe(x, y)) {
                    black_stone_count1++;

                    if (this.goBoard().boardArray(x, y) != GoDefine.GO_BLACK_STONE) {
                        this.abend("abendEngine", "black stone does not exist in theBoardArray");
                    }

                    if (this.whiteGroupList().stoneExistWithinMe(x, y)) {
                        this.abend("abendEngine", "balck exist in wrong group list");
                    }
                }
                if (this.whiteGroupList().stoneExistWithinMe(x, y)) {
                    white_stone_count1++;

                    if (this.goBoard().boardArray(x, y) != GoDefine.GO_WHITE_STONE) {
                        this.abend("abendEngine", "black stone does not exist in theBoardArray");
                    }
                }
            }
        }

        if (black_stone_count != black_stone_count1) {
            this.abend("abendEngine", "black_stone_count does not match");
        }
        if (white_stone_count != white_stone_count1) {
            this.abend("abendEngine", "white_stone_count does not match");
        }

        if (this.blackGroupList().totalStoneCount() != black_stone_count) {
            //printf("abendEngine   %d\n", this->blackGroupList()->totalStoneCount());
            //printf("abendEngine   %d\n", black_stone_count);
            this.abend("abendEngine", "black_stone count does not match");
        }
        if (this.whiteGroupList().totalStoneCount() != white_stone_count) {
            //printf("abendEngine   %d\n", this->whiteGroupList()->totalStoneCount());
            //printf("abendEngine   %d\n", white_stone_count);
            this.abend("abendEngine", "white count does not match");
        }
        /*

                //this.goLog("abendEngine", this.gameObject().gameIsOver());
                if (this.gameObject().gameIsOver()) {
                    if (this.boardSize() * this.boardSize() !== stones_count) {
                        this.abend("abendEngine", "stones_count=" + stones_count);
                    }
                }
        */
        this.emptyGroupList().abendGroupList();
        this.blackGroupList().abendGroupList();
        this.whiteGroupList().abendGroupList();
        this.blackDeadGroupList().abendGroupList();
        this.whiteDeadGroupList().abendGroupList();
        this.blackEmptyGroupList().abendGroupList();
        this.whiteEmptyGroupList().abendGroupList();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
