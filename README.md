# JTimer for JAVA

#### 项目介绍
基于SpringBoot+layui秒级定时任务管理

#### PHP版本
https://gitee.com/itzhoujun/JTimer

#### 项目预览

预览图请点击上方php版本连接，两个版本功能基本一致

#### 运行环境
1. Linux
2. JDK1.8 及以上版本
3. mysql

#### 安装教程

1. 创建数据库jtimer，将src/main/resources目录下jtimer.sql导入
2. 修改application.yml中数据库连接配置
3. 在项目根目录下执行mvn clean package -Dmaven.test.skip=true打包项目
4. 创建run.bat脚本：

```
#!/bin/sh
java -jar target/jtimer-0.0.1-SNAPSHOT.jar  >> jtimer.log 2>&1 &
```
后台默认用户名密码：admin/admin
