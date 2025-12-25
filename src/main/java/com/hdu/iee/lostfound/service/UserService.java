package com.hdu.iee.lostfound.service;

import com.hdu.iee.lostfound.dto.AuthResponse;
import com.hdu.iee.lostfound.dto.LoginRequest;
import com.hdu.iee.lostfound.dto.RegisterRequest;
import com.hdu.iee.lostfound.entity.User;
import com.hdu.iee.lostfound.entity.UserRole;
import com.hdu.iee.lostfound.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 用户注册
     */
    public AuthResponse register(RegisterRequest request) {
        try {
            // 验证密码确认
            if (!request.getPassword().equals(request.getConfirmPassword())) {
                return AuthResponse.failure("两次输入的密码不一致");
            }

            // 检查学号是否已存在
            if (userRepository.existsByStudentId(request.getStudentId())) {
                return AuthResponse.failure("该学号已被注册");
            }

            // 检查邮箱是否已存在（如果提供了邮箱）
            if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
                if (userRepository.existsByEmail(request.getEmail())) {
                    return AuthResponse.failure("该邮箱已被注册");
                }
            }

            // 创建新用户
            User user = new User();
            user.setStudentId(request.getStudentId());
            user.setPassword(request.getPassword()); // 注意：实际项目中应该加密密码
            user.setName(request.getName());
            user.setPhone(request.getPhone());
            user.setEmail(request.getEmail());
            user.setRole(UserRole.STUDENT); // 默认为学生角色

            // 保存用户
            User savedUser = userRepository.save(user);

            // 创建用户数据响应
            AuthResponse.UserData userData = new AuthResponse.UserData(
                    savedUser.getId(),
                    savedUser.getStudentId(),
                    savedUser.getName(),
                    savedUser.getPhone(),
                    savedUser.getEmail(),
                    savedUser.getRole()
            );

            return AuthResponse.success("注册成功", userData);

        } catch (Exception e) {
            return AuthResponse.failure("注册失败：" + e.getMessage());
        }
    }

    /**
     * 用户登录
     */
    public AuthResponse login(LoginRequest request) {
        try {
            // 查找用户
            Optional<User> userOptional = userRepository.findByStudentId(request.getStudentId());

            if (userOptional.isEmpty()) {
                return AuthResponse.failure("学号不存在");
            }

            User user = userOptional.get();

            // 验证密码（注意：实际项目中应该使用加密密码比较）
            if (!user.getPassword().equals(request.getPassword())) {
                return AuthResponse.failure("密码错误");
            }

            // 创建用户数据响应
            AuthResponse.UserData userData = new AuthResponse.UserData(
                    user.getId(),
                    user.getStudentId(),
                    user.getName(),
                    user.getPhone(),
                    user.getEmail(),
                    user.getRole()
            );

            return AuthResponse.success("登录成功", userData);

        } catch (Exception e) {
            return AuthResponse.failure("登录失败：" + e.getMessage());
        }
    }

    /**
     * 根据学号查找用户
     */
    public Optional<User> findByStudentId(String studentId) {
        return userRepository.findByStudentId(studentId);
    }

    /**
     * 检查学号是否存在
     */
    public boolean existsByStudentId(String studentId) {
        return userRepository.existsByStudentId(studentId);
    }

    /**
     * 创建管理员用户（用于初始化）
     */
    public User createAdmin(String studentId, String password, String name) {
        User admin = new User();
        admin.setStudentId(studentId);
        admin.setPassword(password);
        admin.setName(name);
        admin.setRole(UserRole.ADMIN);
        return userRepository.save(admin);
    }
}
