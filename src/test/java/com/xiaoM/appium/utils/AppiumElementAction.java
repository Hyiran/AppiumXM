package com.xiaoM.appium.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.xiaoM.Common.Utils.Assertion;
import com.xiaoM.Common.Utils.Log;
import com.xiaoM.Common.Utils.TestListener;
import com.xiaoM.ExecuteScript.ExecuteScript;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;

/**
 * 解析操作核心类
 * @author XiaoM
 *
 */
public class AppiumElementAction{

	public Log log=new Log(this.getClass());
	private static Map<String, String> map = new HashMap<String, String>();
	public WebElement getElement(AppiumDriver<WebElement> driver,Object[] locator,String driverName){
		String[] control = locator[5].toString().split("::");
		log.info("设备： "+driverName+" "+"查找元素："+locator[3].toString()+" 方式 :  "+control[0]+" [ "+control[1]+" ]");
		WebElement webElement;
		switch (control[0]){
		case "ByXpath" :
			webElement=driver.findElement(By.xpath(control[1]));
			break;
		case "ById":
			webElement=driver.findElement(By.id(control[1]));
			break;
		case "ByCssSelector":
			webElement=driver.findElement(By.cssSelector(control[1]));
			break;
		case "ByName":
			webElement=driver.findElement(By.name(control[1]));
			break;
		case "ByClassName":
			webElement=driver.findElement(By.className(control[1]));
			break;
		case "ByLinkText":
			webElement=driver.findElement(By.linkText(control[1]));
			break;
		case "ByPartialLinkText":
			webElement=driver.findElement(By.partialLinkText(control[1]));
			break;
		case "ByTagName":
			webElement=driver.findElement(By.tagName(control[1]));
			break;
		default :
			webElement=driver.findElement(By.xpath(control[1]));
			break;

		}
		return webElement;
	}

//	/**
//	 * 通过定位信息获取一组元素
//	 * @param locator  元素locator
//	 * @return 返回 WebElement
//	 * @throws NoSuchElementException 找不到元素异常
//	 */
//	public List<WebElement> getElements(AppiumDriver<WebElement> driver,Object[] locator,String driverName){
//		String[] control = locator[5].toString().split("::");
//		log.info("设备： "+driverName+" "+"查找元素："+locator[3].toString()+" 方式 :  "+control[0]+" [ "+control[1]+" ]");
//		List<WebElement> webElements;
//		switch (control[0]){
//		case "ByXpath" :
//			webElements=driver.findElements(By.xpath(control[1]));
//			break;
//		case "ById":
//			webElements=driver.findElements(By.id(control[1]));
//			break;
//		case "ByCssSelector":
//			webElements=driver.findElements(By.cssSelector(control[1]));
//			break;
//		case "ByName":
//			webElements=driver.findElements(By.name(control[1]));
//			break;
//		case "ByClassName":
//			webElements=driver.findElements(By.className(control[1]));
//			break;
//		case "ByLinkText":
//			webElements=driver.findElements(By.linkText(control[1]));
//			break;
//		case "ByPartialLinkText":
//			webElements=driver.findElements(By.partialLinkText(control[1]));
//			break;
//		case "ByTagName":
//			webElements=driver.findElements(By.tagName(control[1]));
//			break;
//		default :
//			webElements=driver.findElements(By.xpath(control[1]));
//			break;
//		}
//		return webElements;
//	}

