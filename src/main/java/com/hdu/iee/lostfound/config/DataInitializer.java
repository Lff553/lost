package com.hdu.iee.lostfound.config;

import com.hdu.iee.lostfound.entity.LostItem;
import com.hdu.iee.lostfound.entity.LostItemStatus;
import com.hdu.iee.lostfound.entity.User;
import com.hdu.iee.lostfound.entity.UserRole;
import com.hdu.iee.lostfound.repository.LostItemRepository;
import com.hdu.iee.lostfound.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner loadSampleData(LostItemRepository lostItemRepository, UserRepository userRepository) {
        return args -> {
            // 初始化管理员用户
            if (userRepository.count() == 0) {
                User admin = new User();
                admin.setStudentId("admin");
                admin.setPassword("admin123");
                admin.setName("系统管理员");
                admin.setPhone("13800000000");
                admin.setEmail("admin@hdu.edu.cn");
                admin.setRole(UserRole.ADMIN);
                admin.setCreatedAt(LocalDateTime.now());
                admin.setUpdatedAt(LocalDateTime.now());
                userRepository.save(admin);

                // 创建一个测试学生用户
                User student = new User();
                student.setStudentId("249400104");
                student.setPassword("249400104");
                student.setName("林芳芳");
                student.setPhone("13900001111");
                student.setEmail("zhangsan@stu.hdu.edu.cn");
                student.setRole(UserRole.STUDENT);
                student.setCreatedAt(LocalDateTime.now());
                student.setUpdatedAt(LocalDateTime.now());
                userRepository.save(student);
            }

            if (lostItemRepository.count() > 0) {
                return;
            }

            LostItem card = new LostItem();
            card.setTitle("学生校园卡");
            card.setDescription("校园卡上印有信息工程学院标识，请失主联系确认。");
            card.setLocation("信息工程学院大楼一楼大厅");
            card.setCreatedAt(LocalDateTime.now().minusDays(1));
            card.setFinderName("李老师");
            card.setFinderContact("13800008888");
            card.setStatus(LostItemStatus.AVAILABLE);

            LostItem umbrella = new LostItem();
            umbrella.setTitle("黑色折叠伞");
            umbrella.setDescription("带有白色条纹边，八角伞骨完好。");
            umbrella.setLocation("图书馆入口旁");
            umbrella.setCreatedAt(LocalDateTime.now().minusDays(2));
            umbrella.setFinderName("张同学");
            umbrella.setFinderContact("13700006666");
            umbrella.setStatus(LostItemStatus.AVAILABLE);

            LostItem earphones = new LostItem();
            earphones.setTitle("蓝牙耳机");
            earphones.setDescription("白色收纳盒，盒盖有NUC贴纸。");
            earphones.setLocation("学院食堂二楼");
            earphones.setCreatedAt(LocalDateTime.now().minusDays(3));
            earphones.setFinderName("辅导员");
            earphones.setFinderContact("13600005555");
            earphones.setStatus(LostItemStatus.AVAILABLE);

            lostItemRepository.save(card);
            lostItemRepository.save(umbrella);
            lostItemRepository.save(earphones);
        };
    }
}

