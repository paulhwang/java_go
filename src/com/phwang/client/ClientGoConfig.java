/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.client;

import com.phwang.core.utils.Encoders;
import com.phwang.core.utils.Define;

public class ClientGoConfig {
    private int boardSize_ = 19;
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
    	buf.append(Encoders.iEncodeRaw3(19));
    	buf.append(Encoders.iEncodeRaw2(this.boardSize_));
    	buf.append(Encoders.iEncodeRaw2(this.handicapPoint_));
    	buf.append(Encoders.iEncodeRaw2(this.handicapPoint_));
    	String data = buf.toString();
        return Encoders.sEncode2(data);
    }
}

