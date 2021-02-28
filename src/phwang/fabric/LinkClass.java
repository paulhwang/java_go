/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.fabric;

import phwang.utils.*;
import phwang.protocols.FabricFrontEndProtocolClass;

public class LinkClass {
    private String objectName() {return "LinkClass";}

    private ListEntryClass listEntryObject_;
    private String myName_;
    private SessionMgrClass sessionMgrObject_;
    private ListQueueClass pendingSessionSetupQueue;
    private ListQueueClass pendingSessionSetupQueue3;

    public String myName() { return this.myName_; }
    public int linkId() { return this.listEntryObject().id(); }
    public String linkIdStr() { return this.listEntryObject().idStr(); }
    private ListEntryClass listEntryObject() { return this.listEntryObject_; }
    public SessionMgrClass sessionMgrObject() { return this.sessionMgrObject_; }

    public int GetSessionArrayMaxIndex() { return this.sessionMgrObject().GetSessionArrayMaxIndex(); }
    public ListEntryClass[] GetSessionArrayEntryTable() { return this.sessionMgrObject().GetSessionArrayEntryTable(); }

    public LinkClass(String my_name_val) {
        this.debug(false, "LinkClass", "init start");
        
        this.myName_ = my_name_val;

        this.pendingSessionSetupQueue = new ListQueueClass(false, 0);
        this.pendingSessionSetupQueue3 = new ListQueueClass(false, 0);
        this.sessionMgrObject_ = new SessionMgrClass(this);
    }

    public void bindListEntry(ListEntryClass list_entry_object_val) {
        this.listEntryObject_ = list_entry_object_val;
    }

    public SessionClass mallocSession() {
        return this.sessionMgrObject().mallocSession();
    }

    public void setPendingSessionSetup(String link_session_id_str_val, String theme_data_val) {
        String data = link_session_id_str_val + theme_data_val;
        this.pendingSessionSetupQueue.enqueue(data);
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
        this.debug(false, "********************SetPendingSessionSetup3", "browser_theme_id_str_val =" + browser_theme_id_str_val);
        this.debug(false, "********************SetPendingSessionSetup3", "session_id_str_val =" + session_id_str_val);
        this.debug(false, "********************SetPendingSessionSetup3", "theme_data_val =" + theme_data_val);
        String data = browser_theme_id_str_val + session_id_str_val + theme_data_val;
        this.pendingSessionSetupQueue3.enqueue(data);
        this.debug(true, "********************SetPendingSessionSetup3", "data =" + data);
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
        return (String) this.pendingSessionSetupQueue.dequeue();
    }

    public String getPendingSessionSetup3() {
        return (String) this.pendingSessionSetupQueue3.dequeue();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
