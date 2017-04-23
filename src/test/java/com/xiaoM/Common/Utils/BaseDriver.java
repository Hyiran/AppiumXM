package com.xiaoM.Common.Utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.xiaoM.appium.utils.AppiumServerUtils;
import com.xiaoM.appium.utils.GetAppPA;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;

public class BaseDriver {
	public Log log=new Log(this.getClass());
	AppiumDriver <WebElement> driver ;
	AppiumServerUtils  AppiumServer = null;
	public URL url;
	public AppiumDriver<WebElement> setUpApp(String device,String devicesPath) throws IOException {
		String driverName = null;
		try {
			Object[][] testBase = IOMananger.readExcelData(device,devicesPath);
			driverName = testBase[1][2].toString();
			String nodeURL =testBase[2][2].toString();
			String platformName =testBase[3][2].toString();
			String deviceId =testBase[4][2].toString();
			String deviceName =testBase[5][2].toString();
			String sdkVersion =testBase[6][2].toString();
			String bootstrapPort = testBase[8][2].toString();
			log.info("设备： "+driverName+" "+"开始执行测试");
			log.info("设备： "+driverName+" "+"启动appium server");
			try {	
				if(nodeURL.equals("")||nodeURL.isEmpty()){
					log.error("设备： "+driverName+" "+"appium url 没有配置");
				}else {
					log.info("设备： "+driverName+" "+"配置信息：Mobile Driver:"+driverName);
					log.info("设备： "+driverName+" "+"Appium Server:"+"http://"+nodeURL+"/wd/hub");
					log.info("设备： "+driverName+" "+"设备Id："+deviceId);
					DesiredCapabilities capabilities = new DesiredCapabilities();
					if(platformName.equals("Android")){
						AppiumServer = new AppiumServerUtils(nodeURL.split(":")[0], Integer.valueOf(nodeURL.split(":")[1]),bootstrapPort);
						url = AppiumServer.startServer();
						File appDir=new File(TestListener.ProjectPath,"apps");
						File app =new File(appDir,TestListener.appName);
						String[] PA = GetAppPA.getPacknameAndActivity(TestListener.ProjectPath,TestListener.appName);
						String appMainPackage =PA[0];
						String appActivity =PA[1];
						capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.APPIUM);
						capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
						capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
						capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, sdkVersion);
						capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
						capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, appMainPackage);
						capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, appActivity);
						capabilities.setCapability("udid", deviceId);
						capabilities.setCapability("unicodeKeyboard", "True");
						capabilities.setCapability("resetKeyboard", "True");
						capabilities.setCapability("noSign", "True");	
						driver = new AppiumDriver<WebElement>(url, capabilities);
					}else{
						String bundleId = testBase[9][2].toString();
						capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
						capabilities.setCapability("platformName", platformName);
						capabilities.setCapability("platformVersion", sdkVersion);
						capabilities.setCapability("deviceName", deviceName);
						capabilities.setCapability(MobileCapabilityType.UDID, deviceId);
						capabilities.setCapability("bundleId",bundleId);
						driver = new AppiumDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
					}	
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("设备： "+driverName+" "+"appium环境配置失败");
			}
		} catch (Exception e) {
			log.error("设备： "+driverName+" "+"读取TestBase配置文件失败");
			e.printStackTrace();
		}
		return driver;
	}


	public AppiumDriver<WebElement> setUpWap(String device,String devicesPath) throws IOException {
		String driverName =null;
		try {
			Object[][] testBase = IOMananger.readExcelData(device,devicesPath);
			driverName = testBase[1][2].toString();
			String nodeURL =testBase[2][2].toString();
			String Browser =testBase[7][2].toString();
			String platformName =testBase[3][2].toString();
			String deviceId =testBase[4][2].toString();
			String deviceName =testBase[5][2].toString();
			String sdkVersion =testBase[6][2].toString();
			String bootstrapPort = testBase[8][2].toString();
			log.info("设备： "+driverName+" "+"开始执行测试");
			log.info("设备： "+driverName+" "+"启动appium server");
			try {
				AppiumServerUtils  AppiumServer = new AppiumServerUtils(nodeURL.split(":")[0], Integer.valueOf(nodeURL.split(":")[1]),bootstrapPort);
				url =AppiumServer.startServer();
				if(nodeURL.equals("")||nodeURL.isEmpty()){
					log.error("设备： "+driverName+" "+"appium url 没有配置");
				}else {
					log.info("设备： "+driverName+" "+"配置信息：Mobile Driver:"+driverName);
					log.info("设备： "+driverName+" "+"Appium Server:"+"http://"+nodeURL+"/wd/hub");
					log.info("设备： "+driverName+" "+"设备Id："+deviceId);
					DesiredCapabilities capabilities = new DesiredCapabilities();
					capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.APPIUM);
					capabilities.setCapability(CapabilityType.BROWSER_NAME, Browser);// Browser
					capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
					capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
					capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, sdkVersion);
					capabilities.setCapability("unicodeKeyboard", "True");
					capabilities.setCapability("resetKeyboard", "True");
					driver = new AndroidDriver<WebElement>(new URL("http://"+nodeURL+"/wd/hub"), capabilities);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("设备： "+driverName+" "+"appium环境配置失败");
			}
		} catch (Exception e) {
			log.error("设备： "+driverName+" "+"读取TestBase配置文件失败");
			e.printStackTrace();
		}
		return driver;
	}
}
