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
