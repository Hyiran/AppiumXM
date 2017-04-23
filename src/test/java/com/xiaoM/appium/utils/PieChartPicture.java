package com.xiaoM.appium.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PieLabelLinkStyle;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleInsets;

import com.xiaoM.Common.Utils.IOMananger;  


public class PieChartPicture { 
	
	String PictureName;
	String CpuPath;
	String MenPath;
	public PieChartPicture(String name,String CpuPath,String MenPath){
		this.PictureName = name;
		this.CpuPath = CpuPath;
		this.MenPath = MenPath;
	}
	private static String NO_DATA_MSG = "数据加载失败";
	private static Font FONT = new Font("宋体", Font.PLAIN, 12);
	private static Font FONT2 = new Font("宋体", Font.PLAIN, 18);
	public static Color[] CHART_COLORS = {
			new Color(31,129,188), new Color(92,92,97), new Color(144,237,125), new Color(255,188,117),
			new Color(153,158,255), new Color(255,117,153), new Color(253,236,109), new Color(128,133,232),
			new Color(158,90,102),new Color(255, 204, 102) };//颜色

	static {
		setChartTheme();
	}
	/**
	 * 中文主题样式 解决乱码
	 */
	public static void setChartTheme() {
		// 设置中文主题样式 解决乱码
		StandardChartTheme chartTheme = new StandardChartTheme("CN");
		// 设置标题字体
		chartTheme.setExtraLargeFont(FONT2);
		// 设置图例的字体
		chartTheme.setRegularFont(FONT);
		// 设置轴向的字体
		chartTheme.setLargeFont(FONT);
		chartTheme.setSmallFont(FONT);
		chartTheme.setTitlePaint(new Color(51, 51, 51));
		chartTheme.setSubtitlePaint(new Color(85, 85, 85));

		chartTheme.setLegendBackgroundPaint(Color.WHITE);// 设置标注
		chartTheme.setLegendItemPaint(Color.BLACK);//
		chartTheme.setChartBackgroundPaint(Color.WHITE);
		// 绘制颜色绘制颜色.轮廓供应商
		// paintSequence,outlinePaintSequence,strokeSequence,outlineStrokeSequence,shapeSequence

		Paint[] OUTLINE_PAINT_SEQUENCE = new Paint[] { Color.WHITE };
		// 绘制器颜色源
		DefaultDrawingSupplier drawingSupplier = new DefaultDrawingSupplier(CHART_COLORS, CHART_COLORS, OUTLINE_PAINT_SEQUENCE,
				DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE,
				DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE);
		chartTheme.setDrawingSupplier(drawingSupplier);

		chartTheme.setPlotBackgroundPaint(Color.WHITE);// 绘制区域
		chartTheme.setPlotOutlinePaint(Color.WHITE);// 绘制区域外边框
		chartTheme.setLabelLinkPaint(new Color(8, 55, 114));// 链接标签颜色
		chartTheme.setLabelLinkStyle(PieLabelLinkStyle.CUBIC_CURVE);

		chartTheme.setAxisOffset(new RectangleInsets(5, 12, 5, 12));
		chartTheme.setDomainGridlinePaint(new Color(192, 208, 224));// X坐标轴垂直网格颜色
		chartTheme.setRangeGridlinePaint(new Color(192, 192, 192));// Y坐标轴水平网格颜色

		chartTheme.setBaselinePaint(Color.WHITE);
		chartTheme.setCrosshairPaint(Color.BLUE);// 不确定含义
		chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
		chartTheme.setTickLabelPaint(new Color(67, 67, 72));// 刻度数字
		chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
		chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar 渲染

		chartTheme.setItemLabelPaint(Color.black);
		chartTheme.setThermometerPaint(Color.white);// 温度计

		ChartFactory.setChartTheme(chartTheme);
	}
	/**
	 * 调试用
	 * @return
	 */
	public static CategoryDataset createDataSet() {  
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.setValue(10, "CPU占用(%)","2017-04-01 16:25:53"); 
		dataset.setValue(20, "CPU占用(%)","2017-04-01 16:26:13");
		dataset.setValue(23, "CPU占用(%)","2017-04-01 16:26:17");
		dataset.setValue(15, "CPU占用(%)","2017-04-01 16:26:22");
		dataset.setValue(29, "CPU占用(%)","2017-04-01 16:27:01");
		dataset.setValue(20, "CPU占用(%)","2017-04-01 16:27:05"); 
		dataset.setValue(23, "CPU占用(%)","2017-04-01 16:27:09");
		dataset.setValue(21, "CPU占用(%)","2017-04-01 16:27:16");
		dataset.setValue(16, "CPU占用(%)","2017-04-01 16:27:19");
		dataset.setValue(29, "CPU占用(%)","2017-04-01 16:27:23");
		dataset.setValue(10, "CPU占用(%)","2017-04-01 16:27:26"); 
		dataset.setValue(20, "CPU占用(%)","2017-04-01 16:27:35");
		dataset.setValue(23, "CPU占用(%)","2017-04-01 16:27:39");
		dataset.setValue(15, "CPU占用(%)","2017-04-01 16:27:42");
		dataset.setValue(29, "CPU占用(%)","2017-04-01 16:27:47");
		dataset.setValue(20, "CPU占用(%)","2017-04-01 16:27:51"); 
		dataset.setValue(23, "CPU占用(%)","2017-04-01 16:27:54");
		dataset.setValue(21, "CPU占用(%)","2017-04-01 16:27:57");
		dataset.setValue(16, "CPU占用(%)","2017-04-01 16:28:30");
		dataset.setValue(29, "CPU占用(%)","2017-04-01 16:28:33");
		dataset.setValue(10, "CPU占用(%)","2017-04-01 16:28:36"); 
		dataset.setValue(20, "CPU占用(%)","2017-04-01 16:28:39");
		dataset.setValue(23, "CPU占用(%)","2017-04-01 16:29:06");
		dataset.setValue(15, "CPU占用(%)","2017-04-01 16:29:09");
		dataset.setValue(29, "CPU占用(%)","2017-04-01 16:29:11");
		dataset.setValue(20, "CPU占用(%)","2017-04-01 16:29:14"); 
		dataset.setValue(23, "CPU占用(%)","2017-04-01 16:29:26");
		dataset.setValue(21, "CPU占用(%)","2017-04-01 16:29:29");
		dataset.setValue(16, "CPU占用(%)","2017-04-01 16:29:31");
		dataset.setValue(29, "CPU占用(%)","2017-04-01 16:29:53");
		dataset.setValue(10, "CPU占用(%)","2017-04-01 16:29:56"); 
		dataset.setValue(20, "CPU占用(%)","2017-04-01 16:30:01");
		dataset.setValue(23, "CPU占用(%)","2017-04-01 16:30:03");
		dataset.setValue(15, "CPU占用(%)","2017-04-01 16:30:07");
		dataset.setValue(29, "CPU占用(%)","2017-04-01 16:30:10");
		dataset.setValue(20, "CPU占用(%)","2017-04-01 16:30:14"); 
		dataset.setValue(23, "CPU占用(%)","2017-04-01 16:30:17");
		dataset.setValue(21, "CPU占用(%)","2017-04-01 16:30:19");
		dataset.setValue(16, "CPU占用(%)","2017-04-01 16:30:22");
		dataset.setValue(29, "CPU占用(%)","2017-04-01 16:30:25");
		return dataset; 
	}
	/** CPU
	 * 创建 简单数据集对象 
	 * 组合数据集对象 
	 * @return 
	 * @throws FileNotFoundException 
	 */  
	public  CategoryDataset createCpuDataSet() throws FileNotFoundException {
		String[] Cpu = null;
		int k;
		List<String> CpuDatas = IOMananger.readTxtFile(CpuPath);
		DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
		for(int i=0;i<CpuDatas.size();i++){
			Cpu = CpuDatas.get(i).split(" ");
			if(!Cpu[0].equals("0")){
				k = Integer.parseInt(Cpu[0]);
				dataset.setValue(k, "CPU占用(%)",Cpu[1]);
			}
		}
		return dataset;  
	}  

