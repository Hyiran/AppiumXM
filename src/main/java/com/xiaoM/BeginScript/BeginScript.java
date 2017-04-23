package com.xiaoM.BeginScript;

import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xiaoM.Common.Utils.Log;
import com.xiaoM.Common.Utils.Run;
import com.xiaoM.Common.Utils.TestListener;
import com.xiaoM.Common.Utils.UseDevices;

public class BeginScript{
	public Log log=new Log(this.getClass());
	String device = UseDevices.Device();//获取运行设备
	
	@DataProvider
	public Object[][]Testcases() throws IOException{
		return TestListener.RunCase;
	}
	
	@Test(dataProvider = "Testcases")
	public  void run(String CaseType,String CaseName) throws Exception{
		Run run = new Run();
		run.testCase(CaseType,CaseName,device);
	}
}
