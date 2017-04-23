# AppiumXM
基于 Appium + testNG + java + maven 适用于Android、iOS自动化测试框架，Excel关键字驱动无需编写代码，支持jenkins持续集成，Android真机或模拟器多设备并发执行以及性能监控，iOS只支持基本操作
# 开发配置
Appium  1.6.3<p>
maven   3.3.9<p>
OSX  10.11.6<p>
Xcode  8.2.1<p>
window 10
# 存在问题
1、测试报告展示测试结束时间有误<p>
2、testNG框架限制多线程数<p>
3、其他未知问题
# 后期优化
1、整合sikuil，实现图片识别操作
# 如何运行？
1、Devices文件夹下添加相应的设备信息<p>
2、testcase文件夹下编辑测试步骤流程<p>
3、支持操作：看源码。。。<p>
4、运行StartTest.xml或编写ant编译配置文件运行<p>
5、多设备参照testNG多线程
# 测试报告展示
![image](https://github.com/xiaoMGitHub/AppiumXM/blob/master/test-output/test.png)
