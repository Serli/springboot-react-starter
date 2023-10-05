package com.serli.starter.repositories;

import com.serli.starter.models.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TodoRepository {

    private static final int MAX_CONTENT_LENGTH = 100;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final TodoMapper todoMapper;

    @Autowired
    public TodoRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, TodoMapper todoMapper) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.todoMapper = todoMapper;
    }

    public List<Todo> findAll() {
        String sqlQuery = """
                SELECT t.id, t.content
                FROM Todo t
                """;
        return namedParameterJdbcTemplate.query(sqlQuery, todoMapper);
    }

    public Optional<Todo> findById(UUID id) {
        String sqlQuery = """
                SELECT t.id, t.content
                FROM Todo t
                WHERE t.id =:id;
                """;
        MapSqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        return namedParameterJdbcTemplate.query(sqlQuery, param, todoMapper).stream().findFirst();
    }

    public UUID create(String content) {
        String sqlQuery = """
                INSERT INTO Todo (content)
                VALUES(:content)
                RETURNING id;
                """;

        String sanitizedContent = content.length() > MAX_CONTENT_LENGTH ? content.substring(0, MAX_CONTENT_LENGTH) : content;
        MapSqlParameterSource param = new MapSqlParameterSource().addValue("content", sanitizedContent);
        return namedParameterJdbcTemplate.queryForObject(sqlQuery, param, UUID.class);
    }

    public void delete(UUID id) {
        String sqlQuery = """
                DELETE FROM Todo
                WHERE id = :id;
                """;
        MapSqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        namedParameterJdbcTemplate.update(sqlQuery, param);
    }
}
