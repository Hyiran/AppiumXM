package com.xiaoM.appium.utils;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.xiaoM.Common.Utils.TestListener;

/**
 *  获取Android apk 包名和Activity
 * @author xiaoM
 *
 */
public class GetAppPA {
	
	public static String[] getPacknameAndActivity(String workspace,String appName){
		String path = workspace+"/apps";
		File file = new File(path);
		File[] tempList = file.listFiles();
		String[] PA =null;
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].getPath().contains(appName)) {
				PA = aaptDump(workspace,tempList[i].getPath());
			}
		}
		return PA;
	}
	
	public static String[] getPacknameAndActivity(){
		String path = TestListener.ProjectPath+"/apps";
		File file = new File(path);
		File[] tempList = file.listFiles();
		String[] PA =null;
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].getPath().contains(TestListener.appName)) {
				PA = aaptDump(TestListener.ProjectPath,tempList[i].getPath());
			}
		}
		return PA;
	}
	private static String[] aaptDump(String workspace,String path) {
		String aapt = null;
		if( System.getProperty("os.name").contains("Mac")){
			aapt = workspace+"/tools/aapt";
		}else{
			aapt = workspace+"/tools/aapt.exe";
		}
		ProcessBuilder pb1 = new ProcessBuilder(aapt, "dump", "badging", path);
		String[] PA = new String[2];
		String a =null;
		try {
			Process process =pb1.start();
			Scanner scanner = new Scanner(process.getInputStream());
			while (scanner.hasNextLine()) {
				a = scanner.nextLine();
				if(a.contains("package: name=")){
					int i = a.indexOf("'");
					int j = a.indexOf("' ");
					PA[0]= a.toString().substring(i+1,j);
				}else if(a.contains("launchable-activity:")){
					int i = a.indexOf("'");
					int j = a.indexOf("' ");
					PA[1] = a.toString().substring(i+1,j);  
				}
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return PA;
	}
}
