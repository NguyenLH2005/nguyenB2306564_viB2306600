package com.example;

import java.sql.Connection;
import com.example.db.DBConnection;

// Luồng hoạt dộng Main -> service -> Repository -> DB
// Main chạy console
// service là nơi chứa logic nghiệp vụ
// Repository là nơi chứa logic truy cập dữ liệu
// model đại diện bảng trong cơ sở dữ liệu (các thuộc tính, getter, setter, constructor,toString)

public class Main {
    public static void main(String[] args) {
        try {
            Connection conn = DBConnection.getConnection();
            System.out.println("Kết nối thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}