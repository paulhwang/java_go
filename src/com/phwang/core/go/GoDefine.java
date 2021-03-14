/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.go;

public class GoDefine {
    public static final int MAX_BOARD_SIZE = 19;

    public static final int GO_EMPTY_STONE = 0;
    public static final int GO_BLACK_STONE = 1;
    public static final int GO_WHITE_STONE = 2;
    public static final int GO_MARK_DEAD_STONE_DIFF = 4;
    public static final int GO_MARK_EMPTY_STONE_DIFF = 8;

    public static final int GO_MARKED_DEAD_BLACK_STONE = (GO_BLACK_STONE + GO_MARK_DEAD_STONE_DIFF);
    public static final int GO_MARKED_DEAD_WHITE_STONE = (GO_WHITE_STONE + GO_MARK_DEAD_STONE_DIFF);

    public static final int GO_MARKED_EMPTY_BLACK_STONE = (GO_BLACK_STONE + GO_MARK_EMPTY_STONE_DIFF);
    public static final int GO_MARKED_EMPTY_WHITE_STONE = (GO_WHITE_STONE + GO_MARK_EMPTY_STONE_DIFF);

    public static int getOppositeColor(int color_val) {
        switch (color_val) {
            case GO_BLACK_STONE:
                return GO_WHITE_STONE;

            case GO_WHITE_STONE:
                return GO_BLACK_STONE;

            default:
                return GO_EMPTY_STONE;
        }
    }

}

