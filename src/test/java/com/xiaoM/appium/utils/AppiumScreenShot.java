package com.xiaoM.appium.utils;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import com.google.common.io.Files;
import com.xiaoM.Common.Utils.Log;
import com.xiaoM.Common.Utils.TestListener;

import io.appium.java_client.AppiumDriver;

/**
 * 截图工具类
 * @author XiaoM
 *
 */
public class AppiumScreenShot{
	private AppiumDriver<WebElement> driver;
	private String CaseName;
	private String driverName;
	Log log =new Log(this.getClass());
	//收集错误信息用于生成测试报告
	
	public void setscreenName(String driverName,String CaseName){
		this.CaseName=CaseName;
		this.driverName = driverName;
	}
	public AppiumScreenShot(AppiumDriver<WebElement> driver){
		this.driver = driver;
	}
	private void takeScreenshot(String screenPath) {
            //appium在Chrome没法截图，需用原生app下进行截图(这里纯属为了WAP端可以进行截图)
            AppiumDriver<WebElement> driver2=((AppiumDriver<WebElement>) driver);
            driver2.context("NATIVE_APP");//切换到NATIVE_APP进行app截图
		try {
			File scrFile = ((TakesScreenshot) driver2).getScreenshotAs(OutputType.FILE);
			Files.copy(scrFile, new File(screenPath));
			log.error("错误截图："+screenPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void takeScreenshot() throws IOException {
		String screenName =this.driverName+"-"+this.CaseName+ ".jpg";
		File dir = new File("test-output/snapshot");
		if (!dir.exists()){
			dir.mkdirs();
			}
		String path = "../test-output/snapshot/"+screenName;
		TestListener.screenMessageList.add(path);
		String screenPath = dir.getAbsolutePath() + "/" + screenName;
		takeScreenshot(screenPath);
	}

}
