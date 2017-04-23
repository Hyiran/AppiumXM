package com.xiaoM.appium.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.xiaoM.Common.Utils.IOMananger;
import com.xiaoM.Common.Utils.TestListener;


public class CpuThread extends Thread {
	int cpuForThisTime;
	String appPackage;
	String device;
	String driverName;
	SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	String date;
	public CpuThread( String appPackage,String device,String driverName) {
		this.appPackage = appPackage;
		this.device = device;
		this.driverName = driverName;
	}

	@Override
	public void run() {
		String workSpace = TestListener.ProjectPath+"/test-output/MonitorResoure/Cpu";
		while(true){
			try {
				Thread.sleep(50);
				cpuForThisTime = getCpu(appPackage,device);
				date =dateFormat.format(new Date()).toString();
				IOMananger.saveToFile(workSpace, driverName, String.valueOf(cpuForThisTime+" "+date));
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 读取手机CPU数据
	 * @param app要测试的应用的包
	 * @return 返回该次的CPU百分比
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static int getCpu(String appPackage,String device) throws IOException, InterruptedException {
		// 读取属于应用的那一行CPU数据
		String line = null;
		// 将line的数据分类
		String[] splitArr = null;
		// 拿出百分比的那一个数据
		String per;
		// 将百分比的百分号去掉 方便转换
		String pernum;
		// 去除百分比的数字
		int perint;
		ProcessBuilder pb = new ProcessBuilder(AppiumComm.adb, "-s",device,"shell", "top -d 1 -m 10 -n 1");
		Process p = pb.start();
		BufferedReader bf = new BufferedReader(new InputStreamReader(p.getInputStream(), "utf-8"));
		while ((line = bf.readLine()) != null) {
			if (line.contains(appPackage)) {
				splitArr = line.split("  ");// 根据两个空格的间隔进行分割
				for (int j = 0; j < splitArr.length; j++) {
					if (splitArr[j].contains("%")) {
						// 将后面的R S去掉（R for Running , S for Sleeping）
						per = splitArr[j].split(" ")[0];
						// 去掉百分号
						pernum = per.replace("%", "");
						// 若取值为空（一般不为空 保险操作）
						if (pernum.equals("") || pernum.isEmpty()) {
							return 0;
						}
						// 转换成数字并返回
						perint = Integer.parseInt(pernum);
						return perint;
					}
				}
			}
		}
		return 0;
	}
}