	/**
	 * 等待元素出现
	 * @param by
	 * @param timeout
	 * @return
	 */
	public WebElement waitForElement(final AppiumDriver<WebElement> driver,final Object[] locator,final String driverName,String sdkVersion){
		WebElement webElement=null;
		int i = Integer.valueOf(locator[6].toString());
		try {
			webElement=(new WebDriverWait(driver, i)).until(
					new ExpectedCondition<WebElement>() {
						@Override
						public WebElement apply(WebDriver dr) {
							return  getElement(driver,locator,driverName);
						}
					});
			return webElement;
		} catch (Exception e) {
			log.error("设备： "+driverName+" "+"【failed】  等待:"+i+"s 找不到元素："+locator[3].toString()+" 方式  "+locator[4].toString()+":[ "+locator[5].toString()+" ]");
			TestListener.messageList.add(driverName+"(版本："+sdkVersion+"):::"+"等待:"+i+"s 找不到元素："+locator[3].toString()+" 方式  "+locator[4].toString()+":[ "+locator[5].toString()+" ]");
			throw e;
		}
	}
//	/**
//	 * 查找一组元素
//	 * @param locator 元素定位信息
//	 * @return
//	 */
//	public List<WebElement>  waitForElements(final AppiumDriver<WebElement> driver,final Object[] locator,final String driverName,String sdkVersion){
//		List<WebElement>  webElements=null;
//		try {
//			webElements=(new WebDriverWait(driver, 20)).until(
//					new ExpectedCondition<List<WebElement>>() {
//						@Override
//						public List<WebElement> apply(WebDriver dr) {
//							List<WebElement> element = getElements(driver,locator,driverName);
//							return element;
//						}
//					});
//			return webElements;
//		} catch (Exception e) {
//			log.error("设备： "+driverName+" "+"【failed】  等待:"+20+"s 找不到元素："+locator[3].toString()+" 方式  "+locator[4].toString()+":[ "+locator[5].toString()+" ]");
//			TestListener.messageList.add(driverName+"(版本："+sdkVersion+"):::"+"等待:"+20+"s 找不到元素："+locator[3].toString()+" 方式  "+locator[4].toString()+":[ "+locator[5].toString()+" ]");
//			throw e;
//		}
//	}

	public void elementSelectForIndex(AppiumDriver<WebElement> driver,Object[] locator,String i,String driverName,String sdkVersion){
		WebElement webElement =  waitForElement(driver,locator,driverName,sdkVersion);
		int index = Integer.valueOf(i);
		Select select = new Select(webElement);
		try {
			select.selectByIndex(index);
			log.info("设备： "+driverName+" "+"选择操作：Index="+i);
		} catch (Exception e) {
			log.error("设备： "+driverName+" "+"复选框选择失败   方式: [ index ] 值： [ "+locator[8].toString()+" ]");
			TestListener.messageList.add(driverName+"(版本："+sdkVersion+"):::"+"复选框选择失败   方式: [ index ] 值： [ "+locator[8].toString()+" ]");
			throw e;
		}
	}
	public void elementSelectForText(AppiumDriver<WebElement> driver,Object[] locator,String text,String driverName,String sdkVersion){
		WebElement webElement =  waitForElement(driver,locator,driverName,sdkVersion);
		Select select = new Select(webElement);
		try {
			select.selectByVisibleText(text);
			log.info("设备： "+driverName+" "+"选择操作：Text="+text);
		} catch (Exception e) {
			log.error("设备： "+driverName+" "+"复选框选择失败   方式: [ text ] 值： [ "+locator[8].toString()+" ]");
			TestListener.messageList.add(driverName+"(版本："+sdkVersion+"):::"+"复选框选择失败   方式: [ text ] 值： [ "+locator[8].toString()+" ]");
			throw e;
		}
	}
	public void elementSelectForValue(AppiumDriver<WebElement> driver,Object[] locator,String value,String driverName,String sdkVersion){
		WebElement webElement =  waitForElement(driver,locator,driverName,sdkVersion);
		Select select = new Select(webElement);
		try {
			select.selectByValue(value);
			log.info("设备： "+driverName+" "+"选择操作：Value="+value);
		} catch (Exception e) {
			log.error("设备： "+driverName+" "+"复选框选择失败   方式: [ value ] 值： [ "+locator[8].toString()+" ]");
			TestListener.messageList.add(driverName+"(版本："+sdkVersion+"):::"+"复选框选择失败   方式: [ value ] 值： [ "+locator[8].toString()+" ]");
			throw e;
		}
	}
	/**
	 *  下拉选择操作
	 * @param locator
	 */
	public void elementSelect(AppiumDriver<WebElement> driver,Object[] locator,String driverName,String sdkVersion){
		String[] Selects = locator[8].toString().split("=");
		switch (Selects[0].toString()){
		case "Index":
			elementSelectForIndex(driver,locator,Selects[1],driverName,sdkVersion);
			break;
		case "Text":
			elementSelectForText(driver,locator,Selects[1],driverName,sdkVersion);
			break;
		case "Value":
			elementSelectForValue(driver,locator,Selects[1],driverName,sdkVersion);
			break;
		}
	}
	/**
	 * 输出控件信息
	 * @param driver
	 * @param locator
	 */
	private void elementExport(AppiumDriver<WebElement> driver, Object[] locator,String driverName,String sdkVersion) {
		WebElement webElement =  waitForElement(driver,locator,driverName,sdkVersion);
		String text = null;
		try {
			text = webElement.getText();
			log.info("设备： "+driverName+" "+"输出数据为： "+text);
			map.put(locator[2].toString(), text);
		} catch (Exception e) {
			log.error("设备： "+driverName+" "+"获取数据失败！");
			TestListener.messageList.add(driverName+"(版本："+sdkVersion+"):::"+"获取数据失败！");
			throw e;
		}		
	}

