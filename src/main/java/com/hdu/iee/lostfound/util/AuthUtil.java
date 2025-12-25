package com.hdu.iee.lostfound.util;

import com.hdu.iee.lostfound.entity.User;
import com.hdu.iee.lostfound.exception.NotFoundException;
import com.hdu.iee.lostfound.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    private final UserRepository userRepository;

    public AuthUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 根据学号获取用户（临时认证方案）
     */
    public User getCurrentUser(String studentId) {
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new RuntimeException("用户未登录");
        }

        return userRepository.findByStudentId(studentId.trim())
                .orElseThrow(() -> new NotFoundException("用户不存在"));
    }
}
