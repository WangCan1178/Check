package com.example.demo.Controller;

import com.example.demo.Entity.Group;
import com.example.demo.Entity.Member;
import com.example.demo.Entity.TaskInfo;
import com.example.demo.Service.GroupService;
import com.example.demo.Service.TaskService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
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
    public String createGroup(@RequestParam("memid") String memid,@RequestParam("groupname") String groupName,@RequestParam("memname") String memname){
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
        int result = groupService.delGroup(group.getId());
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
        System.out.println(result);
        return result;
    }

    @ResponseBody
    @PostMapping("/alter")
    public String alter(@RequestBody Member member){
        int result = groupService.alterName(member.getMemname(),member.getMemid(),member.getGroupid());
        if(result>0){
            return "success";
        }
        return "failed";
    }

//    @ResponseBody
//    @GetMapping("/getTask")
//    public List<TaskInfo> getTask(@RequestParam("id") int id){
//        return taskService.getTask2(id);
//    }

    @ResponseBody
    @PostMapping("/addManager")
    public String addManager(@RequestBody Member member){
        String name = userService.getUserName(member.getMemid());
        int result = groupService.addGroup(member.getMemid(),member.getGroupid(),name,0);
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
        int result = groupService.delMember(member.getMemid(),member.getGroupid());
        if (result>0){
            return "success";
        }else {
            return "failed";
        }
    }

}
