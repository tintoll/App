package com.example.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by tinoll on 2017. 1. 10..
 */
@Controller
public class LoginController {


    @RequestMapping("loginForm")
    String loginForm(){
        return "loginForm";
    }
}