	private void elementIncoming(AppiumDriver<WebElement> driver, Object[] locator,String driverName,String sdkVersion) {
		WebElement webElement =  waitForElement(driver,locator,driverName,sdkVersion);
		String text =null;
		try {
			text = map.get(locator[8].toString()).toString();
			log.info("设备： "+driverName+" "+"控件传入数据： [ "+ text +" ]");
			webElement.sendKeys(text);
		} catch (Exception e) {
			log.error("设备： "+driverName+" "+"控件传入数据失败！");
			TestListener.messageList.add(driverName+"(版本："+sdkVersion+"):::"+"控件传入数据失败，传入数据为："+text);
			throw e;
		}	
	}
	/**
	 * 元素操作
	 * @param locator
	 */
	public void elementOperation(AppiumDriver<WebElement> driver,Object[] locator,String driverName,String sdkVersion){
		WebElement webElement =  waitForElement(driver,locator,driverName,sdkVersion);
		switch (locator[7].toString()){
		case "sendKeys" :
			try {
				log.info("设备： "+driverName+" "+"输入："+ locator[8].toString());
				webElement.sendKeys(locator[8].toString());
			} catch (Exception e) {
				log.error("设备： "+driverName+" "+"控件输入失败！");
				TestListener.messageList.add(driverName+"(版本："+sdkVersion+"):::"+"控件输入失败，输入数据为："+locator[8].toString());
				throw e;
			}
			break;
		case "click" :
			try {
				log.info("设备： "+driverName+" "+"点击控件");
				webElement.click();
			} catch (Exception e) {
				log.error("设备： "+driverName+" "+"点击控件失败！");
				TestListener.messageList.add(driverName+"(版本："+sdkVersion+"):::"+"点击控件失败！");
				throw e;
			}
			break;
		case "longPress":
			try {
				log.info("设备： "+driverName+" "+"长按控件");
				TouchAction touchAction=new TouchAction(driver);
				touchAction.longPress(waitForElement(driver,locator,driverName,sdkVersion));	
			} catch (Exception e) {
				log.error("设备： "+driverName+" "+"长按控件失败！");
				TestListener.messageList.add(driverName+"(版本："+sdkVersion+"):::"+"长按控件失败！");
				throw e;
			}
			break;
		case "verity":
			String actualText = webElement.getText();
			Assertion.Verity(locator[9].toString(),actualText, locator[10].toString(),driverName,sdkVersion);	
			break;
		case "select":
			elementSelect(driver,locator,driverName,sdkVersion);
			break;
		case "export":
			elementExport(driver,locator,driverName,sdkVersion);
			break;
		case "incoming":
			elementIncoming(driver,locator,driverName,sdkVersion);
			break;
		}
	}



