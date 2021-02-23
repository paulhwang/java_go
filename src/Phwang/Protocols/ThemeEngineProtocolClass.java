/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Protocols;

public class ThemeEngineProtocolClass {
    public static final String BASE_MGR_PROTOCOL_PROTOCOL_SERVER_IP_ADDRESS = "127.0.0.1";
    public static final short BASE_MGR_PROTOCOL_TRANSPORT_PORT_NUMBER = 8005;

    public static final int THEME_ROOM_ID_SIZE = 4;
    public static final int ENGINE_BASE_ID_SIZE = 4;

    public static final String THEME_ENGINE_PROTOCOL_COMMAND_IS_SETUP_BASE = "B";
    public static final String THEME_ENGINE_PROTOCOL_RESPOND_IS_SETUP_BASE = "b";
    public static final String THEME_ENGINE_PROTOCOL_COMMAND_IS_PUT_BASE_DATA = "D";
    public static final String THEME_ENGINE_PROTOCOL_RESPOND_IS_PUT_BASE_DATA = "d";
}
