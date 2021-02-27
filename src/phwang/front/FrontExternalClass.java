/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.front;

import phwang.utils.AbendClass;

public class FrontExternalClass implements FrontExportInterface {
    private String objectName() {return "FrontExternalClass";}
    
    private FrontRootClass frontRootObject;
    
    private FrontRootClass FrontRootObject() { return this.frontRootObject; }
    private DFrontClass dFrontObject() { return this.FrontRootObject().dFrontObject(); }
    
    public FrontExternalClass(FrontRootClass root_object_val) {
        this.debug(false, "FrontExternalClass", "init start");
        
        this.frontRootObject = root_object_val;
    }
 	
	public String processHttpRequestPacket(String request_val) {
		return this.dFrontObject().processHttpRequestPacket(request_val);
	}
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
