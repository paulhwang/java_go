/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.utils;

public class QueueEntryClass {
    public QueueEntryClass next;
    public QueueEntryClass prev;
    public Object data;
    
    public void resetQueueEntry() {
    	this.next = null;
    	this.prev = null;
    	this.data = null;
    }
}