	/** MEN
	 * 创建 简单数据集对象 
	 * 组合数据集对象 
	 * @return 
	 * @throws FileNotFoundException 
	 */  
	public CategoryDataset createMenDataSet() throws FileNotFoundException {
		String[] Men = null;
		double k;
		List<String> MenDatas = IOMananger.readTxtFile(MenPath);
		DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
		for(int i=0;i<MenDatas.size();i++){
			Men = MenDatas.get(i).split(" ");
			if(!Men[0].equals("0")){
				k = Double.valueOf(Men[0]);
				dataset.setValue(k, "MEN占用(MB)",Men[1]);
			}
		}
		return dataset;  
	} 

	/** 
	 * 输出图表到指定的磁盘 
	 * @param destPath 
	 * @param chart 
	 */  
	public static void drawToOutputStream(String destPath, JFreeChart chart) {  
		FileOutputStream fos = null;  
		try {  
			fos = new FileOutputStream(destPath);  
			ChartUtilities.writeChartAsJPEG( fos, 
					chart, // 图表对象  
					1600, // 宽  
					800, // 高  
					null); // ChartRenderingInfo信息  
		} catch (IOException e) {  
			e.printStackTrace();  
		} finally {  
			try {  
				fos.close();  
			} catch (IOException e) {  
				e.printStackTrace();  
			}  
		}  
	}  
	/**
	 * 设置类别图表(CategoryPlot) X坐标轴线条颜色和样式
	 * 
	 * @param axis
	 */
	public static void setXAixs(CategoryPlot plot) {
		Color lineColor = new Color(31, 121, 170);
		plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
		plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色

	}

