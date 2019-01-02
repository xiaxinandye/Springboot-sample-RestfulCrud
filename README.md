# 介绍

此 Demo 是为了熟悉 Spring Boot 和 thymeleaf 的使用，所以适用于刚接触 Spring Boot 不久的新手，此项目是一个简单的 Web 版的员工 CRUD，项目内容来源于[尚硅谷谷粒学院 Spring Boot 核心技术篇 ](http://www.gulixueyuan.com/course/231)，我根据自己的理解，又重新地动手做了一些，其中也发现了一些自己的薄弱点，并针对这些薄弱点进行巩固，以便尽快掌握 Spring Boot。

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

使用 thymeleaf  对 login.html 完成 css、js 等链接的修改

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
         <a class="btn btn-sm"> 中文 </a>
         <a class="btn btn-sm">English</a>
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