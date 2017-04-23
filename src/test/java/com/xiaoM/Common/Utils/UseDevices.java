package com.xiaoM.Common.Utils;

import java.util.List;

public class UseDevices {
	static List<String> devices;
	static String device;
	
	public synchronized static String Device (){
		devices = TestListener.deviceLists;
		device = devices.get(0);
		devices.remove(0);
		return device;	
	}
}
