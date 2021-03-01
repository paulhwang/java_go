/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.go;

import phwang.utils.*;

public class GoFightClass {
    private String objectName() {return "GoFightClass";}

    private static final int GO_FIGHT_CLASS_GROUP_LIST_ARRAY_SIZE = 7;

    private GoRootClass theRootObject;
    private Boolean abendEngineOn = false;
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

    public GoFightClass(GoRootClass go_root_object_val) {
        this.theRootObject = go_root_object_val;
        this.theGroupListArray = new GoGroupListClass[GO_FIGHT_CLASS_GROUP_LIST_ARRAY_SIZE];
        this.resetEngineObjectData();
    }

    public void resetEngineObjectData() {
        this.BoardObject().resetBoardObjectData();

        this.theGroupListArray[1] = new GoGroupListClass(this, 1, GoDefineClass.GO_BLACK_STONE, false, null, null);
        this.theGroupListArray[2] = new GoGroupListClass(this, 2, GoDefineClass.GO_WHITE_STONE, false, null, null);
        this.resetMarkedGroupLists();
        this.resetEmptyGroupLists();

        this.theCaptureCount = null;
        this.theLastDeadStone = null;
    }
    
    private void resetMarkedGroupLists() {
        this.theGroupListArray[3] = new GoGroupListClass(this, 3, GoDefineClass.GO_BLACK_STONE, true, "black", "gray");
        this.theGroupListArray[4] = new GoGroupListClass(this, 4, GoDefineClass.GO_WHITE_STONE, true, "white", "gray");
        this.BoardObject().resetMarkedBoardObjectData();
    }

    private void resetEmptyGroupLists() {
        this.theGroupListArray[0] = new GoGroupListClass(this, 0, GoDefineClass.GO_EMPTY_STONE, false, null, null);
        this.theGroupListArray[5] = new GoGroupListClass(this, 5, GoDefineClass.GO_EMPTY_STONE, false, null, "black");
        this.theGroupListArray[6] = new GoGroupListClass(this, 6, GoDefineClass.GO_EMPTY_STONE, false, null, "white");
    }

    public void enterBattle(GoMoveClass move_val) {
        this.debug(false, "enterBattle", move_val.MoveInfo());

        this.BoardObject().addStoneToBoard(move_val.X(), move_val.Y(), move_val.MyColor());
        GoGroupClass my_group = this.insertStoneToGroupList(move_val);
        if (my_group == null) {
            this.abend("enterBattle", "fail in insertStoneToGroupList");
            return;
        }

        int dead_count = this.killOtherColorGroups(move_val, my_group);

        if (!my_group.groupHasAir()) {
            this.removeDeadGroup(my_group);
        }

        if (dead_count != 0) {
            if (move_val.MyColor() == GoDefineClass.GO_BLACK_STONE) {
                this.BoardObject().AddBlackCapturedStones(dead_count);
            }
            else if (move_val.MyColor() == GoDefineClass.GO_WHITE_STONE) {
                this.BoardObject().AddWhiteCapturedStones(dead_count);
            }
            else {
                this.abend("enterBattle", "bad color");
            }
        }
        this.abendEngine();
    }

