package com.serli.starter.controllers;

import com.serli.starter.services.MarkdownService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.FileReader;
import java.io.IOException;

@Controller
public class HomeController {

    private final MarkdownService markdownService;

    @Autowired
    public HomeController(MarkdownService markdownService) {
        this.markdownService = markdownService;
    }

    @ModelAttribute("requestURI")
    public String contextPath(final HttpServletRequest request) {
        return request.getRequestURI();
    }

    @GetMapping("/")
    public String index(Model model) {
        try {
            String documentationAsHtml = markdownService.convertToHtml(new FileReader("README.md"));
            model.addAttribute("documentationAsHtml", documentationAsHtml);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("documentationAsHtml", """
                    <div class="alert alert-danger" role="alert">
                      Erreur lors du chargement de la documentation !
                    </div>
                    """);
        }
        return "index";
    }

    @GetMapping("/react-example")
    public String reactExample(Model model) {
        model.addAttribute("name", "world");
        return "react-example";
    }

    @GetMapping("/bootstrap-theme")
    public String apropos(Model model) {
        return "bootstrap-theme";
    }

}
