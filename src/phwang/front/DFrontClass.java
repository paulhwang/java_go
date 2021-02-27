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
    private UFrontClass uFrontObject() { return this.frontEndRootObject().uFrontObject(); }

    public DFrontClass(FrontRootClass root_object_val) {
        this.debugIt(false, "DFrontClass", "init start");
        
        this.frontRootObject_ = root_object_val;
    }

    /*
    public String processHttpRequestPacket(String input_data_val) {
        this.debugIt(false, "processAjaxRequestPacket", "input_data_val = " + input_data_val);
        
        FrontJobClass job_entry = this.frontJobMgrObject.mallocJobObject();
        this.uFrontObject().binderObject().transmitData(job_entry.ajaxIdStr + input_data_val);
        String response_data = job_entry.readData();
        
        this.debugIt(false, "processAjaxRequestPacket", "response_data = " + response_data);
        return response_data;
    }
*/
    
    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
