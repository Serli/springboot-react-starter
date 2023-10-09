package com.serli.starter.repositories;

import com.serli.starter.models.Todo;
import com.serli.starter.models.TodoStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class TodoMapper implements RowMapper<Todo> {


    @Override
    public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Todo(
                UUID.fromString(rs.getString("id")),
                rs.getString("title"),
                rs.getString("content"),
                TodoStatus.valueOf(rs.getString("status")),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
}