	/**
	 * 坐标点击
	 * @param locator
	 */
	public void driverClick(AppiumDriver<WebElement> driver,Object[] locator,String driverName){
		String[] coordinate = locator[5].toString().split(",");
		log.info("设备： "+driverName+" "+"坐标点击："+coordinate[0]+","+ coordinate[1]);
		int x = Integer.valueOf(coordinate[0]);
		int y =Integer.valueOf(coordinate[1]);
		try {
			TouchAction tAction=new TouchAction(driver);
			tAction.press(x, y).perform();
		} catch (Exception e) {
			log.error("设备： "+driverName+" "+"坐标点击失败：[ "+coordinate[0]+","+ coordinate[1]+" ]");
		}
	}

	/**
	 * 坐标滑动
	 * @param startx
	 * @param starty
	 * @param endx
	 * @param endy
	 */
	@SuppressWarnings("deprecation")
	public void driverSwipe(AppiumDriver<WebElement> driver,Object[] locator,String driverName){
		String[] coordinates = locator[5].toString().split(":");
		String[] A = coordinates[0].split(",");
		String[] B = coordinates[1].split(",");
		int startx = Integer.valueOf(A[0]);
		int starty =Integer.valueOf(A[1]);
		int endx =Integer.valueOf(B[0]);
		int endy =Integer.valueOf(B[1]);
		log.info("设备： "+driverName+" "+"坐标滑动：开始坐标：[ "+ A[0] +","+ A[1] +" ] "+"结束坐标：[ "+ B[0] +","+ B[1] +" ]");
		try {
			driver.swipe(startx, starty, endx, endy, 500);
		} catch (Exception e) {
			log.error("设备： "+driverName+" "+"坐标滑动失败：开始坐标：[ "+ A[0] +","+ A[1] +" ] "+"结束坐标：[ "+ B[0] +","+ B[1] +" ]");
		}	
	}
	/**
	 * 坐标拖拽
	 * @param startx
	 * @param starty
	 * @param endx
	 * @param endy
	 */
	public void driverDragAndDrop(AppiumDriver<WebElement> driver,Object[] locator,String driverName){
		String[] coordinates = locator[5].toString().split(":");
		String[] A = coordinates[0].split(",");
		String[] B = coordinates[1].split(",");
		int startx = Integer.valueOf(A[0]);
		int starty =Integer.valueOf(A[1]);
		int endx =Integer.valueOf(B[0]);
		int endy =Integer.valueOf(B[1]);
		TouchAction tAction=new TouchAction(driver);
		try {
			tAction.longPress(startx, starty).moveTo(endx, endy).release().perform();
			log.info("设备： "+driverName+" "+"坐标拖拽：开始坐标：[ "+ startx +","+ starty +" ] "+"结束坐标： [ "+ endx +","+ endy +" ]");
		} catch (Exception e) {
			log.error("设备： "+driverName+" "+"坐标拖拽失败  开始坐标：[ "+ startx +","+ starty +" ] "+"结束坐标： [ "+ endx +","+ endy +" ]");
			TestListener.messageList.add("坐标拖拽失败  开始坐标：[ "+ startx +","+ starty +" ] "+"结束坐标： [ "+ endx +","+ endy +" ]");
			throw e;
		}	
	}
	/**
	 * 坐标长按
	 * @param locator
	 */
	public void driverLongPress(AppiumDriver<WebElement> driver,Object[] locator,String driverName){
		String[] coordinates = locator[5].toString().split(":");
		int x = Integer.valueOf(coordinates[0]);
		int y =Integer.valueOf(coordinates[1]);
		log.info("设备： "+driverName+" "+"坐标长按： [ "+ x +","+ y +" ] ");
		try {
			TouchAction tAction=new TouchAction(driver);
			tAction.longPress(x, y).release().perform();
		} catch (Exception e) {
			log.error("设备： "+driverName+" "+"坐标长按： [ "+ x +","+ y +" ] 失败");
			TestListener.messageList.add("坐标长按： [ "+ x +","+ y +" ] 失败");
			throw e;
		}
	}

