package com.example.service;

import com.example.model.Account;
import com.example.repository.TaiKhoanRepository;

public class TaiKhoanService {
    private TaiKhoanRepository repository;

    public TaiKhoanService() {
        this.repository = new TaiKhoanRepository();
    }

    public Account login(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            System.out.println("Loi: Tai khoan va mat khau khong duoc de trong!");
            return null;
        }
        return repository.authenticate(username, password);
    }
}
