package com.yunche.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @ClassName: LoginController
 * @Description:
 * @author: yunche
 * @date: 2019/01/02
 */
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
