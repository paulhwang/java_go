/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.FrontEnd;

import Phwang.Utils.AbendClass;
import Phwang.Protocols.FabricFrontEndProtocolClass;

public class FrontEndJobMgrClass {
    private String objectName() {return "FrontEndJobMgrClass";}

    private static final int MAX_AJAX_ENTRY_ARRAY_SIZE = 1000;

    private UFrontClass frontEndFabricObject;
    private int nextAvailableJobId;
    private int maxAllowedJobId;
    private int maxJobArrayIndex;
    private FrontEndJobClass[] jobArray;

    public FrontEndJobMgrClass(UFrontClass fabric_object_val) {
        this.debugIt(false, "FrontEndJobMgrClass", "init start");

        this.frontEndFabricObject = fabric_object_val;

        this.nextAvailableJobId = 0;
        this.setMaxAllowedJobId(FabricFrontEndProtocolClass.AJAX_MAPING_ID_SIZE);

        this.maxJobArrayIndex = 0;
        this.jobArray = new FrontEndJobClass[MAX_AJAX_ENTRY_ARRAY_SIZE];
    }

    private void setMaxAllowedJobId(int ajax_id_size_val) {
        this.maxAllowedJobId = 1;
        for (var i = 0; i < ajax_id_size_val; i++) {
            this.maxAllowedJobId *= 10;
        }
        this.maxAllowedJobId -= 1;
    }


    
    
    

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
