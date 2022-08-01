# common

## base

### application
**BaseApplication**

android/common/src/main/java/com/hancher/common/base/BaseApplication.java

初始化：
1. 保存application实例用于单例（仅仅保存主线程的instance）
2. 工具类初始化：MMKV、PathUtil、NetConnectUtil
3. 启动后台监听服务
activity回调：
1. 记录所有activity，方便全部退出
2. 初始化EventBus

### activity
android/common/src/main/java/com/hancher/common/base/BaseActivity.java

# app

## main

### application

android/app/src/main/java/com/hancher/gamelife/MainApplication.java
继承
android/common/src/main/java/com/hancher/common/base/BaseApplication.java

**MainApplication**

1. 保存application实例用于单例（返回值不同）
2. 初始化bugly、和风天气、Greendao








# 功能：
## 人生功能：
### 倒计时进度条
### 任务记录
### 经验记录
### 背单词记录
## 记录功能：
### 天气插件
### 日记
### 列表
### 日历
## 位置记录
## 记账

#主页：
功能总结：
1. 最近一个月的动态
    包括所有内容
2. 快速插入内容
    考虑整合到1中
3. 设置
    重新设计UI

＃