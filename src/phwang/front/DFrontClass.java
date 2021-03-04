/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.front;

import phwang.utils.*;
import phwang.protocols.FabricFrontEndProtocolClass;

public class DFrontClass {
    private String objectName() {return "DFrontClass";}

    private FrontRootClass frontRootObject_;
    
    public FrontRootClass frontEndRootObject() { return this.frontRootObject_; }
    private FrontJobMgrClass frontJobMgrObject() { return this.frontEndRootObject().frontJobMgrObject(); }
    private UFrontClass uFrontObject() { return this.frontEndRootObject().uFrontObject(); }
    private BinderClass uBinderObject() { return this.uFrontObject().uBinderObject(); }
    private FrontUParser dFrontParserObject() { return this.frontEndRootObject().dFrontParserObject(); }
    
    public DFrontClass(FrontRootClass root_object_val) {
        this.debug(false, "DFrontClass", "init start");
        
        this.frontRootObject_ = root_object_val;
    }

    public String processHttpRequestPacket(String input_data_val) {
        this.debug(false, "processHttpRequestPacket", "input_data_val = " + input_data_val);
        
        String output_str = this.dFrontParserObject().parseInputPacket(input_data_val);
        
        FrontJobClass job_entry = this.frontJobMgrObject().mallocJob();
        
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
