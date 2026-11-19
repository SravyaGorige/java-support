package com.example.controllers;

import com.example.pojo.User;
import com.example.pojo.UserInfo;
import com.example.utils.ExcelTableManager;
import com.example.utils.JDBCManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    @Qualifier("studentTable")
    private List<User> users;

    @GetMapping("/hello")
    public String sayhello() {
        return "HELLO";
    }
    @GetMapping("/test")
    public List<String> test() {
        return List.of("A", "B", "C");
    }

    @GetMapping("/message")
    public String sayTextHello(@RequestParam("text") String msg) {
        return "HELLO :" + msg;
    }

    @PostMapping("/message")
    public String say(@RequestBody UserInfo user) {
        String message = user.getGender().equalsIgnoreCase("male") ? "He is " : "She is ";
        return message + user.getMessage();
    }

    /*@DeleteMapping("/delete")
    public ResponseEntity<User> deleteUser(@RequestBody List<User> allUsersr){
        if(u.get)
            return ResponseEntity.notFound().build();

    }*/

    @PostMapping("/saveall")
    public List<User> saveUserall(@RequestBody List<User> allUsers) throws IOException {
        for (User user: allUsers){
            JDBCManager.saveUser(user);
        }

        users.addAll(allUsers);
        return allUsers;
    }

   /* @GetMapping(value="/users", produces = "application/json")
    public List<User> getUsers() {
        return users;
    }*/
   @GetMapping("/users")
   public ResponseEntity<List<User>> getUsers() {
       return ResponseEntity.ok(users);
   }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        for (User u : users) {
            if (u.getName().equalsIgnoreCase(user.getName())) {
                u.setRollNo(user.getRollNo());
                return ResponseEntity.ok(u);
            }
        }
        return ResponseEntity.notFound().build();
    }



}
