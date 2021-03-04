/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.front;

import phwang.utils.AbendClass;
import phwang.utils.BinderClass;

public class FrontDExport  implements FrontDExportInterface {
    private String objectName() {return "FrontDExport";}
    
    private FrontRoot frontRoot_;
    
    private FrontRoot frontRoot() { return this.frontRoot_; }
    private FrontJobMgr JobMgr() { return this.frontRoot().JobMgr(); }
    private FrontUBinder uFrontObject() { return this.frontRoot().frontUBinder(); }
    private BinderClass uBinderObject() { return this.uFrontObject().uBinderObject(); }
    private FrontUParser frontUParser() { return this.frontRoot().frontUParser(); }
    
    public FrontDExport(FrontRoot front_root_val) {
        this.debug(false, "FrontDExport", "init start");
        
        this.frontRoot_ = front_root_val;
    }
 	
    public String processHttpRequestPacket(String input_data_val) {
        this.debug(false, "processHttpRequestPacket", "input_data_val = " + input_data_val);
        
        String output_str = this.frontUParser().parseInputPacket(input_data_val);
        
        FrontJob job_entry = this.JobMgr().mallocJob();
        
        if (output_str != null) {
            this.debug(false, "processHttpRequestPacket", "output_str=" + output_str);
        	this.uBinderObject().transmitData(job_entry.jobIdStr() + output_str);
        }
        else {
        	this.uBinderObject().transmitData(job_entry.jobIdStr() + input_data_val);
        }
        
        String response_data = job_entry.readData();
        
        this.debug(false, "processHttpRequestPacket", "response_data = " + response_data);
        return response_data;
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
