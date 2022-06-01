package com.example.demo.Controller;

import com.example.demo.Entity.User;
import com.example.demo.Service.GroupService;
import com.example.demo.Service.PicService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("")
public class UserController {
    private UserService userService;
    private GroupService groupService;
    private PicService picService;

    public GroupService getGroupService() {
        return groupService;
    }

    @Autowired
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    public UserService getUserService() {
        return userService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public PicService getPicService() {
        return picService;
    }

    @Autowired
    public void setPicService(PicService picService) {
        this.picService = picService;
    }

    @ResponseBody
    @GetMapping("/login")
    public User login(@RequestParam("username") String id,@RequestParam("password") String password){
//        System.out.println(id+password);
        User result = userService.login(id,password);
        if(result != null){

            return result;
        }else {
            return null;
        }
    }

    @ResponseBody
    @PostMapping("/register")
    public String register(@RequestBody User user){
        int result = userService.register(user.getUserid(),user.getName(),user.getPassword());
        if (result>0){
            return "success";
        }else {
            return "failed";
        }
    }

    @ResponseBody
    @GetMapping("/phone")
    public String phone(@RequestParam("phone") String phone1){
        return userService.phone(phone1);
    }

    @ResponseBody
    @PostMapping("/alterpsw")
    public String alterpsw(@RequestBody User user){
//        System.out.println(user.getUserid()+user.getPassword());
        int result = userService.alterPsw(user.getUserid(),user.getPassword());
        if (result>0){
            return "success";
        }else {
            return "failed";
        }
    }

    @ResponseBody
    @GetMapping("/delUser")
    public String delUser(@RequestParam("id") String id){
        int result = userService.delUser(id);
        if (result>0){
            return "success";
        }else {
            return "failed";
        }
    }

    @ResponseBody
    @GetMapping("/getInfo")
    public int[] getNum(@RequestParam("id") String id){
        int[] result = {0,0,0};
        result[0] = groupService.getAddGroup(id).size();
        result[1] = groupService.getManGroup(id).size();
        result[2] = picService.getUserTask(id);
        return result;
    }



}
