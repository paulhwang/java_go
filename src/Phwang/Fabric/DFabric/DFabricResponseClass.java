/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Fabric.DFabric;

import Phwang.Utils.AbendClass;
import Phwang.Fabric.FabricRootClass;

public class DFabricResponseClass {
    private String objectName() {return "DFabricResponseClass";}
    
    private DFabricParserClass dFabricParserObject;
    private FabricRootClass fabricRootObject() { return this.dFabricParserObject.FabricRootObject(); }

    public DFabricResponseClass(DFabricParserClass dfabric_parser_object_val) {
        this.debugIt(false, "DFabricResponseClass", "init start");

        this.dFabricParserObject = dfabric_parser_object_val;
    }
    /*
    private class SetupLinkResponseFormatClass
    {
        [DataMember]
        public string my_name { get; set; }

        [DataMember]
        public string link_id { get; set; }
    }
*/
    
    public String GenerateSetupLinkResponse(String link_id_var, String my_name_var) {
    	/*
        SetupLinkResponseFormatClass raw_data = new SetupLinkResponseFormatClass { my_name = my_name_var, link_id = link_id_var };

        this.debugIt(true, "GenerateSetupLinkResponse", "");
        DataContractJsonSerializer js = new DataContractJsonSerializer(typeof(SetupLinkResponseFormatClass));
        MemoryStream msObj = new MemoryStream();

        js.WriteObject(msObj, raw_data);
        msObj.Position = 0;

        StreamReader sr = new StreamReader(msObj, Encoding.UTF8);
        String data = sr.ReadToEnd();
        sr.Close();
        msObj.Close();

        this.debugIt(true, "GenerateSetupLinkResponse", "data = " + data);
        string response_data = this.EncodeResponse("setup_link", data);
        return response_data;
        */
    	return null;
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
