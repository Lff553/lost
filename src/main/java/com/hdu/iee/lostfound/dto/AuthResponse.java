package com.hdu.iee.lostfound.dto;

import com.hdu.iee.lostfound.entity.UserRole;

public class AuthResponse {
    private boolean success;
    private String message;
    private UserData user;
    private String token; // 可选：如果需要JWT token

    // 默认构造函数
    public AuthResponse() {}

    // 成功响应构造函数
    public AuthResponse(boolean success, String message, UserData user) {
        this.success = success;
        this.message = message;
        this.user = user;
    }

    // 失败响应构造函数
    public AuthResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    // 静态方法：创建成功响应
    public static AuthResponse success(String message, UserData user) {
        return new AuthResponse(true, message, user);
    }

    // 静态方法：创建失败响应
    public static AuthResponse failure(String message) {
        return new AuthResponse(false, message);
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // 内部类：用户数据
    public static class UserData {
        private Long id;
        private String studentId;
        private String name;
        private String phone;
        private String email;
        private UserRole role;

        // 默认构造函数
        public UserData() {}

        // 构造函数
        public UserData(Long id, String studentId, String name, String phone, String email, UserRole role) {
            this.id = id;
            this.studentId = studentId;
            this.name = name;
            this.phone = phone;
            this.email = email;
            this.role = role;
        }

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public UserRole getRole() {
            return role;
        }

        public void setRole(UserRole role) {
            this.role = role;
        }
    }
}
