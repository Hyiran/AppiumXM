package com.xiaoM.appium.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;

/**
 * @author xiaoM
 * @since 2017-03-30
 */
public class AppiumServerUtils {
	String ipAddress;
	int port;
	String bp;
	AppiumDriverLocalService service;
	
	static{
		try {
			setAppiumPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public AppiumServerUtils(){
	}
	public AppiumServerUtils(String ipAddress, int port,String bp){
		this.ipAddress = ipAddress;
		this.port = port;
		this.bp = bp;
	}
	
	public URL startAppiumServerByDefault() {
		AppiumDriverLocalService service = AppiumDriverLocalService.buildDefaultService();
		service.start();
		if (service == null || !service.isRunning()) {
			throw new RuntimeException("An appium server node is not started!");
		}
		return service.getUrl();
	}
	
	public URL startAppiumServerNoReset() {
		AppiumServiceBuilder builder = new AppiumServiceBuilder();
		AppiumDriverLocalService service = AppiumDriverLocalService.buildService(builder);
		service.start();
		if (service == null || !service.isRunning()) {
			throw new RuntimeException("An appium server node is not started!");
		}
		return service.getUrl();
	}

	public URL startServer() {
		AppiumServiceBuilder builder = new AppiumServiceBuilder();
		builder.withIPAddress(ipAddress);
		builder.usingPort(port);
		builder.withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER, bp);
		service = AppiumDriverLocalService.buildService(builder);
		service.start();
		if (service == null || !service.isRunning()) {
			throw new RuntimeException("An appium server node is not started!");
		}
		return service.getUrl();
	}
	public AppiumDriverLocalService stopServer(){
		if(service!=null){
			return service;
		}
		return null;
	}
	
	public static void  setAppiumPath() throws IOException{
		String os = System.getProperty("os.name");
		if(os.contains("Mac")){// 从配置文件dbinfo.properties中读取配置信息
			Properties pp = new Properties();
			FileInputStream fis = new FileInputStream("config.properties");
            pp.load(fis);
            String appiumPath = pp.getProperty("APPIUM_JS_PATH");
            String adbPath = pp.getProperty("ADB_PATH");
            System.setProperty(AppiumServiceBuilder.APPIUM_PATH , appiumPath);
            AppiumComm.adb = adbPath;
		}
	}
	public static void main(String[] args) throws IOException {
		setAppiumPath();
	}
}
