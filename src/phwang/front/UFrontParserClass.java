/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.front;

import phwang.utils.AbendClass;

public class UFrontParserClass {
    private String objectName() {return "UFrontParserClass";}
    
    private FrontRootClass frontRootObject_;
    
    public FrontRootClass frontEndRootObject() { return this.frontRootObject_; }
    
    public UFrontParserClass(FrontRootClass front_root_object_val) {
        this.debug(false, "UFrontParserClass", "init start");

        this.frontRootObject_ = front_root_object_val;
    }
    
    public String parserResponseData(String input_data_val) {
    	this.debug(true, "********parserResponseData", "input_data_val=" + input_data_val);
    	return null;
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
