package com.example.demo.Controller;

import com.example.demo.Entity.*;
import com.example.demo.Service.GroupService;
import com.example.demo.Service.TaskService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/manage")
public class ManGroupController {
    public GroupService groupService;
    public TaskService taskService;
    public UserService userService;

    public UserService getUserService() {
        return userService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public TaskService getTaskService() {
        return taskService;
    }

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }


    public GroupService getGroupService() {
        return groupService;
    }

    @Autowired
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @ResponseBody
    @GetMapping("/getManGroup")
    public List<Group> getManGroup(@RequestParam("id") String id){
        List<Group> result = groupService.getManGroup(id);
        return result;
    }

    @ResponseBody
    @GetMapping("/addGroup")
    public String createGroup(@RequestParam("userid") String memid,@RequestParam("groupname") String groupName,@RequestParam("memname") String memname){
        int insert = groupService.createGroup(groupName);
        if(insert>0){
            int groupid = groupService.getGroupId(groupName);
            if (groupid > 0){
                int result = groupService.addManager(memid,groupid,memname);
                if(result > 0){
                    return "success";
                }
            }
        }
        return "failed";
    }

    @ResponseBody
    @PostMapping("/delGroup")
    public String delGroup(@RequestBody Group group){
        int result = groupService.delGroup(group.getGroupid());
        if (result>0){
            return "success";
        }else {
            return "failed";
        }
    }

    @ResponseBody
    @GetMapping("/getName")
    public String getName(@RequestParam("id") int id){
        return groupService.getGroupName(id);
    }


    @ResponseBody
    @GetMapping("/altername")
    public String altername(@RequestParam("id") int id,@RequestParam("userid") String userid){
        String result =  groupService.getMemName(id, userid);
//        System.out.println(result);
        return result;
    }

    @ResponseBody
    @PostMapping("/alter")
    public String alter(@RequestBody Member member){
        int result = groupService.alterName(member.getMemname(),member.getUserid(),member.getGroupid());
        if(result>0){
            return "success";
        }
        return "failed";
    }

    @ResponseBody
    @GetMapping("/getTask")
    public List<Task> getTask(@RequestParam("id") int id){
        List<Task> result2 = taskService.getTask2(id);
        result2.sort(new Comparator<Task>() {
            @Override
            public int compare(Task u1, Task u2) {
                System.out.println(u1.getTitle().compareTo(u2.getTitle()));
                return u1.getTitle().compareTo(u2.getTitle()) * -1;
//                if(u1.getTitle().compareTo(u2.getTitle())) {
//                    //return -1:即为正序排序
//                    return -1;
//                }else if (u1.getAge() == u2.getAge()) {
//                    return 0;
//                }else {
//                    //return 1: 即为倒序排序
//                    return 1;
//                }
            }
        });
        return result2;
    }

    @ResponseBody
    @PostMapping("/addManager")
    public String addManager(@RequestBody Member member){
        System.out.println(member.getGroupid());
        String name = userService.getUserName(member.getUserid());
        int result = groupService.addGroup(member.getUserid(),member.getGroupid(),name,0);
        if (result>0){
            return "success";
        }else {
            return "failed";
        }
    }

    @ResponseBody
    @GetMapping("/getMember")
    public List<Member> getMember(@RequestParam("id") int id){
        return groupService.getMember(id);
    }

    @ResponseBody
    @PostMapping("/delMember")
    public String delMember(@RequestBody Member member){
        int result = groupService.delMember(member.getUserid(),member.getGroupid());
        if (result>0){
            return "success";
        }else {
            return "failed";
        }
    }

}
