/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.android;

import com.phwang.core.utils.EncodeNumber;

public class AndroidFabricInfo {
	private String jobIdStr_;
    private String linkIdStr_;
    private String SessionIdStr_;
    
    public String jobIdStr() { return this.jobIdStr_; }
    public String linkIdStr() { return this.linkIdStr_; }
    public String SessionIdStr() { return this.SessionIdStr_; }
    
    public void setLinkIdStr(String val) { this.linkIdStr_ = val; }
    public void setSessionIdStr(String val) { this.SessionIdStr_ = val; }
    
    public AndroidFabricInfo(){
    	this.jobIdStr_ = EncodeNumber.encode(8, AndroidImport.FRONT_JOB_ID_SIZE);
    }
}
