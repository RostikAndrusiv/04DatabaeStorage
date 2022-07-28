package org.example;

import org.example.dao.DaoImpl;
import org.example.service.Service;

/**
 * Hello world!
 */

//TODO limit 200MB

public class App {
    public static void main(String[] args) {
        Service service = new Service();
        DaoImpl dao = new DaoImpl();

        service.upload("c:/2/10.avi");
        service.download("1.jpg", "d:/7/10.avi");

    }
}
