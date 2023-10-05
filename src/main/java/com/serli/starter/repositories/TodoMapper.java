package com.serli.starter.repositories;

import com.serli.starter.models.Todo;
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
                rs.getString("content"),
                rs.getString("title"),
                rs.getInt("status"),
                rs.getTimestamp("created_at")
        );
    }
}
