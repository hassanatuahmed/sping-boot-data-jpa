package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.service.annotation.GetExchange;

@Controller
public class IndexController {

    @GetMapping("/home")
    @ResponseBody
    public String controllerName(){
        return "Hello World";
    }
}
