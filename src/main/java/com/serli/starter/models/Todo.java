package com.serli.starter.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.sql.Timestamp;
import java.util.UUID;

public class Todo {

    public final UUID id;
    public final String content;
    public final String title;
    public final Integer status;
    public final Timestamp createdAt;

    public Todo(UUID id, String content, String title, Integer status, Timestamp createdAt) {
        this.id = id;
        this.content = content;
        this.title=title;
        this.status=status;
        this.createdAt = createdAt;
    }

    public JsonNode toJson() {
        return toJson(new ObjectMapper());
    }

    public JsonNode toJson(ObjectMapper objectMapper) {
        ObjectNode jsonNode = objectMapper.createObjectNode();
        jsonNode
                .put("id", id.toString())
                .put("title", title)
                .put("status", status)
                .put("createdAt", createdAt.getTime())
                .put("content", content);
        return jsonNode;
    }
}
