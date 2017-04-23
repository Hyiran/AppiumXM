package com.xiaoM.Common.Utils;

import java.io.IOException;

/**
 * 公共调用类
 * @author XiaoM
 *
 */
public class CommonUtils {
	/**
	 * 执行cmd命令
	 */
	public static void executeCmd(String cmd) throws IOException {
		Runtime runtime=Runtime.getRuntime();
		runtime.exec("cmd /c start "+cmd);
	}

	/**
	 * 显式等待，程序休眠暂停
	 * @param time 以秒为单位
	 */
	public static void sleep(long time){
		try {
			Thread.sleep(time*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}