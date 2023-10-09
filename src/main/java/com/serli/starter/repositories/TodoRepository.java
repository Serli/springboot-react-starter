package com.serli.starter.repositories;

import com.serli.starter.models.NewTodo;
import com.serli.starter.models.Todo;
import com.serli.starter.models.TodoStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TodoRepository {

    private static final int MAX_TITLE_LENGTH = 100;
    private static final int MAX_CONTENT_LENGTH = 500;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final TodoMapper todoMapper;

    @Autowired
    public TodoRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, TodoMapper todoMapper) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.todoMapper = todoMapper;
    }

    public List<Todo> findAll() {
        String sqlQuery = """
                SELECT t.id, t.title, t.content, t.status, t.created_at
                FROM Todo t
                """;
        return namedParameterJdbcTemplate.query(sqlQuery, todoMapper);
    }

    public Optional<Todo> findById(UUID id) {
        String sqlQuery = """
                SELECT t.id, t.title, t.content, t.status, t.created_at
                FROM Todo t
                WHERE t.id =:id;
                """;
        MapSqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        return namedParameterJdbcTemplate.query(sqlQuery, param, todoMapper).stream().findFirst();
    }

    public UUID create(NewTodo newTodo) {
        String sqlQuery = """
                INSERT INTO Todo (title, content)
                VALUES(:title, :content)
                RETURNING id;
                """;

        String title = newTodo.title;
        String content = newTodo.content;
        String sanitizedTitle = title.length() > MAX_TITLE_LENGTH ? title.substring(0, MAX_TITLE_LENGTH) : title;
        String sanitizedContent = content.length() > MAX_CONTENT_LENGTH ? content.substring(0, MAX_CONTENT_LENGTH) : content;
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("title", sanitizedTitle)
                .addValue("content", sanitizedContent);
        return namedParameterJdbcTemplate.queryForObject(sqlQuery, param, UUID.class);
    }

    public void updateStatus(UUID id, TodoStatus newStatus) {
        String sqlQuery = """
                UPDATE Todo
                SET status = :status::todostatus
                WHERE id = :id;
                """;
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("status", newStatus.toString());
        namedParameterJdbcTemplate.update(sqlQuery, param);
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
