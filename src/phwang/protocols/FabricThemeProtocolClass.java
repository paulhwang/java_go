/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.protocols;

import phwang.fabric.FabricDefineClass;
import phwang.theme.ThemeDefineClass;

public class FabricThemeProtocolClass {
    public static final String FABRIC_THEME_PROTOCOL_SERVER_IP_ADDRESS = "127.0.0.1";
    public static final short FABRIC_THEME_PROTOCOL_TRANSPORT_PORT_NUMBER = 8009;

    public static final String FABRIC_THEME_PROTOCOL_COMMAND_IS_SETUP_ROOM = "R";
    public static final String FABRIC_THEME_PROTOCOL_RESPOND_IS_SETUP_ROOM = "r";
    public static final String FABRIC_THEME_PROTOCOL_COMMAND_IS_PUT_ROOM_DATA = "D";
    public static final String FABRIC_THEME_PROTOCOL_RESPOND_IS_PUT_ROOM_DATA = "d";
    
	public static final int THEME_ROOM_ID_SIZE = ThemeDefineClass.THEME_ROOM_ID_SIZE;

	public static final int FABRIC_LINK_ID_SIZE = FabricDefineClass.FABRIC_LINK_ID_SIZE;
    public static final int FABRIC_GROUP_ID_SIZE = FabricDefineClass.FABRIC_GROUP_ID_SIZE;
}
