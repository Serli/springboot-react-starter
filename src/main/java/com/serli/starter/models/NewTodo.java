package com.serli.starter.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public class NewTodo {

    public final String title;
    public final String content;

    public NewTodo(
            String title,
            String content
    ) {
        this.title = title;
        this.content = content;
    }

    public static Optional<NewTodo> fromJson(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(json);
            JsonNode titleNode = jsonNode.get("title");
            JsonNode contentNode = jsonNode.get("content");
            if (titleNode == null || titleNode.isNull()
                    || contentNode == null || contentNode.isNull()) {
                return Optional.empty();
            } else {
                return Optional.of(
                        new NewTodo(
                                titleNode.asText(),
                                contentNode.asText()
                        )
                );
            }
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }
}
