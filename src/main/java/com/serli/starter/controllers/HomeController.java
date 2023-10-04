package com.serli.starter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    public HomeController() {
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/react-example")
    public String apropos(Model model) {
        model.addAttribute("name", "world");
        return "react-example";
    }

}
