/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.client;

import com.phwang.core.utils.EncodeNumber;
import com.phwang.core.utils.Define;

public class ClientGoConfig {
    private int boardSize_ = 9;
    private int handicapPoint_ = 0;
    private int komiPoint_ = 0;

    public int boardSize() { return this.boardSize_; }
    public int handicapPoint() { return this.handicapPoint_; }
    public int komiPoint() { return this.komiPoint_; }

    public void setBoardSize(int val) { this.boardSize_ = val; }
    public void setHandicapPoint(int val) { this.handicapPoint_ = val; }
    public void setKomiPoint(int val) { this.komiPoint_ = val; }
    
    public String getGoConfigStr() {
    	StringBuilder buf = new StringBuilder();
    	buf.append('G');
    	buf.append(EncodeNumber.encode(9, 3));
    	buf.append(EncodeNumber.encode(this.boardSize_, 2));
    	buf.append(EncodeNumber.encode(this.handicapPoint_, 2));
    	buf.append(EncodeNumber.encode(this.handicapPoint_, 2));
    	String data = buf.toString();

    	buf = new StringBuilder();
        buf.append(EncodeNumber.encode(data.length(), Define.DATA_LENGTH_SIZE));
        buf.append(data);
        return buf.toString();

    }
}

