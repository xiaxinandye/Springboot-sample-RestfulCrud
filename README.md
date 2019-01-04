<!-- GFM-TOC -->
* [介绍](#介绍)
* [零、项目素材](#零项目素材)
* [一、 创建 Spring Boot 项目](#一-创建-spring-boot-项目)
* [二、定制首页](#二定制首页)
    * [1、修改 pom.xml](#1修改-pomxml)
    * [2、引入相应的本地 css、js 文件](#2引入相应的本地-cssjs-文件)
    * [3、编辑 login.html](#3编辑-loginhtml)
    * [4、处理对 login 页面请求的映射](#4处理对-login-页面请求的映射)
    * [5、国际化登录页面](#5国际化登录页面)
    * [6、阶段演示效果](#6阶段演示效果)
* [三、完成 login 操作](#三完成-login-操作)
    * [1、添加控制器用于处理请求](#1添加控制器用于处理请求)
    * [2、添加后台首页 dashboard.html](#2添加后台首页-dashboardhtml)
    * [3、添加登录错误消息展示](#3添加登录错误消息展示)
    * [4、定制拦截器](#4定制拦截器)
* [四、员工的 CRUD](#四员工的-crud)
    * [1、引入相应的 dao 和 entity 类](#1引入相应的-dao-和-entity-类)
    * [2、Restful 风格的 URL](#2restful-风格的-url)
    * [3、模板布局](#3模板布局)
        * [1、dashboard.html（后台首页）](#1dashboardhtml后台首页)
        * [2、sidebar.html（侧边栏模板）](#2sidebarhtml侧边栏模板)
        * [3、topbar.html（顶部栏模板）](#3topbarhtml顶部栏模板)
        * [4、list.html（员工列表页面）](#4listhtml员工列表页面)
        * [5、add.html（员工的添加、修改二合一页面）](#5addhtml员工的添加修改二合一页面)
    * [4、EmployeeController（处理员工请求控制器）](#4employeecontroller处理员工请求控制器)
    * [5、细节讲解](#5细节讲解)
        * [1、PUT、DELTE 请求](#1putdelte-请求)
        * [2、给模板页面传参](#2给模板页面传参)
        * [3、添加、修改页面共用](#3添加修改页面共用)
    * [6、定制错误页面](#6定制错误页面)
    * [7、阶段演示效果](#7阶段演示效果)
<!-- GFM-TOC -->


# 介绍

此 Demo 是为了熟悉 Spring Boot 和 thymeleaf 的使用，所以适用于刚接触 Spring Boot 不久的新手，此项目是一个简单的 Web 版的员工 CRUD，项目内容来源于[尚硅谷谷粒学院 Spring Boot 核心技术篇 ](http://www.gulixueyuan.com/course/231)，我根据自己的理解，又重新地动手做了一些，其中也发现了一些自己的薄弱点，并针对这些薄弱点进行巩固，以便尽快掌握 Spring Boot。

# 零、项目素材

[restful-crud-experiment](/restful-crud-experiment)

# 一、 创建 Spring Boot 项目

- 开发工具：IDEA

- 创建方式：使用 Spring Initializr 方式创建

- 项目结构目录示意图：

  ```
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
  ```

# 二、定制首页

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

        <!--引入 jquery webjars-->
        <!--使用时：路径 webjars/jquery/3.3.1/jquery.js-->
        <dependency>
            <groupId>org.webjars</groupId>
            <artifactId>jquery</artifactId>
            <version>3.3.1</version>
        </dependency>

        <!--引入 bootstrap webjars-->
        <dependency>
            <groupId>org.webjars</groupId>
            <artifactId>bootstrap</artifactId>
            <version>4.2.1</version>
        </dependency>

        <!--引入 thymeleaf3-->
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

```
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
```

## 3、编辑 login.html 

```
│  │      └─templates
│  │              login.html
```

使用 thymeleaf  对 login.html 完成 css、js 、form 等链接的修改

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
		<link href="asserts/css/signin.css" rel="stylesheet" th:href="@{/asserts/css/signin.css}">
	</head>

	<body class="text-center">
		<form class="form-signin" action="dashboard.html" method="post" th:action="@{/user/login}">
			<img class="mb-4" src="asserts/img/bootstrap-solid.svg" th:src="@{/asserts/img/bootstrap-solid.svg}" alt="" width="72" height="72">
			<h1 class="h3 mb-3 font-weight-normal" th:text="#{login.tip}">Please Sign In</h1>
			<label class="sr-only" th:text="#{login.username}">Username</label>
			<input type="text" name="username" class="form-control" placeholder="Username" th:placeholder="#{login.username}" required="" autofocus="">
			<label class="sr-only" th:text="#{login.password}">Password</label>
			<input type="password" name="password" class="form-control" placeholder="Password" required="" th:placeholder="#{login.password}">
			<div class="checkbox mb-3">
				<label>
          <input type="checkbox" value="remember-me" > [[#{login.remember}]]
        </label>
			</div>
			<button class="btn btn-lg btn-primary btn-block" type="submit" th:text="#{login.btn}">Sign in</button>
			<p class="mt-5 mb-3 text-muted">? 2017-2018</p>
			<a class="btn btn-sm" > 中文 </a>
			<a class="btn btn-sm" >English</a>
		</form>

	</body>

</html>
```

## 4、处理对 login 页面请求的映射

```
│  │  │          ├─config
│  │  │          │      MyMvcConfig.java
│  │  │          │      
│  │  │          └─controller
```

扩展 Spring MVC，添加视图控制器，完成我们自己的路径的映射规则

```java
package com.yunche.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName: MyMvcConfig
 * @Description: 练习：使用 WebMvcConfigurer 扩展 SpringMVC 的功能
 * @author: yunche
 * @date: 2019/01/01
 */

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/index.html").setViewName("login");
        registry.addViewController("/index").setViewName("login");
    }
}
```

## 5、国际化登录页面

- 编写国际化文件

  ```
  │  │  └─resources
  │  │      │  application.properties
  │  │      │  
  │  │      ├─i18n
  │  │      │      login.en_US.properties
  │  │      │      login.properties
  │  │      │      login.zh_CN.properties
  ```

  - login_en_US.properties:

    ```properties
    login.btn=Sign In
    login.password=Password
    login.remember=Remember Me
    login.tip=Please Sign In
    login.username=Username
    ```

  - login.properties:

    ```properties
    login.btn=登录
    login.password=密码
    login.remember=记住我
    login.tip=请登录
    login.username=用户名
    ```

  - login_zh_CN.properties:

    ```properties
    login.btn=登录
    login.password=密码
    login.remember=记住我
    login.tip=请登录
    login.username=用户名
    ```

- 绑定编写的国际化配置文件到 Message Source

  ```
  │  │  └─resources
  │  │      │  application.properties
  ```

  application.properties:

  ```properties
  spring.messages.basename=i18n.login
  ```

  <div align="center">  <img src="/img/demo2.gif" width=""/> </div><br>

- 实现按钮手动转换语言

  - 国际化有效的原理：

    Locale 对象存储区域信息， 根据 Locale 来自适应语言配置，默认的`LocaleResolver`就是根据请求头带来的区域信息（`Accept-Language`）获取 Locale 对象。如果需要手动转换语言只需要手动构造`LocaleResolver`来自定义生成 Locale 对象的规则。

  - 定制 LocaleResolver：

    ```
    │  │  │          ├─component
    │  │  │          │      MyLocaleResolver.java
    ```

    MyLocaleResolver.java:

    ```java
    package com.yunche.component;
    
    import org.springframework.stereotype.Component;
    import org.springframework.web.servlet.LocaleResolver;
    import org.thymeleaf.util.StringUtils;
    
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import java.util.Locale;
    
    /**
     * @ClassName: MyLocaleResolver
     * @Description:
     * @author: yunche
     * @date: 2019/01/01
     */
    @Component
    public class MyLocaleResolver implements LocaleResolver {
        @Override
        public Locale resolveLocale(HttpServletRequest request) {
            String l = request.getParameter("l");
            Locale locale = Locale.getDefault();
            if (!StringUtils.isEmpty(l)) {
                String[] split = l.split("_");
                //根据 Language、Country 生成 locale
                locale = new Locale(split[0], split[1]);
            }
            return locale;
        }
    
        @Override
        public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
    
        }
    }
    ```

  - 将定制的组件加入容器中

    此时默认的 LocaleResolver 将不再生效，将使用我们定制的 LocaleResolver。

    ```
    │  │  │          ├─config
    │  │  │          │      MyMvcConfig.java
    ```

    MyMvcConfig.java:

    ```java
    /**
     * 注册定制组件
     */
    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }
    ```

  - 添加相应的链接参数

    login.html:

    ```html
    <a class="btn btn-sm" th:href="@{/index(l='zh_CN')}"> 中文 </a>
    <a class="btn btn-sm" th:href="@{/index(l='en_US')}">English</a>
    ```

## 6、阶段演示效果

<div align="center">  <img src="/img/demo3.gif" width=""/> </div><br>

# 三、完成 login 操作

## 1、添加控制器用于处理请求

```java
package com.yunche.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @ClassName: LoginController
 * @Description:
 * @author: yunche
 * @date: 2019/01/02
 */
@Controller
public class LoginController {

    @PostMapping("/user/login")
    public String login(String username, String password) {
        if ("admin".equals(username) && !StringUtils.isEmpty(password)) {
            return "redirect:/main.html";
        }
        return "login";
    }
}
```

当用户登录成功后，重定向到后台首页。添加对 /main.html 请求的处理：在 MyMvcConfig 下的 addViewControllers 方法中加入：

```java
 registry.addViewController("/main.html").setViewName("dashboard");
```

## 2、添加后台首页 dashboard.html

```
│  │      └─templates
│  │              dashboard.html
```

dashboard.html:

```html
<!DOCTYPE html>
<!-- saved from url=(0052)http://getbootstrap.com/docs/4.0/examples/dashboard/ -->
<html lang="en">
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
      <meta name="description" content="">
      <meta name="author" content="">

      <title>Dashboard Template for Bootstrap</title>
      <!-- Bootstrap core CSS -->
      <link href="asserts/css/bootstrap.min.css" rel="stylesheet">

      <!-- Custom styles for this template -->
      <link href="asserts/css/dashboard.css" rel="stylesheet">
      <style type="text/css">
         /* Chart.js */
         
         @-webkit-keyframes chartjs-render-animation {
            from {
               opacity: 0.99
            }
            to {
               opacity: 1
            }
         }
         
         @keyframes chartjs-render-animation {
            from {
               opacity: 0.99
            }
            to {
               opacity: 1
            }
         }
         
         .chartjs-render-monitor {
            -webkit-animation: chartjs-render-animation 0.001s;
            animation: chartjs-render-animation 0.001s;
         }
      </style>
   </head>

   <body>
      <nav class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0">
         <a class="navbar-brand col-sm-3 col-md-2 mr-0" href="http://getbootstrap.com/docs/4.0/examples/dashboard/#">Company name</a>
         <input class="form-control form-control-dark w-100" type="text" placeholder="Search" aria-label="Search">
         <ul class="navbar-nav px-3">
            <li class="nav-item text-nowrap">
               <a class="nav-link" href="http://getbootstrap.com/docs/4.0/examples/dashboard/#">Sign out</a>
            </li>
         </ul>
      </nav>

      <div class="container-fluid">
         <div class="row">
            <nav class="col-md-2 d-none d-md-block bg-light sidebar">
               <div class="sidebar-sticky">
                  <ul class="nav flex-column">
                     <li class="nav-item">
                        <a class="nav-link active" href="http://getbootstrap.com/docs/4.0/examples/dashboard/#">
                           <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-home">
                              <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path>
                              <polyline points="9 22 9 12 15 12 15 22"></polyline>
                           </svg>
                           Dashboard <span class="sr-only">(current)</span>
                        </a>
                     </li>
                     <li class="nav-item">
                        <a class="nav-link" href="http://getbootstrap.com/docs/4.0/examples/dashboard/#">
                           <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file">
                              <path d="M13 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V9z"></path>
                              <polyline points="13 2 13 9 20 9"></polyline>
                           </svg>
                           Orders
                        </a>
                     </li>
                     <li class="nav-item">
                        <a class="nav-link" href="http://getbootstrap.com/docs/4.0/examples/dashboard/#">
                           <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-shopping-cart">
                              <circle cx="9" cy="21" r="1"></circle>
                              <circle cx="20" cy="21" r="1"></circle>
                              <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"></path>
                           </svg>
                           Products
                        </a>
                     </li>
                     <li class="nav-item">
                        <a class="nav-link" href="http://getbootstrap.com/docs/4.0/examples/dashboard/#">
                           <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-users">
                              <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
                              <circle cx="9" cy="7" r="4"></circle>
                              <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
                              <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
                           </svg>
                           Customers
                        </a>
                     </li>
                     <li class="nav-item">
                        <a class="nav-link" href="http://getbootstrap.com/docs/4.0/examples/dashboard/#">
                           <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-bar-chart-2">
                              <line x1="18" y1="20" x2="18" y2="10"></line>
                              <line x1="12" y1="20" x2="12" y2="4"></line>
                              <line x1="6" y1="20" x2="6" y2="14"></line>
                           </svg>
                           Reports
                        </a>
                     </li>
                     <li class="nav-item">
                        <a class="nav-link" href="http://getbootstrap.com/docs/4.0/examples/dashboard/#">
                           <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-layers">
                              <polygon points="12 2 2 7 12 12 22 7 12 2"></polygon>
                              <polyline points="2 17 12 22 22 17"></polyline>
                              <polyline points="2 12 12 17 22 12"></polyline>
                           </svg>
                           Integrations
                        </a>
                     </li>
                  </ul>

                  <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
              <span>Saved reports</span>
              <a class="d-flex align-items-center text-muted" href="http://getbootstrap.com/docs/4.0/examples/dashboard/#">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-plus-circle"><circle cx="12" cy="12" r="10"></circle><line x1="12" y1="8" x2="12" y2="16"></line><line x1="8" y1="12" x2="16" y2="12"></line></svg>
              </a>
            </h6>
                  <ul class="nav flex-column mb-2">
                     <li class="nav-item">
                        <a class="nav-link" href="http://getbootstrap.com/docs/4.0/examples/dashboard/#">
                           <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file-text">
                              <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                              <polyline points="14 2 14 8 20 8"></polyline>
                              <line x1="16" y1="13" x2="8" y2="13"></line>
                              <line x1="16" y1="17" x2="8" y2="17"></line>
                              <polyline points="10 9 9 9 8 9"></polyline>
                           </svg>
                           Current month
                        </a>
                     </li>
                     <li class="nav-item">
                        <a class="nav-link" href="http://getbootstrap.com/docs/4.0/examples/dashboard/#">
                           <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file-text">
                              <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                              <polyline points="14 2 14 8 20 8"></polyline>
                              <line x1="16" y1="13" x2="8" y2="13"></line>
                              <line x1="16" y1="17" x2="8" y2="17"></line>
                              <polyline points="10 9 9 9 8 9"></polyline>
                           </svg>
                           Last quarter
                        </a>
                     </li>
                     <li class="nav-item">
                        <a class="nav-link" href="http://getbootstrap.com/docs/4.0/examples/dashboard/#">
                           <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file-text">
                              <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                              <polyline points="14 2 14 8 20 8"></polyline>
                              <line x1="16" y1="13" x2="8" y2="13"></line>
                              <line x1="16" y1="17" x2="8" y2="17"></line>
                              <polyline points="10 9 9 9 8 9"></polyline>
                           </svg>
                           Social engagement
                        </a>
                     </li>
                     <li class="nav-item">
                        <a class="nav-link" href="http://getbootstrap.com/docs/4.0/examples/dashboard/#">
                           <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file-text">
                              <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                              <polyline points="14 2 14 8 20 8"></polyline>
                              <line x1="16" y1="13" x2="8" y2="13"></line>
                              <line x1="16" y1="17" x2="8" y2="17"></line>
                              <polyline points="10 9 9 9 8 9"></polyline>
                           </svg>
                           Year-end sale
                        </a>
                     </li>
                  </ul>
               </div>
            </nav>

            <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
               <div class="chartjs-size-monitor" style="position: absolute; left: 0px; top: 0px; right: 0px; bottom: 0px; overflow: hidden; pointer-events: none; visibility: hidden; z-index: -1;">
                  <div class="chartjs-size-monitor-expand" style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;">
                     <div style="position:absolute;width:1000000px;height:1000000px;left:0;top:0"></div>
                  </div>
                  <div class="chartjs-size-monitor-shrink" style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;">
                     <div style="position:absolute;width:200%;height:200%;left:0; top:0"></div>
                  </div>
               </div>
               <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom">
                  <h1 class="h2">Dashboard</h1>
                  <div class="btn-toolbar mb-2 mb-md-0">
                     <div class="btn-group mr-2">
                        <button class="btn btn-sm btn-outline-secondary">Share</button>
                        <button class="btn btn-sm btn-outline-secondary">Export</button>
                     </div>
                     <button class="btn btn-sm btn-outline-secondary dropdown-toggle">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-calendar"><rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect><line x1="16" y1="2" x2="16" y2="6"></line><line x1="8" y1="2" x2="8" y2="6"></line><line x1="3" y1="10" x2="21" y2="10"></line></svg>
                This week
              </button>
                  </div>
               </div>

               <canvas class="my-4 chartjs-render-monitor" id="myChart" width="1076" height="454" style="display: block; width: 1076px; height: 454px;"></canvas>

               
            </main>
         </div>
      </div>

      <!-- Bootstrap core JavaScript
    ================================================== -->
      <!-- Placed at the end of the document so the pages load faster -->
      <script type="text/javascript" src="asserts/js/jquery-3.2.1.slim.min.js" ></script>
      <script type="text/javascript" src="asserts/js/popper.min.js" ></script>
      <script type="text/javascript" src="asserts/js/bootstrap.min.js" ></script>

      <!-- Icons -->
      <script type="text/javascript" src="asserts/js/feather.min.js" ></script>
      <script>
         feather.replace()
      </script>

      <!-- Graphs -->
      <script type="text/javascript" src="asserts/js/Chart.min.js" ></script>
      <script>
         var ctx = document.getElementById("myChart");
         var myChart = new Chart(ctx, {
            type: 'line',
            data: {
               labels: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"],
               datasets: [{
                  data: [15339, 21345, 18483, 24003, 23489, 24092, 12034],
                  lineTension: 0,
                  backgroundColor: 'transparent',
                  borderColor: '#007bff',
                  borderWidth: 4,
                  pointBackgroundColor: '#007bff'
               }]
            },
            options: {
               scales: {
                  yAxes: [{
                     ticks: {
                        beginAtZero: false
                     }
                  }]
               },
               legend: {
                  display: false,
               }
            }
         });
      </script>

   </body>

</html>
```

## 3、添加登录错误消息展示

LoginController：

```java
@Controller
public class LoginController {

    @PostMapping("/user/login")
    public String login(String username, String password, Map<String, Object> map, HttpSession session) {
        if ("admin".equals(username) && "123".equals(password)) {
            session.setAttribute("user", username);
            return "redirect:/main.html";
        }
        map.put("loginState", "login fail");
        return "login";
    }
}
```

login.html:

```html
<!--lead into js-->
		<script src="../static/asserts/js/jquery-3.2.1.slim.min.js" th:src="@{/asserts/js/jquery-3.2.1.slim.min.js}"></script>
		<script src="../static/asserts/js/popper.min.js" th:src="@{/asserts/js/popper.min.js}"></script>
		<script src="../static/asserts/js/bootstrap.min.js" th:src="@{/asserts/js/bootstrap.min.js}"></script>
		
......
<div th:switch="${loginState}">
   <div class="alert alert-danger alert-dismissible fade show" role="alert" th:case="'login fail'">
      [[#{login.result}]]
      <button type="button" class="close" data-dismiss="alert" aria-label="Close">
         <span aria-hidden="true">&times;</span>
      </button>
   </div>
   <h1 class="h3 mb-3 font-weight-normal" th:text="#{login.tip}" th:case="*">Please Sign In</h1>
</div>
```

在国际化文件添加 login.result 及相应的值。

## 4、定制拦截器

- LoginHandlerInterceptor:

```java
package com.yunche.component;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: LoginHandlerInterceptor
 * @Description:
 * @author: yunche
 * @date: 2019/01/03
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {

    /**
     * 目标方法执行前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object user = request.getSession().getAttribute("user");
        if (user == null) {
            //未登录，返回登录页面
            //request.setAttribute("msg", "没有权限请先登录");
            request.getRequestDispatcher("/index.html").forward(request, response);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
```

<div align="center">  <img src="/img/demo4.gif" width=""/> </div><br>

# 四、员工的 CRUD

未与数据库进行连接，仅作模拟操作。

## 1、引入相应的 dao 和 entity 类

```
│  ├─dao
│  │      DepartmentDao.java
│  │      EmployeeDao.java
│  │      
│  └─entities
│          Department.java
│          Employee.java
```

- DepartmentDao.java:

  ```java
  package com.yunche.dao;
  
  import java.util.Collection;
  import java.util.HashMap;
  import java.util.Map;
  
  import com.yunche.entities.Department;
  import org.springframework.stereotype.Repository;
  
  @Repository
  public class DepartmentDao {
  
  	private static Map<Integer, Department> departments = null;
  	
  	static{
  		departments = new HashMap<Integer, Department>();
  		
  		departments.put(101, new Department(101, "D-AA"));
  		departments.put(102, new Department(102, "D-BB"));
  		departments.put(103, new Department(103, "D-CC"));
  		departments.put(104, new Department(104, "D-DD"));
  		departments.put(105, new Department(105, "D-EE"));
  	}
  	
  	public Collection<Department> getDepartments(){
  		return departments.values();
  	}
  	
  	public Department getDepartment(Integer id){
  		return departments.get(id);
  	}
  	
  }
  
  ```

- EmployeeDao.java:

  ```java
  package com.yunche.dao;
  
  import java.util.Collection;
  import java.util.HashMap;
  import java.util.Map;
  
  import com.yunche.entities.Department;
  import com.yunche.entities.Employee;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.stereotype.Repository;
  
  @Repository
  public class EmployeeDao {
  
  	private static Map<Integer, Employee> employees = null;
  	
  	@Autowired
  	private DepartmentDao departmentDao;
  	
  	static{
  		employees = new HashMap<Integer, Employee>();
  
  		employees.put(1001, new Employee(1001, "E-AA", "aa@163.com", 1, new Department(101, "D-AA")));
  		employees.put(1002, new Employee(1002, "E-BB", "bb@163.com", 1, new Department(102, "D-BB")));
  		employees.put(1003, new Employee(1003, "E-CC", "cc@163.com", 0, new Department(103, "D-CC")));
  		employees.put(1004, new Employee(1004, "E-DD", "dd@163.com", 0, new Department(104, "D-DD")));
  		employees.put(1005, new Employee(1005, "E-EE", "ee@163.com", 1, new Department(105, "D-EE")));
  	}
  	
  	private static Integer initId = 1006;
  	
  	public void save(Employee employee){
  		if(employee.getId() == null){
  			employee.setId(initId++);
  		}
  		
  		employee.setDepartment(departmentDao.getDepartment(employee.getDepartment().getId()));
  		employees.put(employee.getId(), employee);
  	}
  	
  	public Collection<Employee> getAll(){
  		return employees.values();
  	}
  	
  	public Employee get(Integer id){
  		return employees.get(id);
  	}
  	
  	public void delete(Integer id){
  		employees.remove(id);
  	}
  }
  
  ```

- Department.java:

  ```
  package com.yunche.entities;
  
  public class Department {
  
  	private Integer id;
  	private String departmentName;
  
  	public Department() {
  	}
  	
  	public Department(int i, String string) {
  		this.id = i;
  		this.departmentName = string;
  	}
  
  	public Integer getId() {
  		return id;
  	}
  
  	public void setId(Integer id) {
  		this.id = id;
  	}
  
  	public String getDepartmentName() {
  		return departmentName;
  	}
  
  	public void setDepartmentName(String departmentName) {
  		this.departmentName = departmentName;
  	}
  
  	@Override
  	public String toString() {
  		return "Department [id=" + id + ", departmentName=" + departmentName + "]";
  	}
  	
  }
  
  ```

- Employee.java：

  ```java
  	package com.yunche.entities;
  
  import java.util.Date;
  
  public class Employee {
  
  	private Integer id;
      private String lastName;
  
      private String email;
      //1 male, 0 female
      private Integer gender;
      private Department department;
      private Date birth;
  
      public Integer getId() {
          return id;
      }
  
      public void setId(Integer id) {
          this.id = id;
      }
  
      public String getLastName() {
          return lastName;
      }
  
      public void setLastName(String lastName) {
          this.lastName = lastName;
      }
  
      public String getEmail() {
          return email;
      }
  
      public void setEmail(String email) {
          this.email = email;
      }
  
      public Integer getGender() {
          return gender;
      }
  
      public void setGender(Integer gender) {
          this.gender = gender;
      }
  
      public Department getDepartment() {
          return department;
      }
  
      public void setDepartment(Department department) {
          this.department = department;
      }
  
      public Date getBirth() {
          return birth;
      }
  
      public void setBirth(Date birth) {
          this.birth = birth;
      }
      public Employee(Integer id, String lastName, String email, Integer gender,
                      Department department) {
          super();
          this.id = id;
          this.lastName = lastName;
          this.email = email;
          this.gender = gender;
          this.department = department;
          this.birth = new Date();
      }
  
      public Employee() {
      }
  
      @Override
      public String toString() {
          return "Employee{" +
                  "id=" + id +
                  ", lastName='" + lastName + '\'' +
                  ", email='" + email + '\'' +
                  ", gender=" + gender +
                  ", department=" + department +
                  ", birth=" + birth +
                  '}';
      }
  }
  ```

## 2、Restful 风格的 URL

| 实验功能                             | 请求 URI | 请求方式 |
| ------------------------------------ | -------- | -------- |
| 查询所有员工                         | emps     | GET      |
| 查询某个员工 (来到修改页面)          | emp/1    | GET      |
| 来到添加页面                         | emp      | GET      |
| 添加员工                             | emp      | POST     |
| 来到修改页面（查出员工进行信息回显） | emp/1    | GET      |
| 修改员工                             | emp      | PUT      |
| 删除员工                             | emp/1    | DELETE   |

## 3、模板布局

考虑到员工的 CRUD 的页面会有许多公共的元素，所以将公共元素抽取出来作为模板。

```
│  │      └─templates
│  │          │  dashboard.html
│  │          │  login.html
│  │          │  
│  │          ├─commons
│  │          │      sidebar.html
│  │          │      topbar.html
│  │          │      
│  │          ├─emps
│  │          │      add.html
│  │          │      list.html

```

注意下面的 html 页面都是最终修改后的页面（省略了繁琐的优化过程），页面里面的一些细节，后面会介绍一些。

### 1、dashboard.html（后台首页）

```html
<!DOCTYPE html>
<!-- saved from url=(0052)http://getbootstrap.com/docs/4.0/examples/dashboard/ -->
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
   <meta name="description" content="">
   <meta name="author" content="">

   <title>Dashboard Template for Bootstrap</title>
   <!-- Bootstrap core CSS -->
   <link href="/asserts/css/bootstrap.min.css" rel="stylesheet">

   <!-- Custom styles for this template -->
   <link href="/asserts/css/dashboard.css" rel="stylesheet">
   <style type="text/css">
      /* Chart.js */

      @-webkit-keyframes chartjs-render-animation {
         from {
            opacity: 0.99
         }
         to {
            opacity: 1
         }
      }

      @keyframes chartjs-render-animation {
         from {
            opacity: 0.99
         }
         to {
            opacity: 1
         }
      }

      .chartjs-render-monitor {
         -webkit-animation: chartjs-render-animation 0.001s;
         animation: chartjs-render-animation 0.001s;
      }
   </style>
</head>

<body>
<!--引入 top bar-->
<div th:replace="commons/topbar::topbar">

</div>

<div class="container-fluid">
   <div class="row">

      <!--引入 side bar-->
      <div th:replace="commons/sidebar::sidebar(activeUrl='main.html')">

      </div>

      <!--Content-->
      <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
         <div class="chartjs-size-monitor" style="position: absolute; left: 0px; top: 0px; right: 0px; bottom: 0px; overflow: hidden; pointer-events: none; visibility: hidden; z-index: -1;">
            <div class="chartjs-size-monitor-expand" style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;">
               <div style="position:absolute;width:1000000px;height:1000000px;left:0;top:0"></div>
            </div>
            <div class="chartjs-size-monitor-shrink" style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;">
               <div style="position:absolute;width:200%;height:200%;left:0; top:0"></div>
            </div>
         </div>
         <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom">
            <h1 class="h2">Dashboard</h1>
            <div class="btn-toolbar mb-2 mb-md-0">
               <div class="btn-group mr-2">
                  <button class="btn btn-sm btn-outline-secondary">Share</button>
                  <button class="btn btn-sm btn-outline-secondary">Export</button>
               </div>
               <button class="btn btn-sm btn-outline-secondary dropdown-toggle">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-calendar"><rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect><line x1="16" y1="2" x2="16" y2="6"></line><line x1="8" y1="2" x2="8" y2="6"></line><line x1="3" y1="10" x2="21" y2="10"></line></svg>
                  This week
               </button>
            </div>
         </div>

         <canvas class="my-4 chartjs-render-monitor" id="myChart" width="1076" height="454" style="display: block; width: 1076px; height: 454px;"></canvas>


      </main>

   </div>
</div>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script type="text/javascript" src="/asserts/js/jquery-3.2.1.slim.min.js" ></script>
<script type="text/javascript" src="/asserts/js/popper.min.js" ></script>
<script type="text/javascript" src="/asserts/js/bootstrap.min.js" ></script>

<!-- Icons -->
<script type="text/javascript" src="/asserts/js/feather.min.js" ></script>
<script>
    feather.replace()
</script>

<!-- Graphs -->
<script type="text/javascript" src="/asserts/js/Chart.min.js" ></script>
<script>
    var ctx = document.getElementById("myChart");
    var myChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"],
            datasets: [{
                data: [15339, 21345, 18483, 24003, 23489, 24092, 12034],
                lineTension: 0,
                backgroundColor: 'transparent',
                borderColor: '#007bff',
                borderWidth: 4,
                pointBackgroundColor: '#007bff'
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: false
                    }
                }]
            },
            legend: {
                display: false,
            }
        }
    });
</script>

</body>

</html>
```

### 2、sidebar.html（侧边栏模板）

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<!--side bar-->
<nav class="col-md-2 d-none d-md-block bg-light sidebar" th:fragment="sidebar">
    <div class="sidebar-sticky">
        <ul class="nav flex-column">
            <li class="nav-item">
                <a class="nav-link active" href="#" th:href="@{/main.html}" th:class="${activeUrl}=='main.html'?'nav-link active':'nav-link'">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-home">
                        <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path>
                        <polyline points="9 22 9 12 15 12 15 22"></polyline>
                    </svg>
                    Dashboard <span class="sr-only">(current)</span>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="http://getbootstrap.com/docs/4.0/examples/dashboard/#">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file">
                        <path d="M13 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V9z"></path>
                        <polyline points="13 2 13 9 20 9"></polyline>
                    </svg>
                    Orders
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="http://getbootstrap.com/docs/4.0/examples/dashboard/#">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-shopping-cart">
                        <circle cx="9" cy="21" r="1"></circle>
                        <circle cx="20" cy="21" r="1"></circle>
                        <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"></path>
                    </svg>
                    Products
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#" th:href="@{/emps}" th:class="${activeUrl}=='emps'?'nav-link active':'nav-link'">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-users">
                        <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
                        <circle cx="9" cy="7" r="4"></circle>
                        <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
                        <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
                    </svg>
                    员工管理
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-bar-chart-2">
                        <line x1="18" y1="20" x2="18" y2="10"></line>
                        <line x1="12" y1="20" x2="12" y2="4"></line>
                        <line x1="6" y1="20" x2="6" y2="14"></line>
                    </svg>
                    Reports
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-layers">
                        <polygon points="12 2 2 7 12 12 22 7 12 2"></polygon>
                        <polyline points="2 17 12 22 22 17"></polyline>
                        <polyline points="2 12 12 17 22 12"></polyline>
                    </svg>
                    Integrations
                </a>
            </li>
        </ul>

        <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
            <span>Saved reports</span>
            <a class="d-flex align-items-center text-muted" href="#">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-plus-circle"><circle cx="12" cy="12" r="10"></circle><line x1="12" y1="8" x2="12" y2="16"></line><line x1="8" y1="12" x2="16" y2="12"></line></svg>
            </a>
        </h6>
        <ul class="nav flex-column mb-2">
            <li class="nav-item">
                <a class="nav-link" href="#">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file-text">
                        <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                        <polyline points="14 2 14 8 20 8"></polyline>
                        <line x1="16" y1="13" x2="8" y2="13"></line>
                        <line x1="16" y1="17" x2="8" y2="17"></line>
                        <polyline points="10 9 9 9 8 9"></polyline>
                    </svg>
                    Current month
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file-text">
                        <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                        <polyline points="14 2 14 8 20 8"></polyline>
                        <line x1="16" y1="13" x2="8" y2="13"></line>
                        <line x1="16" y1="17" x2="8" y2="17"></line>
                        <polyline points="10 9 9 9 8 9"></polyline>
                    </svg>
                    Last quarter
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file-text">
                        <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                        <polyline points="14 2 14 8 20 8"></polyline>
                        <line x1="16" y1="13" x2="8" y2="13"></line>
                        <line x1="16" y1="17" x2="8" y2="17"></line>
                        <polyline points="10 9 9 9 8 9"></polyline>
                    </svg>
                    Social engagement
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file-text">
                        <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                        <polyline points="14 2 14 8 20 8"></polyline>
                        <line x1="16" y1="13" x2="8" y2="13"></line>
                        <line x1="16" y1="17" x2="8" y2="17"></line>
                        <polyline points="10 9 9 9 8 9"></polyline>
                    </svg>
                    Year-end sale
                </a>
            </li>
        </ul>
    </div>
</nav>
</body>
</html>
```

### 3、topbar.html（顶部栏模板）

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<!--top bar-->
<nav class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0" th:fragment = "topbar">
    <a class="navbar-brand col-sm-3 col-md-2 mr-0" href="#">[[${session.user}]]</a>
    <input class="form-control form-control-dark w-100" type="text" placeholder="Search" aria-label="Search">
    <ul class="navbar-nav px-3">
        <li class="nav-item text-nowrap">
            <a class="nav-link" href="http://getbootstrap.com/docs/4.0/examples/dashboard/#">Sign out</a>
        </li>
    </ul>
</nav>
</body>
</html>
```

### 4、list.html（员工列表页面）

```html
<!DOCTYPE html>
<!-- saved from url=(0052)http://getbootstrap.com/docs/4.0/examples/dashboard/ -->
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Dashboard Template for Bootstrap</title>
    <!-- Bootstrap core CSS -->
    <link href="/asserts/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="/asserts/css/dashboard.css" rel="stylesheet">
    <style type="text/css">
        /* Chart.js */

        @-webkit-keyframes chartjs-render-animation {
            from {
                opacity: 0.99
            }
            to {
                opacity: 1
            }
        }

        @keyframes chartjs-render-animation {
            from {
                opacity: 0.99
            }
            to {
                opacity: 1
            }
        }

        .chartjs-render-monitor {
            -webkit-animation: chartjs-render-animation 0.001s;
            animation: chartjs-render-animation 0.001s;
        }
    </style>
</head>

<body>
<!--引入 top bar-->
<div th:replace="commons/topbar::topbar">

</div>

<div class="container-fluid">
    <div class="row">

        <!--引入 side bar-->
        <div th:replace="commons/sidebar::sidebar(activeUrl='emps')">

        </div>


        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
            <h2><a class="btn btn-sm btn-success" href="emp" th:href="@{/emp}"> 员工添加 </a></h2>
            <div class="table-responsive">
                <table class="table table-striped table-sm">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>lastName</th>
                        <th>email</th>
                        <th>gender</th>
                        <th>department</th>
                        <th>birth</th>
                        <th> 操作 </th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr th:each="emp:${emps}">
                        <td th:text="${emp.id}"></td>
                        <td th:text="${emp.lastName}"></td>
                        <td th:text="${emp.email}"></td>
                        <td th:text="${emp.gender}=='0'?' 女 ':' 男 '"></td>
                        <td th:text="${emp.department.departmentName}"></td>
                        <td th:text="${#dates.format(emp.birth, 'yyyy-MM-dd HH:mm')}"></td>
                        <td>
                            <a class="btn btn-sm btn-primary" th:href="@{/emp/}+${emp.id}"> 编辑 </a>
                            <!-- Button trigger modal -->
                            <button type="button" class="btn btn-danger btn-sm" data-toggle="modal" th:attr="data-target=@{#del}+${emp.id}">
                                删除
                            </button>
                            <!-- Modal -->
                            <div class="modal fade" th:id="@{del}+${emp.id}" tabindex="-1" role="dialog"th:attr="aria-labelledby=@{delLabe}+${emp.id}" aria-hidden="true">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" th:id="@{delLabe}+${emp.id}"> 删除员工 </h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <p class="text-danger"> 确定要删除？该操作不可撤销！</p>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal"> 取消 </button>
                                            <button type="button" class="btn btn-primary deleteBtn" th:attr="delurl=@{/emp/}+${emp.id}"> 确定 </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </main>
        <form id="delForm" method="post">
            <input type="hidden" name="_method" value="delete">
        </form>

    </div>
</div>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script type="text/javascript" src="/asserts/js/jquery-3.2.1.slim.min.js"></script>
<script type="text/javascript" src="/asserts/js/popper.min.js"></script>
<script type="text/javascript" src="/asserts/js/bootstrap.min.js"></script>

<!-- Icons -->
<script type="text/javascript" src="/asserts/js/feather.min.js"></script>
<script>
    feather.replace();
</script>
<script>
    $(document).ready(function () {
        $(".deleteBtn").click(function () {
            $("#delForm").attr("action",$(this).attr("delurl")).submit();
        });
    });

</script>

</body>

</html>
```

### 5、add.html（员工的添加、修改二合一页面）

```html
<!DOCTYPE html>
<!-- saved from url=(0052)http://getbootstrap.com/docs/4.0/examples/dashboard/ -->
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Dashboard Template for Bootstrap</title>
    <!-- Bootstrap core CSS -->
    <link href="/asserts/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="/asserts/css/dashboard.css" rel="stylesheet">
    <style type="text/css">
        /* Chart.js */

        @-webkit-keyframes chartjs-render-animation {
            from {
                opacity: 0.99
            }
            to {
                opacity: 1
            }
        }

        @keyframes chartjs-render-animation {
            from {
                opacity: 0.99
            }
            to {
                opacity: 1
            }
        }

        .chartjs-render-monitor {
            -webkit-animation: chartjs-render-animation 0.001s;
            animation: chartjs-render-animation 0.001s;
        }
    </style>
</head>

<body>
<!--引入 top bar-->
<div th:replace="commons/topbar::topbar">

</div>

<div class="container-fluid">
    <div class="row">

        <!--引入 side bar-->
        <div th:replace="commons/sidebar::sidebar(activeUrl='emps')">

        </div>

        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
            <!--需要区分是员工修改还是添加；-->
            <form th:action="@{/emp}" method="post">
                <!--添加隐藏域，用于发送 put 请求修改表单-->
                <input type="hidden" name="_method" value="put" th:if="${emp!=null}"/>
                <input type="hidden" name="id" th:if="${emp!=null}" th:value="${emp.id}">
                <div class="form-group">
                    <label>LastName</label>
                    <input name="lastName" type="text" class="form-control" placeholder="zhangsan" th:value="${emp}!=null?${emp.lastName}:''">
                </div>

                <div class="form-group">
                    <label>Email</label>
                    <input name="email" type="text" class="form-control" placeholder="xxx@qq.com"
                    th:value="${emp}!=null?${emp.email}:''">
                </div>

                <div class="form-group">
                    <label>Gender</label>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="gender" value="1" th:checked="${emp}!=null?(${emp.gender}==1):false">
                        <label class="form-check-label"> 男 </label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="gender" value="0" th:checked="${emp}!=null?(${emp.gender}==0):false">
                        <label class="form-check-label"> 女 </label>
                    </div>
                </div>

                <div class="form-group">
                    <label>Department</label>
                    <select class="form-control" name="department.id">
                        <option th:selected="${dept.id == emp.department.id}" th:value="${dept.id}" th:each="dept:${departments}" th:text="${dept.departmentName}" th:if="${emp!=null}"></option>
                        <option th:value="${dept.id}" th:each="dept:${departments}" th:text="${dept.departmentName}" th:if="${emp==null}"></option>
                    </select>
                </div>

                <div class="form-group">
                    <label>Birth</label>
                    <input name="birth" type="text" class="form-control" placeholder="2017/01/01 12:23" th:value="${emp!=null}?${#dates.format(emp.birth,'yyyy/MM/dd HH:mm')}">
                </div>
                <button type="submit" class="btn btn-primary" th:text="${emp==null}?' 添加 ':' 修改 '"> 添加 </button>
            </form>
        </main>

    </div>
</div>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script type="text/javascript" src="/asserts/js/jquery-3.2.1.slim.min.js" ></script>
<script type="text/javascript" src="/asserts/js/popper.min.js" ></script>
<script type="text/javascript" src="/asserts/js/bootstrap.min.js" ></script>

<!-- Icons -->
<script type="text/javascript" src="/asserts/js/feather.min.js" ></script>
<script>
    feather.replace()
</script>


</body>

</html>
```

## 4、EmployeeController（处理员工请求控制器）

```java
package com.yunche.controller;

import com.yunche.dao.DepartmentDao;
import com.yunche.dao.EmployeeDao;
import com.yunche.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 * @ClassName: EmployeeController
 * @Description:
 * @author: yunche
 * @date: 2019/01/03
 */
@Controller
public class EmployeeController {

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    DepartmentDao departmentDao;
    @GetMapping("/emps")
    public String list(Model model) {
        model.addAttribute("emps", employeeDao.getAll());
        return "emps/list";
    }


    /**
     * 来到添加页面
     *
     * @return
     */
    @GetMapping("/emp")
    public String toAddPage(Model model) {
        model.addAttribute("departments", departmentDao.getDepartments());
        return "emps/add";
    }

    /**
     * 添加员工
     * SpringMVC 自动将请求参数和入参对象的属性进行一一绑定；
     * 要求请求参数的名字和 javaBean 入参的对象里面的属性名是一样的
     * @return
     */
    @PostMapping("/emp")
    public String addEmployee(Employee employee) {
        employeeDao.save(employee);
        return "redirect:/emps";
    }

    /**
     * 来到修改页面
     *
     * @return
     */
    @GetMapping("/emp/{id}")
    public String toEditPage(@PathVariable("id") Integer id, Model model) {
        Employee employee = employeeDao.get(id);
        model.addAttribute("emp", employee);
        model.addAttribute("departments", departmentDao.getDepartments());
        return "emps/add";
    }

    /**
     * 修改员工
     * @param employee
     * @return
     */
    @PutMapping("/emp")
    public String editEmployee(Employee employee) {
        employeeDao.save(employee);
        return "redirect:/emps";
    }

    /**
     * 删除员工
     * @param id
     * @return
     */
    @DeleteMapping("/emp/{id}")
    public String removeEmployee(@PathVariable("id") Integer id) {
        employeeDao.delete(id);
        return "redirect:/emps";
    }
}
```

## 5、细节讲解

### 1、PUT、DELTE 请求

由于 form 表单只允许使用 POST 请求，那么怎么以 PUT、DELETE 请求提交表单呢？可以在表单中添加一个隐藏域（Spring Boot 中 的 WebMvcAutoConfiguration 中有相应处理隐藏域的方法），使用隐藏域来达到以 PUT、DELETE 请求提交表单的效果。如：

```html
   <!--添加隐藏域，用于发送 put 请求修改表单-->
                <input type="hidden" name="_method" value="put" th:if="${emp!=null}"/>
```

### 2、给模板页面传参

在这里的例子是我们要实现当进入后台首页时，侧边栏上的 dashboard 高亮；当进入员工管理时，侧边栏的员工管理高亮。要实现这样的效果，我们可以使用给模板页面传参的方式，用来区别到底进入了哪个页面，然后根据参数的不同，来实现相应的效果。

比如给模板页面传入参数：

```html
 <!--引入 side bar-->
        <div th:replace="commons/sidebar::sidebar(activeUrl='emps')">

        </div>
```

根据参数的值实现相应的效果：

```html
         <li class="nav-item">
             
                <a class="nav-link" href="#" th:href="@{/emps}" th:class="${activeUrl}=='emps'?'nav-link active':'nav-link'">
                    
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-users">
                        <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
                        <circle cx="9" cy="7" r="4"></circle>
                        <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
                        <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
                    </svg>
                    员工管理
                </a>
            </li>
```

### 3、添加、修改页面共用

我们添加、修改是共用一个页面的，所以应该根据某个变量来区分，到底该显示添加页面的效果还是修改页面的效果。在这里是通过 emp 变量, 如果页面能够获取到 emp 变量的值，说明应该进行修改操作；否则，进行添加操作。

```java
     model.addAttribute("emp", employee);
```

```html
<button type="submit" class="btn btn-primary" th:text="${emp==null}?' 添加 ':' 修改 '"> 添加 </button>
```

## 6、定制错误页面

重新修改拦截器，便于演示。

MyMvcConfig：

```java
  @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        //排除静态资源的拦截：*.css , *.js
//        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns("/index.html", "/index", "/user/login", "/asserts/**","/webjars/**");

        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/main.html", "/emps", "/emp/*");
    }
```

默认的处理方法是根据相应状态码的值，在 templates/error 中的文件夹中寻找相应匹配的 html 页面，可以

编写 4xx.html, 5xx.html，也可编写 404.html，精确优先。

404.html:

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>404 NOT FOUND</title>
</head>
<body>
<img src="/asserts/img/very_sorry.png">
</body>
</html>
```

## 7、阶段演示效果

<div align="center">  <img src="/img/demo5.gif" width=""/> </div><br>
