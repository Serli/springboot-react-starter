package com.serli.starter.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.FileReader;
import java.io.IOException;

@Controller
public class HomeController {

    // Markdown conversion utils
    private final Parser parser;
    private final HtmlRenderer renderer;

    @Autowired
    public HomeController() {
        // Create markdown conversion stuff
        this.parser = Parser.builder().build();
        this.renderer = HtmlRenderer.builder().build();
    }

    @ModelAttribute("requestURI")
    public String contextPath(final HttpServletRequest request) {
        return request.getRequestURI();
    }

    @GetMapping("/")
    public String index(Model model) {
        try {
            Node node = parser.parseReader(new FileReader("README.md"));
            String documentationAsHtml = renderer.render(node);
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
