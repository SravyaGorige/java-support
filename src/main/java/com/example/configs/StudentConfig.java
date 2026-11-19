package com.example.configs;


import com.example.pojo.User;
import com.example.utils.ExcelTableManager;
import com.example.utils.JDBCManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
public class StudentConfig {

    public List<User> users = new ArrayList<>();
   // public User user = new User();

    @Bean(name = "studentTable")
    public List<User> studentTable() throws IOException {
        List<User> users = new ArrayList<>();
        System.out.println("I am reading excel");
        users.addAll(JDBCManager.getAllUsers());
        /*for(Map<String,String> student :ExcelTableManager.readTable("StudentData.xlsx") ){
            User user = new User();
            for(Map.Entry<String,String> stu: student.entrySet()) {

                if(stu.getKey().equalsIgnoreCase("Name")){
                    user.setName(stu.getValue());
                }
                else if(stu.getKey().equalsIgnoreCase("RollNo")) {
                    user.setRollNo(Integer.parseInt(stu.getValue()));
                }

            }
            users.add(user);
        }*/
        return users;
    }
}
