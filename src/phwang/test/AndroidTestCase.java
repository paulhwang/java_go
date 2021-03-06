/*  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.test;

import phwang.utils.Abend;
import phwang.utils.ThreadMgr;
import phwang.utils.ThreadInterface;
import phwang.utils.EncodeNumber;

class AndroidTestCase implements ThreadInterface {
    private String objectName() {return "AndroidTestCase";}
    private String androidTestThreadName() { return "AndroidTestThread"; }
    
    private AndroidTest androidTest_;
    private String indexString_;
    private String myNameString_;
    
    private AndroidTest androidTest() { return this.androidTest_; }
    private ThreadMgr threadMgr() { return this.androidTest().threadMgr();}

    public AndroidTestCase(AndroidTest android_test_val, int index_val) {
        this.debug(false, "AndroidTestCase", "init start");
        
        this.androidTest_ = android_test_val;
        this.indexString_ = EncodeNumber.encodeNumber(index_val, 6);
        this.myNameString_ = "Test_" + this.indexString_;
    }
    
    public void startTestTest() {
    	this.threadMgr().createThreadObject(this.androidTestThreadName(), this);
     }

	public void threadCallbackFunction() {
		this.androidTest().incrementThreadCount();
		this.androidTestCaseThreadFunc();
		this.androidTest().decrementThreadCount();
	}
    
    private void androidTestCaseThreadFunc() {
        try {
        	Thread.sleep(100);
        }
        catch (Exception ignore) {}
    	
    }
     
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}

