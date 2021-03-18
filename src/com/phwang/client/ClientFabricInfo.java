/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.client;

import com.phwang.core.utils.EncodeNumber;

public class ClientFabricInfo {
	private String jobIdStr_;
    private String linkIdStr_;
    private String SessionIdStr_;
    private String myName_;
    private String password_;
    
    public String jobIdStr() { return this.jobIdStr_; }
    public String linkIdStr() { return this.linkIdStr_; }
    public String SessionIdStr() { return this.SessionIdStr_; }
    public String myName() { return this.myName_; }
    public String password() { return this.password_; }
    
    public void setLinkIdStr(String val) { this.linkIdStr_ = val; }
    public void setSessionIdStr(String val) { this.SessionIdStr_ = val; }
    public void setMyName(String val) { this.myName_ = val; }
    public void setPassword(String val) { this.password_= val; }
    
    public ClientFabricInfo(){
    	this.jobIdStr_ = EncodeNumber.encode(8, ClientImport.FRONT_JOB_ID_SIZE);
    }
}
