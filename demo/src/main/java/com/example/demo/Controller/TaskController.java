package com.example.demo.Controller;

import com.example.demo.Entity.Isend;
import com.example.demo.Entity.Member;
import com.example.demo.Entity.Task;
import com.example.demo.Service.PicService;
import com.example.demo.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
//import java.sql.Date;
import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/task")
public class TaskController {
    private TaskService taskService;
    public PicService picService;

    public PicService getPicService() {
        return picService;
    }
    public TaskService getTaskService() {
        return taskService;
    }

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @ResponseBody
    @GetMapping("/getTaskInfo")
    public Task getTaskInfo(@RequestParam("id") int id){
//        System.out.println(taskService.getTaskInfo(id));
        return taskService.getTaskInfo(id);
    }



    @Autowired
    public void setPicService(PicService picService){
        this.picService = picService;
    }



    @ResponseBody
    @GetMapping("/getTask2")
    public List<Task> getTask2(@RequestParam("id") int id){
        List<Task> result2 = taskService.getTask2(id);
        return result2;
    }

    @ResponseBody
    @GetMapping("/getTask")
    public List<Isend> getTask(@RequestParam("id") int id, String userid){
        List<Isend> result = taskService.getTask(id,userid);
        return result;
    }

    @ResponseBody
    @PostMapping("/insertTaskJKM")
    public String insertTaskJKM(@RequestBody Task task){
//        System.out.println(task.getTaskid());
//        System.out.println(task.getTitle());
//        System.out.println(task.getGroupid());
//        System.out.println(task.getDescription());
//        System.out.println(task.getPublishtime().toString());
//        System.out.println(task.getEndtime().toString());
//        int result = taskService.insertTaskJKM(task);
        int result = taskService.insertTaskJKM(task.getTitle(),task.getGroupid(),task.getDescription(),task.getPublishtime(),task.getEndtime());
        if (result>0){
            return "success";
        }
        else {
            return "failed";
        }
    }
//    @ResponseBody
//    @PostMapping("/insertTask")
//    public String insertTask(@RequestBody Task task){
//        int result = taskService.insertTask(task.getTitle(),task.getPhoto(),task.getDescription(),task.getRecognition(),task.getGroupid(),task.getPublishtime());
//        if (result>0){
//            return "success";
//        }
//        else {
//            return "failed";
//        }
//    }

    @ResponseBody
    @PostMapping("/deleteTask")
    public String deleteTask(@RequestBody Task task){
        int result = taskService.deleteTask(task.getTaskid());
        if (result>0){
            return "success";
        }else {
            return "failed";
        }
    }

    @ResponseBody
    @PostMapping("/endTask")
    public String endTask(@RequestBody Task task){
        int result = taskService.endTask(task.getEndtime(),task.getTaskid());
        if (result>0){
            return "success";
        }else {
            return "failed";
        }
    }
    @ResponseBody
    @GetMapping("/altername")
    public String altername(@RequestParam("id") int id){
        String result =  taskService.getTaskTitle(id);
        return result;
    }

    @ResponseBody
    @GetMapping("/alterdescription")
    public String alterdescription(@RequestParam("id") int id){
        String result =  taskService.getDescription(id);
        return result;
    }
}