    private GoGroupClass insertStoneToGroupList(GoMoveClass move_val) {
        GoGroupListClass g_list;

        if (move_val.MyColor() == GoDefineClass.GO_BLACK_STONE) {
            g_list = this.blackGroupList();
        }
        else if (move_val.MyColor() == GoDefineClass.GO_WHITE_STONE) {
            g_list = this.whiteGroupList();
        }
        else {
            this.abend("insertStoneToGroupList", move_val.MoveInfo());
            return null;
        }

        GoGroupClass group = g_list.findCandidateGroup(move_val.X(), move_val.Y());
        if (group == null) {
            group = new GoGroupClass(g_list);
            group.insertStoneToGroup(move_val.X(), move_val.Y(), false);
            g_list.insertGroupToGroupList(group);
            return group;
        }

        group.insertStoneToGroup(move_val.X(), move_val.Y(), false);

        int dummy_count = 0;
        GoGroupClass group2;
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

    private int killOtherColorGroups(GoMoveClass move_val, GoGroupClass my_group_val) {
        int count;
        count = this.killOtherColorGroup(my_group_val, move_val.X() - 1, move_val.Y());
        count += this.killOtherColorGroup(my_group_val, move_val.X() + 1, move_val.Y());
        count += this.killOtherColorGroup(my_group_val, move_val.X(), move_val.Y() - 1);
        count += this.killOtherColorGroup(my_group_val, move_val.X(), move_val.Y() + 1);
        return count;
    }

    private int killOtherColorGroup(GoGroupClass my_group_val, int x_val, int y_val) {
        GoGroupClass his_group;

        if (!this.ConfigObject().IsValidCoordinates(x_val, y_val)) {
            return 0;
        }

        if (this.BoardObject().BoardArray(x_val, y_val) != my_group_val.HisColor())
        {
            return 0;
        }

        his_group = this.getGroupByCoordinate(x_val, y_val, my_group_val.HisColor());
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

        int dead_count = his_group.StoneCount();
        if ((my_group_val.StoneCount() == 1) && (his_group.StoneCount() == 1))
        {
            his_group.markLastDeadInfo();
        }

        this.removeDeadGroup(his_group);
        return dead_count;
    }

    private GoGroupClass getGroupByCoordinate(int x_val, int y_val, int color_val) {
        GoGroupListClass g_list;
        if ((color_val == GoDefineClass.GO_BLACK_STONE) || (color_val == GoDefineClass.GO_MARKED_DEAD_BLACK_STONE))
        {
            g_list = this.blackGroupList();
        }
        else
        {
            g_list = this.whiteGroupList();
        }

        for (int i = 0; i < g_list.GroupCount(); i++)
        {
            if (g_list.GroupArray(i).ExistMatrix(x_val, y_val))
            {
                return g_list.GroupArray(i);
            }
        }
        return null;
    }

    private void removeDeadGroup(GoGroupClass group)
    {
        group.removeDeadStoneFromBoard();
        if (group.MyColor() == GoDefineClass.GO_BLACK_STONE)
        {
            this.blackGroupList().removeGroupFromGroupList(group);
        }
        else
        {
            this.whiteGroupList().removeGroupFromGroupList(group);
        }
     }

    private void markLastDeadInfo1111111111111111(GoGroupClass group_val)
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
        int board_size = this.ConfigObject().BoardSize();

        for (int x = 0; x < board_size; x++) {
            for (int y = 0; y < board_size; y++) {
                if (this.BoardObject().BoardArray(x, y) == GoDefineClass.GO_BLACK_STONE) {
                    black_stone_count++;
                    if (!this.blackGroupList().stoneExistWithinMe(x, y)) {
                        this.abend("abendEngine", "black stone does not exist in blackGroupList");
                    }
                }
                else if (this.BoardObject().BoardArray(x, y) == GoDefineClass.GO_WHITE_STONE) {
                    white_stone_count++;
                    if (!this.whiteGroupList().stoneExistWithinMe(x, y)) {
                        this.abend("abendEngine", "white stone does not exist in whiteGroupList");
                    }
                }
                else if (this.BoardObject().BoardArray(x, y) == GoDefineClass.GO_EMPTY_STONE) {
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

                    if (this.BoardObject().BoardArray(x, y) != GoDefineClass.GO_BLACK_STONE) {
                        this.abend("abendEngine", "black stone does not exist in theBoardArray");
                    }

                    if (this.whiteGroupList().stoneExistWithinMe(x, y)) {
                        this.abend("abendEngine", "balck exist in wrong group list");
                    }
                }
                if (this.whiteGroupList().stoneExistWithinMe(x, y)) {
                    white_stone_count1++;

                    if (this.BoardObject().BoardArray(x, y) != GoDefineClass.GO_WHITE_STONE) {
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
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
