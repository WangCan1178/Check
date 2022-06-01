package com.example.demo.Controller;

import com.example.demo.Entity.User;
import com.example.demo.Service.GroupService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("")
public class UserController {
    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @GetMapping("/login")
    public User login(@RequestParam("username") String id, @RequestParam("password") String password) {
        User result = userService.login(id, password);
        System.out.println("eee" + result);
        if (result != null) {
            return result;
        } else {
            return null;
        }
    }

    @ResponseBody
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        int result = userService.register(user.getId(), user.getName(), user.getPassword());
        if (result > 0) {
            return "success";
        } else {
            return "failed";
        }
    }

    @ResponseBody
    @GetMapping("/phone")
    public String phone(@RequestParam("phone") String phone1) {
        return userService.phone(phone1);
    }

    @ResponseBody
    @PostMapping("alterpsw")
    public String alterpsw(@RequestBody User user) {
        int result = userService.alterPsw(user.getId(), user.getPassword());
        if (result > 0) {
            return "success";
        } else {
            return "failed";
        }
    }
}