	/**
	 * 设置类别图表(CategoryPlot) Y坐标轴线条颜色和样式 同时防止数据无法显示
	 * 
	 * @param axis
	 */
	public static void setYAixs(CategoryPlot plot) {
		Color lineColor = new Color(192, 208, 224);
		ValueAxis axis = plot.getRangeAxis();
		axis.setAxisLinePaint(lineColor);// Y坐标轴颜色
		axis.setTickMarkPaint(lineColor);// Y坐标轴标记|竖线颜色
		// 隐藏Y刻度
		axis.setAxisLineVisible(false);
		axis.setTickMarksVisible(false);
		// Y轴网格线条
		plot.setRangeGridlinePaint(new Color(192, 192, 192));
		plot.setRangeGridlineStroke(new BasicStroke(1));

		plot.getRangeAxis().setUpperMargin(0.1);// 设置顶部Y坐标轴间距,防止数据无法显示
		plot.getRangeAxis().setLowerMargin(0.1);// 设置底部Y坐标轴间距

	}

	public static JFreeChart createCpuChart(CategoryDataset Dataset,String PictureName  ) {
		JFreeChart chart = ChartFactory.createLineChart(PictureName+"_CPU性能趋势分析图", " ", "Y轴", Dataset);
		chart.setTextAntiAlias(false);// 抗锯齿
		CategoryPlot plot = chart.getCategoryPlot(); // 获取柱图的Plot对象(实际图表)  	
		plot.setForegroundAlpha(0.65F); //设置前景色透明度  	
		plot.setRangeGridlinesVisible(true);// 设置横虚线可见    
		plot.setRangeGridlinePaint(Color.gray);// 虚线色彩  
		plot.setNoDataMessage(NO_DATA_MSG);
		plot.setInsets(new RectangleInsets(10, 10, 0, 10), false);
		LineAndShapeRenderer renderer =  (LineAndShapeRenderer) plot.getRenderer();
		renderer.setBaseShapesVisible(true);// 数据点绘制形状
		chart.getLegend().setFrame(new BlockBorder(Color.WHITE));// 设置标注无边框
		setXAixs(plot);// X坐标轴渲染
		setYAixs(plot);// Y坐标轴渲染
		CategoryAxis h = plot.getDomainAxis(); //获取x轴  
		h.setTickLabelFont(new Font("楷体", Font.PLAIN, 12));// 轴数值   
		h.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		return chart;
	}
	public static JFreeChart createMenChart(CategoryDataset Dataset,String PictureName  ) {
		JFreeChart chart = ChartFactory.createLineChart(PictureName+"_内存使用趋势分析图", " ", "Y轴", Dataset);
		chart.setTextAntiAlias(false);// 抗锯齿
		CategoryPlot plot = chart.getCategoryPlot(); // 获取柱图的Plot对象(实际图表)  	
		plot.setForegroundAlpha(0.65F); //设置前景色透明度  	
		plot.setRangeGridlinesVisible(true);// 设置横虚线可见    
		plot.setRangeGridlinePaint(Color.gray);// 虚线色彩  
		plot.setNoDataMessage(NO_DATA_MSG);
		plot.setInsets(new RectangleInsets(10, 10, 0, 10), false);
		LineAndShapeRenderer renderer =  (LineAndShapeRenderer) plot.getRenderer();
		renderer.setBaseShapesVisible(true);// 数据点绘制形状
		chart.getLegend().setFrame(new BlockBorder(Color.WHITE));// 设置标注无边框
		setXAixs(plot);// X坐标轴渲染
		setYAixs(plot);// Y坐标轴渲染
		CategoryAxis h = plot.getDomainAxis(); //获取x轴  
		h.setTickLabelFont(new Font("楷体", Font.PLAIN, 12));// 轴数值   
		h.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		return chart;
	}
	public void CpuScreen() throws FileNotFoundException{
		CategoryDataset dataset = createCpuDataSet();// step1:创建数据集对象    
		JFreeChart chart = createCpuChart(dataset,PictureName);// step2:创建折线图  
		File dir = new File("test-output/snapshot");// step3: 输出图表到磁盘  
		if (!dir.exists()){
			dir.mkdirs();
		}
		String screenPath = dir.getAbsolutePath() + "/"+PictureName+"_CPU.jpg";
		drawToOutputStream(screenPath, chart); // step4: 输出图表到指定的磁盘 
	}
	public void MenScreen() throws FileNotFoundException{
		CategoryDataset dataset = createMenDataSet();// step1:创建数据集对象    
		JFreeChart chart = createMenChart(dataset,PictureName);// step2:创建折线图  
		File dir = new File("test-output/snapshot");// step3: 输出图表到磁盘  
		if (!dir.exists()){
			dir.mkdirs();
		}
		String screenPath = dir.getAbsolutePath() + "/"+PictureName+"_Men.jpg";
		drawToOutputStream(screenPath, chart); // step4: 输出图表到指定的磁盘 
	}
	
	public void createScreen() throws FileNotFoundException{
		CpuScreen();
		MenScreen();
	}
	public static void main(String[] args) {
	}
} 