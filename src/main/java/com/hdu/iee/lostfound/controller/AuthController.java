package com.hdu.iee.lostfound.controller;

import com.hdu.iee.lostfound.dto.AuthResponse;
import com.hdu.iee.lostfound.dto.LoginRequest;
import com.hdu.iee.lostfound.dto.RegisterRequest;
import com.hdu.iee.lostfound.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = userService.register(request);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = userService.login(request);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 检查学号是否存在
     */
    @GetMapping("/check-student-id/{studentId}")
    public ResponseEntity<Boolean> checkStudentId(@PathVariable String studentId) {
        boolean exists = userService.existsByStudentId(studentId);
        return ResponseEntity.ok(exists);
    }

    /**
     * 用户登出（前端处理，后端可选）
     */
    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout() {
        return ResponseEntity.ok(AuthResponse.success("登出成功", null));
    }
}
