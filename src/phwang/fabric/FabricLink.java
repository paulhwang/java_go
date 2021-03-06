/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.fabric;

import phwang.utils.*;
import phwang.protocols.FabricFrontEndProtocolClass;

public class FabricLink {
    private String objectName() {return "FabricLink";}

    private ListEntryClass listEntry_;
    private String myName_;
    private FabricSessionMgr sessionMgr_;
    private ListQueue pendingSessionSetupQueue_;
    private ListQueue pendingSessionSetupQueue3_;

    public String myName() { return this.myName_; }
    public int linkId() { return this.listEntry().id(); }
    public String linkIdStr() { return this.listEntry().idStr(); }
    private ListEntryClass listEntry() { return this.listEntry_; }
    public FabricSessionMgr sessionMgr() { return this.sessionMgr_; }

    public int GetSessionArrayMaxIndex() { return this.sessionMgr().getSessionArrayMaxIndex(); }
    public ListEntryClass[] GetSessionArrayEntryTable() { return this.sessionMgr().getSessionArrayEntryTable(); }

    public FabricLink(String my_name_val) {
        this.debug(false, "FabricLink", "init start");
        
        this.myName_ = my_name_val;

        this.pendingSessionSetupQueue_ = new ListQueue(false, 0);
        this.pendingSessionSetupQueue3_ = new ListQueue(false, 0);
        this.sessionMgr_ = new FabricSessionMgr(this);
    }

    public void bindListEntry(ListEntryClass list_entry_object_val) {
        this.listEntry_ = list_entry_object_val;
    }

    public FabricSession mallocSession() {
        return this.sessionMgr().mallocSession();
    }

    public void setPendingSessionSetup(String link_session_id_str_val, String theme_data_val) {
        String data = link_session_id_str_val + theme_data_val;
        this.pendingSessionSetupQueue_.enqueue(data);
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
        this.pendingSessionSetupQueue3_.enqueue(data);
        this.debug(false, "********************SetPendingSessionSetup3", "data =" + data);
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
        return (String) this.pendingSessionSetupQueue_.dequeue();
    }

    public String getPendingSessionSetup3() {
        return (String) this.pendingSessionSetupQueue3_.dequeue();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
