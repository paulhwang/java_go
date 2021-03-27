/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.client;

import com.phwang.core.go.GoExport;
import com.phwang.core.utils.Encoders;
import com.phwang.core.utils.Define;

public class ClientGoAct {
	private char action_ = GoExport.GO_PROTOCOL_MOVE_COMMAND;
    private int x_ = 5;
    private int y_ = 5;
    private int color_ = 1;
    private int index_ = 1;

    public int x() { return this.x_; }
    public int y() { return this.y_; }
    public int color() { return this.color_; }
    public int index() { return this.index_; }

    public void setAction(char val) { this.action_ = val; }
    public void setX(int val) { this.x_ = val; }
    public void setY(int val) { this.y_ = val; }
    public void setColor(int val) { this.color_ = val; }
    public void setIndex(int val) { this.index_ = val; }

    public void setMove(int x_val, int y_val, int color_val, int index_val) {
        this.x_ = x_val;
        this.y_ = y_val;
        this.color_ = color_val;
        this.index_ = index_val;
    }

    protected String getGoActStr() {
    	StringBuilder buf = new StringBuilder();
    	buf.append('G');
		buf.append(this.action_);
    	if (this.action_ == GoExport.GO_PROTOCOL_MOVE_COMMAND) {
    		buf.append(Encoders.iEncodeRaw2(this.x_));
    		buf.append(Encoders.iEncodeRaw2(this.y_));
    		buf.append(Encoders.iEncodeRaw1(this.color_));
    		buf.append(Encoders.iEncodeRaw3(this.index_));
    	}
    	String data = buf.toString();
        return Encoders.sEncode2(data);
    }
}

