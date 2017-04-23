package com.xiaoM.Common.Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * IO类
 * @author XiaoM
 *
 */
public class IOMananger {
	/**
	 * 读取excel
	 * @param sheetName
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static Object[][] readExcelData(String sheetName,String path) throws IOException{
		Object[][] test = null;
		String[] xlsx = path.split("\\.");
		if(xlsx[1].toString().contains("xlsx")){
			test = readExcelDataXlsx(sheetName, path);
		}else{
			test = readExcelDataXls(sheetName, path);
		}
		return test;		
	}
	/**
	 * 定位Excel单元格,获取一行内容
	 * @param sheetName
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static List<String> checkExcelData(String cellContent) throws IOException{
		List<String> test = null;
		String[] xlsx = TestListener.CasePath.split("\\.");
		if(xlsx[1].toString().contains("xlsx")){
			test = checkExcelDataXlsx(cellContent);
		}else{
			test = checkExcelDataXls(cellContent);
		}
		return test;		
	}



	public static Object[][] readExcelDataXlsx(String sheetName,String path) throws IOException {
		InputStream is = new FileInputStream(path);
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(is);//读取Excel
		XSSFSheet sheet = workbook.getSheet(sheetName);//读取sheet
		if(sheet!=null){
			int lastrowNum = sheet.getLastRowNum()+1;//获取总行数
			int collNum = sheet.getRow(0).getLastCellNum();//获取列数	 
			Object[][] user = new Object[lastrowNum][collNum];
			for(int rowNum=0;rowNum<lastrowNum;rowNum++){
				XSSFRow row = sheet.getRow(rowNum);
				for(int j=0;j<collNum;j++){
					if(row.getCell(j)!=null){
						row.getCell(j).setCellType(CellType.STRING);	
						user[rowNum][j] = row.getCell(j).getStringCellValue();
					}
				}	
			}
			return user;
		}else{
			System.out.println(sheet+"is null!");
			return null;
		}	
	}
	public static Object[][] readExcelDataXls(String sheetName,String path) throws IOException {
		InputStream is = new FileInputStream(path);
		@SuppressWarnings("resource")
		HSSFWorkbook workbook = new HSSFWorkbook(is);//读取Excel
		HSSFSheet sheet = workbook.getSheet(sheetName);//读取sheet
		if(sheet!=null){
			int lastrowNum = sheet.getLastRowNum()+1;//获取总行数
			int collNum = sheet.getRow(0).getLastCellNum();//获取列数	 
			Object[][] user = new Object[lastrowNum][collNum];
			for(int rowNum=0;rowNum<lastrowNum;rowNum++){
				HSSFRow row = sheet.getRow(rowNum);
				for(int j=0;j<collNum;j++){
					if(row.getCell(j)!=null){
						row.getCell(j).setCellType(CellType.STRING);
						user[rowNum][j] = row.getCell(j).getStringCellValue();
					}
				}	
			}
			return user;
		}else{
			System.out.println(sheet+"is null!");
			return null;
		}	
	}

	@SuppressWarnings("deprecation")
	public static List<String> checkExcelDataXls( String cellContent) throws IOException {
		InputStream is = new FileInputStream(TestListener.CasePath);
		List<String> list = new LinkedList<String>();
		@SuppressWarnings("resource")
		HSSFWorkbook workbook = new HSSFWorkbook(is);//读取Excel
		HSSFSheet sheet = workbook.getSheet("TestCases");//读取sheet
		if(sheet!=null){
			for (Row row : sheet) {
				for (Cell cell : row) {
					if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
						if (cell.getRichStringCellValue().getString().trim().equals(cellContent)) {
							for(Cell cell2:row){
								list.add(cell2.toString());
							}
							return list; 
						}
					}
				}
			}             
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public static List<String> checkExcelDataXlsx( String cellContent) throws IOException {
		InputStream is = new FileInputStream(TestListener.CasePath);
		List<String> list = new LinkedList<String>();
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(is);//读取Excel
		XSSFSheet sheet = workbook.getSheet("TestCases");//读取sheet
		if(sheet!=null){
			for (Row row : sheet) {
				for (Cell cell : row) {
					if (cell.getCellTypeEnum() == CellType.STRING) {
						if (cell.getRichStringCellValue().getString().trim().equals(cellContent)) {
							for(Cell cell2:row){
								list.add(cell2.toString());
							}
							return list; 
						}
					}
				}
			}             
		}
		return null;
	}

	public static Object[][] runTime(String sheetName,String path) throws IOException{
		Object[][] Date =  readExcelData(sheetName,path);
		List<Object> caseType = new LinkedList<Object>();
		List<Object> caseName = new LinkedList<Object>();
		for(int i=1;i<Date.length;i++){
			if(Date[i][5].equals("YES")){
				caseType.add(Date[i][1]);
				caseName.add(Date[i][3]);	
			}
		}
		Object[][] runTime  = new Object[caseName.size()][2];
		for(int k =0;k<caseName.size();k++){
				runTime[k][0]=caseType.get(k);
				runTime[k][1]=caseName.get(k);
		}
		return runTime;
	}

	/**
	 * 写入数据到txt文本中
	 * @param fileDir 路径
	 * @param FileName	文件名
	 * @param conent
	 */
	public static void saveToFile(String fileDir,String FileName, String conent) {
		File destDir = new File(fileDir);
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
		String Path = fileDir +"/"+ FileName+".txt";
		BufferedWriter bw = null;
		try {
			/**
			 * 追加文件：使用FileOutputStream，在构造FileOutputStream时，把第二个参数设为true
			 * 清空重新写入：把第二个参数设为false
			 */
			FileOutputStream fo = new FileOutputStream(Path, true);
			OutputStreamWriter ow = new OutputStreamWriter(fo);
			bw = new BufferedWriter(ow);
			bw.append(conent);
			bw.newLine();
			bw.flush();
			bw.close();
		} catch (Exception e) {
			System.out.println("写入数据失败！！！");
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 功能：Java读取txt文件的内容
	 * 步骤：1：先获得文件句柄
	 * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
	 * 3：读取到输入流后，需要读取生成字节流
	 * 4：一行一行的输出。readline()。
	 * 备注：需要考虑的是异常情况
	 * @param filePath
	 * @throws FileNotFoundException 
	 */
	public static List<String> readTxtFile(String filePath) throws FileNotFoundException{
		List<String> txt = new ArrayList<String>();
		Scanner in = new Scanner(new File(filePath));  
        while(in.hasNext()){ 
        	String str=in.nextLine(); 
        	if(!str.isEmpty()){
        		txt.add(str.toString());
        	}
        }
        in.close();
		return txt;
	}
	
	/**
     * 删除文件夹下的所有文件
     * @param oldPath
     */
	public static void deleteFile(File Path) {
		if (Path.isDirectory()) {
			File[] files = Path.listFiles();
			for (File file : files) {
				deleteFile(file);
			}
		}else{
			Path.delete();
		}
	}
	/**
	 *判断电脑本地是否存在某文件，存在则删除
	 * @param path 文件路径
	 * @return
	 */
	public static void deleteFile(String[] Paths){
		for(String path:Paths){
			try {
				File file = new File(path);
				if(file.exists()){
					file.delete();
				}
			} catch (Exception e) {
				System.out.println("删除文件失败，文件路径："+ path);
			}
		}	
	}
	/**
	 * 测试日志分类
	 * @param workSpase
	 * @param data
	 * @throws IOException
	 */
	public static void DealwithLog(String workSpase,String date) throws IOException{
		String logPath = workSpase+"/test-output/log/log_"+date+".log";
		String logDriverPath = workSpase+"/test-output/log/DevicesRunLog";
		List<String>logDrivers = IOMananger.readTxtFile(logPath);
		String[] devices = TestListener.Devices.split(",");
		for(String device:devices){
			String logDevicePath = logDriverPath+"/"+device+".txt";
			File file = new File(logDevicePath);
			if(file.exists()){
				file.delete();
			}
			for(int i=0;i<logDrivers.size();i++){
				if(logDrivers.get(i).contains(device)){
					IOMananger.saveToFile(logDriverPath, device, logDrivers.get(i));
				}
			}
		}
	}
	/**
	 * 处理执行错误日志
	 * @param workSpase
	 * @param data
	 * @throws IOException
	 */
	public static void DealwithFailRunLog(String workSpase,String DeviceName,String CaseName) throws IOException{
		String logPath = workSpase+"/test-output/log/DevicesRunLog/"+DeviceName+".txt";
		String logDriverPath = workSpase+"/test-output/log/DevicesRunLog";
		List<String>logDrivers = IOMananger.readTxtFile(logPath);
		String isDevice = DeviceName;
		int start = 0;
		for(String logDriver:logDrivers){
			if(logDriver.contains(isDevice+" 执行用例："+CaseName)){
				start++;
				break;
			}
			start++;
		}
		String txtName = isDevice+"_"+CaseName;
		String logtxtNamePath = logDriverPath+"/"+txtName+".txt";
		File file = new File(logtxtNamePath);
		if(file.exists()){
			file.delete();
		}
		for(int i=start-1;i<logDrivers.size();i++){
			IOMananger.saveToFile(logDriverPath, txtName, logDrivers.get(i));
			if(logDrivers.get(i).contains( DeviceName+" 执行用例："+CaseName+"出错")){
				break;
			}
		}
	}	
	/**
	 * 处理执行成功日志
	 * @param workSpase
	 * @param data
	 * @throws IOException
	 */
	public static void DealwithSuRunLog(String workSpase,String DeviceName,String CaseName) throws IOException{
		String logPath = workSpase+"/test-output/log/DevicesRunLog/"+DeviceName+".txt";
		String logDriverPath = workSpase+"/test-output/log/DevicesRunLog";
		List<String>logDrivers = IOMananger.readTxtFile(logPath);
		String isDevice = DeviceName;
		int start = 0;
		for(String logDriver:logDrivers){
			if(logDriver.contains(isDevice+" 执行用例："+CaseName)){
				start++;
				break;
			}
			start++;
		}
		String txtName = isDevice+"_"+CaseName;
		String logtxtNamePath = logDriverPath+"/"+txtName+".txt";
		File file = new File(logtxtNamePath);
		if(file.exists()){
			file.delete();
		}
		for(int i=start-1;i<logDrivers.size();i++){
			IOMananger.saveToFile(logDriverPath, txtName, logDrivers.get(i));
			if(logDrivers.get(i).contains( DeviceName+" 执行用例："+CaseName+"成功")){
				break;
			}
		}
	}
	public static void main(String[]args) throws IOException{
		String path = "D:/xiaoMWork/workspace/UiTestFramework/test-output/log/log_2017-04-07.log";
		File file = new File(path);
		if(file.exists()){
			file.delete();
		}
	}
}
