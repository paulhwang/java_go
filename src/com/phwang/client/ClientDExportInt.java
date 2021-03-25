/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.client;

public interface ClientDExportInt {
    public void setupLink();
    public void removeLink();
    public void getLinkData();
    public void getNameList();
    public void setupSession();
    public void setupSession2();
    public void setupSession3();
    public void removeSession();
    public void putSessionData(String data_str_val);
    public void getSessionData();
}
