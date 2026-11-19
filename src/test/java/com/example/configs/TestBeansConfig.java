package com.example.configs;

import com.example.pojo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class TestBeansConfig {

    @Bean(name = "studentTable")
    public List<User> studentTable() {
        return new ArrayList<>();
    }
}
