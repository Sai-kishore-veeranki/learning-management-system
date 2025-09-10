package com.vsk.lms;

import com.vsk.lms.user.entity.User;
import com.vsk.lms.user.entity.enums.Role;
import com.vsk.lms.user.reposirtory.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@SpringBootApplication
public class LmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LmsApplication.class, args);



	}
    @Bean
    public CommandLineRunner createAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {

                User admin = new User();
                admin.setUsername("sai");
                admin.setPassword(passwordEncoder.encode("123")); // 🔐 Securely encoded
                admin.setEmail("veerankisaikishore@gmail.com");
                admin.setFullName("System Admin");
                admin.setRole(Role.valueOf("ADMIN")); // Or use Enum if preferred

                userRepository.save(admin);
               log.info(" Admin user created: " + admin.getUsername());

            User student = new User();
            student.setUsername("kiss");
            student.setPassword(passwordEncoder.encode("123")); // 🔐 Securely encoded
            student.setEmail("vsksk105@gmail.com");
            student.setFullName("kiss");
            student.setRole(Role.valueOf("STUDENT")); // Or use Enum if preferred

            userRepository.save(student);
            log.info(" student user created: " + admin.getUsername());

            User instructor = new User();
            instructor.setUsername("kishore");
            instructor.setPassword(passwordEncoder.encode("123")); // 🔐 Securely encoded
            instructor.setEmail("chandrakanthmurala@gmail.com");
            instructor.setFullName("kishore");
            instructor.setRole(Role.valueOf("INSTRUCTOR")); // Or use Enum if preferred

            userRepository.save(instructor);
            log.info(" instructor user created: " + admin.getUsername());

        };
    }


}
