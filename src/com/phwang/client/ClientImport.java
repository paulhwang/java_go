/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.client;

import com.phwang.core.fabric.FabricExport;

public class ClientImport {
    protected static final String FABRIC_ANDROID_SERVER_IP_ADDRESS = "127.0.0.1";
    protected static final short FABRIC_ANDROID_PORT = FabricExport.FABRIC_ANDROID_PORT;

    public static final char CLIENT_IS_ANDROID = FabricExport.CLIENT_IS_ANDROID;

    protected static final char FABRIC_COMMAND_SETUP_LINK       = FabricExport.FABRIC_COMMAND_SETUP_LINK;
    protected static final char FABRIC_COMMAND_REMOVE_LINK      = FabricExport.FABRIC_COMMAND_REMOVE_LINK;
    protected static final char FABRIC_COMMAND_GET_LINK_DATA    = FabricExport.FABRIC_COMMAND_GET_LINK_DATA;
    protected static final char FABRIC_COMMAND_GET_NAME_LIST    = FabricExport.FABRIC_COMMAND_GET_NAME_LIST;
    protected static final char FABRIC_COMMAND_SETUP_SESSION    = FabricExport.FABRIC_COMMAND_SETUP_SESSION;
    protected static final char FABRIC_COMMAND_SETUP_SESSION2   = FabricExport.FABRIC_COMMAND_SETUP_SESSION2;
    protected static final char FABRIC_COMMAND_SETUP_SESSION3   = FabricExport.FABRIC_COMMAND_SETUP_SESSION3;
    protected static final char FABRIC_COMMAND_PUT_SESSION_DATA = FabricExport.FABRIC_COMMAND_PUT_SESSION_DATA;
    protected static final char FABRIC_COMMAND_GET_SESSION_DATA = FabricExport.FABRIC_COMMAND_GET_SESSION_DATA;
}
