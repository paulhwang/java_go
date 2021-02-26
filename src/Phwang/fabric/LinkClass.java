/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.fabric;

import Phwang.Utils.*;
import Phwang.Utils.ListMgr.ListMgrClass;
import Phwang.Utils.ListMgr.ListEntryClass;
import Phwang.protocols.FabricFrontEndProtocolClass;

public class LinkClass {
    private String objectName() {return "LinkClass";}

    private ListEntryClass listEntryObject;
    private int theLinkId;
    private String theLinkIdStr;
    private String myName;
    private SessionMgrClass sessionMgrObject;
    private ListQueueClass pendingSessionSetupQueue;
    private ListQueueClass pendingSessionSetupQueue3;

    public String MyName() { return this.myName; }
    public int LinkId() { return this.theLinkId; }
    public String LinkIdStr() { return this.theLinkIdStr; }
    public SessionMgrClass SessionMgrObject() { return this.sessionMgrObject; }

    public int GetSessionArrayMaxIndex() { return this.sessionMgrObject.GetSessionArrayMaxIndex(); }
    public ListEntryClass[] GetSessionArrayEntryTable() { return this.sessionMgrObject.GetSessionArrayEntryTable(); }

    public LinkClass(String my_name_val) {
        this.debugIt(false, "LinkClass", "init start");
        
        this.myName = my_name_val;

        this.pendingSessionSetupQueue = new ListQueueClass(false, 0);
        this.pendingSessionSetupQueue3 = new ListQueueClass(false, 0);
        this.sessionMgrObject = new SessionMgrClass(this);
    }

    public void bindListEntry(ListEntryClass list_entry_objectg_val) {
        this.listEntryObject = list_entry_objectg_val;
        this.theLinkId = this.listEntryObject.Id();
        this.theLinkIdStr = EncodeNumberClass.encodeNumber(this.theLinkId, FabricFrontEndProtocolClass.FABRIC_LINK_ID_SIZE);
    }

    public SessionClass mallocSession() {
        return this.sessionMgrObject.mallocSession();
    }

    public void setPendingSessionSetup(String link_session_id_str_val, String theme_data_val) {
        String data = link_session_id_str_val + theme_data_val;
        this.pendingSessionSetupQueue.enqueueData(data);
        /*
        char* buf, *data_ptr;

        buf = data_ptr = (char*)malloc(LINK_MGR_DATA_BUFFER_SIZE);
        memcpy(data_ptr, session_id_index_val, SESSION_MGR_PROTOCOL_SESSION_ID_INDEX_SIZE);
        data_ptr += SESSION_MGR_PROTOCOL_SESSION_ID_INDEX_SIZE;
        strcpy(data_ptr, theme_data_val);
        phwangEnqueue(this->thePendingSessionSetupQueue, buf);
        */
    }

    public void setPendingSessionSetup3(String browser_theme_id_str_val, String session_id_str_val, String theme_data_val) {
        this.debugIt(false, "********************SetPendingSessionSetup3", "browser_theme_id_str_val =" + browser_theme_id_str_val);
        this.debugIt(false, "********************SetPendingSessionSetup3", "session_id_str_val =" + session_id_str_val);
        this.debugIt(false, "********************SetPendingSessionSetup3", "theme_data_val =" + theme_data_val);
        String data = browser_theme_id_str_val + session_id_str_val + theme_data_val;
        this.pendingSessionSetupQueue3.enqueueData(data);
        this.debugIt(true, "********************SetPendingSessionSetup3", "data =" + data);
        /*
        char* buf, *data_ptr;

        buf = data_ptr = (char*)malloc(LINK_MGR_DATA_BUFFER_SIZE);
        memcpy(data_ptr, session_id_index_val, SESSION_MGR_PROTOCOL_SESSION_ID_INDEX_SIZE);
        data_ptr += SESSION_MGR_PROTOCOL_SESSION_ID_INDEX_SIZE;
        strcpy(data_ptr, theme_data_val);
        phwangEnqueue(this->thePendingSessionSetupQueue3, buf);
        */

    }
    
    public String getPendingSessionSetup() {
        return (String) this.pendingSessionSetupQueue.dequeueData();
    }

    public String getPendingSessionSetup3() {
        return (String) this.pendingSessionSetupQueue3.dequeueData();
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
