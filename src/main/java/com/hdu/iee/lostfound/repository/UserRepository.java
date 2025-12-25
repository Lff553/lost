package com.hdu.iee.lostfound.repository;

import com.hdu.iee.lostfound.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据学号查找用户
     */
    Optional<User> findByStudentId(String studentId);

    /**
     * 检查学号是否存在
     */
    boolean existsByStudentId(String studentId);

    /**
     * 根据邮箱查找用户
     */
    Optional<User> findByEmail(String email);

    /**
     * 检查邮箱是否存在
     */
    boolean existsByEmail(String email);
}
