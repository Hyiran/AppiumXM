package com.xiaoM.ExecuteScript;

import java.lang.reflect.InvocationTargetException;

import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;

public class ExecuteScript {
	public AppiumDriver<WebElement> driver;
	
	public  ExecuteScript(){
	}
	
	public  ExecuteScript(AppiumDriver<WebElement> driver) {
		this.driver = driver;
	}
	/**
	 * 执行指定的方法
	 * @param MethodName 方法名
	 */
	public void doRun(String MethodName){
		Class< ? extends ExecuteScript> run = this.getClass();
		try {	 
			run.getMethod(MethodName).invoke(this); 
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {	 
			e.printStackTrace();
		} catch (IllegalAccessException e) {	 
			e.printStackTrace();
		} catch (InvocationTargetException e) {	 
			e.printStackTrace();
		} catch (NoSuchMethodException e) {		 
			e.printStackTrace();
		}
	}

	public void Demo(){
		System.out.println("********************");
		System.out.println("*      test        *");
		System.out.println("********************");
	}
}
