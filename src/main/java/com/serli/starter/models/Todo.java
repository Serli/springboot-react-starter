package com.serli.starter.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.time.LocalDateTime;
import java.util.UUID;

public class Todo {

    public final UUID id;
    public final String title;
    public final String content;
    public final TodoStatus status;
    public final LocalDateTime createdAt;

    public Todo(
            UUID id,
            String title,
            String content,
            TodoStatus status,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.status = status;
        this.createdAt = createdAt;
    }

    public boolean isNew() {
        return TodoStatus.NEW.equals(status);
    }

    public boolean isInProgress() {
        return TodoStatus.IN_PROGRESS.equals(status);
    }

    public JsonNode toJson() {
        return toJson(new ObjectMapper());
    }

    public JsonNode toJson(ObjectMapper objectMapper) {
        ObjectNode jsonNode = objectMapper.createObjectNode();
        jsonNode
                .put("id", id.toString())
                .put("title", title)
                .put("content", content)
                .put("status", status.toString())
                .put("created_at", createdAt.toString());
        return jsonNode;
    }

}
