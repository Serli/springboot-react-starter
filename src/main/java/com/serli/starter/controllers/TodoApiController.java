package com.serli.starter.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.serli.starter.models.Todo;
import com.serli.starter.repositories.TodoRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class TodoApiController {

    private final TodoRepository todoRepository;

    @Autowired
    public TodoApiController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @ModelAttribute("requestURI")
    public String contextPath(final HttpServletRequest request) {
        return request.getRequestURI();
    }

    @GetMapping(path = "/api/v1/todos", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String all() {
        List<Todo> todos = todoRepository.findAll();

        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode arrayNode = objectMapper.createArrayNode();
        todos.forEach(todo -> arrayNode.add(todo.toJson()));
        return arrayNode.toString();
    }

    @GetMapping(path = "/api/v1/todos/{id}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String byId(@PathVariable String id, HttpServletResponse response) {
        try {
            Optional<Todo> todoOpt = todoRepository.findById(UUID.fromString(id));
            if (todoOpt.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return """
                        {"message": "Not found"}
                        """;
            } else {
                return todoOpt.get().toJson().toString();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return """
                    {"message": "Bad request"}
                    """;
        }
    }

    @PostMapping(path = "/api/v1/todos", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String create(@RequestBody String body, HttpServletResponse response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(body);
            JsonNode contentNode = jsonNode.get("content");
            JsonNode titleNode = jsonNode.get("title");
            JsonNode statusNode = jsonNode.get("status");
            if (contentNode == null || contentNode.isNull()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return """
                        {"message": "Bad request"}
                        """;
            }
            UUID uuid = todoRepository.create(titleNode.asText(),contentNode.asText(), statusNode.asInt());
            response.setStatus(HttpServletResponse.SC_CREATED);
            return "{\"id\": \"" + uuid.toString() + "\"}";
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return """
                    {"message": "Bad request"}
                    """;
        }
    }

    @DeleteMapping(path = "/api/v1/todos/{id}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String delete(@PathVariable String id, HttpServletResponse response) {
        try {
            UUID uuid = UUID.fromString(id);
            Optional<Todo> todoOpt = todoRepository.findById(uuid);
            if (todoOpt.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return """
                        {"message": "Not found"}
                        """;
            } else {
                todoRepository.delete(uuid);
                return todoOpt.get().toJson().toString();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return """
                    {"message": "Bad request"}
                    """;
        }
    }

}
