package org.example;

import org.example.service.Service;

/**
 * Hello world!
 */

public class App {

    public static void main(String[] args) {
        Service service = new Service();
        service.dropInsertProcedure();
        service.createInsertProcedure();
        service.upload("c:/2/22222.jpg");
        service.download("22222.jpg", "d:/7/22222.jpg");

    }
}
