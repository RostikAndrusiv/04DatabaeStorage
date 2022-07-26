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
    private static final String SQL_SAVE = "insert into db_entity (file_name, file_binary) values (?,?)";
    private static final String SQL_PROCEDURE_SAVE = "{call insert_data(?, ?)}";
    private static final String SQL_PROCEDURE_FIND = "select * from findFileByName(?)";
    private static final String SQL_DELETE = "DELETE FROM db_entity WHERE file_name = ?";

    DbManager dbManager = DbManager.getInstance();

    public boolean save(String filePath) {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE, Statement.RETURN_GENERATED_KEYS);
             InputStream fis = new FileInputStream(filePath)) {
            File file = new File(filePath);
            preparedStatement.setString(1, file.getName());
            preparedStatement.setBinaryStream(2, fis, (int) file.length());
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException | IOException e) {
            log.info(e.getMessage());
            return false;
        }
    }

//    public boolean save(String filePath) {
//        try (Connection connection = dbManager.getConnection();
//             CallableStatement cstmt = connection.prepareCall("{CALL insert_data(?, ?)}");
//             InputStream fis = new FileInputStream(filePath)) {
//            File file = new File(filePath);
//            cstmt.setString(1, file.getName());
//            cstmt.setBinaryStream(2, fis, (int) file.length());
//            cstmt.executeUpdate();
//            return true;
//
//        } catch (SQLException | IOException e) {
//            log.info(e.getMessage());
//            return false;
//        }
//    }

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

    public void delete(String name) {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement pst = connection.prepareStatement(SQL_DELETE);
        ) {
            pst.setString(1, name);
            pst.execute();
        } catch (SQLException e) {
            log.info(e.getMessage());
        }
    }
}