	/**
	 *  坐标选择操作
	 * @param locator
	 */
	public void coordinatesOperation(AppiumDriver<WebElement> driver,Object[] locator,String driverName){
		switch (locator[7].toString()){
		case "click":
			driverClick(driver,locator,driverName);
			break;
		case "swipe":
			driverSwipe(driver,locator,driverName);
			break;
		case "dragAndDrop":
			driverDragAndDrop(driver,locator,driverName);
			break;
		case "longPress":
			driverLongPress(driver,locator,driverName);
			break;
		}
	}
	/**
	 * 显式等待，程序休眠暂停
	 * @param time 以秒为单位
	 */
	public void sleep(long time){
		try {
			Thread.sleep(time*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void SimulateAction(Object[] locator,String deviceId,String driverName,String sdkVersion){
		ProcessBuilder pb = new ProcessBuilder(AppiumComm.adb, "-s",deviceId ,"shell", "input","keyevent",locator[5].toString());
		try {
			log.info("设备： "+driverName+" "+locator[3].toString());
			pb.start();
			sleep(1);
		} catch (Exception e) {
			log.error("设备： "+driverName+" "+locator[3].toString()+"失败");
			TestListener.messageList.add(driverName+"(版本："+sdkVersion+"):::"+locator[3].toString()+"失败");
		}
	}
	
	private void ExecuteScript(AppiumDriver<WebElement> driver,Object[] locator,String driverName,String sdkVersion){
		log.info("设备： "+driverName+" "+"执行外部脚本："+locator[5].toString());
		try {
			ExecuteScript execute = new ExecuteScript(driver);
			execute.doRun(locator[5].toString());
		} catch (Exception e) {
			log.error("设备： "+driverName+" "+"执行外部脚本："+locator[5].toString()+"失败");
			TestListener.messageList.add(driverName+"(版本："+sdkVersion+"):::"+"执行外部脚本："+locator[5].toString()+"失败");
		}
	}
	
	private void HybrisAPP(AppiumDriver<WebElement> driver,Object[] locator) {
		if(locator[1].toString().contains("WEBVIEW")||locator[1].toString().contains("NATIVE_APP")){
			Set<String> contextNames = driver.getContextHandles();
			for (String contextName : contextNames) {
				if (contextName.contains(locator[1].toString())){
					driver.context(contextName);
					break;
				}
			} 
		}	
	}

	public void executeAppiumAction(AppiumDriver<WebElement> driver,Object[] locator,String deviceId,String driverName,String sdkVersion) throws Exception{
		HybrisAPP(driver,locator);
		switch (locator[4].toString()){
		case "Sleep":
			int time = Integer.valueOf(locator[5].toString());
			log.info("设备： "+driverName+" "+locator[3].toString());
			sleep(time);
			break;
		case "Coordinate":
			coordinatesOperation(driver,locator,driverName);
			break;
		case "Script":
			ExecuteScript(driver,locator,driverName,sdkVersion);
			break;
		case "OpenURL":
			log.info("设备： "+driverName+" "+"打开URL:"+ locator[5].toString());
			driver.get(locator[5].toString());
			break;
		case "Control":
			elementOperation(driver,locator,driverName,sdkVersion);
			break;
		case "Simulate":
			SimulateAction(locator,deviceId,driverName,sdkVersion);
			break;
		}
	}
}
