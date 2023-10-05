package com.serli.starter.controllers;

import com.serli.starter.models.Todo;
import com.serli.starter.repositories.TodoRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
public class TodoController {

    private final TodoRepository todoRepository;

    @Autowired
    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @ModelAttribute("requestURI")
    public String contextPath(final HttpServletRequest request) {
        return request.getRequestURI();
    }

    @GetMapping("/todos")
    public String list(Model model) {
        List<Todo> todos = todoRepository.findAll();
        model.addAttribute("todos", todos);
        return "todos";
    }

    @PostMapping("/todos/_create")
    public String create(@RequestParam String content, Model model) {
        todoRepository.create(content);
        return "redirect:/todos";
    }

    @PostMapping("/todos/_delete")
    public String delete(@RequestParam String id, Model model) {
        todoRepository.delete(UUID.fromString(id));
        return "redirect:/todos";
    }

}
