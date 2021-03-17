/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.android;

public class AndroidFabricInfo {
    private String linkIdStr_;
    private String SessionIdStr_;
    
    public String linkIdStr() { return this.linkIdStr_; }
    public String SessionIdStr() { return this.SessionIdStr_; }
    
    public void setLinkIdStr(String val) { this.linkIdStr_ = val; }
    public void setSessionIdStr(String val) { this.SessionIdStr_ = val; }
}
