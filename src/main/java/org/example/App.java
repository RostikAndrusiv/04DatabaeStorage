package org.example;

import org.example.dao.DaoImpl;
import org.example.service.Service;

/**
 * Hello world!
 */

public class App {
    private static final String CREATE_PROCEDURE_SQL = "CREATE PROCEDURE insert_data(f_name varchar, f_binary bytea) " +
            "LANGUAGE SQL " +
            "AS $$ " +
            "INSERT INTO db_entity(file_name, file_binary) " +
            "VALUES (f_name, f_binary); " +
            "$$";

    private static final String DROP_PROCEDURE_SQL = "DROP PROCEDURE insert_data";

    public static void main(String[] args) {
        Service service = new Service();
        DaoImpl dao = new DaoImpl();
        dao.dropProcedure(DROP_PROCEDURE_SQL);
        dao.createInsertProcedure(CREATE_PROCEDURE_SQL);
        service.upload("c:/2/22222.jpg");
        service.download("22222.jpg", "d:/7/22222.jpg");

    }
}
