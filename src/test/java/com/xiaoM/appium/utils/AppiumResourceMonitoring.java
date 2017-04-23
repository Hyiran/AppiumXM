package com.xiaoM.appium.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.xiaoM.Common.Utils.CommonUtils;
import com.xiaoM.Common.Utils.IOMananger;
import com.xiaoM.Common.Utils.Log;
import com.xiaoM.Common.Utils.TestListener;

import io.appium.java_client.AppiumDriver;

public class AppiumResourceMonitoring {
	public Log log=new Log(this.getClass());
	static String[] luanch = null;//存放测试应用包名和Activity
	DecimalFormat df =new DecimalFormat("0.00");//格式化数值，保留两位小数
	public void driverStartApp(AppiumDriver<WebElement> driver,String deviceId,String driverName) throws Exception {
		try {
			log.info("设备： "+driverName+" "+"启动资源监控器");
			String CpuPath = TestListener.ProjectPath+"/test-output/MonitorResoure/Cpu/"+driverName+".txt";
			String MenPath = TestListener.ProjectPath+"/test-output/MonitorResoure/Mem/"+driverName+".txt";
			String NetPath = TestListener.ProjectPath+"/test-output/MonitorResoure/Net/"+driverName+".txt";
			String[] Paths = {CpuPath,MenPath,NetPath};
			IOMananger.deleteFile(Paths);//删除监控日志文件
			luanch = GetAppPA.getPacknameAndActivity();
			AppiumComm.getMobileAppNet(luanch[0], deviceId,driverName);
			CpuThread cpuThread = new CpuThread(luanch[0], deviceId,driverName); // CPU监控线程1
			MemThread memThread = new MemThread(luanch[0], deviceId,driverName);//内存监控线程2
			cpuThread.start();// CPU监控线程启动
			memThread.start();// 内存监控线程启动
		} catch (Exception e) {
			log.error("设备： "+driverName+" "+"启动资源监控器失败设备："+driverName);
			throw e;
		}
	}

	public void driverStop(String deviceId,String driverName,String sdkVersion) throws Exception {
		AppiumComm.getMobileAppNet(luanch[0],deviceId,driverName);
		CommonUtils.sleep(10);//线程等待
		String CpuPath = TestListener.ProjectPath+"/test-output/MonitorResoure/Cpu/"+driverName+".txt";
		String MenPath = TestListener.ProjectPath+"/test-output/MonitorResoure/Mem/"+driverName+".txt";
		String NetPath = TestListener.ProjectPath+"/test-output/MonitorResoure/Net/"+driverName+".txt";
		List<Integer> cpuList = new ArrayList<Integer>();
		List<Double> menList = new ArrayList<Double>();
		List<Integer> NetList = new ArrayList<Integer>();
		String[] Men = null;
		String[] Cpu = null;
		AppiumComm.adbClearCache(luanch[0], deviceId);
		AppiumComm.forceStop(luanch[0], deviceId);
		CommonUtils.sleep(2);
		String appPackageActivity = luanch[0]+"/"+luanch[1];
		String luanchTime = AppiumComm.appLuanchTime(appPackageActivity, deviceId);
		try {
			int cpuMax  = 0;
			Double memMax = null;		
			List<String> Cpus = IOMananger.readTxtFile(CpuPath);
			int a;
			double k;
			for(int i=0;i<Cpus.size();i++) {
				Cpu = Cpus.get(i).split(" ");
				a = Integer.parseInt(Cpu[0]);
				cpuList.add(a);
			}
			List<String> Mens = IOMananger.readTxtFile(MenPath);
			for(int i=0;i<Mens.size();i++) {
				Men = Mens.get(i).split(" ");
				k = Double.valueOf(Men[0]);
				menList.add(k);
			}
			List<String> Nets = IOMananger.readTxtFile(NetPath);
			while(Nets.size()!=4){
				Nets = IOMananger.readTxtFile(NetPath);
			}
			for(String net : Nets) {
				int i = Integer.parseInt(net);
				NetList.add(i);
			}
			PieChartPicture picture = new PieChartPicture (driverName,CpuPath,MenPath);
			picture.createScreen();
			cpuMax = AppiumComm.cpuMaxComp(cpuList);
			memMax = AppiumComm.memMaxComp(menList);
			int cpuAvg = AppiumComm.cpuAvg(cpuList);
			double menAvg = AppiumComm.menAvg(menList);
			String netshangxing = df.format((double) (NetList.get(2)-NetList.get(0))/1024.0);
			String netxiaxing = df.format((double) (NetList.get(3)-NetList.get(1))/1024.0);
			log.info("设备： "+driverName+" "+"首次启动时延为：" + luanchTime +"ms");
			log.info("设备： "+driverName+" "+"执行业务时CPU峰值为：" + cpuMax +"%");
			log.info("设备： "+driverName+" "+"执行业务时内存峰值为：" + df.format(memMax)+"MB");
			log.info("设备： "+driverName+" "+"上行流量："+ netshangxing+"KB");
			log.info("设备： "+driverName+" "+"下行流量："+ netxiaxing+"KB");
			TestListener.ResourceList.add(driverName+"(版本："+sdkVersion+"):::"+"<table><tr BGCOLOR=\"#98FB98\"><td>指标</td><td>启动时延（S）</td><td>CPU 峰值（%）</td><td>CPU 均值（%）</td><td>内存峰值（MB）</td><td>内存均值（MB）</td><td>上行流量（KB）</td><td>下行流量（KB）</td></tr>"
					+ "<tr BGCOLOR=\"#F0FFF0\"><td>数值</td><td>" + luanchTime +"ms</td><td>" + cpuMax +"%</td><td>" + cpuAvg +"%</td><td>" + df.format(memMax)+"MB</td><td>" + df.format(menAvg)+"MB</td><td>"+ netxiaxing+"KB</td><td>"+netshangxing+"KB</td></tr></table>");
			log.info("设备： "+driverName+" "+"关闭资源监控器");
		} catch (Exception e) {
			log.error("设备： "+driverName+" "+"读取资源监控信息失败！");
			throw e;
		}
	}
}
