前期准备：在百度地图官网申请key，申请过程中安全码（sha1）以及包名一定要填写正确。具体方法官网介绍的很清楚，但是根据官网提供的方法，从cmd查询与eclipse查询结果不一致。。。貌似eclipse查到的那个才是对的

必须为新的app申请对应的key，否则地图是显示不出来的，只有网格线。。。

运行demo：
为demo申请一个key。。。（我觉得关键在于包名，这个一定要正确。。。）
在eclipse打开demo，可以直接运行

将demo从eclipse直接导入AS问题不大，主要是在libs里copy一下armeabi，更名为armeabi-v7a，然后就没什么问题了。。。

但是如果在AS里面直接配置。。。特意保留了错误日志：

07-16 11:55:54.830: E/AndroidRuntime(28198): FATAL EXCEPTION: main
07-16 11:55:54.830: E/AndroidRuntime(28198): java.lang.ExceptionInInitializerError
07-16 11:55:54.830: E/AndroidRuntime(28198): 	at com.baidu.mapapi.SDKInitializer.initialize(Unknown Source)
07-16 11:55:54.830: E/AndroidRuntime(28198): 	at com.baidu.mapapi.SDKInitializer.initialize(Unknown Source)
07-16 11:55:54.830: E/AndroidRuntime(28198): 	at com.atomu.maptest.app.MainActivity.onCreate(MainActivity.java:19)
07-16 11:55:54.830: E/AndroidRuntime(28198): 	at android.app.Activity.performCreate(Activity.java:5084)
07-16 11:55:54.830: E/AndroidRuntime(28198): 	at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1079)
07-16 11:55:54.830: E/AndroidRuntime(28198): 	at com.lbe.security.service.core.client.internal.InstrumentationDelegate.callActivityOnCreate(InstrumentationDelegate.java:76)
07-16 11:55:54.830: E/AndroidRuntime(28198): 	at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2044)
07-16 11:55:54.830: E/AndroidRuntime(28198): 	at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2105)
07-16 11:55:54.830: E/AndroidRuntime(28198): 	at android.app.ActivityThread.access$600(ActivityThread.java:136)
07-16 11:55:54.830: E/AndroidRuntime(28198): 	at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1201)
07-16 11:55:54.830: E/AndroidRuntime(28198): 	at android.os.Handler.dispatchMessage(Handler.java:99)
07-16 11:55:54.830: E/AndroidRuntime(28198): 	at android.os.Looper.loop(Looper.java:137)
07-16 11:55:54.830: E/AndroidRuntime(28198): 	at android.app.ActivityThread.main(ActivityThread.java:4881)
07-16 11:55:54.830: E/AndroidRuntime(28198): 	at java.lang.reflect.Method.invokeNative(Native Method)
07-16 11:55:54.830: E/AndroidRuntime(28198): 	at java.lang.reflect.Method.invoke(Method.java:511)
07-16 11:55:54.830: E/AndroidRuntime(28198): 	at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:808)
07-16 11:55:54.830: E/AndroidRuntime(28198): 	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:575)
07-16 11:55:54.830: E/AndroidRuntime(28198): 	at dalvik.system.NativeStart.main(Native Method)
07-16 11:55:54.830: E/AndroidRuntime(28198): Caused by: java.lang.UnsatisfiedLinkError: Couldn't load BaiduMapSDK_v3_0_0: findLibrary returned null
07-16 11:55:54.830: E/AndroidRuntime(28198): 	at java.lang.Runtime.loadLibrary(Runtime.java:365)
07-16 11:55:54.830: E/AndroidRuntime(28198): 	at java.lang.System.loadLibrary(System.java:535)
07-16 11:55:54.830: E/AndroidRuntime(28198): 	at com.baidu.mapapi.a.<clinit>(Unknown Source)
07-16 11:55:54.830: E/AndroidRuntime(28198): 	... 18 more
07-16 11:56:33.371: E/AndroidRuntime(28565): FATAL EXCEPTION: main
07-16 11:56:33.371: E/AndroidRuntime(28565): java.lang.ExceptionInInitializerError
07-16 11:56:33.371: E/AndroidRuntime(28565): 	at com.baidu.mapapi.SDKInitializer.initialize(Unknown Source)
07-16 11:56:33.371: E/AndroidRuntime(28565): 	at com.baidu.mapapi.SDKInitializer.initialize(Unknown Source)
07-16 11:56:33.371: E/AndroidRuntime(28565): 	at com.atomu.maptest.app.MainActivity.onCreate(MainActivity.java:19)
07-16 11:56:33.371: E/AndroidRuntime(28565): 	at android.app.Activity.performCreate(Activity.java:5084)
07-16 11:56:33.371: E/AndroidRuntime(28565): 	at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1079)
07-16 11:56:33.371: E/AndroidRuntime(28565): 	at com.lbe.security.service.core.client.internal.InstrumentationDelegate.callActivityOnCreate(InstrumentationDelegate.java:76)
07-16 11:56:33.371: E/AndroidRuntime(28565): 	at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2044)
07-16 11:56:33.371: E/AndroidRuntime(28565): 	at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2105)
07-16 11:56:33.371: E/AndroidRuntime(28565): 	at android.app.ActivityThread.access$600(ActivityThread.java:136)
07-16 11:56:33.371: E/AndroidRuntime(28565): 	at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1201)
07-16 11:56:33.371: E/AndroidRuntime(28565): 	at android.os.Handler.dispatchMessage(Handler.java:99)
07-16 11:56:33.371: E/AndroidRuntime(28565): 	at android.os.Looper.loop(Looper.java:137)
07-16 11:56:33.371: E/AndroidRuntime(28565): 	at android.app.ActivityThread.main(ActivityThread.java:4881)
07-16 11:56:33.371: E/AndroidRuntime(28565): 	at java.lang.reflect.Method.invokeNative(Native Method)
07-16 11:56:33.371: E/AndroidRuntime(28565): 	at java.lang.reflect.Method.invoke(Method.java:511)
07-16 11:56:33.371: E/AndroidRuntime(28565): 	at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:808)
07-16 11:56:33.371: E/AndroidRuntime(28565): 	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:575)
07-16 11:56:33.371: E/AndroidRuntime(28565): 	at dalvik.system.NativeStart.main(Native Method)
07-16 11:56:33.371: E/AndroidRuntime(28565): Caused by: java.lang.UnsatisfiedLinkError: Couldn't load BaiduMapSDK_v3_0_0: findLibrary returned null
07-16 11:56:33.371: E/AndroidRuntime(28565): 	at java.lang.Runtime.loadLibrary(Runtime.java:365)
07-16 11:56:33.371: E/AndroidRuntime(28565): 	at java.lang.System.loadLibrary(System.java:535)
07-16 11:56:33.371: E/AndroidRuntime(28565): 	at com.baidu.mapapi.a.<clinit>(Unknown Source)
07-16 11:56:33.371: E/AndroidRuntime(28565): 	... 18 more
07-16 11:56:41.410: E/AndroidRuntime(28593): FATAL EXCEPTION: main
07-16 11:56:41.410: E/AndroidRuntime(28593): java.lang.ExceptionInInitializerError
07-16 11:56:41.410: E/AndroidRuntime(28593): 	at com.baidu.mapapi.SDKInitializer.initialize(Unknown Source)
07-16 11:56:41.410: E/AndroidRuntime(28593): 	at com.baidu.mapapi.SDKInitializer.initialize(Unknown Source)
07-16 11:56:41.410: E/AndroidRuntime(28593): 	at com.atomu.maptest.app.MainActivity.onCreate(MainActivity.java:19)
07-16 11:56:41.410: E/AndroidRuntime(28593): 	at android.app.Activity.performCreate(Activity.java:5084)
07-16 11:56:41.410: E/AndroidRuntime(28593): 	at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1079)
07-16 11:56:41.410: E/AndroidRuntime(28593): 	at com.lbe.security.service.core.client.internal.InstrumentationDelegate.callActivityOnCreate(InstrumentationDelegate.java:76)
07-16 11:56:41.410: E/AndroidRuntime(28593): 	at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2044)
07-16 11:56:41.410: E/AndroidRuntime(28593): 	at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2105)
07-16 11:56:41.410: E/AndroidRuntime(28593): 	at android.app.ActivityThread.access$600(ActivityThread.java:136)
07-16 11:56:41.410: E/AndroidRuntime(28593): 	at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1201)
07-16 11:56:41.410: E/AndroidRuntime(28593): 	at android.os.Handler.dispatchMessage(Handler.java:99)
07-16 11:56:41.410: E/AndroidRuntime(28593): 	at android.os.Looper.loop(Looper.java:137)
07-16 11:56:41.410: E/AndroidRuntime(28593): 	at android.app.ActivityThread.main(ActivityThread.java:4881)
07-16 11:56:41.410: E/AndroidRuntime(28593): 	at java.lang.reflect.Method.invokeNative(Native Method)
07-16 11:56:41.410: E/AndroidRuntime(28593): 	at java.lang.reflect.Method.invoke(Method.java:511)
07-16 11:56:41.410: E/AndroidRuntime(28593): 	at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:808)
07-16 11:56:41.410: E/AndroidRuntime(28593): 	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:575)
07-16 11:56:41.410: E/AndroidRuntime(28593): 	at dalvik.system.NativeStart.main(Native Method)
07-16 11:56:41.410: E/AndroidRuntime(28593): Caused by: java.lang.UnsatisfiedLinkError: Couldn't load BaiduMapSDK_v3_0_0: findLibrary returned null
07-16 11:56:41.410: E/AndroidRuntime(28593): 	at java.lang.Runtime.loadLibrary(Runtime.java:365)
07-16 11:56:41.410: E/AndroidRuntime(28593): 	at java.lang.System.loadLibrary(System.java:535)
07-16 11:56:41.410: E/AndroidRuntime(28593): 	at com.baidu.mapapi.a.<clinit>(Unknown Source)
07-16 11:56:41.410: E/AndroidRuntime(28593): 	... 18 more

根据“caused by”，出错还是因为.so文件。。。

使用关键字“AS”找到一个配置教程：
准备开发环境：Android Sutdio

工程配置：
   2.1、创建工程
    2.2、libs根目录下添加baidumapapi_v2_4_0.jar
    2.3、关键点
         在src/main目录下创建jniLibs目录，在jniLibs目录创建armeabi目录
         将libBaiduMapSDK_v2_4_0.so文件放入到jniLibs\armeabi目录下
         (Eclipse和MyEclipse是放到libs\armeabi目录下)
    2.4、参考百度地图的开发步骤的开发即可。
    

http://blog.csdn.net/lmj623565791/article/details/37730469
    
此教程包含baiduapiv3.0.0的点单使用，包括定位，显示当前位置，以及方向传感器的使用，除了没办法下载代码，其他大概还是不错的。。。
