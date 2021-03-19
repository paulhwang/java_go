/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.client;

import com.phwang.core.protocols.ProtocolDefineClass;
import com.phwang.core.utils.EncodeNumber;

public class ClientPutData {
    private int index_ = 0;
    private int x_ = 5;
    private int y_ = 5;

    public int index() { return this.index_; }
    public int x() { return this.x_; }
    public int y() { return this.y_; }

    public void setIndex(int val) { this.index_ = val; }
    public void setX(int val) { this.x_ = val; }
    public void setY(int val) { this.y_ = val; }
    
    public String getGoConfigStr() {
    	StringBuilder buf = new StringBuilder();
    	buf.append('M');
    	buf.append(EncodeNumber.encode(10, 3));
    	buf.append(EncodeNumber.encode(this.index_, 3));
    	buf.append(EncodeNumber.encode(this.x_, 2));
    	buf.append(EncodeNumber.encode(this.y_, 2));
    	String data = buf.toString();

    	buf = new StringBuilder();
        buf.append(EncodeNumber.encode(data.length(), ProtocolDefineClass.DATA_LENGTH_SIZE));
        buf.append(data);
        return buf.toString();

    }
}

