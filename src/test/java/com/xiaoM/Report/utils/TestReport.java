package com.xiaoM.Report.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.xiaoM.Common.Utils.IOMananger;
import com.xiaoM.Common.Utils.TestListener;

public class TestReport implements IReporter {

	private static final String OUTPUT_FOLDER = "test-output/";
	private static final String FILE_NAME = "TestReport.html";
	private ExtentReports extent;

	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {     
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		String date=dateFormat.format(new Date()).toString();//获取当前日期
		try {
			IOMananger.DealwithLog(TestListener.ProjectPath, date);
		} catch (IOException e) {
		}
		init(TestListener.ReportTile);//html文件配置
		for (ISuite suite : suites) {
			Map<String, ISuiteResult>  result = suite.getResults(); 
			for (ISuiteResult r : result.values()) {
				ITestContext context = r.getTestContext();              
				buildTestNodes(context.getFailedTests(), Status.FAIL);
				/* 
				 * 需要展示Skip用例执行该行代码
				 * buildTestNodes(context.getSkippedTests(), Status.SKIP);
				 */
				buildTestNodes(context.getPassedTests(), Status.PASS);

			}
		}     
		for (String s : Reporter.getOutput()) {
			extent.setTestRunnerOutput(s);
		}
		extent.flush();
	}  
	private void init(String ReportName) {
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(OUTPUT_FOLDER + FILE_NAME);
		htmlReporter.config().setDocumentTitle("AppiumXM designed by xiaoM");//html标题
		htmlReporter.config().setReportName(ReportName);//报告主题
		htmlReporter.config().setTheme(Theme.STANDARD);//主题：黑/白
		htmlReporter.config().setEncoding("utf-8");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter); 
		extent.setSystemInfo("OS", System.getProperty("os.name"));
		extent.setSystemInfo("User Name",System.getProperty("user.name"));
		extent.setSystemInfo("Java Version", System.getProperty("java.version"));
		extent.setSystemInfo("Appium Version", "1.6.3");
		extent.setReportUsesManualConfiguration(true);
	}  
	private void buildTestNodes(IResultMap tests, Status status) {
		ExtentTest test =null;
		String DeviceName = null;
		if (tests.size() > 0) {
			int i = 0;
			int j = 0;
			for (ITestResult result : tests.getAllResults()) {
				switch (result.getStatus()) {
				case 1://成功用例
					if(TestListener.TestType.equals("RM")){
						DeviceName = TestListener.mobileSuccessMessageList.get(i);
						String CaseName = result.getParameters()[1].toString();
						test = extent.createTest(DeviceName+"-"+CaseName);
						test.assignCategory(DeviceName);
						test.log(status, TestListener.ResourceList.get(i).split(":::")[1]);
						String logName = DeviceName.split("\\(")[0];
						test.log(status, "<span><a href=\"../test-output/log/DevicesRunLog/"+logName+".txt\">执行日志</a></span>");
						try {
							test.addScreenCaptureFromPath("../test-output/snapshot/"+logName+"_CPU.jpg");
							test.addScreenCaptureFromPath("../test-output/snapshot/"+logName+"_Men.jpg");
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}else{
						DeviceName = TestListener.mobileSuccessMessageList.get(i);
						String CaseName1 = result.getParameters()[1].toString();
						test = extent.createTest(DeviceName+"-"+CaseName1);
						String DeviceName2 = DeviceName.split("\\(")[0];
						String txtName1 = DeviceName2+"_"+CaseName1;
						test.assignCategory(DeviceName);
						try {
							IOMananger.DealwithSuRunLog(TestListener.ProjectPath,DeviceName2, CaseName1);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						test.log(status, "<span><a href=\"../test-output/log/DevicesRunLog/"+txtName1+".txt\">执行日志</a></span>");
						test.log(status, "Test " + status.toString().toLowerCase() + "ed");
					}
					i++;
					break;
				case 2://失败用例
					String[] testDevice = TestListener.messageList.get(j).split(":::");//获取设备测试资料
					String CaseName2 = result.getParameters()[1].toString();
					DeviceName = testDevice[0].split("\\(")[0];
					String txtName2 = DeviceName+"_"+CaseName2;
					test = extent.createTest(testDevice[0]+"-"+CaseName2);
					test.assignCategory(testDevice[0]);
					try {
						IOMananger.DealwithFailRunLog(TestListener.ProjectPath,DeviceName, CaseName2);//只处理执行失败的用例对应的设备运行日志
						test.fail("报错截图：",MediaEntityBuilder.createScreenCaptureFromPath(TestListener.screenMessageList.get(j)).build());
					} catch (IOException e) {
						e.printStackTrace();
					}
					test.log(status, "<span><a href=\"../test-output/log/DevicesRunLog/"+txtName2+".txt\">执行日志</a></span>");
					test.log(status, testDevice[1]);  //添加自定义报错 
					test.log(status, result.getThrowable()); //testng捕抓报错 
					j++;
					break;
				}
				test.getModel().setStartTime(getTime(result.getStartMillis()));
				test.getModel().setEndTime(getTime(result.getEndMillis()));
			}
			
		}
	}

	private Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();      
	}
}
