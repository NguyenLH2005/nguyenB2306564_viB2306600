package com.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
 public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/",
                "root",
                ""
            );
            System.out.println("Kết nối thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}