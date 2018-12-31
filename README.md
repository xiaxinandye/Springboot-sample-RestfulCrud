# 介绍

此 Demo 是为了熟悉 Spring Boot和 thymeleaf 的使用，所以适用于刚接触 Spring Boot不久的新手，此项目是一个简单的 Web 版的员工 CRUD，项目内容来源于[尚硅谷谷粒学院 Spring Boot 核心技术篇](http://www.gulixueyuan.com/course/231)，我根据自己的理解，又重新地动手做了一些，其中也发现了一些自己的薄弱点，并针对这些薄弱点进行巩固，以便尽快掌握 Spring Boot。

# 一、 创建 Spring Boot 项目

- 开发工具：IDEA

- 创建方式：使用 Spring Initializr 方式创建

- 项目结构目录示意图：

  ├─src
  │  ├─main
  │  │  ├─java
  │  │  │  └─com
  │  │  │      └─yunche
  │  │  │          └─controller
  │  │  └─resources
  │  │      ├─public
  │  │      ├─static
  │  │      │  └─asserts
  │  │      │      ├─css
  │  │      │      ├─img
  │  │      │      └─js
  │  │      └─templates

# 二、引入依赖并完成登录页面的样式

## 1、修改 pom.xml

添加 jquery webjars 、bootstrap webjars、thymeleaf 依赖 

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.1.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.yunche</groupId>
    <artifactId>springboot-sample-restfulcrud</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>springboot-sample-restfulcrud</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!--引入jquery webjars-->
        <!--使用时：路径webjars/jquery/3.3.1/jquery.js-->
        <dependency>
            <groupId>org.webjars</groupId>
            <artifactId>jquery</artifactId>
            <version>3.3.1</version>
        </dependency>

        <!--引入bootstrap webjars-->
        <dependency>
            <groupId>org.webjars</groupId>
            <artifactId>bootstrap</artifactId>
            <version>4.2.1</version>
        </dependency>

        <!--引入thymeleaf3-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

## 2、引入相应的本地 css、js 文件

将对应的文件引入到相应的位置

│  │      ├─static
│  │      │  └─asserts
│  │      │      ├─css
│  │      │      │      bootstrap.min.css
│  │      │      │      dashboard.css
│  │      │      │      signin.css
│  │      │      │      
│  │      │      ├─img
│  │      │      │      bootstrap-solid.svg
│  │      │      │      
│  │      │      └─js
│  │      │              bootstrap.min.js
│  │      │              Chart.min.js
│  │      │              feather.min.js
│  │      │              jquery-3.2.1.slim.min.js
│  │      │              popper.min.js

## 3、编辑 login.html 

│  │      └─templates
│  │              login.html

使用 thymeleaf  对 login.html 完成 css、js等链接的修改

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
      <meta name="description" content="">
      <meta name="author" content="">
      <title>Signin Template for Bootstrap</title>
      <!-- Bootstrap core CSS -->
      <link href="asserts/css/bootstrap.min.css" rel="stylesheet" th:href="@{/webjars/bootstrap/4.2.1/css/bootstrap.css}">
      <!-- Custom styles for this template -->
      <link href="asserts/css/signin.css" rel="stylesheet" >
   </head>

   <body class="text-center">
      <form class="form-signin" action="dashboard.html">
         <img class="mb-4" src="asserts/img/bootstrap-solid.svg" alt="" width="72" height="72">
         <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
         <label class="sr-only">Username</label>
         <input type="text" class="form-control" placeholder="Username" required="" autofocus="">
         <label class="sr-only">Password</label>
         <input type="password" class="form-control" placeholder="Password" required="">
         <div class="checkbox mb-3">
            <label>
          <input type="checkbox" value="remember-me"> Remember me
        </label>
         </div>
         <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
         <p class="mt-5 mb-3 text-muted">© 2017-2018</p>
         <a class="btn btn-sm">中文</a>
         <a class="btn btn-sm">English</a>
      </form>

   </body>

</html>
```

## 4、处理对 login 页面请求的映射

│  │  │          └─controller
│  │  │                  HomePageController.java

```java
package com.yunche.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName: HomePageController
 * @Description:
 * @author: yunche
 * @date: 2018/12/31
 */
@Controller
public class HomePageController {

    @RequestMapping(value = {"/", "/index.html", "/index", "login"})
    public String index() {
        return "login";
    }
}
```

## 阶段效果图

![](http://images.cnblogs.com/cnblogs_com/yunche/1374164/o_demo1.gif)