/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.front;

import phwang.utils.AbendClass;

public class FrontDExport  implements FrontDExportInterface {
    private String objectName() {return "FrontDExport";}
    
    private FrontRoot frontRootObject_;
    
    private FrontRoot frontRootObject() { return this.frontRootObject_; }
    private DFrontClass dFrontObject() { return this.frontRootObject().dFrontObject(); }
    
    public FrontDExport(FrontRoot root_object_val) {
        this.debug(false, "FrontDExport", "init start");
        
        this.frontRootObject_ = root_object_val;
    }
 	
	public String processHttpRequestPacket(String request_val) {
		return this.dFrontObject().processHttpRequestPacket(request_val);
	}
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
