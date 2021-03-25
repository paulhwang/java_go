/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.fabric;

import com.phwang.core.utils.Abend;
import com.phwang.core.utils.ListEntry;
import com.phwang.core.utils.ListEntryInt;
import com.phwang.core.utils.ListQueue;

public class FabricLink implements ListEntryInt {
    private String objectName() {return "FabricLink";}

    private ListEntry listEntry_;
    private char clientType_;
    private String myName_;
    private FabricLSessionMgr sessionMgr_;
    private ListQueue pendingSessionSetupQueue_;
    private ListQueue pendingSessionSetupQueue3_;

    protected char clientType() { return this.clientType_; };
    protected String myName() { return this.myName_; }
    protected int linkId() { return this.listEntry().id(); }
    protected String linkIdStr() { return this.listEntry().idStr(); }
    protected ListEntry listEntry() { return this.listEntry_; }
    protected FabricLSessionMgr sessionMgr() { return this.sessionMgr_; }

    protected int GetSessionArrayMaxIndex() { return this.sessionMgr().getSessionArrayMaxIndex(); }
    protected ListEntry[] GetSessionArrayEntryTable() { return this.sessionMgr().getSessionArrayEntryTable(); }

    protected FabricLink(char client_type_val, String my_name_val) {
        this.debug(false, "FabricLink", "init start");

        this.clientType_ = client_type_val;
        this.myName_ = my_name_val;

        this.pendingSessionSetupQueue_ = new ListQueue(false, 0);
        this.pendingSessionSetupQueue3_ = new ListQueue(false, 0);
        this.sessionMgr_ = new FabricLSessionMgr(this);
    }

    public void bindListEntry(ListEntry list_entry_object_val, String who_val) {
        this.listEntry_ = list_entry_object_val;
    }

    public void unBindListEntry(String who_val) {
        this.listEntry_ = null;
    }

    protected FabricSession mallocSession() {
        return this.sessionMgr().mallocSession();
    }

    protected void setPendingSessionSetup(String link_session_id_str_val, String theme_data_val) {
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

    protected void setPendingSessionSetup3(String browser_theme_id_str_val, String session_id_str_val, String theme_data_val) {
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
    
    protected String getPendingSessionSetup() {
        return (String) this.pendingSessionSetupQueue_.dequeue();
    }

    protected String getPendingSessionSetup3() {
        return (String) this.pendingSessionSetupQueue3_.dequeue();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
