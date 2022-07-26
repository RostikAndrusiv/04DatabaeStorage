package org.example;

import org.example.dao.DaoImpl;
import org.example.service.Service;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Service service = new Service();
        DaoImpl dao = new DaoImpl();

        service.upload("c:/2/asdasd.jpg");
        service.download("1.jpg", "d:/7/11111.jpg");

    }
}
