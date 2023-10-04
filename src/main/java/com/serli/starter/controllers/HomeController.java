package com.serli.starter.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {

    @Autowired
    public HomeController() {
    }

    @ModelAttribute("requestURI")
    public String contextPath(final HttpServletRequest request) {
        return request.getRequestURI();
    }

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/react-example")
    public String apropos(Model model) {
        model.addAttribute("name", "world");
        return "react-example";
    }

}
