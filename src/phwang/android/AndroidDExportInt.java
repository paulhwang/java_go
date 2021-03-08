/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.android;

public interface AndroidDExportInt {
    public void setupLink(String name_val, String password_val);
    public void removeLink(String link_id_val);
    public void getLinkData(String link_id_val);
    public void getNameList(String link_id_val);
    public void setupSession(String link_id_val);
    public void setupSession2(String link_id_val);
    public void setupSession3(String link_id_val);
    public void removeSession(String link_id_val, String session_id_val);
    public void putSessionData(String link_id_val, String session_id_val);
    public void getSessionData(String link_id_val, String session_id_val);
}
