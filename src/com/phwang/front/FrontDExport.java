/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.front;

import com.phwang.core.utils.Abend;
import com.phwang.core.utils.Binder;

public class FrontDExport  implements FrontDExportInt {
    private String objectName() {return "FrontDExport";}
    
    private FrontRoot frontRoot_;
    
    private FrontRoot frontRoot() { return this.frontRoot_; }
    private FrontJobMgr jobMgr() { return this.frontRoot().jobMgr(); }
    private FrontUBinder frontUBinder() { return this.frontRoot().frontUBinder(); }
    private Binder uBinder() { return this.frontUBinder().uBinder(); }
    private FrontUParser frontUParser() { return this.frontRoot().frontUParser(); }
    
    protected FrontDExport(FrontRoot front_root_val) {
        this.debug(false, "FrontDExport", "init start");
        
        this.frontRoot_ = front_root_val;
    }
 	
    public String processHttpRequestPacket(String input_data_val) {
        this.debug(false, "processHttpRequestPacket", "input_data_val = " + input_data_val);
        
        String output_str = this.frontUParser().parseInputPacket(input_data_val);
        
        FrontJob job_entry = this.jobMgr().mallocJob();
        
        if (output_str != null) {
            this.debug(true, "processHttpRequestPacket", "output_str=" + output_str);
        	this.uBinder().transmitStringData("H" + job_entry.jobIdStr() + output_str);
        }
        else {
        	this.uBinder().transmitStringData("H" + job_entry.jobIdStr() + input_data_val);
        }
        
        String response_data = job_entry.readData();
        
        this.debug(false, "processHttpRequestPacket", "response_data = " + response_data);
        return response_data;
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
