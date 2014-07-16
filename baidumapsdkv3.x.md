准备开发环境：Android Sutdio

工程配置：
   2.1、创建工程
    2.2、libs根目录下添加baidumapapi_v2_4_0.jar
    2.3、关键点
         在src/main目录下创建jniLibs目录，在jniLibs目录创建armeabi目录
         将libBaiduMapSDK_v2_4_0.so文件放入到jniLibs\armeabi目录下
         (Eclipse和MyEclipse是放到libs\armeabi目录下)
    2.4、参考百度地图的开发步骤的开发即可。
    
    
baidumapsdk只显示网格线不显示地图的原因：key错误，参照已经成功申请的key为新的app申请正确的key，否则真的显示不出来
    
    
此教程包含baiduapiv3.0.0的点单使用，包括定位，显示当前位置，以及方向传感器的使用
http://blog.csdn.net/lmj623565791/article/details/37730469
