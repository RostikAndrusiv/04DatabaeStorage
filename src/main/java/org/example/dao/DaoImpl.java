package org.example.dao;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.db.DbManager;
import org.example.entity.DbEntity;

import java.io.*;
import java.sql.*;
import java.util.Optional;

@Slf4j
@Data
@NoArgsConstructor
public class DaoImpl {

    private static final String EXCEPTION_MESSAGE = "SQL Exception: %s";

    private static final String SQL_PROCEDURE_SAVE = "call insert_data(?, ?)";

    private static final String SQL_PROCEDURE_FIND = "select * from findFileByName(?)";

    private static final String DROP_PROCEDURE_SQL = "DROP PROCEDURE insert_data";

    private static final String CREATE_PROCEDURE_SQL = "CREATE PROCEDURE insert_data(f_name varchar, f_binary bytea) " +
            "LANGUAGE SQL " +
            "AS $$ " +
            "INSERT INTO db_entity(file_name, file_binary) " +
            "VALUES (f_name, f_binary); " +
            "$$";

    DbManager dbManager = DbManager.getInstance();

    public boolean save(DbEntity entity) {
        try (Connection connection = dbManager.getConnection();
             CallableStatement cstmt = connection.prepareCall(SQL_PROCEDURE_SAVE)) {
            cstmt.setString(1, entity.getName());
            cstmt.setBytes(2, entity.getContent());
            cstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            log.info(e.getMessage());
            return false;
        }
    }

    public Optional<DbEntity> findUsingStored(String name) {
        try (Connection connection = dbManager.getConnection();
             CallableStatement cstmt = connection.prepareCall(SQL_PROCEDURE_FIND)) {
            DbEntity entity = null;
            cstmt.setString(1, name);
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                entity = new DbEntity(rs.getLong(1), rs.getString(2), rs.getBytes(3));
            }
            return Optional.ofNullable(entity);
        } catch (SQLException e) {
            log.info(e.getMessage());
            return Optional.empty();
        }
    }

    public void createInsertDataProcedure() {
        execute(CREATE_PROCEDURE_SQL);
    }

    public void dropInsertDataProcedure() {
        execute(DROP_PROCEDURE_SQL);
    }

    private void execute(String sql) {
        try (Connection connection = dbManager.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeQuery(sql);
        } catch (SQLException e) {
            log.info(e.getMessage());
        }
    }
}
